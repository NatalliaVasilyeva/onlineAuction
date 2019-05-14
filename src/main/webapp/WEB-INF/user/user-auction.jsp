<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://minsk.by/auction/functions" prefix="f" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="${sessionScope.locale}">
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="user.business-profile.title"/></title>
    <jsp:include page="../include/common.html"/>
    <jsp:include page="../include/i18n.html"/>
    <script src="../../js/user/user-auction.js"></script>
</head>
<body>
<%@include file="../../fragment/error_message.jsp" %>


<div class="container-fluid">
    <div class="row-fluid">
        <%@include file="/fragment/error_message.jsp" %>
        <%@include file="/fragment/success_message.jsp" %>

        <button type="button" class="btn btn-primary btn-lg btn-block" onclick="showMyLastAuction()">My last lot
        </button>
        <div class="col-md px-0" id="show-my-last-auction" style="display: none">

            <div class="alert alert-secondary" align="center"><fmt:message
                    key="page.breadcrumbs_last_auction"/></div>

            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6 col-md-4 col-xl-3">
                        <div class="card mb-4 shadow-sm">
                            <img class="card-img-top" src="http://placehold.it/500x500"
                                 alt="Card image cap">
                            <div class="card-body">
                                <table>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.since"/>:</td>
                                        <td>${f:formatLocalDateTime(lastAuction.startTime, 'HH:mm dd.MM.yyyy')}</td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.untill"/>:</td>
                                        <td>${f:formatLocalDateTime(lastAuction.finishTime, 'HH:mm dd.MM.yyyy')}</td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.description"/>:</td>
                                        <td>${lastAuction.description}</td>
                                    </tr>
                                </table>

                            </div>
                            <div class="card-footer">
                                <div class="row">
                                    <div class="col-4">
                                        <input type="submit" class="btn btn-outline-primary btn-block"
                                               value="${show_auction}">
                                    </div>
                                    <div class="col-4">
                                        <input type="submit" class="btn btn-outline-primary btn-block"
                                               value="${show_auction}">
                                    </div>
                                    <div class="col-4">
                                        <input type="submit" class="btn btn-outline-primary btn-block"
                                               value="${show_auction}">
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>


</body>
</html>
