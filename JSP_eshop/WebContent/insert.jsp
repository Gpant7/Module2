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
<title>Insert Products</title>
</head>
<body>
	<center>
		<form method="post" action="help.jsp?id=<%=id%>">
			<table>
				<tr>
					<td>Name :</td>
					<td><input type="text" name="name"></td>
					<td>quantity :</td>
					<td><input type="text" name="quantity"></td>
					<td>price :</td>
					<td><input type="text" name="price"></td>
				</tr>
				<tr>	
					<td><input type="submit"></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>