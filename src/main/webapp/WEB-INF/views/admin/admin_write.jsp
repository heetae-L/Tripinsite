<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/assets/css/admincss.css">

	<!-- <script src="//cdn.ckeditor.com/4.11.4/standard/ckeditor.js"></script> -->
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<div class="container">
		<div class="header col-xs-12">
			<h1>공지사항 작성</h1>
		</div>
		<form class="form-horizontal" method="post"
			enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/admin/admin_write_ok.do">
			<!-- 게시판 카테고리에 대한 상태 유지 -->
			<input type="hidden" name="category" id="category" value="free" />
			<!-- 작성자, 비밀번호, 이메일은 로그인하지 않은 경우만 입력한다. -->
			
			<!-- 제목 -->
			<div class="form-group">
				<label for="subject" class="col-sm-2 control-label">제목</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="subject" name="subject" />
				</div>
			</div>
			<!-- 내용 -->
			<div class="form-group">
				<label for="content" class="col-sm-2 control-label">내용</label>
				<div class="col-sm-10">
					<textarea id="content" name="content"
							style="width: 100%;" rows="15"></textarea>
				</div>
			</div>
			<!-- 파일업로드 -->
			<div class="form-group">
				<label for="file" class="col-sm-2 control-label">파일첨부</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="file" name="file"
						multiple />
				</div>
			</div>
			<!-- 버튼들 -->
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">저장하기</button>
					<button type="button" class="btn btn-danger"
						onclick="history.back();">작성취소</button>
				</div>
			</div>
		</form>
		
	</div>
</body>
</html>

