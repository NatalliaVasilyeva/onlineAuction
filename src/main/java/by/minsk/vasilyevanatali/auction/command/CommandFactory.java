//package by.minsk.vasilyevanatali.auction.command;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Optional;
//
//
//public class CommandFactory {
//
//    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
//    CommandProvider commandProvider = CommandProvider.getInstance();
//
//    public Command defineCommand(HttpServletRequest request, HttpServletResponse response) {
//        String commandName = null;
//
//        if (request.getParameter("command") != null
//                && request.getParameter("is_do_get") == null && checkNonce(request, response)) {
//            commandName = request.getParameter("command");
//            LOGGER.debug("Extracted command parameter value is:" + commandName);
//        }
//
//        Optional<Command> resultCommand = Optional.empty();
//        if (commandName != null) {
//            try {
//
//                resultCommand = Optional.of(commandProvider.getCommand("commandName"));
//                LOGGER.debug("Defined command name is:" + commandName.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                LOGGER.error("Illegal type of command: " + commandName);
//                request.getSession().setAttribute("error_message", commandName + "unknown command");
//            }
//        }
//
//        return resultCommand.orElse(commandProvider.getCommand("empty"));
//    }
//
//    private boolean checkNonce(HttpServletRequest request, HttpServletResponse response) {
//        return request.getSession().getAttribute("nonce") != null
//                && request.getParameter("nonce") != null;
//    }
//}
