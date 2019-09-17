<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
<!--  탈퇴창-->
	<div class="container">
		<div class="content">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<h1 class="page-header">회원가입 탈퇴</h1>
					<p>탈퇴를 위해서는 가입 시 입력한 비밀번호를 입력해 주세요.</p>

					<!-- 비밀번호 입력 폼 시작 -->
					<form name="out_form" method="POST"
						id="out_form" action="${pageContext.request.contextPath}/member/out_ok.do">
						<div class="form-group">
							<input type="password" name="password" class="form-control" />
						<div class="form-group">
							<button type="submit" class="btn btn-block"
						style="background-color: #ff8000; color: #fff;" id="out_submit" >회원탈퇴</button>
							</div>	
						</div>
					</form>
					<!-- 비밀번호 입력 폼 끝 -->
				</div>
			</div>
		</div>
	</div>
<!--  탈퇴창 끝 -->
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>