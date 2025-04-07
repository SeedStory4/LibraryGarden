<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 회원정보 수정</title>
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
				<div class="draft-header-tagline">
					<div class="section-title-tagline draft-title-tagline">회원 정보 수정</div>
				</div>
				<hr class="draft-divider-tagline">
				<!-- 선 추가 -->

				<!-- 회원 정보 수정 -->
				<form action="<%= request.getContextPath() %>/user/myPage/userUpdate.do" method="post">
				<div class="draft-content">
					<div class="mb-21">
						<p class="font-767678-18">이름</p>
						<p class="font-000-20">${user.name}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">회원번호</p>
						<p class="font-000-20">${user.userNumber}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">아이디</p>
						<p class="font-000-20">${user.id}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">비밀번호</p>
						<input type="password" name="password" class="user-A-input"  placeholder="비밀번호">
					</div>
					<div class="mb-21">
						<p class="font-767678-18">비밀번호 확인</p>
						<input type="password" name="passwordConfirm" class="user-A-input" placeholder="비밀번호 확인" >
					</div>
					<div class="mb-21">
						<p class="font-767678-18">휴대전화번호</p>
						<input type="tel" name="phone" class="user-A-input" value="${user.phone}" placeholder="휴대전화번호 ( 예> 01012345678 )" >
					</div>
					<div class="mb-21">
						<p class="font-767678-18">이메일</p>
						<input type="email" name="email" class="user-A-input" value="${user.email}" placeholder="이메일">
					</div>
					<div class="mb-21">
						<p class="font-767678-18">주소</p>
						<input type="text" name="address" class="user-A-input"  value="${user.address}" placeholder="주소">
					</div>
					
					<div class="mb-21">
						<p class="font-767678-18">현재 비밀번호</p>
						<input type="password" name="NowPassword" class="user-A-input"  placeholder="현재 비밀번호" required>
					</div>
					
					<div class="mb-33">
						<p class="font-767678-18">가입일</p>
						<p class="font-000-20">${user.date}</p>
					</div>
				</div>

	
				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mb-37">
					<button type="submit" class="draft-btn-small btn-submit-140">확인</button>
    				<button type="reset" class="draft-btn-small btn-cancel-140">취소</button>
				</div>
			</form>
			</section>
		</div>
	</div>


	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>