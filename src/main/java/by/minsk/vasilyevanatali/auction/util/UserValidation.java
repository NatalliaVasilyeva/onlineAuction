package by.minsk.vasilyevanatali.auction.util;


import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class UserValidation {
    private static final Logger LOGGER = LogManager.getLogger(UserValidation.class);

    private static final String USER_LOGIN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
    private static final String USER_PASS = "(?=^.{8,50}$)((?=.*\\d|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*)$";
    private static final String USER_EMAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private static final String INTEGER_NUMBER = "^[1-9]\\d{0,19}$";
    private static final int ADMIN_ROLE = 0;
    private static final int USER_ROLE = 1;

    public static boolean loginValidator(String enterLogin, String enterPass) {
        return Pattern.matches(USER_LOGIN, enterLogin) && Pattern.matches(USER_PASS, enterPass);
    }

    public static boolean registrationValidator(String enterLogin, String enterPass, String enterEmail) {
        return loginValidator(enterLogin, enterPass) && Pattern.matches(USER_EMAIL, enterEmail);
    }

    public static boolean changeRoleValidator(String userId, String newRoleId) {
        return Pattern.matches(INTEGER_NUMBER, userId) && Pattern.matches(INTEGER_NUMBER, newRoleId);
    }

    public static boolean hasRoleAdmin(Object role) {
        return (role != null) && (((User) role).getRole().ordinal() == ADMIN_ROLE);
    }

    public static boolean hasRoleAdminOrUser(Object role) {
        return (role != null) && ((((User) role).getRole().ordinal() == ADMIN_ROLE) ||
                (((User) role).getRole().ordinal() == USER_ROLE));
    }

    public void checkCredentials(User user, Role requaredRole, HttpServletRequest request) throws AuthenticationException {
        if (request == null) {
            throw new AuthenticationException("null request provided");
        }
        if (requaredRole == null) {
            throw new AuthenticationException("null requared role provided");
        }
        if (user == null || user.getRole() == null || user.getRole() != requaredRole) {
            if (user != null && user.getId() != 0) {
                throw new AuthenticationException("Unausorized command atempt by user: " + user.getId());
            } else {
                throw new AuthenticationException("Unausorized command atempt");
            }
        }
    }

    public boolean passwordMatchValidate(String password1, String password2) {

        if (password1 != null && password1.equals(password2)) {
            return true;
        } else {
            LOGGER.debug("Password didn't match each other");
            return false;
        }
    }
}
