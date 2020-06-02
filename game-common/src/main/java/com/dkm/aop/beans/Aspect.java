package com.dkm.aop.beans;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.dkm.config.RedisConfig;
import com.dkm.exception.ApplicationException;
import com.dkm.handle.ApplicationAdviceHandle;
import com.dkm.handle.GlobalResponseHandler;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.utils.IdGenerator;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * @author qf
 * @date 2020/3/6
 * @vesion 1.0
 **/
public abstract class Aspect extends SpringBootServletInitializer {


   /**
    * 多表分页的bean
    * @return
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
      return new PaginationInterceptor();
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

   @Bean
   public GlobalResponseHandler getGlobalResponseHandler() {
      return new GlobalResponseHandler();
   }

   @Bean
   public ApplicationAdviceHandle getApplicationAdviceHandle() {
      return new ApplicationAdviceHandle();
   }


}
