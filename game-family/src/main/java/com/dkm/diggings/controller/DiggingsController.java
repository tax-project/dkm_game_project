package com.dkm.diggings.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.other.User2FamilyId;
import com.dkm.diggings.bean.vo.DiggingsVo;
import com.dkm.diggings.bean.vo.MineDetailVo;
import com.dkm.diggings.bean.vo.MineInfoVo;
import com.dkm.diggings.bean.vo.OccupyResultVo;
import com.dkm.diggings.service.IDiggingsService;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
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
@Api(tags = "家族矿区 API")
@RequestMapping("/diggings/")
@RestController
public class DiggingsController {

    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyDetailDao familyDetailDao;

    @Resource
    private IDiggingsService service;


    @ApiOperation("获取金矿的基础信息和等级相关的信息（静态的）")
    @CrossOrigin
    @GetMapping(value = "/getMineLevelType", produces = "application/json")
    public List<MineInfoVo> getMineLevelType() {
        return service.getItemsLevelType();
    }

    @ApiOperation("获取家族等级信息与金币加成相关的信息 （静态的）")
    @CrossOrigin
    @GetMapping(value = "/getFamilyType", produces = "application/json")
    public List<FamilyAddition> getFamilyType() {
        return service.getFamilyType();
    }


    @ApiOperation("获取家族矿区的所有信息")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/getMineInfo", produces = "application/json")
    public DiggingsVo getAllInfo() {
        val user2FamilyId = getUser2FamilyId();
        return service.getAllInfo(user2FamilyId.getUserId(), user2FamilyId.getFamilyId());
    }


    @ApiOperation("查看矿山的详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "mineId", required = true, dataType = "Long", value = "矿山的 id"),
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    })
    @CrossOrigin
    @CheckToken
    @GetMapping(value = "/{mineId}/detail", produces = "application/json")
    public MineDetailVo detail(@PathVariable long mineId) {
        return service.detail(mineId, getUser2FamilyId().getUserId());
    }

    @ApiOperation("占领矿山")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "mineId", required = true, dataType = "Long", value = "矿山的 id"),
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    })
    @CrossOrigin
    @CheckToken
    @GetMapping(value = "/{mineId}/occupy", produces = "application/json")
    public OccupyResultVo occupy(@PathVariable long mineId) {
        return service.occupy(mineId);
    }


    /**
     * 根据 Token 来获取各种信息
     *
     */
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
