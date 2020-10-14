package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.plunder.dao.UserProduceMapper;
import com.dkm.plunder.entity.UserProduce;
import com.dkm.plunder.entity.vo.UserProduceVo;
import com.dkm.plunder.service.IUserProduceService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserProduceServiceImpl extends ServiceImpl<UserProduceMapper, UserProduce> implements IUserProduceService {

   @Autowired
   private IdGenerator idGenerator;

   @Override
   public void insertProduce(UserProduce userProduce) {
      userProduce.setId(idGenerator.getNumberId());
      //增加产出
      int insert = baseMapper.insert(userProduce);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }

   @Override
   public void allInsertUserProduce(List<UserProduce> list) {
      //添加用户产出信息
      Integer integer = baseMapper.allInsertUserProduce(list);

      if (integer <= 0) {
         log.info("批量增加用户产出失败");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }
}
