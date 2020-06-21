$(document).ready(function() {
		populateInstitution();
		populateCourse();
		populateBranch();
});

function populateInstitution(){
	var institutionList = $("#institutionList")[0].innerHTML;
	if(undefined != institutionList){
        institutionList = jQuery.parseJSON(institutionList);
        for (var i = 0; i < institutionList.length; i++) {
			$("#institution").append ("<option value='"+ institutionList[i].id +"'>"+ institutionList[i].name+", "+ institutionList[i].city +"</option>");
        }
	}
}

function populateCourse(){
	var courseList = $("#courseList")[0].innerHTML;
	if(undefined != courseList){
        courseList = jQuery.parseJSON(courseList);
        for (var i = 0; i < courseList.length; i++) {
			$("#course").append ("<option value='"+ courseList[i].id +"'>"+ courseList[i].name + "</option>");
        }
	}
}

function populateBranch(){
	var branchList = $("#branchList")[0].innerHTML;
	if(undefined != branchList){
        branchList = jQuery.parseJSON(branchList);
        for (var i = 0; i < branchList.length; i++) {
			$("#branch").append ("<option value='"+ branchList[i].id +"'>"+ branchList[i].name + "</option>");
        }
	}
}

function submitForm(){
		$.ajax({
			url:"/addData",
			data:"id="+document.getElementById("id").value + "&first=" + document.getElementById("first").value
						+ "&last=" + document.getElementById("last").value + "&institution=" + document.getElementById("institution").value
						+ "&course=" + document.getElementById("course").value + "&branch=" + document.getElementById("branch").value
						+ "&semester=" + document.getElementById("semester").value,
			type:'post',
		  	success:function(json){
				$("#errorMessage").text(json);
		  	},
			error:function(error){
				$("#errorMessage").text(error.responseText);
			}
		});
}