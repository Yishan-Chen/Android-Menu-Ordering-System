<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,org.ordersystem.jdbc.DB"%>
<script language="JavaScript">
    function check()
    {
       if(document.searchRes.ProductName.value=="")
       {
         alert("�������Ų�ѯ������");
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
        <a target="balnk" href="addres.jsp">�����Դ</a>
      </td>
      <form name="searchRes" action="funtionServlet" method="post">          
      <td align="right">��Դ����:  
	    <input type="text" name="productName">
	    <input type="button" value="��ѯ" onclick="check()">
	    <input type="hidden" name="action" value="queryRes">
	  </td>
    </form>
    </form>
    </tr>
   </table><br><br><br>
   <table align="center" bgcolor="C4DCDF" width="60%">
     <td align="center">
      <a href=funtionServlet?action=adminList&&gId=0>���з���</a>
     </td>
    <%
      Vector<String[]> vgroup = DB.getGroup();//�õ�������Ϣ
      for(String[] s:vgroup){%><td align="center">
      <a href=funtionServlet?action=adminList&&gId=<%=s[0]%>><%=s[1]%></a> 
    </td>
     <%}%>
</table>