<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<template:admin>
	<jsp:attribute name="extraScripts">
		<script src="<c:url value='/assets/js/jquery.jqpagination.js'/>"></script>
		<script src="<c:url value='/assets/js/bootstrap3-typeahead.min.js'/>"></script>
		<script src="<c:url value='/assets/js/quandovai-envio.js'/>"></script>
	</jsp:attribute>
	<jsp:body>
	<div class="row" style="margin-bottom: 5px;">
	
		<div class="col-md-12">
			<div class="well form-large">
				<ul class="nav nav-tabs nav-justified">
					  <li class="active"><a data-toggle="tab" href="#sms">SMS</a></li>
 					  <li><a data-toggle="tab" href="#menu1">E-mail</a></li>
				</ul>
				<div class="tab-content" style="background: white;">
				  <div id="sms" class="tab-pane fade in active well"
							style="background: white; margin-top: 0px;">
						<form style="padding: 10px">
							<h3>Conteúdo</h3>
							<div class="form-group">
								<label>Usar modelo?</label>
								<select id="seleciona-modelo" 
										class="form-control input-sm">
									<c:forEach var="modelo" items="${modelos}">
										<option value="${modelo}"> 
											${modelo}
										</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Conteúdo</label>
								<textarea rows="2" id="txt-conteudo"
										name="envio.mensagem.conteudo" class="form-control input-sm">${preparo.conteudo}</textarea>
								<span id="quantidade-chars" class="text-info">Caractéres: 0</span>
								<span id="quantidade-mensagens" class="text-info pull-right">Mensagens: 1</span>
								<span class="text-danger">${errors.from('conteudo')}</span>
							</div>
						<hr />
						
						<h3>Destinatário</h3>
						<button type="button" class="btn btn-danger btn-sm btn-remover"
									data-toggle="modal" data-target="#modal-seleciona-clientes">
												Selecionar cliente(s)
						</button>
						<div class="form-group">
							 <ul id="clientes-selecionados" class="list-group scroll">
							 </ul>
						</div>
						<hr />
						
						<h3>Envio</h3>
						<div class="form-group">
							<label>Provedor</label>
							<select id="provedor" name="preparo.provedor" class="form-control input-sm">
								<c:forEach items="${provedores}" var="provedor">
									<option value="${provedor}">${provedor}</option>
								</c:forEach>
							</select>  
							<span class="text-danger">${errors.from('provedor')}</span>
						</div>
						<div class="row">
							<div class="form-group col-md-4">
								<label>Quantidade</label>
								<input name="preparo.quantidade" id="quantidade"
											class="form-control input-sm" type="number" />  
								<span class="text-danger">${errors.from('quantidade')}</span>
							</div>
							<div class="form-group col-md-4">
								<label>Período de intervalo</label>
								<select name="preparo.periodo" id="periodo"
											class="form-control input-sm">
									<c:forEach items="${periodos}" var="periodo">
										<option value="${periodo}">${periodo}</option>
									</c:forEach>
								</select>
								<span class="text-danger">${errors.from('periodo')}</span>
							</div>
							<div class="col-md-4">
							 	<div class="form-group">
					                <label>Data/Hora base</label>
					                <div class='input-group date' id='grupo-data-base'>
					                    <input type='text' class="form-control input-sm" name="preparo.dataBase"
					                    	 id="data-base" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>
					                </div>
				             	</div>
							</div>
						</div>
						
						<button type="button" id="btn-calcula-envios" class="btn btn-primary pull-left"
									disabled="disabled">Calcular envios</button>
					
						<div class="form-group">
							 <ul id="envios-sms" class="list-group scroll">
								<c:forEach items="${envios}" var="envio">
									<li class="list-group-item">${envio.mensagem.dateHoraDeEnvio} - <span
												class="pull-right">&times</span></li>
								</c:forEach>
							 </ul>
						</div>
						<hr />
				
						<button  type="button"  id="preparar-envio" class="btn btn-success pull-right" disabled="disabled">Preparar envio</button>
						<hr />
					
					</form>		
				  </div>
				  <div id="menu1" class="tab-pane fade">
				    <h3>Menu 1</h3>
				    <p>Some content in menu 1.</p>
				  </div>
				</div>
				
			</div>		
		</div>
	</div>
	<template:selecionaClientes />
	
	</jsp:body>
</template:admin>