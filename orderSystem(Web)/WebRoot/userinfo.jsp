<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"
 %>
<%
   String param1=request.getParameter("param1").trim();
   String uname=MyConverter.unescape(param1);
   Vector<String> userInfo=new Vector<String>(); 
   userInfo=DB.getUserInfo(uname);
   StringBuffer msg=new StringBuffer();
   for(String s:userInfo)
   {
 	msg.append(s);
 	msg.append("|");
   }
   String s=msg.toString();
  out.println(MyConverter.escape(s));
%>