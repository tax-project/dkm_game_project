package com.dkm.medal.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.medal.dao.MedalMapper;
import com.dkm.medal.entity.Medal;
import com.dkm.medal.service.IMedalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 15:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MedalServiceImpl implements IMedalService {

    @Resource
    private MedalMapper medalMapper;

    @Autowired
    private LocalUser localUser;
    /**
     * 根据用id查询勋章(详情)
     * @return  List<Medal>
     */
    @Override
    public List<Medal> queryMedalById() {
        UserLoginQuery user = localUser.getUser();
        List<Medal> medals = medalMapper.queryMedalById(user.getId());
        if(medals.size()==0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "查询数据为null,或者数据异常");
        }
        return medals;
    }

    /**
     * 根据勋章id查询勋章详情
     * @param medalId
     * @return  List<Medal>
     */
    @Override
    public List<Medal> queryMedalByIdDetails(Integer medalId) {
        return medalMapper.queryMedalByIdDetails(medalId);
    }
}
