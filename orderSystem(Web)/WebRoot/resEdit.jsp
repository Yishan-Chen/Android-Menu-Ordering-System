<%@ page contentType="text/html;charset=gbk"
	import="java.util.*,org.ordersystem.jdbc.*,org.ordersystem.model.*,org.ordersystem.servlet.*"%>
<%
	Vector<String[]> v = DB.getPageContent(currPage, span, group);
	int color = 0;
%>
<center>
	<font color="black" size="5.5"><%= CategoryName %></font>
<table align="center" width="70%" cellspacing="1" bgcolor="black">
	<tr width="60%" height="30" bgcolor="white">
		<br>
		<th>名称</th>
		<th>组别</th>
		<th>价格</th>
		<th>库存</th>
		<th>修改/删除</th>
	</tr>
	<%
		for (String[] s : v) {
	%>
	<tr bgcolor=<%=color % 2 == 0 ? "f7fbb9" : "ffeeee"%>>
		<td align="center"><%=s[0]%></td>
		<td align="center"><%=s[4]%></td>
		<td align="center"><font color="red" size="3"> <%=s[1]%>￥
		</font></td>
		<td align="center"><%=s[2]%></td>
		<td align="center"><a
			href=funtionServlet?action=editRes&&productID=<%=s[3]%>>修改/删除</a></td>
	</tr>
	<%
		color++;
		}
	%>
</table>

<table width="80%" align="center" border="0">
	<tr>
		<td width="33%">
			<%
				if (currPage > 1) {
			%> <a href=resource.jsp?cp= <%= currPage - 1 %>><<上一页</a> <%
 	}
 %>&nbsp;
		</td>
		<form action=resource.jsp method="post">
			<td align="center"><br> <select name="cp"
				onchange="this.form.submit();">
					<%
						for (int i = 1; i <= totalPage; i++) {
							String s = "";
							if (i == currPage) {
								s = "selected";
							}
					%>
					<option value="<%=i%>" <%=s%>>第<%=i%>页
					</option>
					<%
						}
					%>
			</select>
		</form>
		</td>

		<td align="right" width="33%">
			<%
				if (currPage < totalPage) {
			%> <a href=resource.jsp?cp=<%= currPage+1 %>>下一页>></a> 
			<%
 			}
 			%>
		</td>
	</tr>
</table>