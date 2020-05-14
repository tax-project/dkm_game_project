package com.dkm.turntable.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.turntable.entity.Turntable;
import com.dkm.turntable.entity.TurntableItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 9:29
 * @Version: 1.0V
 */
@Mapper
@Component
public interface TurntableItemMapper extends IBaseMapper<TurntableItem> {
    /**
     * 根据ID获得用户信息
     * @param turntable 6个物品的ID集合
     * @return 结果
     */
    List<TurntableItem> luckyDrawItems(Turntable turntable);
}
