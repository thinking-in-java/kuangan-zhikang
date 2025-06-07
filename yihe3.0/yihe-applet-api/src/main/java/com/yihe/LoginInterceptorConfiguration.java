package com.yihe;

import com.yihe.common.redis.RedisService;
import com.yihe.framework.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截器配置
 */
@Configuration
public class LoginInterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    RedisService redisService;
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截器链
        // addPathPatterns用于添加拦截规则
        // excludePathPatterns用户排除拦截
        //将已经写好的重复提交拦截器添加到mvc的拦截器组中
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(new LoginInterceptor(redisService))
                .addPathPatterns("/**") //对来自/** 全路径请求进行拦截
                .excludePathPatterns(
                        "/**/login/*",
                        "/**/sysUser/*",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/swagger*/**",
                        "/**/api-docs/**",
                        "/**/error/**",
                        "/**/getuserphonenumber",
                        "/**/basicRegistrationInformation",
                        "/**/sendSms",
                        "/**/mobileNumberLogin",
                        "/**/chatrecord/upload",
                        "/**/selectGrade",
                        "/**/login",
                        "/**/doDMRisk",
                        "/**/doDiseaseCategory",
                        "/**/checkReport",
                        "/**/upload",
                        "/**/sendVerificationCode",
                        "/**/activationUser",
                        "/**/gainsessionKey",
                        "/**/runData",
                        "/**/selectByenergy",
                        "/**/upload",
                        "/**/newRegControllerApi"

                );
    }

}
