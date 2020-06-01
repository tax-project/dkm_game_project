package com.dkm.gift.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.entity.dto.UserInfoDto;
import com.dkm.gift.entity.vo.SendGiftVo;
import com.dkm.pets.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @description: liwu
 * @author: zhd
 * @create: 2020-05-27 09:28
 **/
@Repository
public interface GiftDao extends BaseMapper<GiftEntity> {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_id,user_Info_gold,user_info_diamonds from tb_user_info WHERE user_id  = #{userId}")
    UserInfoDto getUserInfo(@Param("userId") Long userId);

    /**
     * 更新送礼人金币、钻石
     * @param sendGiftVo
     * @return
     */
    @Update("UPDATE tb_user_info SET user_info_gold = user_info_gold-#{userInfo.gold},user_info_diamonds=user_info_diamonds-#{userInfo.diamond} WHERE user_id =#{userInfo.sendId}")
    Integer updateUserInfo(@Param("userInfo")SendGiftVo sendGiftVo);

    /**
     * 更新魅力
     * @param sendGiftVo
     * @return
     */
    @Update("UPDATE tb_user_info SET user_info_charm = user_info_charm+#{userInfo.charm} WHERE user_id =#{userInfo.receiveId}")
    Integer updateUserCharm(@Param("userInfo")SendGiftVo sendGiftVo);
}
