<%@ page contentType="text/html" pageEncoding="UTF-8" %>      
<jsp:include page="WEB-INF/include/header.jsp" />

<body>
  <div class="wrapper">
    <form class="form-signin" action="<%= request.getContentType() %>/login" >       
      <input type="text" class="form-control" name="<%= Constants.Login.Inputs.LOGIN_TOKEN %>" placeholder="Enter Code here" required="required" autofocus="autofocus" />       
      <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>   
    </form>
  </div>
</body>
<jsp:include page="WEB-INF/include/footer.jsp" />
