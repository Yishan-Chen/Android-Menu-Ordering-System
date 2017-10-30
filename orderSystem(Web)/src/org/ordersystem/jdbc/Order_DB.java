package org.ordersystem.jdbc;

import java.sql.*;
import java.util.*;
public class Order_DB{
	private static Connection con=null;//声明Connection引用
	private static Statement stat=null;//声明Statement引用
	private static ResultSet rs=null;//声明ResultSet引用
	
	public static Vector<String []> getOrderList(String sqla){//得到用户已提交订单
		Vector<String []> v = new Vector<String[]>();//创建返回向量
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象		
			String sql = new String(sqla);//转码
			rs = stat.executeQuery(sql);//执行查询
			while(rs.next()){//遍历结果集
				String s[] = new String[4];
				for(int i=0;i<s.length;i++){//对信息进行转
					s[i] = new String(rs.getString(i+1));
				}				
				v.add(s);//将订单信息添加进返回向量
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	
	public static Vector<String []> getOrderDetail(String oid){//得到某一订单详情
		Vector<String []> v = new Vector<String[]>();
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			rs = stat.executeQuery("select oName,otelNum,Address,oDescription,ostatus,totalPrice,message from OrderDetail"+
						" where oid='"+oid+"'");//执行查询得到结果集			
			while(rs.next()){//遍历结果集
				String s[] = new String[7];
				for(int i=0;i<s.length;i++){//转码
					s[i] = new String(rs.getString(i+1));
				}										
				v.add(s);//将信息添加到返回向量
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	public static Vector<String []> getOrderListThree(String sql){//得到用户已提交订单其中的三项
		Vector<String []> v = new Vector<String[]>();//创建返回向量
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象		
			rs = stat.executeQuery(sql);//执行查询
			while(rs.next()){//遍历结果集
				String s[] = new String[3];
				for(int i=0;i<s.length;i++){//对信息进行转
					s[i] = new String(rs.getString(i+1));
				}				
				v.add(s);//将订单信息添加进返回向量
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	
}