<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*" %>
<%@page import="java.util.*"%>
<%
	String connectionURL = "jdbc:mysql://localhost:3306/eshop";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buy the product</title>
</head>
<body>
<% 
	String quantity=request.getParameter("quantity");
	String price=request.getParameter("price");
	String id=request.getParameter("id");
	String name=request.getParameter("name");
	
	System.out.println(quantity);
	int ammount = Integer.parseInt(quantity) - 1;
	

	Class.forName("com.mysql.jdbc.Driver").newInstance();
	connection = DriverManager.getConnection(connectionURL, "root","");
	statement = connection.createStatement();
	
	String sqlSelect = "update products set quantity=\""+ammount+"\" where id = \""+id+"\";";	
	System.out.println(sqlSelect);
	int m = statement.executeUpdate(sqlSelect);	
%>
	<center>
	The price of <%=name%> is <%=price%>
	<table>
	<tr>
	<td><a href="list.jsp" >Go Back</a></td>
	</tr>
	<tr>
	<td><a href="main.jsp" >register again</a></td>
	</tr>
	</table>
	</center>
</body>
</html>