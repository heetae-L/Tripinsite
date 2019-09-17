<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<!-- js -->
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/daumPostCode.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
	<!-- 회원수정 창 -->
	<div class="container">
		<div class='container'>
			<div class="content">
				<div class='page-header'>
					<h1><i>회원수정</i></h1>
				</div>
				<!-- 회원 수정 시작 -->
				<form class="form-horizontal" name="edit_form" method="POST"
					id="edit_form"
					action="${pageContext.request.contextPath}/member/edit_ok.do">
					<div class="form-group">
						<label for='"password"' class="col-md-2">새 비밀번호<span
						class='identify'>★</span></label>
						<div class="col-md-4">
						<div class="input-group">
							<input type="password" name="password" id="password" class="form-control" placeholder="숫자,한글,영어+숫자로 입력가능" />
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for='password_re' class="col-md-2">새 비밀번호확인<span
						class='identify'>★</span></label>
						<div class="col-md-4">
						<div class="input-group">
							<input type="password" name="password_re" id="password_re"
								class="form-control" placeholder="다시 한번 비밀번호를 입력하세요."/>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for='nickname' class="col-md-2">닉네임<span
						class='identify'>★</span></label>
						<div class="col-md-4">
						<div class="input-group">
							<input type="text" name="nickname" id="nickname" class="form-control" placeholder="한글과 영어로 입력가능" value="${loginInfo.nickname}"/>
							<span class="input-group-btn">
							<button type="button" class="btn" 
								style="background-color: #ff8000; color: #fff;"
								id="nickname_check" >닉네임 중복확인</button></span>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for="age" class="col-md-2">연령대<span
						class='identify'>★</span></label>
						<div class="col-sm-10">
						<div class="input-group">
							<select name="age" class="form-control">
								<option value="">---- 연령대를 선택하세요----</option>
								<option value="1"<c:if test="${loginInfo.age == '1'}">selected</c:if>>10대</option>
								<option value="2"<c:if test="${loginInfo.age == '2'}">selected</c:if>>20대</option>
								<option value="3"<c:if test="${loginInfo.age == '3'}">selected</c:if>>30대</option>
								<option value="4"<c:if test="${loginInfo.age == '4'}">selected</c:if>>40대</option>
								<option value="5"<c:if test="${loginInfo.age == '5'}">selected</c:if>>50대</option>
								<option value="6"<c:if test="${loginInfo.age == '6'}">selected</c:if>>60대</option>
								<option value="7"<c:if test="${loginInfo.age == '7'}">selected</c:if>>70대</option>
							</select>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for='postcode' class="col-md-2">우편번호</label>
						<div class="col-md-4">
						<div class="input-group">
							<input type="text" name="postcode" id="postcode"
								class="form-control" value="${loginInfo.postcode}"/>
							<!-- 클릭 시, Javascript 함수 호출 : openDaumPostcode() -->
							<span class="input-group-btn">
							<input type='button' value='우편번호 찾기' class="btn"
							style="background-color: #303a50; color: #fff;"
							onclick='execDaumPostcode("postcode","addr1","addr2")' /></span>
						</div>
						</div>
					</div>

					<div class="form-group">
						<label for='addr1' class="col-md-2">주소</label>
						<div class="col-md-10">
							<input type="text" name="addr1" id="addr1" class="form-control" 
							value="${loginInfo.addr1}" />
						</div>
					</div>

					<div class="form-group">
						<label for='addr2' class="col-md-2">상세주소</label>
						<div class="col-md-10">
							<input type="text" name="addr2" id="addr2" class="form-control"
							value="${loginInfo.addr2}" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-offset-2 col-md-10">
							<button type="submit" class="btn"
							style="background-color: #ff8000; color: #fff;" id="edit_submit">수정</button>
							<button type="button" class="btn btn-danger" id="join-cancel">취소</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--  회원 수정  끝 -->

	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
		
	<!--  취소 -->
	<script type="text/javascript">
		$(function() {
			$("#join-cancel").click(function() {
				history.back();
			});
		});
	</script>
	
	<!-- 닉네임 중복 체크 -->
	<script type="text/javascript">
	$(function() {
		/** 버튼 클릭시 이벤트 */
		$("#nickname_check").click(function() {
			// 입력값을 취득하고, 내용의 존재여부를 검사한다.
			var nickname_val = $("#nickname").val();

			if (!nickname_val) { // 입력되지 않았다면?
				swal('닉네임이 입력되지 않았습니다.', '닉네임을 입력하세요.', 'question');// <-- 메시지 표시
				$("#nickname").focus(); // <-- 커서를 강제로 넣기
				return false; // <-- 실행 중단
			}

			// 위의 if문을 무사히 통과했다면 내용이 존재한다는 의미이므로,
			// 입력된 내용을 Ajax를 사용해서 웹 프로그램에게 전달한다.
			$.get("../member/nickname_check.do", {
				nickname : nickname_val
			}, function(req) {
				// 사용 가능한 닉네임인 경우 --> req = { result: "OK" }
				// 사용 불가능한 닉네임인 경우 --> req = { result: "FAIL" }
				if (req.result == 'OK') {
					swal("성공",'사용할 수 있는 닉네임입니다.', 'success');
				} else if(req.result == 'overlap'){
					swal("실패", '이미 사용중인 닉네임입니다.', 'error');
				} else if(req.result == 'nicknamelength') {
					swal("실패", '닉네임은 2글자 이상 6글자 이하로 사용가능합니다.', 'error');		
				} else {
				swal("실패", '이미 사용중인 닉네임입니다.', 'error');
				$("#nickname").val("");
				$("#nickname").focus();
				}				}); // end $.get
			}); // end click
		});	// end function
		/** 닉네임 중복체크 끝 */
	</script>
	<!-- 수정확인 끝 -->
</body>
</html>