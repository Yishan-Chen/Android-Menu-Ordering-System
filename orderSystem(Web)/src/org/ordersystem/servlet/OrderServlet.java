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
		String ostatus1 = "�Ѵ���";
		String ostatus2 = "δ����";
		Vector<String[]> OrderList = //�õ������б�
					(Vector<String[]>)session.getAttribute("OrderList");
		int sellerid = DB.getSellerID(MainServlet.username);
		if(OrderList==null)//���Ϊ���򴴽�һ�������б����
		{OrderList = new Vector<String[]>();}			
		if(action.equals("ListDetail")){
			String oid = req.getParameter("oid");//�õ��������
			Vector<String []> ListDetail = Order_DB.getOrderDetail(oid);//ִ�в�ѯ				
			req.setAttribute("ListDetail",ListDetail);
			req.setAttribute("oid",oid);
			req.getRequestDispatcher("detail.jsp").forward(req,res);
		}
		
		else if(action.equals("allOrders")){//��������ѯ����
			String sql = "";//����SQL����
			int conditon = Integer.parseInt(req.getParameter("condition"));
			switch(conditon){
				case 1://1��ʾ���ж���
				sql = "select * from Orders where sellerID =" + sellerid;
				break;
				case 2://2��ʾ�Ѿ�����Ķ���
				sql = "select * from Orders where ostatus='"+ostatus1+"' and sellerID = '"+ sellerid+"'";
				break;
				case 3://3��ʾδ����Ķ���
				sql = "select * from Orders where ostatus='"+ostatus2+"' and sellerID = '"+ sellerid+"'";
				break;
			}
			Vector<String []> list = Order_DB.getOrderList(sql);				
			req.setAttribute("list",list);//�������б���				
			req.getRequestDispatcher("orders.jsp").forward(req,res);
		}
		else if(action.equals("query")){//����Ų�ѯ����
		    Vector<String []> list = null;
		    try{
		    	int oid = Integer.parseInt(req.getParameter("oid"));
				String sql = "select * from Orders where oid='"+oid+"'and sellerID ='"+sellerid+"'";
				list = Order_DB.getOrderList(sql);
		    }
		    catch(NumberFormatException nfe)//���붩���Ÿ�ʽ����ȷ
		    {list = new Vector<String []>();}//����һ���յ�����							
			req.setAttribute("list",list);
			req.getRequestDispatcher("orders.jsp").forward(req,res);
		}
		else if(action.equals("dealOrder")){//������
			String ostatus = req.getParameter("ostatus");
			int oid = Integer.parseInt(req.getParameter("oid"));
			//ƴװSQL
			String sqla = "update Orders set ostatus='"+ostatus+"' where oid='"+oid+"' and sellerID = '"+sellerid+"'";
			String sqlb = "update OrderDetail set ostatus='"+ostatus+"' where oid='"+oid+"' and sellerID ='"+sellerid+"'";			
			boolean b = DB.update(sqla,sqlb);//ִ�и���		
			if(b==true){
				msg = "��������ɹ�<br><br>"
					+"<a href=OrderServlet?action=allOrders&&condition=1>����";
			}
			else{msg = "�������������󣬴���ʧ��"
					+ "<a href=OrderServlet?action=allOrders&&condition=1>����>";
			}
			req.setAttribute("msg",msg);//���ش�����Ϣ
			req.getRequestDispatcher("info.jsp").forward(req,res);
			}
		}
}
