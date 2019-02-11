package com.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.Action;
import com.board.action.ActionForward;

public class DownloadService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			// ① 파일명 가져오기
			String fileName = request.getParameter("b_file");

			// ② 경로 가져오기
			String saveDir = request.getServletContext().getRealPath("/upload");
			File file = new File(saveDir + "/" + fileName);
			
			// 파일이 존재하는지 확인
			if (file.exists() && file.isFile()) {
				// ③ MIMETYPE 설정하기
				String mimeType = request.getServletContext().getMimeType(file.toString());
				if (mimeType == null) {
					response.setContentType("application/octet-stream");
				}

				// ④ 다운로드용 파일명을 설정
				if (request.getHeader("user-agent").indexOf("MSIE") == -1) {
					fileName = new String(fileName.getBytes("UTF-8"), "8859_1");
				} else {
					fileName = new String(fileName.getBytes("EUC-KR"), "8859_1");
				}

				// ⑤ 무조건 다운로드하도록 설정
				response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");

				// ⑥ 요청된 파일을 읽어서 클라이언트쪽으로 저장한다.
				inputStream = new FileInputStream(file);
				outputStream = response.getOutputStream();
				byte b[] = new byte[1024];
				int data = 0;
				while ((data = (inputStream.read(b, 0, b.length))) != -1) {
					outputStream.write(b, 0, data);
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}
			
			outputStream.flush();
			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
