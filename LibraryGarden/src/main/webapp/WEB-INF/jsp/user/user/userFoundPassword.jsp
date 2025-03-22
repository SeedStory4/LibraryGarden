<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 비밀번호 찾기 결과</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/userAccountManagement.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/user/userHeader.do" />

	<div class="wrapper">
		<div class="inner-find">
			<!-- 메인 콘텐츠 -->
			<section class="section-find">
				<div class="draft-header ">
					<img src="<%= request.getContextPath() %>/images/mail.png" alt="mail" class="mail">
				</div>

				<!-- 아이디 찾기 입력 -->
				<div class="draft-content ml-mr-50 text-center">
					<div class="mb-50" >
						<p class="font-673D31-20">임시 비밀번호가 등록된 메일로 발송 되었습니다.</p>
						<p class="font-673D31-20">마이페이지에서 비밀 번호를 변경 할 수 있습니다.</p>
					</div>
				</div>

				<!-- 아이디 찾기 / 취소 버튼 -->
				<div class="draft-actions mb-37">
					<button class="draft-btn-small draft-btn-small-n btn-submit-140">로그인</button>
					<button class="draft-btn-small draft-btn-small-n btn-list-140">메인</button>
				</div>
		</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>