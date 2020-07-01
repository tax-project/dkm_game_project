package com.dkm.housekeeper.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.housekeeper.service.HousekeeperService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/8 16:35
 */
@Api(tags = "管家api")
@RestController
@RequestMapping("/v1/housekeeper")
public class HousekeeperController {

    @Resource
    private HousekeeperService housekeeperService;

    @Resource
    private LocalUser localUser;
    /**
     * 开通管家功能
     * @param money
     */
    @ApiOperation("开通管家")
    @ApiImplicitParam(name = "money", value = "支付金额", required = true, dataType = "BigDecimal", paramType = "path")
    @PostMapping("/openHousekeeper")
    @CrossOrigin
    @CheckToken
    public void openHousekeeper(@RequestBody BigDecimal money){
        if(money==null||money.compareTo(BigDecimal.ZERO)<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数错误");
        }
        if(housekeeperService.openHousekeeper(localUser.getUser().getId() , money)<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"开通失败");
        }
    }

    /**
     * 管家剩余天数、开工时间信息
     * @return
     */
    @ApiOperation("管家剩余天数、开工时间信息")
    @GetMapping("/remnantDays")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> remnantDays(){
        return housekeeperService.remnantDays(localUser.getUser().getId());
    }

    /**
     * 修改管家状态/时间
     */
    @ApiOperation("修改管家时间状态")
    @GetMapping("/updateStatus")
    @CrossOrigin
    @CheckToken
    public Map<String,String> updateStatus(){
        return housekeeperService.updateTime(localUser.getUser().getId());
    }

    /**
     * 获取宝箱装备
     * @return
     */
    @ApiOperation("获取装备")
    @GetMapping("/getBoxEquipment")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentVo> getBoxEquipment(){
         return housekeeperService.getBoxEquipment(localUser.getUser().getId());
    }

    /**
     * 获取宝箱装备
     * @return
     */
    @ApiOperation("获取种植经验金币")
    @GetMapping("/getSeed")
    @CrossOrigin
    @CheckToken
    public Map<String,Integer> getSeed(){
        return housekeeperService.getSeed(localUser.getUser().getId());
    }

    /**
     * 判断是否能偷取
     * @return
     */
    @ApiOperation("获取管家状态判断是否能偷取")
    @GetMapping("/status")
    @CrossOrigin
    public boolean housekeeperStatus(@RequestParam("userId") Long userId){
        return housekeeperService.housekeeperStatus(userId);
    }
}
