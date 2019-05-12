package by.minsk.vasilyevanatali;

import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.ServiceFactory;
import by.minsk.vasilyevanatali.auction.service.UserService;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;

public class main {
    public static void main(String[] args) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        User userWithoutUpdatePassword = userService.updateUserInfo(2, Role.valueOf("USER"), "vasia", "vasia", "natali1111@tut.by", "+375295015296");
        System.out.println(userWithoutUpdatePassword);
    }
}
