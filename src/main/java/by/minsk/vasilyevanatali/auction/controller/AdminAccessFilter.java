package by.minsk.vasilyevanatali.auction.controller;

import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code AdminAccessFilter} specifies requests exclusively
 * available to clients with {@link Role#ADMIN} role.
 * If a request is sent by a client with other role he
 * gets redirected to other page.
 */
@WebFilter(filterName = "AdminAccess",
        urlPatterns = {
                "/block-user", "/unblock-user", "/approve-lot", "/unapprove-lot",
                "/manage-auctions", "/manage-requests", "/manage-users",
                 "/remove-user"
})
public class AdminAccessFilter implements Filter {

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
            User user = (User)session.getAttribute("user");
            if (user.getRole() != null && "ADMIN".equals(user.getRole())) {
                logger.debug("doFilter");
                chain.doFilter(request, response);
                return;
            }
        }
        logger.debug("Redirect");
        resp.sendRedirect("/welcome");
    }

    @Override
    public void destroy() {
    }
}
