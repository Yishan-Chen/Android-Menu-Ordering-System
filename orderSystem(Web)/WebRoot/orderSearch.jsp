<%@ page contentType="text/html;charset=gbk"
	import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*,org.ordersystem.model.vo.Orders"%>
<%
	String param1 = request.getParameter("param1").trim();
	String param2 = request.getParameter("param2").trim();
	int oid = Integer.parseInt(MyConverter.unescape(param1));
	String username = MyConverter.unescape(param2);
	String realName = DB.getRealName(username);
	Vector<String[]> v = new Vector<String[]>();
	String sqla = "select oid,otime,ostatus from Orders where oid='"
				+ oid + "' and uName='" + realName + "'";
	v = Order_DB.getOrderListThree(sqla);
	Orders order = new Orders();
	StringBuffer sb = new StringBuffer();
	for (String[] s : v) {
		order.setOid(Integer.parseInt(s[0]));
		sb.append(order.getOid());
		sb.append("|");
		order.setUname(s[1]);
		sb.append(order.getUname());
		sb.append("|");
		order.setOstatus(s[2]);
		sb.append(order.getOstatus());
		}
	String s = sb.toString();
	
	out.println(MyConverter.escape(s));
 	//System.out.println(s);
%>