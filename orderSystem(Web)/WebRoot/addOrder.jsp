<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*,java.text.*"%>
 
 <%
 	String param1 = request.getParameter("param1").trim();//得到用户真实姓名
 	String param2 = request.getParameter("param2").trim();//得到用户联系电话
 	String param3 = request.getParameter("param3").trim();//得到用户联系地址
 	String param4 = request.getParameter("param4").trim();//得到订单描述
 	String param5 = request.getParameter("param5").trim();//得到具体订单内容
 	String param6 = request.getParameter("param6").trim();//得到订单总价
 	String param7 = request.getParameter("param7").trim();//等到商家ID
 	
 	String uName = MyConverter.unescape(param1);
 	String teleph = MyConverter.unescape(param2);
 	String addres = MyConverter.unescape(param3);
 	String descri = MyConverter.unescape(param4);
 	String oName = MyConverter.unescape(param5);
 	String price= MyConverter.unescape(param6);
 	System.out.println(price);
 	int sellerid = Integer.parseInt(MyConverter.unescape(param7));
 	
 	Integer oid = DB.getId("Orders", "oid");//得到当前订单id最大值并加1
 	Integer orid = DB.getId("OrderDetail","orid");//得到订单详情的orid并加1
 	Date date=new Date();
	DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
	String time=null;
 	
	String sqla = "insert into Orders(oid,uName,sellerID)" + "values("+oid+",'"+uName+"',"+sellerid+")";
	String sqlb = "insert into OrderDetail(orid,oid,oName,otelNum,Address,oDescription,totalPrice,sellerID)" +
					"values("+orid+",'"+oid+"','"+oName+"','"+teleph+"','"+addres+"','"+descri+"','"+price+"',"+sellerid+")";	
	String msg= "";
	
	String[] food = oName.split(",");
	for(String detail : food){
		String[] info = detail.split("×");
		String sqlc = "update Product set Sales =" + info[1].trim() + "where ProductName ='"+ info[0].trim() + "'";
		DB.update(sqlc);
	}
	
	if(DB.update(sqla, sqlb)){
		msg = String.valueOf(oid) + "|" + "successful";
		out.println(MyConverter.escape(msg));
	}
	else{
		msg = String.valueOf(oid) + "|" + "fail";
		out.println(MyConverter.escape(msg));
	}
				  
 %>