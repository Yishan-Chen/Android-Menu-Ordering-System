<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,org.ordersystem.jdbc.*"
%>
 <html>
  <head>
   <title>��Դ����</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
   <script language="JavaScript">
    function check2()
    {
       if(document.resInfo.ProNameAfter.value=="")
       {
         alert("����Ϊ�գ�����");
         resInfo.ProNameAfter.focus();
         return false;
       }
       if(document.resInfo.CategoryID.value=="")
       {
         alert("����Ϊ�գ�����");
         resInfo.CategoryID.focus();
         return false;
       }
       if(document.resInfo.price.value=="")
       {
         alert("�۸�Ϊ�գ�����");
         resInfo.price.focus();
         return false;
       }
       if(document.resInfo.stocks.value=="")
       {
         alert("���Ϊ��");
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
      <td align="right" width="20%">����:</td>
      <td><input type="text" name="ProNameAfter" value=<%= s[0] %>></td>
     </tr>
     <tr>
      <td align="right">����:</td>
       <td><input type="text" name="CategoryID" value=<%= s[3] %>>
       <font color="blue" size="2">����ʾ��ԭ����1Ϊ���ϣ�2Ϊ��ʳ��</font></td>
     </tr>
     
     <tr bgcolor="ffeeee">
      <td align="right">�۸�:</td>
      <td><input type="text" name="price" value=<%= s[1] %>></td>
     </tr>
     <tr>
       <td align="right">���:</td>
 	   <td><input type="text" name="stocks" value=<%= s[2] %>></td>
     </tr>
     
     <tr bgcolor="ffeeee"><td></td>
      <td align="left">
       <input type="hidden" name="action" value="deleteRes">
       <input type="hidden" name="ProductID" value=<%= s[4] %>>
       <input type="hidden" name="ProNameBefor" value=<%= s[0] %>>       
       <input type="button" value="����" onclick="check2()">
       &nbsp&nbsp&nbsp&nbsp
       <input type="submit" value="ɾ��">
       <font color="red" size="2">*ɾ����Դ֮ǰ����ȷ�ϸ���Դû�ж���
      </td>
     </tr>
    </form>
   </table><br>
 </body>
</html>