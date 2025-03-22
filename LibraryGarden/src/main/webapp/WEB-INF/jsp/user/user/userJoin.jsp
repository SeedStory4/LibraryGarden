<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 회원가입</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/userAccountManagement.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/user/userHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
				</div>

				<!-- 회원가입 입력 -->
				<div class="draft-content ml-mr-50">
					<input type="text" class="user-A-input mb-30" placeholder="이름" maxlength="10">
					<input type="text" class="user-A-input mb-17" placeholder="아이디" >
					<div class="draft-actions-end mb-24">
						<button class="draft-btn-small-16 btn-submit-100-30">중복확인</button>
					</div>
		
					
					<input type="password" class="user-A-input mb-30" placeholder="비밀번호" >
					<input type="password" class="user-A-input mb-30" placeholder="비밀번호 확인" >
					<input type="tel" class="user-A-input mb-30"placeholder="휴대전화번호 ( 예> 01012345678 )" >
					<input type="email" class="user-A-input mb-30" placeholder="이메일" >
					<input type="text" class="user-A-input mb-57" placeholder="주소">
				</div>

				<!-- 회원가입/취소 버튼 -->
				<div class="draft-actions mb-37">
					<button class="draft-btn-small btn-submit-140 ">회원가입</button>
					<button class="draft-btn-small btn-cancel-140">취소</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>