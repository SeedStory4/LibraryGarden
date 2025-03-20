<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>희망도서 상세</title>
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
					<div class="section-title draft-title">희망도서 상세</div>
				</div>
				<hr class="draft-divider">
				<!-- 선 추가 -->

				<!-- 도서 정보 -->
				<div class="draft-content ml-28">
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
					<button class="request-status-btn">신청대기</button>
					<!-- <button class="request-status-btn bg-blue">신청중</button> -->
					<!-- <button class="request-status-btn bg-green">신청완료</button> -->
					<!-- <button class="request-status-btn bg-red">신청반려</button> -->
					
				</div>

				<p class="description-title ml-28">● 책 소개</p>

				<div class="draft-book-description shadow ml-28">
					<div class="description-content">
						<p>
							인터내셔널 부커상, 산클레멘테 문학상 수상작 <br> 전세계가 주목한 한강의 역작을 다시 만나다. <br>
							<br> 2016년 인터내셔널 부커상을 수상하며 한국문학의 입지를 한단계 확장시킨 한강의 장편소설
							『채식주의자』를 15년 만에 새로운 장정으로 선보인다. 상처받은 영혼의 고통과 식물적 상상력의 강렬한 결합을 정교한
							구성과 흡인력 있는 문체로 보여주는 이 작품은 섬뜩한 아름다움의 미학을 한강만의 방식으로 완성한 역작이다.“탄탄하고
							정교하며 충격적인 작품으로, 독자들의 마음에 그리고 아마도 그들의 꿈에 오래도록 머물 것이다”라는 평을 받으며
							인터내셔널 부커상을 수상했던 『채식주의자』는 “미국 문학계에 파문을 일으키면서도 독자들과 공명할 것으로 보인다”
							(뉴욕타임스), “놀라울 정도로 아름다운 산문과 믿을 수 없을 만큼 폭력적인 내용의 조합이 충격적이다”(가디언)라는
							해외서평을 받았고 2018년에는 스페인에서 산클레멘테 문학상을 받는 등 전세계에서 뜨거운 반응을 일으켰다. <br>
							<br>『채식주의자』는 어느 날부터 육식을 거부하며 가족들과 갈등을 빚기 시작하는 ‘영혜’가 중심인물로
							등장하는 장편소설이다. 하지만 소설은 영혜를 둘러싼 세 인물인 남편, 형부, 언니의 시선에서 서술되며 영혜는 단
							한번도 주도적인 화자의 위치를 얻지 못한다. 가족의 이름으로 자행되는 가부장의 폭력, 그리고 그 폭력에 저항하며
							금식을 통해 동물성을 벗어던지고 나무가 되고자 한 영혜가 보여주는 식물적 상상력의 경지는 모든 세대 독자를 아우르며
							더 크나큰 공명을 이루어낼 것이다.
						</p>
					</div>
				</div>

				<div class="request-info">
					<!-- 신청자 -->
					<div class="request-info-group">
						<span class="request-info-title">신청자</span> <span
							class="request-info-content">채형찬(032709)</span>
					</div>

					<!-- 신청일 -->
					<div class="request-info-group">
						<span class="request-info-title">신청일</span> <span
							class="request-info-content">2025.03.08</span>
					</div>

					<!-- 상태 -->
					<div class="request-info-group">
						<span class="request-info-title">상태</span>
						<div class="request-status-container">
							<!-- 새 컨테이너 추가 -->
							<select class="js-example-basic-single select" name="state">
							<option value="신청중">신청중</option>
							<option value="신청완료">신청완료</option>
							<option value="신청반려">신청반려</option>
							<option value="신청대기">신청대기</option>
							</select>
							<textarea class="reject-reason-input" placeholder="반려사유를 입력하세요."></textarea>
						</div>
					</div>
				</div>

				<!-- 등록/취소 버튼 -->
				<div class="draft-actions mg-top">
					<button class="draft-btn-small btn-submit">저장</button>
					<button class="draft-btn-small btn-list">목록</button>
				</div>
			</section>
		</div>
	</div>

	<!-- 푸터 로드할 부분 -->
	<jsp:include page="/common/footer.jsp" />

	<script>
		// select2
		$(document).ready(function() {
			$('.js-example-basic-single').select2();
		});
		
		document.addEventListener("DOMContentLoaded", function () {
		    const selectBox = document.querySelector(".js-example-basic-single");
		    const textArea = document.querySelector(".reject-reason-input");

		    // select2 변경 감지 이벤트
		    $('.js-example-basic-single').on("change", function () {
		        let selectedValue = $(this).val();
		        console.log("선택된 값:", selectedValue); // 값 확인 로그

		        if (selectedValue === "신청반려") { 
		            textArea.style.opacity = "1";
		            textArea.style.pointerEvents = "auto"; // 클릭 가능
		        } else {
		            textArea.style.opacity = "0";
		            textArea.style.pointerEvents = "none"; // 클릭 방지
		        }
		    });
		});
    </script>	
</body>
</html>