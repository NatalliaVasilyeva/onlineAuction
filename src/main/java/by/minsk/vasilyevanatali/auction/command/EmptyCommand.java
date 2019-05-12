package by.minsk.vasilyevanatali.auction.command;
import by.minsk.vasilyevanatali.auction.command.Command;

import javax.servlet.http.HttpServletRequest;

//package by.minsk.vasilyevanatali.auction.command;
//
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
public class EmptyCommand implements Command {
//    private static final Logger LOGGER = LogManager.getLogger(EmptyCommand.class);
//
//    @Override
//    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.debug("Perform empty command");
//        String page = ViewPage.LOGIN_PAGE.getPath();
//        HttpSession session = request.getSession();
//        if (session.getAttribute("user") != null && session.getAttribute("previousPage") != null) {
//            page = (String) session.getAttribute("previousPage");
//        }
//        return CommandResult.redirect("controller?command=startLogin");
//    }

@Override
public String execute(HttpServletRequest req) {
        return "index.jsp";
        }
}

