$(document).ready(function() {
	populateInstitution();
	populateCourse();
	populateBranch();
});

var oldId = null;
var oldFirst = null;
var oldLast = null;
var oldInstitution = null;
var oldCourse = null;
var oldBranch = null;
var oldSemester = null;

function populateFormData(){
	var formData = $("#formData")[0].innerHTML;
	formData = jQuery.parseJSON(formData);
	if(formData.length != 0){
		$("#studentInfoTable").show();
		$("#errorMessage").text('');
		
		$("tbody").children("#row").remove();
		var col = [];
	    for (var i = 0; i < formData.length; i++) {
	        for (var key in formData[i]) {
	            if (col.indexOf(key) === -1) {
	                col.push(key);
	            }
	        }
	    }
	
	    for (var i = 0; i < formData.length; i++) {
			var tbody = $("tbody");
			var tr = '<tr id = "row" name = "row">';
	
	        for (var j = 0; j < col.length; j++) {
				if(col[j] == 'institution' || col[j] =='branch' || col[j] == 'course')
					tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]].split('~')[0] +"'>" + formData[i][col[j]].split('~')[1] + "</span></td>";
				else
					tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
	        }
			tr += '<td><a onclick="editRow()">Edit/Delete</a></td>';
			tr += '</tr>';
			tbody.append(tr);
	    }
	}else{
		$("#studentInfoTable").hide();
		$("#errorMessage").text("No Data Found");
	}
	
}

function populateInstitution(){
	var institutionList = $("#institutionList")[0].innerHTML;
	if(undefined != institutionList){
        institutionList = jQuery.parseJSON(institutionList);
		$("#institution").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < institutionList.length; i++) {
			$("#institution").append ("<option value='"+ institutionList[i].id +"'>"+ institutionList[i].name+", "+ institutionList[i].city +"</option>");
        }
	}
}

function populateCourse(){
	var courseList = $("#courseList")[0].innerHTML;
	if(undefined != courseList){
        courseList = jQuery.parseJSON(courseList);
		$("#course").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < courseList.length; i++) {
			$("#course").append ("<option value='"+ courseList[i].id +"'>"+ courseList[i].name + "</option>");
        }
	}
}

function populateBranch(){
	var branchList = $("#branchList")[0].innerHTML;
	if(undefined != branchList){
        branchList = jQuery.parseJSON(branchList);
		$("#branch").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < branchList.length; i++) {
			$("#branch").append ("<option value='"+ branchList[i].id +"'>"+ branchList[i].name + "</option>");
        }
	}
}

function populateNewInstitution(){
	var institutionList = $("#institutionList")[0].innerHTML;
	if(undefined != institutionList){
        institutionList = jQuery.parseJSON(institutionList);
		$("#newInstitution").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < institutionList.length; i++) {
			$("#newInstitution").append ("<option value='"+ institutionList[i].id +"'>"+ institutionList[i].name+", "+ institutionList[i].city +"</option>");
        }
	}
}

function populateNewCourse(){
	var courseList = $("#courseList")[0].innerHTML;
	if(undefined != courseList){
        courseList = jQuery.parseJSON(courseList);
		$("#newCourse").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < courseList.length; i++) {
			$("#newCourse").append ("<option value='"+ courseList[i].id +"'>"+ courseList[i].name + "</option>");
        }
	}
}

function populateNewBranch(){
	var branchList = $("#branchList")[0].innerHTML;
	if(undefined != branchList){
        branchList = jQuery.parseJSON(branchList);
		$("#newBranch").append ("<option value=''>--Any--</option>");
        for (var i = 0; i < branchList.length; i++) {
			$("#newBranch").append ("<option value='"+ branchList[i].id +"'>"+ branchList[i].name + "</option>");
        }
	}
}

function submitSearchCriteriaForm(){
		$.ajax({
			url:"/getfilteredData",
			data:"id=" + document.getElementById("id").value +"&institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value + "&branch=" 
				+ document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				populateFormData();
		  	},
			error:function(error){
				$("#studentInfoTable").hide();
				$("#errorMessage").text(error.responseText);
			}
		});
}

