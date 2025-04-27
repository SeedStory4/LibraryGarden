<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>희망도서 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
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
			<h2 class="section-title m-0 normal relative">희망도서 목록</h2>
			
			<div class="contents">
				<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
				<div class="book-list pt-0">
					<form action="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="title" selected>제목</option>
							<option value="author">서명/저자사항</option>
							<option value="name">신청자</option>
						</select>
						<input type="text" class="shadow w-720" name="keyword" value="">						
						<button class="btn btn-primary btn-small" >검색</button>
					</div>
					</form>
					
					<ul class="tab flex gap-3">
						<li class="shadow <c:if test="${empty filter}">on</c:if>"><a href="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do?${queryParam}">전체</a></li>
						<li class="shadow <c:if test="${filter eq '신청대기'}">on</c:if>"><a href="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do?status=신청대기&${queryParam}">신청대기</a></li>
						<li class="shadow <c:if test="${filter eq '신청완료'}">on</c:if>"><a href="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do?status=신청완료&${queryParam}">신청완료</a></li>
						<li class="shadow <c:if test="${filter eq '신청중'}">on</c:if>"><a href="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do?status=신청중&${queryParam}">신청중</a></li>
						<li class="shadow <c:if test="${filter eq '신청반려'}">on</c:if>"><a href="${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do?status=신청반려&${queryParam}">신청반려</a></li>
					</ul>
					<div class="table">
						<table>
							<colgroup>
								<col width="8%">
								<col width="9%">
								<col>
								<col width="15%">
								<col width="14%">
								<col width="11%">
								<col width="11%">
								<col width="9%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>서명/저자사항</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청날짜</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${rlist}" var="rd" varStatus="status">
								<tr>
									<td>${(pm.scri.page - 1) * pm.scri.perPageNum + status.index + 1}</td>
									<td><img src="${rd.coverImg}" alt="${rd.title}"></td>
									<td><a href="${pageContext.request.contextPath}/admin/bookRequest/${rd.rqidx}/bookRequestDetail.do">${rd.title}</a></td>
									<td>${rd.author}</td>
									<td>${rd.publisher}</td>
									<td>${rd.name}<br>(${rd.userNumber})</td>
									<td>${fn:replace(fn:substringBefore(rd.regDate, ' '), '-', '.')}</td>
									<td class=
										<c:if test="${rd.status eq '신청중'}">"blue"</c:if>
										<c:if test="${rd.status eq '신청완료'}">"green"</c:if>
										<c:if test="${rd.status eq '신청대기'}">"orange"</c:if>
										<c:if test="${rd.status eq '신청반려'}">"red pointer openRejectionModal" data-reason="${rd.rejectionReason}"</c:if>
									>${rd.status}</td>
								</tr>
								</c:forEach>
								
								<c:if test="${empty rlist}">
									<tr>
										<td colspan="8" class="center">도서가 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
						<ul class="paging flex justify-center">
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
			
			<!-- 반려사유 모달 -->
		    <div id="rejectionModal" class="modal" style="display: none">
		      <div class="modal-content">
		        <div class="title-container">
		          <div class="title">반려사유</div>
		          <div class="title-line"></div>
		        </div>
		
		        <!-- 반려사유 -->
		        <div class="rejection-detail shadow">
		
		        </div>
		
		        <!-- 버튼 영역 -->
		        <div class="button-group">
		          <button class="btn btn-primary" id="closeRejectionModal">확인</button>
		        </div>
		      </div>
		    </div>
		</section>
	</div>
	
	<!-- 푸터가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

    <script src="${pageContext.request.contextPath}/js/rejection.js"></script>
    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>
	
</body>
</html>
