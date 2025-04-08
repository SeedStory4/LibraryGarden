<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>도서예약 등록</title>
    <!-- Select2, FullCalendar 및 사용자 CSS 로드 -->
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.css" />
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/locales/ko.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js"></script>
</head>
<body class="custom-page">

<!-- 헤더가 로드될 부분 -->   
<jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>
    
    <div class="wrapper">
        <section class="section p-0">
            <h2 class="section-title m-0 normal">도서예약 등록</h2>
            <div class="contents">
                <div class="user-number flex gap-20">
                    <label class="flex gap-20">
                        <span>회원번호</span>
                        <input type="text" id="userNumber" name="userNumber" class="w-290 shadow" value="${userNumber}" />
                    </label>
                    <button class="btn btn-primary btn-small number-check" onClick="numberCheck()" type="button">확인</button>
                </div>
                <c:set var="queryParam" value="userNumber=${userNumber}&keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}" />
                <div class="book-list border-top-2 <c:if test='${empty userNumber}'>none</c:if>" id="bookListSection">
                    <form action="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do">
                        <div class="search flex gap-20 justify-center">
                            <select class="js-example-basic-single select shadow" name="searchType">
                                <option value="title">제목</option>
                                <option value="author">저자</option>
                            </select>
                            <input type="hidden" name="userNumber" value="${userNumber}" />
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
                                <c:forEach items="${requestScope.lblist}" var="lbd" varStatus="status">
                                    <tr>
                                        <td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
                                        <td><img src="${lbd.coverImg}" alt="${lbd.title}"></td>
                                        <!-- 제목 클릭 시 모달을 열도록 클래스 추가 -->
                                        <td><a href="#" class="openReservationModal" data-lbidx="${lbd.lbidx}">${lbd.title}</a></td>
                                        <td>${lbd.author}</td>
                                        <td>${lbd.publisher}</td>
                                        <td>${lbd.callName}</td>
                                        <td>${lbd.location}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty lbd.dueDate}">
                                                    <c:if test="${lbd.status eq '대출중'}">${lbd.dueDate}</c:if>
                                                    <c:if test="${lbd.status ne '대출중'}">-</c:if>
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="<c:if test='${lbd.status eq "대출중"}'>blue</c:if>
                                                   <c:if test='${lbd.status eq "대출가능"}'>green</c:if>
                                                   <c:if test='${lbd.status eq "예약대기"}'>orange</c:if>
                                                   <c:if test='${lbd.status eq "대출불가"}'>red</c:if>">
                                            ${lbd.status}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <ul class="paging flex w-270 justify-center">
                            <c:if test="${requestScope.pm.prev == true}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${requestScope.pm.startPage - 1}&${queryParam}" aria-label="Previous">◀</a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
                                <li>
                                    <a class="<c:if test='${i == requestScope.pm.scri.page}'>on</c:if>"
                                       href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${i}&${queryParam}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
                                <li class="page-item">
                                    <a href="${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?page=${requestScope.pm.endPage + 1}&${queryParam}" aria-label="Next">▶</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
    </div>
    
    <!-- 예약 모달 -->
    <div id="reservationModal" class="modal" style="display: none;">
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
                <button id="reserveBtn" disabled>예약</button>
                <button id="closeModal">취소</button>
            </div>
        </div>
    </div>
    
    <!-- 푸터가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
    <!-- reservation.js 불러오기 -->
    <script src="${pageContext.request.contextPath}/js/reservation.js"></script>
<script>
$(document).ready(function() {
    // select2 초기화
    $('.js-example-basic-single').select2();

    // 제목 클릭 시 모달 열기 + 예약현황 AJAX 호출
	$('.openReservationModal').on('click', function (e) {
	  e.preventDefault();
	
	  const $row = $(this).closest('tr');
	  const statusText = $row.find('td').last().text().trim();
	  if (statusText === "대출불가") {
	    alert("예약이 불가한 도서입니다.");
	    return;
	  }
	
	  const lbidx = $(this).data('lbidx');
	  const userNumber = $('#userNumber').val();
	
	  $.ajax({
	    url: '${pageContext.request.contextPath}/admin/bookReservation/getReservedDates.do',
	    type: 'GET',
	    data: {
	      lbidx: lbidx,
	      userNumber: userNumber
	    },
	    success: function (data) {
	      window.disabledDates = data.map(d => d.date);
	      window.disabledReasons = {};
	      data.forEach(d => {
	        window.disabledReasons[d.date] = d.reason;
	      });
	
	      $('#reservationModal').show();
	
	      setTimeout(() => {
	        if (window.myCalendar) {
	          if (!window.isCalendarRendered) {
	            window.myCalendar.render(); // ✅ 최초 1회만 render
	            window.isCalendarRendered = true;
	          }
	          window.myCalendar.updateSize();
	          window.myCalendar.refetchEvents();
	        }
	      }, 200);
	    },
	    error: function () {
	      alert("예약 현황을 불러오지 못했습니다.");
	    }
	  });
	});


    $('#closeModal').on('click', function() {
        window.selectedDate = null;
        document.getElementById("selectedDate").textContent = "선택 없음";
        document.querySelectorAll(".fc-day-selected").forEach((el) => {
            el.classList.remove("fc-day-selected");
        });
        $('#reservationModal').hide();
    });
});

// 회원번호 확인 함수
function numberCheck() {
    let userNumber = $("#userNumber").val();
    if (!userNumber) {
        alert("회원번호를 입력해주세요.");
        return;
    }
    location.href = "${pageContext.request.contextPath}/admin/bookReservation/bookReservationWrite.do?userNumber=" + userNumber;
}
</script>

</body>
</html>