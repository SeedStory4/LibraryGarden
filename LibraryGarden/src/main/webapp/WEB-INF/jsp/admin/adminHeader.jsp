<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/header-footer.css">

<header>
	<div class="header-container">
		<a href="<%= request.getContextPath() %>/admin/main.do">
			<img src="<%= request.getContextPath() %>/images/로고.png" alt="로고" class="logo">
		</a>
		<nav class="admin-nav">
			<ul class="nav-menu">
				<li><a href="#">도서관리</a></li>
				<li><a href="#">도서대여</a></li>
				<li><a href="#">도서예약</a></li>
				<li><a href="#">희망도서</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/approval/approvalList.do">결재관리</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/user/userList.do">회원관리</a></li>
			</ul>
			<ul class="admin-nav-auth">
	            <li><a href="<%= request.getContextPath() %>/user/myPage/myPageModify.do">마이페이지</a></li>
	            <li>·</li>
	            <li>
				  <form action="<%= request.getContextPath() %>/user/user/logout.do" method="post" style="display:inline;">
				    <button type="submit" class="logout-btn">로그아웃</button>
				  </form>
			    </li>
			</ul>
		</nav>
	</div>
</header>