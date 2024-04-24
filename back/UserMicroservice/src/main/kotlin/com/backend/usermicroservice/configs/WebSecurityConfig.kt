//package com.bit.back.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpMethod
//import org.springframework.web.servlet.config.annotation.CorsRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//
//@Configuration
//class WebSecurityConfig {
//    @Bean
//    fun corsConfig(): WebMvcConfigurer {
//        return object : WebMvcConfigurer {
//            override fun addCorsMappings(registry: CorsRegistry) {
//                registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:3000")
//                    .allowedMethods(
//                        HttpMethod.GET.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.DELETE.name()
//                    )
//                    .allowedHeaders(
//                        HttpHeaders.CONTENT_TYPE,
//                        HttpHeaders.AUTHORIZATION
//                    )
//            }
//        }
//    }
//}

// NOTE: FOR WEB ONLY