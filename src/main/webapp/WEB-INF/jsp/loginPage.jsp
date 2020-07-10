<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 ,maximum-scale=1">
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/navbarbootstrap.min.css">
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/loginPageScript.js"></script>
<script src="/js/commonScript.js"></script>
</head>
<head>
<title>Login Page</title>
</head>

<body>
	
	<nav class="navbar navbar-expand-md navbar-light">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">My App</a>
		</div>
		<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/">Home</a></li>
			</ul>
		</div>
	</nav>

	<h3 id = "errorMessage">${errorMessage}</h3>
	
	<form id = "searchCriteriaForm" class="form-horizontal" action="/login" method="post" >
		<br>
		<div class="form-group">
			<label class="control-label col-sm-5">Username: </label>
			<input name = "username" id = "username" class="col-sm-5 form-control"  type="text">
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Password : </label>
			<input name = "password" id = "password" class="col-sm-5 form-control" type="text">
		</div>
		<input type="submit" class="btn btn-primary" value="Login">
	</form>
	
</body>
</html>
