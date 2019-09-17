<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<!-- 다음 지도 API -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=50d251f78f10089be3e6c93bb8717f25&libraries=services"></script>
<!-- //다음 지도 API -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/daumMap.css">
<!-- jQuery UI -->
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- 스케쥴러 플러그인-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_material.css"
	type="text/css">
<script
	src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/assets/plugins/codebase/locale_kr.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_serialize.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_limit.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_all_timed.js"></script>
<!-- DatePicker -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/plugins/datepicker/datepicker.min.css" />
<script src="${pageContext.request.contextPath }/assets/plugins/datepicker/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath }/assets/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>

<style type="text/css">
/* 글자 드래그 방지 */
.noselect {
	-webkit-touch-callout: none; /* iOS Safari */
	-webkit-user-select: none; /* Safari */
	-khtml-user-select: none; /* Konqueror HTML */
	-moz-user-select: none; /* Firefox */
	-ms-user-select: none; /* Internet Explorer/Edge */
	user-select: none; /* Non-prefixed version, currently
                                  supported by Chrome and Opera */
}

/* 스케쥴러 설정 */
.dhx_cal_event div.dhx_footer,
.dhx_cal_event.td_event div.dhx_footer,
.dhx_cal_event.cf_event div.dhx_footer,
.dhx_cal_event.fp_event div.dhx_footer,
.dhx_cal_event.tc_event div.dhx_footer,
.dhx_cal_event.rp_event div.dhx_footer,
.dhx_cal_event.ac_event div.dhx_footer,
.dhx_cal_event.sh_event div.dhx_footer,
.dhx_cal_event.rs_event div.dhx_footer,
.dhx_cal_event.df_event div.dhx_footer{
	background-color: transparent !important;
}
.dhx_cal_event .dhx_body{
	-webkit-transition: opacity 0.1s;
	transition: opacity 0.1s;
	opacity: 0.7;
}
.dhx_cal_event .dhx_title{
	line-height: 12px;
}
.dhx_cal_event_line:hover,
.dhx_cal_event:hover .dhx_body,
.dhx_cal_event.selected .dhx_body,
.dhx_cal_event.dhx_cal_select_menu .dhx_body{
	opacity: 1;
}

.dhx_cal_event.td_event div,
.dhx_cal_event_line.td_event{
     background-color: #0288D1 !important;
     border-color: #0288D1 !important;
}
.dhx_cal_event.dhx_cal_editor.td_event{
     background-color: #0288D1 !important;
}
.dhx_cal_event_clear.td_event{
     color:#0288D1 !important;
}

.dhx_cal_event.cf_event div,
.dhx_cal_event_line.cf_event{
	background-color: #F09105 !important;
	border-color: #F09105 !important;
}
.dhx_cal_event.dhx_cal_editor.cf_event{
	background-color: #F09105 !important;
}
.dhx_cal_event_clear.cf_event{
	color:#F09105 !important;
}

.dhx_cal_event.fp_event div,
.dhx_cal_event_line.fp_event{
	background-color: #323434 !important;
	border-color: #323434 !important;
}
.dhx_cal_event.dhx_cal_editor.fp_event{
	background-color: #323434 !important;
}
.dhx_cal_event_clear.fp_event{
	color:#323434 !important;
}

.dhx_cal_event.tc_event div,
.dhx_cal_event_line.tc_event{
	background-color: #323434 !important;
	border-color: #323434 !important;
}
.dhx_cal_event.dhx_cal_editor.tc_event{
	background-color: #323434 !important;
}
.dhx_cal_event_clear.tc_event{
	color:#323434 !important;
}

.dhx_cal_event.rp_event div,
.dhx_cal_event_line.rp_event{
	background-color: #F09105 !important;
	border-color: #F09105 !important;
}
.dhx_cal_event.dhx_cal_editor.rp_event{
	background-color: #F09105 !important;
}
.dhx_cal_event_clear.rp_event{
	color:#F09105 !important;
}

.dhx_cal_event.ac_event div,
.dhx_cal_event_line.ac_event{
	background-color: #8B5FF0 !important;
	border-color: #8B5FF0 !important;
}
.dhx_cal_event.dhx_cal_editor.ac_event{
	background-color: #8B5FF0 !important;
}
.dhx_cal_event_clear.ac_event{
	color:#8B5FF0 !important;
}

.dhx_cal_event.sh_event div,
.dhx_cal_event_line.sh_event{
	background-color: #057D68 !important;
	border-color: #057D68 !important;
}
.dhx_cal_event.dhx_cal_editor.sh_event{
	background-color: #057D68 !important;
}
.dhx_cal_event_clear.sh_event{
	color:#057D68 !important;
}

