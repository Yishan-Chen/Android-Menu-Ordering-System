package com.stu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	static Connection con;//����Connection����
	static Statement stat;//����Statement����
	static ResultSet rs;//����ResultSet����
	public static Connection getCon(){//�õ����ݿ����ӵķ���	
		try{			
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OrderSystem","sa","123");
		}
		catch(ClassNotFoundException e)
		{e.printStackTrace();}
		catch(SQLException e1){
			e1.printStackTrace();
			}
		return con;//�������ݿ�����
	}
}
