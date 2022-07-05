package com.lcomputerstudy.testmvc.dao;

import com.lcomputerstudy.testmvc.vo.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lcomputerstudy.testmvc.database.*;

public class BoardDAO {
	private static BoardDAO b_dao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(b_dao==null) {
			b_dao = new BoardDAO();
		}
		
		return b_dao;
	}
	
	public int registBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bIdx = 0;
			
		if((Integer)board.getB_group() > 0) {
			try {
				conn = DBconnection.getConnection();
				String sql = "insert into board(title,writer,content,view,b_group,b_order,b_depth) value(?,?,?,?,?,?,?)";
				String sql2 = "update board set b_order = b_order +1 where b_group= ? and b_idx <> last_insert_id() and b_order >= ? ";
				String sql3 = "select b_idx from board where b_idx = last_insert_id()";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getWriter());
				pstmt.setString(3, board.getContent());
				pstmt.setInt(4, board.getView());
				pstmt.setInt(5, board.getB_group());
				pstmt.setInt(6, board.getB_order());
				pstmt.setInt(7, board.getB_depth());
				pstmt.executeUpdate();
				pstmt.close();		
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, board.getB_group());
				pstmt.setInt(2, board.getB_order());
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = conn.prepareStatement(sql3);
				rs = pstmt.executeQuery();
				
							
			} catch (Exception ex) {
				System.out.println("SQLException : " + ex.getMessage());
			} finally {
				try{
					if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} else {	
			try {
				conn = DBconnection.getConnection();
				String sql = "insert into board(title,writer,content,view,b_group,b_order,b_depth) value(?,?,?,?,?,?,?)";
				String sql2 = "update board set b_group = last_insert_id() where b_idx = last_insert_id() ";
				String sql3 = "select b_idx from board where b_idx = last_insert_id()";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getWriter());
				pstmt.setString(3, board.getContent());
				pstmt.setInt(4, board.getView());
				pstmt.setInt(5, board.getB_group());
				pstmt.setInt(6, board.getB_order());
				pstmt.setInt(7, board.getB_depth());
				pstmt.executeUpdate();
				pstmt.close();		
				pstmt = conn.prepareStatement(sql2);
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = conn.prepareStatement(sql3);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					bIdx = rs.getInt("b_idx");
				}
				
				
			} catch (Exception ex) {
				System.out.println("SQLException : " + ex.getMessage());
			} finally {
				try{
					if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
		
		return bIdx;
		
	}
	
	public void uploadFile(Board board, BoardFile files) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBconnection.getConnection();
			String sql = "INSERT INTO FILE(b_idx, f_Oname,f_Cname,f_size) VALUE(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			pstmt.setString(2, files.getF_Oname());
			pstmt.setString(3, files.getF_Cname());
			pstmt.setLong(4, files.getF_size());
			pstmt.executeQuery();
			
		}catch(Exception e) {
			
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
	
	public ArrayList<BoardFile> getUploadFile(Board board){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardFile> bf_list = null;
		
		try {
			conn = DBconnection.getConnection();
			String sql1 = "SELECT * FROM file WHERE b_idx = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			bf_list = new ArrayList<BoardFile>();
			
			while(rs.next()) {
				BoardFile files = new BoardFile();
				files.setF_idx(rs.getInt("f_idx"));
				files.setB_idx(rs.getInt("b_idx"));
				files.setF_Oname(rs.getString("f_Oname"));
				files.setF_Cname(rs.getString("f_Cname"));
				files.setF_size(rs.getLong("f_size"));
				bf_list.add(files);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return bf_list;
	}


	public ArrayList<Board> getBoards(Pagination pagination, Search search){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> b_list = null;
		int pageNum = pagination.getPageNum();
		String otext = search.getText();
		String text = "%"+search.getText()+"%";
		String option = search.getOption();
		
		if((otext != null && !otext.equals("")) && (option != null && !option.equals(""))) {
			try {
				String where = null;
				if(option.equals("title")){
					 where ="WHERE title Like ?";
				}else if (option.equals("content")) {
					 where = "WHERE (title Like ? or content Like ?)";
				}else {
					 where = "WHERE writer Like ?";
				}
				conn = DBconnection.getConnection();
				String query = new StringBuilder()
						.append("SELECT		@b_rownum := @b_rownum -1 AS b_rownum,\n")
						.append("			ta.*\n")
						.append("FROM 		board ta,\n")
						.append("			(SELECT @b_rownum := (SELECT COUNT(*)-?+1 FROM board ta "+where+")) tb\n")
						.append(where+"\n")
						.append("ORDER BY 	b_group desc, b_order asc\n")
						.append("LIMIT 		?, 5\n")
						.toString();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, pageNum);
				pstmt.setString(2, text);
				if(option.equals("content")) {
					pstmt.setString(3, text);
					pstmt.setString(4, text);
					pstmt.setString(5, text);
					pstmt.setInt(6, pageNum);
				}else
					pstmt.setString(3, text);
					pstmt.setInt(4, pageNum);
				rs = pstmt.executeQuery();
				b_list = new ArrayList<Board>();
				
				while(rs.next()) {
					Board board = new Board();
					board.setB_rownum(rs.getInt("b_rownum"));
					board.setB_idx(rs.getInt("b_idx"));
					board.setTitle(rs.getString("title"));
					board.setWriter(rs.getString("writer"));
					board.setContent(rs.getString("content"));
					board.setView(rs.getInt("view"));
					board.setDate(rs.getDate("date"));
					board.setB_depth(rs.getInt("b_depth"));
					b_list.add(board);
				}
			} catch (Exception e) {
				e.printStackTrace();
			
			}finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		} else {
			try {
				conn = DBconnection.getConnection();
				String query = new StringBuilder()
						.append("SELECT		@b_rownum := @b_rownum -1 AS b_rownum,\n")
						.append("			ta.*\n")
						.append("FROM 		board ta,\n")
						.append("			(SELECT @b_rownum := (SELECT COUNT(*)-?+1 FROM board ta)) tb\n")
						.append("ORDER BY 	b_group desc, b_order asc\n")
						.append("LIMIT 		?, 5\n")
						.toString();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, pageNum);
				pstmt.setInt(2, pageNum);
				rs = pstmt.executeQuery();
				b_list = new ArrayList<Board>();
				
				while(rs.next()) {
					Board board = new Board();
					board.setB_rownum(rs.getInt("b_rownum"));
					board.setB_idx(rs.getInt("b_idx"));
					board.setTitle(rs.getString("title"));
					board.setWriter(rs.getString("writer"));
					board.setContent(rs.getString("content"));
					board.setView(rs.getInt("view"));
					board.setDate(rs.getDate("date"));
					board.setB_depth(rs.getInt("b_depth"));
					b_list.add(board);
				}
			} catch (Exception e) {
				e.printStackTrace();
			
			}finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
			}
			
		}
		return b_list;
	}
	
	public int getBoardsCount(Search search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String text = "%"+search.getText()+"%";
		String option = search.getOption();
		
		try {
			String query = null;
			conn = DBconnection.getConnection();
			
			if(option != null && option != "") {
				if(option.equals("title")){
					query ="SELECT COUNT(*) count FROM board WHERE title Like ?\n";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, text);
				}else if (option.equals("content")) {
					query = "SELECT COUNT(*) count FROM board WHERE title or content Like ?\n";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, text);
				}else if (option.equals("writer")){
					query = "SELECT COUNT(*) count FROM board WHERE writer Like ?\n";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, text);
				}
			} else {
				query = "SELECT COUNT(*) count FROM board";
				pstmt = conn.prepareStatement(query);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public Board detailBoard(Board board) {	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		
			conn = DBconnection.getConnection();
			String query = "select * from board where b_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setView(rs.getInt("view"));
				board.setDate(rs.getDate("date"));
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return board;
	}
	
	public void updateBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBconnection.getConnection();
			String query = "UPDATE board SET view = view + 1 where b_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board.setView(rs.getInt("view"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException ex) {
				ex.printStackTrace();			
			}
		}
	}
	
	public void editBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBconnection.getConnection();
			String query = "UPDATE board SET title = ?, content = ?, writer = ? where b_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getWriter());
			pstmt.setInt(4, board.getB_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWriter(rs.getString("writer"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException ex) {
				ex.printStackTrace();			
			}
		}
	}
	
	public void delBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if((Integer)board.getB_order() == 1) {
			try {
				conn = DBconnection.getConnection();
				String query = "DELETE FROM board where b_group = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, board.getB_group());
				rs = pstmt.executeQuery();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			try {
				conn = DBconnection.getConnection();
				String query = "DELETE FROM board where b_group = ? and b_order = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, board.getB_group());
				pstmt.setInt(2, board.getB_order());
				rs = pstmt.executeQuery();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void commentBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(board.getC_group() > 0 && !(board.getC_idx() > 0) ) {
			try {
				conn = DBconnection.getConnection();
				String sql = "insert into comment(writer,content,c_group,c_order,c_depth,b_idx) value(?,?,?,?,?,?)";
				String sql2 = "update comment set c_order = c_order +1 where c_idx <> last_insert_id() and c_order >= ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getWriter());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getC_group());
				pstmt.setInt(4, board.getC_order());
				pstmt.setInt(5, board.getC_depth());
				pstmt.setInt(6, board.getB_idx());
				pstmt.executeUpdate();
				pstmt.close();		
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1,  board.getC_order());
				pstmt.executeUpdate();
				
			} catch (Exception ex) {
				System.out.println("SQLException : " + ex.getMessage());
			} finally {
				try{
					if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} else if (!(board.getC_group()>0) && !(board.getC_idx() > 0)) {	
			try {
				conn = DBconnection.getConnection();
				String sql = "insert into comment(writer,content,c_group,c_order,c_depth,b_idx) value(?,?,?,?,?,?)";
				String sql2 = "update comment set c_group = last_insert_id() where c_idx = last_insert_id() ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getWriter());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getC_group());
				pstmt.setInt(4, board.getC_order());
				pstmt.setInt(5, board.getC_depth());
				pstmt.setInt(6, board.getB_idx());
				pstmt.executeUpdate();
				pstmt.close();		
				pstmt = conn.prepareStatement(sql2);
				pstmt.executeUpdate();
				
			} catch (Exception ex) {
				System.out.println("SQLException : " + ex.getMessage());
			} finally {
				try{
					if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else if(board.getC_idx() > 0)
			try {
				conn = DBconnection.getConnection();
				String query = "UPDATE comment SET writer = ?, content = ? where b_idx = ? and c_idx = ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, board.getWriter());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getB_idx());
				pstmt.setInt(4, board.getC_idx());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					board.setWriter(rs.getString("writer"));
					board.setContent(rs.getString("content"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch(SQLException ex) {
					ex.printStackTrace();			
				}
			}
		}
		
	
	public ArrayList<Board> getComments(Board board){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> c_list = null;
		
		try {
			conn = DBconnection.getConnection();
			String query = "SELECT * FROM comment WHERE b_idx =? ORDER BY c_group DESC, c_order ASC";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			c_list = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board1 = new Board();
				board1.setC_idx(rs.getInt("c_idx"));
				board1.setWriter(rs.getString("writer"));
				board1.setContent(rs.getString("content"));
				board1.setDate(rs.getDate("date"));
				board1.setC_group(rs.getInt("c_group"));
				board1.setC_order(rs.getInt("c_order"));
				board1.setC_depth(rs.getInt("c_depth"));
				board1.setB_idx(rs.getInt("b_idx"));
				c_list.add(board1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		}
		
		return c_list;
	}

	public void delComment(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = DBconnection.getConnection();
			String query = "DELETE FROM comment where c_idx = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getC_idx());
			rs = pstmt.executeQuery();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}		
	}
	
	public ArrayList<Board> getSearch(String OptionSelect, String SearchText){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> s_list = null;
		String option = OptionSelect;
		String text = "%"+SearchText+"%";
		String query = null;		
		
		try {
			conn = DBconnection.getConnection();
			if(option.equals("title")) {
				query = "SELECT * FROM board WHERE title LIKE ? ORDER BY b_group desc, b_order asc ";
			} else if (option.equals("titlecontent")) {
				query = "SELECT * FROM board WHERE title OR content LIKE ? ORDER BY b_group desc, b_order asc ";				
			} else {
				query = "SELECT * FROM board WHERE writer LIKE ? ORDER BY b_group desc, b_order asc ";	
			}
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, text);		
			rs = pstmt.executeQuery();
			s_list = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setView(rs.getInt("view"));
				board.setDate(rs.getDate("date"));
				board.setB_depth(rs.getInt("b_depth"));
				s_list.add(board);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return s_list;
		
	}

	
}
