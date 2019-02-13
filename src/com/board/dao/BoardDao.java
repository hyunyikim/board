package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.board.dto.BoardDto;
import com.board.dto.PagingDto;

public class BoardDao {

	DataSource datasource = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	
	public BoardDao() throws NamingException {
		Context context = new InitialContext(); 
		datasource = (DataSource) context.lookup("java:comp/env/PRJA");
	}

	public ArrayList<BoardDto> getBoardList(PagingDto pagingDto, String subject, String keyword) throws SQLException {
		conn = datasource.getConnection();
		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		
		int startIndex = pagingDto.getStartIndex();
		int endIndex = pagingDto.getEndIndex();
		
		String sql = "";
		
		try {
			if(subject.equals("all")) {
				sql = "select * from (select rownum as rnum, A.* from "
						+ "(select * from board order by b_grp desc, b_seq asc) A where rownum <=?) "
						+ "where rnum > ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, endIndex);
				pstmt.setInt(2, startIndex);
			} else {
				sql = "select * from (select rownum as rnum, A.* from "
						+ "(select * from board where " + subject + " like ? order by b_grp desc, b_seq asc) A where rownum <=?) "
						+ "where rnum > ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, endIndex);
				pstmt.setInt(3, startIndex);
				
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_file(rs.getString("b_file"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_date(rs.getString("b_date")); 
				dto.setB_hit(rs.getInt("b_hit"));
				dto.setB_grp(rs.getInt("b_grp")); 
				dto.setB_dept(rs.getInt("b_dept"));
				dto.setB_seq(rs.getInt("b_seq"));
				boardList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();	
			pstmt.close();
			conn.close();
		}
		return boardList;
	}
	
	public int getListCount(String subject, String keyword) throws SQLException {
		conn = datasource.getConnection();
		int listCount = 0;
		String sql = "";
		try {
			if(subject.equals("all")) {		// 전체검색 
				sql = "select count(*) as listCount from board";
				pstmt = conn.prepareStatement(sql);
			} else {						// 조건검색 
				sql = "select count(*) as listCount from board where " + subject + " like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
			} 
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				listCount = rs.getInt("listCount");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();	
			pstmt.close();
			conn.close();
		}
		return listCount;
	}
	
	public int increaseHit(int b_num) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "update board set b_hit = b_hit+1 where b_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public BoardDto boardDetail(int b_num) throws SQLException {
		conn = datasource.getConnection();
		BoardDto dto = new BoardDto();
		try {
			String sql = "select b_num, b_title, b_content, b_file, b_writer, b_date, b_hit, b_grp, b_dept, b_seq "
					+ "from board where b_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_title(rs.getString("b_title"));
				dto.setB_content(rs.getString("b_content"));
				dto.setB_file(rs.getString("b_file"));
				dto.setB_writer(rs.getString("b_writer"));
				dto.setB_date(rs.getString("b_date")); 
				dto.setB_hit(rs.getInt("b_hit"));
				dto.setB_grp(rs.getInt("b_grp")); 
				dto.setB_dept(rs.getInt("b_dept"));
				dto.setB_seq(rs.getInt("b_seq"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return dto;
	}
	
	public int boardWrite(BoardDto dto) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "insert into board (b_num, b_title, b_content, b_writer, b_file, b_date, b_hit, b_grp, b_dept, b_seq) "
					+ "values (seq_bnum.nextval, ?, ?, ?, ?, sysdate, 0, seq_bnum.currval, 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_title());
			pstmt.setString(2, dto.getB_content());
			pstmt.setString(3, dto.getB_writer());
			pstmt.setString(4, dto.getB_file());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public int selectMinSeq(BoardDto origin_dto) throws SQLException {
		conn = datasource.getConnection();
		int minSeq = 0;
		try {
			String sql = "select nvl(min(b_seq), 0) as min_seq from board where b_grp = ? and b_dept <= ? and b_seq > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, origin_dto.getB_grp());
			pstmt.setInt(2, origin_dto.getB_dept());
			pstmt.setInt(3, origin_dto.getB_seq());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				minSeq = rs.getInt("min_seq");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return minSeq;
	}
	
	public int selectMaxSeq(BoardDto origin_dto) throws SQLException {
		conn = datasource.getConnection();
		int max_seq = 0;
		try {
			String sql = "select nvl(max(b_seq), 0) + 1 as max_seq from board where b_grp = ?";	
			//	origin 글번호 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, origin_dto.getB_grp());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				max_seq = rs.getInt("max_seq");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return max_seq;
	}
	
	public int updateSeq(int b_grp, int b_seq) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		
		try {
			String sql = "update board set b_seq = b_seq + 1 where b_grp = ? and b_seq >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_grp);
			pstmt.setInt(2, b_seq);
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public int replyWrite(BoardDto dto) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "insert into board (b_num, b_title, b_content, b_writer, b_date, b_hit, b_grp, b_dept, b_seq) "
					+ "values (seq_bnum.nextval, ?, ?, ?, sysdate, 0, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_title());
			pstmt.setString(2, dto.getB_content());
			pstmt.setString(3, dto.getB_writer());
			pstmt.setInt(4, dto.getB_grp());
			pstmt.setInt(5, dto.getB_dept());
			pstmt.setInt(6, dto.getB_seq());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public int boardSequence() throws SQLException {
		conn = datasource.getConnection();
		int currval = 0;
		try {
			String sql = "select seq_bnum.currval as b_num from dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				currval = rs.getInt("b_num");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return currval;
	}
	
	public int boardUpdate(BoardDto dto) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "update board set b_title = ?, b_content = ?, b_writer = ?, b_date = sysdate where b_num = ?";
			conn = datasource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getB_title());
			pstmt.setString(2, dto.getB_content());
			pstmt.setString(3, dto.getB_writer());
			pstmt.setInt(4, dto.getB_num());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public int boardDelete(int b_num) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "delete from board where b_num = ?";
			conn = datasource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}	
	
	
	public int signUp(String id, String password) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "insert into member (id, password) values(?, ?)";
			conn = datasource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
		return result;
	}	
	
	public int checkId(String id) throws SQLException {
		conn = datasource.getConnection();
		int result = 0;
		try {
			String sql = "select * from member where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();	
			pstmt.close();
			conn.close();
		}
		return result;
	}
	
	public String signIn(String id) throws SQLException {
		conn = datasource.getConnection();
		String password = "";
		try {
			String sql = "select password from member where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				password = rs.getString("password");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return password;
	}	

}
