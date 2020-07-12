<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1 ,maximum-scale=1">
<script src="/js/commonScript/jquery.min.js"></script>
<script src="/js/commonScript/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/navbarbootstrap.min.css">
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/welcomePageScript.js"></script>
<script src="/js/commonScript/commonScript.js"></script>
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
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Attendance</a>
					<ul class="dropdown-menu">
						<li><a href="/viewAttendance" class="nav-item nav-link">View Report</a></li>
						<li><a href="/addAttendance" class="nav-item nav-link">Add Attendance</a></li>
						<li><a href="/updateAttendance" class="nav-item nav-link">Update Attendance</a></li>
					</ul>
				</li>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage Subjects</a>
					<ul class="dropdown-menu">
						<li><a href="/addSubject" class="nav-item nav-link">Add New Subject</a></li>
						<li><a href="/modifySubject" class="nav-item nav-link">Modify Subject Name</a></li>
					</ul>
				</li>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Manage Student Info</a>
					<ul class="dropdown-menu">
						<li><a href="/get" class="nav-item nav-link">Search Student</a></li>
						<li><a href="/add" class="nav-item nav-link">Add Student Data</a></li>
						<li><a href="/updateData" class="nav-item nav-link">Update/Delete Student Data</a></li>
					</ul>
				</li>
				<li><a href="#" id="logoutButton" onclick="logout()">LogOut</a></li>
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
	
	<form id="welcomeForm">
	
		<h4>What's Inside</h4>
		<div>If you are bored of marking attendance with pen and paper or if you are tired of generating attendance report at the end of the year, here is the solution.</div>
		<br><br>
		<h4>What we offer</h4>
		<div>
			<p>A single platform to log student attendance record and generate report.</p>
			<ul>
				<li>Add, modify Student Personal Information</li>
				<li>Log daily Attendance Record</li>
				<li>Modify Attendance record of past date</li>
				<li>Generate Attendance record of a class or a particular student</li>
			</ul>
		</div>
		<br><br>
		<h4>Objective</h4>
		<div>To offer technical solution and easy record keeping of student personal information and attendance.</div>
	</form>
	
	
</body>
</html>
