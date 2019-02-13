package com.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;
import com.board.service.CheckIdService;
import com.board.service.DeleteService;
import com.board.service.DetailService;
import com.board.service.DownloadService;
import com.board.service.ListService;
import com.board.service.ReplyWriteService;
import com.board.service.ReplyWriteViewService;
import com.board.service.SignInService;
import com.board.service.SignUpService;
import com.board.service.UpdateService;
import com.board.service.UpdateViewService;
import com.board.service.WriteService;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String ContextPath = request.getContextPath();
		String url_command = RequestURI.substring(ContextPath.length());
		
		Action action = null;
		ActionForward forward = null;
		
		if (url_command.equals("/") || url_command.equals("/index.do")) {	//	게시글 목록
			try {
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("WEB-INF/views/index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/list.ajax")) {	//	게시글 목록
			try {
				action = new ListService();
				forward = action.execute(request, response);
			} catch (Exception e) {	
				e.printStackTrace();
			}
		} else if (url_command.equals("/boardWriteView.do")) {	//	글쓰기 화면
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("WEB-INF/views/boardWrite.jsp");
		} else if (url_command.equals("/boardWrite.do")) {		//	글쓰기 등록 
			try {
				action = new WriteService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/boardDetail.do")) {		// 	글 상세보기 	(댓글 목록 처리하기, 댓글 null일때)	
			try {
				action = new DetailService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/fileDownload.do")) {		// 	첨부파일 다운로드	
			try {
				action = new DownloadService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/replyWriteView.do")) {	//	답글 화면
			try {
				action = new ReplyWriteViewService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/replyWrite.do")) {		//	답글 등록 
			try {
				action = new ReplyWriteService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/boardUpdateView.do")) {	//	글 수정 화면
			try {
				action = new UpdateViewService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/boardUpdate.do")) {		//	글 수정 
			try {
				action = new UpdateService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/boardDelete.do")) {		//	글 삭제 
			try {
				action = new DeleteService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/signUpView.do")) {	//	회원가입 화면
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("WEB-INF/views/signUp.jsp");
		} else if (url_command.equals("/checkId.ajax")) {	// 아이디 중복확인 
			try {
				action = new CheckIdService();
				forward = action.execute(request, response);
			} catch (Exception e) {	
				e.printStackTrace();
			}
		} else if (url_command.equals("/signInView.do")) {	//	로그인 화면
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("WEB-INF/views/signIn.jsp");
		} else if (url_command.equals("/signUp.do")) {	//	회원가입
			try {
				action = new SignUpService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/signIn.ajax")) {	//	로그인
			try {
				action = new SignInService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		

		// 페이지 이동 방식 설정
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
}
