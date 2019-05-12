package by.minsk.vasilyevanatali.auction.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * {@code HelloTag} is a custom tag used in the welcome page.
 */
public class HelloTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        var objectLogin = pageContext.getSession().getAttribute("login");
        var login = objectLogin != null ? (String) objectLogin : "";
        try {
            pageContext.getOut().write(login);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
