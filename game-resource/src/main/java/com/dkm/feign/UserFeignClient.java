package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.feign.entity.PetsDto;
import com.dkm.feign.fallback.UserFeignClientFallback;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/13
 * @vesion 1.0
 **/
@FeignClient(value = "service-user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

   /**
    *  查询用户所有信息
    * @param id
    * @return
    */
   @GetMapping("/v1/we/chat/queryUser/{id}")
   Result<UserInfoQueryBo> queryUser(@PathVariable("id") Long id);

   /**
    * 修改增加用户声望金币
    * @param increaseUserInfoBO
    * @return
    */
   @PostMapping("/v1/userInfo/increase")
   Result increaseUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO);

   /**
    * 修改减少用户声望金币
    * @param increaseUserInfoBO
    * @return
    */
   @PostMapping("/v1/userInfo/cut")
   Result cutUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO);

   /**
    *  随机返回20条用户信息
    * @return 返回用户信息
    */
   @GetMapping("/v1/userInfo/listUserPlunder")
   Result<List<UserPlunderBo>> listUserPlunder();

   /**
    *  掠夺减少体力
    * @param userId 用户id
    * @param grade 等级
    * @return 返回结果
    */
   @GetMapping("/v1/userInfo/listUserPlunder/{userId}/{grade}")
   Result updateStrength(@PathVariable("userId") Long userId, @PathVariable("grade") Integer grade);

   /**
    *  技能升级
    *  消耗金币增加声望
    *  技能模块
    * @param bo 参数
    * @return 返回结果
    */
   @PostMapping("/v1/userInfo/updateInfo")
   Result updateInfo(@RequestBody UserInfoSkillBo bo);
   /**
    * 战斗获取宠物信息（内部）
    * @param userId
    */
   @GetMapping("/getPetInfo")
   Result<List<PetsDto>> getPetInfo(@RequestParam Long userId);
}
