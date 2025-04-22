<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서대출</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body class="custom-page">

	<!-- 헤더가 로드될 부분 -->   
	<jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<div class="wrapper">
		<section class="section p-0">
			<h2 class="section-title m-0 normal">도서대출</h2>
			
			<div class="contents">
				<div class="user-number flex gap-20">
					<label class="flex gap-20">
						<span>회원번호</span>
						<input type="text" id="userNumber" class="w-290 shadow">
					</label>
					<button class="btn btn-primary btn-small number-check" onClick="numberCheck()" type="button">확인</button>
				</div>
				<div class="book-list border-top-2 none">
					<!-- 대출 가능 여부를 동적으로 표시 -->
                    <p class="loan-info mb-20"></p>
					<div class="search flex gap-20">				
						<label class="flex gap-20">
							<span>도서구분</span>
							<input type="text" id="bookCode" class="w-290 shadow">
						</label>
						<button class="btn btn-primary btn-small" onClick="addBook()">등록</button>
					</div>
					<div class="table">
						<table>
							<colgroup>
								<col width="6%">
								<col width="8%">
								<col>
								<col width="10%">
								<col width="10%">
								<col width="12%">
								<col width="12%">
								<col width="12%">
								<col width="8%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>표지</th>
									<th>제목</th>
									<th>저자</th>
									<th>구분</th>
									<th>대출일</th>
									<th>반납예정일</th>
									<th>반납일</th>
									<th>상태</th>
									<th>삭제/반납</th>
								</tr>
							</thead>
							<tbody id="loanList">
                                <!-- 대출 목록이 동적으로 추가될 부분 -->
                            </tbody>
						</table>
                        <!-- 페이지네이션 -->
                        <ul class="paging flex w-270 justify-center" id="pagination">
                            <!-- 페이지 번호가 동적으로 추가될 부분 -->
                        </ul>	
					</div>
				</div>
			</div>
		</section>
	</div>
	
    <!-- 푸터가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

    <!-- 외부 JS 파일 링크 -->
    <script src="${pageContext.request.contextPath}/js/bookLoanWrite.js"></script>
	<script>var contextPath = '${pageContext.request.contextPath}';</script>
</body>
</html>
