<%@ page contentType="text/html;charset=gbk" 
    import="java.util.*"%>
<html>
 <head>
   <title>订单明细</title>
 </head>
 <body>
   <%Vector<String[]> ListDetail =
     (Vector<String[]>)request.getAttribute("ListDetail");
    String oid =(String)request.getAttribute("oid");%>
    <br><center>订单<%= oid %>详情</center><br>
   <table align="center" width="94%" cellspacing="1" bgcolor="black">
	 <tr width="75%" height="30" bgcolor="white">
	   <th>总价</th> <th>订购食品</th> <th>电话</th> 
	   <th>送餐地址</th> <th>备注</th>  <th>状态</th> <th>留言</th>
	  </tr>
	   <%int color = 0;//控制每行颜色
	  	 for(int i=0;i<ListDetail.size();i++){
		   String[] s = ListDetail.get(i); %>
     <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %> height="40">
	   <td align="center"><%= s[5] %>元</td>
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