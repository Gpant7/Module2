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
<title>Insert title here</title>
</head>
<body>
		<%
			String a=request.getParameter("name");
			String b=request.getParameter("quantity");
			String c=request.getParameter("price");
			String d=request.getParameter("id");
			
			System.out.println(d);
			
			int ammount = Integer.parseInt(d) + 1;

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root","");
			statement = connection.createStatement();
			
			String sqlSelect = "INSERT INTO products (name,quantity,price,id) values (\"" + a +"\",\""+b+"\",\""+c+"\",\""+ammount+"\");";	
			System.out.println(sqlSelect);
			int m = statement.executeUpdate(sqlSelect);
			%><jsp:forward page="main.jsp"></jsp:forward><%
		%>
</body>
</html>