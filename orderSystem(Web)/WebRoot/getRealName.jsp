<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"%>
 <%
  Vector<String[]> v = DB.getResource1();
  String msg="";
  for(String[]s:v)
  {
      msg+=s[3]+"|"+s[0]+"|"+s[1]+"|"+s[2]+"-";
  }
  //System.out.println(msg);
  out.println(MyConverter.escape(msg));
%>