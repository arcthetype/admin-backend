package com.zavier.project.web.config;

import com.zavier.project.web.filter.AccessLogFilter;
import com.zavier.project.web.interceptor.AdminInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AdminInterceptor permissionInterceptor;

    public WebMvcConfig(AdminInterceptor permissionInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "OPTIONS", "PUT", "DELETE")
                .allowedHeaders("access-control-allow-origin", "access-control-allow-headers", "authorization", "x-requested-with", "content-type")
                .exposedHeaders("Content-Length", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Content-Type")
                .allowCredentials(true)
                // 20天（1728000秒）
                .maxAge(172800);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<AccessLogFilter> registrationBean = new FilterRegistrationBean<>(new AccessLogFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("acccessLogFilter");
        return registrationBean;
    }
}
