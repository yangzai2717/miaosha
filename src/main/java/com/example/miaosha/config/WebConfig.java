package com.example.miaosha.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/8 15:56
 * @Description:
 */

@Configuration
public class WebConfig extends WebMvcConfigurationSupport{

    @Autowired
    UserArgumentResolver userArgumentResolver;

    /**
     * 这个方法有什么用呢，我们controller的请求方法中的 model requst response 等参数，那么这些参数是怎么来的，谁赋的值，就是这个方法
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
