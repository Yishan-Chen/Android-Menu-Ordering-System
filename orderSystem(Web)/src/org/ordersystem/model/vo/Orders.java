package org.ordersystem.model.vo;

public class Orders {
	private Integer oid;
	private String uName;
	private String otime;
	private String ostatus;
	
	public Integer getOid() {
		return oid;
	}
	
	public void setOid(Integer oid){
		this.oid = oid;
	}
	
	public String getUname(){
		return uName;
	}
	
	public void setUname(String uName){
		this.uName = uName;
	}
	
	public String getOtime(){
		return otime;
	}
	
	public void getOtime(String otime){
		this.otime = otime;
	}
	
	public String getOstatus(){
		return ostatus;
	}
	
	public void setOstatus(String ostatus){
		this.ostatus = ostatus;
	}

}
