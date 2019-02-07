package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

public class DeleteService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			int b_num = Integer.parseInt(request.getParameter("b_num"));
			BoardDao dao = new BoardDao();
			int result = dao.boardDelete(b_num);
			
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			
			if(result > 0) {  
				String path = request.getContextPath() + "/index.do?curPage="+curPage;
				forward.setRedirect(true);
				forward.setPath(path);
			} else { 
				String path = request.getContextPath() + "/boardDetail.do?b_num=" + request.getParameter("b_num");
				forward.setRedirect(true);
				forward.setPath(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
	
}
