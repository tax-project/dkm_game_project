package com.dkm.goods.services.impl;

import com.dkm.goods.entities.bo.GoodsEntity;
import com.dkm.goods.entities.vo.GoodsVo;
import com.dkm.goods.mappers.GoodsMapper;
import com.dkm.goods.services.IGoodService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.bean.ResultVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        rollbackFor = {Exception.class}
)

public class GoodServiceImpl implements IGoodService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 得到所有的物品
     * @return
     */
    @Override
    @NotNull
    public List<GoodsVo> getAllGoods() {
        val result = new ArrayList<GoodsVo>();
        //查尊所有物品
        val var14 = this.goodsMapper.selectAllGoods();
        for (GoodsEntity it : var14) {
            //将查询的物品加进集合中进行返回
            result.add(new GoodsVo(String.valueOf(it.getId()), it.getName(), it.getUrl(), it.getGoodType(), it.getGoodContent(), it.getGoodMoney(), it.getTabUrl()));
        }
        return result;
    }

    /**
     * 添加物品
     * @param goods
     * @return
     */
    @Override
    @NotNull
    public ResultVo addGoodsItem(@NotNull GoodsVo goods) {
        long id = idGenerator.getNumberId();
        //添加物品
        goodsMapper.insert(new GoodsEntity(id, goods.getName(), goods.getUrl(), goods.getGoodType(), goods.getGoodContent(), goods.getGoodMoney(), goods.getTabUrl()));
        return new ResultVo(true, id);
    }

    /**
     * 修改物品
     * @param id
     * @param goods
     * @return
     */
    @Override
    @NotNull
    public ResultVo updateItemById(long id, @NotNull GoodsVo goods) {

        //根据id查询物品
        GoodsEntity var5 = goodsMapper.selectById(id);
        if (var5 != null) {
            //根据前端传来的参数进行修改
            //如不修改  则传null
            if (goods.getName() != null) {
                //该参数不为空  则添加进GoodsEntity进行修改
                var5.setName(goods.getName());
            }

            if (goods.getUrl() != null) {
                //该参数不为空  则添加进GoodsEntity进行修改
                var5.setUrl(goods.getUrl());
            }

            if (goods.getGoodType() >= 0) {
                //该参数类型大于等于0 则添加进GoodsEntity进行修改
                var5.setGoodType(goods.getGoodType());
            }

            if (goods.getGoodContent() != null) {
                //该参数不为空  则添加进GoodsEntity进行修改
                var5.setGoodContent(goods.getGoodContent());
            }

            if (goods.getGoodMoney() >= 0) {
                //该参数金额大于等于0 则添加进GoodsEntity进行修改
                var5.setGoodMoney(goods.getGoodMoney());
            }

            if (goods.getTabUrl() != null) {
                //该参数不为空  则添加进GoodsEntity进行修改
                var5.setTabUrl(goods.getTabUrl());
            }
            //根据id修改
            goodsMapper.updateById(var5);
            return new ResultVo(true, var5.getId());
        } else {
            return new ResultVo(false, "未找到此ID的数据");
        }
    }

    /**
     * 删除物品
     * @param id
     * @return
     */
    @Override
    @NotNull
    public ResultVo deleteItemById(@NotNull String id) {

        GoodsEntity var3 = goodsMapper.selectById(id);
        if (var3 != null) {
            //根据id删除物品
            goodsMapper.deleteById(var3.getId());
            return new ResultVo(true, var3.getId());
        } else {
            return new ResultVo(false, "未找到此ID的数据");
        }
    }
}
