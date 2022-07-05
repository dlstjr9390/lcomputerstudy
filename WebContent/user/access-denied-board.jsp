<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>권한 필요</title>
</head>
<body>
<h1>권한이 필요합니다.</h1>
<script>
setTimeout(function (){
	window.location.href = "user-login.do";
}, 2000);
</script>
</body>
</html>