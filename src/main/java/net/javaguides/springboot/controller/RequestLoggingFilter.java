package net.javaguides.springboot.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String userAgent = httpRequest.getHeader("User-Agent");
        String postmanToken = httpRequest.getHeader("Postman-Token");

        // Check if headers match Postman signatures
        if ((userAgent != null && userAgent.contains("Postman")) || postmanToken != null) {
            logger.info("Postman request detected! Path: {} | User-Agent: {}", 
                    httpRequest.getRequestURI(), userAgent);
        }

        chain.doFilter(request, response);
    }
}
