package com.dkm.mine2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mine2.bean.other.User2FamilyId;
import com.dkm.mine2.bean.vo.BattleItemPropVo;
import com.dkm.mine2.bean.vo.FamilyAdditionVo2Entity;
import com.dkm.mine2.bean.vo.MineVo;
import com.dkm.mine2.service.IMine2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author fkl
 */
@Api(tags = "新的家族矿区 API")
@RequestMapping("/mine/")
@RestController
public class Mine2Controller {

    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyDetailDao familyDetailDao;

    @Resource
    private IMine2Service mine2Service;


    @ApiOperation("获取金矿的基础信息和等级相关的信息")
    @CrossOrigin
    @GetMapping("/getMineLevelType")
    public List<BattleItemPropVo> getMineLevelType() {
        return mine2Service.getItemsLevelType();
    }

    @ApiOperation("获取家族等级信息与金币加成")
    @CrossOrigin
    @GetMapping("/getFamilyType")
    public List<FamilyAdditionVo2Entity> getFamilyType() {
        return mine2Service.getFamilyType();
    }


    @ApiOperation("获取家族矿区的所有信息")
    @CrossOrigin
    @CheckToken
    @GetMapping("/getMineInfo")
    public MineVo getAllInfo() {
        val user2FamilyId = getUser2FamilyId();
        return mine2Service.getAllInfo(user2FamilyId.getUserId(), user2FamilyId.getFamilyId());
    }


    private User2FamilyId getUser2FamilyId() {
        val userId = localUser.getUser().getId();
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>()
                .lambda().eq(FamilyDetailEntity::getUserId, userId));
        if (familyDetailEntity == null) {
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND, "还未加入家族");
        }
        val familyId = familyDetailEntity.getFamilyId();
        return new User2FamilyId(userId, familyId);
    }
}
