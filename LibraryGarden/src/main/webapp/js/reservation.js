document.addEventListener("DOMContentLoaded", function () {
  let selectedDate = null;
  let calendarEl = document.getElementById("calendar");
  let calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "ko",
    selectable: true,
    showNonCurrentDates: true, // 해당 월에 포함되지 않는 날짜 숨김
    fixedWeekCount: false, // 실제 주 수만 표시
    headerToolbar: {
      left: "prev",
      center: "title",
      right: "next",
    },
    dayCellContent: function (arg) {
      return arg.date.getDate();
    },
    dateClick: function (info) {
      let clickedDate = info.dateStr;
      let formattedDate = clickedDate.replace(/-/g, ".");

      // 예약 불가능한 날짜 예시
      let disabledDates = ["2025.03.01", "2025.03.02", "2025.03.18"];
      if (disabledDates.includes(formattedDate)) {
        alert("해당 날짜는 예약이 불가능합니다.");
        return;
      }

      // 기존에 선택된 날짜 초기화
      document.querySelectorAll(".fc-day-selected").forEach((el) => {
        el.classList.remove("fc-day-selected");
      });

      // 선택한 날짜에 스타일 적용
      let selectedDateEl = document.querySelector('[data-date="' + clickedDate + '"]');
      if (selectedDateEl) {
        selectedDateEl.classList.add("fc-day-selected");
        if (selectedDateEl.classList.contains("fc-day-other")) {
          selectedDateEl.style.color = "white";
        }
      }

      selectedDate = formattedDate;
      document.getElementById("selectedDate").textContent = selectedDate;
      document.getElementById("reserveBtn").disabled = false;
    },
    datesSet: function () {
      if (selectedDate) {
        let selectedDateEl = document.querySelector('[data-date="' + selectedDate.replace(/\./g, "-") + '"]');
        if (selectedDateEl) {
          selectedDateEl.classList.add("fc-day-selected");
        }
      }
      setTimeout(() => {
        document.querySelectorAll(".fc-day-other .fc-daygrid-day-number").forEach((el) => {
          el.style.opacity = "1";
          el.style.color = "#666";
          el.style.fontWeight = "bold";
          el.style.display = "block";
          el.style.visibility = "visible";
          el.style.zIndex = "10";
        });
      }, 200);
    },
  });
  
  calendar.render();
  // 달력 객체를 전역 변수에 저장 (모달 열기 시 접근 가능)
  window.myCalendar = calendar;
  
  // 동의 체크박스 이벤트, 예약 버튼 등 기타 코드...
  document.getElementById("agreeCheck").addEventListener("change", function () {
    let reserveBtn = document.getElementById("reserveBtn");
    reserveBtn.disabled = !this.checked || !selectedDate;
  });

  document.getElementById("reserveBtn").addEventListener("click", function () {
    if (selectedDate) {
      alert("도서가 " + selectedDate + "에 예약되었습니다.");
      document.getElementById("reservationModal").style.display = "none";
    }
  });
});
