package com.lcomputerstudy.testmvc.vo;


public class BoardFile {
	private int b_idx;
	private int f_idx;
	private String f_Oname;
	private String f_Cname;
	private long f_size;
	
	public int getF_idx() {
		return f_idx;
	}
	
	public void setF_idx(int f_idx) {
		this.f_idx = f_idx;
	}
	
	public String getF_Oname() {
		return f_Oname;	
	}
	
	public  void setF_Oname(String f_Oname) {
		this.f_Oname = f_Oname;
	}
	
	public String getF_Cname() {
		return f_Cname;	
	}
	
	public  void setF_Cname(String f_Cname) {
		this.f_Cname = f_Cname;
	}
	
	
	public long getF_size() {
		return f_size;
	}
	
	public void setF_size(long f_size) {
		this.f_size = f_size;
	}
	
	public int getB_idx() {
		return b_idx;
	}
	
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
}
