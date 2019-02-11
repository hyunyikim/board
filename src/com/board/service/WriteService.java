package com.board.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class WriteService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			// 첨부 파일 처리
			String saveDir = request.getSession().getServletContext().getRealPath("/upload");		
			// reqeust.getRealPath("")는 deprecated됨 
			// System.out.println("saveDir : " + saveDir);
			int maxSize = 1024 * 1024 * 100;
			String encType = "UTF-8";
			
			MultipartRequest multipartRequest 
				= new MultipartRequest(request, saveDir, maxSize, encType, new DefaultFileRenamePolicy());
			
			BoardDto dto = new BoardDto();
			dto.setB_title((String)multipartRequest.getParameter("b_title"));
			dto.setB_writer((String)multipartRequest.getParameter("b_writer"));
			dto.setB_content((String)multipartRequest.getParameter("b_content"));
			dto.setB_file((String)multipartRequest.getFilesystemName("b_file"));
			
			BoardDao dao = new BoardDao();
			int result = dao.boardWrite(dto);
			int currval = dao.boardSequence();	   //	현재 글번호 조회 
			
			if(result > 0) {
				String path = request.getContextPath() + "/boardDetail.do?b_num=" + currval;
				forward.setRedirect(true);
				forward.setPath(path);
			} else {
				String path = request.getContextPath() + "/boardWriteView.do";
				forward.setRedirect(true);
				forward.setPath(path);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
}
