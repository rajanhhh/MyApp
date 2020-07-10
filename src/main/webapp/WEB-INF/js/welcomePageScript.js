$(document).ready(function() {
	if(document.getElementById("session").textContent  != "valid"){
		$("#inValidnav").show();
		$("#validnav").hide();
	}else{
		$("#validnav").show();
		$("#inValidnav").hide();
	}
});