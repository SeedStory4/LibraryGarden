(function($){
  var calendar;
  var selectedDate = null;

  function initModifyCalendar() {
    if (calendar) calendar.destroy();
    var el = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(el, {
      initialView: 'dayGridMonth',
      locale: 'ko',
      selectable: true,
      headerToolbar: { left: 'prev', center: 'title', right: 'next' },
      dayCellContent: d => d.date.getDate(),

      events: function(info, success) {
        var evs = (window.disabledDates || []).map(function(ds) {
          return { start: ds.replace(/\./g,'-'), display: 'background', backgroundColor: '#d94436' };
        });
        success(evs);
      },

      dateClick: function(info) {
        var clicked = info.dateStr;
        var dot = clicked.replace(/-/g, '.');

        // 과거 차단
        var today = new Date(); today.setHours(0,0,0,0);
        if (new Date(clicked) < today) {
          return alert('지난 날짜는 예약할 수 없습니다.');
        }
        // 예약불가차단
        if ((window.disabledDates||[]).indexOf(dot) !== -1) {
          var reason = window.disabledReasons && window.disabledReasons[dot];
          return alert(reason === '연체'
            ? '연체기간동안 예약이 불가합니다.'
            : '해당 날짜는 예약이 불가능합니다.');
        }

        // 선택 표시
        el.querySelectorAll('.fc-day-selected').forEach(function(e){ e.classList.remove('fc-day-selected'); });
        var cell = el.querySelector('[data-date="'+clicked+'"]');
        if (cell) cell.classList.add('fc-day-selected');

        selectedDate = dot;
        window.selectedDate = dot;
        document.getElementById('selectedDate').textContent = dot;
        document.getElementById('modifyBtn').disabled = !$('#agreeCheck').is(':checked');
      },

      datesSet: function() {
        // 선택 복원
        if (selectedDate) {
          var hy = selectedDate.replace(/\./g,'-');
          var sel = document.querySelector('[data-date="'+hy+'"]');
          if (sel) sel.classList.add('fc-day-selected');
        }
        // 예약불가 표시
        (window.disabledDates||[]).forEach(function(ds) {
          var eln = document.querySelector('[data-date="'+ds.replace(/\./g,'-')+'"]');
          if (eln) eln.classList.add('disabled-date');
        });
      }
    });
    calendar.render();
    window.myCalendar = calendar;
  }

  $(function(){
    $('.openReservationModifyModal').on('click', function(e){
	
		var status = $(this).data('status');
		if (status !== '예약중') {
		  // 예약중이 아니면 클릭 자체를 무시
		  return;
		}
		
      e.preventDefault();
      var btn = this;
      window.ridx = btn.dataset.ridx;
      window.lbidx = btn.dataset.lbidx;
      window.userNumber = btn.dataset.usernumber;
      var orig = btn.dataset.pickupdate; // yyyy-mm-dd

      // 불가일 가져오기
      $.getJSON(contextPath + '/user/bookReservation/getReservedDatesForModify.do', {
        lbidx: window.lbidx,
        userNumber: window.userNumber,
        ridx: window.ridx
      }).done(function(data){
        window.disabledDates = data.map(d=>d.date);
        window.disabledReasons = {};
        data.forEach(d=>window.disabledReasons[d.date] = d.reason);

        $('#modifyModal').show();
        initModifyCalendar();
        calendar.gotoDate(orig);
        calendar.removeAllEvents();
        calendar.refetchEvents();

        var el = document.getElementById('calendar');
        el.querySelectorAll('.fc-day-selected').forEach(function(e){ e.classList.remove('fc-day-selected'); });
        var sel = el.querySelector('[data-date="'+orig+'"]');
        if (sel) sel.classList.add('fc-day-selected');

        var dot = orig.replace(/-/g,'.');
        $('#selectedDate').text(dot);
        $('#modifyBtn').prop('disabled', false);
      }).fail(function(){
        alert('예약 현황을 가져오지 못했습니다.');
      });
    });

    $('#closeModal').on('click', function(){
      $('#modifyModal').hide();
      if (calendar) { calendar.destroy(); delete window.myCalendar; }
      selectedDate = null;
      delete window.ridx; delete window.lbidx; delete window.userNumber;
      window.disabledDates = [];
      window.disabledReasons = {};
      $('#selectedDate').text('선택 없음');
      $('#agreeCheck').prop('checked', false);
      $('#modifyBtn').prop('disabled', true);
    });

    $('#agreeCheck').on('change', function(){
      $('#modifyBtn').prop('disabled', !(this.checked && selectedDate));
    });

    $('#modifyBtn').on('click', function(){
      if (!$('#agreeCheck').is(':checked')) {
        return alert('동의를 먼저 해주시기 바랍니다.');
      }
      if (!selectedDate || !window.ridx) {
        return alert('예약 날짜를 선택해주세요.');
      }
      var pd = selectedDate.replace(/\./g,'-');
      $.post(contextPath + '/user/bookReservation/modifyReservation.do', {
        ridx: window.ridx,
        pickupDate: pd
      }, function(resp){
        alert(resp.message);
        if (resp.success) location.reload();
      }).fail(function(){
        alert('수정 요청 중 오류가 발생했습니다.');
      });
    });
  });
})(jQuery);