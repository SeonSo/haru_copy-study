package com.project1.haruco.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setContentType("application/json;charset=UTH-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{\"message\" : \"" +"유효하지 않은 "+"\", \"code\" : \"" + HttpServletResponse.SC_UNAUTHORIZED +"}");
    }
}