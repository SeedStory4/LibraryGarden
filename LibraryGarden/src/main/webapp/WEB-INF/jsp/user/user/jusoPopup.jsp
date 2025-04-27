<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String inputYn = request.getParameter("inputYn"); 
    String roadFullAddr = request.getParameter("roadFullAddr");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소 검색</title>
</head>
<body onload="init();">
<form id="form" name="form" method="post">
  <input type="hidden" id="confmKey" name="confmKey" value="devU01TX0FVVEgyMDI1MDQyNjIxNDQxNzExNTY5OTg=">
  <!-- <input type="hidden" id="returnUrl" name="returnUrl" value="http://localhost:8080/sht_webapp/user/user/jusoPopup.do?inputYn=Y"> -->
  <input type="hidden" id="returnUrl" name="returnUrl" 
  value="<%=request.getScheme() %>://<%=request.getServerName() %>:<%=request.getServerPort() %><%=request.getContextPath()%>/user/user/jusoPopup.do?inputYn=Y">  
  <input type="hidden" id="resultType" name="resultType" value="4">
</form>
<!-- 배포용 코드 -->
  
<script>
function init() {
  var inputYn = "<%=inputYn%>";

  if (inputYn !== "Y") {
    document.form.action = "https://business.juso.go.kr/addrlink/addrLinkUrl.do";
    document.form.submit();
  } else {
    // 검색 결과가 돌아온 경우
    opener.jusoCallBack("<%=roadFullAddr%>");
    window.close();
  }
}
</script>
</body>
</html>
