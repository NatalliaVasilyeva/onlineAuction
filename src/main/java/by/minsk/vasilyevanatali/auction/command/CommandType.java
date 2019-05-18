package by.minsk.vasilyevanatali.auction.command;

import by.minsk.vasilyevanatali.auction.command.common.LogOutCommand;
import by.minsk.vasilyevanatali.auction.command.common.LoginCommand;
import by.minsk.vasilyevanatali.auction.command.common.SingUpCommand;
import by.minsk.vasilyevanatali.auction.command.common.ViewUserInfo;
import by.minsk.vasilyevanatali.auction.command.language.ChangeLanguageCommand;
import by.minsk.vasilyevanatali.auction.command.user.*;

/**
 * Whenever user sends HTTP-request a command gets executed.
 * {@code CommandType} represents every possible command.
 */
public enum CommandType {
    //   "approveLot", new ApproveLotCommand();
//        "blockUser", new BlockUserCommand());
    ADD_AUCTION(new AddAuctionCommand()),
    MY_AUCTIONS(new ShowMyAuctionsCommand()),
    EDIT_AUCTION(new EditAuctionCommand()),
    DELETE_AUCTION(new DeleteAuctionCommand()),
    //        "unApproveLot", new UnapproveLotCommand());
//        "unBlockedUser", new UnBlockedUserCommand());
//        "addFunds", new AddFundsCommand());
//        "editUser", new EditUserCommand());
    EDIT_LOT(new EditLotCommand()),
    DELETE_LOT(new DeleteLotCommand()),
    EDIT_PROFILE(new EditUserCommand()),
    SIGN_IN(new LoginCommand()),
    SIGN_OUT(new LogOutCommand()),
    //        "withdrowFunds", new WithdrowFundsCommand());
//        "viewAddAuction", new ViewAddAuction());
//        "viewAuctionSet", new ViewAuctionSet());
//       "viewLot", new ViewLot());
//        "viewLotAwaitApproval", new ViewLotAwaitApproval());
//        "viewLotSet", new ViewLotSet());
    PROFILE(new ViewUserInfo()),

    //        "viewUserSet", new ViewUserSet());
//        "proposeBid", new ProposeBidCommand());
//        "proposeLot", new ProposeLotCommand());
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    //        "empty", new EmptyCommand());
    EMPTY(new EmptyCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    BUSINESS_PROFILE(new BusinessProfileCommand()),

    SIGN_UP(new SingUpCommand());
//       "showProfile", new ProfileCommand());
//        "mainPage", new MainPageCommand());
//        "adminMainPage", new AdminStartPageCommand());
//        "showUserLots", new UserLotsCommand());
//       "showUserBids", new UserBidsCommand());
//        "deleteLot", new DeleteLotCommand());
//        "startPage", new StartPageCommand());
//        "startLogin", new StartLoginCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

}
