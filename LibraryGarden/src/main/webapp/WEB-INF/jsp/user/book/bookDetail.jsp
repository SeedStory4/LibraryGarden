<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서상세</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/font.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/adminMain.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/user/userHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<div class="section-title draft-title">도서상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<c:set var="lbd" value="${requestScope.lbd}" />
				<div class="draft-content">
					<img src="${lbd.coverImg}" alt="${lbd.title}"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title">● 제목</span> <span class="info-content">${lbd.title}</span>
						</p>
						<p>
							<span class="info-title">● 부제</span> <span class="info-content">
								<c:choose>
									<c:when test="${not empty lbd.subtitle}">
									${lbd.subtitle}
								  	</c:when>
									<c:otherwise>
								    -
								    </c:otherwise>
								</c:choose>
							</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span class="info-content">${lbd.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span class="info-content">${lbd.publisher}</span>
						</p>
						<p>
							<span class="info-title">● 출판년도</span> <span class="info-content">${lbd.publishedYear}년</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">${lbd.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">${lbd.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">${lbd.info}/${lbd.category}</span>
						</p>
					</div>
					
					<button class="request-status-btn status-btn-ing <c:if test="${empty lbd.status}">on</c:if>" >대출중(~2024.05.31)</button>
					<!-- <button class="request-status-btn status-btn-ok">대출가능</button> -->
					<!-- <button class="request-status-btn status-btn-wating">예약대기</button> -->
					<!-- <button class="request-status-btn status-btn-no">대출불가</button> -->
				</div>
				<p class="description-title">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>
							{lbd.info}
						</p>
					</div>
				</div>

				<hr class="divider">

				<p class="description-title">● 소장정보</p>

				<table class="info-table">
					<thead>
						<tr>
							<th>구분</th>
							<th>청구기호</th>
							<th>자료실</th>
							<th>반납예정일</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>DM000012</td>
							<td>802.123 한 127 v1</td>
							<td>일반열람실</td>
							<td>2025.03.14</td>
							<td class="status-text">대출중</td>
							<!-- <td class="status-text-ok">대출가능</td> -->
							<!-- <td class="status-text text-orange">예약대기</td> -->
							<!-- <td class="status-text text-red">대출불가</td> -->
						</tr>
					</tbody>
				</table>

				<div class="draft-actions mg-top">
					<button class="draft-btn-small btn-submit">예약</button>
					<button class="draft-btn-small btn-list">목록</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>