<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
    	<%@ include file="/WEB-INF/jsp/user/userHeader.jsp" %>
    </div>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">회원목록</h2>
			
			<div class="contents">
				<div class="list pt-0">
					<div class="search flex gap-20 justify-center">
						<select class="js-example-basic-single select shadow" name="state">
							<option value="name">이름</option>
							<option value="id">아이디</option>
							<option value="role">권한</option>
						</select>
						<input type="text" class="shadow w-720">						
						<button class="btn btn-primary btn-small">검색</button>
					</div>
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
								<tr>
									<td>1</td>
									<td>홍길동</td>
									<td>salfhkwehru2423</td>
									<td>010-4413-1345</td>
									<td>도서관장</td>
									<td>2025.03.14</td>
								</tr>
								<tr>
									<td>1</td>
									<td>홍길동</td>
									<td>salfhkwehru2423</td>
									<td>010-4413-1345</td>
									<td>사서서</td>
									<td>2025.03.14</td>
								</tr>
								<tr>
									<td>1</td>
									<td>홍길동</td>
									<td>salfhkwehru2423</td>
									<td>010-4413-1345</td>
									<td>일반 회원</td>
									<td>2025.03.14</td>
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
