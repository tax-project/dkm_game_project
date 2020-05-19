package com.dkm.produce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.good.entity.Goods;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.entity.vo.UserProduceVo;
import com.dkm.plunder.service.IUserProduceService;
import com.dkm.produce.dao.ProduceMapper;
import com.dkm.produce.entity.Produce;
import com.dkm.produce.service.IProduceService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 21:26
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ProduceServiceImpl extends ServiceImpl<ProduceMapper, Produce> implements IProduceService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private LocalUser localUser;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IUserProduceService userProduceService;

    @Override
    public Map<String,Object> insertProduce(Long attendantId) {

        UserLoginQuery user = localUser.getUser();
        Produce produce = new Produce();
        long produceId = idGenerator.getNumberId();

        produce.setId(produceId);
        produce.setAttendantId(attendantId);


        Goods goods = goodsService.queryRandomGoods();

        produce.setGoodId(goods.getId());

        int insert = baseMapper.insert(produce);

        if (insert <= 0) {
            log.info("添加产出有误");
            throw new ApplicationException(CodeType.SERVICE_ERROR);
        }


        UserProduceVo vo = new UserProduceVo();
        vo.setUserId(user.getId());
        vo.setProduceId(produceId);

        userProduceService.insertProduce(vo);

        //返回随机生成的物品给前端

        Map<String, Object> map = new HashMap<>();

        Integer number = 0;

        //算出随机生成的数量
        if ("金币".equals(goods.getName())) {
            number = 2000;
        } else {
            //加一个
            number = 1;
        }

        map.put("goods", goods);
        map.put("number",number);

        return map;

    }
}
