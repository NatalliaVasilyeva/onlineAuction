package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;



import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class IdParser implements Parser<Integer> {

    private static final Logger LOGGER = LogManager.getLogger(IdParser.class);

    @Override
    public Integer parse(String id) throws WrongInputException {
        LOGGER.debug("Parse id: " + id);
        Optional<Integer> optionalId = Optional.empty();
        if (id != null && !id.trim().isEmpty()) {
            try {
                Integer idValue = Integer.valueOf(id.trim());
                if (idValue > 0) {
                    optionalId = Optional.of(idValue);
                } else {
                    LOGGER.error("Negative or 0 id");
                }
            } catch (NumberFormatException e) {
                LOGGER.error(e);
            }
        }
        return optionalId.orElseThrow(() -> new WrongInputException("Non-valid id provided:" + id));
    }
}

