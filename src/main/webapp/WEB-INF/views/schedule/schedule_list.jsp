<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<style type="text/css">
.liststand {
	background-color: #ff8000;
	color: #fff;
}

h3 {
	text-overflow: ellipsis;
	white-space: nowrap;
	word-wrap: normal;
	overflow: hidden;
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

	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left">
					<i>스토리 게시판</i>
				</h1>
				<!-- 검색폼 -->
				<div style='margin-top: 30px;' class="pull-right">
					<form method="get"
						action="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=${theme}"
						style="width: 300px;">
						<div class="input-group">
							<input type="text" name='keyword' class="form-control"
								placeholder="게시글 검색" /> <span class="input-group-btn">
								<button class="btn" type="submit"
									style="background-color: #ff8000; color: white;">
									<i class='glyphicon glyphicon-search'></i>
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<!-- // header 종료 -->

			<div class="pull-right">
				<a class="btn btn-default liststand" type="button" id="btnview"
					href="${pageContext.request.contextPath}/schedule/schedule_list.do?category=story">최신순</a>
				<a class="btn btn-default" type="button" id="btnlike"
					href="${pageContext.request.contextPath}/schedule/schedule_list.do?category=story&sort=true">추천수</a>
			</div>

			<!-- 한 줄에 4개 씩 배치되도록 그리드 구성 -->
			<div class="row">
				<br /> <br />
				<c:choose>
					<c:when test="${fn:length(scheduleList) >= 1}">
						<c:forEach var="schedule" items="${scheduleList}">
							<!-- 게시물 하나 시작 -->
							<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
								<div class="thumbnail clearfix">
									<c:url var="readUrl" value="/schedule/schedule_view.do">
										<c:param name="category" value="${schedule.category}" />
										<c:param name="schedule" value="${schedule.idschedule}" />
										<c:param name="idboard" value="${schedule.idboard}" />
									</c:url>
									<a href="${readUrl }" style="width: 225px; height: 148px;">
										<c:choose>
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
			<!-- 페이징 버튼 -->
			<div class="clearfix">
				<nav class="text-center">
					<ul class="pagination">
						<!-- 이전 그룹으로 이동 -->
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
								<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
								<c:url var="prevUrl" value="/schedule/schedule_list.do">
									<c:param name="category" value="${category}"></c:param>
									<c:if test="${sort==true}">
										<c:param name="sort" value="true"></c:param>
									</c:if>
									<c:param name="page" value="${pageHelper.prevPage}"></c:param>
								</c:url>

								<li><a href="${prevUrl}"
									style='color: #141e3c; border-radius: 34px; border: none; margin: 3px;'>&laquo;</a></li>
							</c:when>

							<c:otherwise>
								<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
								<li class='disabled'><a href="#"
									style='color: #141e3c; border-radius: 34px; border: none; margin: 3px;'>&laquo;</a></li>
							</c:otherwise>
						</c:choose>

						<!-- 페이지 번호 -->
						<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
						<c:forEach var="i" begin="${pageHelper.startPage}"
							end="${pageHelper.endPage}" step="1">

							<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
							<c:url var="pageUrl" value="/schedule/schedule_list.do">
								<c:param name="category" value="${category}"></c:param>
								<c:if test="${sort==true}">
									<c:param name="sort" value="true"></c:param>
								</c:if>
								<c:param name="page" value="${i}"></c:param>
							</c:url>

							<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
							<c:choose>
								<c:when test="${pageHelper.page == i}">
									<li class='active'><a href="#"
										style='background-color: #ff8000; border-radius: 34px; border: none; margin: 3px;'>${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}"
										style='color: #141e3c; border-radius: 34px; border: none; margin: 3px;'>${i}</a></li>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<!-- 다음 그룹으로 이동 -->
						<c:choose>
							<c:when test="${pageHelper.nextPage > 0}">
								<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
								<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 "nextUrl"에 저장 -->
								<c:url var="nextUrl" value="/schedule/schedule_list.do">
									<c:param name="category" value="${category}"></c:param>
									<c:if test="${sort==true}">
										<c:param name="sort" value="true"></c:param>
									</c:if>
									<c:param name="page" value="${pageHelper.nextPage}"></c:param>
								</c:url>

								<li><a href="${nextUrl}"
									style='color: #141e3c; border-radius: 34px; border: none; margin: 3px;'>&raquo;</a></li>
							</c:when>

							<c:otherwise>
								<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
								<li class='disabled'><a href="#"
									style='color: #141e3c; border-radius: 34px; border: none; margin: 3px;'>&raquo;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div>
			<!-- //페이징 버튼 -->
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

	<script type="text/javascript">
		$(function() {
			var sort=${sort};
			if (sort == true) {
				$('#btnlike').addClass('liststand');
				$('#btnview').removeClass('liststand');
			}
		});
	</script>
</body>
</html>