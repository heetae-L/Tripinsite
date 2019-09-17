<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<form method="post" name="comdel_password" id="comdel_password"
	action="${pageContext.request.contextPath }/bbs/comment_delete_ok.do">
	<input type="hidden" name="idcomment" value="${comment.idcomment}" />
	<!-- 제목 -->
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">댓글 삭제</h4>
	</div>
	<!-- 내용 -->
	<div class="modal-body">
		<c:choose>
			<c:when test="${myComment == true}">
				<!-- 자신의 글에 대한 삭제 -->
				<p>정말 이 댓글을 삭제하시겠습니까?</p>
			</c:when>
			<c:otherwise>
				<!-- 자신의 글이 아닌 경우 -->
				<p>삭제하시려면 비밀번호를 입력하세요</p>
				<div class="form-group">
					<input type="password" name="writer_pw" id="writer_pw"
						class="form-control" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- 하단 -->
	<div class="modal-footer">
		<button type="submit" class="btn"
			style="background-color: #ff8000; color: white;">삭제하기</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	</div>
</form>