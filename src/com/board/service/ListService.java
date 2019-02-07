package com.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;
import com.board.dto.PagingDto;

public class ListService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
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
		return forward;
	}

}
