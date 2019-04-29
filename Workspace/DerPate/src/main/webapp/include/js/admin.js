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
});