package com.dkm.utils;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author qf
 * @date 2020/7/3
 * @vesion 1.0
 **/
public class TransformationUtils<T> {


   /**
    *  转换--去重
    *  将list-->set-->list
    * @return
    */
   public static <T> List<T> transformation (List<T> list) {

      List<T> result = new ArrayList<>();

      Set<T> set = new HashSet<>();
      for (T t : list) {
         set.add(t);
      }

      for (T t : set) {
         result.add(t);
      }

      return result;
   }

}
