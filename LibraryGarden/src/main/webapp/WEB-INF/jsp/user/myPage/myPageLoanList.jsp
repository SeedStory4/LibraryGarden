<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 도서</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous">
</script>
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

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">내 도서</h2>

			<div class="contents">
				<p class="loan-info">${name}(${userNumber})님의
					현재 대출가능여부는 <span
						class="${loanStatus == '이용가능' ? 'green' : 'red'} bold">"${loanStatus}"</span>입니다.
				</p>
				<div class="list">
					<ul class="tab flex gap-3">
						<li class="on shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageLoanList.do">대출이력</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageReservationList.do">예약관리</a></li>
						<li class="shadow"><a href="${pageContext.request.contextPath}/user/myPage/myPageRequestList.do">도서신청관리</a></li>
					</ul>
					<div class="table">
						<table>
							<colgroup>
								<col width="6%">
								<col>
								<col width="10%">
								<col width="10%">
								<col width="12%">
								<col width="12%">
								<col width="12%">
								<col width="9%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>대출일</th>
									<th>반납예정일</th>
									<th>반납일</th>
									<th>상태</th>
									<th>연장</th>
								</tr>
							</thead>
							
							  <jsp:useBean id="now" class="java.util.Date" scope="page"/>
							
							<tbody>
								<c:forEach var="loan" items="${loanList['loanList']}" varStatus="status">
								  <!-- 문자열 "yyyy-MM-dd" → Date -->
  								  <fmt:parseDate value="${loan.dueDate}" pattern="yyyy.MM.dd" var="dueObj"/>
									<tr>
										<td>${status.index + 1}</td>
										<!-- 순차적인 번호 출력 -->
										<td>${loan.title}</td>
										<td>${loan.author}</td>
										<td>${loan.publisher}</td>
										<!-- BOOKS 테이블에서 가져온 publisher -->
										<td>${loan.loanDate}</td>
										<td>${loan.dueDate}</td>
										<td>${loan.returnDate}</td>
										<td
											class="${loan.status == '대출중' ? 'blue' : loan.status == '연체반납' ? 'red' : 'green'}">
											${loan.status}</td>
										<td>
										  <c:if test="${loan.status == '대출중' and loan.extended != 'Y' and dueObj >= now}">
  											<button class="btn btn-small btn-secondary1" onclick="extendLoan(${loan.lidx})">연장</button>
										  </c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-center">
							<!-- ◀ 이전 페이지 (첫 페이지가 아닐 때만 표시) -->
							<c:if test="${currentPage > 1}">
								<li><a href="?page=${currentPage - 1}">◀</a></li>
							</c:if>

							<!-- 페이지 번호는 항상 표시 -->
							<c:forEach var="i" begin="1" end="${totalPageCount}">
								<li><a href="?page=${i}"
									class="${i == currentPage ? 'on' : ''}">${i}</a></li>
							</c:forEach>

							<!-- ▶ 다음 페이지 (마지막 페이지가 아닐 때만 표시) -->
							<c:if test="${currentPage < totalPageCount}">
								<li><a href="?page=${currentPage + 1}">▶</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
<script>
function extendLoan(lidx) {
    if (!confirm("해당 대출의 반납예정일을 7일 연장하시겠습니까?")) {
        return;
    }
    $.ajax({
        url: "${pageContext.request.contextPath}/user/myPage/extendLoan.do",
        type: "POST",
        data: { lidx: lidx },
        success: function(response) {
            if (response.success) {
                alert(response.message);
                location.reload();
            } else {
                alert(response.message);
            }
        },
        error: function() {
            alert("연장 처리 중 오류가 발생했습니다.");
        }
    });
}
</script>
</body>
</html>
