package com.dkm.goldMine.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.goldMine.bean.vo.FamilyGoldMineVo;
import com.dkm.goldMine.service.IMineService;
import com.dkm.jwt.islogin.CheckToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/family/goldMine/")
public class MineController {

    @Resource
    private IMineService mineService;

    @CrossOrigin
    @CheckToken
    @GetMapping("/{familyId}/getInfo")
    public FamilyGoldMineVo getFamilyGoldMine(@PathVariable String familyId) {
        long familyIdInt;
        try {
            familyIdInt = Long.parseLong(familyId);
        } catch (NumberFormatException e) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "家族ID 无法格式化！[" + familyId + "].");
        }
        return   mineService.getFamilyGoldMine(familyIdInt);
    }
}
