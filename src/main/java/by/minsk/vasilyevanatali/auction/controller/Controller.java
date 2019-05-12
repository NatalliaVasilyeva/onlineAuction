package by.minsk.vasilyevanatali.auction.controller;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.minsk.vasilyevanatali.auction.controller.MappingUtil.unfoldExceptionStackTrace;


/**
 * The application is built using single-servlet model. {@code Controller} is the servlet.
 * {@code Controller} relays requests to a specific command in the command layer.
 */
@WebServlet(name = "Controller",
                urlPatterns = {"/welcome", "/sign-up", "/sign-in", "/sign-out", "/profile", "/edit-profile", "/change-language", "/settings", "/change-password", "/business-profile"
                        //"",
//        "/sign-out", "/profile", "/edit-profile", "/manage-auctions",
//        "/manage-requests", "/manage-users", "/block-user", "/unblock-user",
//        "/approve-lot", "/unapprove-lot",  "/remove-auction", "/remove-user",
//       "/create-auction", "/edit-auction",
//        "/business-profile", "/edit-lot", "/remove-lot",
//        "/propose-lot", "/make-bid", "/add-funds", "/withdraw-funds"
}
        )
public class Controller extends HttpServlet {
    ConnectionPool connectionPool;

   // private static Logger logger = LogManager.getLogger();

//    @Override
//    public void init() {
//
//        connectionPool=ConnectionPool.getInstance();
//    }

    /**
     * Forwards the JSP page corresponding to the request.
     *
     * @param req   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet.
     * @param resp  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client.
     * @throws ServletException
     *              if {@link javax.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse)} does.
     * @throws IOException
     *              if either {@link javax.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse)}
     *              or {@link MappingUtil#getUri(HttpServletRequest)} does.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = MappingUtil.getUri(req);
        String page = MappingUtil.mapToJsp(uri);
    //    logger.debug("GET request uri: " + uri);
        System.out.println("page= " + page);
        req.getRequestDispatcher(page).forward(req, resp);
         }

    /**
     * All POST-methods are triggered via AJAX call. Corresponding command gets executed.
     *
     * @param req   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet.
     * @param resp  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client.
     * @throws IOException if any of {@link ServletResponse#getWriter()}
     *              {@link HttpServletResponse#sendError(int, String)}
     *              {@link MappingUtil#getUri(HttpServletRequest)} does.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = MappingUtil.getUri(req);
        System.out.println(uri);
        Command command = MappingUtil.mapToCommand(uri);
         //   logger.debug("POST request uri: " + uri);
        try {
            String page = MappingUtil.mapToJsp(command.execute(req));

            System.out.println("page= " + page);
            req.getRequestDispatcher(page).forward(req, resp);
     //       String json = command.execute(req);
   //         logger.debug("JSON retrieved: " + json);
       //     resp.getWriter().write(json);
//        } catch (CommandException e) {
//            resp.sendError(500, unfoldExceptionStackTrace(e));
        } catch (RuntimeException | ServletException e) {
            resp.sendError(500, unfoldExceptionStackTrace(e));
        }
    }

//    /**
//     * Releases all database resources.
//     */
//    @Override
//    public void destroy() {
//        ConnectionPool.getInstance().destroyConnectionPool();
//    }
}
