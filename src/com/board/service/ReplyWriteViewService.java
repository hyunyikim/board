package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

public class ReplyWriteViewService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			int b_num = Integer.parseInt(request.getParameter("b_num"));
			
			request.setAttribute("curPage", curPage);
			request.setAttribute("b_num", b_num);
			
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/replyWrite.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
}
