// reservationModify.js

document.addEventListener("DOMContentLoaded", function () {
  // 전역 변수 선언
  let selectedDate = null;
  window.selectedDate = null;

  // FullCalendar 초기화
  const calendarEl = document.getElementById("calendar");
  const calendar = new FullCalendar.Calendar(calendarEl, {
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
    // 숫자만 표시
    dayCellContent: arg => arg.date.getDate(),
    // 예약 불가일자 배경 이벤트
    events: (fetchInfo, successCallback) => {
      if (window.disabledDates) {
        const evts = window.disabledDates.map(dateStr => ({
          start: dateStr.replace(/\./g, "-"),
          display: "background",
          backgroundColor: "#d94436",
          color: "#fff"
        }));
        successCallback(evts);
      } else {
        successCallback([]);
      }
    },
    // 날짜 클릭
    dateClick: info => {
      const clicked = info.dateStr;               // "yyyy-mm-dd"
      const clickedDot = clicked.replace(/-/g, "."); // "yyyy.MM.dd"
      const td = info.dayEl;

      // 과거 날짜 차단
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      if (new Date(clicked) < today) {
        alert("지난 날짜는 예약할 수 없습니다.");
        return;
      }

      // 예약 불가 차단
      if (window.disabledDates && window.disabledDates.includes(clickedDot)) {
        const reason = window.disabledReasons[clickedDot];
        alert(reason === "연체"
          ? "연체기간동안 예약이 불가합니다."
          : "해당 날짜는 예약이 불가능합니다.");
        return;
      }

      // 선택 스타일
      document.querySelectorAll(".fc-day-selected").forEach(el =>
        el.classList.remove("fc-day-selected")
      );
      td.classList.add("fc-day-selected");

      // 전역 저장 및 UI 반영
      selectedDate = clickedDot;
      window.selectedDate = clickedDot;
      document.getElementById("selectedDate").textContent = clickedDot;

      // **무조건** 버튼 활성화
      document.getElementById("modifyBtn").disabled = false;
    },
    // 뷰 이동 시 / 렌더링 후
    datesSet: () => {
      // 기존 선택일 유지
      if (window.selectedDate) {
        const selHyphen = window.selectedDate.replace(/\./g, "-");
        const el = document.querySelector(`[data-date="${selHyphen}"]`);
        if (el) el.classList.add("fc-day-selected");
      }
      // 예약 불가일자 표시
      if (window.disabledDates) {
        window.disabledDates.forEach(dateStr => {
          const hyphen = dateStr.replace(/\./g, "-");
          const el = document.querySelector(`[data-date="${hyphen}"]`);
          if (el) el.classList.add("disabled-date");
        });
      }
      // 다른 달 날짜 강제 스타일
      setTimeout(() => {
        document.querySelectorAll(".fc-day-other .fc-daygrid-day-number").forEach(el => {
          el.style.opacity = "1";
          el.style.color = "#666";
          el.style.fontWeight = "bold";
          el.style.display = "block";
          el.style.visibility = "visible";
          el.style.zIndex = "10";
        });
      }, 50);
    },
  });

  calendar.render();
  window.myCalendar = calendar;

  // "동의" 체크박스 토글 (선택일 없으면 다시 disable)
  document.getElementById("agreeCheck").addEventListener("change", function () {
    if (!window.selectedDate) {
      // 날짜가 없으면 체크 해제
      this.checked = false;
      document.getElementById("modifyBtn").disabled = true;
    }
  });

  // 수정 버튼 클릭
  document.getElementById("modifyBtn").addEventListener("click", function () {
    // 1) 동의 확인
    if (!document.getElementById("agreeCheck").checked) {
      alert("동의를 먼저 해주시기 바랍니다.");
      return;
    }
    // 2) 날짜/PK 확인
    if (!window.selectedDate || !window.ridx) {
      alert("예약 날짜를 선택해주세요.");
      return;
    }
    // 3) AJAX 요청
    const pickupDate = window.selectedDate.replace(/\./g, "-");
    $.post(
      contextPath + "/admin/bookReservation/modifyReservation.do",
      { ridx: window.ridx, pickupDate: pickupDate },
      resp => {
        alert(resp.message);
        if (resp.success) location.reload();
      }
    );
  });

  // 제목 클릭 → 수정 팝업 열기 + 불가일 로드
  document.querySelectorAll(".openReservationModifyModal").forEach(btn => {
    btn.addEventListener("click", function (e) {
      e.preventDefault();
      // data-pickupdate: "yyyy-MM-dd"
      const origHyphen = this.dataset.pickupdate;
      const origDot    = origHyphen.replace(/-/g, ".");
      window.ridx       = +this.dataset.ridx;
      window.lbidx      = +this.dataset.lbidx;
      window.userNumber = this.dataset.usernumber;
      window.selectedDate = origDot;

      // AJAX로 수정용 불가일자 조회
      $.get(
        contextPath + "/admin/bookReservation/getReservedDatesForModify.do",
        { lbidx: window.lbidx, userNumber: window.userNumber, ridx: window.ridx }
      )
      .done(data => {
        window.disabledDates   = data.map(d => d.date);
        window.disabledReasons = {};
        data.forEach(d => window.disabledReasons[d.date] = d.reason);

        // 달력 이동 & 재렌더
        calendar.gotoDate(origHyphen);
        calendar.removeAllEvents();
        calendar.render();

        // 선택일 복원
        document.querySelectorAll(".fc-day-selected").forEach(el =>
          el.classList.remove("fc-day-selected")
        );
        const selEl = document.querySelector(`[data-date="${origHyphen}"]`);
        if (selEl) selEl.classList.add("fc-day-selected");
        document.getElementById("selectedDate").textContent = origDot;

        // 버튼 활성화
        document.getElementById("modifyBtn").disabled = false;
      })
      .fail(() => {
        alert("예약 현황을 가져오지 못했습니다.");
      });

      // 팝업 열기
      document.getElementById("modifyModal").style.display = "flex";
      calendar.updateSize();
    });
  });

  // 팝업 닫기
  document.getElementById("closeModal").addEventListener("click", function () {
    window.selectedDate = null;
    document.getElementById("selectedDate").textContent = "선택 없음";
    document.querySelectorAll(".fc-day-selected").forEach(el =>
      el.classList.remove("fc-day-selected")
    );
    document.getElementById("modifyModal").style.display = "none";
    document.getElementById("modifyBtn").disabled = true;
  });

});
