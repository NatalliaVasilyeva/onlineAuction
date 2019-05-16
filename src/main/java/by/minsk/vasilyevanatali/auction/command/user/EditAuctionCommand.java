package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.AuctionService;
import by.minsk.vasilyevanatali.auction.service.LotService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.DateTimeParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.IdParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.StringParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class EditAuctionCommand implements Command {

    /**
     * Update auction by id.
     *
     * @param req the {@code HttpServletRequest} containing user input
     * parameters and session attributes.
     * @return the page {@code String} where need you go.
     * @throws WrongInputException if {@link StringParser , IDParser}
     * @throws ServiceException if {@link LotService problems}
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAuctionCommand.class);


    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform edit auction by user");
        AuctionService auctionService = ServiceFactory.getInstance().getAuctionService();
        String page = null;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("im here 1");
        if (user != null && user.getRole() == Role.USER) {
            System.out.println("im here 2");
            try {
                int auctionId = new IdParser().parse((req.getParameter("auctionId")));
                LocalDateTime auctionStartTime = new DateTimeParser().parseEditAuction(req.getParameter("startTime"));
                LocalDateTime auctionFinishTime = new DateTimeParser().parse(req.getParameter("finishTime"));
                StringParser descriptionParser = new StringParser();
                descriptionParser.setMaxLenght(200);
                String description = descriptionParser.parse(req.getParameter("description"));

                boolean isAuctionEdit = auctionService.updateAuction(auctionId, auctionStartTime, auctionFinishTime, description);
                System.out.println(isAuctionEdit);

                if (isAuctionEdit) {
                    page = "user-auctions";
                } else {
                    req.setAttribute("errorMessage", "Auction doesn't edit");
                    page = "welcome";
                }

            } catch (WrongInputException e) {
                LOGGER.error("Empty auction id parameter provided");
                req.setAttribute("errorMessage", "Id of auction isn't provided");
                page = "welcome";
            } catch (ServiceException e) {
                LOGGER.error(e);
                req.setAttribute("errorMessage", "Service error");
                page = "welcome";
            }
        }

        return page;

    }
}



