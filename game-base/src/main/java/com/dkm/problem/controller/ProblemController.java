package com.dkm.problem.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.problem.entity.Problem;
import com.dkm.problem.entity.vo.ProblemInsertVo;
import com.dkm.problem.entity.vo.ResultVo;
import com.dkm.problem.service.IProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Api(tags = "问题Api")
@RestController
@Slf4j
@RequestMapping("/v1/problem")
public class ProblemController {

   @Autowired
   private IProblemService problemService;

   @PostMapping("/insertAll")
   public ResultVo insertAllProblem (@RequestBody ProblemInsertVo vo) {

      if (vo.getList().size() == 0) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      problemService.insertAllProblem(vo.getList());

      ResultVo resultVo = new ResultVo();
      resultVo.setResult("ok");

      return resultVo;
   }


   @ApiOperation(value = "随机返回10条数据", notes = "随机返回10条数据")
   @ApiImplicitParam(name = "id", value = "红包id", required = true, dataType = "Long", paramType = "path")
   @GetMapping("/listProblem")
   @CrossOrigin
   @CheckToken
   public List<Problem> listProblem (@RequestParam("id") Long id) {
      if (id == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      return problemService.listProblem (id);
   }
}
