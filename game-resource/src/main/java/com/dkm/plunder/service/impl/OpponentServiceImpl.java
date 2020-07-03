package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.plunder.dao.OpponentMapper;
import com.dkm.plunder.entity.Opponent;
import com.dkm.plunder.entity.vo.OpponentVo;
import com.dkm.plunder.service.IOpponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/22 14:49
 */
public class OpponentServiceImpl extends ServiceImpl<OpponentMapper, Opponent> implements IOpponentService {

    @Autowired
    private LocalUser localUser;


    @Override
    public Map<String, Object> queryOpponent() {

        Map<String,Object> map=new HashMap<>(16);

        //查询出匹配到过的对手的信息
        List<OpponentVo> opponentVos = baseMapper.queryOpponent(localUser.getUser().getId());

        //对抗值
        map.put("antagonismValue",30);
        map.put("opponentVos",opponentVos);

        return map;
    }

    @Override
    public int addOpponent(Opponent opponent) {
        return baseMapper.insert(opponent);
    }
}
