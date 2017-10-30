package org.ordersystem.jdbc;
import java.sql.*;

public class SqlSrvDBConn {
	private Statement stmt;
	private Connection conn;
	ResultSet rs;
	
	public  SqlSrvDBConn(){
		stmt = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OrderSystem","sa","123");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		rs=null;
	}
	
	public  Connection getConn(){
		return this.conn;
	}
	public ResultSet excuteQuery(String sql){
		try{
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery(sql);
			
		}catch(SQLException e){
			System.err.println("Data.excuteQuery:" + e.getMessage());
		}
		return rs;
	}
	
	public void closeStmt()
	{
		try{
			stmt.close();
		}catch(SQLException e){
			System.err.println("Data.excuteQuery:" + e.getMessage());
		}
	}
	
	public void closeConn()
	{
		try{
			conn.close();
		}catch(SQLException e){
			System.err.println("Data.excuteQuery:" + e.getMessage());
		}
	}
}
