package com.board.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;
import com.board.dto.PagingDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ListService implements Action {
	
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			// 한글 인코딩 설정 
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			BoardDao dao = new BoardDao();
			PrintWriter out = response.getWriter();
			
			String strCurPage= (String)request.getParameter("curPage");
			String subject = (String) request.getParameter("subject");
			String keyword = (String) request.getParameter("keyword");
			
			
			// 처음 서버 구동 후 리스트 불러올 때
			if(subject == null || subject.equals("null") || subject.equals("")) {
				subject = "all";
			} 
			
			int listCount = dao.getListCount(subject, keyword);
			
			// 페이징 처리 
			if(strCurPage == null || strCurPage.equals("null") || strCurPage.equals("")) {
				strCurPage = "1";
			}
			int curPage = Integer.parseInt(strCurPage);
			PagingDto pagingDto = new PagingDto(curPage, listCount);
						
			ArrayList<BoardDto> boardList = dao.getBoardList(pagingDto, subject, keyword);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("boardList", boardList);
			map.put("pagingDto", pagingDto);
			
			JSONObject jsonObj = JSONObject.fromObject(map);
			out.print(jsonObj);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
