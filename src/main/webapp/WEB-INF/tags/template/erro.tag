<%@ tag
	language="java"
	pageEncoding="ISO-8859-1"%>
<%@taglib
	uri="http://java.sun.com/jsp/jstl/core"
	prefix="c"%>
<%@attribute
	name="field"
	required="true"
	type="java.lang.String"%>
<c:if test=" ${not empty errors.from(field)} ">
	<c:forEach
		var="error"
		items="${errors.from(field)}">
		<span class="text-danger">${error.message}</span>
		<br />
	</c:forEach>
</c:if>