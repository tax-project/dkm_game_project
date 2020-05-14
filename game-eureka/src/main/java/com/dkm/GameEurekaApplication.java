package com.dkm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author qf
 */
@SpringBootApplication
@EnableEurekaServer
public class GameEurekaApplication extends SpringBootServletInitializer {

   public static void main(String[] args) {
      SpringApplication.run(GameEurekaApplication.class, args);
   }



   /**
    * 打包
    * @param builder
    * @return
    */
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(GameEurekaApplication.class);
   }

}
