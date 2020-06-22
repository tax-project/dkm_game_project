package com.dkm.mounts.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.mounts.entity.MountsDetailEntity;
import com.dkm.mounts.entity.dto.MountsDetailDto;
import com.dkm.mounts.entity.dto.UserInfoDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/11 17:25
 */
@Repository
public interface MountsMapper extends IBaseMapper<MountsDetailEntity> {

    /**
     * 获取用户座驾
     * @param userId
     * @return
     */
    @Select("SELECT i.*,m.equip,m.id FROM  (select mounts_id,id,equip from tb_mounts WHERE user_id = #{userId}) m LEFT join  tb_mounts_detail i  on i.mounts_id = m.mounts_id  ")
    List<MountsDetailDto> haveMounts(@Param("userId") Long userId);

    /**
     * 修改座驾装备状态
     * @param id
     * @return
     */
    @Update("update tb_mounts set equip = 1 where id = #{id}")
    Integer updateEquip(@Param("id") Long id);

    /**
     * 取消装备座驾
     * @param userId
     * @return
     */
    @Update("update tb_mounts set equip = 0 where user_id = #{userId} and equip=1")
    Integer cancelEquip(@Param("userId") Long userId);

    /**
     * 获取所有座驾
     * @param userId
     * @return
     */
    @Select("SELECT md.*,m.mounts_id as buy FROM (SELECT mounts_id FROM tb_mounts WHERE user_id = #{userId}) m RIGHT JOIN tb_mounts_detail md on md.mounts_id = m.mounts_id")
    List<MountsDetailDto> getAll(@Param("userId") Long userId);

    /**
     * 增加一辆车
     * @param id
     * @param mountId
     * @param userId
     * @param equip
     * @return
     */
    @Insert("insert into tb_mounts (id,mounts_id,user_id,equip) values(#{id},#{mountId},#{userId},#{equip})")
    Integer insetOne(@Param("id") Long id,@Param("mountId")Long mountId,@Param("userId")Long userId,@Param("equip")Integer equip);

    /**
     * 用户金币/钻石
     * @param userId
     * @return
     */
    @Select("SELECT user_info_gold,user_info_diamonds FROM tb_user_info WHERE user_id = #{userId}")
    UserInfoDto getUserInfo(@Param("userId") Long userId);

    /**
     * 更新钻石/金币
     * @param gold
     * @param diamond
     * @param userId
     * @return
     */
    @Update("update tb_user_info set user_info_gold = user_info_gold-#{gold} ,user_info_diamonds=user_info_diamonds-#{diamond} where user_id = #{userId}")
    Integer updateUser(@Param("gold") Integer gold,@Param("diamond") Integer diamond,@Param("userId") Long userId);


    /**
     * 用户座驾数量
     * @param userId
     * @return
     */
    @Select("SELECT count(*) FROM tb_mounts WHERE user_id = #{userId}")
    Integer getMountNumber(@Param("userId")Long userId);

    /**
     * 用户装备座驾图片
     * @param userId
     * @return
     */
    @Select("SELECT md.mounts_image FROM (SELECT mounts_id FROM tb_mounts WHERE user_id = #{userId} and equip = 1) m LEFT JOIN tb_mounts_detail md on md.mounts_id = m.mounts_id")
    String getMountImg(@Param("userId")Long userId);
}
