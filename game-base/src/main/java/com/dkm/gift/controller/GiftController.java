package com.dkm.gift.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.entity.dto.GiftRankingDto;
import com.dkm.gift.entity.vo.SendGiftVo;
import com.dkm.gift.service.GiftService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 送礼e
 * @author: zhd
 * @create: 2020-05-27 09:20
 **/
@Api(tags = "礼物API")
@RestController
@RequestMapping("/gift")
public class GiftController {

    @Resource
    private LocalUser localUser;

    @Resource
    private GiftService giftService;

    @ApiOperation("获取礼物列表")
    @ApiImplicitParam(name = "type",value = "0金币1钻石",required = true,paramType = "int",dataType = "path")
    @GetMapping("/getGift")
    @CrossOrigin
    @CheckToken
    public List<GiftEntity> getGift(@RequestParam("type") Integer type){
        if(type==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return giftService.getAllGift(type);
    }


    @ApiOperation("送礼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiveId",value = "接收人userId",required = true,paramType = "Long",dataType = "path"),
            @ApiImplicitParam(name = "gold",value = "消耗金币",required = false,paramType = "int",dataType = "path"),
            @ApiImplicitParam(name = "diamond",value = "消耗钻石",required = false,paramType = "int",dataType = "path"),
            @ApiImplicitParam(name = "charm",value = "魅力值",required = true,paramType = "int",dataType = "path"),
            @ApiImplicitParam(name = "giftId",value = "礼物id",required = true,paramType = "Long",dataType = "path"),
            @ApiImplicitParam(name = "giftName",value = "名称",required = true,paramType = "int",dataType = "path"),
            @ApiImplicitParam(name = "giftNumber",value = "礼品数量",required = true,paramType = "int",dataType = "path")
    })
    @PostMapping("/sendGift")
    @CrossOrigin
    @CheckToken
    public void getGift(@RequestBody SendGiftVo sendGiftVo){
        if(sendGiftVo==null||sendGiftVo.getReceiveId()==null
                ||(sendGiftVo.getDiamond().equals(sendGiftVo.getGold()))
                ||sendGiftVo.getDiamond()<0||sendGiftVo.getGold()<0
                ||sendGiftVo.getGiftId()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        sendGiftVo.setSendId(localUser.getUser().getId());
        giftService.sendGift(sendGiftVo);
    }

    @ApiOperation("魅力排行")
    @ApiImplicitParam(name = "type",value = "1富豪2魅力",required = true,paramType = "int",dataType = "path")
    @GetMapping("/getGiftRanking")
    @CrossOrigin
    public List<GiftRankingDto> getGiftRanking(@RequestParam("type") Integer type){
        if(type==null||type>2||type<1){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return giftService.getGiftRanking(type);
    }

    @ApiOperation("花神排行")
    @ApiImplicitParam(name = "type",value = "1骑士2花神",required = true,paramType = "int",dataType = "path")
    @GetMapping("/getGiftFlower")
    @CrossOrigin
    public List<GiftRankingDto> getGiftFlower(@RequestParam("type") Integer type){
        if(type==null||type>2||type<1){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return giftService.getGiftFlower(type);
    }
}
