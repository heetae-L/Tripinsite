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
	href="${pageContext.request.contextPath }/assets/css/free.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/assets/css/carousel.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>

	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h2>${readBoard.subject}</h2>
				<h4 class="pull-left">Trip In Site</h4>
				<h4 class="pull-right">조회수 : ${readBoard.hit} / 작성일 :
					${readBoard.reg_date}</h4>
			</div>
			<h4>파일 목록</h4>
			<ul>
				<c:forEach var="item" items="${fileList}" varStatus="status">
					<li><c:url value="/download.do" var="download_url">
							<c:param name="file" value="${item.file_dir}/${item.file_name}"></c:param>
						</c:url> - 다운로드 : <a href="${download_url}">${item.origin_name}</a></li>
				</c:forEach>
			</ul>

			<!-- 업로드 된 파일 목록 출력 처리 -->
			<c:if test="${fn:length(fileList) > 0}">
				<!-- 캐러셀 영역 구성 -->
				<div id="carouse-generic" class="carousel slide"
					data-ride="carousel" data-interval="false">
					<c:choose>
						<c:when test="${fn:length(fileList) > 0}">
							<div id="carousel" class="carousel slide" data-ride="carousel">
								<!-- Indicators -->
								<ol class="carousel-indicators">
									<c:forEach var="fileItem" items="${fileList }"
										varStatus="status">
										<c:if
											test="${fn:indexOf(fileItem.content_type,'image/jpeg') > -1}">
											<c:set var="cls" value="" />
											<c:if test="${status.index == 0 }">
												<c:set var="cls" value="active" />
											</c:if>
											<li data-target="#carousel" data-slide-to="${status.index }"
												class="${cls }"></li>
										</c:if>
									</c:forEach>
								</ol>

								<!-- Wrapper for slides -->
								<div class="carousel-inner" role="listbox">
									<c:forEach var="item" items="${fileList}" varStatus="status">
										<c:if
											test="${fn:indexOf(item.content_type,'image/jpeg') > -1}">
											<c:set var="cls" value="" />
											<c:if test="${status.index == 0 }">
												<c:set var="cls" value="active" />
											</c:if>
											<c:url var="download_url" value="../download.do">
												<c:param name="file"
													value="${item.file_dir}/${item.file_name}" />
											</c:url>
											<div class="item ${cls}" id="notice-inner">
												<img src="${download_url}"
													onerror="this.src ='${pageContext.request.contextPath }/assets/img/no_image.jpg'"
													class="img-responsive" id="notice-img">
											</div>
										</c:if>
									</c:forEach>
								</div>

								<!-- Controls -->
								<c:if test="${fn:length(fileList) > 1 }">
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
					</c:choose>
				</div>
			</c:if>

			<!-- 내용 -->
			<div style="white-space: pre;"><c:out value="${readBoard.content}" /></div>

			<!-- 버튼들 -->
			<div class="pull-right" style="margin-bottom: 10px;">
				<c:if test="${myBoard == true}">
					<a href='#edit' data-toggle="modal" class="btn"
						style="background-color: #ff8000; color: white;">수정하기 </a>
					<a class="btn btn-danger" type="button" href='#freedel'
						data-toggle="modal">삭제 </a>
				</c:if>
				<a class="btn btn-default" type="button"
					href="${pageContext.request.contextPath}/bbs/bbs_free_list.do?category=free">목록</a>
			</div>

			<!-- 다음글/이전글 -->
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="success" style="width: 100px">다음글</th>
						<td><c:choose>
								<c:when test="${nextBoard != null}">
									<c:url var="nextUrl" value="/bbs/bbs_notice_view.do">
										<c:param name="category" value="${category}" />
										<c:param name="idboard" value="${nextBoard.idboard}" />
									</c:url>
									<a href="${nextUrl}">${nextBoard.subject}</a>
								</c:when>
								<c:otherwise>
							다음글이 없습니다.
							</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th class="success" style="width: 100px">이전글</th>
						<td><c:choose>
								<c:when test="${prevBoard != null}">
									<c:url var="prevUrl" value="/bbs/bbs_notice_view.do">
										<c:param name="category" value="${category}" />
										<c:param name="idboard" value="${prevBoard.idboard}" />
									</c:url>
									<a href="${prevUrl}">${prevBoard.subject}</a>
								</c:when>
								<c:otherwise>
							이전글이 없습니다.
							</c:otherwise>
							</c:choose></td>
					</tr>
				</tbody>
			</table>

			<!-- 게시글 수정 모달 -->
			<div id="edit" class="modal fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<!-- .modal-dialog -->
				<div class="modal-dialog">
					<!-- .modal-content -->
					<div class="modal-content">
						<form method="post" name="edit_password" id="edit_password"
							action="${pageContext.request.contextPath}/admin/admin_notice_edit.do">
							<input type="hidden" name="category" value="${category}" /> <input
								type="hidden" name="idboard" value="${readBoard.idboard}" />
							<!-- 제목 -->
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">게시글 수정</h4>
							</div>
							<!-- 내용 -->
							<div class="modal-body">
								<p>공지를 수정하시겠습니까?</p>
							</div>
							<!-- 하단 -->
							<div class="modal-footer">
								<button type="submit" class="btn"
									style="background-color: #ff8000; color: white;">수정하기</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">취소</button>
							</div>
						</form>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>

			<!-- 게시글 삭제 모달 -->
			<div id="freedel" class="modal fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<!-- .modal-dialog -->
				<div class="modal-dialog">
					<!-- .modal-content -->
					<div class="modal-content">
						<form name="myform" method="post"
							action="${pageContext.request.contextPath}/bbs/bbs_free_delete.do">
							<!-- 카테고리와 게시글 번호 상태유지 -->
							<input type="hidden" name="category" value="${category}" /> <input
								type="hidden" name="idboard" value="${readBoard.idboard}" />
							<!-- 제목 -->
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">게시글 삭제</h4>
							</div>
							<!-- 내용 -->
							<div class="modal-body">
								<p>정말 이 공지를 삭제하시겠습니까?</p>
							</div>
							<!-- 하단 -->
							<div class="modal-footer">
								<button type="submit" class="btn"
									style="background-color: #ff8000; color: white;">삭제하기</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">취소</button>
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

</body>
</html>