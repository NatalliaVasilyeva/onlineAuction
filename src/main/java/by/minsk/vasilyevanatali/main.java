package by.minsk.vasilyevanatali;

import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;

import java.util.List;

public class main {
    public static void main(String[] args) throws ServiceException {
        User user = ServiceFactory.getInstance().getUserService().findUserById(2).get();

       List<Auction> auctions = ServiceFactory.getInstance().getAuctionService().getAllUsersAuction(user).get();
        System.out.println(auctions.toString());
    }
}
