<%@ page contentType="text/html; charset = UTF-8"%>
<html>
<head>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/customStyle.css">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/viewAttendanceData.js"></script>
</head>
<head>
<title>Attendance Report</title>
</head>

<body>
<div class="container">
	<h2 id = "formData" hidden= "hidden">${formData}</h2>
	<h2 id= "institutionList"  hidden= "hidden">${institutionList}</h2>
	<h2 id= "courseList"  hidden= "hidden">${courseList}</h2>
	<h2 id= "branchList"  hidden= "hidden">${branchList}</h2>
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
		 <!-- <label class="radio-inline"><input type="radio" name="selectionType" checked="checked" value="DayWise"><h4><b>Day by Day Report</b></h4></label>
		 <label class="radio-inline"><input type="radio" name="selectionType" value="StudentWise"><h4><b>Student Report</b></h4></label> -->
		 <input type="radio" name="selectionType" checked="checked" value="DayWise"><label><h4><b> Day by Day Report </b></h4></label>
		 <input type="radio" name="selectionType" value="StudentWise"><label><h4><b> Student Report </b></h4></label>
		 
  		<br>
		<span><b>From Date</b></span><input name="startDate" id="startDate" type="date" onChange = "dateChanged();">
		<span><b>To Date</span></h4><input name="endDate" id="endDate" type="date" onChange = "dateChanged();"><br> <br>
		
		<input type="button" value="Search"  class="btn btn-primary" onclick="submitSearchCriteriaForm()">
	</form>
	
	<h3 id = "errorMessage"></h3>
	
		<table id="attendanceTable" class="table table-bordered" style="display:none">
			<tbody>
				<tr>
					<th>Date</th>
					<th>Registration Number</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Institution</th>
					<th>Course</th>
					<th>Branch</th>
					<th>Semester</th>
					<th>Presence</th>
				</tr>
			</tbody>
		</table>
		<table id="attendanceTablePercentage" class="table table-bordered" style="display:none">
			<tbody>
				<tr>
					<th>Registration Number</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Institution</th>
					<th>Course</th>
					<th>Branch</th>
					<th>Semester</th>
					<th>Classes Attended</th>
					<th>Total Number of Classes</th>
					<th>Attendance Percentage</th>
				</tr>
			</tbody>
		</table>
	
	<h3><b>
		Please click <a href="/get">this link</a> to see all Employee Data.
	</b></h3>
	<h3><b>
		Please click <a href="/add">this link</a> to add more data.
	</b></h3>
	<h3><b>
		Please click <a href="/addAttendance">this link</a> to add attendance.
	</b></h3>
</div>
</body>
</html>