function editRow(){
	$("#searchForm").hide();
	$("#studentInfoTable").hide();
	$("#updateForm").show();
	populateNewInstitution();
	populateNewCourse();
	populateNewBranch();
	
	var row = this.event.target.parentElement.parentElement.children;
	
	document.getElementById("newId").value =row[0].firstElementChild.getAttribute("value");
	document.getElementById("newId").textContent = row[0].firstElementChild.getAttribute("value");
	oldId =row[0].firstElementChild.getAttribute("value");
	
	document.getElementById("newFirst").value =row[1].firstElementChild.getAttribute("value");
	oldFirst =row[1].firstElementChild.getAttribute("value");
	
	document.getElementById("newLast").value =row[2].firstElementChild.getAttribute("value");
	oldLast =row[2].firstElementChild.getAttribute("value");
	
	document.getElementById("newInstitution").value =row[3].firstElementChild.getAttribute("value");
	oldInstitution =row[3].firstElementChild.getAttribute("value");
	
	document.getElementById("newCourse").value =row[4].firstElementChild.getAttribute("value");
	oldCourse =row[4].firstElementChild.getAttribute("value");
	
	document.getElementById("newBranch").value =row[5].firstElementChild.getAttribute("value");
	oldBranch =row[5].firstElementChild.getAttribute("value");
	
	document.getElementById("newSemester").value =row[6].firstElementChild.getAttribute("value");
	oldSemester =row[6].firstElementChild.getAttribute("value");
}

function resetData(){
	document.getElementById("newFirst").value =oldFirst;
	document.getElementById("newLast").value =oldLast;
	document.getElementById("newInstitution").value =oldInstitution;
	document.getElementById("newCourse").value =oldCourse;
	document.getElementById("newBranch").value =oldBranch;
	document.getElementById("newSemester").value =oldSemester;
	
	for(var i=0; i<document.getElementsByTagName("select").length;i++){
		document.getElementsByTagName("select")[i].style.borderColor = "#ccc";
	}
	for(var i=0; i<document.getElementsByTagName("input").length;i++){
		document.getElementsByTagName("input")[i].style.borderColor = "#ccc";
	}
}

function updateData(){
	if('' != document.getElementById("newFirst").value && '' != document.getElementById("newLast").value &&
		'' != document.getElementById("newInstitution").value && '' != document.getElementById("newCourse").value  &&
		'' != document.getElementById("newBranch").value && '' != document.getElementById("newSemester").value){
			$.ajax({
				url:"/updateStudentData",
				data:"id="+ oldId + "&first=" + oldFirst + "&last=" + oldLast + "&institution=" + oldInstitution
							+ "&course=" + oldCourse + "&branch=" + oldBranch + "&semester=" + oldSemester 
							+ "&newFirst=" + document.getElementById("newFirst").value
							+ "&newLast=" + document.getElementById("newLast").value + "&newInstitution=" + document.getElementById("newInstitution").value
							+ "&newCourse=" + document.getElementById("newCourse").value + "&newBranch=" + document.getElementById("newBranch").value
							+ "&newSemester=" + document.getElementById("newSemester").value,
				type:'post',
			  	success:function(json){
					$("#errorMessage").text(json);
			  	},
				error:function(error){
					$("#errorMessage").text(error.responseText);
				}
			});
	}else{
		for(var i=0; i<document.getElementsByTagName("select").length;i++){
			if(document.getElementsByTagName("select")[i].value == '')
				document.getElementsByTagName("select")[i].style.borderColor = "red";
			else
				document.getElementsByTagName("select")[i].style.borderColor = "#ccc";
		}
		for(var i=0; i<document.getElementsByTagName("input").length;i++){
			if(document.getElementsByTagName("input")[i].value == '')
				document.getElementsByTagName("input")[i].style.borderColor = "red";
			else
				document.getElementsByTagName("input")[i].style.borderColor = "#ccc";
		}
	}
}

function deleteData(){
	$.ajax({
		url:"/deleteStudentData",
		data:"id="+ oldId + "&first=" + oldFirst + "&last=" + oldLast + "&institution=" + oldInstitution
							+ "&course=" + oldCourse + "&branch=" + oldBranch + "&semester=" + oldSemester,
		type:'post',
	  	success:function(json){
			$("#errorMessage").text(json);
	  	},
		error:function(error){
			$("#errorMessage").text(error.responseText);
		}
	});
}