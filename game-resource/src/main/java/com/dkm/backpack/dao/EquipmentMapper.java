package com.dkm.backpack.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.backpack.entity.EquipmentEntity;
import com.dkm.backpack.entity.vo.EquipmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 装备详情
 * @author: zhd
 * @create: 2020-07-17 20:56
 **/
@Mapper
public interface EquipmentMapper extends IBaseMapper<EquipmentEntity> {

     Integer insertList(@Param("list")List<EquipmentEntity> list);

     @Select("SELECT ue.*,g.name,g.url FROM (SELECT good_id FROM tb_user_backpack WHERE backpack_id = #{backpackId}) ub " +
             "LEFT JOIN tb_goods g on g.id=ub.good_id LEFT JOIN tb_user_equipment ue on ue.backpack_id=#{backpackId}")
     EquipmentVo getEquipmentInfo(@Param("backpackId")Long backpackId);
}
