<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>

<!-- Head starts in header.jspf -->
<%@ include file="../../content/common/header.jspf"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<!-- END Head -->

					<!-- ERROR MESSAGE -->
					<%=request.getAttribute("error-message")%>
					<!-- END ERROR MESSAGE -->

</body>
</html>