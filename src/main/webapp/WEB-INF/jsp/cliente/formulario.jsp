<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="erro"%>

<div class="form-group">
	<label for="cliente.nomeCompleto"> Nome completo </label> <input
		class="form-control" name="cliente.nomeCompleto"
		value="${cliente.nomeCompleto}" placeholder="Nome completo"
		required="required" /> <span class="text-danger">${errors.from('nomeCompleto')}</span>
</div>

<div class="form-group">
	<label for="cliente.sexo"> Sexo</label> <select name="cliente.sexo"
		class="form-control">
		<c:forEach var="sexo" items="${sexos}">
			<option value="${sexo}" ${sexo == cliente.sexo ? 'selected' : ''}>${sexo}</option>
		</c:forEach>
	</select> <span class="text-danger">${errors.from('sexo')}</span>
</div>
<div class="form-group">
	<label for="cliente.dataAniversario">Aniversário</label> <input
		class="form-control date-field" name="cliente.dataAniversario"
		value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${cliente.dataAniversario}"/>" /> <span
		class="text-danger">${errors.from('dataAniversario')}</span>
</div>


<div class="form-group">
	<label for="cliente.email"> E-mail</label> <input class="form-control"
		name="cliente.email" value="${cliente.email}" placeholder="E-mail" />
	<span class="text-danger">${errors.from('email')}</span>
</div>

<div class="form-group">
	<label for="cliente.celular"> Celular</label>
	<div class="row">
		<div class="col-md-3">
			<select class="tipo-telefone form-control" data-fone-input="celular">
				<option value="9digits">9 Digitos</option>
				<option value="8digits">8 Digitos</option>
			</select>
		</div>
		<div class="col-md-9">
			<input id="celular" class="form-control cellphone9"
				name="cliente.celular" value="${cliente.celular}"
				placeholder="Celular" /> <span class="text-danger">${errors.from('celular')}</span>
		</div>
	</div>
</div>

<div class="form-group">
	<label for="cliente.celular2"> Celular 2</label>
	<div class="row">
		<div class="col-md-3">
			<select class="tipo-telefone form-control" data-fone-input="celular2">
				<option value="9digits">9 Digitos</option>
				<option value="8digits">8 Digitos</option>
			</select>
		</div>
		<div class="col-md-9">
			<input id="celular2" class="form-control cellphone9"
				name="cliente.celular2" value="${cliente.celular2}"
				placeholder="Celular 2" /> <span class="text-danger">${errors.from('celular2')}</span>
		</div>
	</div>
</div>