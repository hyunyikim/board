<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>게시판</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
  body {
    font-size: 16px;
  }
	#textarea_comment {
    width: 995px;
    height: 100px;
    display: inline-block;
  }
  #btn_commentWrite {
    width: 140px;
    margin-top: -90px;
    height: 90px;
  }
  .div_boardDetail {
  	margin: 3px;
  }
  #span_title {
  	margin-left: 5px;
  	width: 120px;
  }
  .span_board {
  	margin: 0px 5px;
  }
  #div_content {
  	margin: 5px;
  	padding: 5px;
  	height: 300px;
  }
  .td_writer {
  	width: 100px;
  	height: 80px;
  	text-align: center;
  }
  #div_btn {
  	text-align: right;
  }
  
</style>
</head>
<body>
<div class="container">
  <h2>게시판 글 상세보기</h2>        
  <div class="div_boardDetail">
  	<span id="span_title">글제목</span>
  	<span><b>${dto.b_title }</b></span>
  </div>
  <div class="div_boardDetail">
  	<span class="span_board">작성자&nbsp;&nbsp;${dto.b_writer }&nbsp;&nbsp;|</span>
  	<span class="span_board">작성일&nbsp;&nbsp;${dto.b_date }&nbsp;&nbsp;|</span>
  	<span class="span_board">조회수&nbsp;&nbsp;${dto.b_hit }</span>
  </div>
  <hr>
  <div id="div_content">
  	${dto.b_content }
  </div>
  <div id="div_btn">
  	<input type="hidden" id="curPage" value="${curPage }">
  	<input type="hidden" id="b_num" value="${dto.b_num }">
  	<button class="btn" id="btn_reply">답글 달기</button>
  	<button class="btn" id="btn_update">수정</button>
  	<button class="btn" id="btn_delete">삭제</button>
  	<button class="btn" id="btn_list">목록보기</button>
  </div>
  <hr>
  <h4>댓글 작성</h4>
  <div>
    <form action="commentWrite.do" method="post">
      <textarea id="textarea_comment" class="form-control"></textarea>
      <input type="submit" class="btn" id="btn_commentWrite" value="댓글작성">
    </form>
  </div>
  <table>
  	<tr>
  		<td class="td_writer">작성자</td>
  		<td class="td_comment">댓글입니당</td>
  	</tr>
  </table>
</div>
<script>
	var b_num = $('#b_num').val();
	var curPage = $('#curPage').val();
	
	$('#btn_reply').click(function(){
		location.href="replyWriteView.do?curPage="+curPage+"&b_num="+b_num;
	});
	
	$('#btn_update').click(function(){
		location.href="boardUpdateView.do?b_num="+b_num;
	});
	
	$('#btn_delete').click(function(){
		if(confirm("정말 삭제하시겠습니까?") == true) {
			location.href="boardDelete.do?curPage="+curPage+"&b_num="+b_num;	
		}
	});
	
	$('#btn_list').click(function(){
		location.href="index.do?curPage="+curPage;
	});
</script>
</body>
</html>
