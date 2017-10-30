<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*"
 %>
 
<% String param1=request.getParameter("params1").trim();
   String param2=request.getParameter("params2").trim();
   
   String uname=MyConverter.unescape(param1);
   String pwd=MyConverter.unescape(param2);
   
   String sqla="select LoginName from Customer where LoginName='"+uname+"'";
   if(DB.isExist(sqla)){
				String sql = "select Password from Customer where LoginName='"+uname+"'";
				String password=DB.getInfo(sql).trim();//从数据库得到密码
				if(pwd.equals(password)){
				   out.println(MyConverter.escape("登录成功"));	
				}
				else{
				   out.println(MyConverter.escape("登录失败"));	 
				}
	}
   else
   {
       out.println(MyConverter.escape("用户不存在，请重新输入"));
   }

%>

 
 