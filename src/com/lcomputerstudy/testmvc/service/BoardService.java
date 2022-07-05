package com.lcomputerstudy.testmvc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lcomputerstudy.testmvc.dao.BoardDAO;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.BoardFile;
import com.lcomputerstudy.testmvc.vo.User;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.Search;

public class BoardService {
	private static BoardService b_service = null;
	private static BoardDAO b_dao = null;

	private BoardService() {
		
	}
	
	public static BoardService getInstance() {
		if(b_service == null) {
			b_service = new BoardService();
			b_dao = BoardDAO.getInstance();
		}
		
		return b_service;
	}
	
	public void uploadFile(Board board, BoardFile files) {
		b_dao.uploadFile(board, files);
	}
	
	public ArrayList<BoardFile> getUploadFile(Board board){
		return b_dao.getUploadFile(board);
	}
	
	public int registBoard(Board board) {
		return b_dao.registBoard(board);
	}
	
	public ArrayList<Board> getBoards(Pagination pagination, Search search){
		return b_dao.getBoards(pagination, search);
	}
	
	public int getBoardsCount(Search search) {
		return b_dao.getBoardsCount(search);
	}
	
	public Board detailBoard(Board board) {
		return b_dao.detailBoard(board);
	}
		
	public void updateBoard(Board board) {
		b_dao.updateBoard(board);
		
	}
	
	public void editBoard(Board board) {
		b_dao.editBoard(board);
	}
	
	public void delBoard(Board board) {
		b_dao.delBoard(board);
	}
	
	public void commentBoard(Board board) {
		b_dao.commentBoard(board);
	}
	
	public ArrayList<Board> getComments(Board board){
		return b_dao.getComments(board);
	}
	
	public void delComment(Board board) {
		b_dao.delComment(board);
	}
	
	public ArrayList<Board> getSearch(String OptionSelect, String SearchText){
		return b_dao.getSearch(OptionSelect, SearchText);
	}

}
