var isDataSubmitted = false;

$(document).ready(function() {
	populateInstitution();
	populateCourse();
	populateBranch();
			
});

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
	if($("input[name='selectionType']:checked").prop("value") == "DayWise")
		submitSearchCriteriaFormDayWise();
	else
		submitSearchCriteriaFormStudentWise();		
}

function submitSearchCriteriaFormDayWise(){
		$.ajax({
			url:"/getAttendanceData",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value 
				+ "&branch=" + document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value
				+ "&startDate=" + document.getElementById("startDate").value + "&endDate=" + document.getElementById("endDate").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				$("#errorMessage").text('');
				$("#attendanceTablePercentage").hide();
				$("#attendanceTable").show();
				showAttendanceData();
		  	},
			error:function(error){
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").hide();
				$("#errorMessage").text(error.responseText);
			}
		});
}

function submitSearchCriteriaFormStudentWise(){
		$.ajax({
			url:"/getAttendancePercentage",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value 
				+ "&branch=" + document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value
				+ "&startDate=" + document.getElementById("startDate").value + "&endDate=" + document.getElementById("endDate").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				$("#errorMessage").text('');
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").show();
				showAttendanceData();
		  	},
			error:function(error){
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").hide();
				$("#errorMessage").text(error.responseText);
			}
		});
}

function dateChanged(){
	if(isDataSubmitted){
		isDataSubmitted = false;
		$("#errorMessage").text('');
	}
}

function showAttendanceData(){
	var formData = $("#formData")[0].innerHTML;
	formData = jQuery.parseJSON(formData);
	
	$("tbody:visible").children("#row").remove();
	var col = [];
    for (var i = 0; i < formData.length; i++) {
        for (var key in formData[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    for (var i = 0; i < formData.length; i++) {
		var tbody = $("tbody:visible");
		var tr = '<tr id = "row" name = "row">';

        for (var j = 0; j < col.length; j++) {
			if(col[j] == 'institution' || col[j] =='branch' || col[j] == 'course')
				tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]].split('~')[0] +"'>" + formData[i][col[j]].split('~')[1] + "</span></td>";
			else
				tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
        }
		tr += '</tr>';
		tbody.append(tr);
    }
}
