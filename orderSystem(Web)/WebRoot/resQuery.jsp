<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"
%>
 <html>
  <head>
   <title>��Դ����</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
  </head>
 <body>
   <%@ include file="resTop.jsp" %>
    <%
      Vector<String[]> res = 
      	 (Vector<String[]>)request.getAttribute("list");
     if(res==null||res.size()==0)
     {
       out.println("<center>");
	   out.println("<font color=red size=5>û�д���Դ</font>");
	   out.println("<br><br><a href="
	     +"funtionServlet?action=adminList&&gId=0>����</a></center>");
     }
     else
     {
    %>
    <table align="center" width="80%" cellspacing="1" bgcolor="black">
     <tr width="60%" height="30" bgcolor="white">
		<th>����</th>
		<th>���</th>
		<th>�۸�</th>
		<th>���</th>
		<th>�޸�/ɾ��</th>
	 </tr>
	 <% 
	    int color=0;//�ı�ÿ����ɫ
	 	for(String []s:res)
	    {
	  %>
	  <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %>>
	   <td align="center"><%= s[1] %></td>
	   <td align="center"><%= s[2] %></td>
	   <td align="center"><%= Double.parseDouble(s[3]) %>Ԫ</td>
	   <td align="center"><%= s[4] %></td>
	   <td align="center"><a href=funtionServlet?action=editRes&&ProductID=<%= s[0] %>>�޸�/ɾ��</a></td>
	  </tr>
	  <%
	       color++;
	     }
	  }
	   %>
 </body>
</html>