$(function() {

	var moduloJqueryValidateConfig = (function() {
		$.validator
				.setDefaults({
					highlight : function(element, errorClass, validClass) {
						if (element.type === "radio") {
							this.findByName(element.name).addClass(errorClass)
									.removeClass(validClass);
						} else {
							$(element).closest('.form-group').removeClass(
									'has-success has-feedback').addClass(
									'has-error has-feedback');
							$(element).closest('.form-group').find('i.fa')
									.remove();
							$(element)
									.closest('.form-group')
									.append(
											'<i class="fa fa-exclamation fa-lg form-control-feedback"></i>');
						}
					},
					unhighlight : function(element, errorClass, validClass) {
						if (element.type === "radio") {
							this.findByName(element.name).removeClass(
									errorClass).addClass(validClass);
						} else {
							$(element).closest('.form-group').removeClass(
									'has-error has-feedback').addClass(
									'has-success has-feedback');
							$(element).closest('.form-group').find('i.fa')
									.remove();
							$(element)
									.closest('.form-group')
									.append(
											'<i class="fa fa-check fa-lg form-control-feedback"></i>');
						}
					}
				});
		$.validator
				.addMethod(
						"dateFormat",
						function(value, element) {
							return value
									.match(/^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/);
						},
						"Por favor, digite uma data válida com o formato dd/mm/yyyy.");
		jQuery
				.extend(
						jQuery.validator.messages,
						{
							required : "Campo obrigatório.",
							email : "Por favor,  entre com um endereço válido de e-mail.",
							date : "Por favor, entre com uma data válida.",
							number : "Por favor, entre com um número válido.",
							equalTo : "Por favor, entre com o mesmo valor novamente.",
							maxlength : jQuery.validator
									.format("Por favor, digite até {0} caracteres."),
							minlength : jQuery.validator
									.format("Por favor, digite pelo menos {0} caracteres."),
							rangelength : jQuery.validator
									.format("Por favor, digite um valor entre {0} e {1} caracteres."),
							range : jQuery.validator
									.format("Por favor, digite um valor entre {0} e {1} caracteres."),
							max : jQuery.validator
									.format("Por favor, digite um valor menor ou igual {0}."),
							min : jQuery.validator
									.format("Por favor, digite um valor maior ou igual {0}.")
						});

	}());

	var moduloBotoesRemover = (function() {
		/**
		 * <button id="botao-remover" class="btn btn-danger btn-sm
		 * botao-remover" data-elemento="Deseja remover o cliente
		 * ${cliente.nomeCompleto}?" data-url-remocao="<c:url
		 * value='/cliente/remover/${cliente.id}' />" data-toggle="modal"
		 * data-target="#modal-remover"> Remover </button>
		 * 
		 * e confirmaRemover.tag
		 */
		function configuraBotoesRemover() {
			$(".btn-remover").each(function(e) {
				$(this).click(function() {
					var url = $(this).data('url-remocao');
					var descricaoDoElemento = $(this).data('elemento');
					configuraModal(descricaoDoElemento, url);
				});
			});
		}

		function configuraModal(corpo, urlAcao) {
			var $corpo = $("#mensagem-remover");
			var $formRemover = $("#form-remover");
			$corpo.text(corpo);
			$formRemover.attr("action", urlAcao);
		}
		configuraBotoesRemover();
	}());

	var moduloComponentes = (function() {
		// máscaras
		$('.date-field').mask('00/00/0000');
		$('.cellphone8').mask('(00) 0000-0000');
		$('.cellphone9').mask('(00) 00000-0000');

		//
		$(".tipo-telefone").change(function() {
			var idOfInput = $(this).data("fone-input");
			var $input = $("#" + idOfInput);
			var option = this.value;
			var $select = $(this);
			
			if (option === "9digits") {
				$input.removeClass("cellphone8");
				$input.addClass("cellphone9");
				$input.val("");
				$input.mask('(00) 00000-0000');
			} else {
				$input.removeClass("cellphone9");
				$input.addClass("cellphone8");
				$input.val("");
				$input.mask('(00) 0000-0000');
			}
		});

	}());

	var moduloCadastroCliente = (function() {

		$("#formCadastroCliente").validate({
			rules : {
				"cliente.nomeCompleto" : {
					minlength : 3,
					maxlength : 150,
					required : true
				},
				"cliente.email" : {
					email : true,
					required : true
				},
				"cliente.celular" : {
					required : true
				},
				"cliente.dataAniversario" : {
					dateFormat : true
				}
			}
		});
		var $btnBusca = $("#btn-busca");
		var $inputBusca = $("#procurar-cliente");

		var avaliaPodeBuscar = function(evt) {
			var caracteres = $(this).val().length;
			
			if (caracteres >= 2) {
				$btnBusca.removeAttr("disabled");
			} else {
				$btnBusca.attr("disabled", "disabled");
			}
			return false;
		};

		$inputBusca.bind("keyup", avaliaPodeBuscar);
		$inputBusca.bind("blur", avaliaPodeBuscar);

	}());

	var moduloCadastroModelo = (function() {

		var $btnBusca = $("#btn-busca");
		var $inputBusca = $("#procurar-modelo");

		$("#formCadastroModelo").validate({
			rules : {
				"modelo.conteudo" : {
					minlength : 3,
					maxlength : 150,
					required : true
				},
				"modelo.tipoDeEnvio" : {
					required : true
				},
			}
		});

		var avaliaPodeBuscar = function(evt) {
			var caracteres = $(this).val().length;
			if (caracteres >= 2) {
				$btnBusca.removeAttr("disabled");
			} else {
				$btnBusca.attr("disabled", "disabled");
			}
			return false;
		};

		$inputBusca.bind("keyup", avaliaPodeBuscar);
		$inputBusca.bind("blur", avaliaPodeBuscar);

	}());
	
	
	var moduloCarregaTabelas = (function(){
		var $cadastradasBody = $("#mensagens-cadastradas-tbody");
		var $pagina = $("#mensagens-cadastradas-page");
		var $proximo = $("#mensagens-cadastradas-next");
		var $anterior =  $("#mensagens-cadastradas-prev");
		var $numPagina = $("#num-pagina");
		var temProximo = false;
		
		var carregaTabelaDeCadastradas = function(pagina) {
			$cadastradasBody.empty();
			var url = "/quandovai/mensagens/cadastradas?page="+pagina;
			$.ajax({
				type : "GET",
				url : url,
				cache: false,
				success : function (data){
					console.log(data);
					var envios = data.paginatedList.currentList;
					if (envios.length === 0) {
						$proximo.attr("disabled","disabled");
						return;
					} else {
						$proximo.removeAttr("disabled");
					}
					
					var $linhas = [];
					
					for (var i = 0; i < envios.length; i++) {
						var $linha = $("<tr />");
						var envio = envios[i];
						console.log("ID:" +envio.id);
						// sera enviado em
						var data = envio.mensagem.dateHoraDeEnvio.date,
							tempo = envio.mensagem.dateHoraDeEnvio.time,
							dia = data.day, mes = data.month, ano = data.year,
							hora = tempo.hour, minuto = tempo.minute;
						var data = dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto;
						var $data = $("<td />", {html: data});
						
						// conteudo
						var conteudo = envio.mensagem.conteudo;
						var $conteudo = $("<td />", {html: conteudo});
						
						// cliente
						var cliente = envio.cliente.nomeCompleto;
						var $cliente = $("<td />", {html: cliente});
						
						// provedor
						var provedor = envio.provedor;
						var $provedor = $("<td />", {html: provedor});
						
						// status
						var status = envio.status;
						var $status = $("<td />", {html: status});
						
						$linha.append($data);
						$linha.append($conteudo);
						$linha.append($cliente);
						$linha.append($provedor);
						$linha.append($status);
						
						$linhas.push($linha);
					}
					
					$cadastradasBody.append($linhas);
					
				}
			});
	
		};
		
		var proximoClicked = function(evt) {
			var pagina = Number.parseInt($pagina.val());
			var proximaPagina = pagina + 1;
			if (proximaPagina > 0) {
				$anterior.removeAttr("disabled");
			}
			$pagina.val(proximaPagina);
			$numPagina.html(proximaPagina + 1);
			carregaTabelaDeCadastradas(proximaPagina);
		}
		
		var anteriorClicked = function(evt) {
			var pagina = Number.parseInt($pagina.val());
			var proximaPagina = pagina - 1;
			
			if(proximaPagina === 0) {
				$anterior.attr("disabled","disabled");
			} 
			$pagina.val(proximaPagina);
			$numPagina.html(proximaPagina + 1);
			carregaTabelaDeCadastradas(proximaPagina);	
		}
		
		carregaTabelaDeCadastradas(0);
		$anterior.bind("click", anteriorClicked);
		$proximo.bind("click", proximoClicked);
		
		
	})();
	

});