package com.dkm.mine.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mine.bean.FamilyAddition;
import com.dkm.mine.bean.other.User2FamilyId;
import com.dkm.mine.bean.vo.BattleItemInfoVo;
import com.dkm.mine.bean.vo.MineItemDetailVo;
import com.dkm.mine.bean.vo.MineVo;
import com.dkm.mine.bean.vo.OccupyResultVo;
import com.dkm.mine.service.IMineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author fkl
 */
@Api(tags = "新的家族矿区 API")
@RequestMapping("/mine/")
@RestController
public class MineController {

    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyDetailDao familyDetailDao;

    @Resource
    private IMineService service;


    @ApiOperation("获取金矿的基础信息和等级相关的信息")
    @CrossOrigin
    @GetMapping("/getMineLevelType")
    public List<BattleItemInfoVo> getMineLevelType() {
        return service.getItemsLevelType();
    }

    @ApiOperation("获取家族等级信息与金币加成")
    @CrossOrigin
    @GetMapping("/getFamilyType")
    public List<FamilyAddition> getFamilyType() {
        return service.getFamilyType();
    }


    @ApiOperation("获取家族矿区的所有信息")
    @CrossOrigin
    @CheckToken
    @GetMapping("/getMineInfo")
    public MineVo getAllInfo() {
        val user2FamilyId = getUser2FamilyId();
        return service.getAllInfo(user2FamilyId.getUserId(), user2FamilyId.getFamilyId());
    }

    @ApiOperation("查看矿山详情")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "path", name = "矿区的矿山 ID", required = true, dataType = "Long")
    )
    @CrossOrigin
    @CheckToken
    @GetMapping("/{battleId}/detail")
    public MineItemDetailVo detail(@PathVariable long battleId) {
        return service.detail(battleId);
    }

    @ApiOperation("占领矿山")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "path", name = "矿区的矿山 ID", required = true, dataType = "Long")
    )
    @CrossOrigin
    @CheckToken
    @GetMapping("/{battleId}/occupy")
    public OccupyResultVo occupy(@PathVariable long battleId) {
        return service.occupy(battleId);
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
