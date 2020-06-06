package com.dkm.seed.vilidata;

import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/6 17:02
 */
@Component
public class RandomUtils {

    /**
     * 掉落红包概率 是否有红包掉落
     * @param status
     * @param userInfoGrade  用户等级
     * @return
     */
    public boolean isProduceGoldRed(Integer status, Integer userInfoGrade){
        //红包掉落概率
        int pow = (int) (Math.pow(userInfoGrade, -1 / 2.0) * 100);

        //生产1-100的随机数
        int random = new Random().nextInt(100) + 1;
        if(random<=pow){
            return true;
        }
        return false;
    }


    /**
     * 红包掉落的数量
     */
    public double NumberRedPacketsDropped(){


        return 0;
    }


    /**
     * 掉落金币概率 是否有金币掉落
     * @param seedGarde  种子等级
     * @return
     */
    public boolean isProduceGoldRed(Integer seedGarde){
        //金币掉落概率
        int pow = (int) (Math.pow(seedGarde, -1 / 4.0) * 100);

        //生产1-100的随机数
        int random = new Random().nextInt(100) + 1;
        if(random<=pow){
            return true;
        }
        return false;
    }



//    /**
//     * 金币掉落的数量
//     */
//    public Integer
}
