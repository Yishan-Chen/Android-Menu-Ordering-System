<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
	<title>��������ϵͳ</title>
</head>
<body align="center"  bgcolor="#FFEFDB">
<form action="registerServlet" method="post" >
<table align="center" valign=��middle�� frame="box" bgcolor="#625f5f" width="25%" rules=none height="36%" ><br><br><br><br><br>
	<caption><font size=6 Color="#433b3c">�û�ע��</font><br></caption>
	<tr>
		<td align="center" style="color:#ffffff; width:85px" >��¼��: </td>
		<td>
			<input type="text" name="username" size="27"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">����:</td>
		<td>
			<input type="password" name="password" size="29"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">ע���:</td>
		<td>
			<input type="password" name="regNum" size="29"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">�̼���:</td>
		<td>
			<input type="text" name="sellerName" size="27"/>
		</td>
	</tr>
	<tr align="left">
		<td align="center">
		</td>
		<td >
		<input type="submit" value="ȷ��" style="font-size:16px; width:80px">&nbsp;&nbsp;
		<input type="button" value="����" style="font-size:16px; width:80px" onClick="window.location.href='login.jsp'"/></td>
	</tr>
</table>
</form>
</body>
</html>