<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 도서관리 상세</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>

<body>

	<!-- 헤더가 로드될 부분 -->
    <div id="header-container">
    	<%@ include file="/WEB-INF/jsp/admin/adminHeader.jsp" %>
    </div>

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
					<div class="section-title draft-title">도서관리 상세</div>
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
							<span class="info-title">● 저자</span> <span class="info-content">${lbd.author}</span>
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
							<span class="info-title">● 서적정보</span> <span class="info-content">${lbd.sizeWidth}mm * ${lbd.sizeHeight}mm / ${lbd.weight}g / <c:choose><c:when test="${not empty lbd.category}">${lbd.category}</c:when><c:otherwise> - </c:otherwise></c:choose></span>
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
					<button class="draft-btn-small btn-submit">예약</button>
					
					<c:choose>
				    <c:when test="${lbd.status eq '대출중' or lbd.status eq '예약대기'}">
				        <button class="draft-btn-small btn-submit"
				                onclick="UnableToModify()">
				            수정
				        </button>
				        <button class="draft-btn-small btn-cancel"
				                onclick="UnableToDelete()">
				            삭제
				        </button>
				    </c:when>
				
				    <c:otherwise>
				        <button class="draft-btn-small btn-submit"
				                onclick="location.href='${pageContext.request.contextPath}/admin/book/${lbd.lbidx}/bookModify.do'">
				            수정
				        </button>
				        <button class="draft-btn-small btn-cancel"
				                onclick="confirmDelete('${pageContext.request.contextPath}/admin/book/${lbd.lbidx}/bookDelete.do')">
				            삭제
				        </button>
				    </c:otherwise>
				    </c:choose>
				    
					<button class="draft-btn-small btn-list" onclick="location.href='${pageContext.request.contextPath}/admin/book/bookList.do'">목록</button>					
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" /> 
	<script>
	    function UnableToModify() { 
			alert("'대출중', '예약대기' 상태 일때는 수정할 수 없습니다")
	    }
	    function UnableToDelete() {
			alert("'대출중', '예약대기' 상태 일때는 삭제할 수 없습니다")
	    }
	    function confirmDelete(deleteUrl) {
	        if (confirm('정말 삭제하시겠습니까?')) {
	            location.href = deleteUrl;
	        }
	    }
	</script>
</body>
</html>
