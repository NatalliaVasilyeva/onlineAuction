<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang.toString()}"/>
<fmt:setBundle basename="bundle"/>
<script src="../../js/form/edit-lot.js"></script>
<form>
    <div class="form-group">
        <fmt:message key="form.edit-lot.id" var="id"/>
        <input type="text" class="form-control" id="edit-lot-id-input" name="id" readonly/>
        <small class="text-danger" id="edit-lot-id-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.edit-lot.name" var="name"/>
        <input type="text" class="form-control" id="edit-lot-name-input" name="name"/>
        <small class="text-danger" id="edit-lot-name-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.edit-lot.description" var="description"/>
        <input type="text" class="form-control" id="edit-lot-description-input" name="description"/>
        <small class="text-danger" id="edit-lot-description-error-small"></small>
    </div>
    <div class="form-group">
        <fmt:message key="form.edit-lot.category" var="category"/>
        <select class="form-control" id="edit-lot-category-input" name="category">
            <option value="car"><fmt:message key="form.edit-lot.category.car"/></option>
            <option value="picture"><fmt:message key="form.edit-lot.category.picture"/></option>
            <option value="computer"><fmt:message key="form.edit-lot.category.computer"/></option>
            <option value="phone"><fmt:message key="form.edit-lot.category.phone"/></option>
        </select>
        <small class="text-danger" id="edit-lot-category-error-small"></small>
    </div>
    <div class="form-group">
        <button type="button" class="form-control btn btn-light" onclick="editLotServerCall()">
            <fmt:message key="form.submit"/>
        </button>
    </div>
</form>
