package com.lcomputerstudy.testmvc.controller;

import java.time.LocalDate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lcomputerstudy.testmvc.service.UserService;
import com.lcomputerstudy.testmvc.service.BoardService;
import com.lcomputerstudy.testmvc.vo.User;
import com.lcomputerstudy.testmvc.vo.Util;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.Search;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.BoardFile;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("*.do")
public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private static final String CHARSET = "utf-8";
    private static final String ATTACHES_DIR = "C:\\Users\\l5\\Documents\\work11\\lcomputerstudy\\WebContent\\img";
    private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
    
    
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html; charset=utf-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		
		command = checkSession(request, response, command);
		HttpSession session = null;
		String idx = null;
		String pw = null;
		int count = 0;
		int boardcount = 0;
		int page = 1;
		int uIdx = 0;
		int bIdx = 0;
		int cIdx = 0;
		int Bgroup = 0;
		int Border = 0;
		int Cgroup = 0;
		int Corder = 0;
		int Cdepth = 0;
		int order = 0;
		int depth = 0;
		int group = 0;
		String strGroup = null;
		String strOrder = null;
		String strDepth = null;
		String strCidx = null;
		String option = null;
		String text = null;
		String reqPage = null;
		User user = null;
		User user2 = null;
		boolean isBoolean; 
		Pagination pagination = null;
		Search search = null;
		

		Board board = null;
		BoardService boardservice = null;
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
	
		
		switch (command) {
			case "/user-list.do":
				reqPage = request.getParameter("page");
				if(reqPage !=null) {
					page = Integer.parseInt(reqPage);
				}
				UserService userService = UserService.getInstance();
				count = userService.getUsersCount();
				pagination = new Pagination();
				pagination.setCount(count);
				pagination.setPage(page);
				pagination.init();
				ArrayList<User> list = userService.getUsers(pagination);
			
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
			
				view = "user/list";
				break;
			
			case "/user-insert.do":
				view="user/insert";
				break;
			
			case "/user-insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1")+"-"+request.getParameter("tel2")+"-"+request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
			
				userService = UserService.getInstance();
				userService.insertUser(user);
			
				view = "user/insert-result";
				break;
				
			case "/user-login.do":
				view = "user/login";
				break;
			
			case "/user-login-process.do":
				idx = request.getParameter("login_id");
				pw = request.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx,pw);
				
				if(user != null) {
					session = request.getSession();
					session.setAttribute("user", user);
					
					view ="user/login-result";
				} else {
					view = "user/login-fail";
				}
				break;
				
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view = "user/login";
				break;
			
			case "/access-denied.do":
				view = "user/access-denied";
				break;
			
			case "/edit-auth.do":
				uIdx = Integer.parseInt(request.getParameter("u_idx"));
				isBoolean = Boolean.parseBoolean(request.getParameter("boolean"));
				
				userService = UserService.getInstance();
				userService.editAuth(isBoolean, uIdx);
				count = userService.getUsersCount();
				Pagination pagination2 = new Pagination();
				pagination2.setCount(count);
				pagination2.setPage(page);
				pagination2.init();
				ArrayList<User> list2 = userService.getUsers(pagination2);
			
				request.setAttribute("list", list2);
				request.setAttribute("pagination", pagination2);
				
				view = "user/list";
				break;
				
		//Board
			case "/post-regist.do":
				view = "board/NewPostRegist";
				break;
			
			case "/post-regist-process.do":
				Util boardutil = new Util();
				
				strGroup = request.getParameter("b_group");
				strOrder = request.getParameter("b_order");
				strDepth = request.getParameter("b_depth");

				if (strGroup != null && !strGroup.equals("")) {
					group = Integer.parseInt(strGroup);
				} 
				
				if (strOrder != null && !strOrder.equals("")) {
					order = Integer.parseInt(strOrder) + 1;
				} else {
					order = 1;
				}
				
				if (strDepth != null && !strDepth.equals("")) {
					depth = Integer.parseInt(strDepth) + 1;
				} else {
					depth = 0;
				}
				
				board = new Board();
				board.setB_group(group);
				board.setB_order(order);
				board.setB_depth(depth);	

				File attachesDir = new File("ATTACHES_DIR");
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(attachesDir);
				fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				List<BoardFile> bf_list = new ArrayList<BoardFile>();
				boardservice = BoardService.getInstance();
				try {					
					List<FileItem> items = fileUpload.parseRequest(request);
					for(FileItem item : items) {
					     if (item.isFormField()) {
			                    System.out.printf("파라미터 명 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
			                    switch(item.getFieldName()) {

			                    	
			                    	case "title":
			                    		String Btitle = item.getString(CHARSET);
			            				board.setTitle(Btitle);
			            				
			                    	case "writer":
			                    		String Bwriter = item.getString(CHARSET);
			            				board.setWriter(Bwriter);
			            			
			                    	case "content":
			                    		String Bcontent = item.getString(CHARSET);
			            				board.setContent(Bcontent);	
			            						               
			                    }
			                } else {
			                    System.out.printf("파라미터 명 : %s, 파일 명 : %s,  파일 크기 : %s bytes \n", item.getFieldName(),
			                            item.getName(), item.getSize());
								if(item.getSize()>0) {
									String separator = File.separator;
									int index = item.getName().lastIndexOf(separator);
									String fileName = item.getName().substring(index+1);
									File uploadFile = new File(ATTACHES_DIR + separator + fileName);
									item.write(uploadFile);
									
									BoardFile files = new BoardFile();
									
									files.setF_Oname(item.getName());
									files.setF_Cname(boardutil.RandomName() + item.getName().substring(fileName.lastIndexOf(".")));
									files.setF_size(item.getSize());									
									bf_list.add(files);
								}
			                }	
					}					
					System.out.println("<h1>등록 완료</h1>");
				} catch(Exception e){
					e.printStackTrace();
					System.out.println("<h1>등록 중 오류 발생</h1>");					
				}
				
				boardservice = BoardService.getInstance();
				bIdx = boardservice.registBoard(board);
				board.setB_idx(bIdx);
				
				for(BoardFile file : bf_list) {
					boardservice.uploadFile(board, file);
				}
				
				
				
				view = "board/regist-result";
				break;
				
			case"/fileDownload.do":
				
				
			case"/post-list.do":
				reqPage = request.getParameter("page");
				if(reqPage !=null) {
					page = Integer.parseInt(reqPage);
				}
				if(option == null || option.equals("")) {
					option = request.getParameter("OptionSelect");
					text = request.getParameter("SearchText");
				}
				
				search = new Search();
				search.setSearch(option, text);	
				
				boardservice = BoardService.getInstance();
				count = boardservice.getBoardsCount(search);
				
				pagination = new Pagination(5,5);
				pagination.setCount(count);
				pagination.setPage(page);
				pagination.init();
				
				ArrayList<Board> b_list = boardservice.getBoards(pagination, search);
				
				view = "board/postList";
				request.setAttribute("b_list", b_list);
				request.setAttribute("pagination",pagination);
				request.setAttribute("search", search);
				break;
				
			case"/post-detail.do":
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				board = new Board();
				board.setB_idx(bIdx);
							
				boardservice = BoardService.getInstance();
				Board detailboard = boardservice.detailBoard(board);
				boardservice.updateBoard(board);
				ArrayList<Board> c_list = boardservice.getComments(board);
				bf_list = boardservice.getUploadFile(board);
				
				view = "board/postDetail";
				request.setAttribute("c_list", c_list);
				request.setAttribute("detailboard", detailboard);
				request.setAttribute("bf_list", bf_list);
				break;
				
			case"/post-edit.do":
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				boardservice = BoardService.getInstance();
				userService = UserService.getInstance();
				user2 = userService.getOwner(bIdx);
				
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				if (user.getU_idx() != user2.getU_idx() && user.getU_auth() == false)
					break;				
				
				board = new Board();
				board.setB_idx(bIdx);
				board = boardservice.detailBoard(board);
				request.setAttribute("board", board);
				view = "board/postEdit";
				break;
				
			case"/post-edit-process.do":
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				board = new Board();
				board.setTitle(request.getParameter("title"));
				board.setWriter(request.getParameter("writer"));
				board.setContent(request.getParameter("content"));
				board.setB_idx(bIdx);
				
				boardservice = BoardService.getInstance();
				boardservice.editBoard(board);
				
				view = "board/edit-result";
				break;
			
			case"/post-delete.do":
				
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				userService = UserService.getInstance();
				boardservice = BoardService.getInstance();
				user2 = userService.getOwner(bIdx);
				
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				if (user.getU_idx() != user2.getU_idx() && user.getU_auth() == false)
					break;
				
				Bgroup = Integer.parseInt(request.getParameter("b_group"));
				Border = Integer.parseInt(request.getParameter("b_order"));
				
				board = new Board();
				board.setB_group(Bgroup);
				board.setB_order(Border);
				
				boardservice.delBoard(board);
				
				view = "board/delete-result";
				break;
				
			case"/post-reply.do":
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				board = new Board();
				board.setB_idx(bIdx);
				boardservice = BoardService.getInstance();
				boardservice.detailBoard(board);
				request.setAttribute("board", board);
				
				view = "board/postReply";
				break;
				
			case"/post-comment.do":
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				strGroup = request.getParameter("c_group");
				strOrder = request.getParameter("c_order");
				strDepth = request.getParameter("c_depth");
				strCidx = request.getParameter("c_idx");
				
				if (strGroup != null && !strGroup.equals("")) {
					group = Integer.parseInt(strGroup);
				} 
				
				if (strOrder != null && !strOrder.equals("")) {
					order = Integer.parseInt(strOrder) + 1;
				} else {
					order = 1;
				}
				
				if (strDepth != null && !strDepth.equals("")) {
					depth = Integer.parseInt(strDepth) + 1;
				} else {
					depth = 0;
				}
				
				if(strCidx != null && !strCidx.equals("")) {
					cIdx = Integer.parseInt(request.getParameter("c_idx"));
				}
				
				board = new Board();
				board.setWriter(request.getParameter("writer"));
				board.setContent(request.getParameter("content"));
				board.setC_group(group);
				board.setC_order(order);
				board.setC_depth(depth);
				board.setB_idx(bIdx);
				board.setC_idx(cIdx);
				
				
				boardservice = BoardService.getInstance();
				boardservice.commentBoard(board);
				
				ArrayList<Board> c_list1 = boardservice.getComments(board);
				
				request.setAttribute("c_list", c_list1);
				view = "board/updateComment";
				break;
				
			case"/post-delete-comment.do":
				cIdx = Integer.parseInt(request.getParameter("c_idx"));
				bIdx = Integer.parseInt(request.getParameter("b_idx"));
				
				board = new Board();
				board.setC_idx(cIdx);
				board.setB_idx(bIdx);
				
				boardservice = BoardService.getInstance();
				boardservice.delComment(board);
				
				ArrayList<Board> c_list2 = boardservice.getComments(board);
				
				request.setAttribute("c_list", c_list2);
				view = "board/updateComment";
				break;
				
				
				
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
	}
	
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		
		
		String[] authList = {
				"/user-list.do"
				,"/user-insert.do"
				,"/user-insert-process.do"
				,"/user-detail.do"
				,"/user-edit.do"
				,"/user-edit-process.do"
				,"/logout.do"
				
				,"/post-regist.do"
				,"/post-regist-process.do"
				,"/post-reply.do"
				,"/post-comment.do"
				,"/post-edit.do"
				,"/post-edit-process.do"
				,"/post-delete.do"
				,"/post-delete-comment.do"
		};
		
		User user = (User)session.getAttribute("user");
		for(String item : authList) {
			if(item.equals(command)) {
				if(user==null) {
					command = "/access-denied.do";
					return command;
				}
			}
		}
		
		return command;
	}

}

