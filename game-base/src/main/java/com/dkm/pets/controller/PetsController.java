package com.dkm.pets.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.vo.FeedPetInfoVo;
import com.dkm.pets.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/9 9:34
 */
@Api(tags = "宠物api")
@RestController
@RequestMapping("/v1/pets")
public class PetsController  {

    @Resource
    private PetService petService;

    @Resource
    private LocalUser localUser;
    /***
     * 获得用户宠物信息
     * @return
     */
    @ApiOperation("获取用户宠物信息")
    @GetMapping("/getPets")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> getPets(){
        return petService.getAllPets(localUser.getUser().getId());
    }

    @ApiOperation("宠物升级接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beeTekId", value = "蜂蜜背包id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "fishTekId", value = "鱼背包id", required = false, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pId", value = "宠物id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pGrade", value = "宠物等级", required = true, dataType = "Integer", paramType = "path"),
    })
    @RequestMapping(value = "/feedPets")
    @CrossOrigin
    @CheckToken
    public void feedPets(@RequestBody FeedPetInfoVo petInfoVo){
        if(petInfoVo==null||petInfoVo.getPGrade()==null||petInfoVo.getPGrade()<=0||petInfoVo.getPId()==null
                ||petInfoVo.getBeeTekId()==null||(petInfoVo.getPGrade()>=10&&petInfoVo.getFishTekId()==null)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数有误");
        }
        petInfoVo.setUserId(localUser.getUser().getId());
        petService.feedPet(petInfoVo);
    }

    @ApiOperation("宠物升级接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "milkTekId", value = "牛奶背包id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pId", value = "宠物id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pGrade", value = "宠物等级", required = true, dataType = "Integer", paramType = "path"),
    })
    @PostMapping("/upLevel")
    @CrossOrigin
    @CheckToken
    public void upLevel(@RequestBody FeedPetInfoVo petInfoVo){
        if(petInfoVo==null||petInfoVo.getPGrade()==null||petInfoVo.getPGrade()<=0
                ||petInfoVo.getPId()==null||petInfoVo.getMilkTekId()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数有误");
        }
        petInfoVo.setUserId(localUser.getUser().getId());
        petService.petLevelUp(petInfoVo);
    }

    /**
     * 战斗获取宠物信息（内部）
     * @param userId
     */
    @GetMapping("/getPetInfo")
    @CrossOrigin
    public List<PetsDto> getPetInfo(@RequestParam Long userId){
        return petService.getPetInfo(userId);
    }
}
