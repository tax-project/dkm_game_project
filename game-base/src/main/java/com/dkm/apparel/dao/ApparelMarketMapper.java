package com.dkm.apparel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.apparel.entity.ApparelMarketEntity;
import com.dkm.apparel.entity.dto.ApparelMarketDto;
import com.dkm.apparel.entity.vo.ApparelMarketDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-06-19 15:26
 **/
@Repository
public interface ApparelMarketMapper extends BaseMapper<ApparelMarketEntity> {

    @Select("SELECT am.*,ad.apparel_url,ad.apparel_gold,ad.apparel_sex,ad.apparel_name FROM (SELECT * FROM tb_apparel_market WHERE user_id = #{userId} ) am LEFT JOIN tb_apparel_detail ad on ad.apparel_id=am.apparel_detail_id ")
    List<ApparelMarketDetailVo> getPuttingApparel(@Param("userId")Long userId);

    @Select("SELECT ad.*,am.gold,am.apparel_market_id,am.user_id FROM (" +
            "SELECT*FROM tb_apparel_market WHERE user_id !=#{userId} AND maturity_time> NOW()) am LEFT JOIN tb_apparel_detail ad ON am.apparel_detail_id=ad.apparel_id WHERE ad.apparel_type=#{type}")
    List<ApparelMarketDto> getApparelMarketInfo(@Param("userId")Long userId,@Param("type")Integer type);
}
