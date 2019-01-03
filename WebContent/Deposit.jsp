<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Deposit Form</title>
</head>
<body>
	<h1 align="center">Deposit Form</h1>
	<form action="depositForm.mm">
		<table align="center">
			<tr>
				<td><label>Account Number:</label></td>
				<td><input type="number" name="accountnumber"></td>
			</tr>
			<tr>
				<td><label>Amount To Deposit:</label></td>
				<td><input type="number" name="amount"></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="SUBMIT"></td>
				<td><input type="reset" name="reset" value="RESET"></td>
			</tr>

		</table>
	</form>
</body>
</html>