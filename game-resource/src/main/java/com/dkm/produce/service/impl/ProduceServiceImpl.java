package com.dkm.produce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.service.IAttendantUserService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.good.entity.Goods;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.entity.UserProduce;
import com.dkm.plunder.service.IUserProduceService;
import com.dkm.produce.dao.ProduceMapper;
import com.dkm.produce.entity.Produce;
import com.dkm.produce.entity.vo.AttendantImgVo;
import com.dkm.produce.entity.vo.AttendantPutVo;
import com.dkm.produce.entity.vo.ProduceSelectVo;
import com.dkm.produce.entity.vo.SysAttendantImgVo;
import com.dkm.produce.service.IProduceService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private IAttendantUserService attendantUserService;

    @Override
    public Map<String,Object> insertProduce(Long attendantId, Long attUserId) {
        UserLoginQuery user = localUser.getUser();

        //查询时间有没有过期
        LocalDateTime now = LocalDateTime.now();

        AttendantUser attUser = attendantUserService.queryAttUser(attUserId);

        if (attUser == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "attUserId传错了..大哥");
        }


        String endDate = attUser.getEndDate();
        LocalDateTime time = DateUtils.parseDateTime(endDate);

        long until = now.until(time, ChronoUnit.SECONDS);

        if (until <= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "产出时间已过期");
        }

        //随机返回物品
        Goods goods = goodsService.queryRandomGoods();


        Produce produce = new Produce();
        Long produceId = idGenerator.getNumberId();


        produce.setId(produceId);
        produce.setAttendantId(attendantId);

        Integer number = 0;
        //算出随机生成的数量
        if ("金币".equals(goods.getName())) {
            Random rand = new Random();
            int randNum = rand.nextInt(5000);
            if(randNum<2000){
                randNum = new Random().nextInt(5000);
                number = randNum;
            }

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
    public Map<String,Object> queryImgFood(Long userId) {
        Map<String,Object> map=new HashMap<>(16);

        //装系统跟班
        List<SysAttendantImgVo> sysAttendant=new ArrayList();

        //查询出用户跟班图片
        List<AttendantImgVo> attendantGoods = produceMapper.queryImgFood(userId);
        for (int i = 0; i < attendantGoods.size(); i++) {
            //如果跟班id不等于0说明不是系统跟班
            if(attendantGoods.get(i).getAttendantId()!=0){
                AttendantImgVo attendantImgVo=new AttendantImgVo();
                attendantImgVo.setAttendantId(attendantGoods.get(i).getAttendantId());
                attendantImgVo.setWeChatHeadImgUrl(attendantGoods.get(i).getWeChatHeadImgUrl());
                attendantGoods.add(attendantImgVo);
            }
        }

        map.put("attendantGoods",attendantGoods);

        return map;
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

   @Override
   public void deletePut(Long userId, Long aId) {
      //先查询id集合
      List<ProduceSelectVo> list = baseMapper.queryAllIdList(userId, aId);

      if (null == list || list.size() == 0) {
          return;
      }

      //删除产出表信息
      Integer integer = baseMapper.deleteProduce(list);

      if (integer <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "删除出错");
      }

      Integer integer1 = baseMapper.deleteProduceUser(list);


      if (integer1 <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "删除出错");
      }
   }


}
