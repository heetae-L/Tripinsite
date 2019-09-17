<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/edit_story.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/plugins/datepicker/datepicker.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/plugins/sweetalert/sweetalert2.min.css" />
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<div class="container">
		<div class="content">
			<div class="page-header">
				<h1><i>여행 계획 세우기</i></h1>
			</div>
			<form class="form-horizontal" name="edit_story" method="post" id="edit_story"
				action="${pageContext.request.contextPath }/schedule/mySchedule_edit2.do">
				<div class="form-group border">
					<label for="title" class="col-md-2">제목</label>
					<div class="col-md-10">
						<input type="text" name="title" id="title" class="form-control" />
					</div>
				</div>
				<div class="form-group border">
					<label for="date" class="col-md-2">여행 기간</label>
					<div class="col-md-10">
						<label class="radio-inline"><input type="radio"
							name="date" id="1day" value="1">1일</label> <label
							class="radio-inline"><input type="radio" name="date"
							id="2day" value="2">2일</label> <label class="radio-inline"><input
							type="radio" name="date" id="3day" value="3">3일</label>
							<label class="radio-inline"><input type="radio" name="date" id="4day" value="4">4일</label>
							<label class="radio-inline"><input type="radio" name="date" id="5day" value="5">5일</label>
							<label class="radio-inline"><input type="radio" name="date" id="6day" value="6">6일</label>
							<label class="radio-inline"><input type="radio" name="date" id="7day" value="7">7일</label>
							
					</div>
				</div>
				<div class="form-group border">
					<label for="start_date" class="col-md-2">출발일</label>
					<div class="col-md-10">
						<input type="text" name="start_date" id="datepicker"
							class="form-control" autocomplete=off />
					</div>
				</div>
				<div class="form-group border">
					<label for="tgroup" class="col-md-2">여행구분</label>
					<div class="col-md-10">
						<select name="tgroup" id="tgroup" class="form-control">
							<option value="">선택</option>
							<option value="홀로여행">홀로여행</option>
							<option value="가족여행">가족여행</option>
							<option value="커플여행">커플여행</option>
							<option value="우정여행">우정여행</option>
							<option value="동호회여행">동호회여행</option>
							<option value="비즈니스여행">비즈니스여행</option>
						</select>
					</div>
				</div>
				
				<div class="form-group border">
					<label for="thema" class="col-md-2">여행테마</label>
						<div class="col-md-10">
							<label class="radio-inline" for="thema1"><input type="radio" name="thema" id="thema1" value="식도락">식도락</label>
							<label class="radio-inline" for="thema2"><input type="radio" name="thema" id="thema2" value="쇼핑">쇼핑</label>
							<label class="radio-inline" for="thema3"><input type="radio" name="thema" id="thema3" value="명소">명소</label>
							<label class="radio-inline" for="thema4"><input type="radio" name="thema" id="thema4" value="휴양">휴양</label>
						</div>
				</div>

					
				
				<div class="form-group border">
					<label for="privit" class="col-md-2">공개 설정</label>
					<div class="col-md-10">
						<label class="radio-inline"><input type="radio"
							name="privit" id="open" value="open">공개</label> <label
							class="radio-inline"><input type="radio" name="privit"
							id="private" value="privit">비공개</label>
					</div>
				</div>
				<div class="form-group border">
					<div class="col-md-12">
						<button type="submit" class="btn btn-block"
							style="background-color: #ff8000; color: #eee;">다음 단계로</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
	<script
		src="${pageContext.request.contextPath }/assets/plugins/datepicker/datepicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/assets/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
	<script>
		$(function() {
			$("#datepicker").datepicker({
				autoHide : true, // 날짜 선택 후 자동 숨김 (true/false)
				format : 'yyyy-mm-dd', // 날짜 형식
				language : 'ko-KR', // 언어
				weekStart : 0
			// 시작요일(0=일요일~6=토요일)
			});
		});
	</script>
	<!-- validate 플러그인 참조 -->
    <script src="${pageContext.request.contextPath }/assets/plugins/validate/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath }/assets/plugins/validate/additional-methods.min.js"></script>
    <!-- sweetalert 플러그인 참조 -->
    <script src="${pageContext.request.contextPath }/assets/plugins/sweetalert/sweetalert2.min.js"></script>
    <script type="text/javascript">
        $(function() {

            /** 플러그인의 기본 설정 옵션 추가 */
            jQuery.validator.setDefaults({
                onkeyup:false,          // 키보드입력시 검사 안함
                onclick:false,          // <input>태그 클릭시 검사 안함
                onfocusout:false,       // 포커스가 빠져나올 때 검사 안함
                showErrors:function(errorMap, errorList){ // 에러 발생시 호출되는 함수 재정의
                    // 에러가 있을 때만..
                    if(this.numberOfInvalids()) {
                        // 0번째 에러 메시지에 대한 javascript 기본 alert 함수 사용
                        //alert(errorList[0].message);
                        // 0번째 에러 발생 항목에 포커스 지정
                        //$(errorList[0].element).focus();
                        
                        swal({
                            title: "에러", 
                            text: errorList[0].message, 
                            type: "error"
                        }).then(function(result) {
                            // 창이 닫히는 애니메이션의 시간이 있으므로,
                            // 0.1초의 딜레이 적용 후 포커스 이동
                            setTimeout(function() {
                                $(errorList[0].element).val('');
                                $(errorList[0].element).focus();
                            }, 100);
                        });
                    }
                }
            });

            /** form태그에 부여한 id속성에 대한 유효성 검사 함수 호출 */
            $("#edit_story").validate({
                /** 입력검사 규칙 */
                rules: {
                    // title
                    title: { required: true },
                    // date
                    date: "required",
                    // start_date
                    start_date: { required: true },
                    // tgroup
                    tgroup: { required: true },
                    // thema
                    thema: { required: true },
                    // privit
                    privit: { required: true }
                },
                /** 규칙이 맞지 않을 경우의 메시지 */
                messages: {
                    title: {
                        required: "제목 입력하세요."
                    },
                    date: {
                        required: "여행 기간 입력하세요."
                    },
                    start_date: {
                        required: "출발일 입력하세요."
                    },
                    tgroup: {
                        required: "여행구분 입력하세요."
                    },
                    thema: {
                        required: "여행테마 입력하세요."
                    },
                    privit: {
                        required: "공개 설정 입력하세요."
                    }
                }
            }); // end validate()
        });
    </script>
</body>
</html>