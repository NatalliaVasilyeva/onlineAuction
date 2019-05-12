package by.minsk.vasilyevanatali.auction.command.common;


import by.minsk.vasilyevanatali.auction.command.Command;

import javax.servlet.http.HttpServletRequest;


public class LogOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        //return ViewPage.LOGIN_PAGE.getPath();
        return "welcome";
    }
}
