<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 도서관리 수정</title>
<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reservation.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/adminMain.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/list.css">
<style>
.draft-info .title {
	color: inherit;
	font-size: inherit;
	padding: 0;
	margin: 0;
}

.section-title {
	padding: 0;
}

.p-0 {
	padding: 0;
}
</style>
</head>
<body>

	<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/WEB-INF/jsp/admin/adminHeader.jsp" />

	<c:if test="${not empty msg}">
		<script>
	        alert("${msg}");
	    </script>
	</c:if>
	<div class="wrapper p-0">
		<div class="inner p-0">
			<!-- 메인 콘텐츠 -->
			<section class="section draft-section">
				<div class="draft-header">
					<div class="section-title draft-title">도서관리 수정</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<c:set var="lbd" value="${requestScope.lbd}" />
				<div class="draft-content">
					<img src="${lbd.coverImg}" alt="${lbd.title}"
						class="draft-book-img coverImg">
					<div class="draft-info">
						<p>
							<span class="info-title label-title">● 제목<c:choose>
									<c:when test="${not empty lbd.originalTitle}"> / 원제</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose></span> <span class="info-content m-0 p-0 title">${lbd.title}<c:choose>
									<c:when test="${not empty lbd.originalTitle}"> / ${lbd.originalTitle}</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose></span>
						</p>
						<p>
							<span class="info-title">● 부제</span> <span
								class="info-content subTitle"> <c:choose>
									<c:when test="${not empty lbd.subTitle}">
									${lbd.subTitle}
								  	</c:when>
									<c:otherwise>
								    -
								    </c:otherwise>
								</c:choose>
							</span>
						</p>
						<p>
							<span class="info-title">● 서명/저자사항</span> <span
								class="info-content author">${lbd.author}</span>
						</p>
						<p>
							<span class="info-title">● 출판사</span> <span
								class="info-content publisher">${lbd.publisher}</span>
						</p>
						<p>
							<span class="info-title">● 출판년도</span> <span
								class="info-content publishedYear">${fn:replace(lbd.publishedYear, '-', '.')}</span>
						</p>
						<p>
							<span class="info-title">● 전체쪽수</span> <span
								class="info-content totalPages">${lbd.totalPages}쪽</span>
						</p>
						<p>
							<span class="info-title">● ISBN</span> <span
								class="info-content isbn">${lbd.isbn}</span>
						</p>
						<p>
							<span class="info-title">● 서적정보</span> <span
								class="info-content info">${lbd.sizeWidth}mm *
								${lbd.sizeHeight}mm / ${lbd.weight}g / <c:choose>
									<c:when test="${not empty lbd.category}">${lbd.category}</c:when>
									<c:otherwise> - </c:otherwise>
								</c:choose>
							</span>
						</p>
					</div>
				</div>

				<p class="description-title">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content introduction">
						<p>${lbd.introduction}</p>
					</div>
				</div>

				<hr class="divider">

				<p class="description-title">● 소장정보</p>

				<form name="frm">
					<input type="hidden" name="bidx" value="${lbd.bidx}"> 
					<input type="hidden" name="lbidx" value="${lbd.lbidx}"> 
					<input type="hidden" name="aidx" value="${lbd.aidx}"> 
					<input type="hidden" name="callName" id="callName">
					<input type="hidden" name="cidx" id="cidx" value="${lbd.cidx}">
					<table class="info-table">
						<colgroup>
							<col width="15%">
							<col width="35%">
							<col width="25%">
							<col width="25%">

						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>분류</th>
								<th>청구기호</th>
								<th>자료실</th>

							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${lbd.code}</td>
								<td>
									<select class="js-example-basic-single select" id="parentCategory" name="parentCode">
											<option value="">대분류 선택</option>
											<c:forEach var="parentList" items="${parentList}">
												<option value="${parentList.parentCode}" ${lbd.parentCode eq parentList.parentCode ? 'selected' : ''}>${parentList.name}</option>
											</c:forEach>
									</select> 
								<span class="category-separator">&gt;</span> 
									<select class="js-example-basic-single select" id="childCategory" name="childCode">
										<option value="">소분류 선택</option>
										<c:forEach var="childList" items="${childList}">
											<option value="${childList.childCode}" ${lbd.childCode eq childList.childCode ? 'selected' : ''}>${childList.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="call-number-container">
									<span class="fixed-call-number"> ${fn:substringBefore(lbd.callName, '.')}.</span> 
									<input type="text" class="input-call-number" value="${fn:substringAfter(lbd.callName, '.')}">
								</td>
								<td>
									<select class="js-example-basic-single select" name="location" data-width="wide">
										<option value="">자료실 선택</option>
										<option value="일반열람실"
											${lbd.location eq '일반열람실' ? 'selected' : ''}>일반열람실</option>
										<option value="어린이열람실"
											${lbd.location eq '어린이열람실' ? 'selected' : ''}>어린이열람실</option>
										<option value="보존서고"
											${lbd.location eq '보존서고' ? 'selected' : ''}>보존서고</option>
									</select>
								</td>

							</tr>
						</tbody>
					</table>
				</form>

				<div class="draft-actions mg-top">
					<button class="draft-btn-small btn-submit" type="button" onclick="check();">수정</button>
					<button class="draft-btn-small btn-list" onclick="location.href='${pageContext.request.contextPath}/admin/book/bookList.do'">목록</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/WEB-INF/jsp/cmm/footer.jsp" />

	<script>        
		
		$(document).ready(function() {
		    
			// select2
		    $('.js-example-basic-single').select2().each(function() {
		        if ($(this).attr('data-width') === 'wide') {
		            $(this).next('.select2-container').addClass('select-wide');
		        }
		    });
		    // 처음 페이지 로딩할 때만 select2 적용
		    $('.js-example-basic-single').select2({
		        width: 'resolve' // 필요에 따라 추가 (선택)
		    });
		    
		    // 대분류 선택 시 소분류 불러오기
		    $("#parentCategory").change(function() {
		        let parentCode = $(this).val();
		        
		        // 소분류 초기화
		        $("#childCategory").empty().append(`<option value="">소분류 선택</option>`);

		        // 청구기호도 초기화 
		        $(".fixed-call-number").text("---.");
		        $("#cidx").val("");  // 숨겨진 cidx도 초기화
		        
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
	    // 등록 및 유효성 검사
		function check(){
	    	
			var fm = document.frm;	

		    let currentAidx = fm.aidx.value;
		    let currentBidx = fm.bidx.value;
		    let currentLbidx = fm.lbidx.value;
		    
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
			
            $("#callName").val(fullCode);
            
			//청구기호 중복 검사
            callNumberDuplicateCheck(fullCode, currentLbidx, currentAidx, currentBidx).done(function(result) {
       			console.log(result);
            	if (result == -1) {
                    if (confirm("이 책에 저장되었던 소장번호입니다. 저장하시겠습니까?")) {
                        fm.action = "${pageContext.request.contextPath}/admin/book/bookModifyAction.do"; 
                        fm.method = "post";
                        fm.submit();
                    }
                } else if (result > 0) {
                    alert("이미 존재하는 청구기호입니다. 다시 입력해주세요.");
                    $(".input-call-number").focus();
                } else if (result == 0) {
                    if (confirm("수정하시겠습니까?")) {
                        fm.action = "${pageContext.request.contextPath}/admin/book/bookModifyAction.do"; 
                        fm.method = "post";
                        fm.submit();
                    }
                } else {
                    alert("청구기호 검사 중 오류가 발생했습니다.");
                }
            }); 
		}
	    
	    //  청구기호 중복검사
		function callNumberDuplicateCheck(callName, lbidx, aidx, bidx) {
		    return $.ajax({
		        type: "POST",
		        url: "${pageContext.request.contextPath}/admin/book/checkCallNumberDuplicate.do",
		        data: { "callName": callName,
		            "lbidx": lbidx,
		            "aidx": aidx,
		            "bidx": bidx},
		        dataType: "json"
		    });
		}
	    
    </script>


</body>
</html>