<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/free.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left">자유 게시판 작성</h1>
			</div>
			<form class="form-horizontal" method="post"
				action="${pageContext.request.contextPath }/bbs/bbs_free_write_ok.do">
				<!-- 게시판 카테고리에 대한 상태유지 -->
				<input type="hidden" name="category" value="free" />

				<c:choose>
					<c:when test="${loginInfo == null}">
						<!-- 닉네임 + 비밀번호 -->
						<div class="form-group">
							<label for="writer_name" class="col-sm-2 control-label"
								style="text-align: center;">닉네임</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="writer_name"
									name="writer_name" placeholder="닉네임을 입력하세요." />
							</div>
							<label for="writer_pw" class="col-sm-2 control-label"
								style="text-align: center;">비밀번호</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" id="writer_pw"
									name="writer_pw" placeholder="비밀번호를 입력하세요." />
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="writer_name" value="${loginInfo.nickname}" />
						<input type="hidden" name="writer_pw" value="${loginInfo.password}" />
					</c:otherwise>
				</c:choose>
				<!-- 제목 -->
				<div class="form-group">
					<div class="col-sm-12">
						<input type="text" class="form-control" id="subject"
							name="subject" placeholder="제목" />
					</div>
				</div>
				<!-- 내용 -->
				<div class="form-group">
					<div class="col-sm-12">
						<textarea class="ckeditor" id="content" name="content"
							style="width: 100%;" rows="15" placeholder="내용을 입력하세요."></textarea>
					</div>
				</div>
				<!-- 버튼들 -->
				<div class="form-group">
					<div class="pull-right">
						<button type="submit" class="btn"
							style="background-color: #ff8000; color: white;">작성하기</button>
						<a onclick="history.back();" class="btn btn-default">작성취소 </a>
					</div>
				</div>
			</form>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>