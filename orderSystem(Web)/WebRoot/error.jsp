<%@ page contentType="text/html;charset=gbk"
    import="java.util.*" %>
<html>
<head>
	<title>����</title>
</head>
<body>
<body>
	<br><br><br><br><br><br><br><br>
	<center>
	 <font color="red" size="5">
     <% 
        request.setCharacterEncoding("gbk");
        String message=(String)request.getAttribute("msg");
     	if(message!=null)
     	{//�����Ϣ
     		out.println(message);
     	}
      %>
	</center>
 </body>
</body>
</html>