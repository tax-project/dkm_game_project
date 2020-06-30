package com.dkm.apparel.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.apparel.entity.ApparelOrderEntity;
import com.dkm.apparel.entity.vo.ApparelOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-06-22 14:40
 **/
@Repository
public interface ApparelOrderMapper extends IBaseMapper<ApparelOrderEntity> {

    @Select("SELECT ao.*,ad.apparel_name,ad.apparel_url FROM (" +
            "SELECT*FROM tb_apparel_order WHERE user_id=#{userId}) ao LEFT JOIN tb_apparel_detail ad ON ao.apparel_detail_id=ad.apparel_id")
    List<ApparelOrderVo> getApparelOrders(@Param("userId")Long userId);

    @Update("UPDATE tb_user_info SET user_info_gold = user_info_gold-#{gold} WHERE user_id = #{userId}")
    Integer updateUserGold(@Param("gold")Integer gold,@Param("userId")Long userId);
}
