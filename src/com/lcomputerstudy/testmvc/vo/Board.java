package com.lcomputerstudy.testmvc.vo;

import java.util.Date;
import java.util.List;


public class Board {
	private int b_idx;
	private int c_idx;
	private int b_rownum;
	private String title;
	private String content;
	private int view;
	private String writer;
	private Date date;
	private int b_group;
	private int b_order;
	private int b_depth;
	private int c_group;
	private int c_order;
	private int c_depth;
	private List<BoardFile> files;
	
	public int getB_idx() {
		return b_idx;
	}
	
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	
	public int getB_rownum() {
		return b_rownum;
	}
	
	public void setB_rownum(int b_rownum) {
		this.b_rownum = b_rownum; 
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getView() {
		return view;
	}
	
	public void setView(int view) {
		this.view = view;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getB_group() {
		return b_group;
	}
	
	public void setB_group(int b_group) {
		this.b_group = b_group;
	}
	
	public int getB_order() {
		return b_order;
	}
	
	public void setB_order(int b_order) {
		this.b_order = b_order;
	}
	
	public int getB_depth() {
		return b_depth;
	}
	
	public void setB_depth(int b_depth) {
		this.b_depth = b_depth;
	}
	
	public int getC_idx() {
		return c_idx;
	}
	
	public void setC_idx(int c_idx) {
		this.c_idx = c_idx;
	}
	
	public int getC_group() {
		return c_group;
	}
	
	public void setC_group(int c_group) {
		this.c_group = c_group;
	}
	
	public int getC_order() {
		return c_order;
	}
	
	public void setC_order(int c_order) {
		this.c_order = c_order;
	}
	
	public int getC_depth() {
		return c_depth;
	}
	
	public void setC_depth(int c_depth) {
		this.c_depth = c_depth;
	}
	
	public List<BoardFile> getFiles(){
		return files;
	}
	
	public void setFiles(List<BoardFile> files) {
		this.files = files;
	}

}
