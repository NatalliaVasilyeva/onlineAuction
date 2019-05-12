package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;


import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StringParser implements Parser<String> {

    private static final Logger LOGGER = LogManager.getLogger(StringParser.class);
    private Integer maxLength=250;

    public void setMaxLenght(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String parse(String string) throws WrongInputException {

        LOGGER.debug("Parse string: " + string);
        Optional<String> stringOptional = Optional.empty();
        if (string != null && !string.trim().isEmpty()) {
            String value = string.trim();
            if (maxLength != null && value.length() <= maxLength) {
                stringOptional = Optional.ofNullable(value);
            }
        } else {
            LOGGER.debug("Incorrect string: " + string);
        }
        return stringOptional.orElseThrow(() -> new WrongInputException("Non-valid string provided:" + string));
    }
}
