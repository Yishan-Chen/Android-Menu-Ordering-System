package org.ordersystem.jdbc;


import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.ordersystem.servlet.MainServlet;
public class DB
{
	static Connection con;//����Connection����
	static Statement stat;//����Statement����
	static ResultSet rs;//����ResultSet����
//*****************************���ݿ����Ӻ͹رղ���*************************
	public static Connection getCon(){//�õ����ݿ����ӵķ���	
		try{			
			 Context initial = new InitialContext();//�õ�����������
			 DataSource ds = //�õ�DataSource����
				    (DataSource)initial.lookup("java:comp/env/jdbc/orderSystem");
			 con = ds.getConnection();//�õ����ݿ�����
		}
		catch(Exception e)
		{e.printStackTrace();}
		return con;//�������ݿ�����
//		try{			
//			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			 con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=OrderSystem","sa","123");
//		}
//		catch(ClassNotFoundException e)
//		{e.printStackTrace();}
//		catch(SQLException e1){
//			e1.printStackTrace();
//			}
//		return con;//�������ݿ�����
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
	public static void closeCon(){//�ر����ݿ����ӷ���
		try	{
			  if(rs!=null){rs.close();}
			  if(stat!=null){stat.close();}
			  if(con!=null){con.close();}
		}
		catch(Exception e)
		{e.printStackTrace();}
	}
//******************��ȡ�û���ʵ��������****************************************
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
//******************�̼ҺŲ���**********************************************
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
		finally{DB.closeCon();}//�ر����ݿ�����		
		return id;//���ؽ��
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
//******************��ҳ����**************************************************
	public static int getTotal(int span,int group){
		int result=0;//��ʼ������ҳ��
		String sql = "";//����sql����
		int sellerid = DB.getSellerID(MainServlet.username);
		try{
			con = DB.getCon();
			stat = con.createStatement();
			//�õ���ؼ�¼��������
			if(group==0)//0�������з���
			{sql = "select count(*) from Product where sellerID=" + sellerid;}
			else{
				sql = "select count(*) from Product "+"where CategoryID='"+group+"'";
			}
			rs = stat.executeQuery(sql);//ִ��sql���			
		    rs.next();
		    int rows=rs.getInt(1);//�õ���¼����
		    result=rows/span+((rows%span==0)?0:1);//�������ҳ��
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//�ر����ݿ�����		
		return result;//���ؽ��
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
	            while(rs.next()){//����������õ�������Ϣ		    
				    String group[] = new String[4];
				    for(int i=0;i<group.length;i++){
				      group[i] = //����Ϣ��ӵ�����
				    	new String(rs.getString(i+1).getBytes("iso8859-1"),"gbk");
				    }			
					v.add(group);//����Ϣ������ӵ����ص�������
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
	            while(rs.next()){//����������õ�������Ϣ		    
				    String group[] = new String[6];
				    for(int i=0;i<group.length;i++){
				      group[i] = //����Ϣ��ӵ�����
				      new String(rs.getString(i+1));
//				      System.out.println(group[i]);
				    }			
					v.add(group);//����Ϣ������ӵ����ص�������
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
		Vector<String[]> v = new Vector<String[]>();//����������������
		String sql = "";//����sql�������
		int startRow = 	(page-1)*span;//�������ʼ��¼����
		int sellerid = DB.getSellerID(MainServlet.username);
		try{
			con = DB.getCon();
			stat = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			if(group==0){//group����Ϊ�㣬���Ƕ����еķ�����з�ҳ��ʾ
				sql = "select ProductName ,Price,Stocks, ProductID ,CategoryName from "+
				       "Product,Category where Product.CategoryID = Category.CategoryID and sellerID='"+sellerid
				       + "'order by ProductID ";
			}
			else {//�Ծ��������з�ҳ��ʾ
				sql = "select ProductName, Price,Stocks, ProductID, CategoryName from "+
				       "Product, Category where Product.CategoryID = Category.CategoryID "+
				 	   "and Product.CategoryID='"+group+"'and Product.sellerID='"+sellerid+"'order by ProductName";
			}
			rs = stat.executeQuery(sql);//ִ��sql��䣬�õ������
			if(startRow!=0)//�����ʵ�в��ǵ�����
			{rs.absolute(startRow);}//�������������ʼ��
			int c=0;//���ƶ�ȡ�ļ�¼����
			while(c<span&&rs.next()){//����ʵ�ж�ÿҳ��ʾ�ļ�¼����
				String s[] = new String[5];
				for(int i=0;i<s.length;i++){
			      s[i] = //�������������Ϣ��ӵ�����
			    	new String(rs.getString(i+1));
			    }
				
				v.add(s);//��������ӵ���������
				c++;
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}//�ر����ݿ�����	
		return v;//���ؽ��
	}
//********************ĳ����¼�Ƿ����**************************************
		public static boolean isExist(String sql){//�鿴������¼�Ƿ����
			boolean flag = false;			
			try{			
				con = DB.getCon();//�õ����ݿ�����
				stat = con.createStatement();//����������
				rs = stat.executeQuery(sql);//ִ�в�ѯ
				if(rs.next()) {flag = true;}
			}
			catch(Exception e)	{e.printStackTrace();}
			finally	{DB.closeCon();}//�ر����ݿ�����		
			return flag;//���ؽ��
		}		

//*********************�������ݿ�*****************************************
	public static int update(String sqla){
		int changedCount=0;
		try{
			String sql = new String(sqla.getBytes());
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������
			changedCount = stat.executeUpdate(sql);//���и���
		}
		catch(Exception e)	{e.printStackTrace();}
		finally {DB.closeCon();}//�ر����ݿ����� 		
		return changedCount;//���ظ��¼�¼����
	}
	
	public static boolean update(String sqla,String sqlb){//������
		boolean b = false;//��¼�Ƿ���³ɹ�
		try{
			con = DB.getCon();//�õ����ݿ�����
			con.setAutoCommit(false);//��ֹ�Զ��ύ����ʼһ������
			stat = con.createStatement();
			stat.executeUpdate(sqla);//ִ�и���
			stat.executeUpdate(sqlb);//ִ�и���			
			con.commit();//�������ύ			
			con.setAutoCommit(true);//�ָ��Զ��ύģʽ
			b = true;//���ø��³ɹ�
		}
		catch(Exception e){
			e.printStackTrace();
			try{
				con.rollback();//�������⣬����ع�
				b = false;
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		finally{DB.closeCon();}//�ر����ݿ�����
		return b;//���ظ��³ɹ�����ʧ�ܱ�־
	}
	
		public static boolean updatea(String sqla)//����ӵ�
	{
		boolean b=false;
		try{
			String sql = new String(sqla);//ת��
			con = DB.getCon();//�õ����ݿ�����
			con.setAutoCommit(false);//��ֹ�Զ��ύ����ʼһ������
			stat = con.createStatement();//����������
			stat.executeUpdate(sql);//���и���
			con.commit();//�������ύ			
			con.setAutoCommit(true);//�ָ��Զ��ύģʽ
			b = true;//���ø��³ɹ�
		}
		catch(Exception e){
			e.printStackTrace();
			try{
				con.rollback();//�������⣬����ع�
				b = false;
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		finally{DB.closeCon();}//�ر����ݿ�����
		return b;//���ظ��³ɹ�����ʧ�ܱ�־
	}
//********************����һ��SQL�õ����ݿ�����Ϣ****************************
	public static String getInfo(String sql){
		String Info=null;
		try{			
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������
			rs = stat.executeQuery(sql);//ִ�в�ѯ
			if(rs.next())
			{Info=new String(rs.getString(1));}
		}
		catch(Exception e)	{e.printStackTrace();}
		finally {DB.closeCon();}		
		return Info;
	}
	//***********************�õ���Դ��Ϣ��Ϣ********************************
	public static Vector<String> getGroupInfo(int gId){
		Vector<String> v =new Vector<String>();//����������Ϣ����	
		try{
			 con = DB.getCon();//�õ����ݿ�����
			 stat = con.createStatement();//����������
			 String sql = "select CategoryID,CategoryName,Description from"+
			  				" Category where CategoryID="+gId;
			 rs = stat.executeQuery(sql);//ִ��SQL��ѯ
			 if(rs.next()){//���������Ϣ��ӵ�����������
//				System.out.println(rs.getString(1));
				v.add(new String(rs.getString(1)));
//				System.out.println(rs.getString(2));
				v.add(new String(rs.getString(2)));
//				System.out.println(rs.getString(3));
				v.add(new String(rs.getString(3)));
			 }
		}
		catch(Exception e){e.printStackTrace();}
		finally	{DB.closeCon();}//�ر����ݿ�����
		return v;//���ط�����Ϣ
	}
	
	public static Vector<String[]> getGroup(){
		Vector<String[]> v =new Vector<String[]>();//����������������
		try{
			 con = DB.getCon();//�õ����ݿ�����
			 stat = con.createStatement();//����������
			 String sql = "select CategoryID,CategoryName,Description from Category";
			 rs = stat.executeQuery(sql);
			 while(rs.next()){//����������õ�������Ϣ		    
			    String group[] = new String[2];
			    for(int i=0;i<group.length;i++){
			      group[i] = //����Ϣ��ӵ�����
			    	new String(rs.getString(i+1));
			    }			
				v.add(group);//����Ϣ������ӵ����ص�������
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally
		{DB.closeCon();}	
		return v;
	}
//*******************�õ�ĳ�ű�ĳһ�е����ֵ����1***************************
		public static int getId(String table,String row){//�õ�һ��������ID+1ֵ
			int id = 0;
			try	{
				con = DB.getCon();//�õ����ݿ�����
				stat = con.createStatement();//����������
				rs = stat.executeQuery("select count(*) from "+table);
				rs.next();
				if(rs.getInt(1)==0)	{ id = 1; }//�������û�м�¼����id��Ϊ1
				else{
					rs = stat.executeQuery("select max("+row+") from "+table);
					rs.next();
					id = Integer.parseInt(rs.getString(1))+1;//����ֵ��һ
				}						
			}
			catch(Exception e){e.printStackTrace();}
			finally	{DB.closeCon();}//�ر����ݿ�����
			return id;//���ؽ��
		}
		

//******************�õ���Դ����ϸ��Ϣ***************************
		public static Vector<String[]> getResInfo(String sqla){
			Vector<String []> v = new Vector<String[]>();
			try{
				con = DB.getCon();//�õ����ݿ�����
				stat = con.createStatement();//����������			
				rs = stat.executeQuery(sqla);//ִ�в�ѯ
				while(rs.next()){
					String s[] = new String[6];
					for(int i=0;i<s.length-1;i++){//����Դ��Ϣ��ӵ�����
						s[i] = new String(rs.getString(i+1));
					}				
					v.add(s);//��������ӵ�����������
				}
				for(String s[]:v){//���ݷ���ID�õ�������				
					String sqlb = "select CategoryName from Category where CategoryID='"+s[3]+"'";
					rs = stat.executeQuery(sqlb);
					rs.next();//������α������һλ
					s[5] = new String(rs.getString(1));
				}
			}
			catch(Exception e){e.printStackTrace();}
			finally{DB.closeCon();}//�ر����ݿ�����
			return v;//���ز�ѯ���
		}
//***********************�õ��û�����ϸ��Ϣ********************************
	public static Vector<String> getUserInfo(String LoginName){
		Vector<String> userInfo=new Vector<String>();
		try{
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������
			rs = stat.executeQuery("select Password,CustomerName,Sex,Telephone from Customer where LoginName='"+LoginName+"'");
			if(rs.next()){//���û���Ϣ��ӵ�������
			    userInfo.add(new String(rs.getString(1)));
				userInfo.add(new String(rs.getString(2)));
				userInfo.add(new String(rs.getString(3)));
				userInfo.add(new String(rs.getString(4)));
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}//�ر����ݿ�����		
		return userInfo;//�����û���Ϣ
	}

}