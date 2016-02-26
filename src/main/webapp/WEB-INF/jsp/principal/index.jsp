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
				<span class="sm-st-icon st-green"><i class="fa line-chart"></i></span>
				<div class="sm-st-info">
					<span>Gerencie</span> seus envios.
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="well well-sm">
				<canvas id="chartClientes" width="600" height="400"></canvas>
			</div>
		</div>
	</div>
</template:admin>