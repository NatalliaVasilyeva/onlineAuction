package by.minsk.vasilyevanatali.auction.command.user;

import by.minsk.vasilyevanatali.auction.command.Command;
import by.minsk.vasilyevanatali.auction.service.LotService;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.ParserImpl.IdParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteLotCommand implements Command {
    /**
     * Removes lot from lot table.
     *
     * @param req the {@code HttpServletRequest} containing user input
     * parameters and session attributes.
     * @return page, where you need to go
     * @throws ServiceException if {@link LotService#deleteLot(Integer)}
     * throws {@link ServiceException}.
     */

    private static final Logger LOGGER = LogManager.getLogger(DeleteLotCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.debug("Perform delete lot by user");
        LotService lotService = ServiceFactory.getInstance().getLotService();
        String page = null;
        try {
            Integer lotId = new IdParser().parse(req.getParameter("lotId"));
            lotService.deleteLot(lotId);
            page = "business-profile";
        } catch (ServiceException e) {
            LOGGER.error(e);
            req.setAttribute("errorMessage", "Service error");
            page = "welcome";
        } catch (WrongInputException e) {
            LOGGER.error("Empty lot id parameter provided");
            req.setAttribute("errorMessage", "Id of lot isn't provided");
            page = "welcome";
        }
        return page;
    }
}
