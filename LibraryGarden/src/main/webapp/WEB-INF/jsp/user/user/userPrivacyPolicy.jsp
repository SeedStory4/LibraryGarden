<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 정보 동의</title>
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
				<div class="draft-header ">
					<img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
				</div>

				<!-- 이용 동의 -->
				<div class="draft-content ml-mr-107">
					<div class="mb-11">
						<div class="mb-5 font-673D31-B15 flex-start-center">
							<img src="<%= request.getContextPath() %>/images/check.png" alt="check" class="check"> 이용약관
						</div>
						<div class="terms-box mb-5">
							<p>ooo 서비스 및 제품(이하 ‘서비스’)을 이용해 주셔서 감사합니다. 본 약관은 다양한 서비스의 이용과 관련하여 서비스를 제공하는 ooo 주식회사(이하 ‘ooo’)와 이를 이용하는 서비스 회원(이하 ‘회원’) 또는 비회원과의 관계를 설명하며, 아울러 여러분의 서비스 이용에 도움이 될 수 있는 유익한 정보를 포함하고 있습니다.</p>
							<p>ooo서비스를 이용하시거나 ooo서비스 회원으로 가입하실 경우 여러분은 본 약관 및 관련 운영 정책을 확인하거나 동의하게 되므로, 잠시 시간을 내시어 주의 깊게 살펴봐 주시기 바랍니다.</p>
						</div>
						<label class="custom-checkbox flex-end-center font-673D31-13">
							<input type="checkbox" class="terms-checkbox">
							<span class="checkmark "></span> 이용약관에 동의합니다.
						</label>
					</div>

					<div class="mb-18">
						<div class=" mb-5 font-673D31-B15 flex-start-center">
							<img src="<%= request.getContextPath() %>/images/check.png" alt="check" class="check"> 개인정보 수집 및 이용
						</div>
						<div class="terms-box mb-5">
							<p>개인정보보호법에 따라 oo에 회원가입 신청하시는 분께 수집하는 개인정
								보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 동의 
								거부권 및 동의 거부 시 불이익에 관한 사항을 안내 드리오니 자세히 읽은 후 
								동의하여 주시기 바랍니다.</p>
								<br>
								
								<p>	1. 수집하는 개인정보</p>
								<p>이용자는 회원가입을 하지 않아도 정보 검색 등 대부분의 oo 서비스를 회</p>
							
						</div>
						<label class=" custom-checkbox flex-end-center font-673D31-13">
							<input type="checkbox" class="terms-checkbox">
							<span class="checkmark "></span> 개인정보 수집 및 이용에 동의합니다.
						</label>
					</div>
				</div>

				<!-- 회원가입 버튼 -->
				<div class="draft-actions mb-37">
					<button class="draft-btn-small btn-submit-140">회원가입</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<div id="footer-container"></div>
	<jsp:include page="/common/footer.jsp" /> 
</body>
</html>