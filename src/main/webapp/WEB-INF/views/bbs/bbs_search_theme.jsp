<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<style type="text/css">
.liststand {
	background-color: #ff8000;
	color: #fff;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<div class="container">
		<div class="content">
			<c:if test="${keyword == null }">
				<h1><i>${thema}&nbsp;검색결과</i></h1>
			</c:if>
			<c:if test="${keyword != null }">
				<h1><i>${keyword}&nbsp;검색결과</i></h1>
			</c:if>
			<div class="head">
				<h3>스토리 / 일정</h3>
			</div>
			<hr style="border:1px solid #141e3c; margin:15px 0px;" />
			<div class="pull-right">
				<a class="btn btn-default liststand" type="button" id="btnview"
					href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=${theme}&keyword=${keyword}">최신순</a>
				<a class="btn btn-default" type="button" id="btnlike"
					href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=${theme}&sort=true&keyword=${keyword}">추천수</a>
			</div>
			<br /> <br />
			<div class="row">
				<c:choose>
					<c:when test="${fn:length(scheduleList) >= 1}">
						<c:forEach var="schedule" items="${scheduleList}">
							<!-- 게시물 하나 시작 -->
							<div class="col-sm-3 col-md-3">
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
											${schedule.reg_date }
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
	<script type="text/javascript">
		$(function() {
			var sort = ${sort};
			if (sort == true) {
				$('#btnlike').addClass('liststand');
				$('#btnview').removeClass('liststand');
			}

		});
	</script>
</body>
</html>