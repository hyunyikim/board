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
    height: 100px;
    display: inline-block;
  }
  #btn_commentWrite {
    margin-bottom: 30px;
  }

  #td_content {
    height: 250px;
  }
  #textarea_comment {
    height: 200px;
  }
  #td_writer {
    height: 50px;
  }
  #td_writeBtn {
    text-align: right;
  }
  #table_board {
  	width: 100%;
  }

</style>
</head>
<body>
<div class="container">
  <h2>게시판 답글 달기</h2>        
  <form action="replyWrite.do" method="post" id="form_board">
    <table id="table_board">
      <tr>
        <td >글제목</td>
        <td><input class="form-control" type="text" name="b_title"></td>
      </tr>
      <tr>
        <td >작성자</td>
        <td id="td_writer" ><input class="form-control" type="text" name="b_writer"></td>
      </tr>
      <tr>
        <td id="td_content" colspan="2"><textarea name="b_content" id="textarea_comment" class="form-control" placeholder="글을 작성하세요."></textarea></td>
      </tr>
      <tr>
        <td id="td_writeBtn" colspan="2">
        	<input type="button" class="btn" id="btn_goBack" value="뒤로가기">
        	<input type="button" class="btn" id="btn_boardWrite" value="답글작성">
        </td>
      </tr>
    </table>
    <input type="hidden" id="curPage" name="curPage" value="${curPage }">
  	<input type="hidden" id="b_num" name="b_num" value="${b_num }">
  </form>
</div>
<script>
	
	var b_num = $('#b_num').val();
	var curPage = $('#curPage').val();
	
	$('#btn_boardWrite').click(function(){
		if(confirm("글을 등록하시겠습니까?") == true) {
			$('#form_board').submit();
		}
	});
	$('#btn_goBack').click(function(){
		// 폼에 입력한 내용이 있다면 확인창 띄우기 
		location.href="index.do?curPage="+curPage;
	});
</script>
</body>
</html>
