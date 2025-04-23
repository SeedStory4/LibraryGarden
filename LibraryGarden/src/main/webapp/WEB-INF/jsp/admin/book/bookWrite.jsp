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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminMain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
<style>
.draft-buttons { margin-right: 90px; }
.draft-divider { width: 100%; transform: translateX(0); margin-bottom: 20px; }
.draft-info .title { color: inherit; font-size: inherit; padding:0; margin:0;}
.draft-actions-mt { margin-bottom: 20px; }
</style>
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
    <jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp"/>

	<div class="wrapper">
		<div class="inner p-0">
			<!-- 메인 콘텐츠 -->
			<section class="section draft-section ">
				<div class="draft-header ">
					<div class="section-title draft-title m-0">도서등록</div>
					<button class="btn-green small select-book-btn draft-btn openModal" data-modalType="bookSelect">도서선택</button>
				</div>
				<hr class="draft-divider m-0">

				<!-- 도서 정보 -->
				<div class="draft-content">
					<img src="https://placehold.co/141x213?text=BOOK" alt="Book Sample Image"
						class="draft-book-img coverImg">
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
							<span class="info-title">● 출판사</span> <span class="info-content publisher">도서를 선택해주세요.</span>
						</p>
						<p>
							<span class="info-title">● 출판년도</span> <span class="info-content publishedYear">도서를 선택해주세요.</span>
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
							<td>${hm.lastCode}</td>
							<td class="call-number-container">
							<span class="fixed-call-number">802.</span> 
							<input type="text" class="input-call-number" value="123">
							</td>
							<td>
						<select class="js-example-basic-single select" name="state" data-width="wide">
							<option value="normal">일반열람실</option>
							<option value="kids">어린이열람실</option>
							<option value="kids">보존서고</option>
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

	<!-- 푸터 로드할 부분 -->
    <jsp:include page="/WEB-INF/jsp/cmm/footer.jsp"/>

	<script>
		let selectedAidx = null;
	
		// select2
		$(document).ready(function() {
		    $('.js-example-basic-single').select2().each(function() {
		        if ($(this).attr('data-width') === 'wide') {
		            $(this).next('.select2-container').addClass('select-wide');
		        }
		    });
		});
		
	    // 모달 열기
	    const openModalBtns = document.querySelectorAll(".openModal");
	    let listUrl = "";
	    function openModalClick(e) {
	    	 console.log("모달 열기"); // 추가!
			// 1. 제목 설정
			document.querySelector(".modal .title").innerText = e.target.innerText;
			
			// 2. list 불러오기(기본 1페이지)		
			const modalType = e.target.attributes["data-modalType"].value;
			if(modalType == "bookSelect") {
				listUrl = "${pageContext.request.contextPath}/admin/book/bookSelectList.do"
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
	    
	    // 모달 내 list 불러오기
	    function loadList(page, listUrl) {

	    	// scri 설정
	        const searchType = document.querySelector(".modal .select").value;
	        const keyword = document.querySelector(".modal .input").value;
		    
	    	$.ajax({
				 type: "post",
				 url: listUrl,
				 dataType: "json",
				 data: {"searchType": searchType, 
					 "keyword" : keyword, 
					 "page" : page,
				     "selectedAidx": selectedAidx},
				       contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				       success: function(result) {  // 성공
						 // alert("전송성공");
				       
						 // html 만들기
						 // 1. table 설정
						 const blist = result.blist;
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
					              </colgroup>
					              <thead>
					                <tr>
					                  <th>번호</th>
					                  <th>표지</th>
					                  <th>제목</th>
					                  <th>저자</th>
					                  <th>출판사</th>
					                  <th>출판일</th>
					                  <th>선택</th>
					                </tr>
					              </thead>
					              <tbody>`;
					     if(blist.length == 0) {
					    	 listcontent += `<tr>
									<td colspan="8" class="center">검색된 도서가 없습니다.</td>
								</tr>`;
					     } else {
							 for(var i = 0; i < blist.length; i++){
							 
						        const item = blist[i];
						        const publishedDate = item.publishedYear;
						        const publishedStr = typeof publishedDate === 'string'
						                            ? publishedDate
						                            : new Date(publishedDate).toISOString();
						        
								 listcontent += `<tr>`;
								 listcontent += `<td>\${(pm.scri.page - 1) * pm.scri.perPageNum + i + 1}</td>`;
								 listcontent += `<td><img src=\${blist[i].coverImg} alt=\${blist[i].title}></td>`;
								 listcontent += `<td>\${blist[i].title}</td>`;
								 listcontent += `<td>\${blist[i].author}</td>`;
								 listcontent += `<td>\${blist[i].publisher}</td>`;
								 listcontent += `<td>\${publishedStr.substr(0, 10).replaceAll("-", ".")}</td>`;
								 listcontent += `<td><button class="btn btn-small btn-primary" onClick="select('\${blist[i].aidx}')"">선택</button></td>`;
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
	    
	    // 도서 선택
	    function select(aidx) {
	    	selectedAidx = aidx;    
			if(aidx != undefined) {
			 
			  	$.ajax({
				 type: "post",
				 url: "${pageContext.request.contextPath}/admin/book/bookSelectOne.do",
				 dataType: "json",
				 data: {"aidx" : aidx},
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