package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.*;
import com.dkm.entity.user.SeedCollectVo;
import com.dkm.entity.vo.*;
import com.dkm.feign.entity.ReputationRankingBO;
import com.dkm.feign.entity.UserNameVo;
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
@FeignClient(value = "user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    /**
     * 查询用户所有信息
     *
     * @param id
     * @return
     */
    @GetMapping("/v1/we/chat/queryUser/{id}")
    Result<UserInfoQueryBo> queryUser(@PathVariable("id") Long id);

    /**
     * 修改增加用户声望金币
     *
     * @param increaseUserInfoBO
     * @return
     */
    @PostMapping("/v1/userInfo/increase")
    Result increaseUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO);

    /**
     * 修改减少用户声望金币
     *
     * @param increaseUserInfoBO
     * @return
     */
    @PostMapping("/v1/userInfo/cut")
    Result cutUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO);

    /**
     * 随机返回20条用户信息
     *
     * @return 返回用户信息
     */
    @GetMapping("/v1/userInfo/listUserPlunder")
    Result<List<UserPlunderBo>> listUserPlunder();

    /**
     * 掠夺减少体力
     *
     * @param userId 用户id
     * @param grade  等级
     * @return 返回结果
     */
    @GetMapping("/v1/userInfo/listUserPlunder/{userId}/{grade}")
    Result updateStrength(@PathVariable("userId") Long userId, @PathVariable("grade") Integer grade);

    /**
     * 技能升级
     * 消耗金币增加声望
     * 技能模块
     *
     * @param bo 参数
     * @return 返回结果
     */
    @PostMapping("/v1/userInfo/updateInfo")
    Result updateInfo(@RequestBody UserInfoSkillBo bo);

    /**
     * 随机返回9条用户数据
     *
     * @param userId
     * @return
     */
    @GetMapping("/v1/userInfo/listAttUser")
    Result<List<AttendantWithUserVo>> listAttUser(@RequestParam("userId") Long userId);

    /**
     * 根据用户Id集合查询所有用户头像
     *
     * @param bo
     * @return
     */
    @PostMapping("/v1/we/chat/queryAllHeardByUserId")
    Result<List<UserHeardUrlBo>> queryAllHeardByUserId(@RequestBody ParamBo bo);

    @GetMapping("/v1/userInfo/reputation/ranking")
    Result<List<ReputationRankingBO>> reputationRanking();

    /**
     * 查询用户对手信息
     *
     * @param listVo
     * @return
     */
    @PostMapping("/v1/userInfo/listOpponent")
    Result<List<OpponentVo>> listOpponent(@RequestBody ListVo listVo);

    /**
     * 返回用户昵称头像信息
     *
     * @param listVo 用户id集合
     * @return 返回用户信息
     */
    @PostMapping("/v1/userInfo/queryUserInfoAtt")
    Result<List<UserInfoAttVo>> queryUserInfoAtt(@RequestBody UserAttAllVo listVo);

    /**
     * 根据用户id批量查询用户名
     *
     * @param userIds
     * @return
     */
    @PostMapping("/v1/we/chat/query/list/user/part")
    Result<List<UserNameVo>> queryUserName(@RequestBody List<Long> userIds);

    /**
     *  收取红包和金币
     * @param seedCollectVo 参数
     * @return 返回结果
     */
    @PostMapping("/v1/userInfo/addSeedCollect")
    Result addSeedCollect(@RequestBody SeedCollectVo seedCollectVo);

}



