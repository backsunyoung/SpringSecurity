package com.example.securityexam.beforesecurity;


import jakarta.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<BsyFilter> bsyFilter(){


        FilterRegistrationBean<BsyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BsyFilter());
        registrationBean.addUrlPatterns("/test/*");  // 특정한거에만 필터 수행하고싶다면
        registrationBean.setOrder(2);
        return registrationBean;

    }
}
