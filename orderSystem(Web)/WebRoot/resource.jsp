<%@ page contentType="text/html;charset=gbk"
    import="java.util.*,org.ordersystem.*" %>
 <html>
  <head>
   <title>资源管理</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
  </head>
 <body>
    <%@ include file="resTop.jsp" %>
	<% Vector<String> list = (Vector<String>)session.getAttribute("list");
	   int group = Integer.parseInt(list.get(0));
	   String CategoryName = list.get(1);//得到分组名
	   String cpStr=request.getParameter("cp");
	   int currPage=1;//设置默认当前页为第一页
	   if(cpStr!=null){
	 	currPage=Integer.parseInt(cpStr.trim());//得到当前页
	    }
	   int span=10;//每页显示记录条数为5条
	   int totalPage=DB.getTotal(span,group);//得到总页数
     %>
   <%@ include file="resEdit.jsp" %>
 </body>
</html>