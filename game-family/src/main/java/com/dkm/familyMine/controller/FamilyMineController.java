package com.dkm.familyMine.controller;

import com.dkm.family.service.FamilyService;
import com.dkm.familyMine.dao.FamilyMineDao;
import com.dkm.familyMine.service.IFamilyMineService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
        Map map = new HashMap<Object, Object>();
        map.put("BattleFieldId", familyService.getMineBattleFieldId(id));
        return map;
    }

}
