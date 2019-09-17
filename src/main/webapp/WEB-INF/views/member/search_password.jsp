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
	<!--  비밀번호 찾기 창 -->
	<div class="container">
		<div class="content">
			<h1 class="page-header">
				<i>비밀번호 재설정</i>
			</h1>
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<p>가입시 입력한 이메일 주소를 입력하세요. 임시 비밀번호를 이메일로 보내드립니다.</p>

					<!-- 이메일 주소 입력 폼 시작 -->
					<form name="myform" method="GET" id="search_form"
						action="${pageContext.request.contextPath}/member/search_password_ok.do">
						<div class="form-group">
							<input type="text" name="email" class="form-control" />
							<div class="form-group">
								<button type="submit" class="btn btn-block"
									style="background-color: #ff8000; color: #fff;"
									id="search_submit">비밀번호 재설정 하기</button>
							</div>
						</div>
					</form>
					<!-- // 이메일 주소 입력 폼 끝 -->
				</div>
			</div>
		</div>
	</div>
	<!-- 비밀번호 찾기 창 끝 -->
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>