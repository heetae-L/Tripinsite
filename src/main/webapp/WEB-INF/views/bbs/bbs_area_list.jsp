<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/area.css">
	<!-- ajax 통신 스크립트 -->
	<script type="text/javascript">
		var url = "${pageContext.request.contextPath}";
	</script>
	<script src="${pageContext.request.contextPath}/assets/js/bbs_area_list_js.js" type="text/javascript"></script>
<style type="text/css">
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

	<div class="container">
		
		<!-- .modal -->
		<div id="areaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<!-- .modal-dialog -->
			<div class="modal-dialog">
			</div>
		</div>
		<!-- /.modal -->
		
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left"><i>관광지 게시판</i></h1>
				<!-- 검색폼 + 추가버튼 -->
				<div style='margin-top: 30px;' class="pull-right">
					<form style="width: 300px;" method="get" id="keyword"
						action="${pageContext.request.contextPath }/bbs/bbs_area_key.do">
						<div class="input-group">
							<input type="text" name='keyword' class="form-control"
								placeholder="관광지 검색" /> <span class="input-group-btn">
								<button class="btn" type="submit"
									style="background-color: #ff8000; color: #fff;">
									<i class='glyphicon glyphicon-search'></i>
								</button>
							</span>
						</div>
					</form>
				</div>
				<!-- //검색폼 + 추가버튼 -->
			</div>
			<div class="form-inline">
				<!-- 1차 카테고리 -->
				<div class="form-group">
					<select name="areacode" id="category1" class="form-control">
						<option value="">---지역 선택---</option>
						<option value="1">서울</option>
						<option value="2">인천</option>
						<option value="3">대전</option>
						<option value="4">대구</option>
						<option value="5">광주</option>
						<option value="6">부산</option>
						<option value="7">울산</option>
						<option value="8">세종특별자치시</option>
						<option value="31">경기도</option>
						<option value="32">강원도</option>
						<option value="33">충청북도</option>
						<option value="34">충청남도</option>
						<option value="35">경상북도</option>
						<option value="36">경상남도</option>
						<option value="37">전라북도</option>
						<option value="38">전라남도</option>
						<option value="39">제주도</option>
					</select>
				</div>
				<!-- 2차 카테고리 -->
				<div class="form-group">
					<select name="sigungucode" id="category2" class="form-control"
						style="min-width: 120px;">
						<option value="">--시,군,구 선택--</option>
					</select>
				</div>
			</div>

			<br />

			<!-- 한 줄에 4개 씩 배치되도록 그리드 구성 -->
			<div class="row">
				
			</div>
			<nav class="text-center">
				<ul class="pagination">
					<li><button type="button" class="btn btn-default hidden" id="prev">&laquo;</button></li>
					<li><button type="button" class="btn btn-default hidden" id="next">&raquo;</button></li>
				</ul>
			</nav>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

	<!-- template -->
	<script id="category_item_tmpl" type="text/x-handlebars-template">
		{{#records}}
		<option value="{{code}}">{{name}}</option>
		{{/records}}
	</script>

	<script id="thumbnail_item_tmpl" type="text/x-handlebars-template">
		<input type="hidden" id="page" value="{{page}}"/>
		{{#result.response.body.items.item}}
		<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
			<div class="thumbnail">
				<input name="contentid" type="hidden" value="{{contentid}}"/>
				<a data-toggle="modal" href="#areaModal" class="modal_src"><img src="{{firstimage}}" style="width:100%; height:210px;" onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'"></a>
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
</body>
</html>