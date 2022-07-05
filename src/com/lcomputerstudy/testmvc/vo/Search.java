package com.lcomputerstudy.testmvc.vo;

public class Search {
	private String option;
	private String text;
	
	public String getOption() {
		return option;
	}
	
	public String getText() {
		return text;
	}
	
	public void setSearch(String option, String text) {
		this.option = option;
		this.text= text;
	}
}
