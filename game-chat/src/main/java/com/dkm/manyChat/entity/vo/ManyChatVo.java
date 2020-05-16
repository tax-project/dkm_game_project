package com.dkm.manyChat.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/16
 * @vesion 1.0
 **/
@Data
public class ManyChatVo {

   /**
    * 群聊名字
    */
   private String manyName;

   /**
    * 用户id集合
    */
   private List<Long> list;
}
