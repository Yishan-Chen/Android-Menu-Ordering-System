<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"
 %>
 <%
 String param=request.getParameter("param1").trim();
 int oid=Integer.valueOf(MyConverter.escape(param));
 if(Delete_DB.isDelete(oid))
 {
   out.println(MyConverter.escape("����ɾ���ɹ�!"));
 }
 else
 {
   out.println(MyConverter.escape("����ɾ��ʧ��!"));
 }
 %>