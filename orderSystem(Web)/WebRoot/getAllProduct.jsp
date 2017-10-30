<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"
 %>
<%
  String param1=request.getParameter("param1").trim();
  int sellerid= Integer.parseInt(MyConverter.unescape(param1));
  Vector<String[]> v = DB.getResource1(sellerid);
  String msg="";
  for(String[]s:v)
  {
      msg+=s[3]+"|"+s[4]+"|"+s[0]+"|"+s[1]+"|"+s[2]+"|"+s[5]+"-";
  }
  //System.out.println(msg);
  //out.println(msg);
 out.println(MyConverter.escape(msg));
%>