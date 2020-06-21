<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/AddDataPopulateFields.js"></script>
</head>
<head>
<title>Add Student Data Form</title>
</head>

<body>
<div class="container">
	<h3 id = "errorMessage"></h3>
	<h2 id= "institutionList"  hidden= "hidden">${institutionList}</h2>
	<h2 id= "courseList"  hidden= "hidden">${courseList}</h2>
	<h2 id= "branchList"  hidden= "hidden">${branchList}</h2>
	<form class="form-horizontal">
		<br>
		<div class="form-group">
			<label class="control-label col-sm-5">Registration Number : </label>
			<input name = "id" id = "id" class="col-sm-5 form-control" type="text">
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">First Name: </label>
			<input name = "first" id = "first" class="col-sm-5 form-control"  type="text">
		</div>
		<div class="form-group">
			<label class="control-label col-sm-5">Last Name : </label>
			<input name = "last" id = "last" class="col-sm-5 form-control" type="text">
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
		<input type="button" value="Submit" class="btn btn-primary" onclick="submitForm()">
	</form>
	<h3><b>Please click <a href="/get">this link</a> to see all Employee Data.</b></h3>
	<h3><b>Please click <a href="/addAttendance">this link</a> to add attendance.</b></h3>
	<h3><b>Please click <a href="/viewAttendance">this link</a> to view attendance.</b></h3>
</div>
</body>
</html>
