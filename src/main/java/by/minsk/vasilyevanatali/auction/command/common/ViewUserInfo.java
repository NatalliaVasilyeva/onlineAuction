package by.minsk.vasilyevanatali.auction.command.common;


import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ViewUserInfo implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ViewUserInfo.class);

    @Override
    public String execute(HttpServletRequest request) {
      //  String viewPage = ViewPage.LOGIN_PAGE.getPath();
        String page = "welcome";
        HttpSession session = request.getSession();
        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = (User) session.getAttribute("user");
        Optional<User> optionalUser;
        if (null != user) {
            try {
                optionalUser = userService.findUserById(user.getId());
                if (optionalUser.isPresent()) {
                    session.setAttribute("user", optionalUser.get());
                    LOGGER.debug("Perform view user information");
            //        viewPage = ViewPage.VIEW_USER_INFO.getPath();
                    page = "profile";
                            }

            } catch (ServiceException e) {
                LOGGER.error(e);
                request.setAttribute("error_message", "Service error. Try again later.");
            }
        }
        return page;
       // var gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

      //  return new Gson().toJson(optionalUser.get());

       // return gson.toJson(optionalUser.get());
    }
}
