package by.minsk.vasilyevanatali.auction.command.common;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.EmailParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.LoginParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.PasswordParser;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class SingUpCommand implements Command {
    /**
     * Signs user up and sets his session attributes.
     *
     * @param req the {@code HttpServletRequest} containing user input
     *            parameters and session attributes.
     * @return the json {@code String} containing validation message.
     */
    @Override
    public String execute(HttpServletRequest req) {
        String validationMessage = null;
        String validationMessage1 = null;
        String validationMessage2 = null;
        List<String> message = new ArrayList<>();

        try {
            var login = new LoginParser().parse(req.getParameter("login"));
            var firstName = req.getParameter("firstname");
            var lastName = req.getParameter("lastname");
            var email = new EmailParser().parse(req.getParameter("email"));
            var phone = req.getParameter("phone");
            var password = new PasswordParser().parse(req.getParameter("password"));
            var repeatPassword = new PasswordParser().parse(req.getParameter(("repeat_password")));

            System.out.println(login + " " + firstName);

            UserService userService = ServiceFactory.getInstance().getUserService();
            System.out.println("take user service");
            if (checkPasswordMatch(password, repeatPassword, req)) {
              System.out.println("take check password");
//                if (userService.findUserByLogin(login)) {

                if (!userService.findUserByLoginReturnUser(login)) {
                    System.out.println("take check login");
                    User user = userService.createUser(login, firstName, lastName, email, phone, password);
                    if (null != user) {
                        setBuyerSessionAttributes(req, user);
                    }
                } else{
                    System.out.println("login is present");
                }
            } else {
                validationMessage = "This login is busy. Please, try again";
                validationMessage1 = "This login is busy. Please, try again";
                validationMessage2 = "This login is busy. Please, try again";
                message.add(validationMessage);
                message.add(validationMessage1);
                message.add(validationMessage2);
                message.toArray();
            }

        } catch (ServiceException | WrongInputException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(message);
    }


    private void setBuyerSessionAttributes(HttpServletRequest req, User user) {

        req.getSession().setAttribute("login", user.getLogin());
        req.getSession().setAttribute("role", user.getRole());
        req.getSession().setAttribute("locale", Locale.getDefault());
        req.getSession().setAttribute("user", user);
        // LOGGER.debug("User " + user.getFirstName() + " was created");
    }

    private boolean checkPasswordMatch(String password, String repeatPassword, HttpServletRequest
            request) {

        UserValidation userValidation = new UserValidation();
        boolean isPasswordPairMatch = userValidation.passwordMatchValidate(password, repeatPassword);
        if (!isPasswordPairMatch) {
            request.setAttribute("error_message", "passwords didn't match each other");
        }
        return isPasswordPairMatch;
    }
}

