package by.minsk.vasilyevanatali.auction.util.parser;


import by.minsk.vasilyevanatali.auction.util.exception.WrongInputException;

public interface Parser<T> {
    T parse(String name) throws WrongInputException;
}
