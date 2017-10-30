package org.ordersystem.jdbc;

import java.sql.*;
import javax.sql.*;
import java.util.*;
public class Delete_DB{
	private static Connection con=null;
	private static Statement stat=null;
	private static ResultSet rs=null;
	public static boolean isDelete(int oid){
		boolean b = false;
		int count=0;
		try{
			//oid = new String(oid.getBytes(),"iso8859-1");
		    con = DB.getCon();
		    stat = con.createStatement();
			String sqla="delete from Orders where oid="+oid;
			String sqlb="delete from OrderDetail where oid="+oid;
			count+=stat.executeUpdate(sqlb);
		    count+=stat.executeUpdate(sqla);
		    if(count==2){b=true;}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}
		return b;
	}
}