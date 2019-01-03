<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AddNewAccountForm</title>
</head>
<body>
	<h1 align="center">ADD NEW ACCOUNT FORM</h1>
	<form action="addNewAccountForm.mm">

		<table align="center">
			<tr>
				<td><label>Account Holder Name:</label></td>
				<td><input type="text" name="accountHolderName"></td>
			</tr>
			<tr>
				<td><label>Initial Balance:</label></td>
				<td><input type="number" name="InitialBalance"></td>
			</tr>
			<tr>
				<td><label>Salary Type:</label></td>
				<td><input type="radio" name="salaryType" value="Yes">YES</td>
				<td><input type="radio" name="salaryType" value="No">NO</td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="SUBMIT"></td>
				<td><input type="reset" name="reset" value="RESET"></td>
			</tr>
		</table>
	</form>
</body>
</html>