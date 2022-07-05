<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록</title>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<style>
	#input_title{
		width:300px;	
	}
	#input_content{
		width:500px;
		height:500px;
	}
</style>
<body>	
	<form action="post-regist-process.do" name="Post" method="post" class="FormFile" enctype="multipart/form-data">
		<p>제목 : <input type="text" id=input_tile name="title"></p>
		<p><input type="hidden" name="writer" value="${sessionScope.user.u_name }"></p>
		<p>내용 : <input type="text" id=input_content name="content"></p>
		<p class="add">파일첨부: <input type="file" id="addfile" class= "files" name="file1"><button type="button" class="btnAdd">추가</button></p>		
		<p><input type="submit" value="등록"></p>
	</form>
	
	<div id="tmp">
	</div>
	
	<script>
	var maxAppend = 1; //첨부파일개수
	var option1 = 0; 

	$(document).on('click', '.btnAdd', function() {
		
		if(maxAppend >= 3){
			alert("파일 업로드 최대 개수는 3개입니다.");
			return;
		}else{
			$('.add').append('<p>'+
					'<span style="visibility:hidden;">파일첨부: </span>'+
					'<input type="file" id="addfile" class= "files" name="file1">'+
					'<button type="button" class="btnAdd">추가</button>'+
					'<button type="button" class="btnDel">삭제</button>'+'</p>');
			maxAppend ++;
			
			if(option1 < 1){
				option1++;
			}	 
		};
	});

	$(document).on('click', '.btnDel', function(){
		if(maxAppend > 1){
			$(this).closest('p').remove();
			maxAppend --;	
		} 

	});
	</script>
	
</body>
</html>
