<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
.signUp {
	display: inline-block;
    width: 60%;
}
#signUpLabel_id {
	margin: 10px 25px;
}

#signUpLabel_password {
	margin: 10px 20px;
}
#signUpLabel_passwordChk {
	margin: 10px 10px;
}
.div_btn { 
	text-align: center;
}
#h3_signUp {
	display: inline-block;
}
</style>
</head>
<body>
	<div class="div_btn">
		<h3 id="h3_signUp">로그인</h3>
	</div>
	<form action="signUp.do" method="post" id="form_signUp">
		<label for="id" id="signUpLabel_id">아이디 </label><input class="form-control signUp" type="text" id="id" name="id">
		<br>
		<div class="div_btn">
			<div id="div_checkId"></div>
		</div>
	
		<label for="password" id="signUpLabel_password">비밀번호 </label><input class="form-control signUp" type="password" id="password" name="password"><br>
		<div class="div_btn">
			<input type="button" id="btn_signIn" value="로그인" class="btn">
			<input type="reset" id="btn_cancel" value="취소" class="btn">
		</div>
	</form>
	<script>
		
		$('#btn_signIn').click(function(){
			$.ajax({
				url:"<%=request.getContextPath()%>/signIn.ajax",
				data:{
					"id": $('#id').val(),
					"password": $('#password').val()
				},
				success:function(data){
					if(data == '같다') {
						self.close();
					} else {
						$('#div_checkId').empty();
						$('#div_checkId').append("아이디와 비밀번호가 틀렸습니다. 다시 입력하세요.");
					}
				},
				error:function(error){
					console.log("목록 불러오기 : 에러 발생");
				}
			});
		});
	</script>
</body>
</html>