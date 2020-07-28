package com.dkm.attendant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.produce.entity.bo.ProduceBO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 17:53
 */
@Component
public interface AttendantUserMapper extends BaseMapper<AttendantUser> {

   /**
    *  修改产出次数
    * @param list id集合
    * @return
    */
   Integer updateStatus(List<ProduceBO> list);
}
