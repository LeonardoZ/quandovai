$(function() {

	var shared = {};

	var selecionaClientesModule = function(callback) {
		// Modal Clientes
		var $modal = $("#modal-seleciona-clientes");
		var $btnBusca = $("#btn-busca");
		var $inputBusca = $("#procurar-cliente");
		var $btnTodos = $("#btn-todos-clientes");
		var $chkMarcarTodosLista = $("#chkMarcarTodosLista");
		var $btnConcluir = $("#btn-concluir");
		var $lista = $("#lista-clientes");
		
		// Métodos busca clientes
		var avaliaPodeBuscar = function(evt) {
			var caracteres = $(this).val().length;
			if (caracteres >= 2) {
				$btnBusca.removeAttr("disabled");
			} else {
				$btnBusca.attr("disabled", "disabled");
			}
			return false;
		};

		var buscaClick = function(evt) {
			shared["cli"] = [];
			shared["cliJson"] = [];
			var nomeDoCliente = $inputBusca.val();
			var url = "/quandovai/envio/busca/cliente/"
					+ encodeURI(nomeDoCliente);
			buscaAjax(nomeDoCliente, url);
		}

		var buscaAjax = function(nomeDoCliente, url) {
			$.get(url, function(data) {
				shared["cli"] = data.clientes;
				$lista.empty();
				$(data.clientes).each(function(k, v) {
					constroiLinhaDeCliente(v);
				});
			});
		}

		var constroiLinhaDeCliente = function(cliente) {
			var $li = $("<li />", {
				"class" : "list-group-item"
			});
			var $check = $("<input />", {
				"type" : "checkbox",
				"class" : "chk-envia",
				"id" : cliente.id,
				"text" : "Enviar?"
			});
			var $btnRemove = $("<span />", {
				"html" : "&times",
				"id" : "btn-times",
				"class" : "pull-right"
			});
			$li.append($check).append($btnRemove);
			$li.append($("<span> - " + cliente.nomeCompleto + "</span>"));
			$lista.append($li);
		};

		var buscarTodosClick = function(evt) {
		}

		var marcarTodosDaLista = function() {
			var $checks = $(".chk-envia");
			var marcaTodos = this.checked;
			console.log(marcaTodos);
			if (marcaTodos === true) {
				$checks.each(function(k, v) {
					$(v).attr("checked", 'checked');
				});
			} else {
				$checks.each(function(k, v) {
					$(v).removeAttr('checked');
				});
			}
		}
		
		var toId = function(value) {
			return $(value).prop("id");
		};

		var confirmaClientes = function(evt) {
			var ids = $("ul input:checked").toArray().map(toId);
			var clis = shared["cli"];
			var selectedIds = []
			var selectedClis = [];
			for (var i = 0; i < ids.length; i++) {
				for (var j = 0; j < clis.length; j++) {
					if (ids[i] == clis[j].id) {
						selectedIds.push(ids[i]);
						selectedClis.push(clis[j]);
					}
				}
			}
			
			console.log(shared);
			console.log("IDS "+ids);
			console.log("Clis "+clis);
			shared["cli"] = selectedIds;
			shared["cliJson"] = selectedClis;
			console.log(shared);
			
			$inputBusca.val("");
			$lista.empty();
			
			callback();
			$modal.modal("hide");

		};

		// Bind Busca clientes
		$inputBusca.bind("keyup blur", avaliaPodeBuscar);
		$btnBusca.bind("click", buscaClick);
		$btnTodos.bind("click", buscarTodosClick);
		$chkMarcarTodosLista.bind("change", marcarTodosDaLista);
		$btnConcluir.bind("click", confirmaClientes);
	};

	// Modulo Envio

	var moduloEnvioSmsModule = (function(selecionaModule) {

		// Pagina de envio
		var $txtConteudo = $("#txt-conteudo");
		var $txtDataBase = $("#data-base");
		var $txtQuantidade = $("#quantidade");
		var $txtPeriodo = $("#periodo");
		var $btnCalculaEnvios = $("#btn-calcula-envios");
		var $spanQuantidadeChars = $("#quantidade-chars");
		var $spanQuantidadeMensagens = $("#quantidade-mensagens");
		var $selProvedor = $("#provedor");
		var $clientesLista = $("#clientes-selecionados");
		var $enviosLista = $("#envios-sms");

		var configuraClientesCallback = function() {
			console.log("Cientes do callback: " + shared["cli"]);
			$clientesLista.empty();
			var clientes = shared["cliJson"];
			$(clientes).each(
					function(k, cliente) {
						$li = ($("<li> " + k + " - " + cliente.nomeCompleto
								+ "</li>"));
						$clientesLista.append($li);
					});
			

		}

		// configura modal
		selecionaClientesModule(configuraClientesCallback);

		// Métodos envio sms
		var calculaValoresSmsKeyup = function(evt) {
			var conteudo = this.value, comprimento = conteudo.length;
			var quantasMensagens = Math.ceil(comprimento / 130);
			$spanQuantidadeChars.text("Caracteres: " + comprimento);
			$spanQuantidadeMensagens.text("Mensagens: " + quantasMensagens);
		}

		var avaliaCalculoDeEnvio = function(evt) {
			var dataBase = $txtDataBase.val();
			var quantidade = $txtQuantidade.val();
			var periodo = $txtPeriodo.val();
			var conteudo = $txtConteudo.val();
			var provedor = $selProvedor.val();
			var valoresPreenchidos = dataBase && quantidade && periodo
					&& provedor && conteudo;
			if (valoresPreenchidos) {
				$btnCalculaEnvios.removeAttr("disabled");
			} else {
				$btnCalculaEnvios.attr("disabled", "disabled");
			}

		}

		var calcularEnvio = function(evt) {
			var conteudo = encodeURI($txtConteudo.val());
			var provedor = encodeURI($selProvedor.val());
			var dataBase = $txtDataBase.val();
			var quantidade = encodeURI($txtQuantidade.val());
			var periodo = encodeURI($txtPeriodo.val());
			var idsClientes = shared["cli"];
			
			
			var preparo = {
				"preparo.conteudo" : conteudo,
				"preparo.provedor" : provedor,
				"preparo.dataHoraBase" : dataBase,
				"preparo.quantidade" : quantidade,
				"preparo.periodo" : periodo
			};

			var parsedIds = [];
			for(var i = 0; i < idsClientes.length; i++){
				preparo["preparo.idsClientes["+i+"]"] = idsClientes[i];
			}
			
			var url = "/quandovai/envio/calcular";
			$.ajax({
				type : "POST",
				url : url,
				cache: false,
				data : preparo,
				success : function(data) {
					var envios = data.list;
					console.log("chegou "+data);
					$enviosLista.empty();
					$(envios).each(function(k, v) {
						$enviosLista.append("<li>" + v + "</li>")
					});
				},
				dataType : "json"
			});
		}

		$("#grupo-data-base").datetimepicker({
			format : "DD/MM/YYYY HH:mm",
			locale : 'pt-br'
		});

		// Bind Envio SMS
		$txtConteudo.bind("keyup", calculaValoresSmsKeyup);
		$txtDataBase.bind("keyup blur", avaliaCalculoDeEnvio);
		$txtQuantidade.bind("keyup blur", avaliaCalculoDeEnvio);
		$txtPeriodo.bind("keyup blur", avaliaCalculoDeEnvio);
		$txtConteudo.bind("keyup blur", avaliaCalculoDeEnvio);
		$btnCalculaEnvios.bind("click", calcularEnvio);

	}(selecionaClientesModule));

});