<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*"
 %>
 <% 
   String param1=request.getParameter("yonghuming").trim();
   String param2=request.getParameter("rpwd").trim();
   String param3=request.getParameter("telnum").trim();
   String param4=request.getParameter("realname").trim();
   String param5=request.getParameter("gender").trim();
   
    String yonghuming=MyConverter.unescape(param1);
    String rpwd=MyConverter.unescape(param2);
    String telnum=MyConverter.unescape(param3);
    String realname=MyConverter.unescape(param4);
    String gender=MyConverter.unescape(param5);
    
    
    String sqla="select LoginName from Customer where LoginName='"+yonghuming+"'";
    int CusID = DB.getId("Customer","CustomerID");
    out.println(MyConverter.escape("CusID"));
    
    if(DB.isExist(sqla)){
	 out.println(MyConverter.escape("用户名已存在，请试试另一个！！！"));	
	}
	else
	{
	 String sql="insert into Customer(CustomerID,LoginName,Password,CustomerName,Sex,Telephone) values ('"+CusID+"','"+yonghuming+"','"+rpwd+"'"+
					",'"+realname+"','"+gender+"','"+telnum+"')";
	 DB.update(sql);
	 out.println(MyConverter.escape("注册成功,请输入用户名和密码以登录！"));
	}
  %>