<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib uri="http://minsk.by/auction/tag" prefix="f" %>--%>
<%@taglib uri="http://minsk.by/auction/functions" prefix="f" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="${sessionScope.locale}">
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="user.business-profile.title"/></title>
    <jsp:include page="../include/common.html"/>
    <jsp:include page="../include/i18n.html"/>
    <%--    <script src="../../js/quick-view.js"></script>--%>
    <script src="../../js/user/business-profile.js"></script>
</head>
<body>
<%--<jsp:include page="../header/header.jsp"/>--%>
<%--<div class="container-fluid content">--%>
<%--    <div class="row">--%>
<%--        <c:if test="${user.role == 'USER'}">--%>
<%--            <div class="col-md-2">--%>
<%--                <div class="collapse navbar-collapse show"--%>
<%--                     id="collapsibleNavbar">--%>
<%--                        &lt;%&ndash; Sidebar &ndash;%&gt;--%>
<%--                    <nav class="sidebar categories h-100 py-5 px-4">--%>
<%--                        <c:if test="${not empty user}">--%>
<%--                            <c:if test="${user.role == 'USER'}">--%>
<%--                                <form method="POST" action="view-auction-set">--%>
<%--                                    <input type="submit"--%>
<%--                                           class="submit btn btn-outline-primary btn-block"--%>
<%--                                           value=<fmt:message key="form.view_auction_set"/> name="submit" required/>--%>
<%--                                </form>--%>
<%--                            </c:if>--%>
<%--                        </c:if>--%>
<%--                    </nav>--%>
<%--                        &lt;%&ndash; /Sidebar &ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </c:if>--%>
<%--        <div class="col-md px-0">--%>
<%--            <div class="alert alert-secondary"> <fmt:message key="page.breadcrumbs_lot_set"/></div>--%>
<%--            <%@include file="/fragment/error_message.jsp"%>--%>
<%--            <%@include file="/fragment/success_message.jsp"%>--%>
<%--            <div class="container-fluid">--%>
<%--                <div class="row">--%>
<%--                <h1><fmt:message key="user.business-profile.myLots"/></h1>--%>
<%--                <!-- Card -->--%>
<%--                <c:forEach items="${requestScope.myLots}" var="lot">--%>
<%--                    <div class="col-sm-6 col-md-4 col-xl-3">--%>
<%--                        <div class="card mb-4 shadow-sm">--%>
<%--                            <img class="card-img-top" src="http://placehold.it/500x500"--%>
<%--                                 alt="Card image cap">--%>
<%--                            <div class="card-body">--%>
<%--                                <p class="card-text">${lot.name}</p>--%>
<%--                                <form method="POST" action="controller">--%>
<%--                                    <input type="hidden" name="command" value="view_lot">--%>
<%--                                    <input type="hidden" name="nonce" value="${nonce}"/>--%>
<%--                                    <input type="hidden" name="lot_id" value="${lot.id}">--%>
<%--                                    <input type="submit" class="btn btn-outline-primary btn-block"--%>
<%--                                           value="${show_lot}">--%>
<%--                                </form>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </c:forEach>--%>
<%--                <!-- /Card -->--%>
<%--            </div>--%>

