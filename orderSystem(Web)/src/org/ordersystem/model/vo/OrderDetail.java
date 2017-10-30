package org.ordersystem.model.vo;

public class OrderDetail {
	private Integer orid;
	private Integer oid;
	private String oName;
	private String otelNum;
	private String Address;
	private String oDescription;
	private String ostatus;
	private String categoryName;
	private String totalPrice;
	
	public Integer getOrid(){
		return this.orid;
	}
	
	public void setOrid(Integer orid){
		this.orid = orid;
	}
	
	public Integer getOid(){
		return this.oid;
	}
	
	public void setOid(Integer oid){
		this.oid = oid;
	}
	
	public String getOname(){
		return this.oName;
	}
	
	public void setOname(String oName){
		this.oName = oName;
	}
	
	public String getOtelnum(){
		return this.otelNum;
	}
	
	public void setOtelnum(String otelNum){
		this.otelNum = otelNum;
	}
	
	public String getAddress(){
		return this.Address;
	}
	
	public void setAddress(String Address){
		this.Address = Address;
	}
	
	public String getOdescription(){
		return this.oDescription;
	}
	
	public void setOdescription(String oDescription){
		this.oDescription = oDescription;
	}
	
	public String getOstatus(){
		return this.ostatus;
	}
	
	public void setOstatus(String ostatus){
		this.ostatus = ostatus;
	}
	
	public String getCategoryName(){
		return this.categoryName;
	} 
	
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public String getTotalPrice(){
		return totalPrice;
	} 
	
	public void setTotalPrice(String totalPrice){
		this.totalPrice = totalPrice;
	}
}
