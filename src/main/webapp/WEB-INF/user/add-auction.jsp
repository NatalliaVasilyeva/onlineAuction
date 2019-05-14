<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="user.add-auction.title"/></title>
    <jsp:include page="../include/common.html"/>
    <link rel="stylesheet" href="../../css/input-area.css">
    <script src="../../js/form/add-auction.js"></script>
</head>
<body>
<jsp:include page="../header/header.jsp"/>
<%--<div class="container-fluid h-100 bg-light content">--%>
<div class="container">
    <div class="alert alert-secondary" align="center"><fmt:message key="page.breadcrumbs_add_auction"/></div>
    <%--    <div class="row mx-0 justify-content-center align-items-center text-light">--%>
    <form class="form-horizontal">
        <div class="form-group row">
            <label for="add-auction-start-time-input" class="col-2 col-form-label">
                <fmt:message key="user.add-auction.start-time"/>
            </label>
            <div class="col-10">
                <input type="datetime-local" class="form-control" placeholder="${start-time}"
                       id="add-auction-start-time-input" required/>
                <small class="text-danger" id="add-auction-start-time-error-small"></small>
            </div>
        </div>
        <div class="form-group row">
            <label for="add-auction-finish-time-input" class="col-2 col-form-label">
                <fmt:message key="user.add-auction.finish-time"/>
            </label>
            <div class="col-10">
                <input type="datetime-local" class="form-control" placeholder="${finish-time}"
                       id="add-auction-finish-time-input" required/>
                <small class="text-danger" id="add-auction-finish-time-error-small"></small>
            </div>
        </div>
        <div class="form-group row">
            <label for="add-auction-description-input" class="col-2 col-form-label">
                <fmt:message key="user.add-auction.description"/>
            </label>
            <div class="col-10">
                    <textarea class="form-control" rows="5"
                              id="add-auction-description-input" placeholder="${description}" required></textarea>
                <small class="text-danger" id="add-auction-description-error-small"></small>
            </div>
        </div>
        <div class="form-group row">
            <label for="add-auction-type-input" class="col-2 col-form-label">
                <fmt:message key="user.add-auction.type"/>
            </label>
            <div class="col-10">
                <select id="add-auction-type-input" required>
                    <option value="direct" selected>
                        <fmt:message key="form.direct"/>
                    </option>
                    <option value="reverse">
                        <fmt:message key="form.reverse"/>
                    </option>
                </select>
                <small class="text-danger" id="add-auction-type-error-small"></small>
            </div>
        </div>

        <div class="form-group row">
            <button type="button" class="form-control btn btn-light" onclick="addAuctionServerCall()">
                <fmt:message key="user.add-auction.submit-button"/>
            </button>
        </div>

    </form>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger"><p>${errorMessage}</p></div>
    </c:if>
    <%--    </div>--%>
</div>
<footer>
    <%@include file="../footer/footer.jsp" %>
</footer>
</body>
</html>
