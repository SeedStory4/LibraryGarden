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
					<div class="section-title-tagline draft-title-tagline">회원 상세 정보</div>
				</div>
				<hr class="draft-divider-tagline">
				<!-- 선 추가 -->

				<!-- 회원 정보 수정 -->
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
						<p class="font-767678-18">휴대전화번호</p>
						<p class="font-000-20">${user.phone}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">이메일</p>
						<p class="font-000-20">${user.email}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">주소</p>
						<p class="font-000-20">${user.address}</p>
					</div>
					<div class="mb-21">
						<p class="font-767678-18">권한</p>
						<p class="font-000-20">${user.role}</p>
					</div>
					<div class="mb-33">
					  <p class="font-767678-18">가입일</p>
					  <p class="font-000-20">
					    <c:out value="${fn:replace(fn:substring(user.date, 0, 10), '-', '.')}" />
					  </p>
					</div>
				</div>

	
				
				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mb-37">
					<c:if test="${sessionScope.loginUser.role == '도서관장' || sessionScope.loginUser.role == '사서'}">
						<form action="${pageContext.request.contextPath}/admin/user/userModify.do" method="get" style="display:inline;">
							<input type="hidden" name="id" value="${user.id}" />
							<button type="submit" class="draft-btn-small btn-submit-140">수정</button>
						</form>
					</c:if>
					<a href="${pageContext.request.contextPath}/admin/user/userList.do">
						<button class="draft-btn-small btn-cancel-140">취소</button>
					</a>
				</div>
			</section>
		</div>
	</div>


	<!-- 푸터 로드할 부분 -->
	<div id="footer-container">
		<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
    </div>
</body>
</html>