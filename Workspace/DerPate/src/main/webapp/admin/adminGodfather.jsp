<%@ page contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.model.Admin"
	import="de.db.derpate.manager.LoginManager"
%>

<jsp:include page="/WEB-INF/include/header.jsp" />	
	<nav class="navbar navbar-expand-sm menuebar">
		<div class="container-fluid">
			<ul class="text-center">
				<li>
					<a class="nav-link" href="admin/adminGodfather.jsp">Paten</a>
				 </li>
				<li>
					<a class="nav-link" href="admin/adminWelcome.jsp">Tokengenerator</a>
				</li>
			</ul>
		</div>
	</nav>
	
	<div class="container text-center">
		<div class="col-sm-12 col-md-6 mx-auto">
			<div class="text-justify">
				Alle Paten
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/include/footer.jsp" />