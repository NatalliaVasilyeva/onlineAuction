package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.command.CommandType;
import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.AuctionType;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.AuctionService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.AuctionTypeParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.DateTimeParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.StringParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


public class AddAuctionCommand implements Command {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAuctionCommand.class);

    /**
     * Adds auction to auction table.
     *
     * @param req the {@code HttpServletRequest} containing user input
     *            parameters and session attributes.
     * @return the json {@code String} containing auction and validation validationMessage.
     * @throws WrongInputException if {@link AuctionService createAuction (String, Timestamp)}
     *                             throws {@link ServiceException}.
     */

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform create auction");
        String page = "welcome";
        HttpSession session = req.getSession();
        AuctionService auctionService = ServiceFactory.getInstance().getAuctionService();
        User user = (User) session.getAttribute("user");


        if (user != null && user.getRole() == Role.USER && user.getIsBlocked()!=0) {
            try {
                new UserValidation().checkCredentials(user, Role.USER, req);

                LocalDateTime startTime = new DateTimeParser().parse(req.getParameter("startTime"));
                LocalDateTime finishTime = new DateTimeParser().parse(req.getParameter("finishTime"));
                StringParser descriptionParser = new StringParser();
                descriptionParser.setMaxLenght(200);
                String description = new StringParser().parse(req.getParameter("description"));
                AuctionType auctionType = new AuctionTypeParser().parse(req.getParameter("type").toUpperCase());

                if (startTime.isBefore(finishTime)) {
                    boolean isAuctionCreated = auctionService.createAuction(startTime, finishTime, auctionType, description, user.getId());
                    LOGGER.debug("Auction was created: " + isAuctionCreated);

                    Integer maxId = auctionService.getMaxIdOfUserAuctions(user).get();
                    Auction auction = auctionService.findAuctionById(maxId).get();
                    List<Auction> usersAllAuction = auctionService.getAllAuction().get();

                    req.setAttribute("successMessage", "Auction was created");
                    req.setAttribute("lastAuction", auction);
                    req.setAttribute("allUserAuctions", usersAllAuction);


                } else {
                    LOGGER.debug("Auction start time is after provided finish time");
                    req.setAttribute("errorMessage",
                            "Auction start time is after provided finish time");
                }

            } catch (AuthenticationException e) {
                LOGGER.debug(e);
                req.setAttribute("errorMessage", "Unathorised");
            } catch (WrongInputException e) {
                req.setAttribute("errorMessage", "Wrong input");
                LOGGER.debug(e);
            } catch (ServiceException e) {
                LOGGER.error(e);
                req.setAttribute("errorMessage", "Service error.");
            }
            page = "user-auction";
        }
        return page;
    }
}
