<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<template:admin>
	<jsp:body>
	<div class="row" style="margin-bottom: 5px;">
		<div class="col-md-6">
			 <h2 class="header">Alterar cliente</h2>
      			<form role="form" class="well" id="formCadastroCliente"
      				action="<c:url value='/cliente/atualizar/${cliente.id}'/>" method="POST">
					<%@include file="formulario.jsp"%>
					<button type="submit" class="btn btn-primary">Atualizar</button> 
					<button type="reset" class="btn btn-default">Limpar</button> 
				</form>
		</div>
	</div>
	</jsp:body>
</template:admin>