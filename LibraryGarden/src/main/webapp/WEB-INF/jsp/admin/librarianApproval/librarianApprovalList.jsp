<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>결재관리 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

    <div id="header-container">
    	<%@ include file="/WEB-INF/jsp/user/userHeader.jsp" %>
    </div>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal relative">결재관리 목록<button class="btn btn-primary absolute">기안등록</button></h2>
			
			<div class="contents">
				<div class="book-list pt-0">
					<form action="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="title" selected>제목</option>
							<option value="author">저자</option>
							<option value="name">신청자</option>
						</select>
						<input type="text" class="shadow w-720" name="keyword" value="">
						<button class="btn btn-primary btn-small">검색</button>
					</div>
					</form>
					<ul class="tab flex gap-3">
						<li class="on shadow"><a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do">전체</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?status=대기">대기</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?status=승인">승인</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?status=반려">반려</a></li>
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
								<col width="7%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청날짜</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
      							<c:forEach items="${requestScope.alist}" var="ad" varStatus="status">
								<tr>
									<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
									<td><img src="${ad.coverImg}" alt="${ad.title}"></td>
									<td><a href="${pageContext.request.contextPath}/admin/librarianApproval/${ad.aidx}/librarianApprovalDetail">"${ad.title}"</a></td>
									<td>${ad.author}</td>
									<td>${ad.publisher}</td>
									<td>${ad.name}<br>(${ad.userNumber})</td>
									<td>${fn:substringBefore(ad.regDate, ' ')}</td>
									<td class=
										<c:if test="${ad.status eq '대기'}">"blue"</c:if>
										<c:if test="${ad.status eq '승인'}">"green"</c:if>
										<c:if test="${ad.status eq '반려'}">"red pointer" id="openRejectionModal" data-reason="${ad.rejectionReason}"</c:if>
									>${ad.status}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>						
      
      					<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
						<ul class="paging flex w-270 justify-center">
							<c:if test="${requestScope.pm.prev == true}">
							<li>
					          <a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?page=${requestScope.pm.startPage - 1}&${queryParam}" aria-label="Previous">◀</a>
					        </li>
							</c:if> 
							
					        <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
					        <li><a class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>" href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?page=${i}&${queryParam}">${i}</a></li>
					        </c:forEach>
					        
					        <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
							<li class="page-item">
					          <a href="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalList.do?page=${requestScope.pm.endPage + 1}&${queryParam}" aria-label="Next">▶</a>
					        </li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</section>
		
		<!-- 반려사유 모달 -->
	    <div id="rejectionModal" class="modal" style="display: none">
	      <div class="modal-content">
	        <div class="title-container">
	          <div class="title">반려사유</div>
	          <div class="title-line"></div>
	        </div>
	
	        <!-- 반려사유 -->
	        <div class="rejection-detail shadow"></div>
	
	        <!-- 버튼 영역 -->
	        <div class="button-group">
	          <button class="btn btn-primary" id="closeRejectionModal">확인</button>
	        </div>
	      </div>
	    </div>
	</div>
	
    <div id="footer-container">
    	<%@ include file="/WEB-INF/jsp/cmm/footer.jsp" %>
    </div>

    <script src="${pageContext.request.contextPath}/js/rejection.js"></script>
    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
	
	// 반려사유 팝업
	function openRejectionModalClick(e) {
		const rejectionReason = e.target.attributes["data-reason"].value;
		const rejectionDetail = document.querySelector(".rejection-detail");
		rejectionDetail.textContent = rejectionReason;		
	}
	document.querySelector("#openRejectionModal").addEventListener("click", openRejectionModalClick);
    </script>
	
</body>
</html>
