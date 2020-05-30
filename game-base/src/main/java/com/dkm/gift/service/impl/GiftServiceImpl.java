package com.dkm.gift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.gift.dao.GiftDao;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.entity.dto.UserInfoDto;
import com.dkm.gift.entity.vo.SendGiftVo;
import com.dkm.gift.service.GiftService;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<GiftEntity> getAllGift(Integer type) {
        return giftDao.selectList(new QueryWrapper<GiftEntity>().lambda().eq(GiftEntity::getGiType,type));
    }

    @Override
    public void sendGift(SendGiftVo sendGiftVo) {
        UserInfoDto userInfo = giftDao.getUserInfo(sendGiftVo.getSendId());
        if(sendGiftVo.getGold()!=0&&userInfo.getUserInfoGold()<sendGiftVo.getGold()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"金币不足");
        }
        if(sendGiftVo.getDiamond()!=0&&userInfo.getUserInfoDiamonds()<sendGiftVo.getDiamond()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"钻石不足");
        }
        Integer integer = giftDao.updateUserInfo(sendGiftVo);
        if(integer<1){
            throw  new ApplicationException(CodeType.DATABASE_ERROR,"更新信息失败");
        }
    }
}
