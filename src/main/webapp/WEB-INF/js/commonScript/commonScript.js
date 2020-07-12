function logout(){
	var form = document.createElement("form");
	form.setAttribute('method',"post");
	form.setAttribute('action',"/logout");
	$("body").append(form); 
	form.submit();
}