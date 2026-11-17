package com.yihe.framework.ehr;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Archive entry pages must revalidate so a long-lived tab can discover new UI assets. */
@Component
public class ArchiveResourceCacheFilter extends OncePerRequestFilter {
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
            throws ServletException,IOException {
        String path=request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith("/assets/health-archive/")&&(path.endsWith(".html")||path.endsWith("/live.js")||path.endsWith("/live.css"))){
            response.setHeader("Cache-Control","no-cache, max-age=0, must-revalidate");
            response.setHeader("X-Archive-UI-Version","report-remove-20261115");
        }
        chain.doFilter(request,response);
    }
}
