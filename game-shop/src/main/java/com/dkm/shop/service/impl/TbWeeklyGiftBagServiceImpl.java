package com.dkm.shop.service.impl;


import com.dkm.shop.dao.TbWeeklyGiftBagMapper;
import com.dkm.shop.domain.TbWeeklyGiftBag;
import com.dkm.shop.service.ITbWeeklyGiftBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-10
 */
@Service
public class TbWeeklyGiftBagServiceImpl implements ITbWeeklyGiftBagService {
    @Autowired
    TbWeeklyGiftBagMapper tbWeeklyGiftBagMapper;
    @Override
    public List<TbWeeklyGiftBag> selectAll() {
        return tbWeeklyGiftBagMapper.selectList(null);
    }
}
