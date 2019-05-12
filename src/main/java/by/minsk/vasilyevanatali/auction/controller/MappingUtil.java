package by.minsk.vasilyevanatali.auction.controller;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.command.CommandType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code MappingUtil} is util class containing helper methods
 * to relay task of generating response to some other class in
 * command layer.
 */
@RequestMethods(
        gets = {
                "", "business-profile",
                "manage-auctions", "manage-users", "manage-requests",
                "profile", "settings", "welcome"
        },
        posts = {"", "block-user", "unblock-user", "approve-lot", "unapprove-lot", "create-auction",
                "propose-lot", "business-profile",
                "change-locale", "change-password", "edit-auction",
                "edit-lot", "edit-profile", "make-bid",
                "manage-auctions", "manage-requests", "manage-users",
                "profile", "remove-auction", "delete-lot", "remove-user",
                "sign-in", "sign-out", "sign-up", "add-funds", "withdraw-funds", "change-language"
        })
class MappingUtil {

    //   private static Logger logger = LogManager.getLogger();

    private static Map<String, String> mappingUriJsp = new HashMap<>();

    static {
        mappingUriJsp.put("", "WEB-INF/guest/welcome.jsp");
        mappingUriJsp.put("business-profile", "WEB-INF/user/business-profile.jsp");
        mappingUriJsp.put("manage-auctions", "WEB-INF/admin/manage-auctions.jsp");
        mappingUriJsp.put("manage-requests", "WEB-INF/admin/manage-requests.jsp");
        mappingUriJsp.put("manage-users", "WEB-INF/admin/manage-users.jsp");
        mappingUriJsp.put("profile", "WEB-INF/user/profile.jsp");
        mappingUriJsp.put("request", "WEB-INF/user/request.jsp");
        mappingUriJsp.put("settings", "WEB-INF/user/settings.jsp");
        mappingUriJsp.put("welcome", "WEB-INF/guest/welcome.jsp");
    }

    /**
     * Extracts URI from URL supplied by client's request.
     *
     * @param req the {@code HttpServletRequest} containing user input
     *            parameters and session attributes.
     * @return {@code String} URI.
     * @throws MalformedURLException if invalid URL was passed by a client.
     */
    static String getUri(HttpServletRequest req) throws MalformedURLException {
        return new URL(req.getRequestURL().toString())
                .getPath().replaceFirst("/", "");

    }

    static Command mapToCommand(String uri) {
        String commandType = !uri.isEmpty() ? toEnumFormat(uri) : "EMPTY";
        //       logger.debug("Command: " + commandType);
        return CommandType.valueOf(commandType).getCommand();
    }

    static String mapToJsp(String uri) {
        return mappingUriJsp.get(uri);
    }

    /**
     * Builds message containing all exception messages
     * in stack trace.
     *
     * @param e caught exception
     * @return Stack error message.
     */
    static String unfoldExceptionStackTrace(Throwable e) {
        StringBuilder message = new StringBuilder();
        while (e != null) {
            message.append(Arrays.toString(e.getStackTrace())).append("\n");
            e = e.getCause();
        }
        return message.toString();
    }

    /**
     * Transforms name into a legal enum identifier.
     * By Java convention enum values must be in uppercase and words separated
     * by underscores. Also dashes are illegal to use in identifiers.
     *
     * @param word {@code String} lowercase letters with dashes.
     * @return enum-formatted {@code String}.
     */
    private static String toEnumFormat(String word) {
        return word.trim().replace('-', '_').toUpperCase();
    }
}
