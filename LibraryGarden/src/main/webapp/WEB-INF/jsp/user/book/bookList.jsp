<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서조회 목록</title>
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
			<h2 class="section-title m-0 normal relative">도서조회 목록</h2>
			
			<div class="contents">
				<div class="book-list pt-0">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="state">
							<option value="title">제목</option>
							<option value="author">저자</option>
						</select>
						<input type="text" class="shadow w-720">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
					<div class="table">
						<table>
							<colgroup>
								<col width="6%">
								<col width="8%">
								<col>
								<col width="10%">
								<col width="10%">
								<col width="15%">
								<col width="12%">
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
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>802.123 한 127 v1</td>
									<td>일반열람실</td>
									<td>2025.03.14</td>
									<td class="blue">대출중</td>
								</tr>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>802.123 한 127 v1</td>
									<td>어린이열람실</td>
									<td>-</td>
									<td class="green">대출가능</td>
								</tr>
								<tr>
									<td>1</td>
									<td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
									<td><a href="#">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>802.123 한 127 v1</td>
									<td>일반열람실</td>
									<td>-</td>
									<td class="orange">예약대기</td>
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
		</section>
	</div>
	
    <div id="footer-container">
    	<%@ include file="/WEB-INF/jsp/cmm/footer.jsp" %>
    </div>

    <script>
	// select2
	$(document).ready(function() {
		$('.js-example-basic-single').select2();
	});
    </script>
	
</body>
</html>
