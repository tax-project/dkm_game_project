package com.dkm.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.goods.mapper.bo.GoodsEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

    @Select("select * from tb_goods")
    List<GoodsEntity> selectAllGoods();
}
