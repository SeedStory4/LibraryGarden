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
		        <form action="<%= request.getContextPath() %>/user/user/userJoinAction.do" method="post" id="joinForm">
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
			  let isIdChecked = false;
			  let lastCheckedId = "";
		   
			document.getElementById("idCheckBtn").addEventListener("click", function () {
		      let userId = document.querySelector("input[name='id']").value.trim();
		
		      if (userId === "") {
		        alert("아이디를 입력해주세요.");
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
		        } else {
		          alert("이미 사용 중인 아이디입니다.");
		        }
		      })
		      .catch(err => {
		        console.error("중복 확인 오류:", err);
		        alert("서버 오류 발생!");
		      });
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
		        if (pw === "") {
		          alert("비밀번호를 입력해주세요.");
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
