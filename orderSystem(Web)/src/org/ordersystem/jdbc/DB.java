package org.ordersystem.jdbc;


import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.ordersystem.servlet.MainServlet;
public class DB
{
	static Connection con;//声明Connection引用
	static Statement stat;//声明Statement引用
	static ResultSet rs;//声明ResultSet引用
//*****************************数据库连接和关闭操作*************************
	public static Connection getCon(){//得到数据库连接的方法	
		try{			
			 Context initial = new InitialContext();//得到上下文引用
			 DataSource ds = //得到DataSource引用
				    (DataSource)initial.lookup("java:comp/env/jdbc/orderSystem");
			 con = ds.getConnection();//得到数据库连接
		}
		catch(Exception e)
		{e.printStackTrace();}
		return con;//返回数据库连接
//		try{			
//			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			 con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OrderSystem","sa","123");
//		}
//		catch(ClassNotFoundException e)
//		{e.printStackTrace();}
//		catch(SQLException e1){
//			e1.printStackTrace();
//			}
//		return con;//返回数据库连接
	}
	
	public static ResultSet excuteQuery(String sql){
		try{
			stat=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stat.executeQuery(sql);
			
		}catch(SQLException e){
			System.err.println("Data.excuteQuery:" + e.getMessage());
		}
		return rs;
	}
	public static void closeCon(){//关闭数据库连接方法
		try	{
			  if(rs!=null){rs.close();}
			  if(stat!=null){stat.close();}
			  if(con!=null){con.close();}
		}
		catch(Exception e)
		{e.printStackTrace();}
	}
//******************获取用户真实姓名操作****************************************
	public static String getRealName(String username){
		String realName = "";
		String sql ="";
		try{
			con = DB.getCon();
			stat = con.createStatement();
			sql = "select CustomerName from Customer where LoginName ='"+username+"'";
			rs = stat.executeQuery(sql);
			while(rs.next()){
				realName = rs.getString(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{DB.closeCon();}
		return realName;
	}
//******************商家号操作**********************************************
	public static int getSellerID(String username){
		int id = 0;
		String sql ="";
		try{
			con = DB.getCon();
			stat = con.createStatement();
			sql = "select sellerID from Admin where adname ='"+username+"'";
			rs = stat.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt(1);}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//关闭数据库连接		
		return id;//返回结果
	}
	
	public static int getSellerID2(String sellername){
		String sql = "";
		int id = 0;
		try{
			con = DB.getCon();
			stat = con.createStatement();
			sql = "select sellerID from Seller where sellerName ='"+sellername+"'";
			rs = stat.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt(1);
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}
		return id;
	}
//******************分页操作**************************************************
	public static int getTotal(int span,int group){
		int result=0;//初始化返回页数
		String sql = "";//声明sql引用
		int sellerid = DB.getSellerID(MainServlet.username);
		try{
			con = DB.getCon();
			stat = con.createStatement();
			//得到相关记录的总条数
			if(group==0)//0代表所有分组
			{sql = "select count(*) from Product where sellerID=" + sellerid;}
			else{
				sql = "select count(*) from Product "+"where CategoryID='"+group+"'";
			}
			rs = stat.executeQuery(sql);//执行sql语句			
		    rs.next();
		    int rows=rs.getInt(1);//得到记录条数
		    result=rows/span+((rows%span==0)?0:1);//计算出总页数
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//关闭数据库连接		
		return result;//返回结果
	}
	public static Vector<String[]> getResource(String rlevel)
		{
			Vector<String[]> v=new Vector<String[]>();
			
			String sql;
			try
			{
				con=DB.getCon();
				stat=con.createStatement();
				String rlevell = new String(rlevel.getBytes("gbk"),"iso8859-1");
				sql="select rgid,rlevel,rmoney,rstatus from resource where rlevel='"+rlevell+"'";
				rs=stat.executeQuery(sql);
	            while(rs.next()){//遍历结果集得到分组信息		    
				    String group[] = new String[4];
				    for(int i=0;i<group.length;i++){
				      group[i] = //将信息添加到数组
				    	new String(rs.getString(i+1).getBytes("iso8859-1"),"gbk");
				    }			
					v.add(group);//将信息数组添加到返回的向量里
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DB.closeCon();
			}
			return  v;
		}
		public static Vector<String[]> getResource1(int sellerid)
		{
			Vector<String[]> v=new Vector<String[]>();
			try
			{
				con=DB.getCon();
				stat=con.createStatement();
				String sql="select ProductName,Price,Prodescription,CategoryID,Img,Sales from Product where sellerID=" + sellerid;
				rs=stat.executeQuery(sql);
	            while(rs.next()){//遍历结果集得到分组信息		    
				    String group[] = new String[6];
				    for(int i=0;i<group.length;i++){
				      group[i] = //将信息添加到数组
				      new String(rs.getString(i+1));
//				      System.out.println(group[i]);
				    }			
					v.add(group);//将信息数组添加到返回的向量里
				}
	          
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DB.closeCon();
			}
			return  v;
		}
		
	public static Vector<String[]> getPageContent(int page,int span,int group){
		Vector<String[]> v = new Vector<String[]>();//声明返回向量集合
		String sql = "";//声明sql语句引用
		int startRow = 	(page-1)*span;//计算出起始记录行数
		int sellerid = DB.getSellerID(MainServlet.username);
		try{
			con = DB.getCon();
			stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			if(group==0){//group参数为零，则是对所有的分组进行分页显示
				sql = "select ProductName ,Price,Stocks, ProductID ,CategoryName from "+
				       "Product,Category where Product.CategoryID = Category.CategoryID and sellerID='"+sellerid
				       + "'order by ProductID ";
			}
			else {//对具体分组进行分页显示
				sql = "select ProductName, Price,Stocks, ProductID, CategoryName from "+
				       "Product, Category where Product.CategoryID = Category.CategoryID "+
				 	   "and Product.CategoryID='"+group+"'and Product.sellerID='"+sellerid+"'order by ProductName";
			}
			rs = stat.executeQuery(sql);//执行sql语句，拿到结果集
			if(startRow!=0)//如果其实行不是第零行
			{rs.absolute(startRow);}//结果集滚动到起始行
			int c=0;//控制读取的记录条数
			while(c<span&&rs.next()){//从其实行读每页显示的记录条数
				String s[] = new String[5];
				for(int i=0;i<s.length;i++){
			      s[i] = //遍历结果集将信息添加到数组
			    	new String(rs.getString(i+1));
			    }
				
				v.add(s);//将数组添加到返回向量
				c++;
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//关闭数据库连接	
		return v;//返回结果
	}
//********************某条记录是否存在**************************************
		public static boolean isExist(String sql){//查看此条记录是否存在
			boolean flag = false;			
			try{			
				con = DB.getCon();//得到数据库连接
				stat = con.createStatement();//创建语句对象
				rs = stat.executeQuery(sql);//执行查询
				if(rs.next()) {flag = true;}
			}
			catch(Exception e)	{e.printStackTrace();}
			finally	{DB.closeCon();}//关闭数据库连接		
			return flag;//返回结果
		}		

//*********************更新数据库*****************************************
	public static int update(String sqla){
		int changedCount=0;
		try{
			String sql = new String(sqla.getBytes());
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			changedCount = stat.executeUpdate(sql);//进行更新
		}
		catch(Exception e)	{e.printStackTrace();}
		finally {DB.closeCon();}//关闭数据库连接 		
		return changedCount;//返回跟新记录条数
	}
	
	public static boolean update(String sqla,String sqlb){//事务处理
		boolean b = false;//记录是否更新成功
		try{
			con = DB.getCon();//得到数据库连接
			con.setAutoCommit(false);//禁止自动提交，开始一个事务
			stat = con.createStatement();
			stat.executeUpdate(sqla);//执行更新
			stat.executeUpdate(sqlb);//执行更新			
			con.commit();//将事务提交			
			con.setAutoCommit(true);//恢复自动提交模式
			b = true;//设置更新成功
		}
		catch(Exception e){
			e.printStackTrace();
			try{
				con.rollback();//出现问题，事务回滚
				b = false;
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		finally{DB.closeCon();}//关闭数据库连接
		return b;//返回更新成功或者失败标志
	}
	
		public static boolean updatea(String sqla)//我添加的
	{
		boolean b=false;
		try{
			String sql = new String(sqla);//转码
			con = DB.getCon();//得到数据库连接
			con.setAutoCommit(false);//禁止自动提交，开始一个事务
			stat = con.createStatement();//创建语句对象
			stat.executeUpdate(sql);//进行更新
			con.commit();//将事务提交			
			con.setAutoCommit(true);//恢复自动提交模式
			b = true;//设置更新成功
		}
		catch(Exception e){
			e.printStackTrace();
			try{
				con.rollback();//出现问题，事务回滚
				b = false;
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		finally{DB.closeCon();}//关闭数据库连接
		return b;//返回更新成功或者失败标志
	}
//********************根据一条SQL得到数据库中信息****************************
	public static String getInfo(String sql){
		String Info=null;
		try{			
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			rs = stat.executeQuery(sql);//执行查询
			if(rs.next())
			{Info=new String(rs.getString(1));}
		}
		catch(Exception e)	{e.printStackTrace();}
		finally {DB.closeCon();}		
		return Info;
	}
	//***********************得到资源信息信息********************************
	public static Vector<String> getGroupInfo(int gId){
		Vector<String> v =new Vector<String>();//创建返回信息向量	
		try{
			 con = DB.getCon();//得到数据库连接
			 stat = con.createStatement();//创建语句对象
			 String sql = "select CategoryID,CategoryName,Description from"+
			  				" Category where CategoryID="+gId;
			 rs = stat.executeQuery(sql);//执行SQL查询
			 if(rs.next()){//将结果集信息添加到返回向量中
//				System.out.println(rs.getString(1));
				v.add(new String(rs.getString(1)));
//				System.out.println(rs.getString(2));
				v.add(new String(rs.getString(2)));
//				System.out.println(rs.getString(3));
				v.add(new String(rs.getString(3)));
			 }
		}
		catch(Exception e){e.printStackTrace();}
		finally	{DB.closeCon();}//关闭数据库连接
		return v;//返回分组信息
	}
	
	public static Vector<String[]> getGroup(){
		Vector<String[]> v =new Vector<String[]>();//创建返回向量对象
		try{
			 con = DB.getCon();//得到数据库连接
			 stat = con.createStatement();//创建语句对象
			 String sql = "select CategoryID,CategoryName,Description from Category";
			 rs = stat.executeQuery(sql);
			 while(rs.next()){//遍历结果集得到分组信息		    
			    String group[] = new String[2];
			    for(int i=0;i<group.length;i++){
			      group[i] = //将信息添加到数组
			    	new String(rs.getString(i+1));
			    }			
				v.add(group);//将信息数组添加到返回的向量里
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally
		{DB.closeCon();}	
		return v;
	}
//*******************得到某张表某一列的最大值并加1***************************
		public static int getId(String table,String row){//得到一个表主键ID+1值
			int id = 0;
			try	{
				con = DB.getCon();//得到数据库连接
				stat = con.createStatement();//创建语句对象
				rs = stat.executeQuery("select count(*) from "+table);
				rs.next();
				if(rs.getInt(1)==0)	{ id = 1; }//如果表中没有记录，则将id置为1
				else{
					rs = stat.executeQuery("select max("+row+") from "+table);
					rs.next();
					id = Integer.parseInt(rs.getString(1))+1;//将其值加一
				}						
			}
			catch(Exception e){e.printStackTrace();}
			finally	{DB.closeCon();}//关闭数据库连接
			return id;//返回结果
		}
		

//******************得到资源的详细信息***************************
		public static Vector<String[]> getResInfo(String sqla){
			Vector<String []> v = new Vector<String[]>();
			try{
				con = DB.getCon();//得到数据库连接
				stat = con.createStatement();//创建语句对象			
				rs = stat.executeQuery(sqla);//执行查询
				while(rs.next()){
					String s[] = new String[6];
					for(int i=0;i<s.length-1;i++){//将资源信息添加到数组
						s[i] = new String(rs.getString(i+1));
					}				
					v.add(s);//将数组添加到返回向量中
				}
				for(String s[]:v){//根据分组ID得到分组名				
					String sqlb = "select CategoryName from Category where CategoryID='"+s[3]+"'";
					rs = stat.executeQuery(sqlb);
					rs.next();//结果集游标向后移一位
					s[5] = new String(rs.getString(1));
				}
			}
			catch(Exception e){e.printStackTrace();}
			finally{DB.closeCon();}//关闭数据库连接
			return v;//返回查询结果
		}
//***********************得到用户的详细信息********************************
	public static Vector<String> getUserInfo(String LoginName){
		Vector<String> userInfo=new Vector<String>();
		try{
			con = DB.getCon();//得到数据库连接
			stat = con.createStatement();//创建语句对象
			rs = stat.executeQuery("select Password,CustomerName,Sex,Telephone from Customer where LoginName='"+LoginName+"'");
			if(rs.next()){//将用户信息添加到向量中
			    userInfo.add(new String(rs.getString(1)));
				userInfo.add(new String(rs.getString(2)));
				userInfo.add(new String(rs.getString(3)));
				userInfo.add(new String(rs.getString(4)));
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}//关闭数据库连接		
		return userInfo;//返回用户信息
	}

}