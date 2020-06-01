package com.dkm.plunder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户产出表
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user_produce")
public class UserProduce extends Model<UserProduce> {

   /**
    *  自增主键
    *  其他表不需要用
    */
   private Integer id;

   /**
    * 用户id
    */
   private Long userId;

   /**
    * 产出表id
    */
   private Long produceId;
}
