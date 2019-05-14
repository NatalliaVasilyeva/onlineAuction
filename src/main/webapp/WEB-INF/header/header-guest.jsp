<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<script src="${pageContext.request.contextPath}/js/header/header-guest.js"></script>
<div style="display: none">
    <form id="welcome-form" action="welcome" method="get"></form>
</div>
<nav class="navbar navbar-light" style="background-color: #e3f2fd;">

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

    <ul class="navbar-nav">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
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


    <div class="d-flex flex-row">
        <div class="p-2">
            <a href="" data-toggle="modal" data-target="#sign-in-modal">
                <fmt:message key="form.login"/>
            </a>
        </div>
        <div class="p-2">
            <a href="" data-toggle="modal" data-target="#sign-up-modal">
                <fmt:message key="form.register"/>
            </a>
        </div>
    </div>
</nav>
<div class="modal fade" id="sign-in-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.login"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/sign-in.jsp"/>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="sign-up-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title">
                    <fmt:message key="form.register"/>
                </h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/WEB-INF/form/sign-up.jsp"/>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#change_language_form select[name=language]').change(function () {
            this.form.submit();
        });
    });
</script>