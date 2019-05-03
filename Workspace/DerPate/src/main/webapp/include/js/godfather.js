"use strict";

$(document).ready(function () {
	$(".passwordresetform").submit(function (e) {
		e.preventDefault();
		
		var password = $(this).find("[name='password']").val(); 
		var password_confirm = $(this).find("[name='password_confirm']").val(); 
		var csrf_token = $(this).find("[name='csrf_token']").val();
		
		// If password not entered 
	    if (password == '') {
	        alert ("Please enter Password"); 
	    }
	    // If confirm password not entered 
	    else if (password_confirm == '') {
	        alert ("Please enter confirm password"); 
	    }
	    // If Not same return False.     
	    else if (password != password_confirm) { 
	        alert ("Password did not match: Please try again..."); 
	    } 
	  
	    // Do ajax call 
	    else{ 
	        $.ajax({
	        	url: "changepassword",
	        	method: "POST",
	        	data: {
	        		"new-password": password,
	        		"new-password-confirmation": password_confirm,
	        		"csrf_token": csrf_token
	        	},
	        	dataType: "json",
	        	success: function (data) {
	        		alert("Passwort geändert!");
	        	},
	        	error: function(jqXHR, textStatus, errorThrown) {
	        		var json = jqXHR.responseJSON;
	        		alert(json.text);
	        	}
	        });    
	    }
		return false;
	});
			
				$("#updating-form").submit(function (e) {
					e.preventDefault();
					var $form = $(this);
					
					indicateLoading($form, true);
					
					$.ajax({
						url: $form.attr("action"),
						method: $form.attr("method"),
						data: $form.serialize(),
						cache: false,
						dataType: "json",
						complete: function(e, text) {
							indicateLoading($form, false);
							
							if(e.status == <%= GodfatherUpdateServlet.SC_SUCCESS %>) {
								processResponse(e.responseText);
							}else
							if(e.status == <%= GodfatherUpdateServlet.SC_ERROR %>) {
								alert("Error");
								$("#updating-form [name]").addClass("is-invalid");
							}else{
								alert("Unknown error");
							}
						}
					});
					
					return false;
				});
			});
			
			function processResponse(data) {
				var updatedFields = $.parseJSON(data);
				
				$("#updating-form [name]").removeClass("is-invalid").removeClass("is-valid");
				$.each(updatedFields, function (key, value) {
					var isvalid = value["<%= GodfatherUpdateServlet.JSON_OUTPUT_VALID %>"];
					var newvalue = value["<%=GodfatherUpdateServlet.JSON_OUTPUT_VALUE%>"];
					
					if(isvalid) {
						$("#updating-form [name='" + key + "']").addClass("is-valid");
						if(newvalue != null) {
							$("#updating-form [name='" + key + "']").val(newvalue);
						}
					}else{
						$("#updating-form [name='" + key + "']").addClass("is-invalid");
					}
				});
			}
			
			function indicateLoading(form, bool) {
				var buttons = $(form).find("button[type='submit']");
				$(buttons).prop("disabled", bool);
			}
});