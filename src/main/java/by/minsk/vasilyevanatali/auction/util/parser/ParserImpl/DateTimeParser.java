package by.minsk.vasilyevanatali.auction.util.parser.ParserImpl;


import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;
import by.minsk.vasilyevanatali.auction.util.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateTimeParser implements Parser<LocalDateTime> {

	private static final Logger LOGGER = LogManager.getLogger(DateTimeParser.class);

	public LocalDateTime parse(String datetime) throws WrongInputException {
		LOGGER.debug("Parse datetime: " + datetime);
		Optional<LocalDateTime> datetimeOptional = Optional.empty();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		
		if(datetime != null && !datetime.trim().isEmpty()) {
			try {
				LocalDateTime value = LocalDateTime.parse(datetime.trim(), formatter);
				if(value.isAfter(LocalDateTime.now()) && value.isBefore(LocalDateTime.now().plusDays(30))) {
					datetimeOptional = Optional.of(value);
				} else {
					LOGGER.error("Provided date time is already passed");
				}
			} catch (DateTimeParseException e) {
				LOGGER.error(e);
			}
		}
        return datetimeOptional.orElseThrow(() -> new WrongInputException("Non-valid datetime provided:" + datetime));
	}


	public LocalDateTime parseEditAuction(String datetime) throws WrongInputException {
		LOGGER.debug("Parse datetime: " + datetime);
		Optional<LocalDateTime> datetimeOptional = Optional.empty();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		if(datetime != null && !datetime.trim().isEmpty()) {
			try {
				LocalDateTime value = LocalDateTime.parse(datetime.trim(), formatter);

					datetimeOptional = Optional.of(value);

			} catch (DateTimeParseException e) {
				LOGGER.error(e);
			}
		}
		return datetimeOptional.orElseThrow(() -> new WrongInputException("Non-valid datetime provided:" + datetime));
	}
}
