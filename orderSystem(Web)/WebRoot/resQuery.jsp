<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"
%>
 <html>
  <head>
   <title>资源管理</title>
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
	   out.println("<font color=red size=5>没有此资源</font>");
	   out.println("<br><br><a href="
	     +"funtionServlet?action=adminList&&gId=0>返回</a></center>");
     }
     else
     {
    %>
    <table align="center" width="80%" cellspacing="1" bgcolor="black">
     <tr width="60%" height="30" bgcolor="white">
		<th>名称</th>
		<th>组别</th>
		<th>价格</th>
		<th>库存</th>
		<th>修改/删除</th>
	 </tr>
	 <% 
	    int color=0;//改变每行颜色
	 	for(String []s:res)
	    {
	  %>
	  <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %>>
	   <td align="center"><%= s[1] %></td>
	   <td align="center"><%= s[2] %></td>
	   <td align="center"><%= Double.parseDouble(s[3]) %>元</td>
	   <td align="center"><%= s[4] %></td>
	   <td align="center"><a href=funtionServlet?action=editRes&&ProductID=<%= s[0] %>>修改/删除</a></td>
	  </tr>
	  <%
	       color++;
	     }
	  }
	   %>
 </body>
</html>