<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 아이디 찾기 결과</title>
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
				<div class="draft-header mb-45">
					<img src="../images/magnifyingGlass.png" alt="magnifyingGlass" class="magnifyingGlass">
					<span class="font-673D31-30">아이디 찾기 완료</span>
				</div>

				<!-- 아이디 찾기 입력 -->
				<div class="draft-content ml-mr-50  mb-57 text-center">
					<div class="mb-50" >
						<p class="font-673D31-20 mb-15">검색결과 아이디는 아래와 같습니다.</p>
						<hr class="draft-divider-find">
					</div>
					<div class ="font-1D6093-B25">
						dltmf1045
					</div>
				</div>

				<!-- 아이디 찾기 / 취소 버튼 -->
				<div class="draft-actions mb-37">
					<button class="draft-btn-small  btn-submit-160">로그인</button>
					<button class="draft-btn-small  btn-list-160">비밀번호 찾기</button>
				</div>
		</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>