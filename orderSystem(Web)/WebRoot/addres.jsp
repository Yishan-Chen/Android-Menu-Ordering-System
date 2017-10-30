<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"
%>
 <html>
  <head>
   <title>添加资源</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check3()
    {
       if(document.addRes.ProductName.value=="")
       {
         alert("编号为空！！！");
         addRes.ProductName.focus();
         return false;
       }
       if(document.addRes.CategoryID.value=="")
       {
         alert("规格为空");
         addRes.CategoryID.focus();
         return false;
       }
       if(document.addRes.Price.value=="")
       {
         alert("价格为空！！！");
         addRes.Price.focus();
         return false;
       }
       if(document.addRes.Stocks.value=="")
       {
         alert("描述为空！！！");
         addRes.Stocks.focus();
         return false;
       }
       document.addRes.submit();
    }
   </script>
  </head>
 <body>
   <%@ include file="resTop.jsp" %>
   <center><br>
    <font color="red" size="4">请正确填写资源的信息</font>
   </center><br>
   <table align="center" border="0" width="40%">
    <form name="addRes" action="funtionServlet" method="post">
     <tr bgcolor="b9fbfa">
      <td align="right" width="20%">名称:</td>
      <td><input type="text" name="ProductName"></td>
     </tr>
     <tr bgcolor="ffeeee">
      <td align="right" width="20%">分组:</td>      
      <td><input type="text" name="CategoryID">
      <font color="blue" size="2">（提示：原分组1为饮料，2为美食）</font></td></td>
     </tr>
     <tr bgcolor="b9fbfa">
      <td align="right">价格:</td>
      <td><input type="text" name="Price"></td>
    <tr bgcolor="ffeeee">
      <td align="right">库存:</td>
      <td><input type="text" name="Stocks"></td>
     </tr>
     <tr bgcolor="b9fbfa"><td></td>
      <td align="left">
        <input type="hidden" name="action" value="addRes">&nbsp;
        <input type="button" value="保存" onclick="check3()">&nbsp;
        <input type="reset" value="重置">
       </td>
     </tr>
    </form>
   </table>
 </body>
</html>