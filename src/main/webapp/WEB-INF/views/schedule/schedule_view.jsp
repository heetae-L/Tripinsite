<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/area.css">
<!-- 다음 지도 API -->
<script type="text/javascript"
    src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e02979d032dd1511b1037158b28c724&libraries=services"></script>
<!-- //다음 지도 API -->
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath }/assets/css/daumMap.css">
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath }/assets/css/carousel.css">
   <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath }/assets/css/bootstrap4btn.css">
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
    

<style type="text/css">
#like {
    margin: 0;
    padding: 0;
    width: 60px;
    height: 60px;
    border-radius: 60px;
}

#storyContent {
    color: #141E3C;
    font-size: 16px;
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
/** 큰 이미지 영역의 배치와 크기, 여백 설정 */
.view {
	float: left;
	width: 70%;
	height: 500px;
	padding: 5px 0;
}

</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
    <%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

    <!--본문 시작 -->
    <div class="container">
        <div class="content">
            <div class="page-header">
                <h2>${readBoard.subject}</h2>
                <p>
                    작성자 : ${readBoard.writer_nickname } <br> 작성일 :
                    ${readBoard.reg_date }
                </p>
                <p class="hitview">
                    <span>추천: ${readBoard.love} / 조회: ${readBoard.hit }</span>
                </p>
            </div>
            <!-- 지도가 들어갈 박스 -->
            <div style="margin: auto;">
                <div id="map" style="width: 100%; height: 500px;"></div>
            </div>

            <br> <br>

            <!-- 스케쥴러 -->

            <div style="margin: 0px; padding-top: 50px; overflow: hidden;">
                <div id="scheduler_here" class="dhx_cal_container"
                    style='width: 100%; height: 1200px;'>
                    <div class="dhx_cal_navline">
                        <div class="dhx_cal_prev_button">&nbsp;</div>
                        <div class="dhx_cal_next_button">&nbsp;</div>
                        <!-- <div class="dhx_cal_today_button"></div> -->
                        <div class="dhx_cal_date"></div>
                        <!-- <div class="dhx_cal_tab" name="day_tab" style="left: 0px;"></div> -->
                        <!-- <div class="dhx_cal_tab" name="week_tab" style="left: 89px;"></div> -->
                    </div>
                    <div class="dhx_cal_header"></div>
                    <div class="dhx_cal_data"></div>
                </div>
            </div>
            <!-- /스케쥴러 끝 -->

            <br /> <br />


            <c:if test="${fn:length(imageList) > 0}">
                <!-- 캐러셀 영역 구성 -->
                <div id="carouse-generic" class="carousel slide"
                    data-ride="carousel" data-interval="false">
                    <c:choose>
                        <c:when test="${fn:length(imageList) > 0}">
                            <div id="carousel" class="carousel slide" data-ride="carousel">
                                <!-- Indicators -->
                                <ol class="carousel-indicators">
                                    <c:forEach var="imageItem" items="${imageList }"
                                        varStatus="status">
                                        <c:set var="cls" value="" />
                                        <c:if test="${status.index == 0 }">
                                            <c:set var="cls" value="active" />
                                        </c:if>
                                        <li data-target="#carousel" data-slide-to="${status.index }"
                                            class="${cls }"></li>
                                    </c:forEach>
                                </ol>

                                <!-- Wrapper for slides -->
                                <div class="carousel-inner" role="listbox">
                                    <c:forEach var="imageItem" items="${imageList }"
                                        varStatus="status">
                                        <c:set var="cls" value="" />
                                        <c:if test="${status.index == 0 }">
                                            <c:set var="cls" value="active" />
                                        </c:if>

                                        <c:url var="image_url" value="/download.do">
                                            <c:param name="file" value="${imageItem.imagePath }" />
                                        </c:url>
                                        <div class="item ${cls }">
                                            <img src="${image_url}"
                                                onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'">
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Controls -->
                                <c:if test="${fn:length(imageList) > 1 }">
                                    <a class="left carousel-control" href="#carousel"
                                        data-slide="prev"> <span
                                        class="glyphicon glyphicon-chevron-left"></span>
                                    </a>
                                    <a class="right carousel-control" href="#carousel"
                                        data-slide="next"> <span
                                        class="glyphicon glyphicon-chevron-right"></span>
                                    </a>
                                </c:if>

                            </div>
                        </c:when>
                        <c:otherwise>
                            <img
                                src="${pageContext.request.contextPath }/assets/img/no_image.jpg"
                                class="img-responsive" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <!-- 내용영역 시작 -->
            <div id="storyContent">${readBoard.content}</div>
            <!-- 내용영역 끝 -->

            <c:if test="${loginInfo != null }">
                <div class="clearfix" style="margin-bottom: 10px;">
                    <button type="button" class="btn btn-light pull-right" id="like">
                        <c:if test="${loveCount == 0}">
                            <img alt="추천" id="like_img"
                                src="${pageContext.request.contextPath }/assets/img/heart_before.png"
                                style="max-width: 100%; height: auto;">
                        </c:if>
                        <c:if test="${loveCount > 0}">
                            <img alt="추천" id="like_img"
                                src="${pageContext.request.contextPath }/assets/img/heart.png"
                                style="max-width: 100%; height: auto;">
                        </c:if>
                    </button>
                    <p class="pull-right" style="margin-top: 20px; color: #666;">추천하기&nbsp;</p>
                </div>
            </c:if>
            <c:if test="${loginInfo == null}">
                <div class="clearfix">
                    <p class="pull-right" style="color: #666;">추천을 누르시려면 로그인을 해주세요.</p>
                </div>
            </c:if>

            <!-- 버튼들 -->
            <div class="pull-right" style="margin-bottom: 10px;">
                <a class="btn btn-default" type="button" href="${pageContext.request.contextPath}/schedule/schedule_list.do?category=story">목록</a>
                <a href='#edit' data-toggle="modal" class="btn" style="background-color: #ff8000; color: white;">수정하기 </a> 
				<a class="btn btn-danger" type="button" href='#storydel' data-toggle="modal">삭제 </a>
            </div>

            <!-- 다음글/이전글 -->
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <th class="success" style="width: 100px">다음글</th>
                        <td>
                        	<c:choose>
                                <c:when test="${nextBoard != null}">
                                    <c:url var="nextUrl" value="/schedule/schedule_view.do">
                                        <c:param name="category" value="${readBoard.category}" />
                                        <c:param name="schedule" value="${nextBoard.idschedule}" />
                                        <c:param name="idboard" value="${nextBoard.idboard}" />
                                    </c:url>
                                    <a href="${nextUrl}">${nextBoard.subject}</a>
                                </c:when>
                                <c:otherwise>
									다음글이 없습니다.
								</c:otherwise>
							</c:choose>
						</td>
                    </tr>
                    <tr>
                        <th class="success" style="width: 100px">이전글</th>
                        <td>
                        	<c:choose>
                                <c:when test="${prevBoard != null}">
                                    <c:url var="prevUrl" value="/schedule/schedule_view.do">
                                        <c:param name="category" value="${readBoard.category}" />
                                        <c:param name="schedule" value="${prevBoard.idschedule}" />
                                        <c:param name="idboard" value="${prevBoard.idboard}" />
                                    </c:url>
                                    <a href="${prevUrl}">${prevBoard.subject}</a>
                                </c:when>
								<c:otherwise>
								이전글이 없습니다.
                            	</c:otherwise>
                            </c:choose>
						</td>
                    </tr>
                </tbody>
            </table>

            <!-- 댓글 -->
            <form id="comment_form" method="post"
                action="${pageContext.request.contextPath}/bbs/comment_insert.do">
                <!-- 글 번호 상태 유지 -->
                <input type='hidden' name='idboard' value='${readBoard.idboard}' />
                <!-- 닉네임, 비번은 로그인하지 않은 경우만 입력 -->
                <c:if test="${loginInfo == null}">
                    <div class='form-group form-inline'>
                        <!-- 닉네임, 비밀번호 -->
                        <div class="form-group">
                            <input type="text" name="writer_name" class="form-control" placeholder='닉네임' />
                        </div>
                        <div class="form-group">
                            <input type="password" name="writer_pw" class="form-control" placeholder='비밀번호' />
                        </div>
                    </div>
                </c:if>

                <!-- 내용입력, 저장버튼 -->
                <div class='form-group'>
                    <div class="input-group">
                        <textarea class="form-control custom-control" name='content'
                            style="resize: none; height: 80px"></textarea>
                        <span class="input-group-btn">
                            <button type="submit" class="btn"
                                style="background-color: #ff8000; color: #fff; height: 80px">저장</button>
                        </span>
                    </div>
                </div>
            </form>

            <!-- 댓글 리스트 -->
            <ul class="media-list" id="comment_list"></ul>

            <!-- 댓글 수정 Modal -->
            <div class="modal fade" id="comment_edit_modal" tabindex="-1"
                role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content"></div>
                </div>
            </div>

            <!-- 댓글 삭제 Modal -->
            <div class="modal fade" id="comment_delete_modal" tabindex="-1"
                role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content"></div>
                </div>
            </div>
			
			<!-- .modal -->
			<div id="areaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<!-- .modal-dialog -->
			<div id="areaModal2" class="modal-dialog">
			</div>
			</div>
			<!-- /.modal -->
			

            <!-- 게시글 수정 모달 -->
            <div id="edit" class="modal fade" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true">
                <!-- .modal-dialog -->
                <div class="modal-dialog">
                    <!-- .modal-content -->
                    <div class="modal-content">
                        <form method="post" name="edit_password" id="edit_password"
                            action="${pageContext.request.contextPath }/schedule/schedule_update.do">
                            <input type="hidden" name="category" value="${readBoard.category}" /> 
							<input type="hidden" name="idboard" value="${readBoard.idboard}" /> 
							<input type="hidden" name="schedule" value="${readBoard.idschedule}" />
                            <!-- 제목 -->
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">게시글 수정</h4>
                            </div>
                            <!-- 내용 -->
                            <div class="modal-body">
                                <c:choose>
                                    <c:when test="${myBoard == true}">
                                        <!-- 자신의 글에 대한 수정 -->
                                        <p>게시글을 수정하시겠습니까?</p>
                                        <!-- 하단 -->
                                        <div class="modal-footer">
										    <button type="submit" class="btn"
										        style="background-color: #ff8000; color: white;">수정하기</button>
										    <button type="button" class="btn btn-outline-info"
										        data-dismiss="modal">취소</button>
										</div>
                                    </c:when>
									<c:when test="${loginInfo == null}">
                                    	<p>로그인을 해주세요</p>
                                    	<!-- 하단 -->
										<div class="modal-footer">
										    <button type="button" class="btn btn-outline-warning"
										        data-dismiss="modal">확인</button>
										</div>
                                    </c:when>
                                    <c:when test="${loginInfo != null}">
                                    	<p>이 게시물의 작성자가 아닙니다</p>
                                    	<!-- 하단 -->
										<div class="modal-footer">
										    <button type="button" class="btn btn-outline-warning"
										        data-dismiss="modal">확인</button>
										</div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </form>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <!-- 게시글 삭제 모달 -->
            <div id="storydel" class="modal fade" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true">
                <!-- .modal-dialog -->
                <div class="modal-dialog">
                    <!-- .modal-content -->
                    <div class="modal-content">
                        <form name="myform" method="post"
                            action="${pageContext.request.contextPath}/schedule/myschedule_delete.do">
                            <!-- 카테고리와 게시글 번호 상태유지 -->
                            <input type="hidden" name="category" value="${readBoard.category}" /> 
                            <input type="hidden" name="idboard" value="${readBoard.idboard}" /> 
                            <input type="hidden" name="schedule" value="${readBoard.idschedule}" />
                            <!-- 제목 -->
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">게시글 삭제</h4>
                            </div>
                            <!-- 내용 -->
                            <div class="modal-body">
                                <!-- 자신의 글인 경우와 아닌 경우에 대한 분기 -->
                                <c:choose>
                                    <c:when test="${myBoard == true}">
                                        <!-- 자신의 글에 대한 삭제 -->
                                        <p>정말 이 게시물을 삭제하시겠습니까?</p>
                                        <!-- 하단 -->
										<div class="modal-footer">
										    <button type="submit" class="btn"
										        style="background-color: #ff8000; color: white;">삭제하기</button>
										    <button type="button" class="btn btn-outline-info"
										        data-dismiss="modal">취소</button>
										</div>
                                    </c:when>
                                    <c:when test="${loginInfo == null}">
                                    	<p>로그인을 해주세요</p>
                                    	<!-- 하단 -->
										<div class="modal-footer">
										    <button type="button" class="btn btn-outline-danger"
										        data-dismiss="modal">확인</button>
										</div>
                                    </c:when>
                                    <c:when test="${loginInfo != null}">
                                    	<p>이 게시물의 작성자가 아닙니다</p>
                                    	<!-- 하단 -->
										<div class="modal-footer">
										    <button type="button" class="btn btn-outline-danger"
										        data-dismiss="modal">확인</button>
										</div>
                                    </c:when>
                                </c:choose>
                            </div>
                            
                        </form>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
        </div>
    </div>
    <%@ include file="/WEB-INF/views/inc/footer.jsp"%>
    <!-- 댓글 항목에 대한 템플릿 참조 -->
    <script id="tmpl_comment_item" type="text/x-handlebars-template">
    <li class="media" style='border-top: 1px dotted #ccc; padding-top: 15px' id="comment_{{idcomment}}">
        <div class="media-body">
            <h4 class="media-heading clearfix">
                <!-- 작성자,작성일시 -->
                <div class='pull-left'>
                    {{writer_nickname}}
                    <small>
                        / {{reg_date}}
                    </small>
                </div>

                <!-- 수정,삭제 버튼 -->
                <div class='pull-right'>
                    <a href='${pageContext.request.contextPath}/bbs/comment_edit.do?idcomment={{idcomment}}' 
                        data-toggle="modal" data-target="#comment_edit_modal" class='btn btn-warning btn-xs'>
                            <i class='glyphicon glyphicon-edit'></i>
                    </a>
                    <a href='${pageContext.request.contextPath}/bbs/comment_delete.do?idcomment={{idcomment}}' 
                        data-toggle="modal" data-target="#comment_delete_modal" class='btn btn-danger btn-xs'>
                            <i class='glyphicon glyphicon-remove'></i>
                    </a>
                </div>
            </h4>
            <!-- 내용 -->
            <p>{{{content}}}</p>
        </div>
    </li>
    </script>
    
    <!-- 모달 창 템플릿 참조  --> 
    <script id="modal_item_tmpl" type="text/x-handlebars-template">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="areaModalLabel">관광지 세부정보</h4>
			</div>
			<div class="modal-body">
				<img src="{{result.response.body.items.item.firstimage}}" onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'">
				
				<div class="body-title">
					<h4>{{result.response.body.items.item.title}}</h4>
				
				</div>
				<div class=body-view>
					<p>주소: {{{result.response.body.items.item.addr1}}}</p>
					<p>홈페이지: {{{result.response.body.items.item.homepage}}}</p>
					<p>전화번호: {{{result.response.body.items.item.tel}}}</p>
				</div>
				
				<hr>
				
				<div class="body-con">
					<p>{{{result.response.body.items.item.overview}}}</p>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-block" data-dismiss="modal" style="background-color: #ff8000; color: #fff;">확인</button>
			</div>
		</div>
	</script>
    

    <script type="text/javascript">
    <!-- 다음 맵 스크립트 -->
        var container = document.getElementById('map');
        var options = {
            center : new daum.maps.LatLng(37.5788222356, 126.9769930325),
            level : 7
        };

        var map = new daum.maps.Map(container, options);
        var geocoder = new kakao.maps.services.Geocoder();
        // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
        var bounds = new kakao.maps.LatLngBounds();
        
        function setBounds() {
            // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
            // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
            map.setBounds(bounds);
        }
        <!--
    //다음 맵 스크립트 -->
    </script>

    <!-- 좋아요 버튼 -->
    <script type="text/javascript">
        $(function() {
            $(document).on("click","#like",function() {
                        let img = $("#like_img").attr("src");

                        if (img == "${pageContext.request.contextPath}/assets/img/heart_before.png") {
                            $.get('${pageContext.request.contextPath}/schedule/schedule_love_add.do',
                                {
                                    id : '${loginInfo.id}',
                                    idschedule : '${readBoard.idschedule}',
                                    category : '${readBoard.category}'
                                },
                                function(data) {
                                    if (data.rt == "ok") {
                                        alert("추천 감사합니다.");
                                        $("#like_img").attr("src","${pageContext.request.contextPath }/assets/img/heart.png");
                                    } else {alert(data.rt);}
                                });
                        } else {
                            $.get('${pageContext.request.contextPath}/schedule/schedule_love_delete.do',
                                {
                                    id : '${loginInfo.id}',
                                    idschedule : '${readBoard.idschedule}',
                                    category : '${readBoard.category}'
                                },
                                function(data) {
                                    if (data.rt == "ok") {
                                        alert("추천이 취소되었습니다.");
                                        $("#like_img")
                                                .attr(
                                                        "src",
                                                        "${pageContext.request.contextPath }/assets/img/heart_before.png");
                                    } else {
                                        alert(data.rt);
                                    }
                                });
                            }
                        });
                    });
    </script>
    <script type="text/javascript">
        $(function() {
            /* 페이지가 열리면서 동작하도록 이벤트 정의 없이 Ajax 요청 */
            $.get("${pageContext.request.contextPath}/bbs/comment_list.do", {
                idboard : "${readBoard.idboard}"
            }, function(json) {
                if (json.rt != "OK") {
                    alert(json.rt);
                    return false;
                }
                // 템플릿 HTML을 로드
                var template = Handlebars.compile($("#tmpl_comment_item")
                        .html());

                // JSON에 포함된 '&lt;br/&gt;'을 검색해어 <br>로 변경 -> 정규표현식 /~~/g는 문자열 전체의 의미
                for (var i = 0; i < json.item.length; i++) {
                    json.item[i].content = json.item[i].content.replace(
                            /&lt;br\/&gt;/g, "<br/>");
                    // 댓글 아이템 항목 하나를 템플릿에 결합
                    var html = template(json.item[i]);
                    // 결합된 결과를 댓글 목록에 추가
                    $("#comment_list").append(html);
                }
            });

            /* 댓글 작성 폼의 submit 이벤트 Ajax 구현 */
            // <form> 요소의 method, action 속성과 <input> 태그를 Ajax 요청으로 자동 구성함
            $("#comment_form").ajaxForm(
                    function(json) {
                        // json은 API에서 표시하는 전체 데이터
                        if (json.rt != "OK") {
                            alert(json.rt);
                            return false;
                        }

                        // 줄바꿈에 대한 처리 -> 정규표현식 /~~/g 는 문자열 전체의 의미, -> JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br>로 변경
                        json.item.content = json.item.content.replace(
                                /&lt;br\/&gt;/g, "<br>");

                        // alert("댓글 작성 성공");

                        /* Ajax 댓글 저장 기능 개선
                            백엔드의 기능 개선으로 인해 저장결과가 JSON 형태로 전달됨.
                            전달되는 데이터를 템플릿과 결합하여 댓글 목록 부분에 출력하면 작성 결과를 화면에 표시할 수 있다 */
                        // 템플릿 HTML을 로드한다.
                        var template = Handlebars.compile($(
                                "#tmpl_comment_item").html());
                        // JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
                        var html = template(json.item);
                        // 결합된 결과를 댓글 목록에 추가한다.
                        $("#comment_list").append(html);
                        // 폼 태그의 입력값을 초기화 하기 위해서 reset이벤트를 강제로 호출
                        $("#comment_form").trigger('reset');

                    });

            /* 모든 모달창의 캐시 방지 처리 */
            $('.modal').on('hidden.bs.modal', function(e) {
                // 모달창 내의 내용을 강제로 지움
                $(this).removeData('bs.modal');
            });

            /* 댓글 삭제 모달 처리 */
            $(document).on('submit', "#comdel_password", function(e) {
                e.preventDefault();

                // AjaxForm 플러그인의 강제 호출
                $(this).ajaxSubmit(function(json) {
                    if (json.rt != "OK") {
                        alert(json.rt);
                        return false;
                    }
                    alert("삭제되었습니다");
                    // modal 강제로 닫기
                    $("#comment_delete_modal").modal('hide');

                    // JSON 결과에 포함된 댓글일련번호를 사용하여 삭제할 <li>의 id값을 찾는다
                    var idcomment = json.idcomment;
                    $("#comment_" + idcomment).remove();
                });
            });

            /* 댓글 수정 모달 처리 */
            $(document).on(
                    'submit',
                    "#comedit_password",
                    function(e) {
                        e.preventDefault();

                        // AjaxForm 플러그인의 강제 호출
                        $(this).ajaxSubmit(
                                function(json) {
                                    if (json.rt != "OK") {
                                        alert(json.rt);
                                        return false;
                                    }
                                    // 줄바꿈에 대한 처리 -> 정규표현식 /~~/g 는 문자열 전체의 의미, -> JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br>로 변경
                                    json.item.content = json.item.content
                                            .replace(/&lt;br\/&gt;/g, "<br>");

                                    // 템플릿 HTML을 로드한다.
                                    var template = Handlebars.compile($(
                                            "#tmpl_comment_item").html());
                                    // JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
                                    var html = template(json.item);
                                    // 결합된 결과를 기존 댓글 항목과 교체
                                    $("#comment_" + json.item.idcomment)
                                            .replaceWith(html);

                                    // 댓글 수정 모달 강제로 닫기
                                    $("#comment_edit_modal").modal('hide');
                                });
                    });
        });
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
        
    	// 날짜 객체 시,분,초 0으로 세팅
    	function getZero(date){
    		date.setHours(0);
    		date.setMinutes(0);
    		date.setSeconds(0);
    		return date;
    	}
    	

        $(function() {
            // 스케쥴러 설정하기
            scheduler.xy.scale_height = 30; // header x축 높이를 설정함 (sets the height of the X-Axis)
            scheduler.config.fix_tab_position = false;
            scheduler.config.details_on_dblclick = false;

            scheduler.config.default_date = "%Y-%M-%j"; // 기본 날짜 형식 지정 "2019-6-7"
            scheduler.config.day_date = "%F %j일 (%D)"; // 일간, 주간 스케쥴러 헤더 형식 "6월 7일 (금)""
            scheduler.config.month_date = "%Y년 %F"; // 월간 스케쥴러 헤더 상단 달 표시 형식 "2019년 6월"
            scheduler.config.month_day = "%j"; //   월간 스케쥴러 날짜 표시 형식 "1,2,...30,31"

            // 일정 시작일과 종료일 설정
            var startDate = new Date("${schedule.start_day}");
            getZero(startDate);
            var endDate = new Date("${schedule.end_day}");
            getZero(endDate);
			// 일정 종료일 색상처리
            scheduler.blockTime(new Date(endDate), "fullday");

            scheduler.date.week_start = function(startDate) {
                return startDate;
            };

            scheduler.attachEvent("onClick", function(id, e) {
            	e.preventDefault();
            	var ev = scheduler.getEvent(id);
            	var thisEl = e.target;
            	$(thisEl).attr('data-toggle','modal');
 				$(thisEl).attr('data-target','#areaModal');

        		$.get( "${pageContext.request.contextPath }/bbs/bbs_area_modal_item.do",
        				{ "contentId" : "" + ev.contentid },
        					function(data){
        						$("#areaModal2").empty();
        						var template = Handlebars.compile($("#modal_item_tmpl").html());
        			  
        						var html = template(data);
        			 			$("#areaModal2").append(html);
        			 		});
            });
            
            scheduler.attachEvent("onDblClick", function() {
        		return false;    		
            });

            scheduler.config.left_border = true; // 스케쥴러 왼쪽 테두리 생성
            scheduler.config.all_timed = true; // 스케쥴러 여러 날에 걸친 이벤트를 일반 이벤트처럼 보이게 설정
            scheduler.config.multi_day = true; // 여러 날에 걸친 이벤트 생성 옵션
            scheduler.config.show_loading = false; // 이벤트 로딩시 로딩중 이미지 표시
            scheduler.config.readonly = true; // 읽기모드 설정

            scheduler.config.icons_select = [ "icon_delete" ];

            // 스케쥴러 세부창 설정
            scheduler.config.lightbox.sections = [ {
                name : "description",
                height : 200,
                map_to : "text",
                type : "textarea",
                focus : false
            }, {
                name : "time",
                height : 72,
                map_to : "auto",
                type : "time",
                time_format : [ "%Y", "%m", "%d", "%H:%i" ],
                focus : true
            } ];
            scheduler.attachEvent("onLightbox", function() {
                var section = scheduler.formSection('description');
                var api = scheduler.formSection('api');
                section.control.disabled = true;
                api.control.disabled = true;
            });
    		scheduler.templates.event_class = function (start, end, event) {
    			switch(event.contenttypeid){
    				case '12': return "td_event"; // 12: 관광지
    				case '14': return "cf_event"; // 14: 문화시설
    				case '15': return "fp_event"; // 15: 축제공연행사
    				case '25': return "tc_event"; // 25: 여행코드
    				case '28': return "rp_event"; // 28: 레포트
    				case '32': return "ac_event"; // 32: 숙박
    				case '38': return "sh_event"; // 38: 쇼핑
    				case '39': return "rs_event"; // 39: 음식점
    				default: return "df_event";
    			}
    		};

        	// 스케쥴러 초기화
            if (dateDiff(startDate, endDate) == 1) {
                scheduler.init('scheduler_here', startDate, "day");
            } else {
                scheduler.init('scheduler_here', startDate, "week");
            }
            // 스케쥴 불러오기
            $.get("${pageContext.request.contextPath }/schedule/scheTableApi.do",
                    {idschedule : '${schedule.idschedule}'},
                    function(json){
                        scheduler.parse(json.data, "json");
                        var points = new Array();
                        
                        // position에 마커 위,경도 추가 추가
                        for(var i = 0; i<json.data.length; i++){
                            mapx = json.data[i].mapx
                            mapy = json.data[i].mapy
                            
                            points.push(new kakao.maps.LatLng(mapy, mapx));
                        }
                        
                        for(var i = 0; i < points.length; i++){
                            // 마커를 생성합니다
                            var marker = new kakao.maps.Marker({
                                position: points[i]
                            });
                            
                            // 마커가 지도 위에 표시되도록 설정합니다
                            marker.setMap(map);
                            
                            bounds.extend(points[i]);
                            
                            // 인포 윈도우 표시
                            var iwContent = '<div style="padding:5px; margin:auto; text-align:center;">' + json.data[i].text + '</div>' // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                            var iwPosition = points[i]; //인포윈도우 표시 위치입니다
                                
                            var infowindow = new kakao.maps.InfoWindow({
                                    position : iwPosition, 
                                    content : iwContent
                            });
                            
                            // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
                            infowindow.open(map, marker);
                            setBounds();
                        }
                    }); // end get
        });
    </script>
</body>
</html>