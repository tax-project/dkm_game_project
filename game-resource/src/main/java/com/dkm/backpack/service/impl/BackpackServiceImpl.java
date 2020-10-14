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
import com.dkm.personalcenter.entity.bo.PsBottleBo;
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
        //查询到背包的信息
        List<UserBackpackGoodsVo> backpackGoods = backpackMapper.getBackpackGoods(userId);
        int number = backpackGoods==null?30:(30-backpackGoods.size());
        for (int i = 0; i < number; i++) {
            backpackGoods.add(null);
        }
        //返回
        return backpackGoods;
    }

    @Override
    public void addBackpackGoods(AddGoodsInfo addGoodsInfo) {
        //根据物品id查询物品信息
        GoodsEntity goodsEntity = goodsMapper.selectById(addGoodsInfo.getGoodId());
        if(goodsEntity==null||goodsEntity.getGoodType()==1){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"找不到该物品");}
        //根据用户id和物品id进行查询
        BackPackEntity backPackEntity = backpackMapper.
              selectOne(new LambdaQueryWrapper<BackPackEntity>()
                            .eq(BackPackEntity::getUserId, addGoodsInfo.getUserId())
                            .eq(BackPackEntity::getGoodId, addGoodsInfo.getGoodId()));
        int success = 0;
        if(backPackEntity!=null){
            //得到数量
            int i = backPackEntity.getNumber() + addGoodsInfo.getNumber();
            if(i<0){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"数量不足");
            }
            if(i==0){
                success= backpackMapper.deleteById(backPackEntity.getBackpackId());
            }
            else {
                backPackEntity.setNumber(i);
                //修改背包信息
                success = backpackMapper.updateById(backPackEntity);
            }
        }else {
            if(addGoodsInfo.getNumber()<0){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"数量不足");
            }
            else {
                //判断背包空间是否足够
                if(backpackMapper.getBackpackNumber(addGoodsInfo.getUserId())>=30){
                    throw new ApplicationException(CodeType.SERVICE_ERROR,"背包空间不足");
                }
                BackPackEntity addBackpack = new BackPackEntity();
                addBackpack.setUserId(addGoodsInfo.getUserId());
                addBackpack.setGoodId(addGoodsInfo.getGoodId());
                addBackpack.setBackpackId(idGenerator.getNumberId());
                addBackpack.setNumber(addGoodsInfo.getNumber());
                //添加背包
                success = backpackMapper.insert(addBackpack);
            }
        }
        if(success<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"更新背包失败");
        }
    }

    @Override
    public void sellBackpackGoods(SellGoodsInfo sellGoodsInfo) {
        //根据背包Id查询背包信息
        BackPackEntity backPackEntity = backpackMapper.selectById(sellGoodsInfo.getBackpackId());
        if(backPackEntity==null){throw new ApplicationException(CodeType.PARAMETER_ERROR,"背包没有该物品");}
        //根据物品id查询物品信息
        GoodsEntity goodsEntity = goodsMapper.selectById(backPackEntity.getGoodId());
        if(goodsEntity.getGoodType()==1){
            //判断出售的信息
            int i = backpackMapper.deleteById(backPackEntity.getBackpackId());
            i += equipmentMapper.deleteById(backPackEntity.getBackpackId());
            if(i!=2){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售时出现一点意外");}
        }else {
            //判断背包的数量
            int i = backPackEntity.getNumber() - sellGoodsInfo.getNumber();
            if(i<0){throw new ApplicationException(CodeType.SERVICE_ERROR,"背包没有这么多数量");}
            else if(i==0){
                //进行出售
                int i1 = backpackMapper.deleteById(sellGoodsInfo.getBackpackId());
                if(i1<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售失败");}
            }else{
                //进行出售
                backPackEntity.setNumber(i);
                int i1 = backpackMapper.updateById(backPackEntity);
                if(i1<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"出售失败");}
            }
        }
        IncreaseUserInfoBO userInfoBO = new IncreaseUserInfoBO();
        userInfoBO.setUserId(sellGoodsInfo.getUserId());
        userInfoBO.setUserInfoGold(goodsEntity.getGoodType()==1?50:sellGoodsInfo.getNumber()*50);
        //增加用户信息
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

    @Override
    public List<PsBottleBo> getPsBottle(Long userId) {
        return backpackMapper.getPsBottle(userId);
    }

    @Override
    public void updateNumberByBackpackId(Long userId, Long backpackId) {
        //根据id查询背包信息
        BackPackEntity backPackEntity = backpackMapper.selectById(backpackId);
        if(backPackEntity==null|| !backPackEntity.getUserId().equals(userId)){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"数量不足");
        }
        int i = 0;
        if(backPackEntity.getNumber()>1){
            //如果数量>1
            backPackEntity.setNumber(backPackEntity.getNumber()-1);
            //修改
            i = backpackMapper.updateById(backPackEntity);
            if(i<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        }else {
            //删除
            i = backpackMapper.deleteById(backpackId);
        }
        if(i<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        int add = 0;
        if(backpackId==707431394650138541L){
            add = 100;
        }else  if(backpackId==707441015150160242L){
            add = 200;
        }else  if(backpackId==722958015550165460L){
            add = 50;
        }
        //进行修改
        backpackMapper.updateUserStrength(add,userId);
    }
}
