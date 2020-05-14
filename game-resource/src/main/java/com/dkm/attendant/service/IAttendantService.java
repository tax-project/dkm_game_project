package com.dkm.attendant.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:06
 */
public interface IAttendantService {
    /**
     * 获取默认的三个跟班
     * @return
     */
   List<AttenDant> queryThreeAtt();

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold();
}
