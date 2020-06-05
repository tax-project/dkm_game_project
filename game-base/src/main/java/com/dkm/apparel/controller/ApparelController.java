package com.dkm.apparel.controller;

import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.entity.dto.ApparelDto;
import com.dkm.apparel.service.ApparelService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-15 09:16there are always
 **/
@Api(tags = "服饰API")
@RestController
@RequestMapping("/apparel")
public class ApparelController {

    @Resource
    private ApparelService apparelService;

    @Resource
    private LocalUser localUser;

    @ApiOperation("获取所有服饰信息")
    @ApiImplicitParam(name = "type", value = "1 上衣 2裤子 空全部", paramType = "path", dataType = "Integer", required = false)
    @CrossOrigin
    @GetMapping("/getAll")
    @CheckToken
    public List<ApparelEntity> getAll(Integer type) {
        return apparelService.getAllApparels(type, localUser.getUser().getId());
    }

    @ApiOperation("获取拥有服饰")
    @ApiImplicitParam(name = "type", value = "类型", paramType = "path", dataType = "Integer", required = true)
    @CrossOrigin
    @GetMapping("/getHave")
    @CheckToken
    public List<ApparelEntity> getHave(Integer type) {
        return apparelService.getUserApparel(localUser.getUser().getId(), type);
    }


    @ApiOperation("制作服饰")
    @ApiImplicitParam(name = "apparelId", value = "服饰id", paramType = "path", dataType = "Long", required = true)
    @CrossOrigin
    @PostMapping("/doApparel")
    @CheckToken
    public void doApparel(@RequestBody Long apparelId) {
        apparelService.doApparel(apparelId, localUser.getUser().getId());
    }

    @ApiOperation("制作队列")
    @CrossOrigin
    @PostMapping("/getDoing")
    @CheckToken
    public ApparelDto getDoing() {
        return apparelService.getDoing(localUser.getUser().getId());
    }

    @ApiOperation("立即制作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apparelId", value = "服饰id", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(name = "diamond", value = "钻石", paramType = "path", dataType = "Integer", required = true)
    })
    @CrossOrigin
    @GetMapping("/nowDoing")
    @CheckToken
    public void nowDoing(@RequestParam("apparelId") Long apparelId, @RequestParam("diamond") Integer diamond) {
        apparelService.nowDoing(localUser.getUser().getId(), apparelId, diamond);
    }

    @ApiOperation("穿戴服饰")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apparelId", value = "服饰id", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(name = "type", value = "0脱1穿", paramType = "path", dataType = "Integer", required = true)
    })
    @CrossOrigin
    @GetMapping("/equipApparel")
    @CheckToken
    public void equipApparel(Long apparelId, Integer type) {
        apparelService.equipApparel(localUser.getUser().getId(), apparelId, type);
    }

    @ApiOperation("正在穿戴的服饰")
    @CrossOrigin
    @GetMapping("/nowEquip")
    @CheckToken
    public List<ApparelDto> nowEquip() {
        return apparelService.getEquip(localUser.getUser().getId());
    }

    @ApiOperation("出售服饰")
    @ApiImplicitParam(name = "apparelUserId", value = "apparelUserId", paramType = "path", dataType = "Long", required = true)
    @CrossOrigin
    @PostMapping("/sellApparel")
    @CheckToken
    public void sellApparel(@RequestBody Long apparelUserId) {
        apparelService.sellApparel(apparelUserId, localUser.getUser().getId());
    }
}
