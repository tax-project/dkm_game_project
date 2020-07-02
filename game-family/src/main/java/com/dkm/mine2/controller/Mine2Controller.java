package com.dkm.mine2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mine2.bean.other.User2FamilyId;
import com.dkm.mine2.bean.vo.AllMineInfoVo;
import com.dkm.mine2.service.IMine2Service;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.val;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 符凯龙
 */
@Api(tags = "新的家族矿区 API")
@RequestMapping("/mine/")
@RestController
public class Mine2Controller {

    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyDetailDao familyDetailDao;

    private IMine2Service mine2Service;

    @CrossOrigin
    @CheckToken
    @GetMapping("/getAllInfo")
    public AllMineInfoVo getAllInfo() {
        val user2FamilyId = getUser2FamilyId();
        return mine2Service.getAllInfo(user2FamilyId.getUserId(), user2FamilyId.getFamilyId());
    }


    private User2FamilyId getUser2FamilyId() {
        val userId = localUser.getUser().getId();
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if (familyDetailEntity == null) {
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND, "还未加入家族");
        }
        val familyId = familyDetailEntity.getFamilyId();
        return new User2FamilyId(userId, familyId);
    }
}
