<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"%>
 <html>
  <head>
   <title>��������</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check()
    {
       if(document.searchOrder.oid.value=="")
       {
         alert("�����붩���Ų�ѯ������");
         searchOrder.oid.focus();
         return false;
       }
       document.searchOrder.submit();
    }
   </script>
  </head>
 <body>
   <%@ include file="head.jsp" %>	
   <hr width="100%"></hr><br>
   <table align="center" border="0" width="80%">
    <tr>   
	  <form name="searchOrder" action="OrderServlet" method="post">
	   <td align="right">�������:	 
	    <input type="hidden" name="action" value="query">
	    <input type="text" name="oid">
	    <input type="button" value="��ѯ" onclick="check()">
	   </td>
	  </tr>
	  </form>
   </table><br>
   <table align="center" width="60%">
   <tr>
   <td align="left"><a href=OrderServlet?action=allOrders&&condition=2>�Ѵ���</a></td>
   <td align="center"><a href=OrderServlet?action=allOrders&&condition=1>���ж���</a></td>
   <td align="right"><a href=OrderServlet?action=allOrders&&condition=3>δ����</a></td>
   </tr>
   </table><br>
	   <%Vector<String[]> list = //�õ������б�
          (Vector<String[]>)request.getAttribute("list");
       if(list==null||list.size()==0){//�б���Ϊ��
	     out.println("<center>");
	     out.println("<font color=red size=5>û�ж���</font>");
	     out.println("<br><br><a href="+
	       "OrderServlet?action=allOrders&&condition=1>����</a></center>");
	    }
	    else{%>
   <table align="center" width="70%" cellspacing="1" bgcolor="black">
	 <tr width="60%" height="30" bgcolor="white">
	   <th>���</th> <th>�¶���</th> <th>�¶�ʱ��</th> 
     <th>״̬</th><th>����</th><th>��������</th></tr>
	 </tr>
	   <%int color = 0;
	  	 for(int i=0;i<list.size();i++){ 
		   String[] s = list.get(i);%>
     <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %> height="40">
	   <td align="center"><%= s[0] %></td>  <td align="center"><%= s[1] %></td>
	   <td align="center"><%= s[2] %></td>  <td align="center"><%= s[3] %></td>
	   <td align="center">
	    <a target="blank" href=OrderServlet?action=ListDetail&&oid=<%= s[0] %>>��������</a>	   
	   </td>   
	   <form action="OrderServlet" method="post">
	   <td align="center">
	     <select name="ostatus">
	     <option selected>�Ѵ���</option>
	     <option>δ����</option>
	     </select>
	     <input type="hidden" name="action" value="dealOrder">
	     <input type="hidden" name="oid" value=<%= s[0] %>>
	     <input type="submit" value="�ύ">
	   </td>
	   </form>	  
	</tr> <%color++;}%>
   </table><%}%>
 </body>
</html>