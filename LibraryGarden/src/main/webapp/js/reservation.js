document.addEventListener("DOMContentLoaded", function () {
  let selectedDate = null;

  // 모달 열기 & 닫기 기능
  document.getElementById("openModal").addEventListener("click", function () {
    document.getElementById("modal").style.display = "flex";
  });

  document.getElementById("closeModal").addEventListener("click", function () {
    document.getElementById("modal").style.display = "none";
  });

  // FullCalendar 설정
  let calendarEl = document.getElementById("calendar");
  let calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "ko",
    selectable: true,
    showNonCurrentDates: false, // 🔹 해당 월에 포함되지 않는 날짜 숨김
    fixedWeekCount: false, // 🔹 달의 실제 주 수만 표시 (6주 강제 X)
    headerToolbar: {
      left: "prev", // "<" 버튼 (왼쪽)
      center: "title", // 오늘 날짜 (가운데)
      right: "next", // ">" 버튼 (오른쪽)
    },
    dayCellContent: function (arg) {
      return arg.date.getDate(); // '일'을 제외하고 숫자만 표시
    },

    dateClick: function (info) {
      let clickedDate = info.dateStr;
      // 날짜 형식을 "YYYY.MM.DD"로 변환
      let formattedDate = clickedDate.replace(/-/g, ".");

      // 예약 불가능한 날짜 예시 (빨간색)
      let disabledDates = ["2025.03.01", "2025.03.02", "2025.03.18"];
      if (disabledDates.includes(formattedDate)) {
        // 변환된 날짜 형식과 맞춤
        alert("해당 날짜는 예약이 불가능합니다.");
        return;
      }

      // 기존 선택된 날짜 스타일 초기화
      document.querySelectorAll(".fc-day-selected").forEach((el) => {
        el.classList.remove("fc-day-selected");
      });

      // 선택한 날짜 스타일 적용
      let selectedDateEl = document.querySelector(
        `[data-date="${clickedDate}"]`
      );
      if (selectedDateEl) {
        selectedDateEl.classList.add("fc-day-selected");
      }

      // 선택한 날짜 업데이트 (YYYY.MM.DD 형식 적용)
      selectedDate = formattedDate;
      document.getElementById("selectedDate").textContent = selectedDate;

      // 예약 버튼 활성화
      document.getElementById("reserveBtn").disabled = false;
    },
  });

  calendar.render();

  // 동의 체크박스 기능
  document.getElementById("agreeCheck").addEventListener("change", function () {
    let reserveBtn = document.getElementById("reserveBtn");
    reserveBtn.disabled = !this.checked || !selectedDate;
  });

  // 예약 버튼 클릭
  document.getElementById("reserveBtn").addEventListener("click", function () {
    if (selectedDate) {
      alert("도서가 " + selectedDate + "에 예약되었습니다.");
      document.getElementById("modal").style.display = "none";
    }
  });
});
