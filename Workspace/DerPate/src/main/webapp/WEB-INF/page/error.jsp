<%@ page
	isErrorPage="true"
	contentType="text/html"
	pageEncoding="UTF-8"
	import="org.apache.http.impl.EnglishReasonPhraseCatalog" %>
<%@ include file="../include/header.jsp" %>
<div class="container text-center">
	<h1>
		&#9785;<!-- sad face -->
	</h1>

	<h3>Fehler <%= response.getStatus() %></h3>
	<h4><%= EnglishReasonPhraseCatalog.INSTANCE.getReason(response.getStatus(), null) %></h4>
	<br />
	<p>
		<button onclick="location.reload(true);" class="btn">
			Erneut versuchen
		</button>
	</p>
	<p>
		<button onclick="location.href='<%= request.getContextPath() %>/';" class="btn">
			Zur&uuml;ck zur Startseite
		</button>
	</p>
</div>
<%@ include file="../include/footer.jsp" %>