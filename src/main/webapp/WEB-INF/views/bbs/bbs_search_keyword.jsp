<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/area.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<!--본문 시작 -->
	<div class="container">
		<!-- .modal -->
		<div id="areaModal" class="modal fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<!-- .modal-dialog -->
			<div class="modal-dialog"></div>
		</div>
		<div class="content">
			<h1><i>${keyword}&nbsp;검색결과</i></h1>
			<br />
			<!-- 베스트 게시판 시작 -->
			<div class="page-header">
				<h3>관광지</h3>
			</div>
			<div class="col-md-12">
				<div class="area_row">
					<table class="table table-hover">
						<tbody>
							<tr>
								<td colspan="5" class="text-center"
									style="line-height: 150px; border-top-width: 0px;">조회된 글이
									없습니다.</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<a href="${pageContext.request.contextPath }/bbs/bbs_area_list.do"
					class="btn btn-default"
					style="color: white; background-color: #ff8000;"> 검색 결과 더보기</a>
			</div>
			<!-- //베스트 게시판 끝 -->

			<br />

			<div class="page-header">
				<h3>스토리 / 일정</h3>
			</div>

			<br /> <br />
			<div class="row">
				<c:choose>
					<c:when test="${fn:length(scheduleList) >= 1 }">
						<c:forEach var="schedule" items="${scheduleList }">
							<div class="col-sm-3 col-md-3">
								<div class="thumbnail">
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
										<p>작성자 : ${schedule.writer_nickname } / 작성일 :
											${schedule.edit_date }</p>
										<p class="hitview">
											<span>추천: ${schedule.love} / 조회: ${schedule.hit }</span>
										</p>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<table class="table table-hover">
							<tbody>
								<tr>
									<td colspan="5" class="text-center"
										style="line-height: 100px; border-top-width: 0px;">조회된 글이
										없습니다.</td>
								</tr>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
			<!--스토리 게시판 끝 -->
			<div style="text-align: center;">
				<a
					href="${pageContext.request.contextPath }/schedule/schedule_list.do?category=story"
					class="btn btn-default"
					style="color: white; background-color: #ff8000;"> 게시물 더보기</a>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

	<script id="thumbnail_item_tmpl" type="text/x-handlebars-template">
		{{#result.response.body.items.item}}
		<div class="col-sm-3 col-md-3">
			<div class="thumbnail">
				<input name="contentid" type="hidden" value="{{contentid}}"/>
				<a data-toggle="modal" href="#areaModal" class="modal_src"><img src="{{firstimage2}}" style="width:310px; height:230px;" onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'"></a>
				<div class="caption">
					<h3>{{title}}</h3>
					<p class="caption-content">주소 : {{addr1}}</p>
					<p class="area_view">조회수 : {{readcount}}</p>
				</div>
			</div>
		</div>
		{{/result.response.body.items.item}}
	</script>

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
		$(function() {
			$.get("${pageContext.request.contextPath}/bbs/bbs_area_key.do", {
				keyword : "${keyword}"
			}, function(data) {
				if (data.result.response.body.totalCount > 0) {
					$(".area_row").empty();
					var template = Handlebars.compile($("#thumbnail_item_tmpl")
							.html());
					var html = template(data);
					$(".area_row").append(html);
				}
			}); // end get

			/** 동적 modal 제어를 위해 document로 제어 */
			$(document).on("click",".modal_src",function(e) {
				e.preventDefault();
				$.get(
					"${pageContext.request.contextPath }/bbs/bbs_area_modal_item.do",
					{contentId : $(this).prev().val()},
					function(data) {
						$(".modal-dialog").empty();
						var template = Handlebars.compile($("#modal_item_tmpl").html());
						var html = template(data);
						$(".modal-dialog").append(
								html);
					});
			}); // 동적 모달 제어
		});
	</script>
</body>
</html>