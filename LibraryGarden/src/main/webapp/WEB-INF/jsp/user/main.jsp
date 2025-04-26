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
			    <c:forEach var="i" begin="0" end="${fn:length(topLoanBooks) - 1}" step="3">
			      <div class="book-row">
			        <c:forEach var="j" begin="0" end="2">
			          <c:if test="${i + j < fn:length(topLoanBooks)}">
			            <c:set var="book" value="${topLoanBooks[i + j]}" />
			            <div class="book-card">
			              <span class="book-rank">${i + j + 1}</span>
			              <img src="${book.coverImg}" class="book-img">
			              <div class="book-info">
			                <div class="book-info-row">
			                  <p class="info-title">제목</p>
			                  <p class="info-content" title="${book.title}">${book.title}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">부제</p>
			                  <p class="info-content" title="${book.subtitle}">${book.subtitle}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">서명/저자사항</p>
			                  <p class="info-content" title="${book.author}">${book.author}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">출판사</p>
			                  <p class="info-content" title="${book.publisher}">${book.publisher}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">출판년도</p>
			                  <p class="info-content" title="${book.publishedYear}">${book.publishedYear}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">전체쪽수</p>
			                  <p class="info-content">${book.totalPages}쪽</p>
			                </div>
			              </div>
			            </div>
			          </c:if>
			        </c:forEach>
			      </div>
			    </c:forEach>
			  </div>
			</section>


			<hr class="divider">

			<section class="book-section">
			  <h2 class="section-title">이달의 신간</h2>
			  <div class="book-list">
			    <c:forEach var="i" begin="0" end="${fn:length(latestBooks) - 1}" step="3">
			      <div class="book-row">
			        <c:forEach var="j" begin="0" end="2">
			          <c:if test="${i + j < fn:length(latestBooks)}">
			            <c:set var="book" value="${latestBooks[i + j]}" />
			            <div class="book-card">
			              <span class="book-rank">${i + j + 1}</span>
			              <img src="${book.coverImg}" class="book-img">
			              <div class="book-info">
			                <div class="book-info-row">
			                  <p class="info-title">제목</p>
			                  <p class="info-content" title="${book.title}">${book.title}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">부제</p>
			                  <p class="info-content" title="${book.subtitle}">
			                    <c:choose>
			                      <c:when test="${not empty book.subtitle}">
			                        ${book.subtitle}
			                      </c:when>
			                      <c:otherwise>-</c:otherwise>
			                    </c:choose>
			                  </p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">서명/저자사항</p>
			                  <p class="info-content" title="${book.author}">${book.author}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">출판사</p>
			                  <p class="info-content" title="${book.publisher}">${book.publisher}</p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">출판년도</p>
			                  <p class="info-content" title="${book.publishedYear}">
			                    <c:choose>
			                      <c:when test="${not empty book.publishedYear}">
			                        ${fn:substring(book.publishedYear, 0, 4)}년
			                      </c:when>
			                      <c:otherwise>-</c:otherwise>
			                    </c:choose>
			                  </p>
			                </div>
			                <div class="book-info-row">
			                  <p class="info-title">전체쪽수</p>
			                  <p class="info-content">${book.totalPages}쪽</p>
			                </div>
			              </div>
			            </div>
			          </c:if>
			        </c:forEach>
			      </div>
			    </c:forEach>
			  </div>
			</section>

		</div>
		
    <!-- 푸터 로드할 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
</body>
</html>