package com.dkm.pets.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
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
    public Map<String,Object> getPets(){
        return petService.getAllPets(123L);
    }

    @ApiOperation("喂食宠物")
    @ApiImplicitParam(name = "petInfo", value = "宠物信息", required = true, dataType = "PetsDto", paramType = "path")
    @PostMapping("/feedPet")
    @CrossOrigin
    public Map<String,Object> feedPet(@RequestBody PetsDto petInfo){
        if(petInfo==null||petInfo.getEatFood().size()==0||petInfo.getPId()==null
            ||petInfo.getEatFood().get(0).getFoodId()==null||petInfo.getEatFood().get(0).getENumber()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数错误");
        }
        return petService.feedPet(petInfo,123L);
    }

    @ApiOperation("宠物升级接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "petId", value = "宠物id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "foodId", value = "奶瓶id", required = true, dataType = "Long", paramType = "path")
    })

    @PostMapping("/upLevel")
    @CrossOrigin
    @CheckToken
    public void upLevel(Long pId,Long foodId){

    }
}
