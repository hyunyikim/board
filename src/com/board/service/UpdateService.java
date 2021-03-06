package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

public class UpdateService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			BoardDto dto = new BoardDto();
			dto.setB_num(Integer.parseInt(request.getParameter("b_num")));
			dto.setB_title((String)request.getParameter("b_title"));
			dto.setB_writer((String)request.getParameter("b_writer"));
			dto.setB_content((String)request.getParameter("b_content"));
			
			BoardDao dao = new BoardDao();
			int result = dao.boardUpdate(dto);
			
			if(result > 0) {  
				String path = request.getContextPath() + "/boardDetail.do?b_num=" + request.getParameter("b_num");
				forward.setRedirect(true);
				forward.setPath(path);
			} else { 
				String path = request.getContextPath() + "/boardUpdateView.do?b_num=" + request.getParameter("b_num");
				forward.setRedirect(true);
				forward.setPath(path);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

}
