//package by.minsk.vasilyevanatali.auction.command;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class CommandProvider {
//
//    private static final CommandProvider INSTANCE = new CommandProvider();
//
//    public static CommandProvider getInstance() {
//        return INSTANCE;
//    }
//
//    private Map<String, Command> commands = new HashMap<>();
//
//    private CommandProvider() {
//        commands.put("approveLot", new ApproveLotCommand());
//        commands.put("blockUser", new BlockUserCommand());
//        commands.put("createAuction", new CreateAuctionCommand());
//        commands.put("unApproveLot", new UnapproveLotCommand());
//        commands.put("unBlockedUser", new UnBlockedUserCommand());
//        commands.put("addFunds", new AddFundsCommand());
//        commands.put("editUser", new EditUserCommand());
//        commands.put("logIn", new LoginCommand());
//        commands.put("logOut", new LogOutCommand());
//        commands.put("withdrowFunds", new WithdrowFundsCommand());
//        commands.put("viewAddAuction", new ViewAddAuction());
//        commands.put("viewAuctionSet", new ViewAuctionSet());
//        commands.put("viewLot", new ViewLot());
//        commands.put("viewLotAwaitApproval", new ViewLotAwaitApproval());
//        commands.put("viewLotSet", new ViewLotSet());
//        commands.put("viewUserInfo", new ViewUserInfo());
//        commands.put("viewUserSet", new ViewUserSet());
//        commands.put("proposeBid", new ProposeBidCommand());
//        commands.put("proposeLot", new ProposeLotCommand());
//        commands.put("changeLanguage", new ChangeLanguageCommand());
//        commands.put("empty", new EmptyCommand());
//        commands.put("registration", new RegisterCommand());
//        commands.put("showProfile", new ProfileCommand());
//        commands.put("mainPage", new MainPageCommand());
//        commands.put("adminMainPage", new AdminStartPageCommand());
//        commands.put("showUserLots", new UserLotsCommand());
//        commands.put("showUserBids", new UserBidsCommand());
//        commands.put("deleteLot", new DeleteLotCommand());
//        commands.put("startPage", new StartPageCommand());
//        commands.put("startLogin", new StartLoginCommand());
//
//    }
//
//    public Command getCommand(String commandName) {
//        return commands.get(commandName);
//    }
//}
