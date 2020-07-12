<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 ,maximum-scale=1">
<script src="/js/commonScript/jquery.min.js"></script>
<script src="/js/commonScript/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/navbarbootstrap.min.css">
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/addAttendanceScript.js"></script>
<script src="/js/commonScript/commonScript.js"></script>
</head>
<head>
<title>Attendance Form</title>
</head>

<body>
	<h2 id = "formData" hidden= "hidden">${formData}</h2>
	<h2 id= "institutionList"  hidden= "hidden">${institutionList}</h2>
	<h2 id= "courseList"  hidden= "hidden">${courseList}</h2>
	<h2 id= "branchList"  hidden= "hidden">${branchList}</h2>
	<h2 id= "subjectList"  hidden= "hidden">${subjectList}</h2>
	
	<nav class="navbar navbar-expand-md navbar-light">
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
	
	<form id = "searchCriteriaForm" class="form-horizontal">
		
		<h2 id = "formHeader">Add Attendance</h2>
		
		<div class="form-group">
			<label class="control-label col-sm-5">Institution Name : </label>
			<select name = "institution" id = "institution" class="col-sm-5 form-control" type="text"></select>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Course Name : </label>
			<select name = "course" id = "course" class="col-sm-5 form-control" type="text"></select>
			</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Branch Name : </label>
			<select name = "branch" id = "branch" class="col-sm-5 form-control" type="text"></select>
			</div>
		<div class="form-group">
				<label class="control-label col-sm-5">Semester/Year : </label>
				<select list="semesters" name = "semester" class="col-sm-5 form-control" id = "semester" type="text">
				  <option value="">--Any--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				  <option value="7">7</option>
				  <option value="8">8</option>
				</select>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Subject Name : </label>
			<select name = "subject" id = "subject" class="col-sm-5 form-control" type="text">
				<option value=''>--Select All Above Fields--</option>
			</select>
			</div>
		<input type="button" value="Submit" class="btn btn-primary" onclick="submitSearchCriteriaForm()">
	</form>
	
	<h3 id = "errorMessage"></h3>
	
	<form id = "attendanceForm" style="display:none">
		<h4><b>Date</b></h4>
		<input name="date" id="date" type="date" onChange = "dateChanged();"><br> <br>
		
		<table id="attendanceTable"  class="table table-bordered">
			<tbody>
				<tr>
					<th>Registration Number</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Presence</th>
				</tr>
			</tbody>
		</table>
		<input type="button" value="Submit" class="btn btn-primary" onclick="submitForm()">
	</form>
	<div class= "loader" style="display: none;"> </div>
</body>
</html>
