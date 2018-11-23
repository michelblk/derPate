<%@page import="de.db.derPate.util.HttpStatusCodeUtil"%>
<%@page import="java.net.HttpURLConnection"%>
<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="text-center">
	<h1>
		&#9785;<!-- sad face -->
	</h1>

	<h3><%= HttpStatusCodeUtil.codeToReadableString(response.getStatus()) %></h3>
	<br />
	<p>
		<button onclick="location.reload(true);" class="btn">Erneut
			versuchen</button>
	</p>
	<p>
		<button onclick="location.href='<%= request.getContextPath() %>/';"
			class="btn">Zur&uuml;ck zur Startseite</button>
	</p>
</div>
<%@ include file="../include/footer.jsp" %>