<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<form method="post" name="comedit_password" id="comedit_password"
	action="${pageContext.request.contextPath }/bbs/comment_edit_ok.do">
	<input type="hidden" name="idcomment" value="${comment.idcomment}" />
	<!-- 제목 -->
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">댓글 수정</h4>
	</div>
	<!-- 내용 -->
	<div class="modal-body">
		<!-- 자신의 글이 아닌 경우 -->
		<c:if test="${comment.member_id != loginInfo.id}">
			<div class="form-group">
				<input type="text" name="writer_name" id="writer_name"
					class="form-control" placeholder="작성자"
					value="${comment.writer_nickname}" />
			</div>
			<div class="form-group">
				<input type="password" name="writer_pw" id="writer_pw"
					class="form-control" placeholder="작성시 설정한 비밀번호" />
			</div>
		</c:if>
		<div class="form-group">
			<textarea class="form-control" name="content" rows="5">${comment.content}</textarea>
		</div>
	</div>
	<!-- 하단 -->
	<div class="modal-footer">
		<button type="submit" class="btn"
			style="background-color: #ff8000; color: white;">수정</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	</div>
</form>