document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById("rejectionModal");
  const confirmBtn = document.getElementById("confirmRejection");
  const closeBtn = document.getElementById("closeRejectionModal");
  const rejectionInput = document.getElementById("rejectionReason");

  // 모달 열기 (예제: 버튼 클릭 시 실행)
  document
    .getElementById("openRejectionModal")
    .addEventListener("click", function () {
      modal.style.display = "flex";
    });
  // 포커스 시 box-shadow 강제 적용 및 높이 고정
  rejectionTextarea.addEventListener("focus", function () {
    this.style.boxShadow = "2px 2px 4px rgba(0, 0, 0, 0.25)";
    this.style.opacity = "0.5";
    this.style.background = "#fff";
    this.style.height = "462px"; // 높이 고정
    this.style.marginBottom = "20px"; // 버튼 위치 유지
  });

  // 블러 시에도 동일한 스타일 유지
  rejectionTextarea.addEventListener("blur", function () {
    this.style.boxShadow = "2px 2px 4px rgba(0, 0, 0, 0.25)";
    this.style.opacity = "0.5";
    this.style.background = "#fff";
    this.style.height = "462px"; // 높이 유지
    this.style.marginBottom = "20px"; // 버튼 위치 유지
  });

  // 모달 닫기 (취소 버튼)
  closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
    rejectionInput.value = ""; // 입력 내용 초기화
  });

  // 확인 버튼 클릭 시 입력값 출력
  confirmBtn.addEventListener("click", function () {
    if (rejectionInput.value.trim() === "") {
      alert("반려 사유를 입력해주세요.");
      return;
    }

    alert("반려 사유: " + rejectionInput.value);
    modal.style.display = "none";
    rejectionInput.value = ""; // 입력 내용 초기화
  });
});
