package com.dkm.feedback.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feedback.entity.FeedBack;
import com.dkm.feedback.entity.vo.FeedBackVo;
import com.dkm.feedback.service.IFeedBackService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/19 14:33
 */
@Api(tags = "反馈信息Api")
@RestController
@Slf4j
@RequestMapping("/FeedBackController")
public class FeedBackController {

    @Autowired
    private IFeedBackService iFeedBackService;


    @ApiOperation(value = "添加反馈信息",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "integer",name = "fStatus",value = "问题类型"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "fContent",value = "反馈内容")
    })
    @PostMapping("/insertFeedBack")
    @CrossOrigin
    @CheckToken
    public int insertFeedBack(@RequestBody FeedBackVo feedBackVo){
        if (feedBackVo.getFContent() == null || feedBackVo.getFStatus() == null) {

            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }

        int i = iFeedBackService.insertFeedBack(feedBackVo);

        return i;
    }

    @ApiOperation(value = "查询自己所有的反馈信息",notes = "成功返回数据 反则为空")
    @GetMapping("/queryAllFeedBack")
    @CrossOrigin
    @CheckToken
    public List<FeedBack> queryAllFeedBack(){
        List<FeedBack> feedBacks = iFeedBackService.queryAllFeedBack();
        return feedBacks;
    }

}
