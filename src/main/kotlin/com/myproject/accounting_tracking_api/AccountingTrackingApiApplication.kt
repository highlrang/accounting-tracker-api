package com.myproject.accounting_tracking_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableJpaAuditing
@SpringBootApplication
class AccountingTrackingApiApplication {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AccountingTrackingApiApplication>(*args)
}
