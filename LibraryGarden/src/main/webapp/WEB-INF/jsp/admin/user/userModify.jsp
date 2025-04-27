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
				<form action="${pageContext.request.contextPath}/admin/user/userModifyAction.do" method="post" id="adminModifyForm" novalidate>
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
						<input type="text" name="address" id="address" class="user-A-input mb-17" value="${user.address}" placeholder="주소" required readonly>						
				  		<div class="draft-actions-end">
				  			<button type="button" onclick="goPopup()" class="draft-btn-small-16 btn-submit-100-30">주소검색</button>
				  		</div>
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
						    <c:if test="${user.role != '사서'}">
						    <label class="radio-label">
						        <input type="radio" name="role" value="도서관장" class="radio-input" ${user.role == '도서관장' ? 'checked' : ''}>
						        <span class="radio-box">도서 관장</span>
						    </label>
						    </c:if>
						</div>
					</div>
					<div class="mb-33">
					  <p class="font-767678-18">가입일</p>
					  <p class="font-000-20">
					    <c:out value="${fn:replace(fn:substring(user.regDate, 0, 10), '-', '.')}" />
					  </p>
					</div>
				</div>

	
				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mb-37">
					<button type="submit" class="draft-btn-small btn-submit-140">확인</button>
					<button type="reset" class="draft-btn-small btn-list-140" onclick="history.back();">취소</button>
				</div>
			</form>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<div id="footer-container">
		<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
    </div>
    
		    <script>
			  document.querySelector("input[name='phone']").addEventListener("input", function () {
			    // 숫자와 +만 허용
			    this.value = this.value.replace(/[^0-9+]/g, "");
			    if (this.value.indexOf('+') > 0) {
			      this.value = this.value.replace(/\+/g, ''); // 맨 앞이 아닌 "+"는 제거
			    }
			    if ((this.value.match(/\+/g) || []).length > 1) {
			      this.value = this.value.replace(/\+/g, '+'); // "+"가 2개 이상이면 하나만 남기기
			    }
			  });
			
			  document.getElementById("adminModifyForm").addEventListener("submit", function (e) {
			    const form = e.target;
			    const phone = form.phone.value.trim();
			    const email = form.email.value.trim();
			    const address = form.address.value.trim();
			
			    if (phone === "") {
			      alert("휴대전화번호를 입력해주세요.");
			      form.phone.focus();
			      e.preventDefault();
			      return;
			    }
			
			    if (email === "") {
			      alert("이메일을 입력해주세요.");
			      form.email.focus();
			      e.preventDefault();
			      return;
			    }
			
			    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			    if (!emailRegex.test(email)) {
			      alert("올바른 이메일 형식이 아닙니다.");
			      form.email.focus();
			      e.preventDefault();
			      return;
			    }
			
			    if (address === "") {
			      alert("주소를 입력해주세요.");
			      form.address.focus();
			      e.preventDefault();
			      return;
			    }
			  });
			  
			  
			    function goPopup() {
					  var pop = window.open("<%= request.getContextPath() %>/user/user/jusoPopup.do", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");
					}

					// 팝업에서 주소 받아오는 함수
					function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
							  document.getElementById("address").value = decodeHtmlEntities(roadFullAddr);
							}

					
					function decodeHtmlEntities(str) {
						  var txt = document.createElement("textarea");
						  txt.innerHTML = str;
						  return txt.value;
						}
			</script>
    



</body>
</html>