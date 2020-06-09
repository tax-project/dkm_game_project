package com.dkm.wallet.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.wallet.service.IWithdrawalService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 16:33
 */
@Data
@RestController
@RequestMapping("/WithdrawalRecord")
public class WithdrawalRecord {

    @Autowired
    private IWithdrawalService iWithdrawalService;



    /**
     *  查询所有提现数据
     */
    @ApiOperation(value = " 查询所有提现数据", notes = " 查询所有提现数据")
    @PostMapping("/queryAllWithdrawalData")
    @CrossOrigin
    @CheckToken
   public Map<String,Object> queryAllWithdrawalData(){
       return iWithdrawalService.queryAllWithdrawalData();
   }

}
