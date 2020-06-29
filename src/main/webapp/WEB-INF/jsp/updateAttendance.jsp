<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/updateAttendanceScript.js"></script>
</head>
<head>
<title>Update Attendance</title>
</head>

<body>
	<h2 id = "formData" hidden= "hidden">${formData}</h2>
	<h2 id= "institutionList"  hidden= "hidden">${institutionList}</h2>
	<h2 id= "courseList"  hidden= "hidden">${courseList}</h2>
	<h2 id= "branchList"  hidden= "hidden">${branchList}</h2>
	<h2 id= "subjectList"  hidden= "hidden">${subjectList}</h2>
	
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
	          <li><a href="/updateData">Update/Delete Student Data</a></li>
	        </ul>
	      </li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Attendance<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="/viewAttendance">View Report</a></li>
	          <li><a href="/addAttendance">Add Attendance</a></li>
	          <li><a href="/updateAttendance">Update Attendance</a></li>
	        </ul>
	      </li>
	      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin Zone<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="/addSubject">Add New Subject</a></li>
	        </ul>
	      </li>
	    </ul>
	  </div>
	</nav>
	
	<form id = "searchCriteriaForm" class="form-horizontal">
		<br>
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
				<option value=''>--Any--[Select All Above Fields to get list of subjects]</option>
			</select>
			</div>
		
  		<br>
		<label><h4><b>Date</b></h4></label><input name="date" id="date" type="date" onChange = "dateChanged();">
		
		<input type="button" value="Search"  class="btn btn-primary" onclick="submitSearchCriteriaForm()">
	</form>
	
	<h3 id = "errorMessage"></h3>
	<form id = "attendanceForm" style="display:none">
		<table id="attendanceTable" class="table table-bordered">
			<tbody>
				<tr>
					<th>Registration Number</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Institution</th>
					<th>Course</th>
					<th>Branch</th>
					<th>Semester</th>
					<th>Presence</th>
					<th>Action</th>
				</tr>
			</tbody>
		</table>
		<input type="button" value="Submit" class="btn btn-primary" onclick="submitForm()">
	</form>
</body>
</html>
