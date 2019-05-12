<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<script src="${pageContext.request.contextPath}/js/form/sign-up.js"></script>
<form id="sign-up-form">
    <div class="form-group">
        <fmt:message key="form.login" var="login"/>
        <input type="text" class="form-control" placeholder="${login}" id="sign-up-login-input" required/>
        <small class="text-danger" id="sign-up-login-error-small"></small>
    </div>
    <div class="form-group">
    <fmt:message key="form.first_name" var="firstName"/>
    <input type="text" class="form-control" placeholder="${firstName}" id="sign-up-first-name-input" required/>
    <small class="text-danger" id="sign-up-first-name-error-small"></small>
</div>
    <div class="form-group">
    <fmt:message key="form.last_name" var="lastName"/>
    <input type="text" class="form-control" placeholder="${lastName}" id="sign-up-last-name-input" required/>
    <small class="text-danger" id="sign-up-last-name-error-small"></small>
</div>
    <div class="form-group">
        <fmt:message key="form.email" var="email"/>
        <input type="text" class="form-control" placeholder="${email}" id="sign-up-email-input" required/>
        <small class="text-danger" id="sign-up-email-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.phone" var="phone"/>
        <input type="text" class="form-control" placeholder="${phone}" id="sign-up-phone-input" required/>
        <small class="text-danger" id="sign-up-last-phone-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.password" var="password"/>
        <input type="password" class="form-control" placeholder="${password}" id="sign-up-password-input" required/>
        <small class="text-danger" id="sign-up-password-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.repeat_password" var="confirmPassword"/>
        <input type="password" class="form-control" placeholder="${confirmPassword}" id="sign-up-confirm-password-input"
               required/>
        <small class="text-danger" id="sign-up-confirm-password-error-small"></small>
    </div>
    <div class="form-group">
        <button type="button" class="form-control btn btn-light" onclick="signUpServerCall()">
            <fmt:message key="form.submit"/>
        </button>
    </div>
</form>
