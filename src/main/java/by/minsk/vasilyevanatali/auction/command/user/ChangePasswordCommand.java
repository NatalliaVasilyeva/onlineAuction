package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.PasswordParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ChangePasswordCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);


    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("Perform change user password");
        UserService userService = ServiceFactory.getInstance().getUserService();

        String page = "welcome";
        HttpSession session = request.getSession();

        User userForUpdate = (User) session.getAttribute("user");

        if (userForUpdate != null) {
            try {

                String oldPassword = new PasswordParser().parse(request.getParameter("oldPassword"));
                String newPassword = new PasswordParser().parse(request.getParameter("newPassword"));
                String confirmNewPassword = new PasswordParser().parse(request.getParameter("confirmNewPassword"));

                if (userService.findUserByLoginPassword(userForUpdate.getLogin(), oldPassword).isPresent()) {
                    if (new UserValidation().passwordMatchValidate(newPassword, confirmNewPassword)) {
                        User userWithUpdatePassword = userService.updateUserPassword(userForUpdate.getId(), newPassword);

                        request.setAttribute("message", "User password successfully updated.");
                        session.setAttribute("user", userWithUpdatePassword);
                        LOGGER.debug("User " + userForUpdate.getLogin() + " was updated");
                        page = "settings";
                        if (userWithUpdatePassword.getRole() == Role.USER) {
                            LOGGER.debug("User " + userWithUpdatePassword.getLogin() + " was updated");
                        } else if (userWithUpdatePassword.getRole() == Role.ADMIN) {
                            LOGGER.debug("Administrator was updated");
                        }
                    } else {
                        request.setAttribute("error_message", "Provided passwords didn't match each other");
                        LOGGER.debug("Provided passwords didn't match each other");
                    }
                } else {
                    request.setAttribute("error_message", "This user doesn't find");
                    LOGGER.debug("This user doesn't find");
                }
                //   }
            } catch (ServiceException | WrongInputException e) {
                LOGGER.error(e);
                request.setAttribute("error_message", "Unable to update you at the moment. Try again later.");
            }
        } else {
            request.setAttribute("error_message", "User is not in session");
            LOGGER.debug("User is not in session");
        }
//            nextPage = commandProvider.getCommand("viewUserInfo").execute(request, response);

            return page;
        //  return "{}";
    }
}
