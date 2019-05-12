package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;


import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import by.minsk.vasilyevanatali.auction.util.regularExpression.RegularExpressionHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;

public class EmailParser implements Parser<String> {
    private static final Logger LOGGER = LogManager.getLogger(EmailParser.class);
    private static final Pattern EMAIL_REG_EX = Pattern.compile(RegularExpressionHolder.EMAIL_REG_EX);

    @Override
    public String parse(String email) throws WrongInputException {

        LOGGER.debug("Validate email: " + email);
        Optional<String> emailOptional = Optional.empty();
        if (email != null && EMAIL_REG_EX.matcher(email.trim()).matches()) {
            emailOptional = Optional.ofNullable(email.trim());
        } else {
            LOGGER.error("Incorrect email: " + email);
        }
        return emailOptional.orElseThrow(() -> new WrongInputException("Non-valid email provided:" + email));
    }
}
