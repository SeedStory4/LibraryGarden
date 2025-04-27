<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>희망도서 상세</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
<style type="text/css">
.between {justify-content: space-evenly;}
</style>

</head>
<body>

	<!-- 헤더가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<c:if test="${not empty msg}">
	    <script>
	        alert("${msg}");
	    </script>
	</c:if>
	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<div class="section-title draft-title">희망도서 상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<c:set var="rq" value="${requestScope.rq}" />
				<div class="draft-content ml-28">
					<img src="${rq.coverImg}" alt="${rq.title}"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title self-start">● 제목<c:choose><c:when test="${not empty rq.originalTitle}"> / 원제</c:when><c:otherwise> </c:otherwise></c:choose></span> <span class="info-content max-w-600">${rq.title}<c:choose><c:when test="${not empty rq.originalTitle}"> / ${rq.originalTitle}</c:when><c:otherwise> </c:otherwise></c:choose></span>
						</p>
						<p>
							<span class="info-title">● 부제</span> 
							<span class="info-content">
								<c:choose>
									<c:when test="${not empty rq.subTitle}">
									${rq.subTitle}
								  	</c:when>
									<c:otherwise>
								    -
								    </c:otherwise>
								</c:choose>
							</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span class="info-content">${rq.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span class="info-content">${rq.publisher}</span>
						</p>
						<p>
							<span class="info-title">● 출판일</span> <span class="info-content">${fn:replace(rq.publishedYear, '-', '.')}</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">${rq.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">${rq.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">${rq.sizeWidth}mm * ${rq.sizeHeight}mm / ${rq.weight}g / <c:choose><c:when test="${not empty rq.category}">${rq.category}</c:when><c:otherwise> - </c:otherwise></c:choose></span>
						</p>
					</div>
					<c:choose>
					  <c:when test="${rq.status eq '신청대기'}">
					    <button class="request-status-btn" >신청대기</button>
					  </c:when>
					  <c:when test="${rq.status eq '신청중'}">
					    <button class="request-status-btn bg-blue">신청중</button>
					  </c:when>
					  <c:when test="${rq.status eq '신청완료'}">
					    <button class="request-status-btn bg-green">신청완료</button>
					  </c:when>
					  <c:when test="${rq.status eq '신청반려'}">
					    <button class="request-status-btn bg-red">신청반려</button>
					  </c:when>
					</c:choose>		
				</div>

				<p class="description-title ml-28">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>
							${rq.introduction}
						</p>
					</div>
				</div>

				<div class="request-info between">
					<!-- 신청자 -->
					<div class="request-info-group ">
						<span class="request-info-title">신청자</span> <span
							class="request-info-content">${rq.name}(${rq.userNumber})</span>
					</div>

					<!-- 신청일 -->
					<div class="request-info-group ">
						<span class="request-info-title">신청일</span> <span
							class="request-info-content">${fn:replace(rq.regDate, '-', '.')}</span>
					</div>
				</div>


				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mg-top">
					<button class="draft-btn-small btn-list" onclick="location.href='${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do'">목록</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

	<script>
		// select2
		$(document).ready(function() {
			$('.js-example-basic-single').select2();
		});
		
		document.addEventListener("DOMContentLoaded", function () {
		    const selectBox = document.querySelector(".js-example-basic-single");
		    const textArea = document.querySelector(".reject-reason-input");

		    // select2 변경 감지 이벤트
		    $('.js-example-basic-single').on("change", function () {
		        let selectedValue = $(this).val();
		        console.log("선택된 값:", selectedValue); // 값 확인 로그

		        if (selectedValue === "신청반려") { 
		            textArea.style.opacity = "1";
		            textArea.style.pointerEvents = "auto"; // 클릭 가능
		        } else {
		            textArea.style.opacity = "0";
		            textArea.style.pointerEvents = "none"; // 클릭 방지
		        }
		    });
		});
    </script>	
  
</body>
</html>