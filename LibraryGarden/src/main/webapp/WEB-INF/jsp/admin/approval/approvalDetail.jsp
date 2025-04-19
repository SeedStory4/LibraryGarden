<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사서 기안 상세</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<div class="section-title draft-title">기안 상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<div class="draft-content ml-28">
					<img src="${requestScope.bv.coverImg}" alt="${requestScope.bv.title}"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title">● 제목<c:if test="${not empty requestScope.bv.originalTitle}"> / 원제</c:if></span>
							<span class="info-content">${requestScope.bv.title}<c:if test="${not empty requestScope.bv.originalTitle}"> / ${requestScope.bv.originalTitle}</c:if></span>
						</p>
						<p>
							<span class="info-title">● 부제</span>
							<span class="info-content">
								<c:choose>
									<c:when test="${not empty requestScope.bv.subtitle}">${requestScope.bv.subtitle}</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span class="info-content">${requestScope.bv.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사(출판일)</span> <span class="info-content">${requestScope.bv.publisher}(${fn:replace(requestScope.bv.publishedYear, '-', '.')})</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">${requestScope.bv.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">${requestScope.bv.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span>
							<span class="info-content">${requestScope.bv.sizeWidth}mm * ${requestScope.bv.sizeHeight}mm / ${requestScope.bv.weight}g / ${requestScope.bv.category}</span>
						</p>
						<p>
							<span class="info-title">● 정가</span> <span class="info-content price"></span>
						</p>
					</div>
				</div>

				<p class="description-title ml-28">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>${requestScope.bv.introduction}</p>
					</div>
				</div>

				<!-- 버튼 -->
				<form name="frm" method="post">
					<div class="draft-actions">						
						<c:if test="${requestScope.av.status eq \"대기\" && sessionScope.loginUser.uidx == requestScope.av.uidx}">
							<a href="${pageContext.request.contextPath}/admin/approval/${requestScope.av.aidx}/approvalModify.do" class="draft-btn-small btn-submit flex justify-center align-center">수정</a>
							<button type="button" class="draft-btn-small btn-cancel" onClick="del()">삭제</button>
						</c:if>
						<a href="${pageContext.request.contextPath}/admin/approval/approvalList.do" class="draft-btn-small btn-list flex align-center justify-center">목록</a>
						<c:if test="${sessionScope.loginUser.role eq \"도서관장\"}">
							<c:if test="${requestScope.av.status ne \"승인\"}">
								<button type="button" class="draft-btn-small btn-submit" onClick="approval()">승인</button>
							</c:if>
							<button type="button" class="draft-btn-small btn-cancel openRejectionModal"
								<c:if test="${requestScope.av.status eq \"반려\"}"> style="width: 190px"</c:if>>
								반려
								<c:if test="${requestScope.av.status eq \"반려\"}"> 사유 변경</c:if>
							</button>
						</c:if>
					</div>
				</form>
			</section>
			
			<!-- 반려사유 모달 -->
		    <div id="rejectionModal" class="modal" style="display: none">
		      <div class="modal-content">
		        <div class="title-container">
		          <div class="title">반려사유</div>
		          <div class="title-line"></div>
		        </div>
		
		        <!-- 반려사유 -->
		        <textarea id="rejectionReason" placeholder="반려 사유를 입력해주세요.">${requestScope.av.rejectionReason}</textarea>
		
		        <!-- 버튼 영역 -->
		        <div class="button-group">
		        	<button type="button" class="btn btn-primary" id="confirmRejection">확인</button>
		        	<button type="button" class="btn btn-cancel" id="closeRejectionModal">취소</button>
		        </div>
		      </div>
		    </div>
		</div>
	</div>

	<!-- 푸터가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
	
	<script>
		// 3자리마다 콤마(,)를 입력
		function addComma(str) { 
		  return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		}
		const price = document.querySelector(".price");
		price.innerText = addComma("${requestScope.bv.price}") + "원";
		
		// 게시글 삭제
		function del() {
	    	
	        let fm = document.frm;
			let ans = confirm("삭제하시겠습니까?");
		  	  if (ans == true) {
				  fm.action="${pageContext.request.contextPath}/admin/approval/${requestScope.av.aidx}/approvalDeleteAction.do";
				  fm.method="post";
				  fm.submit();
			}
			
			return;
		}
		
		// 메세지
		const msg = "${requestScope.msg}";
		if (msg != null && msg != "") {
			alert(msg);
		}
		
		// 모달 열기
		const openRejectionModal = document.querySelector(".openRejectionModal");
		const rejectionTextarea = document.getElementById("rejectionReason");
		if(openRejectionModal != null) {
			const modal = document.getElementById("rejectionModal");
			openRejectionModal.addEventListener("click", function () {
				modal.style.display = "flex";
			});
			
			// 모달 닫기
			const closeBtn = document.getElementById("closeRejectionModal");
			closeBtn.addEventListener("click", function () {
				modal.style.display = "none";
			});
			
			// 확인 버튼 클릭 시 입력값 출력
			const confirmBtn = document.getElementById("confirmRejection");
			confirmBtn.addEventListener("click", function () {
				const rejectionReason = rejectionTextarea.value;
				if (rejectionReason.trim() === "") {
			    	alert("반려 사유를 입력해주세요.");
			      	return;
			    }
				
				$.ajax({
					 type: "post",
					 url: "${pageContext.request.contextPath}/admin/approval/${requestScope.av.aidx}/approvalProcessingAction.do",
					 dataType: "json",
					 data: {"rejectionReason" : rejectionReason},
			         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			         success: function(result) {  // 성공
						// alert("전송성공");
				        	
				       	// 모달 닫기
						modal.style.display = "none";
						alert("반려되었습니다.");
						
						// 반려 사유 변경
						rejectionTextarea.value = result.rejectionReason;

					 	// 반려 버튼 변경
						const rejectionBtn = document.querySelector(".draft-actions .btn-cancel");
						rejectionBtn.style.width = "190px";
						rejectionBtn.innerText = "반려 사유 변경";
						
						// 반려 버튼 앞에 승인 버튼 생성(insertAdjacentHTML(position, htmlString)은 문자열을 그대로 DOM에 삽입해 줌. ``가 Node가 아닌 문자열이므로 insertBefore 사용 불가)
						if(document.querySelector(".draft-actions .btn-submit") == null) {
							rejectionBtn.insertAdjacentHTML("beforebegin", `<button type="button" class="draft-btn-small btn-submit" onclick="approval()">승인</button>`);
						}
						
					 },
					 error: function(xhr, status, error) {  // 실패
					 	alert("전송실패");
					    /* console.log("Error Status: " + status);
					    console.log("Error Detail: " + error);
					    console.log("Response: " + xhr.responseText); */
					 }
				});
			});
		}
		
		// 승인
		function approval() {
			let ans = confirm("승인하시겠습니까?");
			if (ans == true) {
				$.ajax({
					 type: "post",
					 url: "${pageContext.request.contextPath}/admin/approval/${requestScope.av.aidx}/approvalProcessingAction.do",
					 dataType: "json",
			         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				         success: function(result) {  // 성공
						 // alert("전송성공");
				        
					 	 alert("승인되었습니다.");
					 	 
					 	 // 반려 사유 삭제
						 rejectionTextarea.value = "";

						 // 반려 버튼 변경
						 const rejectionBtn = document.querySelector(".draft-actions .btn-cancel");
						 rejectionBtn.style.width = "100px";
						 rejectionBtn.innerText = "반려";
						 
					 	 // 승인 버튼 삭제
						 const acceptionBtn = document.querySelector(".draft-actions .btn-submit");
						 acceptionBtn.remove();
					 },
					 error: function(xhr, status, error) {  // 실패
					 	alert("전송실패");
					    /* console.log("Error Status: " + status);
					    console.log("Error Detail: " + error);
					    console.log("Response: " + xhr.responseText); */
					 }
				});
			}
		}
	</script>
</body>
</html>