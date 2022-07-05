<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<table style="margin-bottom:10px">
		<c:forEach items="${c_list}" var="item" varStatus="status">
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
							<button type="button" class="btndel" b_idx="${item.b_idx }" c_idx="${item.c_idx }">삭제</button>
						</c:if>  	
				
					</td>
				</tr>
			<tr style="display:none;">
				
				<td colspan="3">
					<textarea cols="80" rows="1"></textarea>
				</td>
				
				<td>
					<button type="button" class="btnSubmit" writer="${sessionScope.user.u_name }" group="${item.c_group}" order="${item.c_order}" depth="${item.c_depth}" b_idx="${item.b_idx}"  c_idx="${item.c_idx }">등록</button>
				</td>
				
			</tr>
			
			<tr style="display:none;">
				
				<td colspan="3">
					<textarea cols="80" rows="1">${item.content }</textarea>
				</td>
				
				<td>
					<button type="button" class="btnSubmit2" writer="${sessionScope.user.u_name }" group="${item.c_group}" order="${item.c_order}" depth="${item.c_depth}" b_idx="${item.b_idx }" c_idx="${item.c_idx }">등록</button>
				</td>
				
			</tr>
			
		</c:forEach>
	</table>
