package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Lot;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.LotService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


public class BusinessProfileCommand implements Command {
    /**
     * Selects lots and bids owned by user.
     *
     * @param req the {@code HttpServletRequest} containing user input
     * parameters and session attributes.
     * @return the page {@code String} where user go after execute command.
     */
    private static final Logger LOGGER = LogManager.getLogger(BusinessProfileCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform business profile with user's lots and bids");
        String page = "welcome";
        User user = (User) req.getSession().getAttribute("user");
        if (null != user) {
            if (user.getIsBlocked() != 0) {
                LotService lotService = ServiceFactory.getInstance().getLotService();
                try {
                    Optional<List<Lot>> myLots = lotService.takeLotSetBySellerId(user.getId());
                    Optional<List<Lot>> myBidLots = lotService.takeLotSetByBidderId(user.getId());
                    if (myLots.isPresent() || myBidLots.isPresent()) {
                        req.setAttribute("myLots", myLots.get());
                        req.setAttribute("myBidLots", myBidLots.get());
                        LOGGER.debug("There are some info in DB about lots");
                        page = "business-profile";
                    } else {
                        LOGGER.debug("There are no lots of this user's lots or bids in DB");
                        req.setAttribute("errorMessage", "There are no lots of this user's lots or bids in DB");
//                        page = "welcome";
                        page="business-profile";

                    }
                } catch (ServiceException e) {
                    LOGGER.error(e);

                }
            } else {
                req.setAttribute("errorMessage", "User is blocked");
             //   page = "welcome";
                page="business-profile";
            }
        } else {
            req.setAttribute("errorMessage", "User is null");
           // page = "welcome";
            page="business-profile";
        }
        return page;
    }

}
