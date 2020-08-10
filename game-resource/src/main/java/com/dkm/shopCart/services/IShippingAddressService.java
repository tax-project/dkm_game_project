package com.dkm.shopCart.services;

import com.dkm.shopCart.entities.vo.ShippingAddressInfoVo;
import com.dkm.shopCart.entities.vo.ShippingAddressVo;

import java.util.List;

public interface IShippingAddressService {
    List<ShippingAddressInfoVo> getAllAddress(Long userId);

    Boolean addAShippingAddress(Long userId, ShippingAddressVo item);

    Boolean updateAShippingAddressById(Long userId, Long itemId, ShippingAddressVo shippingAddressVo);

    Boolean delete(Long userId, Long itemId);

    Boolean setDefault(Long userId, Long itemId);
}
