package com.utfpr.Projeto_Sistemas.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;




    @Slf4j
    @Component
    @Order(1) // garante que roda antes do SecurityFilter
    public class RequestLoggingFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            // wrappers para ler o corpo
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

            long start = System.currentTimeMillis();

            filterChain.doFilter(wrappedRequest, wrappedResponse);

            long duration = System.currentTimeMillis() - start;

            String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

            log.info(" [{}] {} | {} ms", request.getMethod(), request.getRequestURI(), duration);
            logHeaders(request);
            log.info("Corpo da requisição: {}", body.isBlank() ? "(vazio)" : body);
            log.info(" Resposta: status={} body={}", wrappedResponse.getStatus(), responseBody);

            wrappedResponse.copyBodyToResponse();
        }

        private void logHeaders(HttpServletRequest request) {
            log.info("Headers:");
            for (String header : Collections.list(request.getHeaderNames())) {
                log.info("  {}: {}", header, request.getHeader(header));
            }
        }
    }


