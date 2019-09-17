<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/admincss.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<div class="container">
		<!-- 머리판부분 -->
		<div class="header col-xs-12 ">
			<h1>관리자 게시판</h1>
			<div class="pull-right" id="search"
				style="position: absolute; right: 10px; bottom: 10px;">
				<form method="get"
					action="${pageContext.request.contextPath}/admin/admin_board.do"
					style="width: 250px;">
					<div class="input-group">
						<span class="input-group-btn">
							<button class="btn btn-button" type="submit"
								style="background-color: #ff8800;">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</span> <input type="text" name="keyword" class="form-control"
							placeholder="게시글/작성자 검색" value="${keyword}" />
					</div>
				</form>
			</div>
		</div>
		<!--//머리판 부분 끝-->

		<!-- 관리선택부분 탭바 -->
		<ul class="nav nav-tabs">
			<li class="active"><a href="#">게시판 관리</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/admin_member.do">회원
					관리</a></li>
		</ul>
		<!-- //관리 선택부분 탭바 끝 -->

		<!-- 관리자게시판 내용-->
		<div class="body col-xs-12">

			<table class="table table-striped">
				<thead>
					<tr>
						<th>글 번호</th>
						<th>카테고리</th>
						<th>글 제목</th>
						<th>작성자</th>
						<th>작성일자</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(boardList)>0 }">
							<c:forEach var="board" items="${boardList}">
								<tr>
									<td>${board.idboard}</td>
									<c:if test="${board.is_notice eq '1' }">
										<td>공지</td>
										<td><a
										href="${pageContext.request.contextPath}/bbs/bbs_notice_view.do?category=${board.category}&idboard=${board.idboard}">
										${board.subject}</a></td>
									</c:if>
									<c:if test="${board.is_notice eq '0' }">
										<td>${board.category}</td>
										<td><a
										href="${pageContext.request.contextPath}/bbs/bbs_free_view.do?category=${board.category}&idboard=${board.idboard}">
										${board.subject}</a></td>
									</c:if>
									<td>${board.writer_nickname}</td>
									<td>${board.reg_date}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" class="text-center ">조회된 게시글이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- //관리자게시판 내용 끝-->

		<!-- 작성, 삭제 버튼-->
		<div class="btn-toolbar pull-right" id="bot-btn">
			<a href="${pageContext.request.contextPath}/admin/admin_write.do"><button
					class="btn btn-success">공지사항 작성</button></a> 
		</div>
		<!-- // 작성, 삭제 버튼 끝-->
	</div>


	<!-- 페이지 번호 시작 -->
	<nav class="text-center">
		<ul class="pagination">
			<!-- 이전 그룹으로 이동 -->
			<c:choose>
				<c:when test="${pageHelper.prevPage > 0}">
					<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
					<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
					<c:url var="prevUrl" value="/admin/admin_board.do">
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
				<c:url var="pageUrl" value="/admin/admin_board.do">
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
					<c:url var="nextUrl" value="/admin/admin_board.do">
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
	<!-- // 페이지 번호 끝 -->
</body>
</html>

