package com.dkm.shop.service.impl;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.shop.dao.TbThedailyOneMapper;
import com.dkm.shop.domain.TbThedaily;
import com.dkm.shop.domain.TbThedailyOne;
import com.dkm.shop.service.ITbThedailyOneService;
import com.dkm.shop.service.ITbThedailyService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbThedailyOneServiceImpl  implements ITbThedailyOneService {
    @Resource
    TbThedailyOneMapper tbThedailyOneMapper;
    @Autowired
    ITbThedailyService tbThedailyService;
    @Autowired
    private LocalUser localUser;
    @Autowired
    private IdGenerator idGenerator;
    @Override
    public int selectCountThd(String thdId) {
        if( StringUtils.isEmpty(thdId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        List<TbThedaily> list=tbThedailyService.findById(Long.valueOf(thdId));
        if( StringUtils.isEmpty(list) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND, "找不到数据");
        }
        TbThedailyOne tbThedailyOne=new TbThedailyOne();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        tbThedailyOne.setExp2(format.format(date));
        tbThedailyOne.setUserId(localUser.getUser().getId());
        tbThedailyOne.setThdId(Long.valueOf(thdId));
        for (TbThedaily tbThedaily : list) {
            //等于一 代表一天只能买一次 反则一天只能买二十次
            if(tbThedaily.getThdIsva()==1){
                int rows=tbThedailyOneMapper.selectCountThd(tbThedailyOne);
                if(rows>0){
                    return 2;
                }else{
                    return 0;
                }
            }else{
                int rows=tbThedailyOneMapper.selectCountThd(tbThedailyOne);
                if(rows>=20){
                    return 2;
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override
    public int selectCount(String thdId) {
        if( StringUtils.isEmpty(thdId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        TbThedailyOne tbThedailyOne=new TbThedailyOne();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        tbThedailyOne.setUserId(localUser.getUser().getId());
        tbThedailyOne.setExp2(format.format(date));
        tbThedailyOne.setThdId(Long.valueOf(thdId));
        return tbThedailyOneMapper.selectCountThd(tbThedailyOne);
    }

    @Override
    public void insert(String thdId) {
        if( StringUtils.isEmpty(thdId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        TbThedailyOne tbThedailyOne=new TbThedailyOne();
        tbThedailyOne.setThdId(Long.valueOf(thdId));
        tbThedailyOne.setThoId(idGenerator.getNumberId());
        tbThedailyOne.setUserId(localUser.getUser().getId());
        int rows=tbThedailyOneMapper.insert(tbThedailyOne);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }
}
