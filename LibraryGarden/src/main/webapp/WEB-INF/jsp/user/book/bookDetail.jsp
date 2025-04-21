<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서상세</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/adminMain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/main.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/6.1.8/locales/ko.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js"></script>
        
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/user/userHeader.jsp"/>

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<div class="section-title draft-title">도서상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<c:set var="lbd" value="${requestScope.lbd}" />
				<div class="draft-content">
					<img src="${lbd.coverImg}" alt="${lbd.title}"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title self-start">● 제목<c:choose><c:when test="${not empty lbd.originalTitle}"> / 원제</c:when><c:otherwise> </c:otherwise></c:choose></span> <span class="info-content max-w-600">${lbd.title}<c:choose><c:when test="${not empty lbd.originalTitle}"> / ${lbd.originalTitle}</c:when><c:otherwise> </c:otherwise></c:choose></span>
						</p>
						<p>
							<span class="info-title">● 부제</span> 
							<span class="info-content">
								<c:choose>
									<c:when test="${not empty lbd.subtitle}">
									${lbd.subtitle}
								  	</c:when>
									<c:otherwise>
								    -
								    </c:otherwise>
								</c:choose>
							</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span class="info-content">${lbd.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span class="info-content">${lbd.publisher}</span>
						</p>
						<p>
							<span class="info-title">● 출판일</span> <span class="info-content">${fn:replace(lbd.publishedYear, '-', '.')}</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">${lbd.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">${lbd.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">${lbd.sizeWidth}mm * ${lbd.sizeHeight}mm / ${lbd.weight}g </span>
						</p>
					</div>
					
					<c:choose>
					  <c:when test="${lbd.status eq '대출중'}">
					    <button class="request-status-btn status-btn-ing" >대출중(~${fn:replace(lbd.dueDate, '-', '.')})</button>
					  </c:when>
					  <c:when test="${lbd.status eq '대출가능'}">
					    <button class="request-status-btn status-btn-ok">대출가능</button>
					  </c:when>
					  <c:when test="${lbd.status eq '예약대기'}">
					    <button class="request-status-btn status-btn-wating">예약대기</button>
					  </c:when>
					  <c:when test="${lbd.status eq '대출불가'}">
					    <button class="request-status-btn status-btn-no">대출불가</button>
					  </c:when>
					</c:choose>
				</div>
				
				<p class="description-title">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>
							${lbd.introduction}
						</p>
					</div>
				</div>

				<hr class="divider">

				<p class="description-title">● 소장정보</p>

				<table class="info-table">
					<thead>
						<tr>
							<th>구분</th>
							<th>청구기호</th>
							<th>자료실</th>
							<th>반납예정일</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${lbd.code}</td>
							<td>${lbd.callName}</td>
							<td>${lbd.location}</td>
							<td>
								<c:choose>
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
										<c:if test="${lbd.status eq '대출중'}">"status-text"</c:if>
										<c:if test="${lbd.status eq '대출가능'}">"status-text-ok"</c:if>
										<c:if test="${lbd.status eq '예약대기'}">"status-text text-orange"</c:if>
										<c:if test="${lbd.status eq '대출불가'}">"status-text text-red" </c:if>
									>
									${lbd.status}
							</td>
						</tr>
					</tbody>
				</table>

				<div class="draft-actions mg-top">
					<button type="button" class="draft-btn-small btn-submit openReservationModal" data-lbidx="${lbd.lbidx}">예약</button>
					<button class="draft-btn-small btn-list"  onclick="location.href='${pageContext.request.contextPath}/user/book/bookList.do'">목록</button>
				</div>
			</section>
		</div>
	</div>
	
<input type="hidden" id="userNumber" value="${userNumber}" />	

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

	<!-- 푸터 로드할 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
	<script>
  		var contextPath = '${pageContext.request.contextPath}';
	</script>
    <!-- reservation.js 불러오기 -->
    <script src="${pageContext.request.contextPath}/js/reservation.js"></script>

</body>
</html>