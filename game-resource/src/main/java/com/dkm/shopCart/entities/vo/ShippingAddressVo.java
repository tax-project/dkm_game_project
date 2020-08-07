package com.dkm.shopCart.entities.vo;

import lombok.Data;

@Data
public class ShippingAddressVo {
    private String name;
    private String address;
    private String zip;
    private String phone;


    public ShippingAddressInfoVo cloneToEntityItem(long numberId, Long userId) {
        return new ShippingAddressInfoVo(numberId, userId, name, phone, address, zip);
    }
}
