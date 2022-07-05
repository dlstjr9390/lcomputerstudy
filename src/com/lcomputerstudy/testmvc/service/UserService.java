package com.lcomputerstudy.testmvc.service;

import java.util.ArrayList;
import com.lcomputerstudy.testmvc.dao.UserDAO;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.User;

public class UserService {
	
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		
		return service;
	}
	
	public ArrayList<User> getUsers(){
		return dao.getUsers();
	}
	
	public void insertUser(User user) {
		dao.insertUser(user);
	}
	
	public int getUsersCount() {
		return dao.getUsersCount();
	}
	
	public ArrayList<User> getUsers(Pagination pagination){
		return dao.getUsers(pagination);
	}
	
	public User loginUser(String idx, String pw) {
		return dao.loginUser(idx,pw);
	}
	
	
	public void editAuth(Boolean isBoolean, int uIdx) {
		dao.editAuth(isBoolean, uIdx);
	}
	
	
	public User getOwner(int bIdx) {
		return dao.getOwner(bIdx);
	}
}
