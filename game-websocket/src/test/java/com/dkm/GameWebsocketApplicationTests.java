package com.dkm;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;

@SpringBootTest
public class GameWebsocketApplicationTests {

   @Test
   public void contextLoads() {

      String let = "12345";
      byte[] bytes = let.getBytes();
      ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

      System.out.println(bytes);
   }

}
