package com.dkm.turntable.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.turntable.entity.Turntable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HuangJie
 * @Date: 2020/5/10 11:10
 * @Version: 1.0V
 */
@Component
@Mapper
public interface TurntableMapper extends IBaseMapper<Turntable> {



}
