package com.dkm.config

import com.dkm.admin.services.IAdminUserLoginService
import com.dkm.config.annon.CheckAdminPermission
import com.dkm.constanct.CodeType
import com.dkm.exception.ApplicationException
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AdminTokenInterceptor : HandlerInterceptorAdapter() {
    @Resource
    private lateinit var userLoginService: IAdminUserLoginService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 如果不是映射到方法直接通过
        if (handler !is HandlerMethod) {
            return true
        }
        val method = handler.method
        if (method.isAnnotationPresent(CheckAdminPermission::class.java)) {
            val token = request.getHeader("token") ?: request.getHeader("TOKEN")
            ?: throw ApplicationException(CodeType.TOKENNULL_ERROR)
            return userLoginService.checkToken(token)
        }
        return true
    }
}