package com.dkm.plunder.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.plunder.entity.Opponent;
import com.dkm.plunder.entity.vo.OpponentVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/22 14:22
 */
@Component
public interface OpponentMapper extends IBaseMapper<Opponent> {
    /**
     * 查询对手信息
     * @return
     */
    List<OpponentVo> queryOpponent(Long userId);
}
