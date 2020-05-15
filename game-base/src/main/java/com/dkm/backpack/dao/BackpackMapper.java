package com.dkm.backpack.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.backpack.entity.Backpack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:16
 * @Version: 1.0V
 */
@Mapper
@Component
public interface BackpackMapper extends IBaseMapper<Backpack> {
}
