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

import javax.annotation.Resource;
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
    @Override
    public int selectIsBlack(Long fromId) {
        return tbBlackHouseMapper.selectIsBlack(fromId);
    }

    @Override
    public void insertLand(List<TbBlackHouse> tbBlackHouses) {
        Date date=new Date();
        for (TbBlackHouse list : tbBlackHouses) {
            list.setTime(date);
            list.setIsBlack(0);
            list.setFromId(localUser.getUser().getId());
            list.setBlackId(idGenerator.getNumberId());
        }
        int rows = tbBlackHouseMapper.insertLand(tbBlackHouses);
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
    public TbBlackHouseVo selectIsBlackTwo(TbBlackHouse tbBlackHouse) {
        return tbBlackHouseMapper.selectIsBlackTwo(tbBlackHouse);
    }

    @Override
    public List<TbBlackHouse> selectById(Long fromId) {
        QueryWrapper<TbBlackHouse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("from_id",fromId);
        queryWrapper.eq("is_black",0);
        return tbBlackHouseMapper.selectList(queryWrapper);
    }




}
