package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;

public class SignUpService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			BoardDao dao = new BoardDao();
			
			String id = (String) request.getParameter("id");
			String password = (String) request.getParameter("password");
			
			int result = dao.signUp(id, password);
			request.setAttribute("result", result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
