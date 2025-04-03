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
		        <form action="<%= request.getContextPath() %>/user/user/userJoinAction.do" method="post" id="joinForm" novalidate>
		          <input type="text" name="name" class="user-A-input mb-30" placeholder="이름" maxlength="10" required>
		
		          <input type="text" name="id" class="user-A-input mb-17" placeholder="아이디" required>
		          <div class="draft-actions-end mb-24">
		            <button type="button" id="idCheckBtn" class="draft-btn-small-16 btn-submit-100-30">중복확인</button>
		          </div>
		
		          <input type="password" name="password" class="user-A-input mb-30" placeholder="비밀번호" required>
		          <input type="password" name="passwordConfirm" class="user-A-input mb-30" placeholder="비밀번호 확인" required>
		          <input type="tel" name="phone" pattern="^\+?\d{10,15}$" class="user-A-input mb-30" placeholder="휴대전화번호 ( 예> 01012345678 )" required>
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
			  let isIdChecked = false;
			  let lastCheckedId = "";
		   
			document.getElementById("idCheckBtn").addEventListener("click", function () {
		      let userId = document.querySelector("input[name='id']").value.trim();
		
		      if (userId === "") {
		        alert("아이디를 입력해주세요.");
		        return;
		      }
		      
		      // 아이디 형식 검사
		      const idRegex = /^[a-zA-Z0-9]+$/;
		      if (!idRegex.test(userId)) {
		        alert("아이디는 영문자와 숫자만 입력 가능합니다.");
		        return;
		      }
		
		      fetch("<%= request.getContextPath() %>/user/user/checkId.do", {
		        method: "POST",
		        headers: { "Content-Type": "application/x-www-form-urlencoded" },
		        body: new URLSearchParams({ id: userId })
		      })
		      .then(res => res.text())
		      .then(result => {
		    	  
		    	  console.log("서버 응답 결과:", result); // <-- 디버깅 로그!
		        
		    	  if (result === "OK") {
		          alert("사용 가능한 아이디입니다!");
		          isIdChecked = true;              // 중복검사 성공 플래그 설정
		          lastCheckedId = userId;          // 중복검사 당시의 아이디 기억
		        } else {
		          alert("이미 사용 중인 아이디입니다.");
		          isIdChecked = false;             // 실패 시는 다시 false로
		          lastCheckedId = "";              // 이전 기록 제거
		        }
		      })
		      .catch(err => {
		        console.error("중복 확인 오류:", err);
		        alert("서버 오류 발생!");
		      });
		    });
			
			// 아이디 입력값 변경 시 중복확인 상태 초기화
			  document.querySelector("input[name='id']").addEventListener("input", function () {
			    isIdChecked = false;
			    lastCheckedId = "";
			  });
			
			// 전화번호 오직 숫자만와 +만 허용 
			  document.querySelector("input[name='phone']").addEventListener("input", function () {
	        	  // 입력값 중 숫자와 +만 허용
	        	  this.value = this.value.replace(/[^0-9+]/g, "");

	        	  // "+"가 맨 앞에 1번만 오도록 제한
	        	  if (this.value.indexOf('+') > 0) {
	        	    this.value = this.value.replace(/\+/g, ''); // 맨 앞이 아닌 "+"는 제거
	        	  }
	        	  if ((this.value.match(/\+/g) || []).length > 1) {
	        	    this.value = this.value.replace(/\+/g, '+'); // "+"가 2개 이상이면 하나만 남기기
	        	  }
	        	});
		    
		    
		    
		    document.getElementById("joinForm").addEventListener("submit", function (e) {
		        const form = e.target;
		        const name = form.name.value.trim();
		        const id = form.id.value.trim();
		        const pw = form.password.value.trim();
		        const pwConfirm = form.passwordConfirm.value.trim();
		        const phone = form.phone.value.trim();
		        const email = form.email.value.trim();
		        const address = form.address.value.trim();

		        if (name === "") {
		          alert("이름을 입력해주세요.");
		          form.name.focus();
		          e.preventDefault();
		          return;
		        }
		        if (id === "") {
		          alert("아이디를 입력해주세요.");
		          form.id.focus();
		          e.preventDefault();
		          return;
		        }
		        
		     // 아이디 영어로만 하기
		        const idRegex = /^[a-zA-Z0-9]+$/;
		        if (!idRegex.test(id)) {
		          alert("아이디는 영문자와 숫자만 입력 가능합니다.");
		          form.id.focus();
		          e.preventDefault();
		          return;
		        }
		        
		        
		        if (pw === "") {
		          alert("비밀번호를 입력해주세요.");
		          form.password.focus();
		          e.preventDefault();
		          return;
		        }
		        
		        const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+]{8,}$/;
		        if (!pwRegex.test(pw)) {
		          alert("비밀번호는 8자 이상, 영문/숫자/를 포함해야 합니다.");
		          form.password.focus();
		          e.preventDefault();
		          return;
		        }
		        
		        if (pwConfirm === "") {
		          alert("비밀번호 확인을 입력해주세요.");
		          form.passwordConfirm.focus();
		          e.preventDefault();
		          return;
		        }
		        if (pw !== pwConfirm) {
		          alert("비밀번호가 서로 일치하지 않습니다.");
		          form.passwordConfirm.focus();
		          e.preventDefault();
		          return;
		        }
		        
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
		        
		     // 아이디 중복확인 여부 체크
		        if (!isIdChecked || id !== lastCheckedId) {
		          alert("아이디 중복 확인을 해주세요.");
		          form.id.focus();
		          e.preventDefault();
		          return;
		        }
		     
		      });
		    });
		</script>

		<!-- 푸터 로드 -->
		<jsp:include page="/common/footer.jsp" /> 
		
		</body>
</html>
