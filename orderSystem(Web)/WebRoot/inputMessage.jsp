<%@ page contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*,java.text.*"%>
 
 <% 
 	String param1 = request.getParameter("param1").trim();
 	String param2 = request.getParameter("param2").trim();
 	String msg = MyConverter.unescape(param1);
 	int oid = Integer.parseInt(MyConverter.unescape(param2));
 	System.out.println(msg);
 	String sql = "UPDATE OrderDetail SET message = '" + msg + "' where oid = " + oid;
 	if(DB.updatea(sql)){
 		out.println(MyConverter.escape("successful"));
 	}
 	else{
 		out.println(MyConverter.escape("fail"));
 	}
 %>