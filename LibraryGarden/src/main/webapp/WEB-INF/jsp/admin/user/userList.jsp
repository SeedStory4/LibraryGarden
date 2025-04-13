<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

    <div id="header-container">
    	<!-- 헤더가 로드될 부분 역활(role)에 따른 헤더 변경 -->
	<c:choose>
	  <c:when test="${sessionScope.loginUser.role == '도서관장' || sessionScope.loginUser.role == '사서'}">
	    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>
	  </c:when>
	  <c:otherwise>
	    <jsp:include page="/WEB-INF/jsp/user/userHeader.jsp"/>
	  </c:otherwise>
	</c:choose>
    </div>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">회원목록</h2>
			
			<div class="contents">
				<div class="list pt-0">
				<form action = "${pageContext.request.contextPath}/admin/user/userList.do" method ="get">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="searchType">
							<option value="name" ${param.searchType == 'name' ? 'selected' : ''}>이름</option>
							<option value="id" ${param.searchType == 'id' ? 'selected' : ''}>아이디</option>
							<option value="role" ${param.searchType == 'role' ? 'selected' : ''}>권한</option>
						</select>
						<input type="text" name = "keyword" class="shadow w-720" value="${param.keyword}">						
						<button type="submit" class="btn btn-primary btn-small">검색</button>
					</div>
					</form>
					
					<div class="table">
						<table>
							<colgroup>
								<col width="8%">
								<col width="15%">
								<col>
								<col width="20%">
								<col width="15%">
								<col width="15%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>이름</th>
									<th>아이디</th>
									<th>휴대전화번호</th>
									<th>권한</th>
									<th>가입일</th>
								</tr>
							</thead>
							<tbody>
							  <c:forEach var = "user" items = "${userList}" varStatus = "status">
							    <tr>
							      <td>${pageMaker.totalCount - ((cri.page - 1) * cri.perPageNum) - status.index}</td> <!-- 역순으로 번호 출력 -->
							      <td>${user.name}</td>
							      <td>${user.id}</td>
							      <td>${user.phone}</td>
							      <td>${user.role}</td>
							      <td>${user.date}</td>
							    </tr>
							  </c:forEach>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-center">
							<!-- 이전 페이지 링크 -->
						    <c:if test="${pageMaker.prev}">
						        <li>
						            <a href="?page=${pageMaker.startPage - 1}&perPageNum=${cri.perPageNum}&searchType=${cri.searchType}&keyword=${cri.keyword}">◀</a>
						        </li>
						    </c:if>
						
						    <!-- 페이지 번호 목록 -->
						    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
						        <li>
						            <a href="?page=${pageNum}&perPageNum=${cri.perPageNum}&searchType=${cri.searchType}&keyword=${cri.keyword}"
						               class="${cri.page == pageNum ? 'on' : ''}">
						                ${pageNum}
						            </a>
						        </li>
						    </c:forEach>
						
						    <!-- 다음 페이지 링크 -->
						    <c:if test="${pageMaker.next}">
						        <li>
						            <a href="?page=${pageMaker.endPage + 1}&perPageNum=${cri.perPageNum}&searchType=${cri.searchType}&keyword=${cri.keyword}">▶</a>
						        </li>
						    </c:if>
						</ul>	
					</div>
				</div>
			</div>
		</section>
	</div>
	
    <div id="footer-container">
    	<!-- 푸터 로드할 부분 -->
		<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
    </div>

    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>
	
</body>
</html>
