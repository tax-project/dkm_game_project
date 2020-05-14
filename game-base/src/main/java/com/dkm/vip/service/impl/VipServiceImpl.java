package com.dkm.vip.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.utils.IdGenerator;
import com.dkm.vip.dao.VipMapper;
import com.dkm.vip.entity.VipEntity;
import com.dkm.vip.service.VipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhd
 * @date 2020/5/8 13:39
 */
@Service
public class VipServiceImpl implements VipService {
    @Resource
    private VipMapper vipMapper;
    @Resource
    private IdGenerator idGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean openVip(Long userId,BigDecimal money) {
        VipEntity a = new VipEntity();
        a.setVipId(idGenerator.getNumberId());
        a.setVipMoney(money);
        a.setVipName("VIP");
        a.setUserId(userId);
        try {
            if(vipMapper.insert(a)==0||vipMapper.userAddVip(userId)==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return  false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw  new ApplicationException(CodeType.SERVICE_ERROR,"存储出错");
        }
    }
}
