package com.dkm.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author qf
 * @date 2020/6/8
 * @vesion 1.0
 **/
@Slf4j
public class JavaBeanUtils {

   private JavaBeanUtils(){}


   public static void copyResource (Objects source, Objects target) {

      if (target == null || source == null) {
         log.info("copy的两个Bean不能为空..");
         return;
      }

      BeanUtils.copyProperties(source, target);
   }
}
