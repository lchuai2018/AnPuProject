package com.example.anpuservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {

    @Autowired
    private WebConfigurer webConfigurer;

    @Bean
    public FilterRegistrationBean crosFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.setFilter(webConfigurer.corsFilter());
        // CORS解决跨域问题，先于shiro加载,order控制加载顺序，数小的先执行
        registration.setOrder(Integer.MAX_VALUE - 2);
        registration.addUrlPatterns("/*");
        registration.setName("crosFilter");
        return registration;
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
