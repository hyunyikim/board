package com.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDao;

@WebServlet("/IncreaseHitServlet")
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String ContextPath = request.getContextPath();
		String url_command = RequestURI.substring(ContextPath.length());
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if (url_command.equals("/increaseHit.ajax")) {
			try {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				int b_num = Integer.parseInt(request.getParameter("b_num"));
				
				BoardDao dao = new BoardDao();
				int result = dao.increaseHit(b_num);
				out.print(result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/search.ajax")) {
			try {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				int b_num = Integer.parseInt(request.getParameter("b_num"));
				
				BoardDao dao = new BoardDao();
				int result = dao.increaseHit(b_num);
				out.print(result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		
	}

}
