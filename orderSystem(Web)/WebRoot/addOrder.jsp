<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,org.ordersystem.jdbc.*,java.util.*,java.text.*"%>
 
 <%
 	String param1 = request.getParameter("param1").trim();//�õ��û���ʵ����
 	String param2 = request.getParameter("param2").trim();//�õ��û���ϵ�绰
 	String param3 = request.getParameter("param3").trim();//�õ��û���ϵ��ַ
 	String param4 = request.getParameter("param4").trim();//�õ���������
 	String param5 = request.getParameter("param5").trim();//�õ����嶩������
 	String param6 = request.getParameter("param6").trim();//�õ������ܼ�
 	String param7 = request.getParameter("param7").trim();//�ȵ��̼�ID
 	
 	String uName = MyConverter.unescape(param1);
 	String teleph = MyConverter.unescape(param2);
 	String addres = MyConverter.unescape(param3);
 	String descri = MyConverter.unescape(param4);
 	String oName = MyConverter.unescape(param5);
 	String price= MyConverter.unescape(param6);
 	System.out.println(price);
 	int sellerid = Integer.parseInt(MyConverter.unescape(param7));
 	
 	Integer oid = DB.getId("Orders", "oid");//�õ���ǰ����id���ֵ����1
 	Integer orid = DB.getId("OrderDetail","orid");//�õ����������orid����1
 	Date date=new Date();
	DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
	String time=null;
 	
	String sqla = "insert into Orders(oid,uName,sellerID)" + "values("+oid+",'"+uName+"',"+sellerid+")";
	String sqlb = "insert into OrderDetail(orid,oid,oName,otelNum,Address,oDescription,totalPrice,sellerID)" +
					"values("+orid+",'"+oid+"','"+oName+"','"+teleph+"','"+addres+"','"+descri+"','"+price+"',"+sellerid+")";	
	String msg= "";
	
	String[] food = oName.split(",");
	for(String detail : food){
		String[] info = detail.split("��");
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