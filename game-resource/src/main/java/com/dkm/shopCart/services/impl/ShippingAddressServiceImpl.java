package com.dkm.shopCart.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.shopCart.dao.ShippingAddressMapper;
import com.dkm.shopCart.entities.vo.ShippingAddressInfoVo;
import com.dkm.shopCart.entities.vo.ShippingAddressVo;
import com.dkm.shopCart.services.IShippingAddressService;
import com.dkm.utils.IdGenerator;
import feign.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShippingAddressServiceImpl implements IShippingAddressService {
    @Resource
    private ShippingAddressMapper shippingAddressMapper;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public List<ShippingAddressInfoVo> getAllAddress(Long userId) {
        return shippingAddressMapper.selectList(
                new QueryWrapper<ShippingAddressInfoVo>()
                        .lambda().eq(ShippingAddressInfoVo::getUserId, userId));
    }

    @Override
    public Boolean addAShippingAddress(Long userId, ShippingAddressVo item) {
        return shippingAddressMapper
                .insert(item.cloneToEntityItem(idGenerator.getNumberId(), userId))
                != 0;
    }

    @Override
    public Boolean updateAShippingAddressById(Long userId, Long itemId, ShippingAddressVo item) {
        return shippingAddressMapper.updateByIdAndUserId(item,userId,itemId)>0;
    }

    @Override
    public Boolean delete(Long userId, Long itemId) {
        return shippingAddressMapper.delete(new QueryWrapper<ShippingAddressInfoVo>().lambda().eq(ShippingAddressInfoVo::getUserId, userId)
                .eq(ShippingAddressInfoVo::getId, itemId)) != 0;
    }
}

