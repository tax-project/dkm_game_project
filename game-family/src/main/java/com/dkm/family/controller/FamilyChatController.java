package com.dkm.family.controller;

import com.dkm.family.service.FamilyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 家族聊天
 * @author: zhd
 * @create: 2020-06-09 09:32
 **/
@RestController
@RequestMapping("/familyChat")
public class FamilyChatController {

    @Resource
    private FamilyService familyService;

    @GetMapping("/getFamilyUserId")
    public List<Long> getFamilyUserId(@RequestParam("familyId")Long familyId){
        return familyService.getFamilyUserIds(familyId);
    }
}
