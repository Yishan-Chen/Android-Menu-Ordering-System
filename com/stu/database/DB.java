package com.stu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	static Connection con;//声明Connection引用
	static Statement stat;//声明Statement引用
	static ResultSet rs;//声明ResultSet引用
	public static Connection getCon(){//得到数据库连接的方法	
		try{			
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OrderSystem","sa","123");
		}
		catch(ClassNotFoundException e)
		{e.printStackTrace();}
		catch(SQLException e1){
			e1.printStackTrace();
			}
		return con;//返回数据库连接
	}
}
