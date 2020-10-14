package com.dkm.shopCart.services.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shopCart.dao.ShopCartMapper;
import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.entities.vo.ShopCartItemVo;
import com.dkm.shopCart.services.IShopCartService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopCartServiceImpl implements IShopCartService {

    @Resource
    private ShopCartMapper shopCartMapper;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public List<ShopCartItemInfo> getShopCartInfo(Long userId) {
        return shopCartMapper.selectShopCartListById(userId);
    }

    @Override
    public Boolean addItems(Long userId, List<ShopCartItemVo> items) {
        if (items.isEmpty()){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"LIST IS EMPTY");
        }
        for (ShopCartItemVo item : items) {
            item.setItemId(idGenerator.getNumberId());
        }
        //批量添加
        return shopCartMapper.addAll(userId, items) > 0;
    }

    @Override
    public Boolean updateItems(List<ShopCartItemVo> items) {
        return shopCartMapper.updateAll(items) == items.size();
    }

    @Override
    public Boolean delete(Long id) {
        return shopCartMapper.deleteById(id);
    }
}
