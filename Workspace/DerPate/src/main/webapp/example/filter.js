"use strict";

$(document).ready(function () {
	var submitButton = $("#filtering-form-submit");
	
	$("#filtering-form").submit(function (e) {
		e.preventDefault();
		
		$(submitButton).prop("disabled", true);
		
		$("#async-results").empty();
		$.ajax({
			url: '../godfather',
			method: 'GET',
			data: $(this).serialize(),
			dataType: 'json',
			success: function (data, textStatus, jqXHR) {
				var godfathers = data;
				$.each(godfathers, function (index, godfather) {
					// for each godfather
					var id = godfather["id"];
					var description = godfather["description"];
					var firstName = godfather["firstname"];
					var job = godfather["job"];
					var location = godfather["location"];
					var teachingType = godfather["teachingtype"];
					var educationalYear = godfather["educationalyear"];
					
					var card = $("#godfahter-card-default").clone();
					$(card).removeAttr("id").removeClass("default");
					$(card).attr('data-id', id);
					$(card).find(".godfather-card-image").css('background-image', 'url(\"../godfatherImage?id=' + id + '\")');
					$(card).find(".godfather-card-firstname").text(firstName);
					$(card).find(".godfather-card-location").text(location);
					$(card).find(".godfather-card-teachingType").text(teachingType);
					$(card).find(".godfather-card-job").text(job);
					$(card).find(".godfather-card-year").text(educationalYear);
					$(card).find(".godfather-card-description").text(description);
					$(card).find(".godfather-card-select-id").val(id);
					
					$("#async-results").append(card);
					
				});
				
			},
			error: function (jqXHR, textStatus, errorThrown) {
				
			},
			complete: function() {
				$(submitButton).prop("disabled", false);
			}
		});
		
		return false;
	});
	
	$("#async-results").on("submit", ".godfahter-card-select-form", function (e) {
		e.preventDefault();
		
		var url = $(this).attr("action");
		var method = $(this).attr("method");
		
		console.log(url);
		console.log(method);
		
		$.ajax({
			url: url,
			method: method,
			data: $(this).serialize(),
			cache: false,
			complete: function(e, text) {
				if(e.status == 204) {
					// success
					alert("Success");
				}else if(e.status == 500) {
					// internal error
					alert("Internal error");
				}else if(e.status == 400) {
					// bad request
					alert("Bad request");
				}else{
					alert("Unknown error");
				}
			}
		});
		
		return false;
	});
});

