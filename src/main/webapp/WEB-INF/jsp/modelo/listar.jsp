<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<template:admin>
	<jsp:attribute name="extraScripts">
		<script src="<c:url value='/assets/js/jquery.jqpagination.js'/>"></script>
	</jsp:attribute>
	<jsp:body>
	<div class="row" style="margin-bottom: 5px;">
	
		<div class="col-md-12">
			<section class="panel">
				<header class="panel-heading">Modelos de mensagem</header>
				<div class="panel-body table-responsive">
						
					<div class="row-fluid">
						<div class="col-sm-2">
							<form action="<c:url value='/modelo/cadastro' />">
								<button class="btn btn-sm btn-primary pull-left">
									<i class="fa fa-plus"></i> Adicionar
								</button>
							</form>
						</div>
						<div class="col-sm-6">
								<form action="<c:url value='/modelo' />">						        
									<div class="input-group">
										  <div class="input-group-btn">
									        <a type="submit" href="<c:url value='/modelo' />"
												class="btn btn-sm btn-default">
													     Todos	
											</a>
									      </div>
									      <input type="text" id="procurar-modelo" name="busca"
											class="form-control input-sm" placeholder="Buscar por conteúdo" />
										  <div class="input-group-btn">
										        <button id="btn-busca" type="submit"
												disabled="disabled" class="btn btn-sm btn-default">
														      <i class="fa fa-search"></i>		
												</button>
									      </div>
										  
								    </div>
								</form>
						</div>
					</div>
					<br />
					<hr />
					<table class="table table-condensed table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>Conteúdo</th>
								<th>Tipo de envio</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${modelosPaginados.currentList}" var="modelo">
								<tr>
									<td>${modelo.conteudo}</td>
									<td>${modelo.tipoDeEnvio}</td>
									<td>
										<a href="<c:url value='/modelo/alterar/${modelo.id}' />">
											<span class="btn btn-success btn-sm">Editar</span>
										</a>
										<button type="button"
												class="btn btn-danger btn-sm btn-remover"
												data-elemento="Deseja remover o modelo ${modelo.conteudo}?"
												data-url-remocao="<c:url value='/modelo/remover/${modelo.id}' />"
												data-toggle="modal" data-target="#modal-remover">
													Remover
										</button>
									</td>
								</tr>
							</c:forEach>
							
						
							</tbody>
					</table>
					  <template:paginationComponent search="${termoDeBusca}"
							paginatedList="${modelosPaginados}" page="${param.page}"
							action="${urlLista}" />

				</div>
			</section>
		</div>
	</div>
	<template:confirmaRemover />
	</jsp:body>

</template:admin>