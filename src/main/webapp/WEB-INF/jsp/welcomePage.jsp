<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1 ,maximum-scale=1">
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/navbarbootstrap.min.css">
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/welcomePageScript.js"></script>
<script src="/js/commonScript.js"></script>
</head>
<head>
<title>My App</title>
</head>

<body>
	<h2 id = "session" hidden= "hidden">${session}</h2>
	
	<nav class="navbar navbar-expand-md navbar-light" id="validnav">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">My App</a>
		</div>
		<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Student Data</a>
					<ul class="dropdown-menu">
						<li><a href="/get" class="nav-item nav-link">Search Student</a></li>
						<li><a href="/add" class="nav-item nav-link">Add Student Data</a></li>
						<li><a href="/updateData" class="nav-item nav-link">Update/Delete Student Data</a></li>
					</ul>
				</li>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Attendance</a>
					<ul class="dropdown-menu">
						<li><a href="/viewAttendance" class="nav-item nav-link">View Report</a></li>
						<li><a href="/addAttendance" class="nav-item nav-link">Add Attendance</a></li>
						<li><a href="/updateAttendance" class="nav-item nav-link">Update Attendance</a></li>
					</ul>
				</li>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin Zone</a>
					<ul class="dropdown-menu">
						<li><a href="/addSubject" class="nav-item nav-link">Add New Subject</a></li>
					</ul>
				</li>
				<li class="active"><a onclick="logout()">LogOut</a></li>
			</ul>
		</div>
	</nav>
	
	<!-- Nav bar for invalid session -->
	<nav class="navbar navbar-expand-md navbar-light" id="inValidnav" style="display:none">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">My App</a>
		</div>
		<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#invalidNavbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="invalidNavbarCollapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/login">LogIn</a></li>
			</ul>
		</div>
	</nav>
</body>
</html>
