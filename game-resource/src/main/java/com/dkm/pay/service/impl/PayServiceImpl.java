package com.dkm.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.order.entity.Order;
import com.dkm.order.service.IOrderService;
import com.dkm.pay.dao.PayMapper;
import com.dkm.pay.entity.Pay;
import com.dkm.pay.entity.vo.PayInfoVo;
import com.dkm.pay.entity.vo.PayVo;
import com.dkm.pay.service.IPayService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.JavaBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements IPayService {

   @Autowired
   private IOrderService orderService;

   @Override
   public List<PayInfoVo> listAllPayInfo(Long userId) {

      //先查询订单的所有订单记录
      List<Order> orders = orderService.queryOrderInfo(userId);

      if (null == orders || orders.size() == 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "当前无订单");
      }

      List<String> orderNoList = new ArrayList<>();

      for (Order order : orders) {
         orderNoList.add(order.getOrderNo());
      }

      //拿到所有支付记录
      List<Pay> pays = baseMapper.queryPayList(orderNoList);

      Map<String, Order> map = orders.stream().
            collect(Collectors.toMap(Order::getOrderNo, order -> order));

      return pays.stream().map(pay -> {

         PayInfoVo vo = new PayInfoVo();
         BeanUtils.copyProperties(pay,vo);
         //装配VO
         vo.setOrderStatus(map.get(pay.getOrderNo()).getOrderStatus());
         vo.setOrderType(map.get(pay.getOrderNo()).getOrderType());
         vo.setUserId(map.get(pay.getOrderNo()).getUserId());

         if (pay.getPayTime() != null) {
            vo.setPayTime(DateUtils.formatDateTime(pay.getPayTime()));
         }

         return vo;
      }).collect(Collectors.toList());
   }

   @Override
   public void insertPay(Pay pay) {
      int insert = baseMapper.insert(pay);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "增加失败");
      }
   }

   @Override
   public void updatePayInfo(PayVo payVo) {

      Pay pay = new Pay();
      LambdaQueryWrapper<Pay> wrapper = new LambdaQueryWrapper<Pay>()
            .eq(Pay::getOrderNo,payVo.getOrderNo());

      pay.setPayResult(payVo.getPayResult());
      pay.setPayTime(payVo.getPayTime());
      pay.setPayType(payVo.getPayType());
      pay.setPayNo(payVo.getPayNo());
      //修改支付记录
      int update = baseMapper.update(pay, wrapper);

      if (update <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "修改失败");
      }

   }
}
