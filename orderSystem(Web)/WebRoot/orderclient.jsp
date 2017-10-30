<%@ page contentType="text/html;charset=gbk"
	import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*,org.ordersystem.model.vo.Orders"%>
<%
	String param1 = request.getParameter("param1").trim();
	String uname = MyConverter.unescape(param1);
	System.out.println(uname);
	String realname = DB.getRealName(uname);
	//System.out.println(realname);
	Vector<String[]> v = new Vector<String[]>();
	String sqla = "select oid,otime,ostatus from Orders where uName='"
				+ realname + "'";
	v = Order_DB.getOrderListThree(sqla);
	Orders order = new Orders();
	StringBuffer sb = new StringBuffer();
	for (String[] s : v) {
		sb.append("       ");
		order.setOid(Integer.parseInt(s[0]));
		sb.append(order.getOid());
		sb.append("|");
		sb.append("       ");
		order.setUname(s[1]);
		sb.append(order.getUname());
		sb.append("|");
		sb.append("       ");
		order.setOstatus(s[2]);
		sb.append(order.getOstatus());
		sb.append("-");
		}
	String s = sb.toString();
	out.println(MyConverter.escape(s));
// 	System.out.println(MyConverter.escape(s));
%>