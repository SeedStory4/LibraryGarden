<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사서 기안 등록</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<%-- <jsp:include page="/WEB-INF/jsp/user/header.jsp" /> --%>

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section draft-section">
				<div class="draft-header">
					<div class="section-title draft-title">기안 등록</div>
					<div class="draft-buttons">
						<button class="draft-btn btn-green">희망도서선택</button>
						<button class="draft-btn btn-green small">도서선택</button>
					</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<div class="draft-content">
					<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="도서 이미지"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title">● 제목</span> <span class="info-content">채식주의자</span>
						</p>
						<p>
							<span class="info-title">● 부제</span> <span class="info-content">채식주의자</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span
								class="info-content">한강</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span class="info-content">창비</span>
						</p>
						<p>
							<span class="info-title">● 출판년도</span> <span class="info-content">2024년</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">216쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">12345687351</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">145×210mm/300g</span>
						</p>
					</div>
				</div>

				<!-- 등록/취소 버튼 -->
				<div class="draft-actions-mt">
					<button class="draft-btn-small btn-submit">등록</button>
					<button class="draft-btn-small btn-cancel">취소</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<%-- <jsp:include page="/common/footer.jsp" /> --%>

</body>
</html>