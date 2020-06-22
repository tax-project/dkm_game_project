package com.dkm.produce.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.produce.service.IProduceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 21:46
 */
@Api(tags = "产出api")
@RestController
@RequestMapping("/v1/produce")
public class ProduceController {

    @Autowired
    private IProduceService produceService;


    @ApiOperation(value = "添加产出", notes = "添加产出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attendantId", value = "跟班id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "attUserId", value = "用户跟班id", required = true, dataType = "Long", paramType = "path")
    })
    @GetMapping("/insertProduce")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> insertProduce (@RequestParam("attendantId") Long attendantId, @RequestParam("attUserId") Long attUserId) {
        if (attendantId == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return produceService.insertProduce(attendantId, attUserId);
    }

    /**
     * 掠夺赢了之后
     * 修改产出输方产出的物品
     * @param id  产出id
     * @return
     */
    @ApiOperation(value = "掠夺赢了之后修改产出输方产出的物品", notes = "掠夺赢了之后修改产出输方产出的物品")
    @GetMapping("/deleteOutGoodNumber")
    @CrossOrigin
    @CheckToken
    public int deleteOutGoodNumber(Long id){
        if(id==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return produceService.deleteOutGoodNumber(id);
    }


    /**
     *  内部调用
     *  查询跟班图片和跟班产出的所有物品
     * @param userId
     * @return
     */
    @GetMapping("/queryImgFood")
    public Map<String,Object> queryImgFood(@RequestParam(value = "userId") Long userId){
        return produceService.queryImgFood(userId);
    }



}
