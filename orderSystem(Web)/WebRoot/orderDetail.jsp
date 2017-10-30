<%@page import="org.ordersystem.model.vo.OrderDetail"%>
<%@ page contentType="text/html;charset=gbk"
	import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*"%>
<%
	String param1 = request.getParameter("param1").trim();
	String oid = MyConverter.unescape(param1);
	Vector<String[]> v = new Vector<String[]>();
	v = Order_DB.getOrderDetail(oid);
	OrderDetail orderdetail = new OrderDetail();
	StringBuffer msg = new StringBuffer();
	for (String[] s : v) {
		orderdetail.setOname(s[0]);
		orderdetail.setOtelnum(s[1]);
		orderdetail.setAddress(s[2]);
		orderdetail.setOdescription(s[3]);
		orderdetail.setOstatus(s[4]);
		orderdetail.setTotalPrice(s[5]);
		msg.append("       ");
		msg.append(orderdetail.getOname());
		msg.append("|");
		msg.append("       ");
		msg.append(orderdetail.getTotalPrice());
		msg.append("|");
		msg.append("       ");
		msg.append(orderdetail.getOtelnum());
		msg.append("|");
		msg.append("       ");
		msg.append(orderdetail.getAddress());
		msg.append("|");
		msg.append("       ");
		msg.append(orderdetail.getOdescription());
		msg.append("|");
		msg.append("       ");
		msg.append(orderdetail.getOstatus());
		msg.append("|");
	}
	String s = msg.toString();
	out.println(MyConverter.escape(s));
	//out.println(s);
	// System.out.println(s+",用户数据已经传回客户端！");
%>