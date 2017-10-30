<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,org.ordersystem.jdbc.DB"%>
<script language="JavaScript">
    function check()
    {
       if(document.searchRes.ProductName.value=="")
       {
         alert("请输入编号查询！！！");
         searchRes.ProductName.focus();
         return false;
       }
       document.searchRes.submit();
    }
</script>
<%@ include file="head.jsp" %>
  <hr width="100%"></hr><br>
   <table align="left" border="0" width="98.5%">
   <tr>    
      <td align="center">
        <a target="balnk" href="addres.jsp">添加资源</a>
      </td>
      <form name="searchRes" action="funtionServlet" method="post">          
      <td align="right">资源名称:  
	    <input type="text" name="productName">
	    <input type="button" value="查询" onclick="check()">
	    <input type="hidden" name="action" value="queryRes">
	  </td>
    </form>
    </form>
    </tr>
   </table><br><br><br>
   <table align="center" bgcolor="C4DCDF" width="60%">
     <td align="center">
      <a href=funtionServlet?action=adminList&&gId=0>所有分组</a>
     </td>
    <%
      Vector<String[]> vgroup = DB.getGroup();//得到分组信息
      for(String[] s:vgroup){%><td align="center">
      <a href=funtionServlet?action=adminList&&gId=<%=s[0]%>><%=s[1]%></a> 
    </td>
     <%}%>
</table>