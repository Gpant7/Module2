<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String id=request.getParameter("id");
	System.out.println(id);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin</title>
</head>
<body>
	<form method="post" action="insert.jsp?id=<%=id%>">
		<td><input type="submit" value="insert"></td>
	</form>
	<form method="post" action="delete.jsp?id">
		<td><input type="submit" value="delete"></td>
	</form>
</body>
</html>