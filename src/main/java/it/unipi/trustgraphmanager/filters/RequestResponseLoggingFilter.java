package it.unipi.trustgraphmanager.filters;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Component
@Order(1)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    private final static String START_REQUEST = "Start request {} {}";

    private final static String END_REQUEST = "End request {} {}, elapsed: {}";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info(START_REQUEST, httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        final Date start = new Date();
        chain.doFilter(request, response);
        log.info(END_REQUEST, httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                new Date().getTime() - start.getTime());
    }
}