<%--                <div class="row">--%>
<%--                    <h1><fmt:message key="user.business-profile.myBids"/></h1>--%>
<%--                    <!-- Card -->--%>
<%--                    <c:forEach items="${requestScope.myBidLots}" var="bidLot">--%>
<%--                        <div class="col-sm-6 col-md-4 col-xl-3">--%>
<%--                            <div class="card mb-4 shadow-sm">--%>
<%--                                <img class="card-img-top" src="http://placehold.it/500x500"--%>
<%--                                     alt="Card image cap">--%>
<%--                                <div class="card-body">--%>
<%--                                    <p class="card-text">${bidLot.name}</p>--%>
<%--                                    <form method="POST" action="controller">--%>
<%--                                        <input type="hidden" name="command" value="view_lot">--%>
<%--                                        <input type="hidden" name="nonce" value="${nonce}"/>--%>
<%--                                        <input type="hidden" name="lot_id" value="${lot.id}">--%>
<%--                                        <input type="submit" class="btn btn-outline-primary btn-block"--%>
<%--                                               value="${show_lot}">--%>
<%--                                    </form>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
<%--                    <!-- /Card -->--%>
<%--                </div>--%>
<%--                &lt;%&ndash; Pagination &ndash;%&gt;--%>
<%--                <ul class="pagination">--%>
<%--                    <li class="page-item disabled"><a class="page-link" href="#">&laquo;</a>--%>
<%--                    </li>--%>
<%--                    <li class="page-item active"><a class="page-link" href="#">1</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">4</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">5</a></li>--%>
<%--                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>--%>
<%--                </ul>--%>
<%--                &lt;%&ndash; /Pagination &ndash;%&gt;--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<jsp:include page="../header/header.jsp"/>
<div class="container-fluid">
    <div class="row-fluid">

        <button type="button" class="btn btn-primary btn-lg btn-block" onclick="showMylots()">My lots</button>

        <div class="row top-buffer" id="show-my-lots" style="display: none">
            <div class="col-6">
                <table class="table table-bordered" id="lot-table">
                    <h1><fmt:message key="user.business-profile.my-lots.captions"/></h1>
                    <thead>
                    <tr>
                        <th><fmt:message key="th.business-profile.my-lots-th.image"/></th>
                        <th><fmt:message key="th.id.my-lots"/></th>
                        <th><fmt:message key="user.business-profile.my-lots-th.name"/></th>
                        <th><fmt:message key="user.business-profile.my-lots-th.description"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.category"/></th>
                        <th><fmt:message key="th.id.auction"/></th>
                        <th><fmt:message key="th.action-start-time"/></th>
                        <th><fmt:message key="th.action-finish-time"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.blocked"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.paid"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.myLots}" var="lot">
                        <tr>
                            <td>
                                <img src="${lot.imagePath}">
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.id}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.name}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.description}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.category}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.auctionId}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${f:formatLocalDateTime(lot.startTime, 'HH:mm dd.MM.yyyy')}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${f:formatLocalDateTime(lot.finishTime, 'HH:mm dd.MM.yyyy')}
                                </div>

                            </td>
                            <td>
                                <div class="data">
                                        ${lot.isBlocked}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${lot.isPaid}
                                </div>
                            </td>
                            <td width=20%>
                                <button type=button class="btn btn-light" onclick="editLot(this)"><span
                                        class="fa fa-pencil"></span> <fmt:message
                                        key="business-profile.my-lots.edit-button"/>
                                </button>
                                <button type=button class="btn btn-light" onclick="removeLot(this)"><span
                                        class="fa fa-trash"></span><fmt:message
                                        key="business-profile.my-lots.delete-button"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="row-fluid">
        <button type="button" class="btn btn-primary btn-lg btn-block" onclick="showMyBids()">My bids</button>

        <%--        <div class="row-fluid" id="show-my-bids" style="display: none">--%>
        <div class="row top-buffer" id="show-my-bids" style="display: none">
            <div class="col-6">
                <table class="table table-bordered" id="bid-table">
                    <h1><fmt:message key="user.business-profile.my-bids.captions"/></h1>
                    <thead>
                    <tr>
                        <th><fmt:message key="th.business-profile.my-lots-th.image"/></th>
                        <th><fmt:message key="th.id.my-lots"/></th>
                        <th><fmt:message key="user.business-profile.my-lots-th.name"/></th>
                        <th><fmt:message key="user.business-profile.my-lots-th.description"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.category"/></th>
                        <th><fmt:message key="th.id.auction"/></th>
                        <th><fmt:message key="th.action-start-time"/></th>
                        <th><fmt:message key="th.action-finish-time"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.blocked"/></th>
                        <th><fmt:message key="th.business-profile.my-lots-th.paid"/></th>
                        <th><fmt:message key="th.business-profile.my-bids-th.ownerId"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.myBidLots}" var="bidLot">
                        <tr>
                            <td>
                                <img src="${bidLot.imagePath}">
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.id}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.name}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.description}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.category}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.auctionId}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${f:formatLocalDateTime(bidLot.startTime, 'HH:mm dd.MM.yyyy')}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${f:formatLocalDateTime(bidLot.finishTime, 'HH:mm dd.MM.yyyy')}
                                </div>

                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.isBlocked}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.isPaid}
                                </div>
                            </td>
                            <td>
                                <div class="data">
                                        ${bidLot.sellerId}
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="edit-lot-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title"><fmt:message key="modal.edit.lot.title"/></h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="../form/edit-lot.jsp"/>
            </div>
        </div>
    </div>
</div>

</body>
</html>
