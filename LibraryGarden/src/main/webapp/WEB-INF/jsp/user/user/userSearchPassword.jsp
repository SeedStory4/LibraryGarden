<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 비밀번호 찾기</title>
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

				<!-- 비밀번호호 찾기 입력 -->
				<div class="draft-content ml-mr-50">
					<input type="text" class="user-A-input mb-40" placeholder="아이디" >
					
					<input type="tel" class="user-A-input mb-17" placeholder="휴대전화번호 ( 예> 01012345678 )" >

					<div class="font-D94436-18 mb-45">
						<p> • 가입시 등록한 아이디와 휴대폰 번호를 입력해 주세요.</p>
						<p> • 회원정보와 입력한 정보가 같아야 임시 비밀번호를 받을 수 있습니다.</p>
					</div>
				</div>

				<!-- 비밀번호 찾기 / 취소 버튼 -->
				<div class="text-center mb-37">
					<button class="draft-btn-small btn-submit-600-65 mb-15">비밀번호 이메일 발송</button>
					<button class="draft-btn-small btn-cancel-600-65" >취소</button>
				</div>
		</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>