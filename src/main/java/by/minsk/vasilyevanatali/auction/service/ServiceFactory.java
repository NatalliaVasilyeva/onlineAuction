package by.minsk.vasilyevanatali.auction.service;

public class ServiceFactory {
    private final static ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private AuctionService auctionService;
    private LotService lotService;
    private UserService userService;


    public AuctionService getAuctionService() {
        return auctionService != null ? auctionService : (auctionService = new AuctionService());
    }

    public LotService getLotService() {
        return lotService != null ? lotService : (lotService = new LotService());
    }

    public UserService getUserService() {
        return userService != null ? userService : (userService = new UserService());
    }
}
