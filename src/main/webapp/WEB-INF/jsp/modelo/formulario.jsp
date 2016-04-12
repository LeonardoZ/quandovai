<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="erro"%>

<div class="form-group">
	<label for="modelo.conteudo"> Conteúdo</label> <input
		class="form-control" name="modelo.conteudo"
		value="${modelo.conteudo}" placeholder="Conteúdo"
		required="required" /> <span class="text-danger">${errors.from('nomeCompleto')}</span>
</div>

<div class="form-group">
	<label for="modelo.tipoDeEnvio"> Tipo de envio</label> 
	<select name="modelo.tipoDeEnvio"
		class="form-control">
		<c:forEach var="tipo" items="${tiposDeEnvio}">
			<option value="${tipo}" ${tipo == modelo.tipoDeEnvio ? 'selected' : ''}>${tipo}</option>
		</c:forEach>
	</select> <span class="text-danger">${errors.from('tipo')}</span>
</div>
