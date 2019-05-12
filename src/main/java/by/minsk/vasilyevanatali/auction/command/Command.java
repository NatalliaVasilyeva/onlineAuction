package by.minsk.vasilyevanatali.auction.command;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//public interface Command {
//
//     /**
//      * Processes the request and forms the answer in the form {@link by.minsk.vasilyevanatali.auction.command.CommandResult}.
//      *
//      * @param request  an {@link HttpServletRequest} object that contains client request
//      * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
//      * @return Returns result of processing in the form of {@link by.minsk.vasilyevanatali.auction.command.CommandResult} object.
//       */
//     CommandResult execute(HttpServletRequest request, HttpServletResponse response);
//}


import javax.servlet.http.HttpServletRequest;

/**
 * Every action to user request is represented by a command.
 * Every command implements {@code Command}
 */
public interface Command {

    String execute(HttpServletRequest req);
}
