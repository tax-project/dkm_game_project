package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.vo.IdVo;
import com.dkm.entity.vo.ListVo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.good.entity.vo.GoodQueryVo;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.dao.OpponentMapper;
import com.dkm.plunder.entity.Opponent;
import com.dkm.plunder.entity.vo.OpponentResultVo;
import com.dkm.plunder.entity.vo.OpponentVo;
import com.dkm.plunder.service.IOpponentService;
import com.dkm.utils.TransformationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/22 14:49
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpponentServiceImpl extends ServiceImpl<OpponentMapper, Opponent> implements IOpponentService {

    @Autowired
    private LocalUser localUser;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private IGoodsService goodsService;


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



    @Override
    public Map<String, Object> getOpponentInfo() {

        UserLoginQuery user = localUser.getUser();

        LambdaQueryWrapper<Opponent> wrapper = new LambdaQueryWrapper<Opponent>()
              .eq(Opponent::getUserId, user.getId())
              .last("ORDER BY RAND() limit 20");

        List<Opponent> opponents = baseMapper.selectList(wrapper);

        if (null == opponents || opponents.size() == 0) {
            log.info("查询对手表为空");
            return null;
        }

        ListVo listVo = new ListVo();
        List<IdVo> list = new ArrayList<>();

        List<Long> idList = new ArrayList<>();

        for (Opponent opponent : opponents) {
            IdVo idVo = new IdVo();
            idVo.setUserId(opponent.getOpponentId());
            list.add(idVo);

            idList.add(opponent.getOpponentId());
        }

        listVo.setList(list);

        Result<List<com.dkm.entity.vo.OpponentVo>> listResult = userFeignClient.listOpponent(listVo);

        if (listResult.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "对手feign有误");
        }

        /**
         * 对手用户信息
         */
        List<com.dkm.entity.vo.OpponentVo> userInfo = listResult.getData();

        //根据用户id查询物品
        List<GoodQueryVo> voList = goodsService.queryGoodsList(idList);

        Map<Long, List<GoodQueryVo>> map = userInfo.stream().
              collect(Collectors.toMap(com.dkm.entity.vo.OpponentVo::getUserId, goodQueryVo
              -> new ArrayList<>()));

        for (GoodQueryVo vo : voList) {
            List<GoodQueryVo> arrayList = map.get(vo.getUserId());
            arrayList.add(vo);
        }

        List<OpponentResultVo> result = userInfo.stream().map(opponentInfo -> {
            OpponentResultVo vo = new OpponentResultVo();
            BeanUtils.copyProperties(opponentInfo, vo);
            vo.setGoodList(map.get(opponentInfo.getUserId()));

            return vo;
        }).collect(Collectors.toList());

        Map<String,Object> resultMap = new HashMap<>(3);

        resultMap.put("infoList",result);

        //查询用户信息
        Result<UserInfoQueryBo> queryUser = userFeignClient.queryUser(user.getId());

        if (queryUser.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "feign有误");
        }

        UserInfoQueryBo userData = queryUser.getData();

        resultMap.put("currentStrength",userData.getUserInfoStrength());
        resultMap.put("allStrength",userData.getUserInfoAllStrength());

        return resultMap;
    }
}
