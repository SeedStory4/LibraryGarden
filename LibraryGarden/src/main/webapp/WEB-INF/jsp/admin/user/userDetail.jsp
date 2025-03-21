<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 회원정보 상세</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminAccountManagement.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/admin/adminHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header-tagline">
					<div class="section-title-tagline draft-title-tagline">회원 상세 정보</div>
				</div>
				<hr class="draft-divider-tagline">
				<!-- 선 추가 -->

				<!-- 회원 정보 수정 -->
				<div class="draft-content">
					<div class="mb-21">
						<p class="font-767678-18">이름</p>
						<p class="font-000-20">김이슬</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">회원번호</p>
						<p class="font-000-20">032709</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">아이디</p>
						<p class="font-000-20">dltmf1045</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">휴대전화번호</p>
						<p class="font-000-20">0109171192</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">이메일</p>
						<p class="font-000-20">dltmf1045@gmail.com</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">주소</p>
						<p class="font-000-20">대전 광역시 서구 갈마동 333-33 222호</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">권한</p>
						<p class="font-000-20">일반 회원</p>
					</div>
					<div class="mb-42">
						<p class="font-767678-18">가입일</p>
						<p class="font-000-20">2025.03.05</p>
					</div>
				</div>

	
				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mb-37">
					<button class="draft-btn-small btn-submit-140">수정</button>
					<button class="draft-btn-small btn-cancel-140">취소</button>
				</div>
			</section>
		</div>
	</div>


	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>