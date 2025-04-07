<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사서 기안 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css" />
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/admin/adminHeader.do" />

	<div class="wrapper">
		<div class="inner">
			<!-- 메인 콘텐츠 -->
			<section class="section draft-section">
				<div class="draft-header">
					<div class="section-title draft-title">기안 등록</div>
					<div class="draft-buttons">
						<button class="draft-btn btn-green openModal">희망도서선택</button>
						<button class="draft-btn btn-green small openModal">도서선택</button>
					</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<form name="frm">
					<div class="draft-content">
						<img src="${requestScope.bv.coverImg}" alt="${requestScope.bv.title}" class="draft-book-img">
						<div class="draft-info">
							<p>
								<span class="info-title">● 제목</span> <span class="info-content title">${requestScope.bv.title}</span>
							</p>
							<p>
								<span class="info-title">● 부제</span> <span class="info-content">${requestScope.bv.subtitle}</span>
							</p>
							<p>
								<span class="info-title">● 서명/저자사항</span> <span class="info-content">${requestScope.bv.author}</span>
							</p>
							<p>
								<span class="info-title">● 출판사</span> <span class="info-content">${requestScope.bv.publisher}</span>
							</p>
							<p>
								<span class="info-title">● 출판년도</span> <span class="info-content">${requestScope.bv.publishedYear}</span>
							</p>
							<p>
								<span class="info-title">● 전체쪽수</span> <span class="info-content">${requestScope.bv.totalPages}쪽</span>
							</p>
							<p>
								<span class="info-title">● ISBN</span> <span class="info-content"></span>
							</p>
							<p>
								<span class="info-title">● 서적정보</span> <span class="info-content">${requestScope.bv.info}</span>
							</p>
						</div>
					</div>
	
					<!-- 버튼 -->
					<div class="draft-actions-mt">
						<button type="button" class="draft-btn-small btn-submit" onClick="check()">등록</button>						
						<button type="button" class="draft-btn-small btn-cancel" onClick="history.back()">취소</button>
					</div>
				</form>
			</section>
			
			<!-- 희망도서선택 모달 -->
		    <div class="modal" style="display: none">
		      <div class="modal-content w-1000">
		        <div class="title-container">
		          <div class="title"></div>
		          <div class="title-line"></div>
		        </div>
		
		        <!-- 컨텐츠 영역 -->
		        <div class="book-list">
		          <form name="modal-frm" onsubmit="return false;">
			          <div class="search flex gap-20 justify-center">
			            <select class="js-example-basic-single select shadow" name="searchType">
			              <option value="title" selected>제목</option>
			              <option value="author">저자</option>
			              <option value="name">신청자</option>
			            </select>
			            <input type="text" class="shadow w-520 input" name="keyword">						
			            <button type="button" class="btn btn-primary btn-small" onClick="loadList(1)">검색</button>
			          </div>
		          </form>
		          <div class="table"></div>
		        </div>
		
		        <!-- 버튼 영역 -->
		        <div class="button-group">
		          <button class="btn btn-red" id="closeModal">취소</button>
		        </div>
		      </div>
		    </div>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />
	
	<script>
	// 게시글 등록
	function check() {
		// bidx나 rqidx 확인으로 수정 예정
		const title = document.querySelector(".title");
		if (title.innerText == "") {
			alert("도서를 선택해주세요");
			window.scrollTo({top: 0, behavior: 'smooth'});
			return;
		}
		
        let fm = document.frm;
		let ans = confirm("등록하시겠습니까?");
	  	  if (ans == true) {
			  fm.action="${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalWriteAction.do";
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
	
	// select2
    $(document).ready(function() {
      $('.js-example-basic-single').select2();
    });
	
    let selectedDate = null;

    // 모달 내 list 불러오기
    function loadList(i) {

    	// scri 설정
        const searchType = document.querySelector(".modal .select").value;
        const keyword = document.querySelector(".modal .input").value;
        const page = i;
	    
    	$.ajax({
			 type: "post",
			 url: "${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do",
			 dataType: "json",
			 data: {"searchType": searchType, "keyword" : keyword, "page" : page },
			       contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			       success: function(result) {  // 성공
					 // alert("전송성공");
					 
					 // html 만들기
					 // 1. table 설정
					 const alist = result.alist;
					 const pm = result.pm;
					 
		 			 let listcontent = 
		 				 `<table>
				              <colgroup>
				                <col width="8%">
				                <col width="9%">
				                <col>
				                <col width="16%">
				                <col width="15%">
				                <col width="11%">
				                <col width="11%">
				                <col width="10%">
				              </colgroup>
				              <thead>
				                <tr>
				                  <th>번호</th>
				                  <th>표지</th>
				                  <th>제목</th>
				                  <th>저자</th>
				                  <th>출판사</th>
				                  <th>신청자</th>
				                  <th>신청일</th>
				                  <th>선택</th>
				                </tr>
				              </thead>
				              <tbody>`;
				              
					 for(var i = 0; i < alist.length; i++){
						 listcontent += `<tr>`;
						 listcontent += `<td>\${(pm.scri.page - 1) * pm.scri.perPageNum + i + 1}</td>`;
						 listcontent += `<td><img src=\${alist[i].coverImg} alt=\${alist[i].title}></td>`;
						 listcontent += `<td>\${alist[i].title}</td>`;
						 listcontent += `<td>\${alist[i].author}</td>`;
						 listcontent += `<td>\${alist[i].publisher}</td>`;
						 listcontent += `<td>\${alist[i].name}<br>(\${alist[i].userNumber})</td>`;
						 listcontent += `<td>\${alist[i].regDate.substr(0, 10).replaceAll("-", ".")}</td>`;
						 listcontent += `<td><button class="btn btn-small btn-primary">선택</button></td>`;
						 listcontent += `</tr>`;
					 }
					 
					 listcontent += 
						 `</tbody>
					 	</table>`;
					 	
					 // 2. paging 설정
					 const listUrl = "${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do";
					 const queryParam = "keyword=${pm.scri.keyword}&searchType=${pm.scri.searchType}";
					 
					 let pagecontent = `<ul class="paging flex w-270 justify-center">`;
					 
					 if(Boolean(pm.prev)) {
						 pagecontent += 
							 `<li>
				          		<a href="\${listUrl}?page=\${pm.startPage - 1}&\${queryParam}" aria-label="Previous">◀</a>
				        	  </li>`;
					 }
					 
					 for(var i = Number(pm.startPage); i <= Number(pm.endPage); i++){
						 pagecontent += 
							 `<li>
						 		<a class="pointer`;
						 
						 if(i == Number(pm.scri.page)) {
							pagecontent += ` on`;
						 }
						 
						 pagecontent += 
							 //`" href="\${listUrl}?page=${i}&\${queryParam}">\${i}</a>
							 `" onClick="loadList(\${i})">\${i}</a>
						 	</li>`;
					 }
					 
					 if(Boolean(pm.next) && Number(pm.endPage) > 0) {
						 pagecontent += 
							 `<li class="page-item">
				          		<a href="\${listUrl}?page=\${Number(pm.endPage) + 1}&\${queryParam}" aria-label="Next">▶</a>
				        	  </li>`;
					 }

					 pagecontent += `</ul>`;
					 
					 // 3. html 생성
					 const html = listcontent + pagecontent;
					 $('.modal .table').html(html);
					 
				   },
			 error: function(xhr, status, error) {  // 실패
			 	alert("전송실패");
			    /* console.log("Error Status: " + status);
			    console.log("Error Detail: " + error);
			    console.log("Response: " + xhr.responseText); */
			 }
		});
    }
    
    // 모달 열기
    const openModalBtns = document.querySelectorAll(".openModal");
    function openModalClick(e) {

		// 1. 제목 설정
		document.querySelector(".modal .title").innerText = e.target.innerText;
		
		// 2. list 불러오기(기본 1페이지)
	    loadList(1);
		
		// 3. 모달 열기
	    document.querySelector(".modal").style.display = "flex";

	    // 4. 모달 닫기 이벤트 추가
	    const closeModalBtn = document.querySelector("#closeModal");
	    function closeModalClick() {
		    document.querySelector(".modal").style.display = "none";
		    
		    // select 초기화
		    $('.modal .select').val('title').trigger('change');
	        document.querySelector(".modal .select").value = "";
	        document.querySelector(".modal .input").value = "";
	    }
	    closeModalBtn.addEventListener("click", closeModalClick);
    }
    openModalBtns.forEach((e) => e.addEventListener("click", openModalClick));
    
	</script>
</body>
</html>