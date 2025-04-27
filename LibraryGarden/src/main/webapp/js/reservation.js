// js/reservation.js
(function($){
  var calendar, calendarEl, selectedDate = null;
 
  function initCalendar() {
    // 1) 이전 인스턴스 파기
    if (calendar) {
      calendar.destroy();
    }
    // 2) 컨테이너 요소 가져오기
    calendarEl = document.getElementById('calendar');
    // 3) 새 FullCalendar 생성
    calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth',
      locale: 'ko',
      selectable: true,
      headerToolbar: { left: 'prev', center: 'title', right: 'next' },
      dayCellContent: arg => arg.date.getDate(),

      // 예약불가일 배경 이벤트
      events: function(fetchInfo, success) {
        var evs = (window.disabledDates||[]).map(function(ds){
          return {
            start: ds.replace(/\./g,'-'),
            display: 'background',
            backgroundColor: '#d94436'
          };
        });
        success(evs);
      },

      // 날짜 클릭 핸들러
      dateClick: function(info) {
        var clicked = new Date(info.date);
        clicked.setHours(0,0,0,0);
        var today = new Date(); today.setHours(0,0,0,0);

        // A) 과거 차단
        if (clicked < today) {
          return alert('지난 날짜는 예약할 수 없습니다.');
        }

        // B) 예약불가일 차단
        var iso       = info.dateStr;            // "yyyy-mm-dd"
        var formatted = iso.replace(/-/g,'.');   // "yyyy.MM.dd"
        if ((window.disabledDates||[]).includes(formatted)) {
          var reason = window.disabledReasons && window.disabledReasons[formatted];
          return alert(
            reason === '연체'
              ? '연체기간동안 예약이 불가합니다.'
              : '해당 날짜는 예약이 불가능합니다.'
          );
        }
    
        // 7일 전체 구간 겹침 검사
        for (var offset = 1; offset < 8; offset++) {
          var d = new Date(info.date);
          d.setDate(d.getDate() + offset);
          var dayIso = d.toISOString().slice(0,10);       // "yyyy-mm-dd"
          var dayFmt = dayIso.replace(/-/g, '.');         // "yyyy.MM.dd"
          if ((window.disabledDates||[]).includes(dayFmt)) {
            return alert('선택하신 예약 기간이 기존 예약/대출·연체 기간과 겹칩니다.');
          }
        }

        // C) 선택 표시 갱신
        calendarEl.querySelectorAll('.fc-day-selected')
          .forEach(el => el.classList.remove('fc-day-selected'));
        var cell = calendarEl.querySelector('[data-date="'+iso+'"]');
        if (cell) cell.classList.add('fc-day-selected');

        // D) 전역 저장 & 버튼 활성화
        selectedDate = formatted;
        window.selectedDate = formatted;
        $('#selectedDate').text(formatted);
        $('#reserveBtn').prop('disabled', !$('#agreeCheck').is(':checked'));
      },

      // 렌더/월 이동 후
      datesSet: function() {
        // 지난 선택 복원
        if (selectedDate) {
          var hy = selectedDate.replace(/\./g,'-');
          var el = calendarEl.querySelector('[data-date="'+hy+'"]');
          if (el) el.classList.add('fc-day-selected');
        }
        // 예약불가일 표시
        (window.disabledDates||[]).forEach(ds => {
          var el = calendarEl.querySelector('[data-date="'+ds.replace(/\./g,'-')+'"]');
          if (el) el.classList.add('disabled-date');
        });
        // 비현월 날짜 스타일
        setTimeout(() => {
          calendarEl.querySelectorAll('.fc-day-other .fc-daygrid-day-number')
            .forEach(n => {
              n.style.opacity = '1';
              n.style.color = '#666';
              n.style.fontWeight = 'bold';
            });
        }, 200);
      }
    });

    calendar.render();
    window.myCalendar = calendar;
  }

  $(function(){
    // 만약 isLoggedIn이 undefined면 false로 초기화
    if (typeof window.isLoggedIn === "undefined") {
      window.isLoggedIn = false;
    }
    
    // — 모달 열기 & 예약 현황 로드
    $('.openReservationModal').on('click', function(e){
      e.preventDefault();
      
      // 0) 로그인 체크
      if (!window.isLoggedIn) {
        const returnUrl = encodeURIComponent(location.pathname + location.search);
        location.href = contextPath + '/user/user/userLogin.do?returnUrl=' + returnUrl;
        return;
      }

      // 1) UI 초기화
      selectedDate = null;
      delete window.selectedDate;
      $('#selectedDate').text('선택 없음');
      $('#agreeCheck').prop('checked', false);
      $('#reserveBtn').prop('disabled', true);

      // 2) 도서 상태 체크
      var status = $(this).closest('tr').find('td').last().text().trim();
      if (status === '대출불가') {
        return alert('예약이 불가한 도서입니다.');
      }

      // 3) 글로벌 lbidx/userNumber
      window.lbidx    = $(this).data('lbidx');
      var userNumber  = $('#userNumber').val();

      // 4) 중복 예약·수령완료 체크 API 호출
      $.getJSON(
        contextPath + '/user/bookReservation/checkDuplicateReservation.do',
        { lbidx: window.lbidx, userNumber: userNumber }
      )
      .done(function(resp){
        if (resp.duplicate) {
          // 중복이면 여기서 종료
          return alert('이미 예약 중이거나 수령완료한 도서입니다.');
        }

        // 5) 중복 아니면 달력 데이터 로드
        $.getJSON(
          contextPath + '/user/bookReservation/getReservedDates.do',
          { lbidx: window.lbidx, userNumber: userNumber }
        )
        .done(function(data){
          window.disabledDates   = data.map(d => d.date);
          window.disabledReasons = {};
          data.forEach(d => window.disabledReasons[d.date] = d.reason);

          $('#reservationModal').show();
          initCalendar();
        })
        .fail(function(){
          alert('예약 현황을 불러오지 못했습니다.');
        });
      })
      .fail(function(){
        alert('예약 가능 여부를 확인할 수 없습니다.');
      });
    });

    // — 모달 닫기
    $('#closeModal').on('click', function(){
      $('#reservationModal').hide();
      if (calendar) {
        calendar.destroy();
        delete window.myCalendar;
      }
      selectedDate = null;
      delete window.lbidx;
      window.disabledDates   = [];
      window.disabledReasons = {};
      $('#selectedDate').text('선택 없음');
      $('#agreeCheck').prop('checked', false);
      $('#reserveBtn').prop('disabled', true);
    });

    // — 약관 체크 → 버튼 토글
    $('#agreeCheck').on('change', function(){
      $('#reserveBtn').prop('disabled', !(this.checked && selectedDate));
    });

    // — 예약 등록
    $('#reserveBtn').off('click').on('click', function(e){
      e.preventDefault();

      if (!$('#agreeCheck').is(':checked')) {
        return alert('동의를 먼저 해주세요.');
      }
      if (!selectedDate || !window.lbidx || !$('#userNumber').val()) {
        return alert('예약에 필요한 정보를 확인해주세요.');
      }

      $.post(
        contextPath + '/user/bookReservation/registerReservation.do',
        {
          lbidx:      window.lbidx,
          userNumber: $('#userNumber').val(),
          pickupDate: selectedDate.replace(/\./g,'-')
        }
      )
      .always(function(res){
        if (res && res.message) alert(res.message);
        window.location.href = window.location.href;
      });
    });

    // — 회원번호 확인
    window.numberCheck = function(){
      var num = $('#userNumber').val().trim();
      if (!num) {
        return alert('회원번호를 입력해주세요.');
      }
      $.getJSON(contextPath + '/user/bookReservation/checkUser.do', { userNumber: num })
        .done(function(r){
          if (r.exists) {
            window.location.href =
              contextPath + '/user/bookReservation/bookReservationWrite.do?userNumber=' +
              encodeURIComponent(num);
          } else {
            alert(r.message);
          }
        })
        .fail(function(){
          alert('회원 조회 중 오류가 발생했습니다.');
        });
    };
  });

})(jQuery);
