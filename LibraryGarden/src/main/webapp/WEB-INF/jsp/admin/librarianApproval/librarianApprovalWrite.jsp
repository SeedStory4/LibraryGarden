<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사서 기안 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css" />
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">

<style>
.draft-buttons { margin-right: 90px; }
.draft-divider { width: 100%; transform: translateX(0); margin-bottom: 20px; }
.draft-info .title { color: inherit; }
.draft-actions-mt { margin-bottom: 20px; }
</style>
</head>
<body>

	<!-- 헤더가 로드될 부분 -->	
    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>
    
	<div class="wrapper">
		<div class="inner p-0">
			<!-- 메인 콘텐츠 -->
			<section class="section draft-section m-0 p-0">
				<div class="draft-header">
					<div class="section-title draft-title m-0">기안 등록</div>
					<div class="draft-buttons">
						<button class="draft-btn btn-green openModal" data-modalType="bookRequestSelect">희망도서선택</button>
						<button class="draft-btn btn-green small openModal" data-modalType="bookSelect">도서선택</button>
					</div>
				</div>
				<hr class="draft-divider m-0">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<form name="frm">
					<input type="hidden" name="rqidx">
					<div class="draft-content">
						<img src="https://placehold.co/141x213?text=BOOK" alt="Book Sample Image" class="draft-book-img coverImg">
						<div class="draft-info">
							<p>
								<span class="info-title label-title">● 제목</span> <span class="info-content title m-0 p-0">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 부제</span> <span class="info-content subtitle">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 서명/저자사항</span> <span class="info-content author">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 출판사(출판일)</span> <span class="info-content publisher">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 전체쪽수</span> <span class="info-content totalPages">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● ISBN</span> <span class="info-content isbn">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 서적정보</span> <span class="info-content info">도서를 선택해주세요.</span>
							</p>
							<p>
								<span class="info-title">● 정가</span> <span class="info-content price">도서를 선택해주세요.</span>
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
			
			<!-- 모달 -->
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
			            <button type="button" class="btn btn-primary btn-small" onClick="loadList(1, listUrl)">검색</button>
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

	<!-- 푸터가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>
	
	<script>
	// 게시글 등록
	function check() {

        let fm = document.frm;
        
		// 도서 선택했는지 확인
		if (fm.rqidx.value == "") {
			alert("도서를 선택해주세요");
			window.scrollTo({top: 0, behavior: 'smooth'});
			return;
		}
		
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
    function loadList(page, listUrl) {

    	// scri 설정
        const searchType = document.querySelector(".modal .select").value;
        const keyword = document.querySelector(".modal .input").value;
	    
    	$.ajax({
			 type: "post",
			 url: listUrl,
			 dataType: "json",
			 data: {"searchType": searchType, "keyword" : keyword, "page" : page },
			       contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			       success: function(result) {  // 성공
					 // alert("전송성공");
			       
					 // html 만들기
					 // 1. table 설정
					 const alist = result.alist;
					 const pm = result.pm;
					 const url = listUrl;
					 
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
				     if(alist.length == 0) {
				    	 listcontent += `<tr>
								<td colspan="8" class="center">검색된 도서가 없습니다.</td>
							</tr>`;
				     } else {
						 for(var i = 0; i < alist.length; i++){
							 listcontent += `<tr>`;
							 listcontent += `<td>\${(pm.scri.page - 1) * pm.scri.perPageNum + i + 1}</td>`;
							 listcontent += `<td><img src=\${alist[i].coverImg} alt=\${alist[i].title}></td>`;
							 listcontent += `<td>\${alist[i].title}</td>`;
							 listcontent += `<td>\${alist[i].author}</td>`;
							 listcontent += `<td>\${alist[i].publisher}</td>`;
							 listcontent += `<td>\${alist[i].name}<br>(\${alist[i].userNumber})</td>`;
							 listcontent += `<td>\${alist[i].regDate.substr(0, 10).replaceAll("-", ".")}</td>`;
							 listcontent += `<td><button class="btn btn-small btn-primary" onClick="select(\${alist[i].rqidx})">선택</button></td>`;
							 listcontent += `</tr>`;
						 }
				     }
					 
					 listcontent += 
						 `</tbody>
					 	</table>`;
					 	
					 // 2. paging 설정
					 const queryParam = "keyword=${pm.scri.keyword}&searchType=${pm.scri.searchType}";
					 
					 let pagecontent = `<ul class="paging flex w-270 justify-center">`;
					 
					 if(Boolean(pm.prev)) {
						 pagecontent += 
							 `<li>
				          		<a class="pointer" onClick="loadList(\${pm.startPage - 1}, '\${listUrl}')" aria-label="Previous">◀</a>
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
							 `" onClick="loadList(\${i}, '\${listUrl}')">\${i}</a>
						 	</li>`;
					 }
					 
					 if(Boolean(pm.next) && Number(pm.endPage) > 0) {
						 pagecontent += 
							 `<li class="page-item">
				          		<a class="pointer" onClick="loadList(\${pm.endPage + 1}, '\${listUrl}')" aria-label="Next">▶</a>
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
    
	// 모달 닫기
    function closeModalClick() {
	    document.querySelector(".modal").style.display = "none";
	    
	    // select 초기화
	    $('.modal .select').val('title').trigger('change');
        document.querySelector(".modal .select").value = "";
        document.querySelector(".modal .input").value = "";
    }
    
    // 모달 열기
    const openModalBtns = document.querySelectorAll(".openModal");
    let listUrl = "";
    function openModalClick(e) {

		// 1. 제목 설정
		document.querySelector(".modal .title").innerText = e.target.innerText;
		
		// 2. list 불러오기(기본 1페이지)		
		const modalType = e.target.attributes["data-modalType"].value;
		if(modalType == "bookRequestSelect") {
			listUrl = "${pageContext.request.contextPath}/admin/bookRequest/bookRequestList.do"
		} else {
			listUrl = ""  // api 상
		}
	    loadList(1, listUrl);

		// 3. 모달 열기
	    document.querySelector(".modal").style.display = "flex";

	    // 4. 모달 닫기 이벤트 추가
	    const closeModalBtn = document.querySelector("#closeModal");
	    closeModalBtn.addEventListener("click", closeModalClick);
    }
    openModalBtns.forEach((e) => e.addEventListener("click", openModalClick));
    
    // 도서 선택
    function select(rqidx) {
    	    
		if(rqidx != undefined) {
		 
		  	$.ajax({
			 type: "post",
			 url: "${pageContext.request.contextPath}/admin/librarianApproval/librarianApprovalSelect.do",
			 dataType: "json",
			 data: {"rqidx" : rqidx},
			       contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			       success: function(result) {  // 성공
					 // alert("전송성공");
					 
					 // 도서 정보 보여주기
		 			 const bv = result.bv;
					 let labelTitle = "● 제목";
					 let title = bv.title;
					 let subtitle = "-";
					 
			         document.querySelector(".coverImg").src = bv.coverImg;
			         document.querySelector(".coverImg").alt = bv.title;
			         
			         if(bv.originalTitle != undefined) {
				         title = bv.title + " / " + bv.originalTitle;
				         labelTitle = "● 제목 / 원제";				         
			         }
			         document.querySelector(".label-title").innerText = labelTitle;
			         document.querySelector(".title").innerText = title;
			         
			         if(bv.subtitle != undefined) {
			        	 subtitle = bv.subtitle;
			         }
			         document.querySelector(".subtitle").innerText = subtitle;
			         
			         document.querySelector(".author").innerText = bv.author;
			         document.querySelector(".publisher").innerText = bv.publisher + "(" + bv.publishedYear.replaceAll('-', '.') + ")";
			         document.querySelector(".totalPages").innerText = bv.totalPages + "쪽";
			         document.querySelector(".isbn").innerText = bv.isbn;
			         document.querySelector(".info").innerText = bv.sizeWidth + "mm * " + bv.sizeHeight + "mm / " + bv.weight + "g / " + bv.category;
			         			 		 
			 		 function addComma(str) {  // 3자리마다 콤마(,)를 입력
			 		   return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
			 		 }
			         document.querySelector(".price").innerText = addComma(String(bv.price)) + "원";
			         			         
			         // controller에 보내기 위해 희망도서 idx 저장하기
					 document.frm.rqidx.value = rqidx;
			         
					 // 모달 닫기
			    	 closeModalClick();
					 
				   },
				   error: function(xhr, status, error) {  // 실패
				 	alert("전송실패");
				 	console.log("Error Status: " + status);
				    console.log("Error Detail: " + error);
				    console.log("Response: " + xhr.responseText);
				   }
  			})
  		}
	}
	</script>
</body>
</html>