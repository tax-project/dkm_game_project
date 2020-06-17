package com.dkm.goldMine.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.goldMine.bean.vo.FamilyGoldMineVo;
import com.dkm.goldMine.service.IMineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/family/goldMine/")
public class MineController {

    @Resource
    private IMineService mineService;

    @GetMapping("/{familyId}/getInfo")
    public FamilyGoldMineVo getFamilyGoldMine(@PathVariable String familyId) {
        Long familyIdInt;
        try {
            familyIdInt = Long.parseLong(familyId);
        } catch (NumberFormatException e) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "家族ID 无法格式化！[" + familyId + "].");
        }
        return   mineService.getFamilyGoldMine(familyIdInt);
    }
}
