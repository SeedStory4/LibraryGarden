<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 도서</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/rejection.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

    <div id="header-container">
    	<%@ include file="/WEB-INF/jsp/user/userHeader.jsp" %>
    </div>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">내 도서</h2>
			
			<div class="contents">
				<p class="loan-info">김시연(017147)님의 현재 대출가능여부는 <span class="green bold">"이용가능"</span>입니다.</p>
				<div class="list">
					<ul class="tab flex gap-3">
						<li class="shadow"><a href="#">대출이력</a></li>
						<li class="shadow"><a href="#">예약관리</a></li>
						<li class="on shadow"><a href="#">도서신청관리</a></li>
					</ul>
					<div class="table">
						<table>
							<colgroup>
								<col width="8%">
								<col>
								<col width="16%">
								<col width="15%">
								<col width="13%">
								<col width="11%">
								<col width="12%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>저자</th>
									<th>출판사</th>
									<th>신청일</th>
									<th>상태</th>
									<th>취소</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td class="orange">신청대기</td>
									<td><button class="btn btn-small btn-red">취소</button></td>
								</tr>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td class="blue">신청중</td>
									<td></td>
								</tr>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td class="green">신청완료</td>
									<td></td>
								</tr>
								<tr>
									<td>1</td>
									<td>채식주의자</td>
									<td>한강</td>
									<td>창비</td>
									<td>2025.03.14</td>
									<td class="red pointer" id="openRejectionModal">신청반려</td>
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
	</div>
	
    <div id="footer-container">
    	<%@ include file="/WEB-INF/jsp/cmm/footer.jsp" %>
    </div>
	
    <script src="${pageContext.request.contextPath}/js/rejection.js"></script>
</body>
</html>
