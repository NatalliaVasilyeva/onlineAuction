<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="${sessionScope.locale}">
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="user.settings.title"/></title>
    <jsp:include page="../include/common.html"/>
    <script src="../../js/user/settings.js"></script>
<%--    <script>$(() => setInitialLocale("${sessionScope.locale}"));</script>--%>
    <style>
        .top-buffer { margin-top:100px; }
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<div class="container">
    <div class="row">
        <h1><fmt:message key="user.settings.header"/></h1>
    </div>
    <div class="row">
        <label for="locale-select"><fmt:message key="user.settings.locale"/></label>
        <div class="col-2">
            <select class="form-control" onchange="changeLocale(this)" id="locale-select">
                <option value="en_US">English</option>
                <option value="ru_RU">Русский</option>
                <option value="de_DE">Deutsch</option>
            </select>
        </div>
    </div>
    <div class="row top-buffer" id="change-password-div">
        <button type="button" class="btn btn-light btn-block" onclick="changePassword()">
            <fmt:message key="user.settings.change-password-button"/>
        </button>
    </div>
    <div class="row top-buffer" id="save-new-password-div" style="display: none">
        <form>
            <div class="form-group row">
                <label for="settings-old-password-input" class="col-2">
                    <fmt:message key="user.settings.old-password"/>
                </label>
                <div class="col-8">
                    <input type="password" class="form-control" pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                           maxlength="40" title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                           autocomplete="off"
                           id="settings-old-password-input" name="old_password" required/>
                </div>
                <div class="col-2" id="old-password-error-div">
                    <small class="text-danger" id="settings-old-password-error-small"></small>
                </div>
            </div>
            <div class="form-group row">
                <label for="settings-new-password-input" class="col-2">
                    <fmt:message key="user.settings.new-password"/>
                </label>
                <div class="col-8">
                    <input type="password" class="form-control" pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                           maxlength="40" title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                           autocomplete="off"
                           id="settings-new-password-input" name="new_password" required/>
                </div>
                <div class="col-2" id="new-password-error-div">
                    <small class="text-danger" id="settings-new-password-error-small"></small>
                </div>
            </div>
            <div class="form-group row">
                <label for="settings-confirm-new-password-input" class="col-2">
                    <fmt:message key="user.settings.confirm-new-password"/>
                </label>
                <div class="col-8">
                    <input type="password" class="form-control" pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                           maxlength="40" title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
                           autocomplete="off"
                           id="settings-confirm-new-password-input" name="confirm_new_password" required/>
                </div>
                <div class="col-2" id="confirm-new-password-error-div">
                    <small class="text-danger" id="settings-confirm-new-password-error-small"></small>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-6">
                        <button type="button" class="form-control btn btn-light" onclick="changePasswordServerCall()">
                            <fmt:message key="user.settings.submit-button"/>
                        </button>
                    </div>
                    <div class="col-6">
                        <button type="button" class="form-control btn btn-light" onclick="cancelChangePassword()">
                            <fmt:message key="user.settings.cancel-button"/>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <%@include file="/fragment/error_message.jsp" %>
    <%@include file="/fragment/success_message.jsp" %>
</div>
</body>
</html>
