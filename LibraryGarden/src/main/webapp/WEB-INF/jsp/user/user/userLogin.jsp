<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	
	<!-- 회원가입 완료 혹은 오류시 메세지 -->
		<script>
		  <%-- Flash attribute로 전달된 메시지 받기 --%>
		  const joinSuccessMessage = "${joinSuccessMessage}";
		  const errorMessage = "${errorMessage}";
		  const loginFailMessage = "${loginFailMessage}";
		  const logoutMsg = "${logoutMsg}";
		
		  if (joinSuccessMessage && joinSuccessMessage.trim() !== "") {
		    alert(joinSuccessMessage);
		  }
		
		  if (errorMessage && errorMessage.trim() !== "") {
		    alert(errorMessage);
		  }
		  
		  if (loginFailMessage && loginFailMessage.trim() !== "") {
			    alert(loginFailMessage);
		  }
		  if (logoutMsg && logoutMsg.trim() !== "") {
			  console.log("로그아웃 메시지:", logoutMsg);  // 디버깅을 위한 콘솔 로그 추가
			  alert(logoutMsg);
			}
		</script>


	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
				</div>

				<!-- 로그인 입력  -->
			<form action="${pageContext.request.contextPath}/user/user/loginAction.do" method="post">
				<div class="draft-content ml-mr-50">
					<input type="text" name="id" class="user-A-input mb-40" placeholder="아이디" >
					
					<input type="password" name="password" class="user-A-input mb-77" placeholder="비밀번호" >
				</div>

				<!-- 로그인 버튼 -->
				<div class="draft-actions mb-33">
					<button type="submit" class="draft-btn-small btn-submit-600-65 ">로그인</button>
				</div>
			</form>

				<!-- 아이디 찾기/비밀번호 찾기/회원가입 링크 -->
				<div class="flex-center font-1D6093-13 mb-37"> <a href="<%= request.getContextPath() %>/user/user/userSearchId.do" class="pl-pr-10">아이디 찾기</a>|<a href="<%= request.getContextPath() %>/user/user/userSearchPassword.do" class="pl-pr-10">비밀번호 찾기</a>|<a href="<%= request.getContextPath() %>/user/user/userJoin.do" class="pl-pr-10">회원가입</a></div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<div id="footer-container"></div>
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>