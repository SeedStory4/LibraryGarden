<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 메인</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/userMain.css">
</head>
<body class="custom-page">

    <!-- 헤더가 로드될 부분 -->
	<%-- <jsp:include page="/user/userHeader" /> --%>

	<div class="main-image">
		<img src="<%= request.getContextPath() %>/images/image48.png" alt="메인 이미지">
	</div>

	<div class="wrapper">
			<section class="book-section">
				<h2 class="section-title">이달의 대출 도서 순위</h2>
				<div class="book-list">
					<div class="book-row">
						<div class="book-card"> 
						<span class="book-rank">1</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card"> 
						<span class="book-rank">2</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card">
						<span class="book-rank">3</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div>
					</div>
					<div class="book-row">
						<div class="book-card"> 
						<span class="book-rank">4</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card"> 
						<span class="book-rank">5</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card">
						<span class="book-rank">6</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>


			<hr class="divider">

			<section class="book-section">
				<h2 class="section-title">이달의 신간</h2>
				<div class="book-list">
					<div class="book-row">
						<div class="book-card"> 
						<span class="book-rank">1</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card"> 
						<span class="book-rank">2</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card"> 
						<span class="book-rank">3</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div>
					</div>

					<div class="book-row">
						<div class="book-card"> 
						<span class="book-rank">4</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card"> 
						<span class="book-rank">5</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div> 
						<div class="book-card">
						<span class="book-rank">6</span> 
						<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" class="book-img">
							<div class="book-info">
								<div class="book-info-row">
									<p class="info-title">제목</p>
									<p class="info-content">책 제목</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">부제</p>
									<p class="info-content">책 부제</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">저자</p>
									<p class="info-content">한강</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판사</p>
									<p class="info-content">창비</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">출판년도</p>
									<p class="info-content">2024년</p>
								</div>
								<div class="book-info-row">
									<p class="info-title">전체쪽수</p>
									<p class="info-content">216쪽</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
		
    <!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>