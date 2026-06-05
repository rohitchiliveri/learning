package net.javaguides.springboot.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class PostmanDetectorFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        String userAgent = req.getHeader("User-Agent");

        if (userAgent != null && userAgent.toLowerCase().contains("postman")) {
            System.out.println("[WARN] Postman tool access on URI: " + req.getRequestURI());
        }

        chain.doFilter(request, response);
    }
}
