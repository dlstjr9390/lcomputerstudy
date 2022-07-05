<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
</head>
<body>
<h1>수정</h1>
		<form action="post-edit-process.do" name="Post" method="post">
			<input type="hidden" name="b_idx" value="${board.b_idx }">
			<input type="hidden" name="writer" value="${sessionScope.user.u_name }">
			<p>제목 : <input type="text" name="title"></p>
			<p>내용 : <input type="text" name="content"></p>
			<p><input type="submit" value="수정"></p>
		</form>
</body>
</html>