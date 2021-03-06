<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<script src="${pageContext.request.contextPath}/js/header/header-user.js"></script>
<nav class="navbar navbar-expand-lg" style="background-color: #e3f2fd;">
    <div class="d-flex">
        <div class="p-2">
            <c:choose>
                <c:when test="${not empty user && empty requestScope.auction_set}">
                    <a class="nav-brand" href="/"></a>
                </c:when>
                <c:otherwise>
                    <fmt:message key="header.english_auction"/> <span class="fa fa-leaf"></span>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
    <ul class="navbar-nav mr-auto">

        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
               aria-expanded="false"><fmt:message key="header.user.nav-bar.pages"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a class="nav-link" href="javascript:goToBusinessProfile()">
                        <fmt:message key="header.admin.business-profile"/></a>
                </li>
                <li>
                    <a class="nav-link" href="javascript:goToMyAuctions()">
                        <fmt:message key="header.user.nav-bar.my-auctions"/></a>
                </li>
                <%--                <li><a href="#">Something else here</a></li>--%>
                <%--                <li role="separator" class="divider"></li>--%>
                <%--                <li><a href="#">Separated link</a></li>--%>
                <%--                <li role="separator" class="divider"></li>--%>
                <%--                <li><a href="#">One more separated link</a></li>--%>
            </ul>
        </li>


        <li class="nav-item">
            <a class="nav-link" href="javascript:goToBusinessProfile()">
                <fmt:message key="header.admin.business-profile"/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="javascript:goToRequestAuction()">
                <fmt:message key="header.admin.request"/>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="javascript:goToAddAuction()">
                <fmt:message key="header.user.add-auction"/>
            </a>
        </li>

        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false">
                <fmt:message key="header.language"/>
            </a>
            <div class="dropdown-menu">
                <form method="POST" action="change-language" id="change_language_form">
                    <select class="btn w-100" name="language" required>
                        <option disabled selected value=""><fmt:message key="form.choose_language"/>:</option>
                        <option value="en_US">English</option>
                        <option value="ru_RU">Русский</option>
                        <option value="de_DE">Deutsch</option>
                    </select>
                </form>
            </div>
        </li>
    </ul>
    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false">
                <c:out value="${sessionScope.username}"/>
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="javascript:goToProfile()">
                    <fmt:message key="header.admin.profile"/>
                </a>
                <a class="dropdown-item" href="javascript:goToSettings()">
                    <fmt:message key="header.admin.settings"/>
                </a>
                <a class="dropdown-item" href="javascript:signOut()">
                    <fmt:message key="header.admin.sign-out"/>
                </a>
            </div>
        </li>
    </ul>
</nav>
<form id="profile-form" action="profile" method="get"></form>
<form id="business-profile-form" action="business-profile" method="post"></form>
<form id="my-auction-form" action="my-auctions" method="post"></form>
<form id="settings-form" action="settings" method="get"></form>
<form id="welcome-form" action="welcome" method="get"></form>
<form id="add-auction-form" action="add-auction" method="get"></form>
<%--<form id="request-auction-form" action="request" method="get"></form>--%>

<script type="text/javascript">
    $(function () {
        $('#change_language_form select[name=language]').change(function () {
            this.form.submit();
        });
    });
</script>