package com.dkm.familyMine.controller;

import com.dkm.familyMine.dao.FamilyMineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/familyGoldMine")
public class TestController {
    @GetMapping("/test")
    public String hello() {
        return "ok";
    }
    @Autowired
    private FamilyMineMapper familyDao;

//    @CrossOrigin
//    @CheckToken
//    @GetMapping("/getAllFamily")
//
//    public Object getAllFamily() {
//        return familyDao.getFamilyMineById();
//    }


}
