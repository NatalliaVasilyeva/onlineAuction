<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty successMessage}">
	<div class="alert alert-success">
		<p>${successMessage}</p>
	</div>
</c:if>
