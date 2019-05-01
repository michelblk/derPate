<%@page import="de.db.derpate.CSRFForm"%>
<%@page import="de.db.derpate.util.CSRFPreventionUtil"%>
<%@page import="org.apache.http.entity.ContentType"%>
<%@page import="de.db.derpate.servlet.ServletParameter"%>
<form action="../uploadGodfatherImage" method="post" enctype="multipart/form-data">
  <input type="file" name="<%= ServletParameter.GODFATHER_IMAGE %>" accept="image/png, image/jpeg" />
  <input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.GODFATHER_UPDATE_SELF) %>">
  <input type="submit" />
</form>