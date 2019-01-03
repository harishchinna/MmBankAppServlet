<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Form</title>
</head>
<body>
<h1 align="center">Search Form</h1>
<form action="search.mm">
<table align="center">
		<tr>
		<td>Enter Account Number:</td>
		<td> <input type="number" name="txtAccountNumber" /></td>
		</tr>
		<tr>
		<td> <input type="submit" value="Submit"></td>
		</tr>
</table>
	</form>
	
	<div align="center">
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
	
</body>
</html>