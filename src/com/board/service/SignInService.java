package com.board.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;

public class SignInService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			
			BoardDao dao = new BoardDao();
			
			String id = (String) request.getParameter("id");
			String password = (String) request.getParameter("password");
			
			String result = "";
			String resultPassword = dao.signIn(id);
			if(resultPassword.equals(password)) {
				result = "같다";
				session.setAttribute("id", id);
			} else if (resultPassword.equals("") || resultPassword.equals(null) || resultPassword == null ) {
				result = "다르다";
			} else {
				result = "다르다";
			}
			out.print(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
