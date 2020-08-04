package com.dkm.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * @author OpenE
 */
@Configuration
class GlobalCorsConfig {
    private fun addCorsConfig(): CorsConfiguration {
        return CorsConfiguration().run {
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
            this
        }
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", addCorsConfig())
        return CorsFilter(source)
    }
}