package com.dkm.config

import com.dkm.admin.entities.vo.UserLoginVo
import com.dkm.constanct.CodeType
import com.dkm.exception.ApplicationException
import com.dkm.feign.AnotherUserFeignClient
import feign.Feign
import feign.RequestTemplate
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource

@Configuration
class NoAuthFeignConfiguration {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Resource
    private lateinit var userFeignClient: AnotherUserFeignClient
    private val token by lazy {
        val login = userFeignClient.login(UserLoginVo("admin", "admin"))
        val userLoginStatusVo = login.data
                ?: throw ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "请求admin用户token失败！")
        userLoginStatusVo.token
    }

    @Bean
    fun feignBuilder(): Feign.Builder {
        return Feign.builder().requestInterceptor { requestTemplate: RequestTemplate? ->
            requestTemplate?.header("TOKEN", token)
            logger.info("添加 ADMIN TOKEN ：{}", token)
        }
    }
}