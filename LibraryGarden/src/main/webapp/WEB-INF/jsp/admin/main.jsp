<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메인</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>
	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/admin/adminHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 희망도서목록 -->
			<section class="section section-bordered">
				<div class="section-title">
					희망도서목록 <span class="section-add">＋</span>
				</div>
				<div class="list">
					<div class="table">
						<table class="no-border-y">
							<colgroup>
								<col width="6%">
								<col width="25%">
								<col width="15%">
								<col width="15%">
								<col width="15%">
								<col width="14%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청일</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">신청중</td>
								</tr>
								<tr>
									<td>2</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">신청중</td>
								</tr>
								<tr>
									<td>3</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">신청중</td>
								</tr>
								<tr>
									<td>4</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">신청중</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</section>

			<!-- 결재관리목록 -->
			<section class="section section-bordered">
				<div class="section-title">
					결재관리목록 <span class="section-add">＋</span>
				</div>
				<div class="list">
					<div class="table">
						<table class="no-border-y">
							<colgroup>
								<col width="6%">
								<col width="25%">
								<col width="15%">
								<col width="15%">
								<col width="15%">
								<col width="14%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청자</th>
									<th>신청일</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">대기</td>
								</tr>
								<tr>
									<td>2</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">대기</td>
								</tr>
								<tr>
									<td>3</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">대기</td>
								</tr>
								<tr>
									<td>4</td>
									<td><a href="#" class="title-link">채식주의자</a></td>
									<td>한강</td>
									<td>창비</td>
									<td>채형찬</td>
									<td>2025.03.01</td>
									<td class="blue">대기</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</section>
		</div>
	</div>


	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>