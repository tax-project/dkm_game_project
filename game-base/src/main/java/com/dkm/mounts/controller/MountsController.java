package com.dkm.mounts.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mounts.entity.MountsDetailEntity;
import com.dkm.mounts.entity.dto.BuyMountVo;
import com.dkm.mounts.entity.dto.MountsDetailDto;
import com.dkm.mounts.entity.dto.UserInfoDto;
import com.dkm.mounts.service.MountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhd
 * @date 2020/5/11 17:27
 */
@Api(tags = "座驾api")
@RestController
@RequestMapping("/mounts")
public class MountsController {

    @Resource
    private LocalUser localUser;

    @Resource
    private MountService mountService;

    /**
     * 展示所有座驾
     * @return
     */
    @ApiOperation("展示所有座驾")
    @GetMapping("/getMounts")
    @CrossOrigin
    @CheckToken
    public List<MountsDetailDto> getMounts(){
        return mountService.getAll(localUser.getUser().getId());
    }

    /**
     * 展示拥有座驾
     * @return
     */
    @ApiOperation("展示拥有座驾")
    @GetMapping("/haveMounts")
    @CrossOrigin
    @CheckToken
    public List<MountsDetailDto> haveMounts(){
        return mountService.haveMounts(localUser.getUser().getId());
    }


    /**
     * 装备座驾
     * @param id
     */
    @ApiOperation("装备座驾")
    @ApiImplicitParam(name = "id", value = "拥有座驾id", required = true, dataType = "Long", paramType = "path")
    @PostMapping("/equipMount")
    @CrossOrigin
    @CheckToken
    public void equipMount(@RequestBody Long id){
        if(id == null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        mountService.equipMount(id,localUser.getUser().getId());
    }

    @ApiOperation("购买座驾")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mountId", value = "座驾id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "gold", value = "座驾价格", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "diamond", value = "座驾钻石", required = true, dataType = "Integer", paramType = "path")
    })
    @PostMapping("/buyMount")
    @CrossOrigin
    @CheckToken
    public void buyMount(@RequestBody BuyMountVo buyMountVo ){
        if(buyMountVo.getMountId()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        mountService.buyMount(buyMountVo.getMountId(),localUser.getUser().getId(),
                buyMountVo.getGold()==null?0:buyMountVo.getGold(),buyMountVo.getDiamond()==null?0:buyMountVo.getDiamond());
    }

    @GetMapping("/getUserInfo")
    @CheckToken
    @CrossOrigin
    public UserInfoDto getUserInfo(){
        return mountService.getUserInfo(localUser.getUser().getId());
    }

}
