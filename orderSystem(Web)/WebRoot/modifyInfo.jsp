<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"
 %>
 <%
   String param1=request.getParameter("param1").trim();//�û���
   String param2=request.getParameter("param2").trim();//����
   String param3=request.getParameter("param3").trim();//��ʵ����
   String param4=request.getParameter("param4").trim();//�Ա�
   String param5=request.getParameter("param5").trim();//�ֻ���
   
   String uname=MyConverter.unescape(param1);
   String pwd=MyConverter.unescape(param2);
   String realname=MyConverter.unescape(param3);
   String sex=MyConverter.unescape(param4);
   String telnum=MyConverter.unescape(param5);
   Vector<String> userInfo=new Vector<String>(); 
   String sqla="update Customer set Password='"+pwd+"',Telephone='"+telnum+"',CustomerName='"+
   realname+"',Sex='"+sex+"' where LoginName='"+uname+"'";
   if(DB.updatea(sqla)){
	 out.println(MyConverter.escape("���³ɹ��������µ�¼��"));
	 	//System.out.println("���³ɹ�������");
	}
   else
   {
   	 out.println(MyConverter.escape("����ʧ�ܣ���"));
   }
%>