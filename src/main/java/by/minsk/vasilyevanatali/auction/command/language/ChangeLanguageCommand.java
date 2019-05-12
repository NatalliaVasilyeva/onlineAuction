package by.minsk.vasilyevanatali.auction.command.language;


import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The Class ChangeLanguageCommand.
 */
public class ChangeLanguageCommand implements Command {
    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ChangeLanguageCommand.class);

    /**
     * Executes ChangeLanguageCommand with the data parsed from the
     * {@link HttpServletRequest} content
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("Perform " + "change locale");
        String nextPage = null;
        String referer = null;
        try {
            referer = new URI(request.getHeader("referer")).getPath().replaceFirst("/", "");

            } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Language locale=Language.EN_US;
        try {
            locale = Language.valueOf(request.getParameter("language").toUpperCase());
            LOGGER.debug("Locale defined: " + locale);
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.error(e);
        }
        request.getSession().setAttribute("locale", locale.getLocale());

        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            //TODO
            nextPage = "welcome";
        } else {
            nextPage = "welcome";
        }

        return nextPage;
    }

}
