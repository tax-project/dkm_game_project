//package com.dkm.feign;
//
//import com.dkm.data.Result;
//import com.dkm.feign.fallback.UserFeignClientFallback;
//import com.dkm.goldMine.bean.ao.UserInfoBO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//@FeignClient(value = "user", fallback = UserFeignClientFallback.class)
//public interface UserFeignClient {
//
//
//    @PostMapping("/v1/userInfo/increase")
//    Result<UserInfoBO> updateGold(UserInfoBO userInfoBO);
//
//}