package com.dkm.mallEvents.service.impl;

import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.mallEvents.dao.CancerDao;
import com.dkm.mallEvents.entities.vo.GoodsInfoVo;
import com.dkm.mallEvents.entities.vo.SingleHistoryUserVo;
import com.dkm.mallEvents.entities.vo.SingleTopUpVo;
import com.dkm.mallEvents.service.ICancerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CancerServiceImpl implements ICancerService {

    @Resource
    private IBackpackService backpackService;

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

    /**
     *
     *  注意啦，这个没有判断是否充值完成的，因为暂时不存在充值模块
     *
     */
    @Override
    public Boolean getSingleTopUpInfoCheck(Long userId, Integer id) {

        List<GoodsInfoVo> goods = cancerDao.selectById(id);
        if (goods == null || goods.size() == 0) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "未找到此数据");
        }
        SingleHistoryUserVo historyUserVo = cancerDao.selectHistoryByUser(userId,id);
        if (historyUserVo != null){
            throw new ApplicationException(CodeType.RESOURCES_EXISTING,"你已经领取过啦.");
        }
        for (GoodsInfoVo good : goods) {
            AddGoodsInfo addGoodsInfo = new AddGoodsInfo();
            addGoodsInfo.setUserId(userId);
            addGoodsInfo.setGoodId(good.getGoodsId());
            addGoodsInfo.setNumber(good.getSize());
            backpackService.addBackpackGoods(addGoodsInfo);
        }
        cancerDao.addUser(userId, id);
        return true;
    }
}
