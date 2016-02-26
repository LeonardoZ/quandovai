<%@taglib
	tagdir="/WEB-INF/tags/template"
	prefix="template"%>
<%@taglib
	tagdir="/WEB-INF/tags/template"
	prefix="sucesso"%>
<%@taglib
	tagdir="/WEB-INF/tags/template"
	prefix="erros"%>
<%@taglib
	tagdir="/WEB-INF/tags/template"
	prefix="erro"%>
<%@taglib
	uri="http://java.sun.com/jsp/jstl/core"
	prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quando Vai? - Controle</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<!-- bootstrap 3.0.2 -->
<link
	href="<c:url value='/assets/css/bootstrap.min.css' />"
	rel="stylesheet"
	type="text/css" />
<!-- Theme style -->
<link
	href="<c:url value='/assets/css/style.css' />"
	rel="stylesheet"
	type="text/css" />

<link
	href="<c:url value='/assets/css/login.css' />"
	rel="stylesheet"
	type="text/css" />
<body id="login-body">
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a
					class="navbar-brand"
					href="#">Quando vai?</a>
			</div>
			<div
				id="navbar"
				class="navbar-collapse collapse"></div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="container">
		<div
			class="row"
			style="margin-top: 80px;">
			<div
				class="page-header"
				style="color: white">
				<h1>Agende o envio de SMS e E-mail para seus clientes com
					facilidade.</h1>
			</div>
			<div class="col-lg-4 col-lg-offset-2">
				<section class="panel divider">
					<header class="panel-heading">
						<h2>Entrar</h2>
					</header>
					<div class="panel-body">
						<span class="text-danger">${errors.from('login')}</span>
						<form
							id="loginForm"
							role="form"
							method="post"
							action="<c:url value="/login" />">
							<div class="form-group">
								<label for="login.email">E-mail</label>
								<input
									type="email"
									class="form-control"
									value="${login.email}"
									name="login.email"
									id="login-email"
									required
									placeholder="Insira um e-mail válido">
							</div>
							<div class="form-group">
								<label for="login.senha">Senha</label>
								<input
									type="password"
									name="login.senha"
									value="${login.senha}"
									class="form-control"
									id="login-senha"
									minlength="6"
									maxlength="8"
									required
									placeholder="Insira uma senha entre 6 e 8 caracteres">
							</div>
							<button
								type="submit"
								class="btn btn-info">Entrar</button>
							<a>Não é cadastrado? Cadastra-se agora!</a>
						</form>

					</div>
				</section>
			</div>
			<div class="col-lg-4">
				<section class="panel">
					<header class="panel-heading">
						<h2>Cadastrar</h2>
					</header>
					<div class="panel-body">
						<form
							id="formCadastroUsuario"
							role="form"
							method="post"
							action="<c:url value="/usuario" />">
							<div class="form-group">
								<label for="usuarioCadastro.nome">Nome</label>
								<input
									name="usuarioCadastro.nome"
									id="cadastro-nome"
									value="${usuarioCadastro.nome}"
									class="form-control"
									minlength="3"
									maxlength="120"
									required
									placeholder="Insira seu nome completo">
								<span class="text-danger">${errors.from('nome')}</span>
							</div>
							<div class="form-group">
								<label for="usuarioCadastro.email">E-mail</label>
								<input
									type="email"
									class="form-control"
									value="${usuarioCadastro.email}"
									name="usuarioCadastro.email"
									id="cadastro-email"
									required
									placeholder="Insira um e-mail válido">
								<span class="text-danger">${errors.from('email')}</span>

							</div>
							<div class="form-group">
								<label for="usuarioCadastro.senha">Senha</label>
								<input
									type="password"
									name="usuarioCadastro.senha"
									value="${usuarioCadastro.senha}"
									class="form-control"
									id="cadastro-senha"
									minlength="6"
									maxlength="8"
									required
									placeholder="Insira uma senha entre 6 e 8 caracteres">

								<span class="text-danger">${errors.from('senha')}</span>

							</div>

							<div class="form-group">
								<label for="usuarioCadastro.senha"> Digite a senha
									novamente</label>
								<input
									id="cadastro-senha-novamente"
									value="${usuarioCadastro.senhaNovamente}"
									class="form-control"
									type="password"
									name="usuarioCadastro.senhaNovamente"
									placeholder="Insira uma senha entre 6 e 8 caractéres">

								<span class="text-danger">${errors.from('senhaNovamente')}</span>

							</div>
							<button
								type="submit"
								class="btn btn-info">Cadastrar</button>
						</form>
					</div>
				</section>
			</div>
		</div>
	</div>
	<script
		src="<c:url value='/assets/js/jquery.min.js" type="text/javascript'/>"
		type="text/javascript"></script>

	<script
		type="text/javascript"
		src="<c:url value='/assets/js/jquery.validate.js' />"></script>
	<script type="text/javascript">
		$("#formLogin").validate();
		$("#formCadastroUsuario").validate({
			rules : {
				"usuarioCadastro.senhaNovamente" : {
					equalTo : "#cadastro-senha"
				}
			}
		});
	</script>

	<script type="text/javascript">
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
	</script>

</body>
</html>