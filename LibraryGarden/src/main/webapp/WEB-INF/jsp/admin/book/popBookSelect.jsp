<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>도서선택 팝업</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css" />
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body>

    <!-- 버튼 -->
    <button id="openModal">도서선택</button>

    <!-- 도서선택 모달 -->
    <div id="modal" class="modal">
      <div class="modal-content w-1000">
        <div class="title-container">
          <div class="title">도서선택</div>
          <div class="title-line"></div>
        </div>

        <!-- 컨텐츠 영역 -->
        <div class="book-list">
          <div class="search flex gap-20 justify-center">
            <select class="js-example-basic-single select shadow" name="state">
              <option value="title">제목</option>
              <option value="author">저자</option>
            </select>
            <input type="text" class="shadow w-520">						
            <button class="btn btn-primary btn-small">검색</button>
          </div>
          <div class="table">
            <table>
              <colgroup>
                <col width="8%">
                <col width="9%">
                <col>
                <col width="16%">
                <col width="15%">
                <col width="13%">
                <col width="12%">
              </colgroup>
              <thead>
                <tr>
                  <th>번호</th>
                  <th>표지</th>
                  <th>제목</th>
                  <th>저자</th>
                  <th>출판사</th>
                  <th>출판년도</th>
                  <th>선택</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
                <tr>
                  <td>1</td>
                  <td><img src="https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg" alt="채식주의자"></td>
                  <td>채식주의자</td>
                  <td>한강</td>
                  <td>창비</td>
                  <td>2025.03.14</td>
                  <td><button class="btn btn-small btn-primary">선택</button></td>
                </tr>
              </tbody>
            </table>
            <ul class="paging flex w-270 justify-spacebtween">
              <li><a href="#">◀</a></li>
              <li><a href="#" class="on">1</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li><a href="#">▶</a></li>
            </ul>	
          </div>
        </div>

        <!-- 버튼 영역 -->
        <div class="button-group">
          <button class="btn btn-red" id="closeModal">취소</button>
        </div>
      </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/reservation.js"></script>
    
    <script>
      // select2
      $(document).ready(function() {
        $('.js-example-basic-single').select2();
      });
    </script>
	
</body>
</html>
