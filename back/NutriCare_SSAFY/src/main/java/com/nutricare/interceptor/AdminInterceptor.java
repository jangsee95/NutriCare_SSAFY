package com.nutricare.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String role = (String) request.getAttribute("role");

        if (role == null || !role.equals("ADMIN")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403에러 줄거임
            return false;
        }

        return true;
    }
}
