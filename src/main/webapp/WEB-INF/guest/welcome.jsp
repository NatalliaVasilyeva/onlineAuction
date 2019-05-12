<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cust" uri="/WEB-INF/tld/customTwo.tld" %>
<html>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="guest.welcome.title"/></title>
    <jsp:include page="/WEB-INF/include/common.html"/>
    <jsp:include page="/WEB-INF/include/i18n.html"/>
</head>
<body>
<jsp:include page="/WEB-INF/header/header.jsp"/>
<div class="container">
    <div align="center">
        <h1 id="h1-welcome">
            <fmt:message key="guest.welcome.h-message"/>
            <cust:helloTag/>
        </h1>
    </div>
</div>
<H2>Hello</H2>
<%@include file="/fragment/error_message.jsp"%>
<%@include file="/fragment/success_message.jsp"%>
</body>
</html>
