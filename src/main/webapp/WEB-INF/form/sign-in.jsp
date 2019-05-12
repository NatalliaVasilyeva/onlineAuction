<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<%--<script src="${pageContext.request.contextPath}/js/form/sign-in.js"></script>--%>
<form id="sign-in-form" action="sign-in" method="post">
    <div class="form-group">
        <fmt:message key="form.login" var="login"/>
        <input type="text" name="username" class="form-control" placeholder="${login}" pattern="^[A-Za-z0-9_]{6,40}$"
               maxlength="40" title="Username(6-40 symbols) may contain A-Z a-z 0-9 _"
               autocomplete="off" id="sign-in-login-input" required/>
        <small class="text-danger" id="sign-in-login-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.password" var="password"/>
        <input type="password" name="password" class="form-control" placeholder="${password}" id="sign-in-password-input" pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
               maxlength="40" title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
               autocomplete="off"required/>
        <small class="text-danger" id="sign-in-password-error-small"></small>
    </div>
<%--    <div class="from-group">--%>
<%--        <button type="button" class="form-control btn btn-light" onclick="signInServerCall()">--%>
<%--            <fmt:message key="form.submit"/>--%>
<%--        </button>--%>
<%--    </div>--%>
   <input type="submit" class="submit bg-info text-light" value="${login}" name="login" required/>
</form>

