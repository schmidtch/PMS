var oldID = "";
function menuFunction(id){
	if (oldID != "") {
		$("#" + oldID + "bar").slideUp(function complete() {
			if (oldID != id) {
				// alert("oldID="+oldID+" id="+id);
				$("#" + id + "bar").slideDown();
			}
		});
	} else {
		$("#" + id + "bar").slideDown();
	}
	if (oldID == id) {
		id = "";
	}
	oldID = id;
}
function patientListClick(svnr, birthdate) {
	$.ajax({
		type : "POST",
		url : "patient/show",
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			svnr : svnr,
			birthdate : birthdate
		}),
		success : function(data) {
			if (data.msg == "success") {
				$("#content").html(data.data);
			} else {
				alert("Error");
			}
		}
	});
}

