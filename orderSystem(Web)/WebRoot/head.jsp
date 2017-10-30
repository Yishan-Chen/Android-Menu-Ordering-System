<%@ page contentType="text/html;charset=gbk" language="java" pageEncoding="gbk" %>

<table align=center border="0" width="80%" height=20>
	<tr align="center">
	  <td height=15 colspan="5">
	  		<font color="#5e82e9" size="6">订单管理系统</font>
	  </td>
	</tr>
	<tr>
   <td align="left" colspan="2">
   	<a href = "login.jsp" >注销</a>
   </td>
   <td align="right" colspan="5">
  	<%String usr = (String)session.getAttribute("usr");
  	  if(usr!=null){
  	   out.println("管理员 "+usr+" 您好!");
  	   }%>
    </td>
  </tr>
</table>

<table align="center" border="0" width="80%" bgcolor="#92cfeb">
	<tr>
	 <td><a href="pwdEdit.jsp">修改密码</a>
	 <td><a href= OrderServlet?action=allOrders&&condition=1>处理订单</a>
	 <td><a href=funtionServlet?action=adminList&&gId=0>资源管理</a>
	 <td><a href="helpFile.jsp">帮助</a></td>
	</tr>
</table>


