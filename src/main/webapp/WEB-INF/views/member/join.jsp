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
	<!-- 회원가입 창 -->
	<div class='container'>
		<div class="content">
			<div class='page-header'>
				<h1><i>회원가입</i></h1>
			</div>
			<!-- 가입폼 시작 -->
			<form class="form-horizontal" name="join_form" method="post"
				id="join_form"
				action="${pageContext.request.contextPath }/member/join_ok.do">
				<div class="form-group">
					<label for="email" class="col-md-2">이메일<span
						class='identify'>★</span></label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="text" name="email" id="email" class="form-control" placeholder="사용가능한 메일주소를 입력해주세요 .">
							<span class="input-group-btn">
								<button type="button" class="btn"
									style="background-color: #ff8000; color: #fff;"
									id="email_check">중복확인</button>
							</span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for='"password"' class="col-md-2">비밀번호<span
						class='identify'>★</span></label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="password" name="password" id="password"
								class="form-control"  placeholder="숫자,한글,영어+숫자로 입력가능"/>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for='password_re' class="col-md-2">비밀번호확인<span
						class='identify'>★</span></label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="password" name="password_re" id="password_re"
								class="form-control" placeholder="다시 한번 비밀번호를 입력하세요." />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="nickname" class="col-md-2">닉네임<span
                        class='identify'>★</span></label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="text" name="nickname" id="nickname"
								class="form-control"  placeholder="한글과 영어로 입력가능"> 
								 <span class="input-group-btn">
								<button type="button" class="btn"
									style="background-color: #ff8000; color: #fff;"
									id="nickname_check">닉네임 중복확인</button>
							</span>
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
								<option value="1">10대</option>
								<option value="2">20대</option>
								<option value="3">30대</option>
								<option value="4">40대</option>
								<option value="5">50대</option>
								<option value="6">60대</option>
							</select>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="terms" class="col-md-2">약관<span
						class='identify'>★</span></label>
					<div class="col-md-10">
						<input type="checkbox" name="agree" id="agree" value="동의" /> <label
							for="agree"> 약관에 대한 동의</label><a data-toggle="modal"
							href="#myModal" class="btn btn-xs"
							style="background-color: #ff8000; color: #fff;">전문보기</a>
						<!-- .modal -->
						<div id="myModal" class="modal fade" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<!-- .modal-dialog -->
							<div class="modal-dialog">
								<!-- .modal-content -->
								<div class="modal-content">
									<!-- 제목 -->
									<div class="modal-header">
										<!-- 닫기버튼 -->
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">가입을 위한 약관동의서</h4>
									</div>
									<!-- 내용 -->
									<div class="modal-body">
										<h6 class="font-bold">이용약관 동의</h6>
										<p>(목적)</p>
						<p>(1)이 약관은 tripinsite(이하 "tripinsite 사이트"라 합니다)에서 제공하는 인터넷서비스의 이용 조건 및 절차에 관한 기본적인 사항을 규정함을 목적으로 합니다.</p>
										<hr>
										<h6 class="font-bold">약관의 효력 및 변경</h6>
										<p>(1) 이 약관은 서비스 화면이나 기타의 방법으로 이용고객에게 공지함으로써 효력을 발생합니다.</p>
										<p>(2) 사이트는 이 약관의 내용을 변경할 수 있으며, 변경된 약관은 제1항과 같은 방법으로 공지 또는 통지함으로써 효력을 발생합니다.</p>
										<hr>
										<h6 class="font-bold">이용계약의 성립</h6>
										<p>(1) 이용약관 하단의 동의 버튼을 누르면 이 약관에 동의하는 것으로 간주됩니다.	</p>
										<p>(2) 이용계약은 서비스 이용희망자의 이용약관 동의 후 이용 신청에 대하여 사이트가 승낙함으로써 성립합니다. </p>
										<hr>
										<h6 class="font-bold">이용신청</h6>
										<p>(1) 신청자가 본 서비스를 이용하기 위해서는 사이트 소정의 가입신청 양식에서 요구하는 이용자 정보를 기록하여 제출해야 합니다.</p>
										<p>(2) 가입신청 양식에 기재하는 모든 이용자 정보는 모두 실제 데이터인 것으로 간주됩니다. 실제 정보를 입력하지 않은 사용자는 법적인 보호를 받을 수 없으며, 서비스의 제한을 받을 수 있습니다.</p>
										<hr>
										<h6 class="font-bold">이용자정보의 변경</h6>
							<p>(1)회원은 이용 신청시에 기재했던 회원정보가 변경되었을 경우에는, 온라인으로 수정하여야 하며 변경하지 않음으로 인하여 발생되는 모든 문제의 책임은 회원에게 있습니다.</p>
							<hr>
										<h6 class="font-bold">회원의 의무</h6>
										<p>(회원은 서비스 이용 시 다음의 행위를 하지 않아야 합니다.)</p>
										<p>(1)다른 회원의 이메일을 부정하게 사용하는 행위</p>
										<p>(2)서비스에서 얻은 정보를 사이트의 사전승낙 없이 회원의 이용 이외의 목적으로 복제하거나 이를 변경, 출판 및 방송 등에 사용하거나 타인에게 제공하는 행위</p>
										<p>(3)사이트의 저작권, 타인의 저작권 등 기타 권리를 침해하는 행위</p>
										<p>(4)공공질서 및 미풍양속에 위반되는 내용의 정보, 문장, 도형 등을 타인에게 유포하는 행위</p>
										<p>(5)범죄와 결부된다고 객관적으로 판단되는 행위</p>
										<p>(6)기타 관계법령에 위배되는 행위</p>
										<p>(7)회원은 관계법령, 이 약관에서 규정하는 사항, 서비스 이용 안내 및 주의 사항을 준수하여야 합니다.</p>
										<p>(8)회원은 내용별로 사이트가 서비스 공지사항에 게시하거나 별도로 공지한 이용 제한 사항을 준수하여야 합니다.</p>
										<hr>
										<h6 class="font-bold">회원 아이디(email)과 비밀번호 관리에 대한 회원의 의무</h6>
										<p>(1)이메일과 비밀번호에 대한 모든 관리는 회원에게 책임이 있습니다. 회원에게 부여된 이메일과 비밀번호의 관리소홀, 부정사용에 의하여 발생하는 모든 결과에 대한 전적인 책임은 회원에게 있습니다.</p>
										<hr>
										<h6 class="font-bold">정보의 변경</h6>
										<p>(1)회원이 주소, 비밀번호 등 고객정보를 변경하고자 하는 경우에는 홈페이지의 회원정보 변경 서비스를 이용하여 변경할 수 있습니다.</p>
										<hr>
										<h6 class="font-bold">계약사항의 해지</h6>
										<p>(1)회원은 서비스 이용계약을 해지할 수 있으며, 해지할 경우에는 본인이 직접 서비스를 통해서 해지가 가능합니다.</p>
										<br>
									</div>
									<!-- 닫기 버튼-->
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">닫기</button>
										<!--// 닫기 버튼 끝 -->
									</div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
					</div>

				</div>
				<!-- /.modal -->

				<div class="form-group">
					<label for='gender' class="col-md-2">성별<span
						class='identify'>★</span></label>
					<div class="col-md-10">
						<label class="radio-inline"> <input type="radio"
							name="gender" id="gender1" value="M" />남자
						</label> <label class="radio-inline"> <input type="radio"
							name="gender" id="gender2" value="F" />여자
						</label>
					</div>
				</div>

				<div class="form-group">
					<label for="postcode" class="col-md-2">우편번호</label>
					<div class="col-md-4">
						<div class="input-group">
							<input type="text" name="postcode" id="postcode"
								class="form-control" />
							<!-- 클릭 시, Javascript 함수 호출 : openDaumPostcode() -->
							<span class="input-group-btn"> <input type='button'
								value="우편번호 찾기" class="btn"
								style="background-color: #303a50; color: #fff;"
								onclick='execDaumPostcode("postcode","addr1","addr2")'></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for='addr1' class="col-md-2">주소</label>
					<div class="col-md-10">
						<div class="input-group">
							<input type="text" name="addr1" id="addr1" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for='addr2' class="col-md-2">상세주소</label>
					<div class="col-md-10">
						<div class="input-group">
							<input type="text" name="addr2" id="addr2" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="submit" class="btn"
							style="background-color: #ff8000; color: #fff;" id="join_submit">가입</button>
						<button type="button" class="btn btn-danger" id="join-cancel">취소</button>
					</div>
				</div>
			</form>
		</div>
		<!--  회원가입 창 끝 -->
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
	<!--  취소  -->
	<script type="text/javascript">
		$(function() {
			$("#join-cancel").click(function() {
				history.back();
			});
		});
	</script>

	<!-- 유효성 검사 -->
	<!-- 이메일 중복체크 시작 -->
	<script type="text/javascript">
	$(function() {
		/** 버튼 클릭시 이벤트 */
		$("#email_check").click(function() {
			// 입력값을 취득하고, 내용의 존재여부를 검사한다.
			var email_val = $("#email").val();

			if (!email_val) { // 입력되지 않았다면?
					swal('이메일이 입력되지 않았습니다.', '이메일을 입력하세요.',
						'question'); // <-- 메시지 표시
						$("#email").focus(); // <-- 커서를 강제로 넣기
					return false; // <-- 실행 중단
			}
			// 위의 if문을 무사히 통과했다면 내용이 존재한다는 의미이므로,
			// 입력된 내용을 Ajax를 사용해서 웹 프로그램에게 전달한다.
			$.get("${pageContext.request.contextPath }/member/email_check.do",
				{email : email_val},function(req) {
				// 사용 가능한 이메일인 경우 --> req = { result: "OK" }
				// 사용 불가능한 이메일인 경우 --> req = { result: "FAIL" }
				if (req.result == 'OK') {swal(
										"성공",
										'사용할 수 있는 이메일입니다.',
										'success');
				}else if (req.result == 'emailformerror') {
					swal("실패",
							'이메일 형식이 잘못되었습니다.',
							'error');
				} else if (req.result == 'overlap') {swal(
						"실패", '이미 사용중인 이메일입니다.', 'error'); 	
				} else {swal("실패", '이미 사용중인 이메일입니다.', 'error'); 	
				$("#email").val("");
				$("#email").focus();
			// 이메일 중복체크 끝 
				};
			}); // end get
		}); // end click
			
		// 닉네임 중복체크 시작
		$("#nickname_check").click(function() {
			// 입력값을 취득하고, 내용의 존재여부를 검사한다.
			var nickname_val = $("#nickname").val();

			if (!nickname_val) { // 입력되지 않았다면?
				swal('닉네임이 입력되지 않았습니다.', '닉네임을 입력하세요.',
				'question'); // <-- 메시지 표시
				$("#nickname").focus(); // <-- 커서를 강제로 넣기
				return false; // <-- 실행 중단
			}
			// 위의 if문을 무사히 통과했다면 내용이 존재한다는 의미이므로,
			// 입력된 내용을 Ajax를 사용해서 웹 프로그램에게 전달한다.
			$.get("${pageContext.request.contextPath}/member/nickname_check.do", {
				nickname : nickname_val
			}, function(req) {
				// 사용 가능한 닉네임인 경우 -> req = { result: "OK" }
				// 사용 불가능한 닉네임인 경우 -> req = { result: "FAIL" }
				if (req.result == 'OK') {
					swal("성공", '사용할 수 있는 닉네임입니다.', 'success');
				} else if(req.result == 'overlap'){
					swal("실패", '이미 사용중인 닉네임입니다.', 'error');
				} else if(req.result == 'nicknamelength') {
					swal("실패", '닉네임은 2글자 이상 6글자 이하로 사용가능합니다.', 'error');		
				} else {
				swal("실패", '이미 사용중인 닉네임입니다.', 'error');
				$("#nickname").val("");
				$("#nickname").focus();
				}
			});
		}); // end click
		
		// 약관 동의, 
		$("#join_submit").click(function(e){
			if(!regex.check('input[name=agree]', '약관에 대한 동의를 체크하세요.')) { return false; }			
		})
	}); // end function	
	</script>
	<!-- 가입확인 끝 -->
</body>
</html>