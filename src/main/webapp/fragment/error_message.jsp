
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty errorMessage}">
	<div class="alert alert-danger">
		<p>${errorMessage}</p>
	</div>
</c:if>
