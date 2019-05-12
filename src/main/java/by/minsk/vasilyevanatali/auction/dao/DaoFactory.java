package by.minsk.vasilyevanatali.auction.dao;


import by.minsk.vasilyevanatali.auction.dao.impl.AuctionDao;
import by.minsk.vasilyevanatali.auction.dao.impl.BidDao;
import by.minsk.vasilyevanatali.auction.dao.impl.LotDao;
import by.minsk.vasilyevanatali.auction.dao.impl.UserDao;

public class DaoFactory {
    private final static DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private AuctionDao auctionDao;
    private BidDao bidDao;
    private LotDao lotDao;
    private UserDao userDao;

    public AuctionDao getAuctionDao() {
        return auctionDao != null ? auctionDao : (auctionDao = new AuctionDao());
    }

    public BidDao getBidDao() {
        return bidDao != null ? bidDao : (bidDao = new BidDao());
    }

    public LotDao getLotDao() {
        return lotDao != null ? lotDao : (lotDao = new LotDao());
    }

    public UserDao getUserDao() {
        return userDao != null ? userDao : (userDao = new UserDao());
    }
}
