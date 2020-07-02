package com.dkm.blackHouse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.blackHouse.dao.TbBlackHouseMapper;
import com.dkm.blackHouse.domain.TbBlackHouse;
import com.dkm.blackHouse.domain.vo.TbBlackHouseVo;
import com.dkm.blackHouse.service.TbBlackHouseService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.land.entity.Land;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 黑屋表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@Service
public class TbBlackHouseServiceImpl implements TbBlackHouseService {
    @Resource
    TbBlackHouseMapper tbBlackHouseMapper;

    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private LocalUser localUser;

    @Autowired
    TbBlackHouseService tbBlackHouseService;
    @Override
    public int selectIsBlack(Long fromId) {
        return tbBlackHouseMapper.selectIsBlack(fromId);
    }

    @Override
    public TbBlackHouseVo selectIsBlackT(TbBlackHouse tbBlackHouse) {
        return tbBlackHouseMapper.selectIsBlackTwo(tbBlackHouse);
    }

    @Override
    public void insertLand(TbBlackHouse tbBlackHouses) {
        Date date=new Date();
        Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 15);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        tbBlackHouses.setTime(date);
        tbBlackHouses.setTimeone(sdf.format(now.getTime()));
        tbBlackHouses.setIsBlack(0);
        tbBlackHouses.setFromId(localUser.getUser().getId());
        tbBlackHouses.setBlackId(idGenerator.getNumberId());
        int rows = tbBlackHouseMapper.insert(tbBlackHouses);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "批量增加失败");
        }
    }

    @Override
    public void updateIsBlack(TbBlackHouse tbBlackHouse) {
        tbBlackHouse.setFromId(localUser.getUser().getId());
        QueryWrapper<TbBlackHouse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("from_id",tbBlackHouse.getFromId());
        queryWrapper.eq("to_id",tbBlackHouse.getToId());
        tbBlackHouse.setIsBlack(1);
        int rows=tbBlackHouseMapper.update(tbBlackHouse,queryWrapper);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "释放黑屋失败");
        }
    }

    @Override
    public TbBlackHouseVo selectIsBlackTwo(Long userId) {
        //首先根据传过来的登录用户的id查询出被关人的id
        List<TbBlackHouse> selectById=tbBlackHouseService.selectById(userId);
        if(ObjectUtils.isEmpty(selectById)){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND, "该用户的黑屋没人被关");
        }
        TbBlackHouse tbBlackHouse=new TbBlackHouse();

        if(selectById.size()>1){
            throw new ApplicationException(CodeType.RESOURCES_EXISTING, "该用户黑屋关了两个");
        }
        for (TbBlackHouse blackHouse : selectById) {
            tbBlackHouse.setToId(blackHouse.getToId());
            tbBlackHouse.setFromId(blackHouse.getFromId());
        }
        return tbBlackHouseMapper.selectIsBlackTwo(tbBlackHouse);
    }

    @Override
    public TbBlackHouseVo selectIsBlackThree(TbBlackHouse tbBlackHouse) {
        return tbBlackHouseMapper.selectIsBlackThree(tbBlackHouse);
    }

    @Override
    public List<TbBlackHouse> selectById() {
        QueryWrapper<TbBlackHouse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("from_id",localUser.getUser().getId());
        queryWrapper.eq("is_black",0);
        return tbBlackHouseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TbBlackHouse> selectToId() {
        QueryWrapper<TbBlackHouse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("to_id",localUser.getUser().getId());
        queryWrapper.eq("is_black",0);
        return tbBlackHouseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TbBlackHouse> selectById(Long userId) {
        QueryWrapper<TbBlackHouse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("from_id",userId);
        queryWrapper.eq("is_black",0);
        return tbBlackHouseMapper.selectList(queryWrapper);
    }


}
