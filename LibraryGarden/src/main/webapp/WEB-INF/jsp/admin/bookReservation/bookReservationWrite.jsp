<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서예약 등록</title>
<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

	<jsp:include page="/user/userHeader.do" />

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">도서예약 등록</h2>

			<div class="contents">
				<div class="user-number flex gap-20">
					<label class="flex gap-20"> <span>회원번호</span> <input
						type="text" id="userNumber" name="userNumber" class="w-290 shadow"
						value="${userNumber}" />
					</label>
					<button class="btn btn-primary btn-small number-check"
						onClick="numberCheck()" type="button">확인</button>
				</div>
				<c:set var="queryParam"
					value="userNumber=${userNumber}&keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}" />
				<div class="book-list border-top-2 <c:if test="${empty userNumber}">none</c:if>" id="bookListSection">
					<form
						action="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do">
						<div class="search flex gap-20 justify-center">
							<select class="js-example-basic-single select shadow"
								name="searchType">
								<option value="title">제목</option>
								<option value="author">저자</option>
							</select> <input type="hidden" name="userNumber" value="${userNumber}" />
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
								<col width="10%">
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
								<c:forEach items="${requestScope.lblist}" var="lbd"
									varStatus="status">
									<tr>
										<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
										<td><img src="${lbd.coverImg}" alt="${lbd.title}"></td>
										<td><a href="#">${lbd.title}</a></td>
										<td>${lbd.author}</td>
										<td>${lbd.publisher}</td>
										<td>${lbd.callName}</td>
										<td>${lbd.location}</td>
										<td><c:choose>
												<c:when test="${not empty lbd.dueDate}">
													<c:if test="${lbd.status eq '대출중'}">${lbd.dueDate}</c:if>
													<c:if test="${lbd.status ne '대출중'}">-</c:if>
												</c:when>
												<c:otherwise>
										    -
										  </c:otherwise>
											</c:choose></td>
										<td class=<c:if test="${lbd.status eq '대출중'}">"blue"</c:if>
											<c:if test="${lbd.status eq '대출가능'}">"green"</c:if>
											<c:if test="${lbd.status eq '예약대기'}">"green"</c:if>
											<c:if test="${lbd.status eq '대출불가'}">"red" </c:if>>${lbd.status}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-center">
							<c:if test="${requestScope.pm.prev == true}">
								<li><a
									href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${requestScope.pm.startPage - 1}&${queryParam}"
									aria-label="Previous">◀</a></li>
							</c:if>

							<c:forEach var="i" begin="${requestScope.pm.startPage}"
								end="${requestScope.pm.endPage}" step="1">
								<li><a
									class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>"
									href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${i}&${queryParam}">${i}</a></li>
							</c:forEach>

							<c:if
								test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
								<li class="page-item"><a
									href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${requestScope.pm.endPage + 1}&${queryParam}"
									aria-label="Next">▶</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</div>

	<jsp:include page="/cmm/footer.do" />

	<script>
		// select2
		$(document).ready(function() {
			$('.js-example-basic-single').select2();
		});

		function numberCheck() {
			let userNumber = $("#userNumber").val();

			if (!userNumber) {
				alert("회원번호를 입력해주세요.");
				return;
			}

			// 연체 확인 없이 바로 도서 목록 페이지로 이동
			location.href = "${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?userNumber=" + userNumber;
		}
	</script>

</body>
</html>
