<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/free.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left">자유 게시판 수정</h1>
			</div>

			<form class="form-horizontal" method="post"
				action="${pageContext.request.contextPath }/bbs/bbs_free_edit_ok.do">

				<!-- 게시판 카테고리에 대한 상태유지 -->
				<input type="hidden" name="category" value="${board.category}" />
				<!-- 수정 대상에 대한 상태유지 -->
				<input type="hidden" name="idboard" value="${board.idboard}" /> <input
					type="hidden" name="writer_nickname"
					value="${board.writer_nickname}" />

				<!-- 제목 -->
				<div class="form-group">
					<div class="col-sm-12">
						<input type="text" class="form-control" name="subject"
							id="subject" value="${board.subject}" />
					</div>
				</div>
				<!-- 내용 -->
				<div class="form-group">
					<div class="col-sm-12">
						<textarea class="ckeditor" name="content" id="content"
							style="width: 100%;" rows="15">${board.content}</textarea>
					</div>
				</div>
				<!-- 버튼들 -->
				<div class="form-group">
					<div class="pull-right">
						<button type="submit" class="btn"
							style="background-color: #ff8000; color: white;">수정완료</button>
						<button type="button" class="btn btn-default"
							onclick="history.back();">수정취소</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>