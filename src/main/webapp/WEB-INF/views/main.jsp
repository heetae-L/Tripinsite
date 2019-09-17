<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/area.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/intro.css">
<script type="text/javascript">
	var url = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/assets/js/main_js.js"
	type="text/javascript">
	
</script>
<style type="text/css">
/** 목록 정의 초기화 및 목록 박스 좌측 배치 */
.thumbnail2 {
	padding: 0;
	margin: 0;
	list-style: none;
	width: 30%;
	height: 500px;
	float: left;
}

/** 목록의 각 항목에 대한 크기 및 여백 설정 */
.thumbnail2 li {
	width: 100%;
	height: 33%;
	padding: 5px 10px;
}

/** 썸네일 이미지의 크기 설정 */
.thumbnail2 img {
	width: 100%;
	height: 100%;
}

/** 큰 이미지 영역의 배치와 크기, 여백 설정 */
.view {
	float: left;
	width: 70%;
	height: 500px;
	padding: 5px 0;
}

/** 큰 이미지의 크기 설정 */
.view img {
	width: 100%;
	height: 100%;
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
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
	<!-- <%@ include file="/WEB-INF/views/inc/intro.jsp"%>  -->
	<div class="container">
		<!-- .modal -->
		<div id="areaModal" class="modal fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<!-- .modal-dialog -->
			<div class="modal-dialog"></div>
		</div>
		<div class="content" id="content">
			<!-- 베스트 게시판 시작 -->
			<div class="head">
				<h1 style="color: #141e3c">
					<i>베스트 관광지</i>
				</h1>
			</div>

			<hr style="border: 1px solid #141e3c; margin: 15px 0px;">

			<div class="area_row">
				<!-- API 한 줄에 4개씩 출력 그리드 -->
			</div>

			<div style="text-align: center;">
				<a href="${pageContext.request.contextPath }/bbs/bbs_area_list.do"
					class="btn" style="color: white; background-color: #ff8000;">
					게시물 더보기 </a>
			</div>
		</div>
		<!-- //베스트 게시판 끝 -->
		<div style="height: 10px;"></div>
		<div class="content" id="content">
			<!-- 스토리 게시판 시작 -->
			<div class="head">
				<h1 style="color: #141e3c">
					<i>추천 스토리</i>
				</h1>
			</div>

			<hr style="border: 1px solid #141e3c; margin: 15px 0px;">
			<!-- story 한줄에 4개씩 출력 그리드 -->
			<div class="story_row">
				<c:choose>
					<c:when test="${fn:length(scheduleList) >= 1}">
						<c:forEach var="schedule" items="${scheduleList}">
							<!-- 게시물 하나 시작 -->
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="thumbnail clearfix">
									<c:url var="readUrl" value="/schedule/schedule_view.do">
										<c:param name="category" value="${schedule.category}" />
										<c:param name="schedule" value="${schedule.idschedule}" />
										<c:param name="idboard" value="${schedule.idboard}" />
									</c:url>
									<a href="${readUrl }"> <c:choose>
											<c:when test="${schedule.imagePath != null }">
												<c:url var="downloadUrl" value="/download.do">
													<c:param name="file" value="${schedule.imagePath }"></c:param>
												</c:url>
												<img src="${downloadUrl }" class="img-responsive"
													onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'" />
											</c:when>
											<c:otherwise>
												<img
													src="${pageContext.request.contextPath }/assets/img/no_image.jpg"
													class="img-responsive" />
											</c:otherwise>
										</c:choose>
									</a>

									<div class="caption">
										<h3>${schedule.subject}</h3>
										<p>
											작성자 : ${schedule.writer_nickname } <br> 작성일 :
											<fmt:parseDate var="parsedDate" value="${schedule.reg_date }"
												pattern="yyyy-MM-dd" />
											<fmt:formatDate var="newFormattedDateString"
												value="${parsedDate}" pattern="yyyy-MM-dd" />${newFormattedDateString}
										</p>
										<p class="hitview">
											<span>추천: ${schedule.love} / 조회: ${schedule.hit }</span>
										</p>
									</div>
								</div>
							</div>
							<!-- 게시물 하나 끝 -->
							<c:set var="maxPageNo" value="${maxPageNo-1}" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<table class="table table-hover">
							<tbody>
								<tr>
									<td colspan="5" class="text-center" style="line-height: 100px">조회된
										글이 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-sm-12 col-md-12" style="text-align: center;">
				<a
					href="${pageContext.request.contextPath }/schedule/schedule_list.do?category=story"
					class="btn btn-defatul"
					style="color: white; background-color: #ff8000;"> 게시물 더보기 </a>
			</div>
			<!--스토리 게시판 끝 -->
			<!-- 통계 작성할 공간 -->
			<div class="col-sm-12">
				<div class="head">
					<h1 style="color: #141e3c">
						<i>사이트 통계</i>
					</h1>
				</div>
				<hr style="border: 1px solid #141e3c; margin: 15px 0px;">
				<ul class="thumbnail2">
					<li><a href="#" onclick="setImage(0); return false;"><img
							src="${pageContext.request.contextPath }/assets/img/area_avg.png" /></a></li>
					<li><a href="#" onclick="setImage(1); return false;"><img
							src="${pageContext.request.contextPath }/assets/img/start_avg.png" /></a></li>
					<li><a href="#" onclick="setImage(2); return false;"><img
							src="${pageContext.request.contextPath }/assets/img/theme_avg.png" /></a></li>
				</ul>
				<div class="view clearfix">
					<img
						src="${pageContext.request.contextPath }/assets/img/area_avg.png"
						id="target" />
				</div>
			</div>
		</div>
		<!-- // 통계 작성 끝 -->
	</div>
	<!-- //본문 끝 -->

	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

	<!-- API -->
	<script id="thumbnail_item_tmpl" type="text/x-handlebars-template">
      {{#result.response.body.items.item}}
     	<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
         <div class="thumbnail">
            <input name="contentid" type="hidden" value="{{contentid}}"/>
            <a data-toggle="modal" href="#areaModal" class="modal_src"><img src="{{firstimage}}" style="width:100%; height:210px;"></a>
            <div class="caption">
               <h3>{{title}}</h3>
               <p class="caption-content">{{addr1}}</p>
               <p class="area_view">조회수 : {{readcount}}</p>
            </div>
         </div>
      	</div>
      {{/result.response.body.items.item}}
   </script>

	<!-- API Modal -->
	<script id="modal_item_tmpl" type="text/x-handlebars-template">
      <div class="modal-content">
         <div class="modal-header">
            <h4 class="modal-title" id="areaModalLabel">관광지 세부정보</h4>
         </div>
         <div class="modal-body">
            <img src="{{result.response.body.items.item.firstimage}}">
            
            <div class="body-title">
               <h4>{{result.response.body.items.item.title}}</h4>
            
            </div>
            <div class=body-view>
               <p>주소(add): {{{result.response.body.items.item.addr1}}}</p>
               <p>홈페이지(homepage): {{{result.response.body.items.item.homepage}}}</p>
               <p>전화번호(tel): {{{result.response.body.items.item.tel}}}</p>
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
		/** 링크에 의해서 호출될 함수 */
		function setImage(index) {
			// 이미지의 경로를 담고 있는 배열
			var image_list = [
					'${pageContext.request.contextPath}/assets/img/area_avg.png',
					'${pageContext.request.contextPath }/assets/img/start_avg.png',
					'${pageContext.request.contextPath }/assets/img/theme_avg.png' ];
 			// 이미지 요소의 객체화
			var image = document.getElementById("target");
 			// 객체의 src속성에 배열의 값들 중에서 파라미터로 전달된 위치의 값을 설정한다.
			image.src = image_list[index];
		}
	</script>

</body>

</html>