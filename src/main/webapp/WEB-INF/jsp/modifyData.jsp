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
<script src="/js/modifyData.js"></script>
</head>
<head>
<title>Modify Student Data Form</title>
</head>

<body>
	<h2 id = "formData" hidden= "hidden">${formData}</h2>
	<h2 id= "institutionList"  hidden= "hidden">${institutionList}</h2>
	<h2 id= "courseList"  hidden= "hidden">${courseList}</h2>
	<h2 id= "branchList"  hidden= "hidden">${branchList}</h2>
	
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
			</ul>
		</div>
	</nav>
	
	<h3 id = "errorMessage"></h3>
	<form class="form-horizontal" id="searchForm">
		<br>
		<div class="form-group">
			<label class="control-label col-sm-5">Registration Number : </label>
			<input name = "id" id = "id" class="col-sm-5 form-control" type="text">
		</div>
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
				  <option value="">--Select--</option>
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
		<input type="button" value="Search" class="btn btn-primary" onclick="submitSearchCriteriaForm()">
	</form>
	
	<h3 id = "errorMessage"></h3>

	<table id="studentInfoTable" class="table table-bordered" style="display:none">
		<tbody>
			<tr>
				<th>Registration Number</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Institution</th>
				<th>Course</th>
				<th>Branch</th>
				<th>Semester</th>
				<th>Action</th>
			</tr>
		</tbody>
	</table>
	
	<form class="form-horizontal" id="updateForm" style="display:none">
		<br>
		<h2 id ="updateFormHeader">Enter new Values</h2>
		<div class="form-group">
			<label class="control-label col-sm-5">Registration Number : </label>
			<span name = "newId" id = "newId" class="col-sm-5 form-control" style="width: 50%!important;"></span>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">First Name: </label>
			<input name = "newFirst" id = "newFirst" class="col-sm-5 form-control"  type="text">
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Last Name : </label>
			<input name = "newLast" id = "newLast" class="col-sm-5 form-control" type="text">
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Institution Name : </label>
			<select name = "newInstitution" id = "newInstitution" class="col-sm-5 form-control" type="text"></select>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Course Name : </label>
			<select name = "newCourse" id = "newCourse" class="col-sm-5 form-control" type="text"></select>
			</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Branch Name : </label>
			<select name = "newBranch" id = "newBranch" class="col-sm-5 form-control" type="text"></select>
			</div>
		<div class="form-group">
				<label class="control-label col-sm-5">Semester/Year : </label>
				<select list="semesters" name = "newSemester" class="col-sm-5 form-control" id = "newSemester" type="text">
				  <option value="">--Select--</option>
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
		<input type="button" value="Reset" class=" btn btn-light" onclick="resetData()"><br><br>
		<div class="form-inline">
			<input type="button" value="Update" class="form-group btn btn-primary" style="left: 20%;" onclick="updateData()">
			<input type="button" value="Delete" class="form-group btn btn-danger" style="left: 50%;" onclick="deleteData()">
		</div>
	</form>
	
</body>
</html>
