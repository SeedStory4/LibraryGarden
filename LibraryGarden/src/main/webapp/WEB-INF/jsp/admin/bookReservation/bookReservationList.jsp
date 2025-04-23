<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서예약 목록</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/locales/ko.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js"></script>
</head>
<body class="custom-page">

    <!-- 헤더가 로드될 부분 -->   
	<jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal relative">도서예약 목록<button class="btn btn-primary absolute" onclick="location.href = contextPath + '/user/bookReservation/bookReservationWrite.do'">도서예약등록</button></h2>
			
			<div class="contents">
			<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}" />
				<div class="book-list pt-0">
				<form action="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="title">제목</option>
							<option value="author">저자</option>
							<option value="name">예약자</option>
						</select>
						<input type="text" class="shadow w-720" name="keyword" value="">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
				</form>
					<ul class="tab flex gap-3">
						<li class="shadow <c:if test="${empty requestScope.filter}">on</c:if>"><a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?${queryParam}">전체</a></li>
						<li class="shadow <c:if test="${requestScope.filter eq '예약중'}">on</c:if>"><a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?status=예약중&${queryParam}">예약중</a></li>
						<li class="shadow <c:if test="${requestScope.filter eq '수령완료'}">on</c:if>"><a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?status=수령완료&${queryParam}">수령완료</a></li>
						<li class="shadow <c:if test="${requestScope.filter eq '예약취소'}">on</c:if>"><a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?status=예약취소&${queryParam}">예약취소</a></li>
					</ul>
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
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>청구기호</th>
									<th>예약자</th>
									<th>예약날짜</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>										
							<c:forEach items="${requestScope.rlist}" var="r" varStatus="status">
								<tr>
									<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
									<td><img src="${r.coverImg}" alt="${r.title}"></td>
									    <td>
									      <a href="#"
									         class="openReservationModifyModal"
									         data-ridx="${r.ridx}"
									         data-lbidx="${r.lbidx}"
									         data-usernumber="${r.userNumber}"
									         data-pickupdate="${r.pickupDate}"
									         data-status="${r.status}">
									        ${r.title}
									      </a>
									    </td>
									<td>${r.author}</td>
									<td>${r.publisher}</td>
									<td>${r.callName}</td>
									<td>${r.name}<br>(${r.userNumber})</td>
									<td>${r.pickupDate}</td>
									<td class=
										<c:if test="${r.status eq '예약중'}">"blue"</c:if>
										<c:if test="${r.status eq '수령완료'}">"green"</c:if>
										<c:if test="${r.status eq '예약취소'}">"red"</c:if>
									>${r.status}
										<c:if test="${r.status eq '예약중'}">
											    <button class="btn btn-small btn-red mt-5" 
            										onclick="cancelReservation(${r.ridx})">취소</button>
										</c:if>									
									</td>
								</tr>
								</c:forEach>	
							<c:if test="${empty rlist}">
								<tr>
									<td colspan="9" style="text-align:center;">예약된 도서가 없습니다.</td>
								</tr>
							</c:if>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-center">
							<c:if test="${requestScope.pm.prev == true}">
							<li>
					          <a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?page=${requestScope.pm.startPage - 1}&${queryParam}" aria-label="Previous">◀</a>
					        </li>
							</c:if> 
							
					        <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
					        <li><a class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>" href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?page=${i}&${queryParam}">${i}</a></li>
					        </c:forEach>
					        
					        <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
							<li class="page-item">
					          <a href="${pageContext.request.contextPath}/user/bookReservation/bookReservationList.do?page=${requestScope.pm.endPage + 1}&${queryParam}" aria-label="Next">▶</a>
					        </li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	
    <!-- 수정 모달 -->
    <div id="modifyModal" class="modal" style="display: none;">
        <div class="modal-content">
            <div class="title-container">
                <div class="title">도서예약</div>
                <div class="title-line"></div>
            </div>
            <p class="notice">※ 예약일 전 도서가 미반납 될 경우 대출이 불가할 수 있습니다.</p>
            <label class="custom-checkbox flex-end-center font-673D31-13">
                <input type="checkbox" id="agreeCheck" class="terms-checkbox">
                <span class="checkmark"></span> 동의합니다.
            </label>
            <!-- 캘린더 영역 -->
            <div class="calendar-container">
                <div id="calendar"></div>
                <!-- 예약 상태 표시 -->
                <div class="reservation-status">
                    <div class="status">
                        <span class="status-box available"></span> 예약가능
                    </div>
                    <div class="status">
                        <span class="status-box unavailable"></span> 예약불가
                    </div>
                    <div class="status">
                        <span class="status-box selected"></span> 예약선택
                    </div>
                </div>
            </div>
            <!-- 선택한 예약 날짜 표시 -->
            <p class="reservationDate">
                예약날짜: <span id="selectedDate">선택 없음</span>
            </p>
            <!-- 버튼 영역 -->
            <div class="button-group">
                <button id="modifyBtn" disabled>수정</button>
                <button id="closeModal">취소</button>
            </div>
        </div>
    </div>
	
    <!-- 푸터가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

	<script>
	  $(document).ready(function() {
		    // select2 초기화
		    $('.js-example-basic-single').select2();
		  });
	  
		function cancelReservation(ridx) {
		    if (!confirm("정말로 예약을 취소하시겠습니까?")) {
		        return;
		    }
		    $.ajax({
		        url: '${pageContext.request.contextPath}/user/bookReservation/cancelReservation.do',
		        type: 'POST',
		        data: { ridx: ridx },
		        success: function(response) {
		            if (response.success) {
		                alert(response.message);
		                location.reload(); // 페이지 새로고침으로 목록 갱신
		            } else {
		                alert(response.message);
		            }
		        },
		        error: function() {
		            alert("예약 취소 중 오류가 발생했습니다.");
		        }
		    });
		}
	</script>
	
	<script>
      var contextPath = '${pageContext.request.contextPath}';
    </script>
	
	<script src="${pageContext.request.contextPath}/js/reservationModify.js"></script>
	
</body>
</html>
