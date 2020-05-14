package com.dkm.mounts.controller;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mounts.entity.MountsInfoEntity;
import com.dkm.mounts.service.MountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<MountsInfoEntity> getMounts(){
        return mountService.getAll();
    }

    /**
     * 展示拥有座驾
     * @return
     */
    @ApiOperation("展示拥有座驾")
    @GetMapping("/haveMounts")
    @CrossOrigin
    public List<MountsInfoEntity> haveMounts(){
        return mountService.haveMounts(123L);
    }
}
