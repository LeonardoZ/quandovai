<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<template:admin>
	<jsp:body>
	<div class="row" style="margin-bottom: 5px;">
		<div class="col-md-12">
			 <h2 class="header">Adicionar</h2>
      			<form role="form" class="well" id="formCadastroModelo"
      				action="<c:url value='/modelo/adicionar'/>" method="POST">
					<%@include file="formulario.jsp"%>
					<button type="submit" class="btn btn-primary">Salvar</button> 
					<button type="reset" class="btn btn-default">Limpar</button> 
				</form>
		</div>
	</div>
	</jsp:body>
</template:admin>