<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 로그인</title>
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

				<!-- 로그인 입력  -->
				<div class="draft-content ml-mr-50">
					<input type="text" class="user-A-input mb-40" placeholder="아이디" >
					
					<input type="password" class="user-A-input mb-77" placeholder="비밀번호" >
				</div>

				<!-- 로그인 버튼 -->
				<div class="draft-actions mb-33">
					<button class="draft-btn-small btn-submit-600-65 ">로그인</button>
				</div>

				<!-- 아이디 찾기/비밀번호 찾기/회원가입 링크 -->
				<div class="flex-center font-1D6093-13 mb-37"> <a href="#" class="pl-pr-10">아이디 찾기</a>|<a href="#" class="pl-pr-10">비밀번호 찾기</a>|<a href="#" class="pl-pr-10">회원가입</a></div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<div id="footer-container"></div>
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>