.dhx_cal_event.rs_event div,
.dhx_cal_event_line.rs_event{
	background-color: #EE428C  !important;
	border-color: #EE428C  !important;
}
.dhx_cal_event.dhx_cal_editor.rs_event{
	background-color: #EE428C  !important;
}
.dhx_cal_event_clear.rs_event{
	color:#EE428C  !important;
}
/* media query */
@media screen and (min-width: 992px) {
  div.container {
    width : 900px;
  }
}
@media screen and (min-width: 1200px) {
  div.container {
    width : 1100px;
  }
@media screen and (min-width: 1400px) {
  div.container {
    width : 1300px;
  }
}
@media screen and (min-width: 1500px){
  div.container {
    width : 1400px;
  }
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>

	<div class="container">
		<div class="content" style="margin: 50px;">
			<div class="col-md-12">
				<div
					style="background-color: #141e3c; color: #eee; height: 100px; padding: 30px;">
					<div class="story_head" style="text-align: center; margin: auto;">
						<h3 style="margin: auto;">${result.subject }</h3>
					</div>
				</div>
			</div>
			<!-- 컨텐츠 영역 -->
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="col-md-3" style="border: 1px solid #ccc;">
					<!-- 검색폼 -->
					<div class="area_search" style="margin-top: 10px;">
						<div class="pull-right">
							<form id="area_search_input" method="get"
								action="${pageContext.request.contextPath }/schedule/area_search.do">
								<div class="input-group">
									<input type="text" name='keyword' id='keyword' class="form-control" placeholder="장소 검색" />
										<span class="input-group-btn">
										<button class="btn" type="submit" style="background-color: #ff8000; color: white;">
											<i class='glyphicon glyphicon-search'></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
					<!-- //검색폼 -->
					<!-- 검색 결과 -->
					<div>
						<ul style="margin-top: 70px;" id="search_result">
							<li>검색 후 검색 결과에서 드래그앤드롭 하거나 버튼을 클릭하세요.</li>
						</ul>
						<nav class="text-center">
							<ul class="pagination">
								<li><button type="button" class="btn btn-default hidden" id="prev">&laquo;</button></li>
								<li><button type="button" class="btn btn-default hidden" id="next">&raquo;</button></li>
							</ul>
						</nav>
					</div>
					<!-- // 검색 결과 -->
					<hr />
				</div>
				<div class="col-md-9" style="padding-bottom: 50px;">
					<!-- 지도가 들어갈 박스 -->
					<div id="map" style="width: 100%; height: 320px;"></div>

					<!-- 스케쥴러 -->
					<div style="margin: 0px; padding-top: 50px; overflow: hidden;">
						<button type="button" class="btn btn-default" id="planSave"
							style="background-color: #303a50; color: #fff;">일정 수정</button>
						<button type="button" class="btn btn-normal" id="planDelete">일정
							삭제</button>
						<div id="scheduler_here" class="dhx_cal_container"
							style='width: 100%; height: 1200px;'>
							<div class="dhx_cal_navline">
								<div class="dhx_cal_prev_button">&nbsp;</div>
								<div class="dhx_cal_next_button">&nbsp;</div>
								<!-- <div class="dhx_cal_today_button"></div>  -->
								<div class="dhx_cal_date"></div>
								<div class="dhx_cal_tab" name="day_tab" style="left: 0px;"></div>
								<div class="dhx_cal_tab" name="week_tab" style="left: 89px;"></div>
							</div>
							<div class="dhx_cal_header"></div>
							<div class="dhx_cal_data"></div>
						</div>
					</div>
					<!-- /스케쥴러 끝 -->
					<!-- //테이블 끝 -->
					<hr />
					<form method="post" class="form-horizontal"
						enctype="multipart/form-data"
						action="${pageContext.request.contextPath }/schedule/schedule_update_ok.do">
						<input type="hidden" name="idboard" id="idboard" value="${result.idboard}" />
						<input type="hidden" name="schedule" id="schedule" value="${result.idschedule}" />
						<div class="form-group border">
							<label for="subject" class="col-md-2">제목</label>
							<div class="col-md-10">
								<input type="text" name="subject" id="subject"
									class="form-control" value="${result.subject}" />
							</div>
						</div>

						<div class="form-group border">
							<label for="start_date" class="col-md-2">출발일</label>
							<div class="col-md-10">
								<input type="text" name="start_date" id="datepicker"
									class="form-control" autocomplete=off
									value="${result.start_day}" />
							</div>
						</div>
						<div class="form-group border">
							<label for="tgroup" class="col-md-2">여행구분</label>
							<div class="col-md-10">
								<select name="tgroup" id="tgroup" class="form-control">
									<option value="">선택</option>
									<option value="홀로여행"
										<c:if test="${result.tag == '홀로여행'}">selected</c:if>>홀로여행</option>
									<option value="가족여행"
										<c:if test="${result.tag == '가족여행'}">selected</c:if>>가족여행</option>
									<option value="커플여행"
										<c:if test="${result.tag == '커플여행'}">selected</c:if>>커플여행</option>
									<option value="우정여행"
										<c:if test="${result.tag == '우정여행'}">selected</c:if>>우정여행</option>
									<option value="동호회여행"
										<c:if test="${result.tag == '동호회여행'}">selected</c:if>>동호회여행</option>
									<option value="비즈니스여행"
										<c:if test="${result.tag == '비즈니스여행'}">selected</c:if>>비즈니스여행</option>
								</select>
							</div>
						</div>

						<div class="form-group border">
							<label for="thema" class="col-md-2">여행테마</label>
							<div class="col-md-10">
								<label class="radio-inline" for="thema1"><input
									type="radio" name="thema" id="thema1" value="1"
									<c:if test="${result.theme == 1}">checked</c:if>>식도락</label> <label
									class="radio-inline" for="thema2"><input type="radio"
									name="thema" id="thema2" value="2"
									<c:if test="${result.theme == 2}">checked</c:if>>쇼핑</label> <label
									class="radio-inline" for="thema3"><input type="radio"
									name="thema" id="thema3" value="3"
									<c:if test="${result.theme == 3}">checked</c:if>>명소</label> <label
									class="radio-inline" for="thema4"><input type="radio"
									name="thema" id="thema4" value="4"
									<c:if test="${result.theme == 4}">checked</c:if>>휴양</label>
							</div>
						</div>

						<div class="form-group border">
							<label for="privit" class="col-md-2">공개 설정</label>
							<div class="col-md-10">
								<label class="radio-inline"><input type="radio"
									name="privit" id="open" value="0"
									<c:if test="${result.privit == 0}">checked</c:if>>공개</label> <label
									class="radio-inline"><input type="radio" name="privit"
									id="privite" value="1"
									<c:if test="${result.privit == 1}">checked</c:if>>비공개</label>
							</div>
						</div>
						<hr style="margin-top: 80px;" />
						<div class="text_area form-group" id="text_area">
							<button class="btn pull-right toggle"
								style="background-color: #ff8000; color: #fff;"
								id="story_text_button" type="button">스토리 작성</button>
							<!-- 내용 -->
							<div class="form-group hidden" id="story_text">
								<div class="col-sm-12">
									<textarea class="ckeditor" name="content" id="story_content"
										style="width: 100%;" rows="15" placeholder="내용을 입력하세요.">${result.content}</textarea>
								</div>
								<!-- 파일업로드 -->
								<div class="col-sm-10">
									<input type="file" class="form-control" id="file" name="file" multiple>

									<!-- 첨부파일 리스트가 존재할 경우 삭제할 항목 선택 가능 -->
									<c:if test="${imageList != null }">
										<c:forEach var="file" items="${imageList }">
											<!-- 이미지를 다운받기 위한 URL 구성 -->
											<c:url value="download.do" var="downloadUrl">
												<c:param name="file"
													value="${file.file_dir }/${file.file_name }" />
											</c:url>

											<div class="checkbox">
												<label> <input type="checkbox" name="del_file"
													id="del_file" value="${file.idfile }" />
													${file.origin_name } 삭제하기
												</label>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-block"
								style="background-color: #141e3c; color: #fff;">글 저장하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

	<!-- template -->
	<script id="search_item_tmpl" type="text/x-handlebars-template">
		<input type="hidden" id="page" value="{{page}}"/>
		{{#result.response.body.items.item}}
			<li>
				<div class="draggable noselect">
					<h4 name='title'>{{title}}</h4>
					<p name='addr'>{{addr1}}{{addr2}}</p>
					<input type="hidden"
						value='{"contentid":"{{contentid}}","title":"{{title}}","areacode":"{{areacode}}","sigungucode":"{{sigungucode}}","addr1":"{{addr1}}","addr2":"{{addr2}}","contenttypeid":"{{contenttypeid}}","mapx":"{{mapx}}","mapy":"{{mapy}}","tel":"{{tel}}"}'>
				</div>
				<button type="button" class="btn btn-block box-add" style="background-color: #ff8000; color: white;">일정추가</button>
			</li>
		{{/result.response.body.items.item}}
	</script>

	<!-- 스케쥴러 이벤트 -->
	<script type="text/javascript">
	// 두 개의 날짜를 비교하여 차이를 알려주는 함수
	function dateDiff(date1, date2, abs){ // abs = true or false
		var diffDate1 = date1 instanceof Date ? date1 : new Date(date1);
		var diffDate2 = date2 instanceof Date ? date2 : new Date(date2);

		diffDate1 = new Date(diffDate1.getFullYear(), diffDate1.getMonth(), diffDate1.getDate());
		diffDate2 = new Date(diffDate2.getFullYear(), diffDate2.getMonth(), diffDate2.getDate());

		if(abs == true){
			var diff = Math.abs(diffDate2.getTime() - diffDate1.getTime());
		} else {
			var diff = diffDate2.getTime() - diffDate1.getTime();
		}
		diff = Math.ceil(diff / (1000 * 3600 * 24));

		return diff;
	}
	// 날짜 YYYY-MM-DD 형식으로 만들기
	function getFormatDate(date){
		var year = date.getFullYear();	//yyyy
		var month = (1 + date.getMonth());	//M
		month = month >= 10 ? month : '0' + month;	//month 두자리로 저장
		var day = date.getDate();	//d
		day = day >= 10 ? day : '0' + day;	//day 두자리로 저장
		return year + '-' + month + '-' + day;
	}

	// 날짜 객체 시,분,초 0으로 세팅
	function getZero(date){
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}
	
	// api json의 title 내부의 쌍따옴표를 외따옴표로 치환하는 함수
	function changeTitle(apiStr){
		var a = apiStr.indexOf("title");
		var b = apiStr.indexOf("areacode");
		a += 8;
		b -= 3;
		var c = apiStr.substring(a,b);
		var d = "";
	    var j = -1, k = 0;
		do {
			j = c.indexOf("\"", j + 1);
			if (j != -1) {
				d = d + c.substring(k, j) + "\'";
				k = j + 1;
			}
		} while( j + 1 < c.lenth && j != -1);
			
		if ( d != "") {
			apiStr = apiStr.substring(0,a) + d + apiStr.substring(b);
		}
		return apiStr;
	}
	

	var start_day = new Date("${result.start_day}");
	var end_day = new Date("${result.end_day}");
	getZero(start_day);
	getZero(end_day);
	var date = dateDiff(start_day, end_day, true);

     $(function(){
    	 /** 다음 맵 스크립트 */
    	 var container = document.getElementById('map');
    	 var options = {
    		center : new daum.maps.LatLng(37.5788222356, 126.9769930325),
	  		level : 3
	  		};

	  	var map = new daum.maps.Map(container, options);
	  	var geocoder = new daum.maps.services.Geocoder();

	  	// 지도에 표시된 마커 객체를 가지고 있을 배열
	  	var markers = [];
	  	var infowindows = [];

	  	// 수정시 br처리
	  	var str = $('#story_content').val();
		str = str.split('<br/>').join("\r\n");
		$('#story_content').val(str);

    	/** 스토리 작성 이벤트 */
    	$('#story_text_button').click(function() {

			if ($('#story_text_button').hasClass("view") == true) {
				$('#story_text_button').removeClass("view");
				$("#story_text").addClass("hidden");
				$('#story_text_button').html("스토리 작성");
				$('#story_content').val('');
				return;
				}

				$("#story_text").removeClass("hidden");
				$("#story_text_button").addClass("view");
				$('#story_text_button').html("스토리 작성 취소");
		});

		/** Ajax 지역 검색 */
		$("#area_search_input").ajaxForm(
			function(data) {
				$("#search_result").empty();

				var template = Handlebars.compile($("#search_item_tmpl").html());

				var html = template(data);

				$("#search_result").append(html);

				if(parseInt(data.totalCount) > (parseInt(data.page) * 12)){
					$("#next").removeClass("hidden");
				}

		});

		/* 버튼 누르면 스케쥴러에 일정 추가됨 */
		$(document).on('click','.box-add',function(event) {
			var drop_point = scheduler.getActionData(event);
			 var node = $(this).parent().children('div').clone();
			 if(drop_point.date){
				 // 새로운 일정 이벤트 추가
				 node.find('h4').attr('style', "font-size:10px;");
				 node.find('p').attr('style', "font-size:8px;");
				 var apiStr = changeTitle(node.find('input').val());
				 var api = JSON.parse(apiStr);
					
				 var ev = {
 							 text : node.html(),
 							 start_date : drop_point.date,
 							 end_date : scheduler.date.add(drop_point.date, scheduler.config.event_duration, 'minute'),
 							 api : node.find('input').val(),
 							 contenttypeid : api.contenttypeid
 							 };
				 // 스케쥴러에 추가한다
				 scheduler.addEvent(ev);
			 }

			// 선택한 항목에 대한 Map이벤트
			var addr = $(this).prev().children('p').text();

			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(addr, function(result, status) {

			    // 정상적으로 검색이 완료됐으면
			      if (status === daum.maps.services.Status.OK) {
			         var coords = new daum.maps.LatLng(result[0].y, result[0].x);

			         // 결과값으로 받은 위치를 마커로 표시합니다
			         var marker = new daum.maps.Marker({
			             map: map,
			             position: coords
			         });

			    	 // 생성된 마커를 배열에 추가합니다
			         markers.push(marker);

			         // 인포윈도우로 장소에 대한 설명을 표시합니다
			         var infowindow = new daum.maps.InfoWindow({
			             content: '<div style="width:150px;text-align:center;padding:6px 0;">' + addr + '</div>'
			         });
			         infowindow.open(map, marker);
			         infowindows.push(infowindow);

			         // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			         map.setCenter(coords);
			     }
			 });

		});

		// 배열에 추가된 마커들을 지도에 삭제하는 함수
		function setMarkers(map) {
		    for (var i = 0; i < markers.length; i++) {
		        markers[i].setMap(map);
		        infowindows[i].close();
		    }
		}

		/** 동적 html 제어를 위해 document로 제어 */
		$(document).on(function(){
			$.get(url + "/schedule/area_search.do",

					function(data){
						$("#search_result").empty();

						var template = Handlebars.compile($("#search_item_tmpl").html());

						var html = template(data);
						$("#search_result").append(html);

						if(parseInt(data.totalCount) > (parseInt(data.page) * 12)){
							$("#next").removeClass("hidden");
						}

				})
		}); // end $.get

		/** 페이징 처리 시작 */
		var url = "${pageContext.request.contextPath}";

		$("#next").click(function(){
			$.get(url + "/schedule/area_search.do",
			{
				keyword : $("#keyword").val(),
				page : $("#page").val(),
				action : "next"
			},
			function(data){
	  			$("#search_result").empty();
	  			var template = Handlebars.compile($("#search_item_tmpl").html());

	  			var html = template(data);
				$("#search_result").append(html);

				$("#prev").removeClass("hidden");

				if(parseInt(data.totalCount) <= (parseInt(data.page) * 12)){
					$("#next").addClass("hidden");
				}
			});
		});

		$("#prev").click(function(){
			$.get(url + "/schedule/area_search.do",
			{
				keyword : $("#keyword").val(),
				page : $("#page").val(),
				action : "prev"
			},
			function(data){
				$("#search_result").empty();
		  		var template = Handlebars.compile($("#search_item_tmpl").html());

				var html = template(data);
				$("#search_result").append(html);

				$("#next").removeClass("hidden");

				if(parseInt(data.page) < 2){
					$("#prev").addClass("hidden");
				}
			});
		});
		/** 페이징 처리 끝 */

		/** datepicker */
    	$("#datepicker").datepicker({
			autoHide : true, // 날짜 선택 후 자동 숨김 (true/false)
			format : 'yyyy-mm-dd', // 날짜 형식
			language : 'ko-KR', // 언어
			weekStart : 0 // 시작요일(0=일요일~6=토요일)
			});
		$("#datepicker").on('hide.datepicker', function (e) {
			var dateText = $("#datepicker").datepicker('getDate', true);
			var diff = dateDiff(start_day, dateText, false);
			if(diff == 0){
    			return false;
    		}
			var evs = scheduler.getEvents(); //모든 이벤트 가져오기
			for(var i=0;i<evs.length; i++){  //각 이벤트 처리
				var ev = evs[i];
				ev.start_date = scheduler.date.add(ev.start_date, diff, 'day');
				ev.end_date = scheduler.date.add(ev.end_date, diff, 'day');
				scheduler.updateEvent(ev.id);
				var sd = dateToStr(new Date(ev.start_date));
				var ed = dateToStr(new Date(ev.end_date));

				$.ajax({ // sche_table의 start_date, end_date 수정
					type :'POST',
					url : '${pageContext.request.contextPath }/schedule/scheTableApi.do/'+ev.id, //데이터를 요청할 페이지
					dataType : 'json', // 받아올 데이터 유형
					data : { "id": ev.id, "start_date": sd, "end_date": ed }, // 요청할 페이지에 전송할 파라메터
					async:false,
					error : function() { //에러 발생시 처리함수
						swal('에러가 발생하였습니다.');
					},
					success : function(req){
						// swal(req.rt);
						}
					});
				}
			start_day = new Date(dateText);
			getZero(start_day);
			end_day.setDate( end_day.getDate() + diff );
			scheduler.config.limit_start = new Date(start_day);
			$.ajax({ // schedule의 start_day, end_day 수정
				type : 'POST',
				url : '${pageContext.request.contextPath }/schedule/scheduleApi.do/', //데이터를 요청할 페이지
				dataType : 'json', // 받아올 데이터 유형
				data : { "idschedule": "${result.idschedule}", "start_day": getFormatDate(start_day), "end_day": getFormatDate(end_day) }, // 요청할 페이지에 전송할 파라메터
				async:false,
				error : function() { //에러 발생시 처리함수
					swal('에러가 발생하였습니다.');
					return false;
					},
				success : function(req){
					// swal(req.rt);
					}
				});
			scheduler.updateView(new Date(start_day));
			});

		// 스케쥴러 설정하기
		scheduler.xy.scale_height = 30; // header x축 높이를 설정함 (sets the height of the X-Axis)
		// scheduler.config.start_on_monday = false;	// 월요일부터 시작 옵션
		scheduler.config.fix_tab_position = false;
		scheduler.config.details_on_dblclick=false;

		scheduler.config.default_date = "%Y-%M-%j"; // 기본 날짜 형식 지정 "2019-6-7"
		scheduler.config.day_date = "%F %j일 (%D)";		// 일간, 주간 스케쥴러 헤더 형식 "6월 7일 (금)""
		scheduler.config.month_date = "%Y년 %F";	// 월간 스케쥴러 헤더 상단 달 표시 형식 "2019년 6월"
		scheduler.config.month_day = "%j";	//	월간 스케쥴러 날짜 표시 형식 "1,2,...30,31"

		scheduler.config.limit_start = new Date(start_day);
		scheduler.date.week_start = function(start_day){
			return start_day;
			};
		scheduler.attachEvent("onDblClick", function(){
			return false;
			});

		scheduler.config.edit_on_create = false;	// 이벤트 생성시 편집창 띄울 것인지 설정
		scheduler.config.event_duration = 120;	// 이벤트 생성시 자동으로 지속 시간(분단위) 정해줌
		scheduler.config.auto_end_date = true;	// 이벤트 생성시 자동으로 종료 시간 계산해줌
		scheduler.config.left_border = true;	// 스케쥴러 왼쪽 테두리 생성
		scheduler.config.all_timed = true; // 스케쥴러 여러 날에 걸친 이벤트를 일반 이벤트처럼 보이게 설정
		scheduler.config.multi_day = true;	// 여러 날에 걸친 이벤트 생성 옵션
		scheduler.config.show_loading = true;	// 이벤트 로딩시 로딩중 이미지 표시
		scheduler.config.dblclick_create = false;	// 더블클릭 스케쥴 생성 방지
		scheduler.config.drag_create = false; // 드래그 스케쥴 생성 방지
		scheduler.config.icons_select = [ "icon_delete" ];

		// 스케쥴러 세부창 설정
		scheduler.config.lightbox.sections=[
			{name:"description", height:200, map_to:"text", type:"textarea", focus:false},
			{name:"time", height:72, map_to:"auto", type:"time", time_format:["%Y", "%m", "%d", "%H:%i"], focus:true}
			];
		scheduler.attachEvent("onLightbox", function(){
			var section = scheduler.formSection('description');
			var api = scheduler.formSection('api');
			section.control.disabled = true;
			api.control.disabled = true;
			});

		// scheduler.templates.format_date 설정
		var dateToStr = scheduler.date.date_to_str("%Y-%m-%d %H:%i");
		scheduler.templates.format_date = function(date){
			return dateToStr(date);
			};

		// 스케쥴러 toJSON() 변환 대상 데이터 지정
		scheduler.data_attributes = function(){
			return [
				["id"],
				["api"], // "api" 구조 [contentid, title, areacode, sigungucode, adr1, addr2, contenttpeid, mapx, mapy, tel]
				["start_date",scheduler.templates.format_date],
				["end_date",scheduler.templates.format_date]];
			};
		
		scheduler.templates.event_class = function (start, end, event) {
			switch(event.contenttypeid){
				case '12': return "td_event"; // 12: 관광지
				case '14': return "cf_event"; // 14: 문화시설
				case '15': return "fp_event"; // 15: 축제공연행사
				case '25': return "tc_event"; // 25: 여행코스
				case '28': return "rp_event"; // 28: 레포트
				case '32': return "ac_event"; // 32: 숙박
				case '38': return "sh_event"; // 38: 쇼핑
				case '39': return "rs_event"; // 39: 음식점
				default: return "df_event";
			}
		};

		// 스케쥴러 초기화
		if( dateDiff(start_day, end_day, false) == 1 ){
			scheduler.init('scheduler_here', start_day, "day");
		} else{
			scheduler.init('scheduler_here',start_day,"week");
		}
		scheduler.load("${pageContext.request.contextPath }/schedule/scheTableApi.do?idschedule=${result.idschedule}");

		/** 스케쥴러 이벤트 추가시 핸들러 */
		scheduler.attachEvent('onEventAdded', function(id, event) {
			var sd = dateToStr(new Date(event.start_date));
			var ed = dateToStr(new Date(event.end_date));

			$.ajax({
				type :'POST',
				url : '${pageContext.request.contextPath }/schedule/scheTableApi.do', //데이터를 요청할 페이지
				dataType : 'json', // 받아올 데이터 유형
				data : { "start_date": sd,
					"end_date": ed,
					"api": event.api,
					"idschedule": "${result.idschedule}"  }, // 요청할 페이지에 전송할 파라메터
					async:false,
					error : function() { //에러 발생시 처리함수
						swal('에러가 발생하였습니다.');
						},
					success : function(req){
						// swal(req.rt);
						scheduler.changeEventId(id, req.id);
						var ev = scheduler.getEvent(req.id);
						var check = 0;
						if (dateDiff(ev.start_date, start_day, false) > 0){
							start_day = new Date(ev.start_date);
							getZero(start_day);
							check++;
							$("#datepicker").val(getFormatDate(start_day));
							}
						if (dateDiff(end_day, ev.end_date, false) >= 0){
							end_day = new Date(ev.end_date);
							getZero(end_day);
							end_day.setDate(end_day.getDate()+1);
							check++;
							}
						if (check > 0){
							$.ajax({
								type : 'POST',
								url : '${pageContext.request.contextPath }/schedule/scheduleApi.do/', //데이터를 요청할 페이지
								dataType : 'json', // 받아올 데이터 유형
								data : { "idschedule": "${result.idschedule}", "start_day": getFormatDate(start_day), "end_day": getFormatDate(end_day) }, // 요청할 페이지에 전송할 파라메터
								async:false,
								error : function() { //에러 발생시 처리함수
									swal('에러가 발생하였습니다.');
									return false;
									},
								success : function(req){
									// swal(req.rt);

										}
								});
							}// end if
						} // end success
					}); // end ajax
				}); // end attachEvent

		/** 스케쥴러 이벤트 변경시 핸들러 */
		scheduler.attachEvent('onEventChanged', function(id, event) {
			var sd = dateToStr(new Date(event.start_date));
			var ed = dateToStr(new Date(event.end_date));

			$.ajax({
				type :'POST',
				url : '${pageContext.request.contextPath }/schedule/scheTableApi.do/'+id, //데이터를 요청할 페이지
				dataType : 'json', // 받아올 데이터 유형
				data : { "id": id, "start_date": sd, "end_date": ed }, // 요청할 페이지에 전송할 파라메터
				async:false,
				error : function() { //에러 발생시 처리함수
					swal('에러가 발생하였습니다.');
					return false;
					},
				success : function(req){
					// swal(req.rt);
					// scheduler 내의 일정을 비교해서 일정 시작일과 종료일을 찾기
					var evs = scheduler.getEvents(); //모든 일정 이벤트 얻기
					var min = new Date(evs[0].start_date);
					var max = new Date(evs[0].end_date);
					for(var i=1; i<evs.length; i++){ //일정 시작일과 종료일 찾기
						var ev = evs[i];
						if(dateDiff(ev.start_date, min, false) > 0){
							min = new Date(ev.start_date);
							}
						if(dateDiff(max, ev.end_date, false) >= 0){
							max = new Date(ev.end_date);
							}
						}
					getZero(min);
					getZero(max);
					max.setDate(max.getDate() + 1);
					$("#datepicker").val(getFormatDate(min));
					$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath }/schedule/scheduleApi.do/', //데이터를 요청할 페이지
						dataType : 'json', // 받아올 데이터 유형
						data : { "idschedule": "${result.idschedule}", "start_day": getFormatDate(min), "end_day": getFormatDate(max) }, // 요청할 페이지에 전송할 파라메터
						async:false,
						error : function() { //에러 발생시 처리함수
							swal('에러가 발생하였습니다.');
							return false;
						},
						success : function(req){
							// swal(req.rt);
							start_day = new Date(min);
							end_day = new Date(max);
							}
						});
					} // end success : function(req)
				});
			});

		/** 스케쥴러 이벤트 삭제시 핸들러 */
		scheduler.attachEvent('onEventDeleted', function(id, event) {
			$.ajax({
				type :'DELETE',
				url : '${pageContext.request.contextPath }/schedule/scheTableApi.do/'+id, //데이터를 요청할 페이지
				dataType : 'json', // 받아올 데이터 유형
				data : { "result": event }, // 요청할 페이지에 전송할 파라메터
				async:false,
				error : function() { //에러 발생시 처리함수
					swal('에러가 발생하였습니다.');
					},
				success : function(req){
					// swal(req.rt);
					// scheduler 내의 일정을 비교해서 일정 시작일과 종료일을 찾기
					var evs = scheduler.getEvents(); // 스케쥴러 모든 이벤트 얻기
					var min = new Date(evs[0].start_date);
					var max = new Date(evs[0].end_date);
						for(var i=1; i<evs.length; i++){  // 일정 시작일과 종료일 찾기
							var ev = evs[i];
							if(dateDiff(ev.start_date, min, false) > 0){
								min = new Date(ev.start_date);
								}
							if(dateDiff(max, ev.end_date, false) >= 0){
								max = new Date(ev.end_date);
								}
							}
						getZero(min);
						getZero(max);
						max.setDate(max.getDate() + 1);
						$("#datepicker").val(getFormatDate(min));
						$.ajax({
							type : 'POST',
							url : '${pageContext.request.contextPath }/schedule/scheduleApi.do/', //데이터를 요청할 페이지
							dataType : 'json', // 받아올 데이터 유형
							data : { "idschedule": "${result.idschedule}", "start_day": getFormatDate(min), "end_day": getFormatDate(max) }, // 요청할 페이지에 전송할 파라메터
							async:false,
							error : function() { //에러 발생시 처리함수
								swal('에러가 발생하였습니다.');
								return false;
								},
							success : function(req){
								// swal(req.rt);
								start_day = new Date(min);
								end_day = new Date(max);
								}
							});
					} // end success : function(req)
				});
			});

		/** 스케쥴러 이벤트 전부 삭제시 핸들러 */
		scheduler.attachEvent("onClearAll", function (){
			$.ajax({
				type :'POST',
				url : '${pageContext.request.contextPath }/schedule/scheTableApi.do/idschedule/', //데이터를 요청할 페이지
				dataType : 'json', // 받아올 데이터 유형
				data : { "idschedule": "${result.idschedule}" }, // 요청할 페이지에 전송할 파라메터
				async:false,
				error : function() { //에러 발생시 처리함수
					swal('DB에서 삭제 에러가 발생하였습니다.');
					return false;
					},
				success : function(req){
					// swal(req.rt);
					}
				});
			});

		// 스케쥴러 전체 저장 버튼 클릭시 이벤트
		$("#planSave").click(function(){
			swal('확인', '여행 일정은 실시간 저장중입니다.', 'success');
			});

		// 스케쥴러 전체 삭제 버튼 클릭시 이벤트
		$("#planDelete").click(function(){
			swal({
				title: '확인',	// 제목
				text: "정말 모든 일정을  삭제하시겠습니까?",	// 내용
				type: 'warning',	// 종류
				confirmButtonText: '예',	// 확인버튼 표시 문구
				showCancelButton: true,	// 취소버튼 표시 여부
				cancelButtonText: '아니오'	// 취소 버튼 표시 문구
				}).then(function(result){	// 버튼이 눌러졌을 경우의 콜백 연결
					if (result.value){	// 예 버튼이 눌러진 경우
						scheduler.clearAll();
						setMarkers(null);
						swal('삭제', '성공적으로 삭제되었습니다.', 'success');
					} else if (result.dismiss == 'cancel'){	// 아니오 버튼이 눌러진 경우
						swal('취소', '삭제가 취소되었습니다.', 'error');
						}
				});
			});

		// 장소 검색 후 스케쥴러로 드래그 앤 드롭 기능 사용하기
		$(document).on("mouseover",".draggable", function(){
			$(this).draggable({
				cursor: "move",
				cursorAt: {left: 10, top: 10},
				helper: function(){
					var helper = $(this).clone();
					helper.find('h4').attr('style', "font-size:10px;");
					helper.find('p').attr('style', "font-size:8px;");
					helper.css("width", "215px");

					return helper;
					},
				zIndex: 100,
				opacity: 0.5,
				revert: true,
				revertDuration: 50,
				start : function(event, ui){
					// 선택한 항목에 대한 Map이벤트
					var addr = $(this).find('p').text();
					// 주소로 좌표를 검색합니다
					geocoder
					.addressSearch(
							addr,
							function(result, status) {

								// 정상적으로 검색이 완료됐으면
								if (status === daum.maps.services.Status.OK) {
									var coords = new daum.maps.LatLng(
											result[0].y,
											result[0].x);

									// 결과값으로 받은 위치를 마커로 표시합니다
									var marker = new daum.maps.Marker(
											{
												map : map,
												position : coords
											});

									// 생성된 마커를 배열에 추가합니다
									markers.push(marker);

									// 인포윈도우로 장소에 대한 설명을 표시합니다
									var infowindow = new daum.maps.InfoWindow(
											{
												content : '<div style="width:150px;text-align:center;padding:6px 0;">'
														+ addr
														+ '</div>'
											});
									infowindow.open(map,
											marker);
									infowindows
											.push(infowindow);

									// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
									map.setCenter(coords);
								}
							});
					},
				stop: function(event, ui){
					var drop_point = scheduler.getActionData(event);
					var node = event.target || event.srcElement;
					var apiStr = changeTitle(ui.helper.find('input').val());
					var api = JSON.parse(apiStr);
					
					if(drop_point.date){
						// 새로운 일정 이벤트 추가
						var ev = {
								text : ui.helper.html(),
								start_date : drop_point.date,
								end_date : scheduler.date.add(drop_point.date, scheduler.config.event_duration, 'minute'),
								api : ui.helper.find('input').val(),
								contenttypeid : api.contenttypeid
								};
						
						// 스케쥴러에 추가한다
						scheduler.addEvent(ev);

						} // end if
					} // end stop: function
				}); // end .draggable
			}); // end document.on
		}); // end $(function())
   </script>
</body>
</html>
