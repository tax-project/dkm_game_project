package com.dkm.pay.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/15
 * @vesion 1.0
 **/
@Data
public class PayResultVo {

   private int code;

   private String msg;

   private Object data;
}
