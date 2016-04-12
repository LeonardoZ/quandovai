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
				<header class="panel-heading">Suas mensagens</header>
				<div class="panel-body table-responsive">
						
					<div class="row-fluid">
						<div class="col-sm-2">
							<form action="<c:url value='/envio/cadastro' />">
								<button class="btn btn-sm btn-primary pull-left">
									<i class="fa fa-plus"></i> Adicionar
								</button>
							</form>
						</div>
						<div class="col-sm-6">
								<form action="<c:url value='/envio' />">						        
									<div class="input-group">
										  <div class="input-group-btn">
									        <a type="submit" href="<c:url value='/envio' />"
												class="btn btn-sm btn-default">
													     Todos	
											</a>
									      </div>
									      <input type="text" id="procurar-envio" name="busca"
											class="form-control input-sm"
											placeholder="Buscar por conteúdo" />
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
					<table
							class="table table-condensed table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>Conteúdo</th>
								<th>Cliente</th>
								<th>Tipo de envio</th>
								<th>Enviar em</th>
								<th>Status de entrega</th>
								<th>Provedor</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
						<c:when test="${not empty mensagensPaginadas.currentList}">
							<c:forEach items="${mensagensPaginadas.currentList}" var="envio">
								<tr>
									<td>${envio.mensagem.conteudo}</td>
									<td>${envio.cliente.nomeCompleto}</td>
									<td>${envio.mensagem.tipoDeEnvio}</td>
									<td><fmt:formatDate value="${envio.dateHoraDeEnvio}" /></td>
									<td>${envio.status}</td>
									<td>${envio.provedor}</td>
									<td>
										<a href="<c:url value='/envio/alterar/${envio.id}' />">
											<span class="btn btn-success btn-sm">Editar</span>
										</a>
										<button type="button"
														class="btn btn-danger btn-sm btn-remover"
														data-elemento="Deseja remover a mensagem?"
														data-url-remocao="<c:url value='/envio/remover/${envio.id}' />"
														data-toggle="modal" data-target="#modal-remover">
													Remover
										</button>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								Nenhuma mensagem registrada.
							</tr>
						</c:otherwise>	
						</c:choose>
					</tbody>
					</table>
					  <template:paginationComponent search="${termoDeBusca}"
							paginatedList="${mensagensPaginadas}" page="${param.page}"
							action="${urlLista}" />

				</div>
			</section>
		</div>
	</div>
	<template:confirmaRemover />
	</jsp:body>
</template:admin>