package com.example.securityexam.jwtexam.test.jwt.util.exception;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
// 시큐리티에서 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할때 동작하는 인터페이스
    //사요아가 인증되ㅣ지 않으ㅏㅅ을때 어떠헤게 응답할지를 정의해주기 위해 사용
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");


        if(isRestRequest(request)){
            //rest로 요청이 들어왔을때 수행할 코드
            handleRestResponse(request, response, exception);

        } else {
            // page로 옃청이 들어왓ㅇ를때 수행할 코드
            handlePageResponse(request, response, exception);

        }
    }


    private void setResponse(HttpServletResponse response, JwtExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        HashMap<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("message", exceptionCode.getMessage());
        errorInfo.put("code", exceptionCode.getCode());
        Gson gson = new Gson();
        String responseJson = gson.toJson(errorInfo);
        response.getWriter().print(responseJson);
    }



    private void handleRestResponse(HttpServletRequest request, HttpServletResponse response, String exception) throws IOException {
        log.error("Rest Request - Commence Get Exception : {}", exception);

        if (exception != null) {
            if (exception.equals(JwtExceptionCode.INVALID_TOKEN.getCode())) {
                log.error("entry point >> invalid token");
                setResponse(response, JwtExceptionCode.INVALID_TOKEN);
            } else if (exception.equals(JwtExceptionCode.EXPIRED_TOKEN.getCode())) {
                log.error("entry point >> expired token");
                setResponse(response, JwtExceptionCode.EXPIRED_TOKEN);
            } else if (exception.equals(JwtExceptionCode.UNSUPPORTED_TOKEN.getCode())) {
                log.error("entry point >> unsupported token");
                setResponse(response, JwtExceptionCode.UNSUPPORTED_TOKEN);
            } else if (exception.equals(JwtExceptionCode.NOT_FOUND_TOKEN.getCode())) {
                log.error("entry point >> not found token");
                setResponse(response, JwtExceptionCode.NOT_FOUND_TOKEN);
            } else {
                setResponse(response, JwtExceptionCode.UNKNOWN_ERROR);
            }
        } else {
            setResponse(response, JwtExceptionCode.UNKNOWN_ERROR);
        }
    }




// 페이지 요청중 예외가 발생했다면 로그남기고 무조건 /lofingform으로 리다이렉트
    private void handlePageResponse(HttpServletRequest request, HttpServletResponse response, String exception) throws IOException {
        log.error("Page Request - Commence Get Exception : {}", exception);

        if (exception != null) {
            // 추가적인 페이지 요청에 대한 예외 처리 로직을 여기에 추가할 수 있습니다.
        }

        response.sendRedirect("/loginform");
    }



    private boolean isRestRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With"); // 데이터를 요청하는건지, ajax를 요청하면 데이터란거
        return "XMLHttpRequest".equals(requestedWithHeader) || request.getRequestURI().startsWith("/api/");  //  rest json인거 라고 약속
    }


}
