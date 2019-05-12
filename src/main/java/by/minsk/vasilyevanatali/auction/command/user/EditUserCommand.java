package by.minsk.vasilyevanatali.auction.command.user;


import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.EmailParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.LoginParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.PasswordParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.StringParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditUserCommand.class);
    // private CommandProvider commandProvider = CommandProvider.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("Perform edit user information");
        UserService userService = ServiceFactory.getInstance().getUserService();
        //  String nextPage = ViewPage.LOGIN_PAGE.getPath();
        String page = "welcome";
        HttpSession session = request.getSession();

        User userForUpdate = (User) session.getAttribute("user");

        if (userForUpdate != null) {
            try {
                String firstName = new StringParser().parse(request.getParameter("firstName"));
                String lastName = new StringParser().parse(request.getParameter("lastName"));
                String login = new LoginParser().parse(request.getParameter("login"));
                String email = new EmailParser().parse(request.getParameter("email"));
                String phone = new StringParser().parse(request.getParameter("phone"));
                String role = request.getParameter("role").toUpperCase();


                //  String password = new PasswordParser().parse(request.getParameter("password"));
                //   String repeatPassword = new PasswordParser().parse(request.getParameter(("repeat_password")));

                if (login.equals(userForUpdate.getLogin())) {
                    //  if (new UserValidation().passwordMatchValidate(password, repeatPassword)) {
                    User userWithoutUpdatePassword = userService.updateUserInfo(userForUpdate.getId(), Role.valueOf(role), firstName, lastName, email, phone);


                    if (null != userWithoutUpdatePassword) {
                        //  String hash = Encryption.encrypt(password);
                        //     User user = userService.updateUserPassword(userWithoutUpdatePassword.getId(), password);
                        //    if (null != user) {
                        request.setAttribute("message", "User successfully updated.");
                        // session.setAttribute("user", user);
                        session.setAttribute("user", userWithoutUpdatePassword);
                        LOGGER.debug("User " + login + " was updated");
                        // if (user.getRole() == Role.USER) {
                        if (userWithoutUpdatePassword.getRole() == Role.USER) {
                            //  LOGGER.debug("User " + user.getLogin() + " was updated");
                            LOGGER.debug("User " + userWithoutUpdatePassword.getLogin() + " was updated");
                        } //else if (user.getRole() == Role.ADMIN) {
                        else if (userWithoutUpdatePassword.getRole() == Role.ADMIN) {
                            LOGGER.debug("Administrator was updated");
                        }
                    }
                    //    }
                } else {
                    request.setAttribute("error_message", "Provided passwords didn't match each other");
                    LOGGER.debug("Provided passwords didn't match each other");
                }
                //   }
            } catch (ServiceException e) {
                LOGGER.error(e);
                request.setAttribute("error_message", "Unable to update you at the moment. Try again later.");
            } catch (WrongInputException e) {
                LOGGER.error(e);
            }

//            nextPage = commandProvider.getCommand("viewUserInfo").execute(request, response);
            page = "profile";
        }
        return page;
        //  return "{}";
    }
}

