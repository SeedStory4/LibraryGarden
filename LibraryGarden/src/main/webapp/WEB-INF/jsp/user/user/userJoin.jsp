<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 회원가입</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/userAccountManagement.css">
</head>
<body>

		<!-- 헤더 로드 -->
		<jsp:include page="/user/userHeader.do" />
		
		<div class="wrapper">
		  <div class="inner">
		    <section class="section">
		      <div class="draft-header">
		        <img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
		      </div>
		
		      <!-- 회원가입 form -->
		      <div class="draft-content ml-mr-50">
		        <form action="<%= request.getContextPath() %>/user/userJoinAction.do" method="post" id="joinForm">
		          <input type="text" name="name" class="user-A-input mb-30" placeholder="이름" maxlength="10" required>
		
		          <input type="text" name="id" class="user-A-input mb-17" placeholder="아이디" required>
		          <div class="draft-actions-end mb-24">
		            <button type="button" id="idCheckBtn" class="draft-btn-small-16 btn-submit-100-30">중복확인</button>
		          </div>
		
		          <input type="password" name="password" class="user-A-input mb-30" placeholder="비밀번호" required>
		          <input type="password" name="passwordConfirm" class="user-A-input mb-30" placeholder="비밀번호 확인" required>
		          <input type="tel" name="phone" class="user-A-input mb-30" placeholder="휴대전화번호 ( 예> 01012345678 )" required>
		          <input type="email" name="email" class="user-A-input mb-30" placeholder="이메일" required>
		          <input type="text" name="address" class="user-A-input mb-57" placeholder="주소" required>
		
		          <!-- 버튼들 form 안에 위치 -->
		          <div class="draft-actions mb-37">
		            <button type="submit" class="draft-btn-small btn-submit-140">회원가입</button>
		            <button type="reset" class="draft-btn-small btn-cancel-140">취소</button>
		          </div>
		        </form>
		      </div>
		    </section>
		  </div>
		</div>
		
		<!-- JS: 중복확인 클릭 이벤트 -->
		<script>
		  document.addEventListener("DOMContentLoaded", function() {
		    document.getElementById("idCheckBtn").addEventListener("click", function () {
		      let userId = document.querySelector("input[name='id']").value.trim();
		
		      if (userId === "") {
		        alert("아이디를 입력해주세요.");
		        return;
		      }
		
		      // 추후 AJAX로 중복확인 구현
		      alert("입력된 아이디: " + userId + "\n(중복확인 기능은 추후 구현 예정)");
		    });
		  });
		</script>
		
		<!-- 푸터 로드 -->
		<jsp:include page="/common/footer.jsp" /> 
		
		</body>
</html>
