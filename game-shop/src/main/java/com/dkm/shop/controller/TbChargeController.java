package com.dkm.shop.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbCharge;
import com.dkm.shop.service.TbChargeService;
import com.dkm.shop.utils.Message;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首充豪礼表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@RequestMapping("/dkm/tbCharge")
@RestController
@ResponseBody
@Api(description = "游戏项目首充豪礼表接口文档")
public class TbChargeController {
    @Resource
    TbChargeService tbChargeService;

    /**
     * 查询所有的首充豪礼
     * @param
     */
    @ApiOperation(value = "查询所有的首充豪礼",notes = "如果查询成功返回首充豪礼的json,没有则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "cheapId",value = "首充豪礼主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "ruleDescription",value = "规则说明"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "chargeImage",value = "首充图片"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "chargeMoney",value = "充值金额"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "拓展字段1"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "Date",name = "createTime",value = "创建时间")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/selectAll")
    @CrossOrigin
    //@CheckToken
    public List<TbCharge> selectAll(){
        List<TbCharge> list=tbChargeService.selectAll();
        return list;
    }

    /**
     * 首充豪礼的增加接口
     * @param tbCharges
     */
    @ApiOperation(value = "首充豪礼的增加接口",notes = "成功则返回操作成功!")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "cheapId",value = "首充豪礼主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "ruleDescription",value = "规则说明",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "chargeImage",value = "首充图片",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "chargeMoney",value = "充值金额",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "拓展字段1"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "Date",name = "createTime",value = "创建时间")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addBlackHouse")
    @CrossOrigin
   // @CheckToken
    public Message addBlackHouse(@RequestBody TbCharge tbCharges){
        Message errorResult=new Message();
        if(StringUtils.isEmpty(tbCharges.getRuleDescription()) || StringUtils.isEmpty(tbCharges.getChargeImage()) || StringUtils.isEmpty(tbCharges.getChargeMoney()) ){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "必填参数不能为空");
        }
        tbChargeService.addTbCharge(tbCharges);
        errorResult.setNum(1);
        errorResult.setMsg("操作成功");
        return errorResult;
    }
	
}
