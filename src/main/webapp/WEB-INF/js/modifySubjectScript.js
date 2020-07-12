$(document).ready(function() {
	populateInstitution();
	populateCourse();
	populateBranch();
});

function populateFormData(){
	var formData = $("#formData")[0].innerHTML;
	formData = jQuery.parseJSON(formData);
	if(formData.length != 0){
		$("#subjectInfoTable").show();
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
				if(col[j] == 'id')
					tr += "<td style='display:none'><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
				else
					tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
	        }
			tr += '<td><a href="#" onclick="editRow()">Edit/Delete</a></td>';
			tr += '</tr>';
			tbody.append(tr);
	    }
	}else{
		$("#subjectInfoTable").hide();
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


function submitSearchCriteriaForm(){
	if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value){
	
		for(var i=0; i<document.getElementsByTagName("select").length;i++){
			document.getElementsByTagName("select")[i].style.borderColor = "#ccc";
		}
		document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.add("blurredForm");})
		$(".loader").show();
		
		$.ajax({
			url:"/getSubjectList",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value + "&branch=" 
				+ document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				populateFormData();
				document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
				$(".loader").hide();
		  	},
			error:function(error){
				$("#subjectInfoTable").hide();
				$("#errorMessage").text(error.responseText);
				document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
				$(".loader").hide();
			}
		});
	}else{
		for(var i=0; i<document.getElementsByTagName("select").length;i++){
			if(document.getElementsByTagName("select")[i].value == '')
				document.getElementsByTagName("select")[i].style.borderColor = "red";
			else
				document.getElementsByTagName("select")[i].style.borderColor = "#ccc";
		}
	}
}

function editRow(){
	$("#searchForm").hide();
	$("#subjectInfoTable").hide();
	$("#updateForm").show();
	
	var row = this.event.target.parentElement.parentElement.children;
	
	var institution = document.getElementById("institution");
	document.getElementById("newInstitution").textContent = institution.options[institution.selectedIndex].text;
	
	var course = document.getElementById("course");
	document.getElementById("newCourse").textContent = course.options[course.selectedIndex].text;
	
	var branch = document.getElementById("branch");
	document.getElementById("newBranch").textContent = branch.options[branch.selectedIndex].text;
	
	var semester = document.getElementById("semester");
	document.getElementById("newSemester").textContent = semester.options[semester.selectedIndex].text;
	
	document.getElementById("subject").value = row[0].firstElementChild.getAttribute("value")
	document.getElementById("subject").textContent = row[1].firstElementChild.getAttribute("value");
}

function updateData(){
	if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value
		&& '' != document.getElementById("subject").value && '' != document.getElementById("newSubject").value){
			
			document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.add("blurredForm");})
			$(".loader").show();
			
			$.ajax({
				url:"/updateSubjectData",
				data:"institution=" + document.getElementById("institution").value
						+ "&course=" + document.getElementById("course").value + "&branch=" + document.getElementById("branch").value
						+ "&semester=" + document.getElementById("semester").value + "&subject=" + document.getElementById("subject").value
						+ "&newSubject=" + document.getElementById("newSubject").value,
				type:'post',
			  	success:function(json){
					$("#errorMessage").text(json);
					document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
					$(".loader").hide();
			  	},
				error:function(error){
					$("#errorMessage").text(error.responseText);
					document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
					$(".loader").hide();
				}
			});
	}else{
		for(var i=0; i<document.getElementsByTagName("input").length;i++){
			if(document.getElementsByTagName("input")[i].value == '')
				document.getElementsByTagName("input")[i].style.borderColor = "red";
			else
				document.getElementsByTagName("input")[i].style.borderColor = "#ccc";
		}
	}
}

function deleteData(){
	document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.add("blurredForm");})
	$(".loader").show();
	
	$.ajax({
		url:"/deleteSubjectData",
		data:"institution=" + document.getElementById("institution").value
				+ "&course=" + document.getElementById("course").value + "&branch=" + document.getElementById("branch").value
				+ "&semester=" + document.getElementById("semester").value + "&subject=" + document.getElementById("subject").value,
		type:'post',
	  	success:function(json){
			$("#errorMessage").text(json);
			document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
			$(".loader").hide();
	  	},
		error:function(error){
			$("#errorMessage").text(error.responseText);
			document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.remove("blurredForm");})
			$(".loader").hide();
		}
	});
}

function backtoForm(){
	$("#searchForm").show();
	$("#subjectInfoTable").show();
	$("#updateForm").hide();
	submitSearchCriteriaForm();
}