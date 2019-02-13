<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
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
		<h3 id="h3_signUp">회원가입</h3>
	</div>
	<form action="signUp.do" method="post" id="form_signUp">
		<label for="id" id="signUpLabel_id">아이디 </label><input class="form-control signUp" type="text" id="id" name="id">
		<button id="btn_checkId" class="btn">중복확인</button>
		<br>
		<div class="div_btn">
			<div id="div_checkId"></div>
		</div>
	
		<label for="password" id="signUpLabel_password">비밀번호 </label><input class="form-control signUp" type="password" id="password" name="password"><br>
		<label for="password" id="signUpLabel_passwordChk">비밀번호 확인</label><input class="form-control signUp" type="password" id="passwordCheck"><br>
	
		<div class="div_btn">
			<input type="button" id="btn_signUp" value="회원가입" class="btn">
			<input type="reset" id="btn_cancel" value="취소" class="btn">
		</div>
	</form>
	<script>
		var checkId = -1;
		$('#btn_checkId').click(function(){
			$.ajax({
				url:"<%=request.getContextPath()%>/checkId.ajax",
				data:{
					"id": $('#id').val()
				},
				success:function(data){
					if(data == 0) {
						$('#div_checkId').empty();
						$('#div_checkId').append("아이디를 사용하실 수 있습니다.");
						checkId = 0;
					} else {
						$('#div_checkId').empty();
						$('#div_checkId').append("입력하신 아이디가 중복입니다. 다시 입력하세요.");
						checkId = 1;
					}
				},
				error:function(error){
					console.log("목록 불러오기 : 에러 발생");
				}
			});
			return false;
		});
		$('#btn_signUp').click(function(){
			if(checkId == -1) {
				alert("아이디 중복 확인 후 진행하세요.");
			} else if (checkId == 1) {
				alert("아이디 중복 확인 후 진행하세요.");
			} else {
				alert("중복 확인 완료");
				$('#form_signUp').submit();
				self.close();
			}
		});
	</script>
</body>
</html>