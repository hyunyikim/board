package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

public class UpdateViewService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			int b_num = Integer.parseInt(request.getParameter("b_num"));
			BoardDao dao = new BoardDao();
			BoardDto dto = dao.boardDetail(b_num);
			request.setAttribute("dto", dto);
			
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/boardUpdate.jsp");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
 
}
