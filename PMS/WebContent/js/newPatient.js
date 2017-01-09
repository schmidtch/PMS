function savePatient() {
	var name = $("#nameTxt").val();
	var givenname = $("#givenname").val();
	var givenname2 = $("#givenname2").val();
	var title = $("#title").val();
	var sex = $('input[name="sex"]:checked').val();
	var birthname = $("#birthname").val();
	var svnr = $("#svnr").val();
	var birthdate = $("#birthdate").val();
	patient = JSON.stringify({name : name, givenname : givenname, givenname2 : givenname2, title : title, sex : sex, birthname : birthname, svnr : svnr, birthdate : birthdate, adress : JSON.stringify({street : $("#street").val(), streetnumber : $("#streetnr").val(), zip : $("#zip").val(), location : $("#location").val(), country : $("#country").val()}), contact : JSON.stringify({telefon : $("#telefon").val(), email : $("#email").val() })});
	$.ajax({
		type : "POST",
		url : "patient/new",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : patient
	}).done(function(msg) {
		var responseText = msg.state;
		if(responseText=="success"){
			alert("Patient erfolgreich hinzugefügt!");
			$.ajax({
		        type: "GET",
		        url: "patient/show",
		        dataType: "html",
		        contentType: "text/html;charset=UTF-8",
		        success: function (data) {
		           $("#content").html(data);
		           $("li").removeClass('active');
		           $("#show").addClass('active');
		        }
		    });
		} else {
			alert(msg.error);
		}		
	});	
}


var oldId = "name";
function changePanelMenu(id) {
	if(oldId!=id) {
		$("#"+oldId+"Panel").slideUp();
		$("#"+id+"Panel").slideDown();
		oldId=id;
	}
}

