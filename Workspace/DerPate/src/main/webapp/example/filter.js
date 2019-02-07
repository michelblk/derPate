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
					var otherInformations = godfather["information"];
					var description = otherInformations["description"];
					var firstName = otherInformations["firstName"];
					var jobId = otherInformations["job"]["id"];
					var job = otherInformations["job"]["job"];
					var locationId = otherInformations["location"]["id"];
					var location = otherInformations["location"]["location"];
					var teachingTypeId = otherInformations["teachingType"]["id"];
					var teachingType = otherInformations["teachingType"]["teachingType"];
					
					var card = $("#godfahter-card-default").clone();
					$(card).removeAttr("id").removeClass("default");
					$(card).attr('data-id', id);
					$(card).find(".godfather-card-image").css('background-image', 'url(\"../image?trainee=' + id + '\")');
					$(card).find(".godfather-card-firstname").text(firstName);
					$(card).find(".godfather-card-location").attr('data-id', locationId).text(location);
					$(card).find(".godfather-card-teachingType").attr('data-id', teachingTypeId).text(teachingType);
					$(card).find(".godfather-card-job").attr('data-id', jobId).text(job);
					$(card).find(".godfather-card-year").text("TODO");
					$(card).find(".godfather-card-description").text(description);
					
					
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
});

