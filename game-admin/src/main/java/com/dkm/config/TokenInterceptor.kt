package com.dkm.config

import com.dkm.admin.service.IAdminUserLoginService
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * admin token 验证
 *
 * @author OpenE
 */

class TokenInterceptor : HandlerInterceptorAdapter() {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Resource
    private lateinit var userLoginService: IAdminUserLoginService

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info(request.requestURI)
        val token = request.getHeader("TOKEN")
        if (token != null) {
            if (userLoginService.checkToken(token)) {
                return true
            }
        }

        response.writer.write("{\n" +
                "  \"code\": 4001,\n" +
                "  \"msg\": \"TOKEN 错误！\",}")
        response.addHeader("Content-Type", "application/json;charset=utf-8")
        return false

        //进行逻辑判断，如果ok就返回true，不行就返回false，返回false就不会处理请求
    }
}