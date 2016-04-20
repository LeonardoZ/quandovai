<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<template:admin>
	<div class="row" style="margin-bottom: 5px;">

		<div class="col-md-3">
			<div class="sm-st clearfix">
				<span class="sm-st-icon st-red"><i
					class="fa fa-check-square-o"></i></span>
				<div class="sm-st-info">
					<span>Cadastre</span> seus clientes!
				</div>
			</div>
		</div>

		<div class="col-md-3">
			<div class="sm-st clearfix">
				<span class="sm-st-icon st-blue"><i class="fa fa-hashtag"></i></span>
				<div class="sm-st-info">
					<span>Configure</span> seu perfil.
				</div>
			</div>
		</div>

		<div class="col-md-3">
			<div class="sm-st clearfix">
				<span class="sm-st-icon st-violet"><i
					class="fa fa-calendar-o"></i></span>
				<div class="sm-st-info">
					<span>Agende</span> suas mensagens.
				</div>
			</div>
		</div>

		<div class="col-md-3">
			<div class="sm-st clearfix">
				<span class="sm-st-icon st-green"><i class="fa fa-bar-chart"></i></span>
				<div class="sm-st-info">
					<span>Gerencie</span> seus envios.
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="well" style="background-color: white">
			<!-- Ultimas mensagens cadastradas -->
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Será enviada em: </th>
						<th>Conteúdo</th>
						<th>Cliente</th>
						<th>Provedor</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody id="mensagens-cadastradas-tbody">
													
				</tbody>
			</table>
	    	<div class="text-center">
	    		<input type="hidden" id="mensagens-cadastradas-page" value="0" />
	    		<button id="mensagens-cadastradas-prev" disabled="disabled" type="button" class="btn btn-default btn-xs">
	    			<i class="fa fa-arrow-left" aria-hidden="true"></i>
				</button>
				<strong><span id="num-pagina">1</span></strong>
	    		<button id="mensagens-cadastradas-next" type="button" class="btn btn-default btn-xs">
					<i class="fa fa-arrow-right" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		</div>
	</div>
	
	
</template:admin>