package com.apisign.demo.config;

import com.apisign.demo.interceptor.SignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @desc: mvc config
 * @author: liuhongdi
 * @date: 2020-07-01 11:40
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DefaultMvcConfig implements WebMvcConfigurer {

    @Resource
    private SignInterceptor signInterceptor;

    /**
     * 添加Interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor)
                .addPathPatterns("/**")                    //所有请求都需要进行报文签名sign
                .excludePathPatterns("/html/*","/js/*");   //排除html/js目录
    }

}