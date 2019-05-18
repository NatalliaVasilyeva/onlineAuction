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
    <script src="../../js/user/user-auctions.js"></script>
</head>
<body>
<%@include file="../../fragment/error_message.jsp" %>

<jsp:include page="../header/header.jsp"/>
<div class="container-fluid">
    <div class="row-fluid">
        <%@include file="/fragment/error_message.jsp" %>
        <%@include file="/fragment/success_message.jsp" %>

        <button type="button" class="btn btn-primary btn-lg btn-block" onclick="showMyLastAuction()">My last auction
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
                                <table table-editable id="edit-auction-card-last-auction-table">
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.id"/>:</td>
                                        <%--                                        <td contenteditable="false">--%>
                                        <td><input type="text" class="form-control" id="my-last-auction-id"
                                                   name="auction_id"
                                                   value="${lastAuction.id}" readonly/></td>
                                        <%--                                            ${lastAuction.id}</td>--%>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.since"/>:</td>
                                        <%--                                        <td contenteditable="false" id="my-last-auction-start-time1">--%>
                                        <td><input type="text" class="form-control"
                                                   id="my-last-auction-start-time" name="auction-start-time"
                                                   value="${f:formatLocalDateTime(lastAuction.startTime, "yyyy-MM-dd'T'HH:mm")}"
                                                   readonly/></td>
                                        <%--                                            ${f:formatLocalDateTime(lastAuction.startTime, 'HH:mm dd.MM.yyyy')}</td>--%>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.untill"/>:</td>
                                        <%--                                        <td contenteditable="false" id="my-last-auction-finish-time1">--%>
                                        <td><input type="text" class="form-control"
                                                   id="my-last-auction-finish-time" name="auction-finish-time"
                                                   value="${f:formatLocalDateTime(lastAuction.finishTime, "yyyy-MM-dd'T'HH:mm")}"
                                                   readonly/></td>
                                        <%--                                            ${f:formatLocalDateTime(lastAuction.finishTime, 'HH:mm dd.MM.yyyy')}</td>--%>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.description"/>:</td>
                                        <%--                                        <td contenteditable="false" id="my-last-auction-description1">--%>
                                        <td><input type="text" class="form-control" id="my-last-auction-description"
                                                   name="auction-description"
                                                   value="${lastAuction.description}" readonly/></td>
                                        <%--                                            ${lastAuction.description}</td>--%>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="business-profile.user.auction-set.type"/>:</td>
                                        <td><input type="text" class="form-control" id="my-last-auction-type"
                                                   name="auction_type"
                                                   value="${lastAuction.auctionType}" readonly/></td>
                                        <%--                                        <td>${lastAuction.auctionType}</td>--%>
                                    </tr>
                                </table>

                            </div>
                            <div class="card-footer" id="footer-button">
                                <div class="row">
                                    <div class="col-4">
                                        <input type="button" class="btn btn-outline-primary btn-block"
                                               onclick="MakeEditable()"
                                               value=<fmt:message
                                                key="business-profile.user.auction-set.edit-auction"/>/>
                                    </div>
                                    <div class="col-4">
                                        <input type="button" class="btn btn-outline-primary btn-block"
                                               onclick="DeleteAuctionServerCall()"
                                               value=<fmt:message
                                                key="business-profile.user.auction-set.delete-auction"/>/>
                                    </div>
                                    <div class="col-4">
                                        <input type="button" class="btn btn-outline-primary btn-block"
                                               value=<fmt:message key="business-profile.user.auction-set.add-lot"/>/>
                                    </div>
                                </div>
                            </div>

                            <div class="card-footer" id="footer-edit-button" style="display: none">
                                <div class="footer title">Do you shure to edit auction?</div>
                                <div class="row">
                                    <div class="col-4">
                                        <input type="button" class="btn btn-outline-primary btn-block"
                                               onclick="EditAuctionServerCall()"
                                               value=
                                        <fmt:message
                                                key="business-profile.user.auction-set.edit-auction-yes"/>>
                                    </div>
                                    <div class="col-4">
                                        <input type="button" class="btn btn-outline-primary btn-block"
                                               onclick="CancelEditAuction()"
                                               value=
                                        <fmt:message
                                                key="business-profile.user.auction-set.edit-auction-no"/>>
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
