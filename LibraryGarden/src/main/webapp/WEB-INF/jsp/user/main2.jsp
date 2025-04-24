<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 메인</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/userMain.css">
</head>
<body class="custom-page">

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
							<div class="book-row">
						<c:forEach var="book" items="${topLoanBooks}" varStatus="status">
						  <div class="book-card">
						    <span class="book-rank">${status.index + 1}</span>
						    <img src="${book.coverImg}" class="book-img">
						    <div class="book-info">
						        <div class="book-info-row"><p class="info-title">제목</p><p class="info-content">${book.title}</p></div>
						        <div class="book-info-row"><p class="info-title">부제</p><p class="info-content">${book.subtitle}</p></div>
						        <div class="book-info-row"><p class="info-title">저자</p><p class="info-content">${book.author}</p></div>
						        <div class="book-info-row"><p class="info-title">출판사</p><p class="info-content">${book.publisher}</p></div>
						        <div class="book-info-row"><p class="info-title">출판년도</p><p class="info-content">${book.publishedYear}</p></div>
						        <div class="book-info-row"><p class="info-title">전체쪽수</p><p class="info-content">${book.totalPages}쪽</p></div>
						    </div>
						  </div>
						</c:forEach>
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
			            <c:forEach var="book" items="${latestBooks}" varStatus="status">
			                <div class="book-card">
			                    <span class="book-rank">${status.index + 1}</span>
			                    <img src="${book.coverImg}" class="book-img">
			                    <div class="book-info">
			                        <div class="book-info-row">
			                            <p class="info-title">제목</p>
			                            <p class="info-content">${book.title}</p>
			                        </div>
			                        <div class="book-info-row">
			                            <p class="info-title">부제</p>
			                            <p class="info-content">
			                                <c:choose>
			                                    <c:when test="${not empty book.subtitle}">
			                                        ${book.subtitle}
			                                    </c:when>
			                                    <c:otherwise>
			                                        -
			                                    </c:otherwise>
			                                </c:choose>
			                            </p>
			                        </div>
			                        <div class="book-info-row">
			                            <p class="info-title">저자</p>
			                            <p class="info-content">${book.author}</p>
			                        </div>
			                        <div class="book-info-row">
			                            <p class="info-title">출판사</p>
			                            <p class="info-content">${book.publisher}</p>
			                        </div>
			                        <div class="book-info-row">
			                            <p class="info-title">출판년도</p>
			                            <p class="info-content">
			                                <c:choose>
			                                    <c:when test="${not empty book.publishedYear}">
			                                        ${fn:substring(book.publishedYear, 0, 4)}년
			                                    </c:when>
			                                    <c:otherwise>
			                                        -
			                                    </c:otherwise>
			                                </c:choose>
			                            </p>
			                        </div>
			                        <div class="book-info-row">
			                            <p class="info-title">전체쪽수</p>
			                            <p class="info-content">${book.totalPages}쪽</p>
			                        </div>
			                    </div>
			                </div>
			            </c:forEach>
			        </div>

					
				</div>
			</section>
		</div>
		
    <!-- 푸터 로드할 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
</body>
</html>