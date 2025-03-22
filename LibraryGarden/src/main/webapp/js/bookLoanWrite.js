// select2
$(document).ready(function() {
	$('.js-example-basic-single').select2();
});

function numberCheck() {
    var userNumber = $('#userNumber').val();

    $.ajax({
        url: '/sht_webapp/admin/bookLoan/checkUserLoanStatus.do', 
        // ↑ contextPath 직접 작성 또는 동적으로 치환
        type: 'GET',
        data: { userNumber: userNumber },
        dataType: 'json',
        success: function(response) {
            console.log("AJAX 성공: ", response);

            var loanStatus = response.loanStatus || '데이터 없음';
            var userName   = response.userName   || '이름 없음';
            var loanList   = response.loanList   || [];

            $('.loan-info').html(
                userName + '(' + userNumber + ')님의 현재 대출가능여부는 ' +
                '<span class="red bold">"' + loanStatus + '"</span>입니다.'
            );

            var tbody = $('#loanList');
            tbody.empty();

            if (Array.isArray(loanList) && loanList.length > 0) {
                loanList.forEach(function(item, index) {
                    // ES6 템플릿 문자열 사용 (JSP가 관여 못함)
                    var row = `
                        <tr>
                            <td>${index + 1}</td>
                            <td><img src="${item.coverImg}" alt="${item.title}"></td>
                            <td>${item.title}</td>
                            <td>${item.author}</td>
                            <td>${item.code}</td>
                            <td>${item.loanDate}</td>
                            <td>${item.dueDate}</td>
                            <td>${item.returnDate || "-"}</td>
                            <td class="${item.status === '대여중' ? 'blue' : 'green'}">
                                ${item.status}
                            </td>
                            <td>
                                <button class="btn btn-small btn-red mb-5"
                                    onClick="deleteLoan(${item.lidx})">삭제</button>
                                <button class="btn btn-small btn-primary"
                                    onClick="returnLoan(${item.lidx})">반납</button>
                            </td>
                        </tr>
                    `;
                    tbody.append(row);
                });
            } else {
                tbody.append(`
                    <tr>
                        <td colspan="10">대출 기록이 없습니다.</td>
                    </tr>
                `);
            }

            $('.book-list').removeClass('none');
        },
        error: function(xhr, status, error) {
            console.error("AJAX 오류: ", error);
            console.error("상태 코드: ", xhr.status);
            console.error("응답 텍스트: ", xhr.responseText);
            alert('회원 정보를 불러오는 데 실패했습니다.');
        }
    });
}
