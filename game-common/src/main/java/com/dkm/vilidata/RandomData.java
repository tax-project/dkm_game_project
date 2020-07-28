package com.dkm.vilidata;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Component
public class RandomData {


   /**
    *   ---去重的随机数
    * @param size 总条数
    * @param count 随机抽取的条数
    * @return
    */
   public Set<Integer> getList (Integer size, Integer count) {
      Set<Integer> set = getSet(size,count);
      if (set.size() < count) {
         while (true) {
            Set<Integer> set1 = getSet(size,count);
            if (set1.size() == count) {
               return set1;
            }

            if (set1.size() > count) {
               throw new ApplicationException(CodeType.SERVICE_ERROR, "获取题目失败");
            }
         }
      }
      return set;
   }

   private Set<Integer> getSet (Integer size, Integer count) {
      Set<Integer> set = new HashSet<Integer>();
      for (int i = 0; i < count; i++) {
         int random = (int) (Math.random()*size);
         set.add(random);
      }
      return set;
   }
}
