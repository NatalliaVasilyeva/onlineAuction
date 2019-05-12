package by.minsk.vasilyevanatali.auction.command.common;


import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.LoginParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.PasswordParser;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    //private CommandProvider commandProvider = CommandProvider.getInstance();

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform login page");
        String page = null;
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = req.getSession();
        //  String page = ViewPage.LOGIN_PAGE.getPath();
        try {
            String login = new LoginParser().parse(req.getParameter("username"));

            System.out.println(login);
            // String login = new LoginParser().parse(req.getParameter("login"));
            String password = new PasswordParser().parse(req.getParameter("password"));

            System.out.println(password);

            UserValidation userValidation = new UserValidation();
            if (!userValidation.loginValidator(login, password)) {
                LOGGER.debug("User matching credentials not found");
                req.setAttribute("incorrect", "Incorrect login or password, try again");
                //   validationMessage = "Incorrect login or password, try again";
                //   return ViewPage.LOGIN_PAGE.getPath();
                page= "welcome";
            }
            // password = Encryption.encrypt(password);
            Optional<User> optionalUser = userService.findUserByLoginPassword(login, password);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Role role = user.getRole();
                session.setAttribute("role", role);
                session.setAttribute("locale", Locale.getDefault());
                session.setAttribute("username", login);
                session.setAttribute("user", user);
                if (role.getName().toUpperCase().equals("ADMIN")) {
                    LOGGER.debug("Administrator log-in: " + user.getFirstName());
                } else if (role.getName().toUpperCase().equals("USER")) {
                    LOGGER.debug("User log-in: " + user.getFirstName());
                }
                //   page = commandProvider.getCommand("viewAuctionSet").execute(request, response);
                page= "welcome";
            } else {

                req.setAttribute("error_message", "Incorrect login or password");
            }
        } catch (ServiceException e) {
            LOGGER.error("problem with service layer or lower", e);
        } catch (WrongInputException e) {
            LOGGER.error(e);
            req.setAttribute("error_message", "Wrong login or password.");
        }
         return page;
       // return new Gson().toJson(validationMessage);
    }
}