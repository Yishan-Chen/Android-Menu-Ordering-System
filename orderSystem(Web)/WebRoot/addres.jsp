<%@ page contentType="text/html;charset=gbk"
    import="java.util.*"
%>
 <html>
  <head>
   <title>�����Դ</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check3()
    {
       if(document.addRes.ProductName.value=="")
       {
         alert("���Ϊ�գ�����");
         addRes.ProductName.focus();
         return false;
       }
       if(document.addRes.CategoryID.value=="")
       {
         alert("���Ϊ��");
         addRes.CategoryID.focus();
         return false;
       }
       if(document.addRes.Price.value=="")
       {
         alert("�۸�Ϊ�գ�����");
         addRes.Price.focus();
         return false;
       }
       if(document.addRes.Stocks.value=="")
       {
         alert("����Ϊ�գ�����");
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
    <font color="red" size="4">����ȷ��д��Դ����Ϣ</font>
   </center><br>
   <table align="center" border="0" width="40%">
    <form name="addRes" action="funtionServlet" method="post">
     <tr bgcolor="b9fbfa">
      <td align="right" width="20%">����:</td>
      <td><input type="text" name="ProductName"></td>
     </tr>
     <tr bgcolor="ffeeee">
      <td align="right" width="20%">����:</td>      
      <td><input type="text" name="CategoryID">
      <font color="blue" size="2">����ʾ��ԭ����1Ϊ���ϣ�2Ϊ��ʳ��</font></td></td>
     </tr>
     <tr bgcolor="b9fbfa">
      <td align="right">�۸�:</td>
      <td><input type="text" name="Price"></td>
    <tr bgcolor="ffeeee">
      <td align="right">���:</td>
      <td><input type="text" name="Stocks"></td>
     </tr>
     <tr bgcolor="b9fbfa"><td></td>
      <td align="left">
        <input type="hidden" name="action" value="addRes">&nbsp;
        <input type="button" value="����" onclick="check3()">&nbsp;
        <input type="reset" value="����">
       </td>
     </tr>
    </form>
   </table>
 </body>
</html>