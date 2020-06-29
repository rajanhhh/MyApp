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

function populateFormData(){
	var formData = $("#formData")[0].innerHTML;
	formData = jQuery.parseJSON(formData);
	if(formData.length != 0){
		$("#institution, #course, #branch, #semester").parent("div").hide();
		document.getElementById("subject").style.borderColor = "#ccc";
		$("#attendanceForm").show();
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
			tr += '<td><input type="checkbox" id="presence" name="presence" value = "N" onChange = "toggleCheckBox()"></td>';
			tr += '</tr>';
			tbody.append(tr);
	    }
	}else{
		$("#attendanceForm").hide();
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
				$("#subject").append ("<option value=''>-No Subject Found--</option>");
			}
		});
}

function submitSearchCriteriaForm(){
	if('' != document.getElementById("institution").value && '' != document.getElementById("course").value  &&
		'' != document.getElementById("branch").value && '' != document.getElementById("semester").value){
		$.ajax({
			url:"/getfilteredData",
			data:"institution=" + document.getElementById("institution").value + "&course=" + document.getElementById("course").value + "&branch=" 
				+ document.getElementById("branch").value + "&semester=" + document.getElementById("semester").value,
			type:'post',
		  	success:function(json){
				$("#formData").text(json);
				populateFormData();
		  	},
			error:function(error){
				$("#attendanceForm").hide();
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
	}
}

function submitForm(){
			var json = '[';
		   var otArr = [];
		   var tbl2 = $('#attendanceTable tbody tr').each(function(i) {        
		      x = $(this).children();
		      var itArr = [];
			  var isBlank = true;
		      x.each(function() {
				 if(null!= this.firstElementChild){
					//itArr.push('"' + this.firstElementChild.id+ '"' + ":" +'"' + this.firstElementChild.value + '"');
					itArr.push('"' + this.firstElementChild.id+ '"' + ":" +'"' + this.firstElementChild.getAttribute("value") + '"');
					isBlank = false;
				}
		      });
			  if(!isBlank)
		      otArr.push('{' + itArr.join(',') + '}');
		   })
		   
			//json += otArr.join(",") + '}';
			json += otArr.join(",") ;
			json += ']';
			json = "date=" + document.getElementById("date").value + "&subject=" + document.getElementById("subject").value + "&attendanceList=" +json;
		$.ajax({
			url:"/submitAttendance",
			data:json,
			type:'post',
		  	success:function(json){
				isDataSubmitted = true;
				$("#errorMessage").text(json);
		  	},
			error:function(error){
				$("#errorMessage").text(error.responseText);
			}
		});
}

function toggleCheckBox(){
	if(this.event.target.checked)
		this.event.target.value = "Y";
	else
		this.event.target.value = "N";
}
function dateChanged(){
	if(isDataSubmitted){
		$(':checkbox').prop("checked", false);
		$(':checkbox').prop("value", "N");
		isDataSubmitted = false;
		$("#errorMessage").text('');
	}
}
