package com.dkm.shop;

import com.dkm.aop.beans.Aspect;
import com.dkm.config.RedisConfig;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.utils.IdGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author qf
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableTransactionManagement
@EnableFeignClients
@MapperScan("com.dkm.shop.*.dao")
public class GameShopApplication extends Aspect {

   public static void main(String[] args) {
      SpringApplication.run(GameShopApplication.class, args);
   }


   /**
    * 打包
    * @param builder
    * @return
    */
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(GameShopApplication.class);
   }

   @Bean
   public LocalUser localUser () {
      return new LocalUser();
   }

   @Bean
   public IdGenerator getIdGenerator() {
      return new IdGenerator();
   }

   @Bean
   public RedisConfig getRedisConfig() {
      return new RedisConfig();
   }

}
