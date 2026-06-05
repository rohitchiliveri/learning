package net.javaguides.springboot.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ClientDetectionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ClientDetectionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String userAgent = httpRequest.getHeader("User-Agent");
        String postmanToken = httpRequest.getHeader("Postman-Token");
        String referer = httpRequest.getHeader("Referer");

        System.out.println(httpRequest.getMethod());        
        
        // 1. Check for Postman
        if ((userAgent != null && userAgent.contains("Postman")) || postmanToken != null) {
            logger.info("[TRAFFIC] Source: POSTMAN | Path: {}", httpRequest.getRequestURI());
        }
        
        // 2. Check for Swagger UI
        else if (referer != null && referer.contains("swagger-ui")) {
            logger.info("[TRAFFIC] Source: SWAGGER UI | Path: {} | Referer: {}", 
                    httpRequest.getRequestURI(), referer);
        }
        
        // 3. Normal browser or alternative client
        else {
            logger.debug("[TRAFFIC] Source: Other Browser/Client | Path: {}", httpRequest.getRequestURI());
        }

        chain.doFilter(request, response);
    }
}
