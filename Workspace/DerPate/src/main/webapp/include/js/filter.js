"use strict";

$(document).ready(function () {
	var submitButton = $("#filtering-form-submit");
	var modal = $("#more-info-godfather");
	
	$("#filtering-form").submit(function (e) {
		e.preventDefault();
		
		$(submitButton).prop("disabled", true);
		
		$("#async-results").empty();
		$.ajax({
			url: 'findGodfather',
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
					var age = godfather["age"];
					
					var card = $("#godfahter-card-default").clone();
					$(card).removeAttr("id").removeClass("default");
					$(card).attr('data-id', id);
					$(card).find(".godfather-card-image").css('background-image', 'url(\"../godfatherImage?id=' + id + '\")');
					$(card).find(".godfather-card-firstname").text(firstName);
					$(card).find(".godfather-card-age").val(age);
					$(card).find(".godfather-card-location").text(location);
					$(card).find(".godfather-card-teachingType").text(teachingType);
					$(card).find(".godfather-card-job").text(job);
					$(card).find(".godfather-card-educationalyear").val(educationalYear);
					$(card).find(".godfather-card-description").val(description);
					
					
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
	
	$("#async-results").on("click", ".more-info-godfather-button", function (e) {
		e.preventDefault();
		$(modal).modal("show");
		
		var targetedCard = $(e.target).parents(".godfather-card");
		var id = $(targetedCard).attr('data-id');
		var firstName = $(targetedCard).find(".godfather-card-firstname").text();
		var age = $(targetedCard).find(".godfather-card-age").val();
		var location = $(targetedCard).find(".godfather-card-location").text();
		var teachingType = $(targetedCard).find(".godfather-card-teachingType").text();
		var job = $(targetedCard).find(".godfather-card-job").text();
		var educationalYear = $(targetedCard).find(".godfather-card-educationalyear").val();
		var description = $(targetedCard).find(".godfather-card-description").val();
		
		var moreInfoModal = $("#more-info-godfather");
		$(moreInfoModal).find(".godfather-card-select-id").val(id);
		$(moreInfoModal).find(".godfather-card-firstname").text(firstName);
		$(moreInfoModal).find(".godfather-card-age").text(age);
		$(moreInfoModal).find(".godfather-card-location").text(location);
		$(moreInfoModal).find(".godfather-card-teachingType").text(teachingType);
		$(moreInfoModal).find(".godfather-card-job").text(job);
		$(moreInfoModal).find(".godfather-card-educationalyear").text(educationalYear);
		$(moreInfoModal).find(".godfather-card-description").text(description);
		
		
		return false;
	});
	
	
	$(".godfahter-card-select-form").on("submit", "#more-Info-godfather", function (e) {
		e.preventDefault();
		
		var url = $(this).attr("action");
		var method = $(this).attr("method");
		
		$.ajax({
			url: url,
			method: method,
			data: $(this).serialize(),
			cache: false,
			complete: function(e, text) {
				if(e.status == 204) {
					// success
					location.href="";
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
		
	});
});
