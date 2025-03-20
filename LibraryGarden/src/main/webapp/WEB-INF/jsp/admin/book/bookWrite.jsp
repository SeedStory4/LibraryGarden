<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 도서등록</title>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/admin/adminHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section">
				<div class="draft-header">
					<div class="section-title draft-title">도서등록</div>
					<button class="btn-green small select-book-btn draft-btn">도서선택</button>
				</div>
				<hr class="draft-divider">

				<!-- 도서 정보 -->
				<div class="draft-content">
					<img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="도서 이미지"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title">● 제목</span> <span class="info-content">채식주의자</span>
						</p>
						<p>
							<span class="info-title">● 부제</span> <span class="info-content">채식주의자</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span
								class="info-content">한강</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span class="info-content">창비</span>
						</p>
						<p>
							<span class="info-title">● 출판년도</span> <span class="info-content">2024년</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">216쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">12345687351</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">145×210mm/300g</span>
						</p>
					</div>
				</div>

				<hr class="divider">

				<p class="description-title">● 소장정보</p>

				<table class="info-table">
					<colgroup>
						<col width="15%">
						<col width="25%">
						<col width="25%">
						<col width="35%">
					</colgroup>
					<thead>
						<tr>
							<th>구분</th>
							<th>청구기호</th>
							<th>자료실</th>
							<th>분류</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>DM000012</td>
							<td class="call-number-container">
							<span class="fixed-call-number">802.</span> 
							<input type="text" class="input-call-number" value="123">
							</td>
							<td>
						<select class="js-example-basic-single select" name="state" data-width="wide">
							<option value="normal">일반열람실</option>
							<option value="kids">어린이열람실</option>
						</select>
							</td>
							<td>
						<select class="js-example-basic-single select" name="state">
							<option value="normal">문학</option>
							<option value="kids">역사</option>
						</select> 
							<span class="category-separator">&gt;</span> 
						<select class="js-example-basic-single select" name="state">
							<option value="normal">한국문학</option>
							<option value="kids">중국문학</option>
							<option value="kids">일본문학</option>
						</select>
							</td>
						</tr>
					</tbody>
				</table>
			
				<div class="draft-actions mt-20">
					<button class="draft-btn-small btn-submit">등록</button>
				</div>

				<hr class="custom-divider">
				<div class="table table-narrow">
					<table>
						<colgroup>
							<col width="6%">
							<col width="8%">
							<col>
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="15%">
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
								<th>구분</th>
								<th>청구기호</th>
								<th>자료실</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td><img
									src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg"
									alt="채식주의자"></td>
								<td><a href="#">채식주의자</a></td>
								<td>한강</td>
								<td>창비</td>
								<td>DM250314</td>
								<td>802.123 한 127 v1</td>
								<td>일반열람실</td>
								<td><button class="delete-btn">삭제</button></td>
							</tr>
							<tr>
								<td>1</td>
								<td><img
									src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg"
									alt="채식주의자"></td>
								<td><a href="#">채식주의자</a></td>
								<td>한강</td>
								<td>창비</td>
								<td>DM250314</td>
								<td>802.123 한 127 v1</td>
								<td>일반열람실</td>
								<td><button class="delete-btn">삭제</button></td>
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


			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />

	<script>
		// select2
		$(document).ready(function() {
		    $('.js-example-basic-single').select2().each(function() {
		        if ($(this).attr('data-width') === 'wide') {
		            $(this).next('.select2-container').addClass('select-wide');
		        }
		    });
		});
    </script>

</body>
</html>