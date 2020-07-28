package com.dkm.mallEvents.service.impl;

import com.dkm.mallEvents.dao.CancerDao;
import com.dkm.mallEvents.entities.vo.GoodsInfoVo;
import com.dkm.mallEvents.entities.vo.SingleTopUpVo;
import com.dkm.mallEvents.service.ICancerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CancerServiceImpl implements ICancerService {



    @Resource
    private CancerDao cancerDao;

    @Override
    public List<SingleTopUpVo> getSingleTopUp(Long userId) {
        List<SingleTopUpVo> singleTopUpVos = cancerDao.selectSingle();
        for (SingleTopUpVo singleTopUpVo : singleTopUpVos) {
            singleTopUpVo.setStatus(cancerDao.findCheckedById(userId, singleTopUpVo.getId()) != null);
        }
        return singleTopUpVos;
    }

    @Override
    public Boolean getSingleTopUpInfoCheck(Long userId, Integer id) {
        List<GoodsInfoVo> goods =cancerDao.selectById(id);
        return null;
    }
}
