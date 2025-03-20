<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>희망도서 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

    <div id="header-container">
    	<%@ include file="/WEB-INF/jsp/user/userHeader.jsp" %>
    </div>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal relative">희망도서 목록<button class="btn btn-primary absolute">도서등록</button></h2>
			
			<div class="contents">
				<div class="book-list pt-0">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="state">
							<option value="title">제목</option>
							<option value="author">저자</option>
							<option value="name">신청자</option>
						</select>
						<input type="text" class="shadow w-720">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
					<ul class="tab flex gap-3">
						<li class="on shadow"><a href="#">전체</a></li>
						<li class="shadow"><a href="#">신청대기</a></li>
						<li class="shadow"><a href="#">신청완료</a></li>
						<li class="shadow"><a href="#">신청중</a></li>
						<li class="shadow"><a href="#">신청반려</a></li>
					</ul>
					<div class="table">
						<table>
							<colgroup>
								<col width="8%">
								<col width="9%">
								<col>
								<col width="15%">
								<col width="14%">
								<col width="11%">
								<col width="11%">
								<col width="9%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청날짜</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>노지혜<br>(030709)</td>
									<td>2025.03.14</td>
									<td class="blue">신청중</td>
								</tr>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>노지혜<br>(030709)</td>
									<td>2025.03.14</td>
									<td class="green">신청완료</td>
								</tr>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>노지혜<br>(030709)</td>
									<td>2025.03.14</td>
									<td class="orange">신청대기</td>
								</tr>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>노지혜<br>(030709)</td>
									<td>2025.03.14</td>
									<td class="red pointer" id="openRejectionModal">신청반려</td>
								</tr>
							</tbody>
						</table>
						<ul class="paging flex w-270 justify-spacebtween">
							<li><a href="#">◀</a></li>
							<li><a href="#" class="on">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">▶</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<!-- 반려사유 모달 -->
		    <div id="rejectionModal" class="modal" style="display: none">
		      <div class="modal-content">
		        <div class="title-container">
		          <div class="title">반려사유</div>
		          <div class="title-line"></div>
		        </div>
		
		        <!-- 반려사유 -->
		        <div class="rejection-detail shadow">
		
		        </div>
		
		        <!-- 버튼 영역 -->
		        <div class="button-group">
		          <button class="btn btn-primary" id="closeRejectionModal">확인</button>
		        </div>
		      </div>
		    </div>
		</section>
	</div>
	
    <div id="footer-container">
    	<%@ include file="/WEB-INF/jsp/cmm/footer.jsp" %>
    </div>

    <script src="${pageContext.request.contextPath}/js/rejection.js"></script>
    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>
	
</body>
</html>
