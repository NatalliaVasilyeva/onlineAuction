package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.AuctionService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.IdParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteAuctionCommand implements Command {

    /**
     * Removes action from auction list.
     *
     * @param req the {@code HttpServletRequest} containing user input
     * parameters and session attributes.
     * @return page with user auction {@code String}.
     * @throws WrongInputException if {@link AuctionService#deleteAuctionById(Integer)} (int)}
     * throws {@link ServiceException}.
     */

    private static final Logger LOGGER = LogManager.getLogger(DeleteAuctionCommand.class);


    @Override
    public String execute(HttpServletRequest req) {

        LOGGER.debug("Perform delete auction by user");
        AuctionService auctionService = ServiceFactory.getInstance().getAuctionService();
        String page = "welcome";

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("im here 1");

        try {

            Integer auctionId = new IdParser().parse((req.getParameter("auctionId")));
            Auction auction = auctionService.findAuctionById(auctionId).get();
            System.out.println("auction by id for delete");

            if ((user != null && auction.getOwner_id() == user.getId()) || user.getRole() == Role.ADMIN) {
                System.out.println("im here 2");
                boolean isAuctionDeelete = false;
                isAuctionDeelete = auctionService.deleteAuctionById(auctionId);

                if (isAuctionDeelete) {
                    //       page = "user-auctions";
                    page = new ShowMyAuctionsCommand().execute(req);
                } else {
                    req.setAttribute("errorMessage", "Auction doesn't delete");
                    page = "welcome";
                }
            }
            } catch(WrongInputException e){
                LOGGER.error("Empty auction id parameter provided");
                req.setAttribute("errorMessage", "Id of auction isn't provided");
                page = "welcome";
            } catch(ServiceException e){
                LOGGER.error(e);
                req.setAttribute("errorMessage", "Service error");
                page = "welcome";
            }


        return page;
    }
}