package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.AuctionService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMyAuctionsCommand implements Command {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ShowMyAuctionsCommand.class);

    /**
     * Show all auctions of user to table and last add auction.
     *
     * @param req the {@code HttpServletRequest} containing user input
     *            parameters and session attributes.
     * @return the json {@code String} containing auction and validation validationMessage.
     * @throws ServiceException if {@link AuctionService createAuction (String, Timestamp)}
     *                             throws {@link ServiceException}.
     */


    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform auction user's set");
        String page = "welcome";
        HttpSession session = req.getSession();
        AuctionService auctionService = ServiceFactory.getInstance().getAuctionService();
        User user = (User) session.getAttribute("user");


        if (user != null && user.getRole() == Role.USER) {
            try {
                new UserValidation().checkCredentials(user, Role.USER, req);

                    Integer maxAuctionId = auctionService.getMaxIdOfUserAuctions(user).get();
                    Auction auction = auctionService.findAuctionById(maxAuctionId).get();
                    List<Auction> usersAllAuction = auctionService.getAllUsersAuction(user).get();



                    req.setAttribute("lastAuction", auction);
                    req.setAttribute("allUserAuctions", usersAllAuction);

            } catch (ServiceException e) {
                LOGGER.error(e);
                req.setAttribute("errorMessage", "Service error.");
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
            page = "user-auction";
        }
        return page;
    }
}
