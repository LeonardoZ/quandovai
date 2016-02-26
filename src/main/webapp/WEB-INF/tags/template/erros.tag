<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty errors}">
	<div class="alert alert-danger alert-dismissible" role="alert">
		<a type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</a>
		<c:forEach var="error" items="${errors}">
		${error.category} - ${error.message}<br />
		</c:forEach>
	</div>
</c:if>