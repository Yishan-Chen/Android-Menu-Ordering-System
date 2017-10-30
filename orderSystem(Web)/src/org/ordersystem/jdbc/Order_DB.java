package org.ordersystem.jdbc;

import java.sql.*;
import java.util.*;
public class Order_DB{
	private static Connection con=null;//����Connection����
	private static Statement stat=null;//����Statement����
	private static ResultSet rs=null;//����ResultSet����
	
	public static Vector<String []> getOrderList(String sqla){//�õ��û����ύ����
		Vector<String []> v = new Vector<String[]>();//������������
		try{
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������		
			String sql = new String(sqla);//ת��
			rs = stat.executeQuery(sql);//ִ�в�ѯ
			while(rs.next()){//���������
				String s[] = new String[4];
				for(int i=0;i<s.length;i++){//����Ϣ����ת
					s[i] = new String(rs.getString(i+1));
				}				
				v.add(s);//��������Ϣ��ӽ���������
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	
	public static Vector<String []> getOrderDetail(String oid){//�õ�ĳһ��������
		Vector<String []> v = new Vector<String[]>();
		try{
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������
			rs = stat.executeQuery("select oName,otelNum,Address,oDescription,ostatus,totalPrice,message from OrderDetail"+
						" where oid='"+oid+"'");//ִ�в�ѯ�õ������			
			while(rs.next()){//���������
				String s[] = new String[7];
				for(int i=0;i<s.length;i++){//ת��
					s[i] = new String(rs.getString(i+1));
				}										
				v.add(s);//����Ϣ��ӵ���������
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	public static Vector<String []> getOrderListThree(String sql){//�õ��û����ύ�������е�����
		Vector<String []> v = new Vector<String[]>();//������������
		try{
			con = DB.getCon();//�õ����ݿ�����
			stat = con.createStatement();//����������		
			rs = stat.executeQuery(sql);//ִ�в�ѯ
			while(rs.next()){//���������
				String s[] = new String[3];
				for(int i=0;i<s.length;i++){//����Ϣ����ת
					s[i] = new String(rs.getString(i+1));
				}				
				v.add(s);//��������Ϣ��ӽ���������
			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally	{DB.closeCon();}
		return v;
	}
	
}