package by.minsk.vasilyevanatali.auction.controller;

import by.minsk.vasilyevanatali.auction.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code UserAccessFilter} specifies requests
 * available to clients with {@link Role#USER} role
 * but not guests. If a request is sent by a guest he
 * gets redirected to other page.
 * Requests accessible by {@link Role#USER} are reachable
 * by {@link Role#ADMIN} too.
 */
@WebFilter(filterName = "UserAccess",
        urlPatterns = {
                "/propose-lot", "/business-profile",
                "/change-locale", "/change-password",
                "/edit-lot", "/edit-profile", "/make-bid",
                "/add-funds", "/withdraw-funds",
                "/profile", "/delete-lot",
                "/sign-out"
})
public class UserAccessFilter implements Filter {

    private static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var session = req.getSession(false);
        if (session != null) {
            var role = session.getAttribute("role");
            if (role != null) {
                if ("ADMIN".equals(role.toString()) || "USER".equals(role.toString())) {
                    logger.debug("doFilter");
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        logger.debug("Redirect");
        resp.sendRedirect("welcome");
    }

    @Override
    public void destroy() {

    }
}
