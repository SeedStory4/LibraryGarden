<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 도서</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

	<!-- 헤더가 로드될 부분 역활(role)에 따른 헤더 변경 -->
	<c:choose>
	  <c:when test="${sessionScope.loginUser.role == '도서관장' || sessionScope.loginUser.role == '사서'}">
	    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>
	  </c:when>
	  <c:otherwise>
	    <jsp:include page="/WEB-INF/jsp/user/userHeader.jsp"/>
	  </c:otherwise>
	</c:choose>

	<c:if test="${not empty msg}">
	    <script>
	        alert("${msg}");
	    </script>
	</c:if>
	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">내 도서</h2>
			
			<div class="contents">
				<p class="loan-info">${uv.name}(${uv.userNumber})님의 현재 대출가능여부는 <span
						class="${loanStatus == '이용가능' ? 'green' : 'red'} bold">"${loanStatus}"</span>입니다.</p>
				<div class="list">
					<ul class="tab flex gap-3">
						<li class="shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageLoanList.do">대출이력</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageReservationList.do">예약관리</a></li>
						<li class="on shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageRequestList.do">도서신청관리</a></li>
					</ul>
					<div class="table">
					<form name="frm">
						<table>
							<colgroup>
								<col width="8%">
								<col>
								<col width="16%">
								<col width="15%">
								<col width="13%">
								<col width="11%">
								<col width="12%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청일</th>
									<th>상태</th>
									<th>취소</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="request" items="${requestList}" varStatus="status">
								<tr>
									<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
									<td>${request.title}</td>
									<td>${request.author}</td>
									<td>${request.publisher}</td>
									<td>${request.regDate}</td>
									<td class=
										<c:if test="${request.status eq '신청중'}">"blue"</c:if>
										<c:if test="${request.status eq '신청완료'}">"green"</c:if>
										<c:if test="${request.status eq '신청대기'}">"orange"</c:if>
										<c:if test="${request.status eq '신청반려'}">"red pointer openRejectionModal" data-reason="${ad.rejectionReason}"</c:if>
									>${request.status}</td>
									<td><c:choose>
										<c:when test='${request.status eq "신청대기"}'>
											<button type="button" onclick="confirmDelete(${pm.scri.page},${request.bidx},${request.rqidx})" class="btn btn-small btn-red">취소</button>
										</c:when>
										<c:otherwise>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>
								<c:if test="${empty requestList}">
									<tr>
										<td colspan="9" style="text-align:center;">신청한 희망도서가 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
						</form>
						<ul class="paging flex w-270 justify-center">
							<c:if test="${requestScope.pm.prev == true}">
							<li>
					          <a href="${pageContext.request.contextPath}/user/myPage/myPageRequestList.do?page=${requestScope.pm.startPage - 1}" aria-label="Previous">◀</a>
					        </li>
							</c:if> 
							
					        <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
					        <li><a class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>" href="${pageContext.request.contextPath}/user/myPage/myPageRequestList.do?page=${i}">${i}</a></li>
					        </c:forEach>
					        
					        <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
							<li class="page-item">
					          <a href="${pageContext.request.contextPath}/user/myPage/myPageRequestList.do?page=${requestScope.pm.endPage + 1}" aria-label="Next">▶</a>
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
	        <div class="rejection-detail shadow">
	
	        </div>
	
	        <!-- 버튼 영역 -->
	        <div class="button-group">
	          <button class="btn btn-primary" id="closeRejectionModal">확인</button>
	        </div>
	      </div>
	    </div>
	</div>
	
	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
	
    <script src="${pageContext.request.contextPath}/js/rejection.js"></script>
    
    <script>
    
	function confirmDelete(page, bidx, rqidx) {
		
		var fm = document.frm;	
		var ans = confirm("저장하시겠습니까?");
		if (ans == true) {
			fm.action='${pageContext.request.contextPath}/user/myPage/deleteRequest.do?page='+ page +'&bidx='+ bidx +'&rqidx='+ rqidx;
			fm.method="post"; 
			fm.submit();
		 }
	}
	</script>
</body>
</html>
