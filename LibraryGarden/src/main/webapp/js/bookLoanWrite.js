// select2
$(document).ready(function () {
    $('.js-example-basic-single').select2();
});

// 전역 상태 변수 추가
let currentPage = 1;
let currentPerPageNum = 12;

function numberCheck(page = currentPage, perPageNum = currentPerPageNum) {
    // 현재 상태 저장
    currentPage = page;
    currentPerPageNum = perPageNum;

    var userNumber = $('#userNumber').val();

    $.ajax({
        url: contextPath + '/admin/bookLoan/checkUserLoanStatus.do',
        type: 'GET',
        data: { userNumber: userNumber, page: page, perPageNum: perPageNum },
        dataType: 'json',
        success: function (response) {
            var userName = response.userName || '이름 없음';

            if (userName === '이름 없음') {
                alert('존재하지 않는 회원 번호입니다.');
                $('.loan-info').html('');
                $('#loanList').empty().append(`<tr><td colspan="10">대출 기록이 없습니다.</td></tr>`);
                $('#pagination').empty();
                $('#bookCode').val('');
                return;
            }

            var loanStatus = response.loanStatus || '데이터 없음';
            var loanList = response.loanList || [];
            var totalCount = response.totalCount || 0;
            var loanStatusColor = loanStatus.includes("이용가능") ? "green" : "red";

            $('.loan-info').html(
                userName + '(' + userNumber + ')님의 현재 대출가능여부는 ' +
                '<span class="' + loanStatusColor + ' bold">"' + loanStatus + '"</span>입니다.'
            );

            var tbody = $('#loanList');
            tbody.empty();

            if (Array.isArray(loanList) && loanList.length > 0) {
                loanList.forEach(function (item, index) {
                    if (item.delyn === 'Y') return;

                    var row = `
                        <tr id="loan-row-${item.lidx}">
                            <td>${(page - 1) * perPageNum + index + 1}</td>
                            <td><img src="${item.coverImg}" alt="${item.title}"></td>
                            <td>${item.title}</td>
                            <td>${item.author}</td>
                            <td>${item.code}</td>
                            <td>${item.loanDate}</td>
                            <td>${item.dueDate}</td>
                            <td>${item.returnDate || "-"}</td>
                            <td class="${item.status === '대출중' ? 'blue' : item.status === '연체반납' ? 'red' : 'green'}">
                                ${item.status}
                            </td>
                            <td>
                                ${item.status === '대출중' ? `
                                    <button class="btn btn-small btn-red mb-5" onClick="deleteLoan(${item.lidx})">삭제</button>
                                    <button class="btn btn-small btn-primary" onClick="returnLoan(${item.lidx})">반납</button>
                                ` : ''}
                            </td>
                        </tr>
                    `;
                    tbody.append(row);
                });

                var pageCount = Math.ceil(totalCount / perPageNum);
                var pagination = $('#pagination');
                pagination.empty();

                // ◀ 이전
                if (page > 1) {
                    pagination.append(`<li><a href="#" onclick="numberCheck(${page - 1}, ${perPageNum})">◀</a></li>`);
                }

                // 페이지 번호들
                for (let i = 1; i <= pageCount; i++) {
                    pagination.append(`
                        <li>
                            <a href="#" class="${i === page ? 'on' : ''}" onclick="numberCheck(${i}, ${perPageNum})">${i}</a>
                        </li>
                    `);
                }

                // ▶ 다음
                if (page < pageCount) {
                    pagination.append(`<li><a href="#" onclick="numberCheck(${page + 1}, ${perPageNum})">▶</a></li>`);
                }

            } else {
                tbody.append(`<tr><td colspan="10">대출 기록이 없습니다.</td></tr>`);
            }

            $('.book-list').removeClass('none');
        },
        error: function (xhr, status, error) {
            alert('등록된 회원 번호가 없습니다.');
        }
    });
}

// 도서 등록
function addBook() {
    var userNumber = $('#userNumber').val();
    var code = $('#bookCode').val();

    if (!userNumber || !code) {
        alert("회원번호와 도서구분 코드를 모두 입력해주세요.");
        return;
    }

    $.ajax({
        url: contextPath + '/admin/bookLoan/checkBookStatus.do',
        type: 'POST',
        data: { code: code },
        success: function (response) {
            if (response === "대출중" || response === "예약대기") {
                alert("현재 대출 중이거나 예약 대기 상태인 도서입니다.");
                $('#bookCode').val('');
                return;
            } else if (response === "없는 도서") {
                alert("없는 도서입니다.");
                $('#bookCode').val('');
                return;
            }

            $.ajax({
                url: contextPath + '/admin/bookLoan/addBookLoan.do',
                type: 'POST',
                data: { userNumber: userNumber, code: code },
                success: function (response) {
                    alert(response);
                    numberCheck();  // 현재 페이지 유지
                    $('#bookCode').val('');
                },
                error: function (xhr, status, error) {
                    alert("대출 등록 실패: " + xhr.responseText);
                    $('#bookCode').val('');
                }
            });
        },
        error: function (xhr, status, error) {
            alert("도서 상태 조회 오류: " + xhr.responseText);
            $('#bookCode').val('');
        }
    });
}

// 대여 삭제
function deleteLoan(lidx) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    $.ajax({
        url: contextPath + '/admin/bookLoan/deleteLoan.do',
        type: 'POST',
        data: { lidx: lidx },
        success: function (response) {
            alert(response);
            numberCheck();  // 현재 페이지 유지
        },
        error: function (xhr, status, error) {
            alert("대출 삭제 실패: " + xhr.responseText);
        }
    });
}

// 반납 처리
function returnLoan(lidx) {
    if (!confirm("반납 처리하시겠습니까?")) return;

    $.ajax({
        url: contextPath + '/admin/bookLoan/returnLoan.do',
        type: 'POST',
        data: { lidx: lidx },
        success: function (response) {
            alert(response);
            numberCheck(); 
        },
        error: function (xhr, status, error) {
            alert("반납 처리 실패: " + xhr.responseText);
        }
    });
}
