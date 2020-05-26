package com.dkm.knapsack.controller;



import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@RequestMapping("/dkm/tbEquipmentKnapsack")
@RestController
@Api(description = "用户装备的接口文档")
public class TbEquipmentKnapsackController {
	@Autowired
    ITbEquipmentKnapsackService tbEquipmentKnapsackService;

    /**
     * 增加用户装备的接口文档
     * @param tbEquipmentKnapsack
     */
    @ApiOperation(value = "增加用户装备接口  input都是需要传递的参数",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3为食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addTbEquipmentKnapsack")
    @CrossOrigin
    //@CheckToken
    public void addTbEquipmentKnapsack(@RequestBody TbEquipmentKnapsack tbEquipmentKnapsack){
        tbEquipmentKnapsackService.addTbEquipmentKnapsack(tbEquipmentKnapsack);
    }

    /**
     * 批量增加装备到背包的接口
     * @param equipmentId 装备主键
     */
    @ApiOperation(value = "批量增加装备到背包的接口  input都是需要传递的参数",notes = "成功返回成功")
    @GetMapping("/addTbEquipmentKnapsackTwo")
    @CrossOrigin
    @CheckToken
    public void addTbEquipmentKnapsackTwo(String equipmentId){
        tbEquipmentKnapsackService.addTbEquipmentKnapsackTwo(equipmentId);
    }

    /**
     * 查询出当前用户id的背包容量
     * @return
     */
    @ApiOperation(value = "查询出当前用户id的当前背包的容量  input都是需要传递的参数",notes = "成功返回成功")
    @GetMapping("/selectCount")
    @CrossOrigin
    @CheckToken
    public int selectCount(){
        return tbEquipmentKnapsackService.selectCount();
    }

    /**
     * 查登录用户的装备
     * @return
     */
    @ApiOperation(value = "查当前登录用户的装备",notes = "成功返回数据 反则为空 data为装备 dataOne为食物")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备的外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3为食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodName",value = "食物名字"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodUrl",value = "食物图片地址"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodGold",value = "食物售价")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectUserId")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> selectUserId(){
        Map<String,Object> map=new HashMap<>();
        List<TbEquipmentKnapsackVo> list=tbEquipmentKnapsackService.selectUserId();
        List<TbEquipmentKnapsackVo> listOne=tbEquipmentKnapsackService.selectFoodId();
        map.put("data",list);
        map.put("dataOne",listOne);

        if(!StringUtils.isEmpty(list) || !StringUtils.isEmpty(listOne)){
            return map;
        }else{
            return map;
        }
    }

    @ApiOperation(value = "后端专用 根据用户id查装备",notes = "成功返回数据 反则为空 ")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备的外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3为食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodName",value = "食物名字"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodUrl",value = "食物图片地址"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodGold",value = "食物售价")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectTwo/{userId}")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentKnapsackVo> selectTwo(@PathVariable("userId") String userId){
        return tbEquipmentKnapsackService.selectUserIdAndFoodId(Long.valueOf(userId));
    }
    /**
     * 查登录用户的装备
     * @return
     */
    @ApiOperation(value = "后端专用",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备的外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3为食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodName",value = "食物名字"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "foodUrl",value = "食物图片地址"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodGold",value = "食物售价")
    })
    @GetMapping("/selectUserIdAndFoodId")
    @CrossOrigin
    //@CheckToken
    public  List<TbEquipmentKnapsackVo> selectUserIdAndFoodId(Long userId){
        List<TbEquipmentKnapsackVo> listOne=tbEquipmentKnapsackService.selectUserIdAndFoodId(userId);

        return listOne;
    }

    /**
     * 出售装备的接口
     * @param tekId  用户装备表的主键
     * @param tekMoney 装备的金额
     */
    @ApiOperation(value = "出售装备的接口  input都是需要传递的参数",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键",required = true),
            @ApiImplicitParam(paramType = "query",name = "tekMoney",value = "道具50金币 穿戴品只有5金币",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/deleteTbEquipment")
    @CrossOrigin
    //@CheckToken
    public void deleteTbEquipment(Long tekId,Integer tekMoney){
        tbEquipmentKnapsackService.deleteTbEquipment(tekId,tekMoney);
    }

    /**
     *  点击装备查看是否已经装备过
     * @param equipmentId 装备主键
     * @return
     */
    @ApiOperation(value = "点击装备看是否已经装备上了",notes = "没有装备上返回code=2 且返回Datathree 为此装备详情，装备上返回code=3 并且返回数据dataOne是查询为装备上的装备数据   dataTwo查询已经装备上了的装备数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/findById")
    @CrossOrigin
    //@CheckToken
    public Map<String,Object> findById(Long equipmentId){
        Map<String,Object> map=tbEquipmentKnapsackService.findById(equipmentId);
        return map;
    }

    @ApiOperation(value = "卸下装备的接口",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/updateSell")
    @CrossOrigin
    //@CheckToken
    public void updateSell(@RequestParam("tekId") Long tekId){
        tbEquipmentKnapsackService.updateSell(tekId);
    }

    /**
     * 点击装备 首先查一下有没有相同的装备装上了 加入装备上了给他替换 没有则给它修
     * @param tekId
     */
    @ApiOperation(value = "点击装备按钮的立即把装备装上的接口",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备 "),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/updateTekId")
    @CrossOrigin
    //@CheckToken
    public void updateTekId(Long tekId){
        tbEquipmentKnapsackService.updateTekId(tekId);
    }

    /**
     * 点击使用道具和食物的修改数量接口
     * @param tekId
     * @param foodNumber
     */
    @ApiOperation(value = "点击使用道具和食物的修改数量接口",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tekId",value = "用户背包装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekMoney",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekSell",value = "我的装备 1为装备上了 2为背包装备 "),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekDaoju",value = "1为穿戴品 2为道具 3食物"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tekIsva",value = "1为没有被卖 0被卖了"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "foodId",value = "食物主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "foodNumber",value = "食物数量",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/updateIsva")
    @CrossOrigin
    //@CheckToken
    public void updateIsva(Long tekId,Integer foodNumber){
        tbEquipmentKnapsackService.updateIsva(tekId,foodNumber);
    }

    @ApiOperation(value = "后台专用接口",notes = "成功返回成功")
    @CrossOrigin
    //@CheckToken
    @GetMapping("/userCenter")
    public List<TbEquipmentKnapsackVo> userCenter(){
        List<TbEquipmentKnapsackVo> list=tbEquipmentKnapsackService.selectUserId();
        return list;
    }

}
