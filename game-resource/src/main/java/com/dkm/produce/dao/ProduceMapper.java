package com.dkm.produce.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.produce.entity.Produce;
import com.dkm.produce.entity.vo.AttendantGoods;
import com.dkm.produce.entity.vo.AttendantPutVo;
import com.dkm.produce.entity.vo.UserAttendantGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 21:25
 */
@Component
public interface ProduceMapper extends IBaseMapper<Produce> {
    /**
     * 根据用户id查询跟班和跟班产生的物品
     */
    List<AttendantGoods> queryJoinOutPutGoods(Long userId);

    List<AttendantPutVo> queryOutput(Long userId);

}
