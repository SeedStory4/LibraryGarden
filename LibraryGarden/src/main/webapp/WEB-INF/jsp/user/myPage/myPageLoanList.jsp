<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 도서</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

    <jsp:include page="/user/userHeader.do" />

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">내 도서</h2>
			
			<div class="contents">
				<p class="loan-info">김시연(017147)님의 현재 대출가능여부는 <span class="green bold">"이용가능"</span>입니다.</p>
				<div class="list">
					<ul class="tab flex gap-3">
						<li class="on shadow"><a href="#">대출이력</a></li>
						<li class="shadow"><a href="#">예약관리</a></li>
						<li class="shadow"><a href="#">도서신청관리</a></li>
					</ul>
					<div class="table">
						<table>
							<colgroup>
								<col width="6%">
								<col>
								<col width="10%">
								<col width="10%">
								<col width="12%">
								<col width="12%">
								<col width="12%">
								<col width="9%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>대출일</th>
									<th>반납예정일</th>
									<th>반납일</th>
									<th>상태</th>
									<th>연장</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td class="blue">대출중</td>
									<td><button class="btn btn-small btn-secondary1">연장</button></td>
								</tr>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td class="green">반납완료</td>
									<td></td>
								</tr>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td>2025.03.14</td>
									<td class="red">연체반납</td>
									<td></td>
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
	
    <jsp:include page="/cmm/footer.do" />
	
</body>
</html>
