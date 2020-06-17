package com.dkm.wallet.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.wallet.entity.WithdrawalRecord;
import com.dkm.wallet.service.IWithdrawalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:53
 */
@Api(tags = "提现记录api")
@RestController
@RequestMapping("/WithdrawalRecordController")
public class WithdrawalRecordController {

    @Autowired
    private IWithdrawalRecordService iWithdrawalRecordService;

    /**
     * 查看本月提现记录
     */
    @ApiOperation(value = "查看本月提现记录", notes = "查看本月提现记录")
    @PostMapping("/queryRecordMonth")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> queryRecordMonth() {
        return iWithdrawalRecordService.queryRecordMonth();
    }


}
