"use strict";

$(document).ready(function () {
	$(".godfather-update").click(function (e) {
		e.preventDefault();
		
		var form = $(this).parents(".godfather-card").find(".card-body");
		var id = $(this).siblings(".godfather-id").val();
		var csrf = $(this).siblings(".godfather-csrf").val();
		
		var data = $(form).serializeArray();
		data.push({name: "godfatherid", value: id});
		
		$.ajax({
			url: 'adminGodfatherUpdate',
			method: 'POST',
			headers: {
				'X-Csrf-Token': csrf
			},
			data: data,
			success: function(data) {
				location.reload(true);
			},
			error: function(jqXHR) {
				alert("Ein Fehler ist aufgetreten!");
				location.reload(true);
			}
		});
		
		return false;
	});
	
	$(".godfather-remove").click(function (e) {
		e.preventDefault();
		
		var id = $(this).siblings(".godfather-id").val();
		var csrf = $(this).siblings(".godfather-csrf").val();
		
		if(confirm("Wirklich löschen?")) {
			$.ajax({
				url: 'adminGodfatherUpdate?godfatherid='+id,
				method: 'DELETE',
				headers: {
					'X-Csrf-Token': csrf
				},
				success: function(data) {
					location.reload(true);
				},
				error: function(jqXHR) {
					alert("Ein Fehler ist aufgetreten!");
					location.reload(true);
				}
			});
		}
		
		return false;
	});
});