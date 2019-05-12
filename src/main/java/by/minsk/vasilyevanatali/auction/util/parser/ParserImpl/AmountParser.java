package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;



import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import by.minsk.vasilyevanatali.auction.util.regularExpression.RegularExpressionHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;

public class AmountParser implements Parser<Integer> {

    private static final Logger LOGGER = LogManager.getLogger(AmountParser.class);
    private static final Pattern MONEY_AMOUNT_REGEX = Pattern.compile(RegularExpressionHolder.MONEY_AMOUNT_REG_EX);

    public Integer parse(String amount) throws WrongInputException {

        LOGGER.debug("Validate amount: " + amount);
        Optional<Integer> optionalAmount = Optional.empty();
        if (amount != null && !amount.trim().isEmpty() && MONEY_AMOUNT_REGEX.matcher(amount.trim()).matches()) {
            try {
                Integer value = Integer.valueOf(amount.trim());
                optionalAmount = Optional.ofNullable(value);
            } catch (NumberFormatException e) {
                LOGGER.error(e);
            }
        }
        return optionalAmount.orElseThrow(() -> new WrongInputException("Non-valid amount provided:" + amount));
    }
}
