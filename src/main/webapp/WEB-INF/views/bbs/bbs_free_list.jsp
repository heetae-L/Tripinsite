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
	href="${pageContext.request.contextPath }/assets/css/free.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left"><i>자유 게시판</i></h1>
				<!-- 검색폼 -->
				<div style='margin-top: 30px;' class="pull-right">
					<form method="get"
						action="${pageContext.request.contextPath}/bbs/bbs_free_list.do"
						style="width: 300px">
						<input type="hidden" name="category" value="free" />
						<div class="input-group">
							<input type="text" name="keyword" class="form-control"
								placeholder="제목/내용 검색" value="${keyword}" /> <span
								class="input-group-btn">
								<button class="btn" type="submit"
									style="background-color: #ff8000; color: white;" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>

			<!-- 게시글 목록 -->
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center" style="width: 100px">글번호</th>
						<th class="text-center">제목</th>
						<th class="text-center" style="width: 100px">조회수</th>
						<th class="text-center" style="width: 100px">작성자</th>
					</tr>
					<!-- 여기에 공지를 할까 -->
					<c:choose>
						<c:when test="${fn:length(noticeList) > 0}">
							<c:forEach var="board" items="${noticeList}">
								<tr>
									<td class="text-center">공지</td>
									<td class="text-center free-title"><c:url var="readUrl"
											value="/bbs/bbs_notice_view.do">
											<c:param name="category" value="${category}" />
											<c:param name="idboard" value="${board.idboard}" />
										</c:url> <a href="${readUrl}">${board.subject}</a></td>
									<td class="text-center">${board.hit}</td>
									<td class="text-center">Trip In Site</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" class="text-center" style="line-height: 100px">공지가
									없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<!--// ㅇㅇ -->
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(boardList) > 0}">
							<c:forEach var="board" items="${boardList}">
								<tr>
									<td class="text-center">${maxPageNo}</td>
									<td class="text-center free-title"><c:url var="readUrl"
											value="/bbs/bbs_free_view.do">
											<c:param name="category" value="${category}" />
											<c:param name="idboard" value="${board.idboard}" />
										</c:url> <a href="${readUrl}">${board.subject}</a></td>
									<td class="text-center">${board.hit}</td>
									<td class="text-center">${board.writer_nickname}</td>
								</tr>
								<c:set var="maxPageNo" value="${maxPageNo-1}" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" class="text-center" style="line-height: 100px">조회된
									글이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<!-- 글쓰기 -->
			<div class="pull-right">
				<a href="${pageContext.request.contextPath }/bbs/bbs_free_write.do"
					class="btn" style="background-color: #ff8000; color: white;">
					글쓰기 </a>
			</div>
			<!-- //글쓰기 -->

			<!-- 페이징 버튼 -->
			<div class="clearfix">
				<nav class="text-center">
					<ul class="pagination">
						<!-- 이전 그룹으로 이동 -->
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
								<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
								<c:url var="prevUrl" value="/bbs/bbs_free_list.do">
									<c:param name="category" value="${category}"></c:param>
									<c:param name="keyword" value="${keyword}"></c:param>
									<c:param name="page" value="${pageHelper.prevPage}"></c:param>
								</c:url>

								<li><a href="${prevUrl}" style='color:#141e3c; border-radius: 34px; border: none; margin:3px;'>&laquo;</a></li>
							</c:when>

							<c:otherwise>
								<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
								<li class='disabled'><a href="#" style='color:#141e3c; border-radius: 34px; border: none; margin:3px;'>&laquo;</a></li>
							</c:otherwise>
						</c:choose>

						<!-- 페이지 번호 -->
						<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
						<c:forEach var="i" begin="${pageHelper.startPage}"
							end="${pageHelper.endPage}" step="1">

							<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
							<c:url var="pageUrl" value="/bbs/bbs_free_list.do">
								<c:param name="category" value="${category}"></c:param>
								<c:param name="keyword" value="${keyword}"></c:param>
								<c:param name="page" value="${i}"></c:param>
							</c:url>

							<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
							<c:choose>
								<c:when test="${pageHelper.page == i}">
									<li class='active'><a href="#" style='background-color: #ff8000; border-radius: 34px; border: none; margin:3px;'>${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}" style='color:#141e3c; border-radius: 34px; border: none; margin:3px;'>${i}</a></li>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<!-- 다음 그룹으로 이동 -->
						<c:choose>
							<c:when test="${pageHelper.nextPage > 0}">
								<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
								<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 "nextUrl"에 저장 -->
								<c:url var="nextUrl" value="/bbs/bbs_free_list.do">
									<c:param name="category" value="${category}"></c:param>
									<c:param name="keyword" value="${keyword}"></c:param>
									<c:param name="page" value="${pageHelper.nextPage}"></c:param>
								</c:url>

								<li><a href="${nextUrl}" style='color:#141e3c; border-radius: 34px; border: none; margin:3px;'>&raquo;</a></li>
							</c:when>

							<c:otherwise>
								<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
								<li class='disabled'><a href="#" style='color:#141e3c; border-radius: 34px; border: none; margin:3px;'>&raquo;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div>
			<!-- //페이징 버튼 -->
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>