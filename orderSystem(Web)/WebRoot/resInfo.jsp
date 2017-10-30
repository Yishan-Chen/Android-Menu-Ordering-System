<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,org.ordersystem.jdbc.*"
%>
 <html>
  <head>
   <title>资源管理</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check2()
    {
       if(document.resInfo.ProNameAfter.value=="")
       {
         alert("名称为空！！！");
         resInfo.ProNameAfter.focus();
         return false;
       }
       if(document.resInfo.CategoryID.value=="")
       {
         alert("分组为空！！！");
         resInfo.CategoryID.focus();
         return false;
       }
       if(document.resInfo.price.value=="")
       {
         alert("价格为空！！！");
         resInfo.price.focus();
         return false;
       }
       if(document.resInfo.stocks.value=="")
       {
         alert("库存为空");
         resInfo.stocks.focus();
         return false;
       }
       document.resInfo.action.value="changeRes";
       document.resInfo.submit();
    }
   </script>
  </head>
 <body>
   <%@ include file="resTop.jsp" %>	
    <%
      Vector<String[]> rinfo = 
      	 (Vector<String[]>)request.getAttribute("rinfo");
 	  String []s = rinfo.get(0);
 	%>
   <table align="center" border="0" width="60%">
    <form name="resInfo" action="funtionServlet" method="post">
     <tr bgcolor="ffeeee">
      <td align="right" width="20%">名称:</td>
      <td><input type="text" name="ProNameAfter" value=<%= s[0] %>></td>
     </tr>
     <tr>
      <td align="right">分组:</td>
       <td><input type="text" name="CategoryID" value=<%= s[3] %>>
       <font color="blue" size="2">（提示：原分组1为饮料，2为美食）</font></td>
     </tr>
     
     <tr bgcolor="ffeeee">
      <td align="right">价格:</td>
      <td><input type="text" name="price" value=<%= s[1] %>></td>
     </tr>
     <tr>
       <td align="right">库存:</td>
 	   <td><input type="text" name="stocks" value=<%= s[2] %>></td>
     </tr>
     
     <tr bgcolor="ffeeee"><td></td>
      <td align="left">
       <input type="hidden" name="action" value="deleteRes">
       <input type="hidden" name="ProductID" value=<%= s[4] %>>
       <input type="hidden" name="ProNameBefor" value=<%= s[0] %>>       
       <input type="button" value="保存" onclick="check2()">
       &nbsp&nbsp&nbsp&nbsp
       <input type="submit" value="删除">
       <font color="red" size="2">*删除资源之前请先确认该资源没有订单
      </td>
     </tr>
    </form>
   </table><br>
 </body>
</html>