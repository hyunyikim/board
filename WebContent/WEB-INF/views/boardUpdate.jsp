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
  <h2>게시판 글 수정</h2>        
  <form action="boardUpdate.do?b_num=${dto.b_num}" method="post">
    <table id="table_board">
      <tr>
        <td >글제목</td>
        <td><input class="form-control" type="text" name="b_title" value="${dto.b_title }"></td>
      </tr>
      <tr>
        <td >작성자</td>
        <td id="td_writer" ><input class="form-control" type="text" name="b_writer" value="${dto.b_writer }"></td>
      </tr>
      <tr>
        <td id="td_content" colspan="2"><textarea name="b_content" id="textarea_comment" class="form-control" placeholder="글을 작성하세요.">${dto.b_content }</textarea></td>
      </tr>
      <tr>
        <td id="td_writeBtn" colspan="2"><input type="submit" class="btn" value="글작성"></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
