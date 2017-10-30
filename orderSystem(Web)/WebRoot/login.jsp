<%@ page language="java" pageEncoding="gbk" 
    import="java.util.*"
%>
<html>
<head>
	<title align="center" >订单管理系统</title>
</head>
<body   bgcolor="#FFEFDB">
<form form = "center" action="mainServlet" method="post"><form/>
<table align="center" >
	<tr >
		<td  colsapn="1" ><img src="image/head.jpg"></td>
	</tr>
</table><br>
<table align="center" frame="box" rules=none width="23%" bgcolor="#625f5f" height="21%" >
	<caption><font size=6 Color="#433b3c">用户登录</font></caption>
	<tr>
		<td align= "center" style="color:#ffffff; width:65px"> 账号: </td>
		<td>
			<input type="text" name="adname" size="27"/>
		</td>
	</tr>
	<tr >
		<td align= "center" style="color:#ffffff;">密码:</td>
		<td>
			<input type="password" name="password" size="29"/>
		</td>
	</tr>
<tr>
	<td></td>
	<td>
	<input type="hidden" name="action" value="adlogin">	
	<input type="submit" value="登录" style="font-size:16px; width:80px" >&nbsp;&nbsp;
	<input type="button" value="注册" style="font-size:16px; width:80px" onClick="window.location.href='register.jsp'">
	</td>
</tr>
</form></table>
</body>
</html>