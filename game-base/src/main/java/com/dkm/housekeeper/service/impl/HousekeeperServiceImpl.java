package com.dkm.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.SeedPlantUnlock;
import com.dkm.feign.entity.SeedPlantVo;
import com.dkm.housekeeper.dao.HousekeeperMapper;
import com.dkm.housekeeper.entity.HousekeeperEntity;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.housekeeper.service.HousekeeperService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/8 16:41
 */
@Service
public class HousekeeperServiceImpl implements HousekeeperService {

    @Resource
    private HousekeeperMapper housekeeperMapper;
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private ResourceFeignClient resourceFeignClient;

    /**
     * 开通、续费
     * @param userId
     * @param money
     * @return
     */
    @Override
    public int openHousekeeper(Long userId, BigDecimal money) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId,userId));
        LocalDateTime now = LocalDateTime.now();
        if(selectOne!=null){
            //判断是否续费
            selectOne.setOrderTime(now);
            selectOne.setHousekeeperMoney(money);
            selectOne.setExpireTime(selectOne.getExpireTime().isBefore(now)?now.minusDays(-30):selectOne.getExpireTime().minusDays(-30));
            return  housekeeperMapper.updateById(selectOne);
        }else {
            //新开管家
            HousekeeperEntity housekeeper = new HousekeeperEntity();
            housekeeper.setUserId(userId);
            housekeeper.setIsEffective(1);
            housekeeper.setHousekeeperMoney(money);
            housekeeper.setOrderTime(LocalDateTime.now());
            housekeeper.setHousekeeperId(idGenerator.getNumberId());
            housekeeper.setExpireTime(now.minusDays(-30));
            return housekeeperMapper.insert(housekeeper);
        }
    }

    /**
     * 管家到期信息
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> remnantDays(Long userId) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        Map<String,Object> result = new HashMap<>();
        //返回管家时间信息
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId, userId));
        if(selectOne!=null&&selectOne.getIsEffective()!=0){
            //管家过期
            LocalDateTime now = LocalDateTime.now();
            if(selectOne.getExpireTime().isBefore(now)){
                selectOne.setIsEffective(0);
                housekeeperMapper.updateById(selectOne);
                throw new  ApplicationException(CodeType.SERVICE_ERROR,"管家过期");
            }
            //返回管家时间
            result.put("status",now.isBefore(selectOne.getEndWorkTime())?1:0);
            result.put("toDayTime", DateUtils.formatDate(now.toLocalDate()).replace("-","/")+" 12:00:00");
            result.put("startWorkTime",selectOne.getStartWorkTime()==null?null: DateUtils.formatDate(selectOne.getStartWorkTime()).replace("-","/"));
            result.put("reTime", DateUtils.formatDateTime(selectOne.getExpireTime()).replace("-","/"));
            result.put("endWorkTime",selectOne.getEndWorkTime()==null?null: DateUtils.formatDateTime(selectOne.getEndWorkTime()).replace("-","/"));
            return result;
        }else {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"未找到记录");
        }
    }

    /**
     * 管家修改时间
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> updateTime(Long userId) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId, userId));
        LocalDateTime now = LocalDateTime.now();
        selectOne.setEndWorkTime(now.minusHours(-8));
        selectOne.setStartWorkTime(now);
        selectOne.setSeedGold(0);
        selectOne.setSeedExp(0);
        selectOne.setSeedCount(0);
        //第二天12点时间
        Map<String,String> result = new HashMap<>();
        if(housekeeperMapper.updateById(selectOne)>=0){
            result.put("endWorkTime", DateUtils.formatDateTime(selectOne.getEndWorkTime()).replace("-","/"));
            return result;
        }else {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"操作失败");
        }
    }

    @Override
    public List<TbEquipmentVo> getBoxEquipment(Long userId){
        HousekeeperEntity housekeeperEntity = housekeeperMapper.selectOne(new QueryWrapper<HousekeeperEntity>().lambda().eq(HousekeeperEntity::getUserId, userId));
        if(housekeeperEntity==null||housekeeperEntity.getStartWorkTime()==null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"管家没有工作");
        }
        List<Long> allBoxId = housekeeperMapper.getAllBoxId();
        //计算当前时间戳到开始时间戳秒数
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(housekeeperEntity.getEndWorkTime())){
            now = housekeeperEntity.getEndWorkTime();
        }
        long time = now.toEpochSecond(ZoneOffset.of("+8"))- housekeeperEntity.getStartWorkTime().toEpochSecond(ZoneOffset.of("+8"));
        //宝箱15分钟开一次 计算次数
        long count = time / (15 * 60);
        List<Long> boxId =new ArrayList<>();
        for (long i = 0; i < count; i++) {
            boxId.addAll(allBoxId);
        }
        Result<List<TbEquipmentVo>> listResult = resourceFeignClient.selectByBoxIdTwo(boxId);
        if(listResult.getCode()!=0){
            throw  new ApplicationException(CodeType.SERVICE_ERROR);
        }
        return listResult.getData();
    }


    @Override
    public boolean housekeeperStatus(Long userId) {
        HousekeeperEntity housekeeperEntity = housekeeperMapper.selectOne(new QueryWrapper<HousekeeperEntity>().lambda().eq(HousekeeperEntity::getUserId,userId));
        if(housekeeperEntity!=null&&housekeeperEntity.getIsEffective()==1){
            //管家在上班 不能进行偷取
            return LocalDateTime.now().isAfter(housekeeperEntity.getEndWorkTime());
        }else {
            //表示可以偷取等操作
            return true;
        }
    }

    @Override
    public Map<String,Integer> getSeed(Long userId) {
        Map<String,Integer> map = new HashMap<>();
        //查询管家状态
        HousekeeperEntity housekeeperEntity = housekeeperMapper.selectOne(new QueryWrapper<HousekeeperEntity>().lambda().eq(HousekeeperEntity::getUserId, userId));
        if(housekeeperEntity==null||housekeeperEntity.getIsEffective()==0){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND,"管家已过期");
        }
        //得到种植的种子
        Result<List<SeedPlantUnlock>> listResult = resourceFeignClient.queryUserIdSeed(userId);
        if(listResult.getCode()!=0){
            throw new ApplicationException(CodeType.DATABASE_ERROR,listResult.getMsg());
        }
        //种子数据
        List<SeedPlantUnlock> data = listResult.getData();
        //种子土地
        int size = data.size();
        //当前时间判断
        LocalDateTime now = LocalDateTime.now();
        now = now.isAfter(housekeeperEntity.getEndWorkTime())?housekeeperEntity.getEndWorkTime():now;
        LocalDateTime startWorkTime = housekeeperEntity.getStartWorkTime();
        //计算种子成熟时间 得到秒数。等级的3次方除以2.0*20+60
        Integer integer = (int) Math.pow(data.get(0).getSeedGrade(), 3 / 2.0) * 20 + 60;
        //计算需要收取几次
        long l = now.toEpochSecond(ZoneOffset.of("+8")) - startWorkTime.toEpochSecond(ZoneOffset.of("+8"));
        //需要收取的次数 并更新次数
        int count =(int) l / integer-housekeeperEntity.getSeedCount();
        housekeeperEntity.setSeedCount(housekeeperEntity.getSeedCount()+count);
        //收取的经验
        int exp = count * data.size() * data.get(0).getSeedExperience();
        //收取的金币
        int gold = count * data.size() * data.get(0).getSeedGold();
        //调用收取种子的接口
        housekeeperEntity.setSeedGold(housekeeperEntity.getSeedGold()+gold);
        housekeeperEntity.setSeedExp(housekeeperEntity.getSeedExp()+exp);
        housekeeperMapper.updateById(housekeeperEntity);
        map.put("gold",housekeeperEntity.getSeedGold());
        map.put("exp",housekeeperEntity.getSeedExp());
        return map;
    }

}
