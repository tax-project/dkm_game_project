package com.dkm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author qf
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GameWebsocketApplication extends SpringBootServletInitializer {

   public static void main(String[] args) {
      SpringApplication.run(GameWebsocketApplication.class, args);
   }


   /**
    * 打包
    * @param builder
    * @return
    */
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(GameWebsocketApplication.class);
   }

}
