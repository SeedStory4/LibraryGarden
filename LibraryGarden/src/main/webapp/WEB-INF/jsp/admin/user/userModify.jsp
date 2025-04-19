<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 회원정보 상세</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminAccountManagement.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 역활(role)에 따른 헤더 변경 -->
		 <div id="header-container">
			<c:choose>
			  <c:when test="${sessionScope.loginUser.role == '도서관장' || sessionScope.loginUser.role == '사서'}">
			    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>
			  </c:when>
			  <c:otherwise>
			    <jsp:include page="/WEB-INF/jsp/user/userHeader.jsp"/>
			  </c:otherwise>
			</c:choose>
		  </div>

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
				<form action="${pageContext.request.contextPath}/admin/user/userModifyAction.do" method="post">
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
						<input type="hidden" name="id" value="${user.id}">
						<p class="font-000-20">${user.id}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">휴대전화번호</p>
						<input type="tel" name="phone" class="user-A-input" value="${user.phone}"  placeholder="휴대전화번호 ( 예> 01012345678 )">
					</div>
					<div class="mb-21">
						<p class="font-767678-18">이메일</p>
						<input type="email" name="email" class="user-A-input" value="${user.email}"  placeholder="이메일">
					</div>
					<div class="mb-21">
						<p class="font-767678-18">주소</p>
						<input type="text" name="address" class="user-A-input"  value="${user.address}" placeholder="주소">
					</div>
					<div class="mb-21">
						<p class="font-767678-18">권한</p>
						<div class="radio-container">
							<label class="radio-label">
						        <input type="radio" name="role" value="일반회원" class="radio-input" ${user.role == '일반회원' ? 'checked' : ''}>
						        <span class="radio-box">일반 회원</span>
						    </label>
						    <label class="radio-label">
						        <input type="radio" name="role" value="사서" class="radio-input" ${user.role == '사서' ? 'checked' : ''}>
						        <span class="radio-box">사서</span>
						    </label>
						    <label class="radio-label">
						        <input type="radio" name="role" value="도서관장" class="radio-input" ${user.role == '도서관장' ? 'checked' : ''}>
						        <span class="radio-box">도서 관장</span>
						    </label>
						</div>
					</div>
					<div class="mb-30">
						<p class="font-767678-18">가입일</p>
						<p class="font-000-20">${user.date}</p>
					</div>
				</div>

	
				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mb-37">
					<button type="submit" class="draft-btn-small btn-submit-140">확인</button>
					<button type="reset" class="draft-btn-small btn-list-140">취소</button>
				</div>
			</form>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<div id="footer-container">
		<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
    </div>



</body>
</html>