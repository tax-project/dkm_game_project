package com.dkm.familyMine.controller;

import com.dkm.familyMine.service.IFamilyMineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 家族矿区请求方案
 */
@RestController
@RequestMapping("/familyMine")
public class FamilyMineController {

    @Autowired
    private IFamilyMineService familyService;

    //    @CrossOrigin
//    @CheckToken


    @GetMapping("/{id}/battleField")
    public Object getBattleField(  @PathVariable Long id) {
        Map<Object, Object> map = new HashMap<>();
        map.put("BattleFieldId", familyService.getMineBattleFieldId(id));
        return map;
    }

}
