package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;



import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import by.minsk.vasilyevanatali.auction.util.regularExpression.RegularExpressionHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;

public class PasswordParser implements Parser<String> {

    private static final Logger LOGGER = LogManager.getLogger(PasswordParser.class);
    private static final Pattern PASSWORD_REG_EX = Pattern.compile(RegularExpressionHolder.PASSWORD_REG_EX);

    @Override
    public String parse(String password) throws WrongInputException {
        LOGGER.debug("Validate password.");
        Optional<String> passwordOptional = Optional.empty();
        if (password != null && PASSWORD_REG_EX.matcher(password.trim()).matches()) {
            passwordOptional = Optional.of(password.trim());
        } else {
            LOGGER.error("Incorrect password: " + password);
        }
        return passwordOptional.orElseThrow(() -> new WrongInputException("Non-valid passwword provided."));
    }
}
