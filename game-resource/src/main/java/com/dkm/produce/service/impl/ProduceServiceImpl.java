package com.dkm.produce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.dao.AttendantUserMapper;
import com.dkm.attendant.entity.AttenDant;
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

    @Autowired
    private AttendantMapper attendantMapper;

    /**
     *  物品金币
     */
    private final String GOOD_NAME = "金币";

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

        produce.setAttUserId(attUserId);

        int number;
        //算出随机生成的数量
        if (GOOD_NAME.equals(goods.getName())) {

            //随机生成1000-2000的金币
            number = (int) (Math.random() * (2000 - 1000 + 1)) + 1000;

        } else {
            //随机生成1-3的随机数
            number = (int) (Math.random() * (3 - 1 + 1)) + 1;
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

        Map<String, Object> map = new HashMap<>(2);

        map.put("goods", goods);
        map.put("number",number);

        return map;

    }

    @Override
    public Map<String,Object> queryImgFood(Long userId) {
        Map<String,Object> map=new HashMap<>(16);

        //跟班
        List<AttendantImgVo> attendantImg= new ArrayList<>();

        List<AttendantUser> attendantUsers = attendantUserService.queryListByUserId(userId);


        //查询出所有用户跟班
        List<AttendantImgVo> attendantGoods = produceMapper.queryImgFood(userId);

        for (int i = 0; i < attendantUsers.size(); i++) {
            //不等于0说明是系统跟班 随机查询出一个跟班  放入集合
            if(attendantUsers.get(i).getAttendantId()!=0){

                AttendantImgVo attendantImgVo=new AttendantImgVo();
                //随机查询一个跟班
                AttenDant attenDant = attendantMapper.queryAttendant();

                //放入对象
                attendantImgVo.setWeChatHeadImgUrl(attenDant.getAtImg());

                attendantImg.add(attendantImgVo);
            }


            //等于0就是用户跟班
            if(attendantUsers.get(i).getAttendantId()==0){

                AttendantImgVo attendantImgVo=new AttendantImgVo();
                attendantImgVo.setWeChatHeadImgUrl(attendantGoods.get(i).getWeChatHeadImgUrl());
                attendantImg.add(attendantImgVo);
            }

        }


        //统计出所有物品的数量
        List<AttendantPutVo> attendantPutVos = produceMapper.queryOutput(userId);


        map.put("attendantImg",attendantImg);

        if(attendantPutVos.size()==0){
            //1代表没有
            map.put("status",1);
            map.put("attendantPutVos","暂无产出，快去抓跟班吧");
        }else{
            //0代表有产出
            map.put("status",0);
            map.put("attendantPutVos",attendantPutVos);
        }

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
