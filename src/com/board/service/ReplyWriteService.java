package com.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

public class ReplyWriteService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			BoardDao dao = new BoardDao();
			
			int origin_num = Integer.parseInt(request.getParameter("b_num"));	// 답글이 달릴 글(원글)의 글번호 
			BoardDto origin_dto = dao.boardDetail(origin_num);					// 원글 정보
			int minSeq = dao.selectMinSeq(origin_dto);							// 최소 시퀀스
			
			System.out.println("minSeq : " + minSeq);
			
			if(minSeq == 0) {	//	답글이 가장 마지막에 달릴 경우 
				int maxSeq = dao.selectMaxSeq(origin_dto);
				BoardDto dto = new BoardDto();
				dto.setB_title((String)request.getParameter("b_title"));
				dto.setB_writer((String)request.getParameter("b_writer"));
				dto.setB_content((String)request.getParameter("b_content"));
				dto.setB_grp(origin_dto.getB_grp());
				dto.setB_dept(origin_dto.getB_dept() + 1);	// 뎁스 1 증가 
				dto.setB_seq(maxSeq);	// 글그룹 중 가장 마지막 순서로 배치 
				dao.replyWrite(dto);
				
			} else {			// 답글이 글 사이에 달릴 경우 
				int b_grp = origin_dto.getB_grp();
				System.out.println("updateseq b_grp:"+b_grp +", b_seq:"+minSeq);
				BoardDto dto = new BoardDto();
				
				dto.setB_grp(origin_dto.getB_grp());
				dto.setB_seq(minSeq);
				dao.updateSeq(dto);		// 최소 시퀀스 이후 글들의 시퀀스 조정
				
				
				dto.setB_title((String)request.getParameter("b_title"));
				dto.setB_writer((String)request.getParameter("b_writer"));
				dto.setB_content((String)request.getParameter("b_content"));
				dto.setB_grp(origin_dto.getB_grp());
				dto.setB_dept(origin_dto.getB_dept() + 1);	// 뎁스 1 증가 
				dto.setB_seq(minSeq);	// 최소 시퀀스 
				dao.replyWrite(dto);
			}
			
			int currval = dao.boardSequence();	//	현재 글번호 조회
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			String path = request.getContextPath() + "/boardDetail.do?b_num=" + currval + "&curPage=" + curPage;
			forward.setRedirect(true);
			forward.setPath(path);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}
}
