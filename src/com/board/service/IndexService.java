package com.board.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;

public class IndexService implements Action {
	
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			// 한글 인코딩 설정 
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			HashMap<String, Object> opt = new HashMap<String, Object>();
			
			BoardDao dao = new BoardDao();
			String subject = (String) request.getParameter("subject");
			if(subject == null || subject.equals("") || subject.equals("null")) {
				opt.put("subject", "all");
			} else {
				
			}
			
			int listCount = dao.getListCount("","");
			// System.out.println("listCount : " + listCount);
			
			request.setAttribute("listCount", listCount);
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/index.jsp");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		try {
			// 한글 인코딩 설정 
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			// dao 접속
			BoardDao dao = new BoardDao();
			
			
			int listCount = dao.getListCount();
			// 화면에서 curPage 받아옴, 우선은 1로 지정 
			// 만약 curPage 가 없다면 1로 지정 
			String strCurPage= (String)request.getParameter("curPage");
			if(strCurPage == null || strCurPage.equals("null") || strCurPage.equals("")) {
				strCurPage = "1";
			} 
			int curPage = Integer.parseInt(strCurPage);
			PagingDto pagingDto = new PagingDto(curPage, listCount);
			
			int startIndex = pagingDto.getStartIndex();
			int endIndex = pagingDto.getEndIndex();
			
			ArrayList<BoardDto> boardList = dao.getBoardList(startIndex, endIndex); 
			request.setAttribute("boardList", boardList);
			request.setAttribute("pagingDto", pagingDto);
			
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		return forward;
	}

}
