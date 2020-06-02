package com.dkm.produce.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.produce.entity.vo.AttendantGoods;
import com.dkm.produce.entity.vo.UserAttendantGoods;
import com.dkm.produce.service.IProduceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @ApiImplicitParam(name = "attendantId", value = "跟班id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/insertProduce")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> insertProduce (@RequestParam("attendantId") Long attendantId) {
        if (attendantId == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return produceService.insertProduce(attendantId);
    }

    @GetMapping("/queryJoinOutPutGoods/{userId}")
    @CrossOrigin
    public List<AttendantGoods> queryJoinOutPutGoods(@PathVariable("userId") Long userId){
        return produceService.queryJoinOutPutGoods(userId);
    }


    @ApiOperation(value = "查询用户产出的物品", notes = "查询用户产出的物品")
    @GetMapping("/queryOutput")
    public List<UserAttendantGoods> queryOutput(){
        return produceService.queryOutput();
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
    public int deleteOutGoodNumber(Long id){
        if(id==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return produceService.deleteOutGoodNumber(id);
    }








}
