<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	table{
		border-collapse:collapse;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th{
		border:1px solid #818181;
		width:200px;
		text-align:center;
		
	}
	table th{
		background-color:#F2F2F2;
	}
	a{
		text-decoration:none;
		color:#000;
		border:none;
		cursor:pointer;
		display:inline-block;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<th>제목</th>
			<td style="width:300px; text-align:left;">${detailboard.title}</td>	
			<th>조회수</th>
			<td>${detailboard.view}</td>
		</tr>
		<tr>	
			<th>작성자</th>
			<td style="width:300px; text-align:left;">${detailboard.writer}</td>
			<th>작성일자</th>
			<td>${detailboard.date}</td>			
		</tr>
		<tr> 
			<th>내용</th>
			<td colspan="3" style="text-align:left;">${detailboard.content }</td>
		</tr>
	</table>
	<table style="margin-top:10px; margin-bottom:30px">
			<tr>
				<th colspan="2" >첨부파일</th>
				<c:forEach items="${bf_list }" var="item" varStatus="status">				
					<td style="width:300px;"><a href="/lcomputerstudy/img/${item.f_Oname}">${item.f_Cname }</a></td>
				</c:forEach>	
			</tr>
	</table>
	<table style="margin-bottom:30px">
			<tr style="height:50px;">
				<c:if test="${sessionScope.user.u_name == detailboard.writer || sessionScope.user.u_auth == true }">
					<td style="border:none;">
					<a href="post-edit.do?b_idx=${detailboard.b_idx}" style="padding:10px; width:70px;font-weight:700;background-color:#818181;color:#fff;">수정</a>
					</td>
				</c:if>
				
				<c:if test="${sessionScope.user.u_name == detailboard.writer|| sessionScope.user.u_auth == true }">
					<td style="border:none;">
					<a href="post-delete.do?b_group=${detailboard.b_group }&b_order=${detailboard.b_order }&b_idx=${detailboard.b_idx}" style="padding:10px; width:70px;font-weight:700;background-color:red;color:#fff;">삭제</a>
				</c:if>
				
				<td style="border:none;">
				<a href="post-reply.do?b_idx=${detailboard.b_idx }"	style="padding:10px; width:70px;font-weight:700;background-color:#818181;color:#fff;">답글</a>
				
				<td style="border:none;">
				<a href="post-list.do"	style="padding:10px; width:70px;font-weight:700;background-color:#818181;color:#fff;">목록</a>
			</tr>			
	</table>
<div id="commentList">
	<table style="margin-bottom:10px">
		<c:forEach items="${c_list}" var="item" varStatus="status">
			<c:if test="${item.b_idx == detailboard.b_idx }">
			<tr>
				<td style="text-align:left"> 작성자: ${item.writer }</td>
				<c:if test="${item.c_depth > 0 }">
					<td style="text-align:left">
						<span style="color:red; font-style:italic; padding-left:${item.c_depth * 20}px;">re: </span>${item.content}
					</td>
				</c:if>
				<c:if test="${item.c_depth == 0 }">
					<td style="text-align:left">${item.content}</td>
				</c:if>	
				<td>${item.date}</td>
				<td style="text-align:left;">
					<button type="button" class="btnComment" style="margin-left:10px">댓글</button>
					
					<c:if test="${sessionScope.user.u_name == item.writer}">
						<button type="button" class="btnEdit">수정</button>
					</c:if>
					
					<c:if test="${sessionScope.user.u_name != item.writer }">
						<button type="button" style="visibility:hidden;">수정</button>
					</c:if>		
						
					<c:if test="${sessionScope.user.u_name == item.writer || sessionScope.user.u_auth == true}">
						<button type="button" class="btndel" b_idx="${item.b_idx}" c_idx="${item.c_idx }" >삭제</button>
					</c:if>	
			
				</td>
			</tr>
			</c:if>
			<tr style="display:none;">
				
				<td colspan="3">
					<textarea cols="80" rows="1"></textarea>
				</td>
				
				<td>
					<button type="button" class="btnSubmit" writer="${sessionScope.user.u_name }" group="${item.c_group}" order="${item.c_order}" depth="${item.c_depth}" b_idx="${item.b_idx }">등록</button>
				</td>
				
			</tr>
			
			<tr style="display:none;">
				
				<td colspan="3">
					<textarea cols="80" rows="1">${item.content }</textarea>
				</td>
				
				<td>
					<button type="button" class="btnSubmit2" writer="${sessionScope.user.u_name }" group="${item.c_group}" order="${item.c_order}" depth="${item.c_depth}" b_idx="${item.b_idx}" c_idx="${item.c_idx }">등록</button>
				</td>
				
			</tr>
		</c:forEach>
	</table>
</div>		
	<table style="border:hidden;" >
			<tr>
				<td style= "text-align:left; border:hidden; width:10%;">댓글:</td>
				<td colspan="3" style="text-align:center; border:hidden;">
					<textarea id="Txtarea" cols="60" rows="1"></textarea>
				</td>
					<td style="width:10%;">
					<button type="button" class="btnSubmit3" writer="${sessionScope.user.u_name }" group="${board.c_group}" order="${board.c_order}" depth="${board.c_depth}" b_idx="${detailboard.b_idx}" c_idx="${board.c_idx }">등록</button>
					</td>
			</tr>
	</table>

	<script>
	$(document).on('click', '.btnComment', function () {
		$(this).parent().parent().next().show();
	});

	$(document).on('click', '.btnEdit', function () {
		$(this).parent().parent().next().next().show();
	});

	$(document).on('click', '.btndel', function () {
		let b_idx = $(this).attr('b_idx');
		let c_idx = $(this).attr('c_idx');
		
		$.ajax({
			method:"POST",
			url:"post-delete-comment.do",
			data:{c_idx: c_idx, b_idx: b_idx}
		})	
		.done(function(msg){
			$('#commentList').html(msg);
		});	
	});
	
	$(document).on('click', '.btnSubmit', function() {
		let group = $(this).attr('group');
		let order = $(this).attr('order');
		let depth = $(this).attr('depth');
		let b_idx = $(this).attr('b_idx');
		let writer = $(this).attr('writer');
		let content = $(this).parent().prev().find('textarea').val();	

		$.ajax({
			method:"POST",
			url:"post-comment.do",
			data:{c_group: group, c_order: order, c_depth: depth, b_idx: b_idx, writer: writer, content: content}
		})	
		.done(function(msg){
			$('#commentList').html(msg);
		});	

	});

	$(document).on('click', '.btnSubmit2', function() {
		let c_idx = $(this).attr('c_idx');
		let group = $(this).attr('group');
		let order = $(this).attr('order');
		let depth = $(this).attr('depth');
		let b_idx = $(this).attr('b_idx');
		let writer = $(this).attr('writer');
		let content = $(this).parent().prev().find('textarea').val();	

		$.ajax({
			method:"POST",
			url:"post-comment.do",
			data:{c_idx: c_idx, c_group: group, c_order: order, c_depth: depth, b_idx: b_idx, writer: writer, content: content}
		})	
		.done(function(msg){
			$('#commentList').html(msg);
		});	
	
	});

	$(document).on('click', '.btnSubmit3', function() {
		
		let c_idx = $(this).attr('c_idx');
		let group = $(this).attr('group');
		let order = $(this).attr('order');
		let depth = $(this).attr('depth');
		let b_idx = $(this).attr('b_idx');
		let writer = $(this).attr('writer');
		let content = $(this).parent().prev().find('textarea').val();	

		$.ajax({
			method:"POST",
			url:"post-comment.do",
			data:{c_idx: c_idx, c_group: group, c_order: order, c_depth: depth, b_idx: b_idx, writer: writer, content: content}
		})	
		.done(function(msg){
			$('#commentList').html(msg);
			$('#Txtarea').val('');
		});	
	
	});

	
	</script>	
</body>
</html>