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

    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url,u.user_sex,ui.user_info_grade as userGrade FROM (SELECT * FROM tb_gift_ranking where send>0 ORDER BY send desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id LEFT JOIN tb_user_info ui on ui.user_id=gr.user_id ")
    List<GiftRankingDto> getGiftRankingSend();
    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url,u.user_sex,ui.user_info_grade as userGrade FROM (SELECT * FROM tb_gift_ranking where accept>0 ORDER BY accept desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id LEFT JOIN tb_user_info ui on ui.user_id=gr.user_id ")
    List<GiftRankingDto> getGiftRankingAccept();

    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT * FROM tb_gift_ranking where accept_flower>0 ORDER BY accept_flower desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id")
    List<GiftRankingDto> getGiftRankingAcceptFlower();
    @Select("SELECT gr.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT * FROM tb_gift_ranking where send_flower>0 ORDER BY send_flower desc LIMIT 20) gr LEFT JOIN tb_user u on u.user_id = gr.user_id")
    List<GiftRankingDto> getGiftRankingSendFlower();

}
