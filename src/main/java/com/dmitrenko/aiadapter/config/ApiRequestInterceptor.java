package com.dmitrenko.aiadapter.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.dmitrenko.aiadapter.util.CommonConstant.REQUEST_ID;
import static com.dmitrenko.aiadapter.util.CommonConstant.REQUEST_ID_HEADER;

@Slf4j
public class ApiRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(REQUEST_ID, request.getHeader(REQUEST_ID_HEADER));
        log.debug(String.format("Received request to endpoint [%s]", request.getRequestURL()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}