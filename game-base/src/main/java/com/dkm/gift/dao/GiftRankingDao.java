package com.dkm.gift.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dkm.gift.entity.GiftRankingEntity;
import com.dkm.gift.entity.dto.GiftRankingDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 送礼排行
 * @author: zhd
 * @create: 2020-06-18 14:37
 **/
@Repository
public interface GiftRankingDao extends IService<GiftRankingEntity> {

    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT * FROM tb_gift_ranking ORDER BY send desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id")
    List<GiftRankingDto> getGiftRankingSend();
    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT * FROM tb_gift_ranking ORDER BY accept desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id")
    List<GiftRankingDto> getGiftRankingAccept();
}
