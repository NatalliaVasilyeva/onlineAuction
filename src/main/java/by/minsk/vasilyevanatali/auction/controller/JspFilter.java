package by.minsk.vasilyevanatali.auction.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code AdminAccessFilter} denies access to specified pages if
 * they are accessed via file location.
 */
@WebFilter(filterName = "JspFilter", urlPatterns = {"/WEB-INF/*"})
public class JspFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void destroy() {

    }
}
