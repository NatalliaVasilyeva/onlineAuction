package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;



import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import by.minsk.vasilyevanatali.auction.util.regularExpression.RegularExpressionHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;


public class LoginParser implements Parser<String> {
    private static final Logger LOGGER = LogManager.getLogger(LoginParser.class);
    private static final Pattern LOGIN_REG_EX = Pattern.compile(RegularExpressionHolder.LOGIN_REG_EX);

    @Override
    public String parse(String login) throws WrongInputException {
        LOGGER.debug("Validate login: " + login);
        Optional<String> loginOptional = Optional.empty();
        if (login != null && LOGIN_REG_EX.matcher(login.trim()).matches()) {
            loginOptional = Optional.ofNullable(login.trim());
        } else {
            LOGGER.error("Incorrect login: " + login);
        }
        return loginOptional.orElseThrow(() -> new WrongInputException("Non-valid login provided:" + login));
    }
}

