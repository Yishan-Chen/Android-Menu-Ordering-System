<%@ page contentType="text/html;charset=gbk" 
    import="java.util.*"%>
<html>
 <head>
   <title>������ϸ</title>
 </head>
 <body>
   <%Vector<String[]> ListDetail =
     (Vector<String[]>)request.getAttribute("ListDetail");
    String oid =(String)request.getAttribute("oid");%>
    <br><center>����<%= oid %>����</center><br>
   <table align="center" width="94%" cellspacing="1" bgcolor="black">
	 <tr width="75%" height="30" bgcolor="white">
	   <th>�ܼ�</th> <th>����ʳƷ</th> <th>�绰</th> 
	   <th>�Ͳ͵�ַ</th> <th>��ע</th>  <th>״̬</th> <th>����</th>
	  </tr>
	   <%int color = 0;//����ÿ����ɫ
	  	 for(int i=0;i<ListDetail.size();i++){
		   String[] s = ListDetail.get(i); %>
     <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %> height="40">
	   <td align="center"><%= s[5] %>Ԫ</td>
	   <td align="center"><%= s[0] %></td>
	   <td align="center"><%= s[1] %></td>
	   <td align="center"><%= s[2] %></td>
	   <td align="center"><%= s[3] %></td>
	   <td align="center"><%= s[4] %></td>
	   <td align="center"><%= s[6] %></td>
	</tr><%color++;}%>
   </table>
 </body>
</html>