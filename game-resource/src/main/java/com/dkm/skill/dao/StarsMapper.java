package com.dkm.skill.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.skill.entity.Stars;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Component
public interface StarsMapper extends IBaseMapper<Stars> {

    /**
     * 根据用户id查询自己当前拥有的金星星数量
     * @param userId
     * @return
     */
    Stars queryCurrentConsumeByUserId(Long userId);
}
