package org.ordersystem.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ordersystem.jdbc.DB;
import org.ordersystem.jdbc.SqlSrvDBConn;
import org.ordersystem.model.vo.Admin;

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet{
	//private static final long serialVersionUID = 4239094099814273948L;
	public static String username;
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,
					IOException{
		request.setCharacterEncoding("gbk");
		String usr=request.getParameter("adname");
		String pwd=request.getParameter("password");
		username = usr;
		
		boolean validated = false;
		SqlSrvDBConn sqlsrvdb = new SqlSrvDBConn();
		HttpSession session = request.getSession();
		Admin user = null;
		user=(Admin)session.getAttribute("user");
		String msg = "";
		
		if(user==null){
			String sql = "select * from Admin";
			ResultSet rs = sqlsrvdb.excuteQuery(sql);
			try{
				while(rs.next())
				{	
					if(usr.equals(rs.getString("adname"))
							&&(pwd.equals(rs.getString("adpwd"))))
					{	
						user=new Admin();
						user.setId(rs.getInt(1));
						user.setUsername(rs.getString(2));
						user.setPassword(rs.getString(3));
						session.setAttribute("usr",usr);
						validated = true;
					}
				}
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			sqlsrvdb.closeStmt();
			sqlsrvdb.closeStmt();
			DB.closeCon();
		}
		else{
			validated = true;
		}
		if(validated){
			response.sendRedirect("main.jsp");
		}
		else{
			msg="登录名或密码有误！<br/><a href=login.jsp>返回登录</a>";
			request.setAttribute("msg",msg);//转发消息
			request.getRequestDispatcher("error.jsp").forward(request,response);
		}
		
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,
	IOException{
		doGet(request,response);
	}
}

