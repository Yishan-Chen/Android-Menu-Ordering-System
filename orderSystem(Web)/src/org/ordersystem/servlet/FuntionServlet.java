package org.ordersystem.servlet;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.ordersystem.jdbc.DB;

public class FuntionServlet extends HttpServlet {
	public void init(ServletConfig conf) throws ServletException {// Servlet的inti初始化方法
		super.init(conf);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {// doGet方法
		doPost(req, res);// 调用doPost方法
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("gbk");// 设置req编码为gbk
		res.setCharacterEncoding("gbk");// 设置res编码为gbk
		String action = req.getParameter("action");// 得到请求的响应action
		HttpSession session = req.getSession(true);// 得到session对象
		String msg = "";// 声明消息字符串
		int sellerid = DB.getSellerID(MainServlet.username);

		if (action.equals("adminList")) {
			Vector<String> list = new Vector<String>();

			int gId = Integer.parseInt(req.getParameter("gId"));
			// System.out.println(gId);
			if (gId == 0) {// 0代表所有分组
				list.add("0");
				list.add("所有分组");
			} else {// 某一特定分组
				list = DB.getGroupInfo(gId);
			}
			session.setAttribute("list", list);
			res.sendRedirect("resource.jsp");
		}

		else if (action.equals("queryRes")) {// 查询
			String productName = req.getParameter("productName");// 得到资源名称
			String sql = "select *"
					+ "from Product where ProductName='" + productName +"' and sellerID =" + sellerid;
			Vector<String[]> v = DB.getResInfo(sql);// 执行查询
			req.setAttribute("list", v);
			req.getRequestDispatcher("resQuery.jsp").forward(req, res);
		}

		else if (action.equals("editRes")) {// 编辑资源
			int productID = Integer.parseInt(req.getParameter("productID"));
			String sql = "select ProductName ,Price ,Stocks ,CategoryID ,ProductID from Product where ProductID="
					+ productID;
			Vector<String[]> rinfo = DB.getResInfo(sql);
			req.setAttribute("rinfo", rinfo);
			req.getRequestDispatcher("resInfo.jsp").forward(req, res);
		} 
		
		else if (action.equals("changeRes")) {
			String ProNameBefor = req.getParameter("ProNameBefor");// 编辑前的编号
			String ProNameAfter = req.getParameter("ProNameAfter");// 编辑后的编号
			int ProductID = Integer.parseInt(req.getParameter("ProductID"));// 得到资源ID主键
			// 得到修改后的信息
			int CategoryID = Integer.parseInt(req.getParameter("CategoryID"));// 得到资源ID主键
			int stocks = Integer.parseInt(req.getParameter("stocks"));
			double price = Double.parseDouble(req.getParameter("price").trim());
			String sql = "update Product set ProductName ='" + ProNameAfter
					+ "',CategoryID ='" + CategoryID + "'," + "Price =" + price
					+ ",Stocks='" + stocks + "' where ProductID=" + ProductID;
			if (ProNameBefor.equals(ProNameAfter)) {// 组内编号没改变
				if (DB.update(sql) > 0) {
					msg = "修改保存成功<br><br><a href=funtionServlet?action=adminList&&gId=0>返回";
				}
			} else {// 组内编号改变了
				String sqla = "select * from Product where ProductName='"
						+ ProNameAfter + "'";
				if (DB.isExist(sqla)) {
					msg = "已经有此资源，请核对输入。<br><br>"
							+ "<a href=funtionServlet?action=editRes&&ProductID="
							+ ProductID + ">返回继续修改";
				} else {
					if (DB.update(sql) > 0) {
						msg = "修改保存成功<br><br><a href=funtionServlet?action=adminList&&gId=0>返回";
					}
				}
			}
			this.forward(req, res, msg, "info.jsp");
		} else if (action.equals("deleteRes")) {
			String ProductID = req.getParameter("ProductID");// 得到要删除的ID号
			String sql = "delete from Product where ProductID ='" + ProductID
					+ "'";
			if (DB.update(sql) > 0) {
				msg = "删除成功<br><br><a href=funtionServlet?action=adminList&&gId=0>返回";
			} else {
				msg = "未知错误，删除失败";
			}
			this.forward(req, res, msg, "info.jsp");
		} else if (action.equals("addRes")) {
			// 得到要添加资源的详细信息
			String pName = req.getParameter("ProductName").trim();
			int cID = Integer.parseInt(req.getParameter("CategoryID"));
			int stocks = Integer.parseInt(req.getParameter("Stocks"));
			Double price = Double.parseDouble(req.getParameter("Price").trim());
			String sql = "select * from Product where ProductName='" + pName
					+ "'";
			if (DB.isExist(sql)) {
				msg = "此编号对应的资源已经存在，请核对编号输入。<br><a href=addres.jsp>返回";
			} else {
				int pid = DB.getId("Product", "ProductID");// 得到资源表中主键最大值加1
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
					msg = "增加资源成功！！！<br><a href=addres.jsp><br>返回";
				}
			}
			this.forward(req, res, msg, "info.jsp");
		}

		if (action.equals("changePwd")) {// 管理员修改密码
			String adname = req.getParameter("adname");
			String adpwd = req.getParameter("adpwd");
			String newPwd = req.getParameter("newPwd");
			String sql = "select adpwd from Admin where adname='" + adname
					+ "'";
			String pwdFromDB = DB.getInfo(sql);
			if (pwdFromDB == null) {
				msg = "该管理员用户不存在，请重新核对输入";
			} else if (!pwdFromDB.equals(adpwd)) {
				msg = "密码输入不正确，请重新输入";
			} else {
				sql = "update Admin set adpwd='" + newPwd + "' where adname='"
						+ adname + "'";
				if (DB.update(sql) > 0) {
					msg = "修改成功";
				} else {
					msg = "未知错误，修改失败！！！";
				}
			}
			this.forward(req, res, msg, "adinfo.jsp");
		}
	}

	public void forward(HttpServletRequest req, HttpServletResponse res,
			String msg, String url) throws ServletException, IOException {
		req.setAttribute("msg", msg);// 设置消息
		req.getRequestDispatcher("info.jsp").forward(req, res);// 转发到消息页面
	}

}
