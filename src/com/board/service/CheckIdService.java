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

public class CheckIdService implements Action {
	
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			// 한글 인코딩 설정 
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			BoardDao dao = new BoardDao();
			PrintWriter out = response.getWriter();
			
			String id= (String)request.getParameter("id");
			int result = dao.checkId(id);
			
			out.print(result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
