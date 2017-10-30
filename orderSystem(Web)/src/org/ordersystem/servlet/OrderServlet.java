package org.ordersystem.servlet;

import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;

import org.ordersystem.jdbc.DB;
import org.ordersystem.jdbc.Order_DB;
@SuppressWarnings("serial")
public class OrderServlet extends HttpServlet{
	//private static final long serialVersionUID = 7747506950585841168L;
	public void init(ServletConfig conf) throws ServletException 
	{ 
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		req.setCharacterEncoding("gbk");
		res.setCharacterEncoding("gbk");		
		String action = req.getParameter("action");		
		HttpSession session=req.getSession(true);
		String msg="";
		String ostatus1 = "已处理";
		String ostatus2 = "未处理";
		Vector<String[]> OrderList = //得到订单列表
					(Vector<String[]>)session.getAttribute("OrderList");
		int sellerid = DB.getSellerID(MainServlet.username);
		if(OrderList==null)//如果为空则创建一个订单列表对象
		{OrderList = new Vector<String[]>();}			
		if(action.equals("ListDetail")){
			String oid = req.getParameter("oid");//得到订单编号
			Vector<String []> ListDetail = Order_DB.getOrderDetail(oid);//执行查询				
			req.setAttribute("ListDetail",ListDetail);
			req.setAttribute("oid",oid);
			req.getRequestDispatcher("detail.jsp").forward(req,res);
		}
		
		else if(action.equals("allOrders")){//按条件查询订单
			String sql = "";//声明SQL引用
			int conditon = Integer.parseInt(req.getParameter("condition"));
			switch(conditon){
				case 1://1表示所有订单
				sql = "select * from Orders where sellerID =" + sellerid;
				break;
				case 2://2表示已经处理的订单
				sql = "select * from Orders where ostatus='"+ostatus1+"' and sellerID = '"+ sellerid+"'";
				break;
				case 3://3表示未处理的订单
				sql = "select * from Orders where ostatus='"+ostatus2+"' and sellerID = '"+ sellerid+"'";
				break;
			}
			Vector<String []> list = Order_DB.getOrderList(sql);				
			req.setAttribute("list",list);//将订单列表返回				
			req.getRequestDispatcher("orders.jsp").forward(req,res);
		}
		else if(action.equals("query")){//按编号查询订单
		    Vector<String []> list = null;
		    try{
		    	int oid = Integer.parseInt(req.getParameter("oid"));
				String sql = "select * from Orders where oid='"+oid+"'and sellerID ='"+sellerid+"'";
				list = Order_DB.getOrderList(sql);
		    }
		    catch(NumberFormatException nfe)//输入订单号格式不正确
		    {list = new Vector<String []>();}//返回一个空的向量							
			req.setAttribute("list",list);
			req.getRequestDispatcher("orders.jsp").forward(req,res);
		}
		else if(action.equals("dealOrder")){//处理订单
			String ostatus = req.getParameter("ostatus");
			int oid = Integer.parseInt(req.getParameter("oid"));
			//拼装SQL
			String sqla = "update Orders set ostatus='"+ostatus+"' where oid='"+oid+"' and sellerID = '"+sellerid+"'";
			String sqlb = "update OrderDetail set ostatus='"+ostatus+"' where oid='"+oid+"' and sellerID ='"+sellerid+"'";			
			boolean b = DB.update(sqla,sqlb);//执行更新		
			if(b==true){
				msg = "订单处理成功<br><br>"
					+"<a href=OrderServlet?action=allOrders&&condition=1>返回";
			}
			else{msg = "订单处理发生错误，处理失败"
					+ "<a href=OrderServlet?action=allOrders&&condition=1>返回>";
			}
			req.setAttribute("msg",msg);//返回处理消息
			req.getRequestDispatcher("info.jsp").forward(req,res);
			}
		}
}
