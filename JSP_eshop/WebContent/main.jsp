<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ page import="java.sql.*"%>
	<%
	String connectionURL = "jdbc:mysql://localhost:3306/eshop";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	%>
<html>
	<head>
	<title>Login Page</title>
	</head>
	<body>
	<%
	if (request.getParameter("name")==null || request.getParameter("name")=="" || request.getParameter("password") ==null || request.getParameter("password") =="") 
	{
	%>
		<br>
		<h3>Write your Name and Password</h3>
		<form method="post" action="main.jsp">
			<table>
				<tr>
					<td>Name :</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td><input type="submit"></td>
				</tr>
			</table>
		</form>
	<%
	}
	else
	{
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "root","");
		statement = connection.createStatement();
		
		String data="Select * from persons where name =\""+ name +"\" and password = \""+ password + "\";";
		System.out.println(data);
		rs = statement.executeQuery(data);
		
		if (rs == null || !rs.next() )
		{
		%>
			<br>
			Your registration " <%=data%> "   is wrong, try again!
			
			<a href="main.jsp" >Go Back</a>
		<%
		}
		else if (name.equals("admin")){%>
			<jsp:forward page="admin.jsp">
			<jsp:param value="7" name="id"/>
			</jsp:forward>
		<%
		}
		else
		{
		%>
			<jsp:forward page="list.jsp">
			<jsp:param value="<%=name%>" name="username"/>
			<jsp:param value="<%=password %>" name="password"/>
			</jsp:forward>
		<%
		}
	}
	%>

	</body>
</html>