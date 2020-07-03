package com.dkm.turntable.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.turntable.entity.vo.AddGoodsInfoVo;
import com.dkm.turntable.entity.vo.TurntableInfoVo;
import com.dkm.turntable.service.ITurntableCouponService;
import com.dkm.turntable.service.ITurntableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: game_project
 * @description: 转盘
 * @author: zhd
 * @create: 2020-06-11 09:40
 **/
@Api(tags = "幸运大转盘API")
@RestController
@RequestMapping("/turntable")
public class TurntableController {

    @Resource
    private LocalUser localUser;

    @Resource
    private ITurntableCouponService turntableCouponService;

    @Resource
    private ITurntableService turntableService;

    /**
     * 获取转盘物品
     * @param type
     * @return
     */
    @GetMapping("/getGoods")
    @ApiOperation("获取转盘物品信息")
    @ApiImplicitParam(value = "转盘类型1绿券2蓝券以此类推",name = "type",dataType = "int",paramType = "path",required = true)
    @CrossOrigin
    @CheckToken
    public List<TurntableInfoVo> getGoods(Integer type){
        return turntableService.getTurntable(localUser.getUser().getId(),type);
    }

    /**
     * 用户卷数量信息
     * @return
     */
    @GetMapping("/getCoupons")
    @ApiOperation("获取卷信息")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> getCoupons(){
        return turntableCouponService.getUserCoupon(localUser.getUser().getId());
    }

    /**
     * 用户卷数量信息
     * @return
     */
    @PostMapping("/addGoods")
    @ApiOperation("获取抽中物品到背包")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "抽中物品id",name = "id",dataType = "long",paramType = "path",required = true),
            @ApiImplicitParam(value = "抽中物品数量",name = "number",dataType = "int",paramType = "path",required = true),
            @ApiImplicitParam(value = "抽奖类型",name = "type",dataType = "int",paramType = "path",required = true)
    })
    @CrossOrigin
    @CheckToken
    public void addGoods(@RequestBody AddGoodsInfoVo addGoodsInfoVo){
        if(addGoodsInfoVo==null||addGoodsInfoVo.getId()==null||addGoodsInfoVo.getNumber()<1)throw new ApplicationException(CodeType.PARAMETER_ERROR);
        turntableService.addGoods(localUser.getUser().getId(),addGoodsInfoVo);
    }
}
