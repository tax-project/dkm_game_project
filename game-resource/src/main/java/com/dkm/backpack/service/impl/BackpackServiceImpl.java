package com.dkm.backpack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.dao.GoodsMapper;
import com.dkm.backpack.entity.BackPackEntity;
import com.dkm.backpack.entity.GoodsEntity;
import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.entity.bo.SellGoodsInfo;
import com.dkm.backpack.entity.vo.FoodInfoVo;
import com.dkm.backpack.entity.vo.GoldStarVo;
import com.dkm.backpack.entity.vo.UserBackpackGoodsVo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 21:52
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BackpackServiceImpl implements IBackpackService {

    @Resource
    private BackpackMapper backpackMapper;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public List<UserBackpackGoodsVo> getUserBackpackGoods(Long userId) {
        List<UserBackpackGoodsVo> backpackGoods = backpackMapper.getBackpackGoods(userId);
        int number = backpackGoods==null?30:(30-backpackGoods.size());
        for (int i = 0; i < number; i++) {
            backpackGoods.add(null);
        }
        return backpackGoods;
    }

    @Override
    public void addBackpackGoods(AddGoodsInfo addGoodsInfo) {
        GoodsEntity goodsEntity = goodsMapper.selectById(addGoodsInfo.getGoodId());
        if(goodsEntity==null||goodsEntity.getGoodType()==1){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"找不到该物品");}
        BackPackEntity backPackEntity = backpackMapper.
              selectOne(new LambdaQueryWrapper<BackPackEntity>()
                            .eq(BackPackEntity::getUserId, addGoodsInfo.getUserId())
                            .eq(BackPackEntity::getGoodId, addGoodsInfo.getGoodId()));
        int success = 0;
        if(backPackEntity!=null){
            int i = backPackEntity.getNumber() + addGoodsInfo.getNumber();
            if(i<0){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"数量不足");
            }
            if(i==0){
                success= backpackMapper.deleteById(backPackEntity.getBackpackId());
            }
            else {
                backPackEntity.setNumber(i);
                success = backpackMapper.updateById(backPackEntity);
            }
        }else {
            if(addGoodsInfo.getNumber()<0){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"数量不足");
            }
            else {
                if(backpackMapper.getBackpackNumber(addGoodsInfo.getUserId())>=30){
                    throw new ApplicationException(CodeType.SERVICE_ERROR,"背包空间不足");
                }
                BackPackEntity addBackpack = new BackPackEntity();
                addBackpack.setUserId(addGoodsInfo.getUserId());
                addBackpack.setGoodId(addGoodsInfo.getGoodId());
                addBackpack.setBackpackId(idGenerator.getNumberId());
                addBackpack.setNumber(addGoodsInfo.getNumber());
                success = backpackMapper.insert(addBackpack);
            }
        }
        if(success<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"更新背包失败");
        }
    }

    @Override
    public void sellBackpackGoods(SellGoodsInfo sellGoodsInfo) {
        BackPackEntity backPackEntity = backpackMapper.selectById(sellGoodsInfo.getBackpackId());
        if(backPackEntity==null){throw new ApplicationException(CodeType.PARAMETER_ERROR,"背包没有该物品");}
        GoodsEntity goodsEntity = goodsMapper.selectById(backPackEntity.getGoodId());
        if(goodsEntity.getGoodType()==1){
            int i = backpackMapper.deleteById(backPackEntity.getBackpackId());
            i += equipmentMapper.deleteById(backPackEntity.getBackpackId());
            if(i!=2){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售时出现一点意外");}
        }else {
            int i = backPackEntity.getNumber() - sellGoodsInfo.getNumber();
            if(i<0){throw new ApplicationException(CodeType.SERVICE_ERROR,"背包没有这么多数量");}
            else if(i==0){
                int i1 = backpackMapper.deleteById(sellGoodsInfo.getBackpackId());
                if(i1<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售失败");}
            }else{
                backPackEntity.setNumber(i);
                int i1 = backpackMapper.updateById(backPackEntity);
                if(i1<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售失败");}
            }
        }
        IncreaseUserInfoBO userInfoBO = new IncreaseUserInfoBO();
        userInfoBO.setUserId(sellGoodsInfo.getUserId());
        userInfoBO.setUserInfoGold(goodsEntity.getGoodType()==1?50:sellGoodsInfo.getNumber()*50);
        userFeignClient.increaseUserInfo(userInfoBO);
    }

    @Override
    public GoldStarVo getStar(Long userId) {
        return backpackMapper.getStars(userId);
    }

    @Override
    public List<FoodInfoVo> getFood(Long userId) {
        return backpackMapper.getFoods(userId);
    }
}
