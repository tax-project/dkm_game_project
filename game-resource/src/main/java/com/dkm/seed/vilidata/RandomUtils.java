package com.dkm.seed.vilidata;


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
     * @param userInfoGrade  用户等级
     * @return
     */
    public boolean isProduceGoldRed(Integer userInfoGrade){
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
     * @return
     */
    public double NumberRedPacketsDropped(){
         /*if(status==1){
             //double v = money / 60;
             return 0.01;
         }*/
        int random = new Random().nextInt(100) + 1;
        if(random==1){
            return 0.1;
        }
        return 0.01;
    }


    /**
     * 掉落金币概率 是否有金币掉落
     * @param seedGrade  种子等级
     * @return
     */
    public boolean probabilityDroppingGold(Integer seedGrade){
        //金币掉落概率
        int pow = (int) (Math.pow(seedGrade, -1 / 4.0) * 100);

        //生产1-100的随机数
        int random = new Random().nextInt(100) + 1;
        if(random<=pow){
            return true;
        }
        return false;
    }



    /**
     * 金币掉落的数量
     *
     */
    public Integer NumberCoinsDropped(Integer gold,Long time){
        double start = gold / time * 2 * 0.5;
        int start1 = (int) start;

        double end=gold / time * 2 * 0.7;
        int end1 = (int) end;
        //金币
        int random = new Random().nextInt(start1) + end1;

        return random;
    }
}
