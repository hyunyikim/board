<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>게시판</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
body {
	font-size: 16px;
}

#th_num {
	width: 150px;
}

#th_title {
	width: 200px;
}

#th_writer, #th_date, #th_hit {
	width: 100px;
}

tr element {
	text-align: center;
}

#div_btn {
	text-align: right;
}

#div_pagenation {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<h2>게시판</h2>
		<table class="table table-hover">
			<thead>
				<tr>
					<th id="th_num">글번호</th>
					<th id="th_title">글제목</th>
					<th id="th_writer">작성자</th>
					<th id="th_date">작성일</th>
					<th id="th_hit">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${boardList}">
					<tr>
						<td>${dto.b_num}</td>
						<td>
							<c:if test="${dto.b_dept ne 0}">
								<c:forEach var="i" begin="1" end="${dto.b_dept}" step="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach>
								<%-- <img src="<%= request.getContextPath() %>/img/icon_reply.png"/> --%>
								ㄴ
							</c:if>
							<a href="boardDetail.do?curPage=${pagingDto.curPage}&b_num=${dto.b_num}" class="a_boardDetail" id="${dto.b_num }">${dto.b_title}</a>
						</td>
						<td>${dto.b_writer}</td>
						<td>${dto.b_date}</td>
						<td>${dto.b_hit}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="div_btn">
			<button id="btn_writeBoard" class="btn">새글 작성</button>
		</div>
		<div id="div_pagenation">
			<c:if test="${pagingDto.curRange ne 1}">
				<a href="#" onClick="fn_paging(1)">[처음]</a>
			</c:if>
			<c:if test="${pagingDto.curPage ne 1}">
				<a href="#" onClick="fn_paging('${pagingDto.prevPage}')">[이전]</a>
			</c:if>
			<c:forEach var="pageNum" begin="${pagingDto.startPage}" end="${pagingDto.endPage }">
				<c:choose>
					<c:when test="${pageNum eq pagingDto.curPage }">
						<b><U><a href="#" onClick="fn_paging('${pageNum}')">${pageNum}</a></U></b>
					</c:when>
					<c:otherwise>
						<a href="#" onClick="fn_paging('${pageNum}')">${pageNum}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${pagingDto.curPage ne pagingDto.pageCount}">
				<a href="#" onClick="fn_paging('${pagingDto.nextPage}')">[다음]</a>
			</c:if>
			<c:if test="${pagingDto.curRange ne pagingDto.rangeCount && pagingDto.rangeCount > 0}">
				<a href="#" onClick="fn_paging('${pagingDto.pageCount}')">[끝]</a>
			</c:if>
		</div>
	</div>
	<script>
		$('.a_boardDetail').click(function(){ // 조회수 증가 
			var b_num = $(this).attr("id");
				$.ajax({
					url:"<%=request.getContextPath()%>/increaseHit.ajax",
					data:{
						"b_num":b_num 
					},
					success:function(data){
						console.log("data" + data);
					},
					error:function(error){
						console.log("조회수 증가 : 에러 발생");
					}
				});
		});
		
		$('#btn_writeBoard').click(function() {
			location.href = "boardWriteView.do";
		});
		
		// 페이징 함수 
		function fn_paging(curPage){
			location.href = "${pageContext.request.contextPath}/index.do?curPage="+curPage;
		}
	</script>
</body>
</html>
