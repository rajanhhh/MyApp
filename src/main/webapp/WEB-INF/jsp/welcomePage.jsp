<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<head>
<title>My App</title>
</head>

<body>

	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="/">My App</a>
	    </div>
	    <ul class="nav navbar-nav">
	      <li class="active"><a href="/">Home</a></li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Student Data<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="/get">Search Student</a></li>
	          <li><a href="/add">Add Student Data</a></li>
	        </ul>
	      </li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Attendance<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="/viewAttendance">View Report</a></li>
	          <li><a href="/addAttendance">Add Attendance</a></li>
	        </ul>
	      </li>
	    </ul>
	  </div>
	</nav>

</body>
</html>
