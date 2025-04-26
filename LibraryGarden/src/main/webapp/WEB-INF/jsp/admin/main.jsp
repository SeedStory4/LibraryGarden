<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>
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

	<div class="wrapper">
		<div class="inner">
			<!-- 희망도서목록 -->
			<section class="section section-bordered">
				<div class="section-title">
					희망도서목록 <span class="section-add"><a href="<%= request.getContextPath() %>/admin/bookRequest/bookRequestList.do">＋</a></span>
				</div>
				<div class="list">
					<div class="table">
						<table class="no-border-y">
							<colgroup>
								<col width="6%">
								<col width="25%">
								<col width="15%">
								<col width="15%">
								<col width="15%">
								<col width="14%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청일</th>
									<th>상태</th>
								</tr>
							</thead>
								<thead>
								  <c:forEach var="req" items="${requestList}" varStatus="status">
								    <tr>
								      <td>${status.index + 1}</td>
								      <td>${req.title}</td>
								      <td>${req.author}</td>
								      <td>${req.publisher}</td>
								      <td>${req.name}</td>
								      <td>${fn:substring(req.regDate, 0, 10)}</td>
								      <td class=
										<c:if test="${req.status eq '신청중'}">"blue"</c:if>
										<c:if test="${req.status eq '신청완료'}">"green"</c:if>
										<c:if test="${req.status eq '신청반려'}">"red"</c:if>
										<c:if test="${req.status eq '신청대기'}">"orange"</c:if>
									>${req.status}</td>
								    </tr>
								  </c:forEach>
							</tbody>
							</table>
						</div>
					</div>
			</section>

			<!-- 결재관리목록 -->
			<section class="section section-bordered">
				<div class="section-title">
					결재관리목록 <span class="section-add"><a href="<%= request.getContextPath() %>/admin/approval/approvalList.do">＋</a></span>
				</div>
				<div class="list">
					<div class="table">
						<table class="no-border-y">
							<colgroup>
								<col width="6%">
								<col width="25%">
								<col width="15%">
								<col width="15%">
								<col width="15%">
								<col width="14%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청일</th>
									<th>상태</th>
								</tr>
							</thead>
							<thead>
								<c:forEach var="app" items="${approvalList}" varStatus="status">
								    <tr>
								        <td>${status.index + 1}</td>
								        <td>${app.title}</td>
								        <td>${app.author}</td>
								        <td>${app.publisher}</td>
								        <td>${app.name}</td>
								        <td>${fn:substring(app.regDate, 0, 10)}</td>
								        <td class=
										<c:if test="${app.status eq '대기'}">"blue"</c:if>
										<c:if test="${app.status eq '승인'}">"green"</c:if>
										<c:if test="${app.status eq '반려'}">"red"</c:if>
									>${app.status}</td>
								    </tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</section>
		</div>
	</div>


	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp" />
	
	<!-- <script>
		window.addEventListener("DOMContentLoaded", () => {
		    const tableBody = document.getElementById("requestTableBody");
		    const rows = Array.from(tableBody.rows);
		    rows.reverse(); // 순서를 반대로
	
		    // 기존 행 제거 후 역순으로 다시 추가
		    rows.forEach(row => {
		        tableBody.appendChild(row);
		    });
		});
	</script> -->
	
	
</body>
</html>