package com.gordeev.campaignbooking.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class MDCRequestFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();
        MDC.put("requestId", uuid);
        logger.info("MDCRequestFilter is started with uuid: {}", uuid);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("doFilter of MDCRequestFilter is done.");
        } finally {
            MDC.remove("requestId");
            logger.info("MDCRequestId is removed.");
        }
    }

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void destroy() {

    }
}