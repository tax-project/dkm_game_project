package com.dkm.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.seed.dao.DropStatusMapper;
import com.dkm.seed.entity.DropStatus;
import com.dkm.seed.service.IDropStatusService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/7/31
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DropStatusServiceImpl extends ServiceImpl<DropStatusMapper, DropStatus> implements IDropStatusService {

   @Autowired
   private IdGenerator idGenerator;

   @Override
   public void dropStatusUpdate(DropStatus dropStatus) {


      if (dropStatus.getId() == null) {
         //增加
         dropStatus.setId(idGenerator.getNumberId());
         int insert = baseMapper.insert(dropStatus);

         if (insert <= 0) {
            log.info("增加失败");
         }
      }

      //修改
      int updateById = baseMapper.updateById(dropStatus);

      if (updateById <= 0) {
         log.info("修改失败");
      }
   }

   @Override
   public DropStatus queryDropStatus(Long userId) {
      LambdaQueryWrapper<DropStatus> wrapper = new LambdaQueryWrapper<DropStatus>()
            .eq(DropStatus::getUserId, userId);
      return baseMapper.selectOne(wrapper);
   }

   @Override
   public void deleteDrop(Long userId) {
      LambdaQueryWrapper<DropStatus> wrapper = new LambdaQueryWrapper<DropStatus>()
            .eq(DropStatus::getUserId, userId);

      int delete = baseMapper.delete(wrapper);
      if (delete <= 0) {
         log.info("删除失败");
      }
   }
}
