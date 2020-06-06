package com.dkm.produce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.good.entity.Goods;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.plunder.entity.UserProduce;
import com.dkm.plunder.service.IUserProduceService;
import com.dkm.produce.dao.ProduceMapper;
import com.dkm.produce.entity.Produce;
import com.dkm.produce.entity.vo.AttendantGoods;
import com.dkm.produce.entity.vo.AttendantPutVo;
import com.dkm.produce.entity.vo.UserAttendantGoods;
import com.dkm.produce.service.IProduceService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private ProduceMapper produceMapper;

    @Override
    public Map<String,Object> insertProduce(Long attendantId) {
        UserLoginQuery user = localUser.getUser();
        Goods goods = goodsService.queryRandomGoods();


        Produce produce = new Produce();
        Long produceId = idGenerator.getNumberId();


        produce.setId(produceId);
        produce.setAttendantId(attendantId);

        Integer number = 0;
        //算出随机生成的数量
        if ("金币".equals(goods.getName())) {
            Random rand = new Random();
            int randNum = rand.nextInt(5000)+2000;
            number = randNum;
        } else {
            Random r = new Random();
            int randNum =r.nextInt(3)+1;
            //加一个
            number = randNum;
        }
         produce.setNumber(number);

        produce.setGoodId(goods.getId());

        //默认0
        produce.setStatus(0);

        int insert = baseMapper.insert(produce);
        if (insert <= 0) {
            log.info("添加产出有误");
            throw new ApplicationException(CodeType.SERVICE_ERROR);
        }
        UserProduce userProduce = new UserProduce();
        userProduce.setUserId(user.getId());
        userProduce.setProduceId(produceId);

        userProduceService.insertProduce(userProduce);

        //返回随机生成的物品给前端

        Map<String, Object> map = new HashMap<>();

        map.put("goods", goods);
        map.put("number",number);

        return map;

    }

    @Override
    public List<AttendantGoods> queryJoinOutPutGoods(Long userId) {
        return produceMapper.queryJoinOutPutGoods(userId);
    }

    @Override
    public  List<AttendantPutVo> queryOutput(Long userId) {
        //查询所有要返回的跟班数据
        return produceMapper.queryOutput(userId);

    }

    @Override
    public int deleteOutGoodNumber(Long id) {
        return produceMapper.deleteById(id);
    }


}
