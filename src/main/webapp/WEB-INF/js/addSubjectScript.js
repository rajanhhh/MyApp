var isDataSubmitted = false;

$(document).ready(function() {
	populateInstitution();
	populateCourse();
	populateBranch();
	
	$("#institution, #course, #branch, #semester").change(function(){
		if('' != document.getElementById("institution").value && '' != document.getElementById("institution").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value){
			getListOfSubjects();
		}else{
			$("#subject").html('');
			$("#subject").append ("<option value=''>--Select All Above Fields--</option>");
		}
	});

			
});


function populateInstitution(){
	var institutionList = $("#institutionList")[0].innerHTML;
	if(undefined != institutionList){
        institutionList = jQuery.parseJSON(institutionList);
		$("#institution").append ("<option value=''>--Select--</option>");
        for (var i = 0; i < institutionList.length; i++) {
			$("#institution").append ("<option value='"+ institutionList[i].id +"'>"+ institutionList[i].name+", "+ institutionList[i].city +"</option>");
        }
	}
}

function populateCourse(){
	var courseList = $("#courseList")[0].innerHTML;
	if(undefined != courseList){
        courseList = jQuery.parseJSON(courseList);
		$("#course").append ("<option value=''>--Select--</option>");
        for (var i = 0; i < courseList.length; i++) {
			$("#course").append ("<option value='"+ courseList[i].id +"'>"+ courseList[i].name + "</option>");
        }
	}
}

function populateBranch(){
	var branchList = $("#branchList")[0].innerHTML;
	if(undefined != branchList){
        branchList = jQuery.parseJSON(branchList);
		$("#branch").append ("<option value=''>--Select--</option>");
        for (var i = 0; i < branchList.length; i++) {
			$("#branch").append ("<option value='"+ branchList[i].id +"'>"+ branchList[i].name + "</option>");
        }
	}
}

function populateSubjectList(){
	var subjectList = $("#subjectList")[0].innerHTML;
	if(undefined != subjectList){
        subjectList = jQuery.parseJSON(subjectList);
		$("#subject").html('');
        for (var i = 0; i < subjectList.length; i++) {
			$("#subject").append ("<option value='"+ subjectList[i].id +"'>"+ subjectList[i].name + "</option>");
        }
	}
}

function getListOfSubjects(){
		$.ajax({
			url:"/getSubjectList",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value + "&branch=" 
				+ document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value,
			type:'post',
		  	success:function(json){
				$("#subjectList").text(json);
				populateSubjectList();
		  	},
			error:function(error){
				$("#subject").html('');
				$("#subject").append ("<option value='0'>-No Subject Found--</option>");
			}
		});
}

function submitForm(){
	if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value &&
		'' != document.getElementById("newSubject").value){
			
			document.querySelectorAll("body :not(.loader):not(nav)").forEach(function myFunction(nodes){nodes.classList.add("blurredForm");})
			$(".loader").show();
		
			$.ajax({
				url:"/addNewSubject",
				data:"institution=" + document.getElementById("institution").value
						+ "&course=" + document.getElementById("course").value + "&branch=" + document.getElementById("branch").value
						+ "&semester=" + document.getElementById("semester").value + "&subject=" + document.getElementById("newSubject").value,
				type:'post',
			  	success:function(json){
					$("#errorMessage").text(json);
					getListOfSubjects();
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