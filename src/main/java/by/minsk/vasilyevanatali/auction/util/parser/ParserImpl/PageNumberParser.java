package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;



import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class PageNumberParser implements Parser<Integer> {

    private static final Logger LOGGER = LogManager.getLogger(PageNumberParser.class);

    @Override
    public Integer parse(String pageNumber) throws WrongInputException {
        LOGGER.debug("Parse page number: " + pageNumber);
        if (pageNumber == null) {
            throw new WrongInputException("Page can't be null");
        }
        return Optional.of(pageNumber)
                .filter(p -> !p.trim().isEmpty())
                .map(p -> {
                    try {
                        return Integer.valueOf(p.trim());
                    } catch (NumberFormatException e) {
                        LOGGER.error(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p >= 0)
                .orElseThrow(() -> new WrongInputException("Non-valid page number provided:" + pageNumber));
    }
}
