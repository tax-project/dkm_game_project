package com.dkm.box.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.box.dao.UserBoxMapper;
import com.dkm.box.entity.UserBoxEntity;
import com.dkm.box.entity.vo.BoxInfoVo;
import com.dkm.box.service.IUserBoxService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 14:30
 **/
@Service
public class UserBoxServiceImpl implements IUserBoxService {

    @Resource
    private UserBoxMapper userBoxMapper;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public List<BoxInfoVo> getBoxInfo(Long userId) {
        //获取用户宝箱
        List<BoxInfoVo> boxInfoVos = userBoxMapper.selectBoxById(userId);
        LocalDateTime now = LocalDateTime.now();
        if(boxInfoVos==null||boxInfoVos.size()==0){
            //无记录 新增记录
            ArrayList<UserBoxEntity> list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                //8个宝箱
                UserBoxEntity userBoxEntity = new UserBoxEntity();
                userBoxEntity.setBoxId(idGenerator.getNumberId());
                userBoxEntity.setUserId(userId);
                userBoxEntity.setOpenTime(now);
                //两个普通宝箱 6个2级宝箱
                userBoxEntity.setBoxLevel(i>1?2:1);
                list.add(userBoxEntity);
            }
            Integer integer = userBoxMapper.insertList(list);
            if(integer<=0) {throw  new ApplicationException(CodeType.SERVICE_ERROR,"初始化宝箱失败");}
            //转化前端信息
            list.forEach(userBoxEntity-> boxInfoVos.add(new BoxInfoVo(userBoxEntity.getBoxId(),null, DateUtils.formatDateTime(now),userBoxEntity.getBoxLevel(),1)));
            return boxInfoVos;
        }
        boxInfoVos.forEach(boxInfoVo -> {
            boxInfoVo.setStatus(now.isAfter(boxInfoVo.getOpenTime())?1:0);
            boxInfoVo.setTime(DateUtils.formatDateTime(boxInfoVo.getOpenTime()));
        });
        return boxInfoVos;
    }
}
