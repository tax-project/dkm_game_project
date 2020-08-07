package com.dkm.shopCart.dao;

import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.entities.vo.ShopCartItemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCartMapper {

    List<ShopCartItemInfo> selectShopCartListById(Long userId);

    Integer addAll(@Param("userId") Long userId,@Param("list") List<ShopCartItemVo> list);

    int updateAll(List<ShopCartItemVo> items);

    Boolean deleteById(Long id);
}
