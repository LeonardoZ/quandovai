<%@attribute name="extraScripts" fragment="true"%>
<%@attribute name="extraStyles" fragment="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quando Vai? - Controle</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<meta name="description" content="Developed By M Abdur Rokib Promy">
<meta name="keywords"
	content="Admin, Bootstrap 3, Template, Theme, Responsive">
	

<!-- bootstrap 3.0.2 -->
<link href="<c:url value='/assets/css/bootstrap.min.css' />"
	rel="stylesheet" type="text/css" />
<!-- font Awesome -->
<link href="<c:url value='/assets/css/font-awesome.css' />"
	rel="stylesheet" type="text/css" />
<!-- Ionicons -->
<link href="<c:url value='/assets/css/ionicons.min.css' />"
	rel="stylesheet" type="text/css" />

<link href='http://fonts.googleapis.com/css?family=Lato'
	rel='stylesheet' type='text/css'>
<link href="<c:url value='/assets/css/datepicker/datepicker.css' />"
	rel='stylesheet' type='text/css'>
	
<link href="<c:url value='/assets/css/bs-datetimepicker/bootstrap-datetimepicker.css' />"
	rel='stylesheet' type='text/css'>
<!-- Theme style -->
<link href="<c:url value='/assets/css/style.css' />" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/assets/css/app.css' />" rel="stylesheet"
	type="text/css" />

<!--[if lt IE 9]>
          <script src="<c:url value='https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js'/>"></script>
          <script src="<c:url value='https://oss.maxcdn.com/libs/respond./assets/js/1.3.0/respond.min.js'/>"></script>
          <![endif]-->

</head>
<body class="skin-black">
	<!-- header logo: style can be found in header.less -->
	<header class="header">
		<a href="<c:url value='/' />" class="logo"> Quando vai? </a>
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top" role="navigation">
			<!-- Sidebar toggle button-->
			<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas"
				role="button"> <span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
			</a>
			<div class="navbar-right">
				<ul class="nav navbar-nav">

					<!-- User Account: style can be found in dropdown.less -->
					<li class="dropdown user user-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="fa fa-user"> </i>
					</a>
						<ul class="dropdown-menu dropdown-custom dropdown-menu-right">
							<li class="dropdown-header text-center">Conta</li>
							<li class="divider"></li>

							<li><a href="#"> <i class="fa fa-user fa-fw pull-right"></i>
									${nomeUsuarioLogado}
							</a>
							<li><a href="#"><i class="fa fa-ban fa-fw pull-right"></i>
									Sair</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</header>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="left-side sidebar-offcanvas">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left info">
						<p>Bem vindo, ${nomeUsuarioLogado}</p>

						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>

				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li class="active"><a href="index.html"> <i
							class="fa fa-dashboard"></i> <span>Dashboard</span>
					</a></li>
					<li><a href="<c:url value='/cliente' />"> <i
							class="fa fa-users"></i> <span>Clientes</span>
					</a></li>
					<li><a href="<c:url value='/modelo' />"> <i
							class="fa fa-pencil"></i> <span>Modelos de Mensagem</span>
					</a></li>

					<li><a href="<c:url value='/envio/cadastro' />"> <i
							class="fa fa-paper-plane-o"></i> <span>Envio de Mensagens</span>
					</a></li>

					<li><a href="simple.html"> <i class="fa fa-glass"></i> <span>Configurações
								de E-mail</span>
					</a></li>

					<li><a href="simple.html"> <i class="fa fa-mobile"></i> <span>Configurações
								de SMS</span>
					</a></li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<aside class="right-side">

			<!-- Main content -->
			<section class="content">

				<jsp:doBody />
			</section>
			<!-- /.content -->
			<div class="footer-main">Quando Vai LTDA - 2016</div>
		</aside>
		<!-- /.right-side -->

	</div>
	<!-- ./wrapper -->
  	<script data-main="<c:url value='/assets/js/main.js' />" src="<c:url value='/assets/js/require.js' />"></script>	
	
	<!-- jQuery 2.0.2 -->
	<script src="<c:url value='/assets/js/jquery.min.js'/>"
		type="text/javascript" type="text/javascript"></script>

	<script src="<c:url value='/assets/js/jquery-ui-1.10.3.min.js'/>"
		type="text/javascript"></script>

	<script src="<c:url value='/assets/js/bootstrap.min.js'/>"
		type="text/javascript">
		
	</script>

	<script
		src="<c:url value='/assets/js/plugins/moment/moment.min.js'/>"
		type="text/javascript"></script>
		
	<script
		src="<c:url value='/assets/js/plugins/moment/locales.min.js'/>"
		type="text/javascript"></script>
		
	<script
		src="<c:url value='/assets/js/plugins/moment/moment-with-locales.min.js'/>"
		type="text/javascript"></script>
	
	<script
		src="<c:url value='/assets/js/plugins/datepicker/bootstrap-datepicker.js'/>"
		type="text/javascript"></script>
	
	<script
		src="<c:url value='/assets/js/plugins/bs-datetimepicker/bootstrap-datetimepicker.min.js'/>"
		type="text/javascript"></script>
		
	<script src="<c:url value='/assets/js/plugins/chart.js'/>"
		type="text/javascript"></script>

	<script src="<c:url value='/assets/js/jquery.mask.min.js'/>"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="<c:url value='/assets/js/jquery.validate.js' />"></script>

	<script src="<c:url value='/assets/js/app.js'/>" type="text/javascript"></script>

	<script src="<c:url value='/assets/js/quandovai.js'/>"
		type="text/javascript"></script>

	<jsp:invoke fragment="extraScripts" />

</body>
</html>