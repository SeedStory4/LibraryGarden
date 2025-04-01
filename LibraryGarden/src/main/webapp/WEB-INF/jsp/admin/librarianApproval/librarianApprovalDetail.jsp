<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사서 기안 상세</title>
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
					<div class="section-title draft-title">기안 상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<div class="draft-content ml-28">
					<img src="${requestScope.bv.coverImg}" alt="${requestScope.bv.title}"
						class="draft-book-img">
					<div class="draft-info">
						<p>
							<span class="info-title">● 제목</span> <span class="info-content">${requestScope.bv.title}</span>
						</p>
						<p>
							<span class="info-title">● 부제</span> <span class="info-content">${requestScope.bv.subtitle}</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span class="info-content">${requestScope.bv.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사(출판년도)</span> <span class="info-content">${requestScope.bv.publisher}(${requestScope.bv.publishedYear})</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span class="info-content">${requestScope.bv.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span class="info-content">${requestScope.bv.info}/${requestScope.bv.category}</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span class="info-content">${requestScope.bv.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 정가</span> <span class="info-content price"></span>
						</p>
					</div>
				</div>

				<p class="description-title ml-28">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>${requestScope.bv.introduction}</p>
					</div>
				</div>

				<!-- 버튼 -->
				<form name="frm">
					<div class="draft-actions">
						<button class="draft-btn-small btn-submit">수정</button>
						<button type="button" class="draft-btn-small btn-cancel" onClick="del()">삭제</button>
						<button type="button" class="draft-btn-small btn-list" onClick="history.back()">목록</button>
					</div>
				</form>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
	
	<script>
		// 3자리마다 콤마(,)를 입력
		function addComma(str) { 
		  return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		}
		const price = document.querySelector(".price");
		price.innerText = addComma("${requestScope.bv.price}") + "원";
		
		// 게시글 삭제
		function del() {
			
	        let fm = document.frm;
			let ans = confirm("삭제하시겠습니까?");
		  	  if (ans == true) {
				  fm.action="${pageContext.request.contextPath}/admin/librarianApproval/${requestScope.aidx}/librarianApprovalDeleteAction.do";
				  fm.method="post";
				  fm.submit();
			}
			
			return;
		}
		
		// 메세지
		const msg = "${requestScope.msg}";
		if (msg != null && msg != "") {
			alert(msg);
		}
	</script>
</body>
</html>