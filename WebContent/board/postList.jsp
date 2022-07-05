<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
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
	}
	
	h1{
		text-align:center;
	}
	
	ul{
		width:100%;
		list-style:none;
		padding:0;
	}
	
	li{
		width:100%;
		list-style:none;
		padding:0;
	}
</style>
<body>
	<c:if test="${sessionScope.user.u_name != null}">
		<h4 style="text-align:right; margin-right:100px; color:blue;">
		<c:if test="${sessionScope.user.u_auth == true}">
			<span style="color:red">관리자</span>
		</c:if>
		${sessionScope.user.u_name }&nbsp;&nbsp;<a href="logout.do">로그아웃</a></h4>
	</c:if>
	<c:if test="${sessionScope.user.u_name == null}">
		<h4 style="text-align:right; margin-right:100px; color:red;">로그인이 필요합니다.&nbsp;&nbsp;<a href="user-login.do">로그인</a></h4>
	</c:if>
	
<h1>글 목록</h1>
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>내용</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>작성일시</th>
		<tr>
		<c:forEach items="${b_list}" var="item" varStatus="status">
				<tr>
					<td><a href="post-detail.do?b_idx=${item.b_idx}">${item.b_rownum}</a></td>
					<c:if test="${item.b_depth > 0 }">
						<td style="text-align:left">
							<span style="color:red; font-style:italic; padding-left:${item.b_depth * 20}px;">re:</span>
							<a href="post-detail.do?b_idx=${item.b_idx}">${item.title}</a>
						</td>
					</c:if>
					<c:if test="${item.b_depth == 0 }">
						<td style="text-align:left">
							<a href="post-detail.do?b_idx=${item.b_idx}">${item.title }</a>
						</td>
					</c:if>	
					<td>${item.content}</td>
					<td>${item.writer }</td>
					<td>${item.view}</td>
					<td>${item.date}</td>
				</tr>
		</c:forEach>
	</table>
	<div>
		<ul>
			<li style="text-align:center; margin-left:500px; margin-bottom:30px; border:none; ">
				<a href="post-regist.do"style="margin-right:20px; padding:10px; width:70px;font-weight:700;background-color:#818181;color:#fff;">작성</a>
				<a href="post-list.do"style="padding:10px; width:70px;font-weight:700;background-color:#818181;color:#fff;">목록</a>
			</li>
						
		</ul>
	</div>
<!--pagination -->
	<div>
		<ul class="ulpagination">
			<c:choose>
				<c:when test = "${pagination.prevPage lt pagination.nextPage and pagination.prevPage gt 0}">
					<li class="lipagination">
						<a href="post-list.do?page=${pagination.prevPage }&OptionSelect=${search.option}&SearchText=${search.text}">
							◀
						</a>
					</li>
				</c:when>
			</c:choose>
			
			<c:forEach var="i" begin="${pagination.startPage }"	end="${pagination.endPage }" step="1">
			
				<c:choose>
					<c:when test="${pagination.page eq i }">
					
						<li class="lipagination" style="background-color:#ededed;">
							<span>${i}</span>
						</li>
					</c:when>
					<c:when test="${pagination.page ne i }">
						<li class="lipagination">
							<a href="post-list.do?page=${i}&OptionSelect=${search.option}&SearchText=${search.text}">${i }</a>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${pagination.nextPage le pagination.lastPage }">
					<li class="lipagination">
						<a href="post-list.do?page=${pagination.nextPage }&OptionSelect=${search.option}&SearchText=${search.text}">▶</a>
					</li>
				</c:when>
			</c:choose>
		</ul>
		<script>
			$(function(){
				$('.ulpagination').css({'width':'600px', 'height':'50px', 'margin':'10px auto'});
				$('.lipagination').css({'list-style':'none', 'width':'50px', 'line-height':'50px', 'border':'1px solid #ededed', 'float':'left', 'text-align':'center',
				 'margin':'0 5px','border-radius':'5px'})	 
			});	
		</script>
	</div>	

	<form action="post-list.do" name="search" method="post">
		<table>
			<tr>
				<td style="border:hidden; text-align:right;"><select name="OptionSelect">
					<option value="title">제목</option>
					<option value="content">제목+내용</option>
					<option value="writer">작성자</option>		
				</select></td>
				<td style="border:hidden;">
					<input type="text" name="SearchText">
				</td>
				<td style="border:hidden; text-align:left;">
					<button type="submit" class="btnSearch">검색</button>
				</td>
			</tr>
		</table>
	</form>
</html>