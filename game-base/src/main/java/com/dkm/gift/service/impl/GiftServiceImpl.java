package com.dkm.gift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.gift.dao.GiftDao;
import com.dkm.gift.dao.GiftRankingDao;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.entity.GiftRankingEntity;
import com.dkm.gift.entity.dto.GiftRankingDto;
import com.dkm.gift.entity.dto.UserInfoDto;
import com.dkm.gift.entity.vo.SendGiftVo;
import com.dkm.gift.service.GiftService;
import com.dkm.medal.dao.MedalDao;
import com.dkm.medal.dao.MedalUserDao;
import com.dkm.medal.entity.MedalEntity;
import com.dkm.medal.entity.MedalUserEntity;
import com.dkm.utils.IdGenerator;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-05-27 09:26
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class GiftServiceImpl implements GiftService {

    @Resource
    private GiftDao giftDao;

    @Resource
    private MedalDao medalDao;

    @Resource
    private MedalUserDao medalUserDao;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private GiftRankingDao giftRankingDao;

    @Override
    public List<GiftEntity> getAllGift(Integer type) {
        return giftDao.selectList(new QueryWrapper<GiftEntity>().lambda().eq(GiftEntity::getGiType,type));
    }

    @Override
    public void sendGift(SendGiftVo sendGiftVo) {
        //更新送礼人和收礼人信息
        UserInfoDto userInfo = giftDao.getUserInfo(sendGiftVo.getSendId());
        if(sendGiftVo.getGold()>0&&userInfo.getUserInfoGold()<sendGiftVo.getGold()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"金币不足");
        }
        if(sendGiftVo.getDiamond()>0&&userInfo.getUserInfoDiamonds()<sendGiftVo.getDiamond()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"钻石不足");
        }
        //更新送礼人钻石、金币
        Integer integer = giftDao.updateUserInfo(sendGiftVo);
        //更新收，礼人魅力等值
        Integer integer1 = giftDao.updateUserCharm(sendGiftVo);
        if(integer<1||integer1<1){
            throw  new ApplicationException(CodeType.DATABASE_ERROR,"更新信息失败");
        }
        //查询礼物勋章信息
        MedalEntity medalEntity = medalDao.selectOne(new QueryWrapper<MedalEntity>().lambda().eq(MedalEntity::getGiId, sendGiftVo.getGiftId()));
        //更新送礼排行表
        List<GiftRankingEntity> giftRankingEntities = giftRankingDao.list(new LambdaQueryWrapper<GiftRankingEntity>().in(GiftRankingEntity::getUserId,Stream.of(sendGiftVo.getSendId(),sendGiftVo.getReceiveId()).collect(Collectors.toList())));
        if(giftRankingEntities.stream().noneMatch(a-> a.getUserId().equals(sendGiftVo.getSendId())))giftRankingEntities.add(new GiftRankingEntity(sendGiftVo.getSendId(),0,0));
        if(giftRankingEntities.stream().noneMatch(a-> a.getUserId().equals(sendGiftVo.getReceiveId())))giftRankingEntities.add(new GiftRankingEntity(sendGiftVo.getReceiveId(),0,0));
        giftRankingEntities.forEach(a->{
            if(a.getUserId().equals(sendGiftVo.getSendId()))a.setSend(sendGiftVo.getCharm());
            else a.setAccept(sendGiftVo.getCharm());
        });
        giftRankingDao.updateBatchById(giftRankingEntities);
        //礼物有勋章
        if(medalEntity!=null){
            //是否有送礼记录
            MedalUserEntity medalUserEntity = medalUserDao.getOne(new QueryWrapper<MedalUserEntity>().lambda()
                    .eq(MedalUserEntity::getUserId, sendGiftVo.getSendId())
                    .eq(MedalUserEntity::getMedalId, medalEntity.getMedalId()));
            if(medalUserEntity==null){
                medalUserEntity = new MedalUserEntity();
                medalUserEntity.setMedalId(medalEntity.getMedalId());
                medalUserEntity.setUserId(sendGiftVo.getSendId());
                medalUserEntity.setMuId(idGenerator.getNumberId());
                medalUserEntity.setProcess(0);
                medalUserEntity.setMedalLevel(0);
                medalUserEntity.setMedalReceiveCount(0);
            }
            //礼物勋章类型0礼物1幸运
            if(medalEntity.getMedalType()==0){
                medalUserEntity.setProcess(medalUserEntity.getProcess()+1);
            }else if(medalEntity.getMedalType()==1){
                medalUserEntity.setProcess(medalUserEntity.getProcess()+sendGiftVo.getDiamond());
            }
            //判断当前级别
            medalUserEntity.setMedalLevel(medalUserEntity.getProcess()>=medalEntity.getMedalProcess3()?3:
                                        (medalUserEntity.getProcess()>=medalEntity.getMedalProcess2()?2:
                                        (medalUserEntity.getProcess()>=medalEntity.getMedalProcess1()?1:0)));
            medalUserDao.saveOrUpdate(medalUserEntity);
        }
    }

    @Override
    public List<GiftRankingDto> getGiftRanking(Integer type) {
        if(type==1){
            return giftRankingDao.getGiftRanking("send");
        }else{
            return giftRankingDao.getGiftRanking("accept");
        }
    }
}
