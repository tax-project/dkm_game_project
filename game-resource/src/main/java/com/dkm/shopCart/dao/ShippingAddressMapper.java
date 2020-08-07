package com.dkm.shopCart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.shopCart.entities.vo.ShippingAddressInfoVo;
import com.dkm.shopCart.entities.vo.ShippingAddressVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShippingAddressMapper extends BaseMapper<ShippingAddressInfoVo> {
    Integer updateByIdAndUserId(@Param("item") ShippingAddressVo item,
                                @Param("userId") Long userId, @Param("itemId") Long itemId);
}
