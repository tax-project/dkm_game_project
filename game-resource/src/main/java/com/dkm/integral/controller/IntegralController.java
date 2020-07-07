package com.dkm.integral.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.integral.entity.Integral;
import com.dkm.integral.service.IIntegralService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.vo.SeedVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:46
 */
@RestController
@Slf4j
@RequestMapping("IntegralController")
public class IntegralController {

    @Autowired
    private IIntegralService iIntegralService;


    /**
     * 查询所有积分产品
     * @return
     */
    @ApiOperation(value = "查询所有积分产品", notes = "查询所有积分产品")
    @PostMapping("/queryAllIntegral")
    @CrossOrigin
    public List<Integral> queryAllIntegral() {
        return iIntegralService.queryAllIntegral();
    }

    /**
     * 根据用户id查询用户积分
     * @return
     */
    @ApiOperation(value = "根据用户id查询用户积分", notes = "根据用户id查询用户积分")
    @PostMapping("/queryUserIdIntegral")
    @CrossOrigin
    @CheckToken //自定义注解 判断用户token是否存在
   public Integer queryUserIdIntegral(){
       return iIntegralService.queryUserIdIntegral();
   }


    /**
     * 立即兑换
     * @return
     */
    @ApiOperation(value = "立即兑换", notes = "立即兑换")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "iMyIntegral", value = "积分价格"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "sCurrentlyHasNum", value = "得到的金星星数量"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "sStar", value = "星星类型（金星星传1，紫星星传2）"),
    })
    @PostMapping("/immediatelyChange")
    @CrossOrigin
    @CheckToken //自定义注解 判断用户token是否存在
    public void immediatelyChange(@RequestParam(value = "iMyIntegral") Integer iMyIntegral,
                                  @RequestParam(value = "sCurrentlyHasNum") Integer sCurrentlyHasNum,
                                  @RequestParam(value = "sStar") Integer sStar){

        if(iMyIntegral==null || sCurrentlyHasNum==null || sStar==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        //修改用户积分
        iIntegralService.updateUserIntegral(iMyIntegral);
        //修改用户星星的数量
        iIntegralService.updateUserStarsNumber(sCurrentlyHasNum,sStar);
    }
}
