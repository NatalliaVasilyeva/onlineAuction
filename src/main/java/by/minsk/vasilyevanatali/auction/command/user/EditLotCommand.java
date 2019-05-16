package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.LotService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.IdParser;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.StringParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditLotCommand implements Command {
    /**
     * Updates lot table row classified by lot id.
     *
     * @param req the {@code HttpServletRequest} containing user input
     * parameters and session attributes.
     * @return the page {@code String} where need you go.
     * @throws WrongInputException if {@link StringParser, IDParser}
     * @throws ServiceException if {@link LotService problems}
     */
    private static final Logger LOGGER = LogManager.getLogger(EditLotCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform edit lot by user");
        LotService lotService = ServiceFactory.getInstance().getLotService();
        String page = null;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals("USER")) {
            try {
                int lotId = new IdParser().parse((req.getParameter("lotId")));
                String lotName = new StringParser().parse(req.getParameter("lotName"));
                String lotDescription = new StringParser().parse(req.getParameter("lotDescription"));
                String category = new StringParser().parse(req.getParameter("lotCategory"));

                boolean islotEdit = lotService.editLot(lotId, lotName, lotDescription, category);
                if (islotEdit) {
                    page = "business-profile";
                } else {
                    req.setAttribute("errorMessage", "Lot doesn't edit");
                    page = "welcome";
                }

            } catch (WrongInputException e) {
                LOGGER.error("Empty lot id parameter provided");
                req.setAttribute("errorMessage", "Id of lot isn't provided");
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
