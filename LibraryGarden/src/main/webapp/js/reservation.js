document.addEventListener("DOMContentLoaded", function () {
  let selectedDate = null;
  window.selectedDate = null; // 다른 곳에서도 접근 가능하도록 설정
  
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
	// 캘린더에 예약 불가능 날짜(픽업날짜 기준 7일)를 배경으로 표시
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
	  // clickedDate는 문자열("yyyy-mm-dd")
	  let clickedDate = info.dateStr;
	  // Date 객체로 변환
	  let clickedDateObj = new Date(clickedDate);
	  let today = new Date();
	  today.setHours(0, 0, 0, 0); // 시간 정보 제거하여 날짜만 비교
	  
	  if (clickedDateObj < today) {
	    alert("지난 날짜는 예약할 수 없습니다.");
	    return;
	  }
	 
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

	  // 기존 선택된 날짜 스타일 제거
      document.querySelectorAll(".fc-day-selected").forEach((el) => {
        el.classList.remove("fc-day-selected");
      });

	  // 클릭한 날짜에 선택 스타일 추가
      let selectedDateEl = document.querySelector('[data-date="' + clickedDate + '"]');
      if (selectedDateEl) {
        selectedDateEl.classList.add("fc-day-selected");
        if (selectedDateEl.classList.contains("fc-day-other")) {
          selectedDateEl.style.color = "white";
        }
      }

      selectedDate = formattedDate;
	  window.selectedDate = formattedDate;
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
      }, 300);
    },
  });

  window.myCalendar = calendar;

  // 이용약관 체크박스에 따른 예약버튼 활성화
  document.getElementById("agreeCheck").addEventListener("change", function () {
    let reserveBtn = document.getElementById("reserveBtn");
    reserveBtn.disabled = !this.checked || !selectedDate;
  });

  // 예약 등록 버튼 클릭
  document.getElementById("reserveBtn").addEventListener("click", function () {
	// 체크박스가 체크되어 있지 않으면 경고창을 띄우기
	if (!document.getElementById("agreeCheck").checked) {
	    alert("동의를 먼저 해주시기 바랍니다.");
	    return;
	}
	
	 // 예약에 필요한 정보가 모두 있는지 확인
	 if (selectedDate && window.lbidx && $('#userNumber').val()) {
          // "yyyy.MM.dd" → "yyyy-MM-dd" 형식 변환
          const pickupDate = selectedDate.replace(/\./g, "-");
          $.ajax({
              url: contextPath + '/admin/bookReservation/registerReservation.do',
              type: 'POST',
              data: {
                  lbidx: window.lbidx, // 모달 열 때 저장한 도서 번호
                  userNumber: $('#userNumber').val(),
                  pickupDate: pickupDate
              },
              success: function (response) {
                  if (response.success) {
                      alert(response.message);
                      $('#reservationModal').hide();
					            // 이전 이벤트들 제거 후, 다시 refetch해서 현재 도서 예약 정보만 반영
					            if (window.myCalendar) {
					              window.myCalendar.removeAllEvents();
					              window.myCalendar.refetchEvents();
					            }
					          } else {
					            alert(response.message);
					          }
					        },
					        error: function () {
					          alert("예약 등록을 실패했습니다.");
					        }
					      });
					    } else {
					      alert("예약에 필요한 정보를 확인해주세요.");
					    }
					  });
  

});
