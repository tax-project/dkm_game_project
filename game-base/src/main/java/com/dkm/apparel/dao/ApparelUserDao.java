package com.dkm.apparel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.apparel.entity.ApparelUserEntity;
import com.dkm.apparel.entity.dto.ApparelDto;
import com.dkm.apparel.entity.vo.ApparelMarketDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: game_project
 * @description: 服饰关联
 * @author: zhd
 * @create: 2020-05-22 10:33
 **/
@Repository
public interface ApparelUserDao extends BaseMapper<ApparelUserEntity> {

    /**
     * 制作消费
     * @param gold
     * @return
     */
    @Update("update tb_user_info set user_info_gold = user_info_gold - #{gold},user_info_diamonds=user_info_diamonds-#{diamond} where user_id=#{userId} and user_info_diamonds>=#{diamond} and user_info_gold>=#{gold}")
    Integer updateUser(@Param("userId") Long userId,@Param("gold") Integer gold,@Param("diamond") Integer diamond);

    /**
     * 制作中的服饰
     * @param id
     * @return
     */
    @Select("SELECT ad.* FROM (SELECT * FROM `tb_apparel_user` WHERE apparel_user_id = #{id} ) au LEFT JOIN tb_apparel_detail ad on au.apparel_detail_id =  ad.apparel_id")
    ApparelDto getDoing(@Param("id") Long id);

    /**
     * 获取装备中的服饰
     * @param userId
     * @return
     */
    @Select("SELECT ad.*,au.apparel_user_id FROM (SELECT apparel_user_id,apparel_detail_id FROM tb_apparel_user WHERE user_id = #{userId} and is_equip = 1) au LEFT JOIN tb_apparel_detail ad on ad.apparel_id = au.apparel_detail_id")
    List<ApparelDto> getEquip(@Param("userId") Long userId);

    /**
     * 获取摆摊服饰
     * @param userId
     * @param type
     * @return
     */
    List<ApparelMarketDetailVo> getApparelMarket(@Param("userId")Long userId,@Param("type")Integer type);
}
