package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;


import by.minsk.vasilyevanatali.auction.entity.AuctionType;
import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class AuctionTypeParser implements Parser<AuctionType> {

    private static final Logger LOGGER = LogManager.getLogger(AuctionTypeParser.class);

    @Override
    public AuctionType parse(String auctionType) throws WrongInputException {

        LOGGER.debug("Parse auction type: " + auctionType);
        Optional<AuctionType> typeOptional = Optional.empty();
        if (auctionType != null && !auctionType.trim().isEmpty()) {
            try {
                typeOptional = Optional.ofNullable(AuctionType.valueOf(auctionType.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                LOGGER.error(e);
            }
        }
        return typeOptional.orElseThrow(() -> new WrongInputException("Non-valid auction type provided:" + auctionType));
    }
}
