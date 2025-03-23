// select2
$(document).ready(function() {
    $('.js-example-basic-single').select2();
});

function numberCheck(page = 1, perPageNum = 12) {
    var userNumber = $('#userNumber').val();

    $.ajax({
        url: '/sht_webapp/admin/bookLoan/checkUserLoanStatus.do',
        type: 'GET',
        data: { userNumber: userNumber, page: page, perPageNum: perPageNum },
        dataType: 'json',
        success: function(response) {
            var userName = response.userName || '이름 없음';

            // 없는 회원일 경우 alert 처리
            if (userName === '이름 없음') {
                alert('존재하지 않는 회원 번호입니다.');
                // 화면 초기화
                $('.loan-info').html('');
                $('#loanList').empty().append(`<tr><td colspan="10">대출 기록이 없습니다.</td></tr>`);
                $('#pagination').empty();
                return;  // 더 이상 처리하지 않음
            }

            var loanStatus = response.loanStatus || '데이터 없음';
            var loanList = response.loanList || [];
            var totalCount = response.totalCount || 0;

            // 이용 가능 여부의 색상 처리
            var loanStatusColor = (loanStatus.includes("이용가능")) ? "green" : "red";

            $('.loan-info').html(
                userName + '(' + userNumber + ')님의 현재 대출가능여부는 ' +
                '<span class="' + loanStatusColor + ' bold">"' + loanStatus + '"</span>입니다.'
            );

            var tbody = $('#loanList');
            tbody.empty();

            if (Array.isArray(loanList) && loanList.length > 0) {
                loanList.forEach(function(item, index) {
                    var row = `
                        <tr>
                            <td>${(page - 1) * perPageNum + index + 1}</td>
                            <td><img src="${item.coverImg}" alt="${item.title}"></td>
                            <td>${item.title}</td>
                            <td>${item.author}</td>
                            <td>${item.code}</td>
                            <td>${item.loanDate}</td>
                            <td>${item.dueDate}</td>
                            <td>${item.returnDate || "-"}</td>
                            <td class="${item.status === '대여중' ? 'blue' : item.status === '연체반납' ? 'red' : 'green'}">
                                ${item.status}
                            </td>
                            <td>
                                ${item.status === '대여중' ? `
                                    <button class="btn btn-small btn-red mb-5" onClick="deleteLoan(${item.lidx})">삭제</button>
                                    <button class="btn btn-small btn-primary" onClick="returnLoan(${item.lidx})">반납</button>
                                ` : ''}
                            </td>
                        </tr>
                    `;
                    tbody.append(row);
                });

                // 페이지 번호 표시
                var pageCount = Math.ceil(totalCount / perPageNum);
                var pagination = $('#pagination');
                pagination.empty();

                // 이전 페이지 버튼
                if (page > 1) {
                    pagination.append(`
                        <li><a href="#" onclick="numberCheck(${page - 1}, ${perPageNum})">◀</a></li>
                    `);
                }

                // 페이지 번호 표시
                for (let i = 1; i <= pageCount; i++) {
                    pagination.append(`
                        <li>
                            <a href="#" class="${i === page ? 'on' : ''}" onclick="numberCheck(${i}, ${perPageNum})">${i}</a>
                        </li>
                    `);
                }

                // 다음 페이지 버튼
                if (page < pageCount) {
                    pagination.append(`
                        <li><a href="#" onclick="numberCheck(${page + 1}, ${perPageNum})">▶</a></li>
                    `);
                }

            } else {
                tbody.append(`<tr><td colspan="10">대출 기록이 없습니다.</td></tr>`);
            }

            $('.book-list').removeClass('none');
        },
        error: function(xhr, status, error) {
            alert('등록된 회원 번호가 없습니다.');
        }
    });
}
