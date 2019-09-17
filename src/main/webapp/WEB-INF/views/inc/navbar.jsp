<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="k" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!-- 메뉴바 -->
<nav class="navbar navbar-default navbar-fixed-top navbar-inverse"
	role="navigation" id="navbar">
	<div class="container">
		<!-- 로고 -->
		<div class="navbar-header">
			<!-- 반응형 메뉴 구현 기능 추가 -->
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<!--// 반응형 메뉴 구현 기능 추가 끝 -->
			<a class="navbar-brand"
				href="${pageContext.request.contextPath }/home" id="logo-src"
				style="padding: 0;"> <img
				src="${pageContext.request.contextPath}/assets/img/logo.png"
				style="width: 165px; height: 50px;" />
			</a>
		</div>
		<!-- //로고 -->
		<!-- 메뉴 영역 -->
		<div class="collapse navbar-collapse" id="gnb">

			<!-- 사이트 메뉴 -->
			<ul class="nav navbar-nav">
				<li class="menu-item pull-left"><a
					href="${pageContext.request.contextPath }/introduction/introduction.do">소
						개</a>
					<ul class="sub">
						<li><a
							href="${pageContext.request.contextPath }/introduction/introduction.do">사이트소개</a></li>
					</ul></li>
				<li class="menu-item pull-left"><a href="#">게 시 판</a>
					<ul class="sub">
						<li><a
							href="${pageContext.request.contextPath }/bbs/bbs_area_list.do">관광지정보</a></li>
						<li><a
							href="${pageContext.request.contextPath }/bbs/bbs_free_list.do?category=free">자유게시판</a></li>
						<li><a
							href="${pageContext.request.contextPath }/schedule/schedule_list.do?category=story">스토리게시판</a></li>
					</ul></li>
				<li class="menu-item pull-left"><a href="#">일 정</a>
					<ul class="sub">
						<li><a
							href="${pageContext.request.contextPath }/schedule/mySchedule_edit.do">일정작성</a></li>
						<li><a
							href="${pageContext.request.contextPath }/schedule/mySchedule_list.do">일정보기</a></li>
					</ul></li>
			</ul>
			<!-- //사이트 메뉴 -->
			<!-- 로그인 메뉴 -->
			<c:choose>
				<c:when test="${loginInfo == null }">
					<!-- 로그인 메뉴 -->
					<form class="navbar-form navbar-right" id="login" method="post"
						action="${pageContext.request.contextPath }/member/login_ok.do">
						<div class="form-group" style="padding: 0;">
							<input type="text" name="user_id" class="form-control"
								placeholder="E-mail">
						</div>
						<div class="form-group" style="padding: 0;">
							<input type="password" name="user_pw" class="form-control"
								placeholder="Password">
						</div>
						<button type="submit" class="btn" id="login_ok"
							style="background-color: #ff8000; color: white;">
							<i class="glyphicon glyphicon-log-in"></i>
						</button>
						<!-- 회원 가입 -->
						<a href="${pageContext.request.contextPath }/member/join.do"
							class="btn" style="background-color: #ff8000; color: white;">
							<i class="glyphicon glyphicon-plus"></i>
						</a>
						<!-- 비밀번호 찾기 -->
						<a
							href="${pageContext.request.contextPath }/member/search_password.do"
							class="btn" style="background-color: #ff8000; color: white;">
							<i class="glyphicon glyphicon-wrench"></i>
						</a>
					</form>
					<!-- //로그인 메뉴 -->
				</c:when>
				<c:otherwise>
					<!-- 로그인 된 경우 -->
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" style="padding: 5px">
								${loginInfo.nickname}님 <span class="caret"></span>
						</a> <!-- 로그인한 경우 표시될 메뉴 -->
							<ul class="dropdown-menu">
								<li><a
									href="${pageContext.request.contextPath }/member/logout.do">로그아웃</a></li>
								<c:choose>
									<c:when test="${loginInfo.email == 'admin' }">
										<!-- 관리자로 로그인 된 경우 -->
										<li><a
											href="${pageContext.request.contextPath }/admin/admin_member.do">회원관리
										</a></li>
										<li><a
											href="${pageContext.request.contextPath }/admin/admin_board.do">관리자
												게시판</a></li>
										<!-- //관리자로 로그인 된 경우 -->
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.request.contextPath }/member/edit.do">회원정보
												수정</a></li>
										<li><a
											href="${pageContext.request.contextPath}/member/mypage.do">마이페이지</a>
										<li><a
											href="${pageContext.request.contextPath }/member/out.do">회원
												탈퇴</a></li>
									</c:otherwise>
								</c:choose>
							</ul></li>
						<!-- //로그인한 경우 표시될 메뉴 -->
					</ul>
				</c:otherwise>
			</c:choose>
			<!-- //로그인 메뉴 -->
		</div>
		<!-- //메뉴 영역 -->
	</div>
</nav>
<!-- //메뉴바 -->

<script type="text/javascript">
	$(function() {
		/** 메뉴 바 */
		$(".menu-item").hover(function() {
			$(this).find(".sub").slideToggle(100);
		});

		/** 로그인 실패 구현 */
		$("#login").ajaxForm(function(json) {
			//json은 API에서 표시하는 전체 데이터
			if (json.login == "false") {
				swal({
					title : '<font color="red">에러</font>', // 제목
					html : '로그인에 실패했습니다.', // 내용
					type : 'error', // 종류
					showCloseButton : false, // 닫기 버튼 표시 여부
					confirmButtonText : '확인', // 확인버튼 표시 문구
					confirmButtonColor : '#a00', // 확인버튼 색상
					showCancelButton : false
				});
				return;
			} else if (json.login == "ok") {
				swal({
					title : '<font color="green">성공</font>', // 제목
					html : '로그인에 성공했습니다.', // 내용
					type : 'success', // 종류
					showCloseButton : false, // 닫기 버튼 표시 여부
					confirmButtonText : '확인', // 확인버튼 표시 문구
					confirmButtonColor : '#a00', // 확인버튼 색상
					showCancelButton : false
				}).then(function(result) {
					location.replace(json.url);
				});
				return;
			} else {
				swal({
					title : '<font color="green">에러</font>', // 제목
					html : '이미 로그인중입니다.', // 내용
					type : 'error', // 종류
					showCloseButton : false, // 닫기 버튼 표시 여부
					confirmButtonText : '확인', // 확인버튼 표시 문구
					confirmButtonColor : '#a00', // 확인버튼 색상
					showCancelButton : false
				}).then(function(result) {
					location.replace(json.url);
				});
				return;
			}
		});
	});
</script>