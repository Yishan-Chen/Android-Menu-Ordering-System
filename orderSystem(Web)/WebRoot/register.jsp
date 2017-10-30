<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
	<title>订单管理系统</title>
</head>
<body align="center"  bgcolor="#FFEFDB">
<form action="registerServlet" method="post" >
<table align="center" valign=“middle” frame="box" bgcolor="#625f5f" width="25%" rules=none height="36%" ><br><br><br><br><br>
	<caption><font size=6 Color="#433b3c">用户注册</font><br></caption>
	<tr>
		<td align="center" style="color:#ffffff; width:85px" >登录名: </td>
		<td>
			<input type="text" name="username" size="27"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">密码:</td>
		<td>
			<input type="password" name="password" size="29"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">注册号:</td>
		<td>
			<input type="password" name="regNum" size="29"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="color:#ffffff;">商家名:</td>
		<td>
			<input type="text" name="sellerName" size="27"/>
		</td>
	</tr>
	<tr align="left">
		<td align="center">
		</td>
		<td >
		<input type="submit" value="确定" style="font-size:16px; width:80px">&nbsp;&nbsp;
		<input type="button" value="返回" style="font-size:16px; width:80px" onClick="window.location.href='login.jsp'"/></td>
	</tr>
</table>
</form>
</body>
</html>