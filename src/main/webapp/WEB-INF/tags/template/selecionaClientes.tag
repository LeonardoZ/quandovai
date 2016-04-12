<%@taglib tagdir="/WEB-INF/tags/template" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Seleciona clientes -->

<div id="modal-seleciona-clientes" class="modal fade" tabindex="-1"
	role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Selecione o(s) destinatário(s) das mensagens</h4>
			</div>
			<div class="modal-body">
				<div class="row-fluid">
					<div class="col-sm-6">
						<div class="input-group">
							<div class="input-group-btn">
								<button id="btn-todos-clientes" class="btn btn-sm btn-default"> Todos os Clientes</button>
							</div>
							<input type="text" id="procurar-cliente" name="busca"
								class="form-control input-sm" placeholder="Buscar por nome" />
							<div class="input-group-btn">
								<button id="btn-busca" disabled="disabled"
									class="btn btn-sm btn-default">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<br />
				<hr />
				<div>
					<label for="chkMarcarTodosLista">Marcar todos da lista?</label>
					<input type="checkbox" name="chkMarcarTodosLista" id="chkMarcarTodosLista" />
				</div>
				<h4>Enviar para: </h4>
				<ul id="lista-clientes" class="list-group scroll-large">
				
				</ul>
			</div>
			<div class="modal-footer">
				<button id="btn-concluir" class="btn btn-success">Concluir</button>
			</div>
		</div>
	</div>
</div>
