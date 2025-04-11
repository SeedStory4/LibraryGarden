<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서관리 목록</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

	<!-- 헤더가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal relative">도서관리 목록<button class="btn btn-primary absolute"  onclick="location.href='${pageContext.request.contextPath}/admin/book/bookWrite.do'">도서등록</button></h2>
			
			<div class="contents">
				<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
				<div class="book-list pt-0">
				<form action="${pageContext.request.contextPath}/admin/book/bookList.do">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="title" selected>제목</option>
							<option value="author">서명/저자사항</option>
						</select>
						<input type="text" class="shadow w-720" name="keyword" value="">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
				</form>
					<div class="table">
						<table>
							<colgroup>
								<col width="6%">
								<col width="8%">
								<col>
								<col width="10%">
								<col width="10%">
								<col width="15%">
								<col width="12%">
								<col width="12%">
								<col width="8%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>청구기호</th>
									<th>자료실</th>
									<th>반납예정일</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
							    <c:forEach items="${requestScope.lblist}" var="lbd" varStatus="status">
								<tr>
									<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
									<td><img src="${lbd.coverImg}" alt="${lbd.title}"></td>
									<td><a href="${pageContext.request.contextPath}/admin/book/${lbd.lbidx}/bookDetail.do">${lbd.title}</a></td>
									<td>${lbd.author}</td>
									<td>${lbd.publisher}</td>
									<td>${lbd.callName}</td>
									<td>${lbd.location}</td>
									<td><c:choose>
										  <c:when test="${not empty lbd.dueDate}">
										    <c:if test="${lbd.status eq '대출중' or lbd.status eq '예약대기'}">${fn:replace(lbd.dueDate, '-', '.')}</c:if>
										    <c:if test="${lbd.status ne '대출중' and lbd.status ne '예약대기'}">-</c:if>	
										  </c:when>
										  <c:otherwise>
										    -
										  </c:otherwise>
										</c:choose>
									</td>
									<td class=
										<c:if test="${lbd.status eq '대출중'}">"blue"</c:if>
										<c:if test="${lbd.status eq '대출가능'}">"green"</c:if>
										<c:if test="${lbd.status eq '예약대기'}">"orange"</c:if>
										<c:if test="${lbd.status eq '대출불가'}">"red pointer openRejectionModal" </c:if>
									>${lbd.status}</td>
								</tr>
								</c:forEach>
								<c:if test="${empty lblist}">
									<tr>
										<td colspan="9" style="text-align:center;">예약된 도서가 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-center">
							<c:if test="${requestScope.pm.prev == true}">
							<li>
					          <a href="${pageContext.request.contextPath}/admin/book/bookList.do?page=${requestScope.pm.startPage - 1}&${queryParam}" aria-label="Previous">◀</a>
					        </li>
							</c:if> 
							
					        <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
					        <li><a class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>" href="${pageContext.request.contextPath}/admin/book/bookList.do?page=${i}&${queryParam}">${i}</a></li>
					        </c:forEach>
					        
					        <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
							<li class="page-item">
					          <a href="${pageContext.request.contextPath}/admin/book/bookList.do?page=${requestScope.pm.endPage + 1}&${queryParam}" aria-label="Next">▶</a>
					        </li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<!-- 푸터가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>
	
</body>
</html>
