<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/header-footer.css">

<header>
	<div class="header-container">
		<a href="<%= request.getContextPath() %>/user/main.do"> 
		<img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
		</a>
		<nav class="nav">
		<ul class="nav-menu">
				<li><a href="#">도서조회</a></li>
				<li><a href="#">희망도서신청</a></li>
				<li><a href="<%= request.getContextPath() %>/user/myPage/myPageLoanList.do">내 도서</a></li>
		</ul>		
			<ul class="nav-auth">	
		    <c:choose>
		        <%--  로그인 안 된 상태 --%>
		        <c:when test="${empty sessionScope.loginUser}">
		            <li><a href="<%= request.getContextPath() %>/user/user/userJoin.do">회원가입</a></li>
		            <li>·</li>
		            <li><a href="<%= request.getContextPath() %>/user/user/userLogin.do">로그인</a></li>
		        </c:when>
		
		        <%-- 로그인 된 상태 --%>
		        <c:otherwise>
		            <li><a href="<%= request.getContextPath() %>/user/user/userMypage.do">마이페이지</a></li>
		            <li>·</li>
		           <li>
					  <form action="<%= request.getContextPath() %>/user/user/logout.do" method="post" style="display:inline;">
					    <button type="submit" class="logout-btn">로그아웃</button>
					  </form>
				   </li>
		        </c:otherwise>
		    </c:choose>
		</ul>
	  </nav>
	</div>
</header>