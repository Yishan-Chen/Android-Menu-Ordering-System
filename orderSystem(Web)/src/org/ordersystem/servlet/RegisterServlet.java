package org.ordersystem.servlet;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.management.Descriptor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ordersystem.jdbc.DB;
import org.ordersystem.jdbc.SqlSrvDBConn;


@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet{
	//private static final long serialVersionUID = 5769375969723769419L;
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,
	IOException{
		request.setCharacterEncoding("gbk");
		String usr=request.getParameter("username");
		String pwd=request.getParameter("password");
		String num=request.getParameter("regNum");
		String name=request.getParameter("sellerName");
		String msg="";
		int sid = 0;
		
		PreparedStatement ps=null;
		SqlSrvDBConn sqlsrvdb = new SqlSrvDBConn();
		Connection ct = sqlsrvdb.getConn();
				
		try{
			if(usr.equals("") || pwd.equals("") || num.equals("") || name.equals("")){
				msg="必须完整填写信息！<br/><a href=register.jsp>重新注册</a>";
			}
			else{
				String sqla = "select sellerID from Seller where sellerName = '" + name + "'";
				if(DB.isExist(sqla)){
					sid = DB.getSellerID2(name);
				}
				else{
					sid = DB.getId("Seller", "sellerID" );
					ps = ct.prepareStatement("insert into Seller(sellerID,sellerName) values(?,?)");
					ps.setInt(1, sid);
					ps.setString(2, name);
					ps.executeUpdate();
				}
				String sqlc = "select * from Admin where adname='"+usr+"'";
				if(DB.isExist(sqlc)){
					msg=usr+":用户名已存在，请重设！<br/><a href=register.jsp>重新注册</a>";}
				else{
					if(num.equals("YISHANDEABCEWQS")){
						ps = ct.prepareStatement("insert into Admin(adname,adpwd,sellerID) values(?,?,?)");
						ps.setString(1, usr);
						ps.setString(2, pwd);
						ps.setInt(3, sid);
						ps.executeUpdate();
						msg="注册成功！<br/><a href=login.jsp>现在登陆</a>";
						}
					else{
						msg="注册码错误！<br/><a href=register.jsp>重新注册</a>";
					}
				}
			}
			request.setAttribute("msg",msg);//转发消息
			request.getRequestDispatcher("error.jsp").forward(request,response);
			return;
		}
			catch(SQLException e){
			e.printStackTrace();
		}
	}
		
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,
	IOException{
		doGet(request,response);
	}
}
