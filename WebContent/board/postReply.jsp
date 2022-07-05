<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 등록</title>
</head>
<body>
	<form action="post-regist-process.do" name="Post" method="post" enctype="multipart/form-data">
		<p><input type="hidden" name="b_idx" value="${board.b_idx}"></p>
		<p><input type="hidden" name="b_group" value="${board.b_group}"></p>
		<p><input type="hidden" name="b_order" value="${board.b_order}"></p>
		<p><input type="hidden" name="b_depth" value="${board.b_depth}"></p>
		<p>제목 : <input type="text"  name="title"></p>
		<p><input type="hidden"  name="writer" value="${sessionScope.user.u_name }"></p>
		<p>내용 : <input type="text"  name="content"></p>
		<p>파일설명: <input type="text" name="description"></p>
		<p>파일첨부: <input type="file" name="file1"></p>		
		<p><input type="submit" value="등록"></p>
	</form>

</body>
</html>