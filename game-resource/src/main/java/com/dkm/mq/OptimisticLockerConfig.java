package com.dkm.mq;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Mybatis Plus 的乐观锁插件
 *
 * @author OpenE
 * @version 1.0
 * @date 2020年7月15日
 */
@Component
public class OptimisticLockerConfig {

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
