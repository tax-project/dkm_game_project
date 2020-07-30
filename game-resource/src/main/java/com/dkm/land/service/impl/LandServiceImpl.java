package com.dkm.land.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.land.dao.LandMapper;
import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.UserLandUnlock;
import com.dkm.land.service.ILandService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:土地
 * @USER: 刘梦祺
 * @DATE: 2020/5/9 9:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LandServiceImpl implements ILandService {
    @Resource
    private LandMapper landMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private LocalUser localUser;
    /**
     * 土地批量增加
     * @param list
     * @return null
     */
    @Override
    public void insertLand(List<Land> list) {
        for (Land vo : list) {
           vo.setLaId(idGenerator.getNumberId());
        }
        int insertland = landMapper.insertLand(list);
        if(insertland <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "批量增加失败");
        }
    }
    /**
     * 查询土地
     * @return List<Land>
     */
    @Override
    public List<UserLandUnlock> queryUserByIdLand() {
        Long id = localUser.getUser().getId();
        List<UserLandUnlock> userLandUnlocks = landMapper.queryUserByIdLand(id);
        if(userLandUnlocks.size()==0){
            List<UserLandUnlock> list=new ArrayList<>();
            for (int i = 1; i <=10; i++) {
                UserLandUnlock userLandUnlock=new UserLandUnlock();
                userLandUnlock.setLaNo(i);
                if(i==1){
                    userLandUnlock.setLaStatus(1);
                }else{
                    userLandUnlock.setLaStatus(0);
                }
                userLandUnlock.setUserId(localUser.getUser().getId());
                list.add(userLandUnlock);
            }
            landMapper.addLand(list);

            return list;
        }
        return userLandUnlocks;
    }

    @Override
    public void updateLandStatus(Integer laNo) {
        int i = landMapper.updateStatus(localUser.getUser().getId(),laNo);
        if(i<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"解锁土地异常");
        }
    }
}
