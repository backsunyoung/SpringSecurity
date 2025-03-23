package com.example.securityexam.beforesecurity;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
@Order(1)
public class UserFilter implements Filter {

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info("");
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            log.info("유저필터 두필터 실행전");
// 스레드 로컬에 저장하고 싶은 객체가 존재한다면? 복잡한 로직들이 실행되서 값을 가져오는 경우 존재
            String name = servletRequest.getParameter("name");

            // 스레드 로컬에 저장하고 싶은 객체가 존재한다면?
            UserContext.setUser(new User(name));

            filterChain.doFilter(servletRequest, servletResponse);
            log.info("유저필터 두필터 실행후");
        }finally {
            UserContext.clear();
        }
    }

    private User extractUserFromRequest(ServletRequest request){

        //복잡한 로직을 통해 사용자의 정보를 추출한다면
        String name = request.getParameter("name");
        User user = new User(name);

        return new User(name);
    }
}
