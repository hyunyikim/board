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
	margin: 15px 0px;
}

#div_pagenation {
	text-align: center;
}
#search_subject {
	width: 10%;
    display: inline;
}
#search_keyword {
	width: 30%;
    display: inline;
}
#btn_search {
    width: 100px;
	margin-right: 20%;
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
			<tbody id="tbody"></tbody>
		</table>
		<div id="div_btn">
			<select class="form-control" id="search_subject" name="search_subject">
			  <option value="all">전체</option>
			  <option value="b_title">제목</option>
			  <option value="b_writer">글쓴이</option>
			  <option value="b_content">글내용</option>
			</select>
			<input type="text" class="form-control" id="search_keyword" name="search_keyword">
			<button id="btn_search" class="btn">검색</button>
			<button id="btn_writeBoard" class="btn">새글 작성</button>
		</div>
		
		<div id="div_pagenation"></div>
	</div> 
	<input type="hidden" value="${pagingDto.curPage}" id="curPage">

	<script>
		var pageData = {
				"curPage":1,
				"subject":"",	 
				"keyword":""
			};
	
		list();			 // 처음 글 목록 불러오기
		
		
		// 비동기로 목록 불러오기 
		function list(){
			console.log("현재 페이지 : " + pageData.curPage + ", 주제 : " + pageData.subject + ", 키워드 : " + pageData.keyword);
			// 태그 초기화 
			$('#tbody').empty();
			$('#div_pagenation').empty();
			
			$.ajax({
				url:"<%=request.getContextPath()%>/list.ajax",
				data:{
					"curPage": pageData.curPage,
					"subject": pageData.subject,
					"keyword": pageData.keyword
				},
				success:function(data){
					console.log("data : " + data);
					
					jsonData = JSON.parse(data);
					var boardList = jsonData.boardList;
					var pagingDto = jsonData.pagingDto;
					
					// 게시글 목록 
					var html = "";
					$.each(boardList, function(index, item){
						var depth = parseInt(item.b_dept);
						
						html += "<tr><td>"+item.b_num+"</td><td>";
						if(depth != 0) {
							for(var i = 0; i < depth; i++) {
								html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp";
							}
							html += "ㄴ";
						}
						html += '<a href="boardDetail.do" class="a_boardDetail" id="'+item.b_num+'">'+item.b_title+'</a>';
						html += '</td><td>';	
						html += item.b_writer + '</td><td>';
						html += item.b_date +'</td><td>';
						html += item.b_hit + '</td></tr>';
					});
					$('#tbody').append(html);
					
					// 페이징 처리 
					var html2 = "";
					if(pagingDto.curRange != 1) {
						html2 += '<a href="#" onClick="fn_paging(1)">[처음]</a>&nbsp;';
					}
					if(pagingDto.curPage != 1) {
						html2 += '<a href="#" onClick="fn_paging('+pagingDto.prevPage+')">[이전]</a>&nbsp;';
					}
					for(var i=pagingDto.startPage; i<=pagingDto.endPage; i++) {
						if(i == pagingDto.curPage) {
							html2 += '<b><U><a href="#" onClick="fn_paging('+i+')">'+i+'</a></U></b>&nbsp;';
						} else {
							html2 += '<a href="#" onClick="fn_paging('+i+')">'+i+'</a>&nbsp;';
						}
					}
					if(pagingDto.curPage != pagingDto.pageCount) {
						html2 += '<a href="#" onClick="fn_paging('+pagingDto.nextPage+')">[다음]</a>&nbsp;';
					}
					if(pagingDto.curRange != pagingDto.rangeCount && pagingDto.rangeCount > 0){
						html2 += '<a href="#" onClick="fn_paging('+pagingDto.pageCount+')">[끝]</a>';
					}
					$('#div_pagenation').append(html2);
				
				},
				error:function(error){
					console.log("목록 불러오기 : 에러 발생");
				}
			});
		}
		
		
		function fn_paging(curPage){
			pageData.curPage = curPage;
			alert("pageData.curPage : " + pageData.curPage + ", curPage : " + curPage);
			list();
		}
		
		$('#btn_writeBoard').click(function() {
			location.href = "boardWriteView.do";
		});
		
		
		$('#btn_search').click(function() {
			pageData.subject = $('#search_subject option:selected').val();	 
			pageData.keyword = $('#search_keyword').val();
			list();
		});
	</script>
</body>
</html>
