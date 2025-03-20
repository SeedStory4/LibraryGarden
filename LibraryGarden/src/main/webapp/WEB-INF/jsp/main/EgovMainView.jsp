<%--
  Class Name : EgovMainView.jsp 
  Description : 메인화면
  Modification Information
 
      수정일         수정자                   수정내용
    -------    --------    ---------------------------
     2011.08.31   JJY       경량환경 버전 생성
 
    author   : 실행환경개발팀 JJY
    since    : 2011.08.31 
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Language" content="ko" >
<title>INDEX</title>
<style>
  table { width: 100%; border-collapse: collapse; min-width: 1200px; }
  table td, table th { border: 1px solid #333; padding: 10px; }
</style>
</head>
<body>
  <table>
    <thead>
      <tr>
        <th colspan="3">화면</th>
        <th>작성자</th>
        <th>파일명</th>
        <th>컨트롤러명</th>
      </tr>
    </thead>
    
    <tbody>
      <!-- 사용자 -->
      <!-- 메인 -->
      <tr>
        <td rowspan="17">user</td>
        <td></td>
        <td></td>
        <td>채형찬</td>
        <td><a href="">main</a></td>
        <td>MainController</td>
      </tr>
  
      <!-- 도서조회 -->
      <tr>
        <td rowspan="3">book</td>
        <td>목록</td>
        <td rowspan="2">김시연</td>
        <td><a href="">bookList</a></td>
        <td rowspan="2">Book1Controller</td>
      </tr>
      <tr>
        <td>상세</td>
        <td><a href="">bookDetail</a></td>
      </tr>
      <tr>
        <td>도서예약팝업</td>
        <td>김이슬</td>
        <td><a href="<c:url value='/user/book/popBookReservationWrite'/>">popBookReservationWrite</a></td>
        <td>Book2Controller</td>
      </tr>
  
      <!-- 희망도서 신청 -->
      <tr>
        <td>bookRequest</td>
        <td>신청</td>
        <td>김시연</td>
        <td><a href="">bookRequestWrite</a></td>
        <td>BookRequestController</td>
      </tr>
  
      <!-- 회원가입 -->
      <tr>
        <td rowspan="7">user</td>
        <td>회원가입 약관동의</td>
        <td rowspan="7">채형찬</td>
        <td><a href="">userPrivacyPolicy</a></td>
        <td rowspan="7">UserController</td>
      </tr>
      <tr>
        <td>회원가입 정보 입력</td>
        <td><a href="">userJoin</a></td>
      </tr>
      <tr>
        <td>로그인</td>
        <td><a href="">userLogin</a></td>
      </tr>
      <tr>
        <td>ID 찾기</td>
        <td><a href="">userSearchId</a></td>
      </tr>
      <tr>
        <td>ID 찾기 결과</td>
        <td><a href="">userFoundId</a></td>
      </tr>
      <tr>
        <td>PW 찾기</td>
        <td><a href="">userSearchPassword</a></td>
      </tr>
      <tr>
        <td>PW 찾기 결과</td>
        <td><a href="">userFoundPassword</a></td>
      </tr>
  
      <!-- 마이페이지 -->
      <tr>
        <td rowspan="5">myPage</td>
        <td>대출이력관리</td>
        <td rowspan="2">김이슬</td>
        <td><a href="">mypageLoanList</a></td>
        <td rowspan="2">MyPage1Controller</td>
      </tr>
      <tr>
        <td>예약관리</td>
        <td><a href="">myPageReservationList</a></td>
      </tr>
      <tr>
        <td>도서신청관리</td>
        <td>김시연</td>
        <td><a href="">myPageRequestList</a></td>
        <td>MyPage2Controller</td>
      </tr>
      <tr>
        <td>도서신청 반려 사유 팝업</td>
        <td>노지혜</td>
        <td><a href="">popMyPageRejectionDetail</a></td>
        <td>MyPage3Controller</td>
      </tr>
      <tr>
        <td>회원 정보 수정</td>
        <td rowspan="2">채형찬</td>
        <td><a href="">myPageModify</a></td>
        <td>MyPage4Controller</td>
      </tr>
  
      <!-- 관리자 -->
      <!-- 메인 -->
      <tr>
        <td rowspan="25">admin</td>
        <td></td>
        <td></td>
        <td><a href="">main</a></td>
        <td>MainController</td>
      </tr>
  
      <!-- 도서관리 -->
      <tr>
        <td rowspan="5">book</td>
        <td>목록</td>
        <td rowspan="5">김시연</td>
        <td><a href="">bookList</a></td>
        <td rowspan="5">BookController</td>
      </tr>
      <tr>
        <td>상세</td>
        <td><a href="">bookDetail</a></td>
      </tr>
      <tr>
        <td>수정</td>
        <td><a href="">bookModify</a></td>
      </tr>
      <tr>
        <td>등록</td>
        <td><a href="">bookWrite</a></td>
      </tr>
      <tr>
        <td>도서선택 팝업</td>
        <td><a href="">popBookSelect</a></td>
      </tr>
  
      <!-- 도서대여 -->
      <tr>
        <td>bookLoan</td>
        <td>등록</td>
        <td rowspan="5">김이슬</td>
        <td><a href="">bookLoanWrite</a></td>
        <td>BookLoanController</td>
      </tr>

      <!-- 도서예약 -->
      <tr>
        <td rowspan="4">bookReservation</td>
        <td>목록</td>
        <td><a href="">bookReservationList</a></td>
        <td rowspan="4">BookReservationController</td>
      </tr>
      <tr>
        <td>등록</td>
        <td><a href="">bookReservationWrite</a></td>
      </tr>
      <tr>
        <td>등록 팝업</td>
        <td><a href="">popBookReservationWrite</a></td>
      </tr>
      <tr>
        <td>수정 팝업</td>
        <td><a href="">popBookReservationModify</a></td>
      </tr>
  
      <!-- 희망도서관리 -->
      <tr>
        <td rowspan="2">bookRequest</td>
        <td>목록</td>
        <td rowspan="2">김시연</td>
        <td><a href="">bookRequestList</a></td>
        <td rowspan="2">BookRequestController</td>
      </tr>
      <tr>
        <td>상세</td>
        <td><a href="">bookRequestDetail</a></td>
      </tr>
     
      <!-- 결재관리(사서) -->
      <tr>
        <td rowspan="6">librarianApproval</td>
        <td>기안 목록</td>
        <td rowspan="9">노지혜</td>
        <td><a href="">librarianApprovalList</a></td>
        <td rowspan="6">LibrarianApprovalController</td>
      </tr>
      <tr>
        <td>기안 등록</td>
        <td><a href="">librarianApprovalWrite</a></td>
      </tr>
      <tr>
        <td>희망도서선택 팝업</td>
        <td><a href="">popLibrarianApprovalBookRequestSelect</a></td>
      </tr>
      <tr>
        <td>도서선택 팝업</td>
        <td><a href="">popLibrarianApprovalBookSelect</a></td>
      </tr>
      <tr>
        <td>기안 상세</td>
        <td><a href="">librarianApprovalDetail</a></td>
      </tr>
      <tr>
        <td>기안 수정</td>
        <td><a href="">librarianApprovalModify</a></td>
      </tr>
     
      <!-- 결재관리(도서관장) -->
      <tr>
        <td rowspan="3">directorApproval</td>
        <td>기안 목록</td>
        <td><a href="">directorApprovalList</a></td>
        <td rowspan="3">DirectorApprovalController</td>
      </tr>
      <tr>
        <td>기안 상세</td>
        <td><a href="">directorApprovalDetail</a></td>
      </tr>
      <tr>
        <td>반려사유 팝업</td>
        <td><a href="">popDirectorApprovalRejectionWrite</a></td>
      </tr>
  
      <!-- 회원관리 -->
      <tr>
        <td rowspan="3">user</td>
        <td>목록</td>
        <td rowspan="3">채형찬</td>
        <td><a href="">userList</a></td>
        <td rowspan="3">UserController</td>
      </tr>
      <tr>
        <td>상세</td>
        <td><a href="">userDetail</a></td>
      </tr>
      <tr>
        <td>수정</td>
        <td><a href="">userModify</a></td>
      </tr>
    </tbody>    
  </table>
</body>
</html>

