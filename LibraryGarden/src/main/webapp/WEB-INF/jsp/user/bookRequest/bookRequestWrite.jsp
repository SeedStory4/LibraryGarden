<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>희망도서 신청</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/user/userHeader.jsp"/>

	<c:if test="${not empty msg}">
	    <script>
	        alert("${msg}");
	    </script>
	</c:if>
	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">희망도서 신청</h2>
			
			<div class="contents">
				<c:set var="queryParam" value="keyword=${requestScope.pm.scri.keyword}&searchType=${requestScope.pm.scri.searchType}"></c:set>
				<div class="book-list pt-0">
				<form action="${pageContext.request.contextPath}/user/bookRequest/bookRequestWrite.do">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="title" selected>제목</option>
							<option value="author">저자</option>
						</select>
						<input type="text" class="shadow w-720"  name="keyword" value="">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
				</form>
					<div class="table">
						<table>
							<colgroup>
								<col width="8%">
								<col width="9%">
								<col>
								<col width="16%">
								<col width="15%">
								<col width="13%">
								<col width="12%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>출판일</th>
									<th>신청</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${empty blist}">
								<tr>
  									<td colspan="7" style="text-align:center;">검색어를 입력하세요.</td> <!-- lbdist가 없으면 메시지 출력 -->  
								</tr>
								</c:when> 
								<c:otherwise>
							    <c:forEach items="${requestScope.blist}" var="bv" varStatus="status">
								<tr>
									<td>${(requestScope.pm.scri.page - 1) * requestScope.pm.scri.perPageNum + status.index + 1}</td>
									<td><img src="${bv.coverImg}" alt="${bv.title}"></td>
									<td>${bv.title}</td>
									<td>${bv.author}</td>
									<td>${bv.publisher}</td>
									<td>${fn:replace(bv.publishedYear, '-', '.')}</td>
									<td>
								        <button type="button" class="btn btn-small btn-primary request-btn"
								            data-isbn="${bv.isbn}"
								            data-searchtype="${pm.scri.searchType}"
								            data-keyword="${pm.scri.keyword}"
								            data-page="${pm.scri.page}">
								            신청
								        </button>
									</td>
								</tr>
								</c:forEach>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<c:choose>
							<c:when test="${empty blist}">
 									 <!-- blist가 없으면 출력 안함 -->  
							</c:when> 
							<c:otherwise>
						<ul class="paging flex w-270 justify-spacebtween">
							<c:if test="${requestScope.pm.prev == true}">
							<li>
					          <a href="${pageContext.request.contextPath}/user/bookRequest/bookRequestWrite.do?page=${requestScope.pm.startPage - 1}&${queryParam}" aria-label="Previous">◀</a>
					        </li>
							</c:if> 
							
					        <c:forEach var="i" begin="${requestScope.pm.startPage}" end="${requestScope.pm.endPage}" step="1">
					        <li><a class="<c:if test="${i == requestScope.pm.scri.page}">on</c:if>" href="${pageContext.request.contextPath}/user/bookRequest/bookRequestWrite.do?page=${i}&${queryParam}">${i}</a></li>
					        </c:forEach>
					        
					        <c:if test="${requestScope.pm.next == true && requestScope.pm.endPage > 0}">
							<li class="page-item">
					          <a href="${pageContext.request.contextPath}/user/bookRequest/bookRequestWrite.do?page=${requestScope.pm.endPage + 1}&${queryParam}" aria-label="Next">▶</a>
					        </li>
							</c:if>
						</ul>	
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<!-- 푸터가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
	<script>
	$(document).ready(function() {
	    // 신청 버튼 클릭
	    $('.request-btn').click(function() {
	        // 데이터 가져오기
	        const isbn = $(this).data('isbn');
	        const searchType = $(this).data('searchtype');
	        const keyword = $(this).data('keyword');
	        const page = $(this).data('page');
	
	        // 보낼 데이터 JSON 만들기
	        const requestData = {
	        	isbn: isbn,
	            searchType: searchType,
	            keyword: keyword,
	            page: page
	        };
	
	        // 확인창 띄우기
	        if (confirm('정말 신청하시겠습니까?')) {
	            // Ajax로 POST 요청
	            $.ajax({
	                url: '${pageContext.request.contextPath}/user/bookRequest/bookRequestWriteAction.do',
	                type: 'POST',
	                contentType: 'application/json',
	                data: JSON.stringify(requestData),
	                success: function(response) {
	                    alert('신청이 완료되었습니다!');
	                    // 성공하면 현재 검색 유지하고 다시 검색 페이지로
	                    location.href = '${pageContext.request.contextPath}/user/bookRequest/bookRequestWrite.do?searchType=' + searchType + '&keyword=' + encodeURIComponent(keyword) + '&page=' + page;
	                },
	                error: function() {
	                    alert('신청 중 오류가 발생했습니다. 다시 시도해주세요.');
	                }
	            });
	        }
	    });
	});
	</script>
    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>

</body>
</html>
