package com.dkm;


import com.dkm.backpack.entity.bo.AddBackpackItemBO;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameBaseApplicationTests {
   @Test
   public void contextLoads() {
      AddBackpackItemBO addBackpackItemBO = new AddBackpackItemBO();
      System.out.println(addBackpackItemBO);
   }

}
