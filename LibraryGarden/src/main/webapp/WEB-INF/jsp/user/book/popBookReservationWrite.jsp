<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Reservation PopUp</title>

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.css"
    />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/locales/ko.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/reservation.css">
  </head>
  <body>
  <!-- 헤더가 로드될 부분 -->
	<jsp:include page="/user/userHeader.do" />
    <!-- 예약 버튼 -->
    <button id="openModal">도서 예약</button>

    <!-- 도서 예약 모달 -->
    <div id="modal" class="modal">
      <div class="modal-content">
        <div class="title-container">
          <div class="title">도서예약</div>
          <div class="title-line"></div>
        </div>
        <p class="notice">
          ※ 예약일 전 도서가 미반납 될 경우 대출이 불가할 수 있습니다.
        </p>

       <label class="custom-checkbox flex-end-center font-673D31-13">
							<input type="checkbox" class="terms-checkbox">
							<span class="checkmark "></span> 동의합니다.
						</label>

        <!-- 캘린더 -->
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

        <!-- 예약 날짜 표시 -->
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

    <script src="<%= request.getContextPath() %>/js/reservation.js"></script>
    <!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
  </body>
</html>
