var isDataSubmitted = false;

$(document).ready(function() {
	populateInstitution();
	populateCourse();
	populateBranch();
	
	$("#institution, #course, #branch, #semester").change(function(){
		if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value){
			getListOfSubjects();
		}else{
			$("#subject").html('');
			$("#subject").append ("<option value=''>--Any--[Select All Above Fields to get list of subjects]</option>");
		}
	});		
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
function populateSubjectList(){
	var subjectList = $("#subjectList")[0].innerHTML;
	if(undefined != subjectList){
        subjectList = jQuery.parseJSON(subjectList);
		$("#subject").html('');
		$("#subject").append ("<option value=''>--All--</option>");
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
				$("#subject").append ("<option value=''>--Any--[No Subject Found]</option>");
			}
		});
}

function submitSearchCriteriaForm(){
	if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value){
			
		for(var i=0; i<document.getElementsByClassName("mandatory").length;i++){
			document.getElementsByClassName("mandatory")[i].style.borderColor = "#ccc";
		}
		
		if($("input[name='selectionType']:checked").prop("value") == "DayWise")
			submitSearchCriteriaFormDayWise();
		else
			submitSearchCriteriaFormStudentWise();	
	}else{
		for(var i=0; i<document.getElementsByClassName("mandatory").length;i++){
			if(document.getElementsByClassName("mandatory")[i].value == '')
				document.getElementsByClassName("mandatory")[i].style.borderColor = "red";
			else
				document.getElementsByClassName("mandatory")[i].style.borderColor = "#ccc";
		}
	}	
}

function submitSearchCriteriaFormDayWise(){
		$.ajax({
			url:"/getAttendanceData",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value 
				+ "&branch=" + document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value
				+ "&subject=" + document.getElementById("subject").value
				+ "&startDate=" + document.getElementById("startDate").value + "&endDate=" + document.getElementById("endDate").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				$("#errorMessage").text('');
				$("#attendanceTablePercentage").hide();
				$("#attendanceTable").show();
				$("#printButton").show();
				$("#downloadButton").show();
				showAttendanceData();
				
		  	},
			error:function(error){
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").hide();
				$("#errorMessage").text(error.responseText);
				$("#printButton").hide();
				$("#downloadButton").hide();
			}
		});
}

function submitSearchCriteriaFormStudentWise(){
		$.ajax({
			url:"/getAttendancePercentage",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value 
				+ "&branch=" + document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value
				+ "&subject=" + document.getElementById("subject").value
				+ "&startDate=" + document.getElementById("startDate").value + "&endDate=" + document.getElementById("endDate").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				$("#errorMessage").text('');
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").show();
				showAttendanceData();
				$("#printButton").show();
				$("#downloadButton").show();
		  	},
			error:function(error){
				$("#attendanceTable").hide();
				$("#attendanceTablePercentage").hide();
				$("#errorMessage").text(error.responseText);
				$("#printButton").hide();
				$("#downloadButton").hide();
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
				tr += "<td style='display: none'><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]].split('~')[0] +"'>" + formData[i][col[j]].split('~')[1] + "</span></td>";
			else if(col[j] == 'semester')
				tr += "<td style='display: none'><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
			else
				tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
        }
		tr += '</tr>';
		tbody.append(tr);
    }
}

function downloadAsExcel(){
	var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
	var textRange; var j=0;
	//tab = document.getElementById('attendanceTablePercentage'); 
	// id of table
	
	for(i = 0 ; i < document.getElementsByTagName("table").length ; i++) {     
		if(document.getElementsByTagName("table")[i].style.display != "none"){
			tab = document.getElementsByTagName("table")[i];
		}
	}
	
	for(j = 0 ; j < tab.rows.length ; j++) 
	{     
		tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
	}
	
	tab_text=tab_text+"</table>";
	tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
	tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
	tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params
	
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE "); 
	
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
	{
		txtArea1.document.open("txt/html","replace");
		txtArea1.document.write(tab_text);
		txtArea1.document.close();
		txtArea1.focus(); 
		sa=txtArea1.document.execCommand("SaveAs",true,"AttendanceData.xls");
	}  
	else                 //other browser not tested on IE 11
		sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));
}