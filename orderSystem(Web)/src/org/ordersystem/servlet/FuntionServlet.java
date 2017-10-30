package org.ordersystem.servlet;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.ordersystem.jdbc.DB;

public class FuntionServlet extends HttpServlet {
	public void init(ServletConfig conf) throws ServletException {// Servlet��inti��ʼ������
		super.init(conf);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {// doGet����
		doPost(req, res);// ����doPost����
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("gbk");// ����req����Ϊgbk
		res.setCharacterEncoding("gbk");// ����res����Ϊgbk
		String action = req.getParameter("action");// �õ��������Ӧaction
		HttpSession session = req.getSession(true);// �õ�session����
		String msg = "";// ������Ϣ�ַ���
		int sellerid = DB.getSellerID(MainServlet.username);

		if (action.equals("adminList")) {
			Vector<String> list = new Vector<String>();

			int gId = Integer.parseInt(req.getParameter("gId"));
			// System.out.println(gId);
			if (gId == 0) {// 0�������з���
				list.add("0");
				list.add("���з���");
			} else {// ĳһ�ض�����
				list = DB.getGroupInfo(gId);
			}
			session.setAttribute("list", list);
			res.sendRedirect("resource.jsp");
		}

		else if (action.equals("queryRes")) {// ��ѯ
			String productName = req.getParameter("productName");// �õ���Դ����
			String sql = "select *"
					+ "from Product where ProductName='" + productName +"' and sellerID =" + sellerid;
			Vector<String[]> v = DB.getResInfo(sql);// ִ�в�ѯ
			req.setAttribute("list", v);
			req.getRequestDispatcher("resQuery.jsp").forward(req, res);
		}

		else if (action.equals("editRes")) {// �༭��Դ
			int productID = Integer.parseInt(req.getParameter("productID"));
			String sql = "select ProductName ,Price ,Stocks ,CategoryID ,ProductID from Product where ProductID="
					+ productID;
			Vector<String[]> rinfo = DB.getResInfo(sql);
			req.setAttribute("rinfo", rinfo);
			req.getRequestDispatcher("resInfo.jsp").forward(req, res);
		} 
		
		else if (action.equals("changeRes")) {
			String ProNameBefor = req.getParameter("ProNameBefor");// �༭ǰ�ı��
			String ProNameAfter = req.getParameter("ProNameAfter");// �༭��ı��
			int ProductID = Integer.parseInt(req.getParameter("ProductID"));// �õ���ԴID����
			// �õ��޸ĺ����Ϣ
			int CategoryID = Integer.parseInt(req.getParameter("CategoryID"));// �õ���ԴID����
			int stocks = Integer.parseInt(req.getParameter("stocks"));
			double price = Double.parseDouble(req.getParameter("price").trim());
			String sql = "update Product set ProductName ='" + ProNameAfter
					+ "',CategoryID ='" + CategoryID + "'," + "Price =" + price
					+ ",Stocks='" + stocks + "' where ProductID=" + ProductID;
			if (ProNameBefor.equals(ProNameAfter)) {// ���ڱ��û�ı�
				if (DB.update(sql) > 0) {
					msg = "�޸ı���ɹ�<br><br><a href=funtionServlet?action=adminList&&gId=0>����";
				}
			} else {// ���ڱ�Ÿı���
				String sqla = "select * from Product where ProductName='"
						+ ProNameAfter + "'";
				if (DB.isExist(sqla)) {
					msg = "�Ѿ��д���Դ����˶����롣<br><br>"
							+ "<a href=funtionServlet?action=editRes&&ProductID="
							+ ProductID + ">���ؼ����޸�";
				} else {
					if (DB.update(sql) > 0) {
						msg = "�޸ı���ɹ�<br><br><a href=funtionServlet?action=adminList&&gId=0>����";
					}
				}
			}
			this.forward(req, res, msg, "info.jsp");
		} else if (action.equals("deleteRes")) {
			String ProductID = req.getParameter("ProductID");// �õ�Ҫɾ����ID��
			String sql = "delete from Product where ProductID ='" + ProductID
					+ "'";
			if (DB.update(sql) > 0) {
				msg = "ɾ���ɹ�<br><br><a href=funtionServlet?action=adminList&&gId=0>����";
			} else {
				msg = "δ֪����ɾ��ʧ��";
			}
			this.forward(req, res, msg, "info.jsp");
		} else if (action.equals("addRes")) {
			// �õ�Ҫ�����Դ����ϸ��Ϣ
			String pName = req.getParameter("ProductName").trim();
			int cID = Integer.parseInt(req.getParameter("CategoryID"));
			int stocks = Integer.parseInt(req.getParameter("Stocks"));
			Double price = Double.parseDouble(req.getParameter("Price").trim());
			String sql = "select * from Product where ProductName='" + pName
					+ "'";
			if (DB.isExist(sql)) {
				msg = "�˱�Ŷ�Ӧ����Դ�Ѿ����ڣ���˶Ա�����롣<br><a href=addres.jsp>����";
			} else {
				int pid = DB.getId("Product", "ProductID");// �õ���Դ�����������ֵ��1
				sql = "insert into Product(ProductID,ProductName,CategoryID,sellerID,Price,Stocks)"
						+ "values("
						+ pid
						+ ",'"
						+ pName
						+ "','"
						+ cID
						+ "','"
						+ sellerid
						+ "','"
						+ price + "'," + stocks + ")";
				if (DB.update(sql) > 0) {
					msg = "������Դ�ɹ�������<br><a href=addres.jsp><br>����";
				}
			}
			this.forward(req, res, msg, "info.jsp");
		}

		if (action.equals("changePwd")) {// ����Ա�޸�����
			String adname = req.getParameter("adname");
			String adpwd = req.getParameter("adpwd");
			String newPwd = req.getParameter("newPwd");
			String sql = "select adpwd from Admin where adname='" + adname
					+ "'";
			String pwdFromDB = DB.getInfo(sql);
			if (pwdFromDB == null) {
				msg = "�ù���Ա�û������ڣ������º˶�����";
			} else if (!pwdFromDB.equals(adpwd)) {
				msg = "�������벻��ȷ������������";
			} else {
				sql = "update Admin set adpwd='" + newPwd + "' where adname='"
						+ adname + "'";
				if (DB.update(sql) > 0) {
					msg = "�޸ĳɹ�";
				} else {
					msg = "δ֪�����޸�ʧ�ܣ�����";
				}
			}
			this.forward(req, res, msg, "adinfo.jsp");
		}
	}

	public void forward(HttpServletRequest req, HttpServletResponse res,
			String msg, String url) throws ServletException, IOException {
		req.setAttribute("msg", msg);// ������Ϣ
		req.getRequestDispatcher("info.jsp").forward(req, res);// ת������Ϣҳ��
	}

}
