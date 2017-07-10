<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ page import="java.sql.*"%>
	<%@ page import="java.io.*" %>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.PrintWriter"%>
	<%
	String connectionURL = "jdbc:mysql://localhost:3306/eshop";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	connection = DriverManager.getConnection(connectionURL, "root","");
	statement = connection.createStatement();
	
	String data="Select * from products";
	System.out.println(data);
	rs = statement.executeQuery(data);
	%>

<html>
	<head>
	<title>Table of products</title>
	</head>
	<body>
		<center>
		<br>
		<h3>Here is the table of products</h3> 
		<br>
			<table border="3">
				<tr>
					<th>Name</th>
				</tr>
				<%
				while (rs.next())
				{
				if (Integer.parseInt(rs.getString("quantity"))>0){
				%>
				<tr>
					<td><a href="buy.jsp?id=<%=rs.getString("id")%>&price=<%=rs.getString("price")%>&name=<%=rs.getString("name")%>&quantity=<%=rs.getString("quantity")%>"><%=rs.getString("name")%></td>
				</tr>
				<%
					}
				}
				%>
				
			
			</table>
		
		</center>
	
	</body>
</html>