<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

	<c:if test="${not empty msg}">
	    <script>
	        alert("${msg}");
	    </script>
	</c:if>
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
							<span class="info-title">● 출판일</span> <span class="info-content publishedYear">도서를 선택해주세요.</span>
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


				<form name="frm">
				<input type="hidden" name="bidx" value="${hm.bidx}">
				<input type="hidden" name="code" value="${hm.lastCode}">
				<input type="hidden" name="callName" id="callName">
				<input type="hidden" name="cidx" id="cidx">
				<input type="hidden" name="aidx" value="${hm.aidx}">

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
							<td >${hm.lastCode}</td>
							<td class="call-number-container">
								<span class="fixed-call-number">---.</span> 
								<input type="text" class="input-call-number" value="">
							</td>
							<td>
								<select class="js-example-basic-single select" name="location" data-width="wide">
									<option value="">자료실 선택</option>
									<option value="일반열람실">일반열람실</option>
									<option value="어린이열람실">어린이열람실</option>
									<option value="보존서고">보존서고</option>
								</select>
							</td>
							<td>
								<select class="js-example-basic-single select" id="parentCategory" name="parentCode">
								    <option value="">대분류 선택</option>
								    <c:forEach var="parentList" items="${hm.parentList}">
								      <option value="${parentList.parentCode}">${parentList.name}</option>
								    </c:forEach>
								</select> 
									<span class="category-separator">&gt;</span> 
								<select class="js-example-basic-single select" id="childCategory" name="childCode">
								    <option value="">소분류 선택</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				
				<div class="draft-actions mt-20">
					<button class="draft-btn-small btn-submit"  type="button" onclick="check();">등록</button>
				</div>

				<hr class="custom-divider">
				<div class="table table-narrow book-write-list">

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
		// 모달에서 책 선택
		let selectedAidx = null;

		// 페이지 열었을 때 등록된 도서 자동 로딩
		window.onload = function() {
			loadBookList(1);
		}
		
		$(document).ready(function() {
			// select2
		    $('.js-example-basic-single').select2().each(function() {
		        if ($(this).attr('data-width') === 'wide') {
		            $(this).next('.select2-container').addClass('select-wide');
		        }
		    });
			
		    // 대분류 선택 시 소분류 불러오기
		    $("#parentCategory").change(function() {
		        let parentCode = $(this).val();
		        
		        // 대분류가 선택되지 않은 경우 안내
		        if (!parentCode) {
		            alert("먼저 대분류를 선택해주세요.");
		            return; // 더 이상 진행하지 않음
		        }
		        
		        // 소분류 초기화
		        $("#childCategory").empty().append(`<option value="">소분류 선택</option>`);

		        // 대분류가 선택된 경우 → Ajax로 소분류 요청
		        if (parentCode) {
		            $.ajax({
		                url: "${pageContext.request.contextPath}/admin/book/getChildrenCategory.do",
		                type: "POST",
		                data: { "parentCode": parentCode },
		                success: function(data) {
		                    
		                    for (let i = 0; i < data.length; i++) {
		                        let $option = $("<option>")
		                        .val(data[i].childCode)
		                        .text(data[i].name)
         					    .attr("data-cidx", data[i].cidx);  
		                    	$("#childCategory").append($option);
		                    }

		                    // 새로 append된 childCategory에도 select2 다시 적용
		                    $("#childCategory").select2("destroy");
		                    $("#childCategory").select2();
		                },
		                error: function() {
		                    alert("하위 카테고리 불러오기 실패");
		                }
		            });
		        }
		    });
		    
			 // 소분류 선택 시 청구기호 자동 변경
		    $("#childCategory").on("change", function () {
		        let selectedIndex = $(this).prop('selectedIndex');
		        // 선택된 option
		        let selectedOption = $(this).find('option:selected');
		        
		        if (selectedIndex > 0) {
		            // cidx 값 설정
		            let selectedCidx = selectedOption.data("cidx");
		            $("#cidx").val(selectedCidx);

		            // 청구기호 앞자리 설정
		            const selectedCode = $(this).val();  // childCode
		            $(".fixed-call-number").text(selectedCode + ".");
		        } else {
		            // 선택 안했을 경우 초기화
		            $("#cidx").val("");
		            $(".fixed-call-number").text("---.");
		        }

		    });
		});
		
	    // 모달 열기
	    const openModalBtns = document.querySelectorAll(".openModal");
	    let listUrl = "";
	    function openModalClick(e) {
	    	 
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
									<td colspan="7" class="center">검색된 도서가 없습니다.</td>
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
			 			 const bv = result;
				         document.querySelector(".coverImg").src = bv.coverImg;
				         document.querySelector(".coverImg").alt = bv.title;
						 
						 let labelTitle = "● 제목";
						 let title = bv.title;
				         if(bv.originalTitle != undefined) {
					         title = bv.title + " / " + bv.originalTitle;
					         labelTitle = "● 제목 / 원제";				         
				         }
				         document.querySelector(".label-title").innerText = labelTitle;
				         document.querySelector(".draft-info .title").innerText = title;
				         
						 let subtitle = "-";
				         if(bv.subtitle != undefined) {
				        	 subtitle = bv.subtitle;
				         }
				         document.querySelector(".subtitle").innerText = subtitle;
				         document.querySelector(".author").innerText = bv.author;
				         document.querySelector(".publisher").innerText = bv.publisher;
				         document.querySelector(".publishedYear").innerText = bv.publishedYear;
				         document.querySelector(".totalPages").innerText = bv.totalPages + "쪽";
				         document.querySelector(".isbn").innerText = bv.isbn;
				         document.querySelector(".info").innerText = bv.sizeWidth + "mm * " + bv.sizeHeight + "mm / " + bv.weight + "g / " + bv.category;

				         			         
				         // 도서 등록을 위해 결재, 책 idx 저장하기
						 document.frm.aidx.value = bv.aidx;
						 document.frm.bidx.value = bv.bidx;
				         
						 // 모달 닫기
						 document.querySelector(".modal").style.display = "none";
					    $('.modal .select').val('title').trigger('change');
				        document.querySelector(".modal .select").value = "";
				        document.querySelector(".modal .input").value = "";
						 
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
	    
	    // 등록 및 유효성 검사
		function check(){
	    	
			var fm = document.frm;	

			// 청구기호 유효성 검사 변수
			let fixedCode = $(".fixed-call-number").text().trim();   
			let inputCode = $(".input-call-number").val().trim();   
			let fullCode = fixedCode + inputCode;        

			// 도서
			if (fm.bidx.value === "" ) {// bidx
			    alert("등록할 도서를 선택헤주세요");
			    return;
			}
			
			// 분류
			if (fm.parentCode.value === "") {
			    alert("대분류를 선택해주세요.");
			    return;
			}
			if (fm.childCode.value === "") {
			    alert("소분류를 선택해주세요.");
			    return;
			}
			if (fixedCode === "---." || !fixedCode) {
			    alert("분류를 다시 선택해주세요.");
			    return;
			}
			
			// 청구기호
			if (!inputCode) { // 청구기호 입력값 유효성 검사
			    alert("청구기호 뒷자리를 입력해주세요.");
			    $(".input-call-number").focus();
			    return;
			}
			
			// 자료실
			if (fm.location.value === "" ) { // 자료실 입력값 유효성 검사
			    alert("자료실을 선택해주세요.");
			    return;
			}
			
			//청구기호 중복 검사
		    callNumberDuplicateCheck(fullCode).done(function(result) {
		        if (result > 0) {
		            alert("이미 존재하는 청구기호입니다. 다시 입력해주세요.");
		            $(".input-call-number").focus();
		        } else {
		            $("#callName").val(fullCode);
		        	var ans = confirm("저장하시겠습니까?");
		        	if (ans == true){

		        		fm.action="${pageContext.request.contextPath}/admin/book/bookWriteAction.do",
		        		fm.method="post"; 
		        		fm.submit();
		        	}

		        }
		    }).fail(function() {
		        alert("청구기호 중복검사 중 오류가 발생했습니다.");
		    });

			 
	    }
	    
	    //  청구기호 중복검사
		function callNumberDuplicateCheck(callName) {
		    return $.ajax({
		        type: "POST",
		        url: "${pageContext.request.contextPath}/admin/book/checkCallNumberDuplicate.do",
		        data: { "callName": callName },
		        dataType: "json"
		    });
		}
	    
	    // 등록된 도서 정보 리스트 가지고 오기
		function loadBookList(page) {
		
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/admin/book/bookWriteList.do",
				dataType: "json",
				data: {
					page: page
				},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(result) {
					const blist = result.bwlist;
					const pm = result.bwpm;
					let listHtml  = "";

					// 테이블 생성
					listHtml  += `
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
							<tbody>`;
					if (blist.length === 0) {
						listHtml  += `<tr><td colspan="9" class="center">등록된 도서가 없습니다.</td></tr>`;
					} else {
						for (let i = 0; i < blist.length; i++) {
							const item = blist[i];
							const num = (pm.scri.page - 1) * pm.scri.perPageNum + i + 1;
							listHtml  += `
								<tr>
									<td>\${num}</td>
									<td><img src="\${item.coverImg}" alt="\${item.title}" /></td>
									<td><a href="${pageContext.request.contextPath}/admin/book/\${item.lbidx}/bookDetail.do">\${item.title}</a></td>
									<td>\${item.author}</td>
									<td>\${item.publisher}</td>
									<td>\${item.code}</td>
									<td>\${item.callName}</td>
									<td>\${item.location}</td>
									<td><button class="delete-btn" onclick="deleteBookWrite(\${item.lbidx},this)">삭제</button></td>
								</tr>
							`;
						}
					}
		
					listHtml += `
							</tbody>
						</table>
					`;
		
					// 페이징 처리
					let paging = `<ul class="paging flex w-270 justify-spacebtween">`;
					if (pm.prev) {
						paging += `<li><a href="javascript:void(0);" onclick="loadBookList(${pm.startPage - 1})">◀</a></li>`;
					}
					for (let i = pm.startPage; i <= pm.endPage; i++) {
						paging += `<li><a href="javascript:void(0);" onclick="loadBookList(${i})" class="${i == pm.scri.page ? 'on' : ''}">${i}</a></li>`;
					}
					if (pm.next) {
						paging += `<li><a href="javascript:void(0);" onclick="loadBookList(${pm.endPage + 1})">▶</a></li>`;
					}
					paging += `</ul>`;
		
					// HTML 삽입
					document.querySelector(".book-write-list").innerHTML = listHtml + paging;
				},
				error: function() {
					alert("도서 목록 불러오기 실패");
				}
			});
		}
	    
	    // 등록된 도서 삭제하기 
		function deleteBookWrite(lbidx, btn) {
        	var ans = confirm("저장하시겠습니까?");
        	if (ans == true){
			   	$.ajax({
			        type: "POST",
			        url: "${pageContext.request.contextPath}/admin/book/bookWriteDelete.do",
			        data: { "lbidx": lbidx },
			        dataType: "json",
			        success : function(response) {
						alert("삭제되었습니다.");
						// 삭제된 행 정렬
						$(btn).closest("tr").remove();
			             // 남은 행 번호 다시 정렬
		                $(".book-write-list tbody tr").each(function(index) {
		                    $(this).find("td").eq(0).text(index + 1);  // 첫 번째 <td>에 번호 다시 넣기
		                });
					},
					error: function() {
						alert("삭제 실패");
					}
			    });
        	}
		}
	    
    </script>

</body>
</html>