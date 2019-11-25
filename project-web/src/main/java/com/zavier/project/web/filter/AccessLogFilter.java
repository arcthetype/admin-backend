package com.zavier.project.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class AccessLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String requestURI = servletRequest.getRequestURI();
            long start = System.currentTimeMillis();
            chain.doFilter(request, response);
            log.info("{} cost time:{} ms", requestURI, (System.currentTimeMillis() - start));
        }

    }
}
