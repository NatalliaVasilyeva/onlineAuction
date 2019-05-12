<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="${sessionScope.locale}">
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="user.profile.title"/></title>
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/include/common.html"/>
    <script src="${pageContext.request.contextPath}/js/user/profile.js"></script>
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/header/header.jsp"/>
<div class="container">
    <h1><fmt:message key="user.profile.header"/>:
        <c:choose>
            <c:when test="${user.isBlocked==0}">
                <span class="badge badge-danger"><fmt:message key="fragment.blocked"/></span>
            </c:when>
            <c:otherwise>
                <span class="badge badge-success"><fmt:message key="fragment.active"/></span>
            </c:otherwise>
        </c:choose>
    </h1>
    <%@include file="/fragment/error_message.jsp" %>
    <%@include file="/fragment/success_message.jsp" %>


    <form id="profile-form">
        <div class="form-group row">
            <label for="profile-first-name-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.first-name"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control"
                       id="profile-first-name-input" name="first-name" value="${user.firstName}" readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-last-name-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.last-name"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-last-name-input" name="last-name"
                       value="${user.lastName}" readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-user-name-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.user-name"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-user-name-input" name="user-name"
                       value="${user.login}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-email-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.email"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-email-input" name="email" value="${user.email}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-phone-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.phone"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-phone-input" name="phone"
                       value="${user.phoneNumber}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-role-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.role"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-role-input" name="role" value="${user.role}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-money-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.money"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-money-input" name="balance" value="${user.balance}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row">
            <label for="profile-frozen-money-input" class="col-2 col-form-label">
                <fmt:message key="user.profile.frozen-money"/>
            </label>
            <div class="col-10">
                <input type="text" class="form-control" id="profile-frozen-money-input" name="frozen-money"
                       value="${user.frozenMoney}"
                       readonly/>
            </div>
        </div>
        <div class="form-group row" id="edit-profile-button-div">
            <button type="button" class="form-control btn btn-light" onclick="makeEditable()">
                <fmt:message key="user.profile.edit-button"/>
            </button>
        </div>
        <div class="form-group row" id="edit-manage-buttons-div" style="display: none">
            <div class="col-6">
                <button type="button" class="form-control btn btn-light" onclick="editProfileServerCall()">
                    <fmt:message key="user.profile.submit-button"/>
                </button>
            </div>
            <div class="col-6">
                <button type="button" class="form-control btn btn-light" id="cancel-edits" onclick="makeUneditable()">
                    <fmt:message key="user.profile.cancel-button"/>
                </button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
