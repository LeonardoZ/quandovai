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
			$li.append($check);
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
		
		$chkMarcarTodosLista.bind("change", marcarTodosDaLista);

		$lista.delegate("li", "click")
		
		$btnBusca.bind("click", buscaClick);
		$btnTodos.bind("click", buscarTodosClick);
		$btnConcluir.bind("click", confirmaClientes);
	};

	// Modulo Envio

	var moduloEnvioSmsModule = (function(selecionaModule) {

		// Pagina de envio
		var $txtConteudo = $("#txt-conteudo");
		var $txtDataBase = $("#data-base");
		var $txtDataBase = $("#data-base");
		var $txtDataBaseSimples = $("#data-base-simples");
		var $txtQuantidade = $("#quantidade");
		var $txtPeriodo = $("#periodo");
		
		var $btnCalculaEnvios = $("#btn-calcula-envios");
		var $btnEnvioSimples = $("#btn-envio-simples");
		var $btnSalvar = $("#btn-salvar");
		
		var $spanQuantidadeChars = $("#quantidade-chars");
		var $spanQuantidadeMensagens = $("#quantidade-mensagens");
		
		var $selProvedor = $("#provedor");
		var $selProvedorSimples = $("#provedor-simples");
		
		var $clientesLista = $("#clientes-selecionados");
		var $enviosLista = $("#envios-sms");

		var configuraClientesCallback = function() {
			$clientesLista.empty();
			var clientes = shared["cliJson"];
			$(clientes).each(
					function(k, cliente) {
						var $li = $("<li />", {
							"html" : k + " - " + cliente.nomeCompleto,
							"class" : "list-group-item",
						});
						$li.attr("data-id", cliente.id);
						var $btnRemove = $("<span />", {
							"html" : "&times",
							"class" : "btn btn-danger btn-xs pull-right remove"
						});
						$li.append($btnRemove);
						$clientesLista.append($li);
					});
		}
		
		var removerCliente = function(){
			var $li = $(this).parent();
			var clienteId = $li.data("id");
			
			for (var i = 0; i < shared["cli"].length; i++) {
				if (shared["cli"][i] == clienteId){
					var x  = shared["cli"].splice(i, 1);
					console.log("shared removido " + x+ " id do elemento "+clienteId);
					$li.fadeOut("slow");
					$li.remove();
					break;
				}
			}
			
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
		
		var avaliaCalculoDeEnvioSimples = function(evt) {
			var dataBase = $txtDataBaseSimples.val();
			var conteudo = $txtConteudo.val();
			var provedor = $selProvedorSimples.val();
			var valoresPreenchidos = dataBase && provedor && conteudo;
			if (valoresPreenchidos) {
				$btnEnvioSimples.removeAttr("disabled");
			} else {
				$btnEnvioSimples.attr("disabled", "disabled");
			}
			
		}
		
		var calculaEnviosAjax = function(preparo){
			var url = "/quandovai/envio/calcular";
			$.ajax({
				type : "POST",
				url : url,
				cache: false,
				data : preparo,
				success : function(data) {
					var envios = data.list;
					$enviosLista.empty();
					for(var i = 0; i < envios.length; i++) {
					    var envio = envios[i];
					    var data = envio.mensagem.dateHoraDeEnvio.date,
							tempo = envio.mensagem.dateHoraDeEnvio.time,
							dia = data.day, mes = data.month, ano = data.year,
							hora = tempo.hour, minuto = tempo.minute;
						var cliente = " - "+envio.cliente.nomeCompleto;
						var data = dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto;
						
						
						var li = "<li class='list-group-item'>" +data + cliente + "</li>";
						var $li = $(li);	
						$btnSalvar.removeAttr("disabled");
						$enviosLista.append($li)
					}
						
				},
				dataType : "json"
			});
		}
		
		var configuraPreparoCalculado = function(){
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

			for(var i = 0; i < idsClientes.length; i++){
				preparo["preparo.idsClientes["+i+"]"] = idsClientes[i];
			}
			return preparo;
		}

		var fazEnvioCalculado = function(evt) {
			var preparo = configuraPreparoCalculado();
			$btnSalvar.attr("data-envio", "calculado");
			calculaEnviosAjax(preparo);
			
		}
		
		var configuraPreparoSimples = function (){
			var conteudo = encodeURI($txtConteudo.val());
			var provedor = encodeURI($selProvedorSimples.val());
			var dataBase = $txtDataBaseSimples.val();
			var quantidade = 1
			var periodo = "DIARIO";
			var idsClientes = shared["cli"];
			
			var preparo = {
				"preparo.conteudo" : conteudo,
				"preparo.provedor" : provedor,
				"preparo.dataHoraBase" : dataBase,
				"preparo.quantidade" : quantidade,
				"preparo.periodo" : periodo
			};

			for(var i = 0; i < idsClientes.length; i++){
				preparo["preparo.idsClientes["+i+"]"] = idsClientes[i];
			}
			return preparo;
		}
		
		var fazEnvioSimples = function(evt) {
			var preparo = configuraPreparoSimples();
			$btnSalvar.attr("data-envio", "simples");
			$btnSalvar.removeAttr("disabled");
			calculaEnviosAjax(preparo);
		}
		
		var salvarEnvios = function(evt) {
			var tipoEnvio = $btnSalvar.data("envio");
			var preparo = {};
			
			if (tipoEnvio === "simples"){
				preparo = configuraPreparoSimples();
			} else if (tipoEnvio === "calculado") {
				preparo = configuraPreparoCalculado();
			}
			
			var url = "/quandovai/envio/salvar";
			$.ajax({
				type : "POST",
				url : url,
				cache: false,
				data : preparo,
				success : function(data) {
					location.href = "/quandovai";
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
		$txtConteudo.bind("keyup blur", avaliaCalculoDeEnvioSimples);
		$txtDataBaseSimples.bind("keyup blur", avaliaCalculoDeEnvioSimples);
		
		$clientesLista.delegate("li span", "click", removerCliente)
		
		$btnCalculaEnvios.bind("click", fazEnvioCalculado);
		$btnEnvioSimples.bind("click", fazEnvioSimples);
		$btnSalvar.bind("click", salvarEnvios);

	}(selecionaClientesModule));

});