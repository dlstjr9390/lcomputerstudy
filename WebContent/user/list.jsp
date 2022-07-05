<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록2</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	table{
		border-collapse:collapse;
		margin:40px auto;
	}
	
	table tr th{
		font-weight:700;
	}
	
	table tr td, table tr th{
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a{
		text-decoration:none;
		color:#000;
		font-weight:700;
	}
	
	h1{
		text-align:center;
	}
	
	ul{
		width:600px;
		height:50px;
		margin:10px auto;
	}
	
	li{
		list-style:none;
		width:50px;
		line-height:50px;
		border:1px solid #ededed;
		float:left;
		text-align:center;
		margin:0 5px;
		border-radius:5px;
	}
	
</style>
</head>
<body>
<c:if test="${sessionScope.user.u_name != null}">
	<h4 style="text-align:right; margin-right:100px; color:blue;">
		<c:if test="${sessionScope.user.u_auth == true}">
			<span style="color:red">관리자</span>
		</c:if>
		${sessionScope.user.u_name }&nbsp;&nbsp;<a href="logout.do">로그아웃</a>
	</h4>
</c:if>

<h1>회원 목록</h1>
	<table>
		<tr>
			<td colspan="3">전체 회원 수 : ${pagination.count }</td>
		<tr>
			<th>No</th>
			<th>ID</th>
			<th>이름</th>
		</tr>
		<c:forEach items="${list}" var="item" varStatus="status">
			<tr>
				<td><a href="user-detail.do?u_idx=${item.u_idx}">${item.rownum}</a></td>
				<td>${item.u_id}</td>
				<td>${item.u_name}</td>
				<c:if test="${sessionScope.user.u_idx == 1 && item.u_name != sessionScope.user.u_name }">
					<td style="border:none; text-align:left; ">				
						<button type="button" class="btnGiveAuth" u_idx="${item.u_idx }">권한주기</button>
						<c:if test="${item.u_auth == true && item.u_idx != 1 }">
							<button type="button" class="btnDelAuth" u_idx="${item.u_idx }">권한삭제</button>
						</c:if>
					</td>	
				</c:if>
			<tr>
		</c:forEach>
	</table>
<!-- 아래부터 pagination -->
	<div>
		<ul>
			<c:choose>
				<c:when test = "${pagination.prevPage lt pagination.nextPage and pagination.prevPage gt 0}">
					<li>
						<a href="user-list.do?page=${pagination.prevPage }">
							◀
						</a>
					</li>
				</c:when>
			</c:choose>
			
			<c:forEach var="i" begin="${pagination.startPage }"	end="${pagination.endPage }" step="1">
			
				<c:choose>
					<c:when test="${pagination.page eq i }">
					
						<li style="background-color:#ededed;">
							<span>${i}</span>
						</li>
					</c:when>
					<c:when test="${pagination.page ne i }">
						<li>
							<a href="user-list.do?page=${i}">${i }</a>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${pagination.nextPage le pagination.lastPage }">
					<li style ="">
						<a href="user-list.do?page=${pagination.nextPage }">▶</a>
					</li>
				</c:when>
			</c:choose>
		</ul>
	</div>
	
	<script>
	$(document).on('click', '.btnGiveAuth', function(){
		alert("권한이 변경되었습니다.");
		let u_idx = $(this).attr('u_idx');
		let boolean = true;
		location.href = "edit-auth.do?u_idx="+u_idx+"&boolean="+boolean;
	});
	
	$(document).on('click', '.btnDelAuth', function(){
		alert("권한이 변경되었습니다.");
		let u_idx = $(this).attr('u_idx');
		let boolean = false;
		location.href = "edit-auth.do?u_idx="+u_idx+"&boolean="+boolean;
	});
	</script>
</body>
</html>