document.addEventListener("DOMContentLoaded", function () {
  let selectedDate = null;
  let isCalendarRendered = false; // ⭐ 모달 최초 열릴 때 한 번만 render()

  let calendarEl = document.getElementById("calendar");
  let calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "ko",
    selectable: true,
    showNonCurrentDates: true,
    fixedWeekCount: false,
    headerToolbar: {
      left: "prev",
      center: "title",
      right: "next",
    },
    dayCellContent: function (arg) {
      return arg.date.getDate();
    },
    events: function (fetchInfo, successCallback, failureCallback) {
      if (window.disabledDates) {
        let disabledEvents = window.disabledDates.map(function (dateStr) {
          return {
            start: dateStr.replace(/\./g, "-"),
            display: "background",
            backgroundColor: "#d94436",
            color: "#fff"
          };
        });
        successCallback(disabledEvents);
      } else {
        successCallback([]);
      }
    },
    dateClick: function (info) {
      let clickedDate = info.dateStr;
      let formattedDate = clickedDate.replace(/-/g, ".");

      if (window.disabledDates && window.disabledDates.includes(formattedDate)) {
        const reason = window.disabledReasons[formattedDate];
        if (reason === "연체") {
          alert("연체기간동안 예약이 불가합니다.");
        } else {
          alert("해당 날짜는 예약이 불가능합니다.");
        }
        return;
      }

      document.querySelectorAll(".fc-day-selected").forEach((el) => {
        el.classList.remove("fc-day-selected");
      });

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

      if (window.disabledDates) {
        window.disabledDates.forEach(function (dateStr) {
          let disabledDateEl = document.querySelector('[data-date="' + dateStr.replace(/\./g, "-") + '"]');
          if (disabledDateEl) {
            disabledDateEl.classList.add("disabled-date");
          }
        });
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

  window.myCalendar = calendar;
  window.isCalendarRendered = isCalendarRendered;

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
