// js/reservation.js
(function($){
  var calendar;
  var calendarEl;
  var selectedDate = null;

  function initCalendar() {
    // 이전 달력 인스턴스 파기
    if (calendar) {
      calendar.destroy();
    }
    calendarEl = document.getElementById('calendar');

    calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth',
      locale: 'ko',
      selectable: true,
      headerToolbar: { left: 'prev', center: 'title', right: 'next' },
      dayCellContent: function(arg){ return arg.date.getDate(); },

      // 예약불가일을 배경 이벤트로 표시
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
        // info.date는 로컬 타임존의 Date 객체
        var clickedDate = new Date(info.date);
        clickedDate.setHours(0,0,0,0);

        var today = new Date();
        today.setHours(0,0,0,0);

        // 1) 과거 날짜 차단
        if (clickedDate < today) {
          alert('지난 날짜는 예약할 수 없습니다.');
          return;
        }

        // 2) 예약불가일자 차단
        var iso = info.dateStr;               // "yyyy-mm-dd"
        var formatted = iso.replace(/-/g,'.'); // "yyyy.MM.dd"
        if ((window.disabledDates||[]).indexOf(formatted) !== -1) {
          var reason = window.disabledReasons && window.disabledReasons[formatted];
          if (reason === '연체') {
            alert('연체기간동안 예약이 불가합니다.');
          } else {
            alert('해당 날짜는 예약이 불가능합니다.');
          }
          return;
        }

        // 3) 선택 표시 갱신
        calendarEl.querySelectorAll('.fc-day-selected').forEach(function(el){
          el.classList.remove('fc-day-selected');
        });
        var cell = calendarEl.querySelector('[data-date="'+iso+'"]');
        if (cell) cell.classList.add('fc-day-selected');

        // 4) 전역 상태 저장 & 버튼 활성화
        selectedDate = formatted;
        window.selectedDate = formatted;
        document.getElementById('selectedDate').textContent = formatted;
        document.getElementById('reserveBtn').disabled = !$('#agreeCheck').is(':checked');
      },

      // 렌더 완료나 월 이동 후 호출
      datesSet: function() {
        // 이전 선택 날짜 복원
        if (selectedDate) {
          var hy = selectedDate.replace(/\./g,'-');
          var sel = calendarEl.querySelector('[data-date="'+hy+'"]');
          if (sel) sel.classList.add('fc-day-selected');
        }
        // 예약불가일 표시
        (window.disabledDates||[]).forEach(function(ds){
          var el = calendarEl.querySelector('[data-date="'+ds.replace(/\./g,'-')+'"]');
          if (el) el.classList.add('disabled-date');
        });
        // 비현월 날짜 스타일 고정
        setTimeout(function(){
          calendarEl.querySelectorAll('.fc-day-other .fc-daygrid-day-number')
            .forEach(function(el){
              el.style.opacity = '1';
              el.style.color = '#666';
              el.style.fontWeight = 'bold';
            });
        }, 200);
      }
    });

    calendar.render();
    window.myCalendar = calendar;
  }

  $(function(){
    // 도서 제목 클릭 → 모달 표시 & AJAX로 disabledDates 로드
    $('.openReservationModal').on('click', function(e){
      e.preventDefault();
      var status = $(this).closest('tr').find('td').last().text().trim();
      if (status === '대출불가') {
        return alert('예약이 불가한 도서입니다.');
      }
      window.lbidx = $(this).data('lbidx');
      var userNumber = $('#userNumber').val();

      $.getJSON(contextPath + '/admin/bookReservation/getReservedDates.do', {
        lbidx: window.lbidx,
        userNumber: userNumber
      })
      .done(function(data){
        window.disabledDates = data.map(function(d){ return d.date; });
        window.disabledReasons = {};
        data.forEach(function(d){ window.disabledReasons[d.date] = d.reason; });

        $('#reservationModal').show();
        initCalendar();
      })
      .fail(function(){
        alert('예약 현황을 불러오지 못했습니다.');
      });
    });

    // 모달 닫기
    $('#closeModal').on('click', function(){
      $('#reservationModal').hide();
      if (calendar) {
        calendar.destroy();
        delete window.myCalendar;
      }
      selectedDate = null;
      delete window.lbidx;
      window.disabledDates = [];
      window.disabledReasons = {};
      $('#selectedDate').text('선택 없음');
      $('#agreeCheck').prop('checked', false);
      $('#reserveBtn').prop('disabled', true);
    });

    // 약관 체크 → 예약 버튼 토글
    $('#agreeCheck').on('change', function(){
      $('#reserveBtn').prop('disabled', !(this.checked && selectedDate));
    });

    // 예약 등록
    $('#reserveBtn').on('click', function(){
      if (!$('#agreeCheck').is(':checked')) {
        return alert('동의를 먼저 해주세요.');
      }
      if (!selectedDate || !window.lbidx || !$('#userNumber').val()) {
        return alert('예약에 필요한 정보를 확인해주세요.');
      }
      $.post(contextPath + '/admin/bookReservation/registerReservation.do', {
        lbidx: window.lbidx,
        userNumber: $('#userNumber').val(),
        pickupDate: selectedDate.replace(/\./g,'-')
      }, function(res){
        alert(res.message);
        if (res.success) {
          $('#reservationModal').hide();
          initCalendar();
        }
      })
      .fail(function(){
        alert('예약 등록을 실패했습니다.');
      });
    });

    // 회원번호 확인 함수 (JSP에서 호출)
    window.numberCheck = function() {
      var num = $('#userNumber').val().trim();
      if (!num) {
        return alert('회원번호를 입력해주세요.');
      }
      $.getJSON(contextPath + '/admin/bookReservation/checkUser.do', { userNumber: num })
        .done(function(r){
          if (r.exists) {
            window.location.href = contextPath
              + '/admin/bookReservation/bookReservationWrite.do?userNumber='
              + encodeURIComponent(num);
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
