<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<script src="../../js/header/header-admin.js"></script>
<nav class="navbar navbar-expand-lg bg-light">
    <a class="nav-brand" href="/"><fmt:message key="header.english_auction"/> <span class="fa fa-leaf"></span></a>
    <ul class="navbar-nav mr-auto">
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
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown"aria-haspopup="true" aria-expanded="false" >
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
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <fmt:message key="header.admin.manage"/>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                <a class="dropdown-item" href="javascript:goToManageAuctions()">
                    <fmt:message key="header.admin.manage.auctions"/>
                </a>
                <a class="dropdown-item" href="javascript:goToManageRequests()">
                    <fmt:message key="header.admin.manage.requests"/>
                </a>
                <a class="dropdown-item" href="javascript:goToManageUsers()">
                    <fmt:message key="header.admin.manage.users"/>
                </a>
            </div>
        </li>
    </ul>
    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
<form id="welcome-form" action="welcome" method="get"></form>
<form id="business-profile-form" action="business-profile" method="get"></form>
<form id="profile-form" action="profile" method="get"></form>
<form id="settings-form" action="settings" method="get"></form>
<form id="load-auction-table-form" action="manage-auctions" method="get"></form>
<form id="manage-requests-form" action="manage-requests" method="get"></form>
<form id="manage-users-form" action="manage-users" method="get"></form>
<form id="request-auction-form" action="request" method="get"></form>

<script type="text/javascript">
    $(function () {
        $('#change_language_form select[name=language]').change(function () {
            this.form.submit();
        });
    });
</script>