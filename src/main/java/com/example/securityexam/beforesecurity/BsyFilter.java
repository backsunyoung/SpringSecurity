package com.example.securityexam.beforesecurity;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Order(2)
@Slf4j
//@Component
// @WebFilter(urlPatterns = "/*")
//@WebFilter(urlPatterns = "/test/*")
public class BsyFilter implements Filter {
    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
        log.info("BsyFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("BsyFilter doFilter() 실행전"+Thread.currentThread().getName());
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("BsyFilter doFilter() 실행후"+Thread.currentThread().getName());
    }

    @Override
    public void destroy() {
        log.info("BsyFilter destroy()");
    }
}
