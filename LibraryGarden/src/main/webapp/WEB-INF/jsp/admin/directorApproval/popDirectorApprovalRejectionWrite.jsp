<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>반려사유 팝업</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/rejection.css">
</head>
<body>
<!-- 헤더가 로드될 부분 -->
	<jsp:include page="/admin/adminHeader.do" />
  <!-- 반려사유 모달 -->
    <div id="rejectionModal" class="modal">
      <div class="modal-content">
        <div class="title-container">
          <div class="title">반려사유</div>
          <div class="title-line"></div>
        </div>

        <textarea
          id="rejectionReason"
          placeholder="반려 사유를 입력하세요..."
        ></textarea>

        <!-- 버튼 영역 -->
        <div class="button-group">
          <button id="confirmRejection">확인</button>
          <button id="closeRejectionModal">취소</button>
        </div>
      </div>
    </div>

    <script src="<%= request.getContextPath() %>/js/rejection.js"></script>
    <!-- 푸터 로드할 부분 -->
	<jsp:include page="/cmm/footer.do" />

</body>
</html>