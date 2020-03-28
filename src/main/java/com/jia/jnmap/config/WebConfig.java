package com.jia.jnmap.config;

import com.jia.jnmap.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器，拦截所有请求
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

}