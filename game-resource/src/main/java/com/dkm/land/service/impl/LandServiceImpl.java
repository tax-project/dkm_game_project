package com.dkm.land.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.land.dao.LandMapper;
import com.dkm.land.entity.Land;
import com.dkm.land.service.ILandService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
     * 根据用户id查询土地
     * @return List<Land>
     */
    @Override
    public List<Land> queryLandId() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        return landMapper.queryLand(query.getId());
    }
}
