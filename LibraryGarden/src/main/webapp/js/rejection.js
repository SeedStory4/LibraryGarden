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
