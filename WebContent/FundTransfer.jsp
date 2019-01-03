<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fund Transfer Form</title>
</head>
<body>
<h1 align="center">Fund Transfer Form</h1>
<form action="fundTransferForm.mm">
<table align="center">
<tr>
				<td><label>Account Number To Withdraw:</label></td>
				<td><input type="number" name="accountnumberofwithdraw"></td>
			</tr>
			<td><label>Account Number To Deposit:</label></td>
				<td><input type="number" name="accountnumberofdeposit"></td>
			</tr>
			<tr>
				<td><label>Amount To Transfer:</label></td>
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