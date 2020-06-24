package com.dkm.family.controller;

import com.dkm.family.dao.FamilyDao;
import com.dkm.family.entity.vo.UserInfoVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-23 11:54
 **/
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    FamilyDao familyDao;

    @GetMapping("/getUserinfoById")
    @CrossOrigin
    public UserInfoVo getUserinfoById(@RequestParam("userId")Long userId){
        return familyDao.getUserinfoById(userId);
    }
}
