package com.dkm.union.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.union.entity.UnionEntity;
import com.dkm.union.entity.vo.UnionUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-18 10:54
 **/
@Repository
public interface UnionMapper extends BaseMapper<UnionEntity> {


    List<UnionUserVo> getUnionUser(@Param("userIds") List<Long> userIds);


}
