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
	href="${pageContext.request.contextPath}/assets/css/free.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
	<div class="container">
		<div class="content">
			<div class="page-header clearfix">
				<h1 class="pull-left">공지사항 수정</h1>
			</div>

			<form class="form-horizontal" method="post"
				enctype="multipart/form-data"
				action="${pageContext.request.contextPath }/admin/admin_notice_edit_ok.do">

				<!-- 게시판 카테고리에 대한 상태유지 -->
				<input type="hidden" name="category" id="category" value="free" />
				<input type="hidden" name="idboard" id="idboard"
					value="${board.idboard}" />

				<!-- 제목 -->
				<div class="form-group">
					<label for="subject" class="col-sm-2 control-label">제목</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="subject"
							id="subject" value="${board.subject}" />
					</div>
				</div>
				<!-- 내용 -->
				<div class="form-group">
					<label for="content" class="col-sm-2 control-label">내용</label>
					<div class="col-sm-10">
						<textarea class="ckeditor" name="content" id="content"
							style="width: 100%;" rows="15">${board.content}</textarea>
					</div>
				</div>
				<!-- 파일업로드 -->
				<div class="form-group">
					<label for="file" class="col-sm-2 control-label">파일첨부</label>
					<div class="col-sm-10">
						<input type="file" class="form-control" id="file" name="file"
							multiple />
					</div>
				</div>

				<div class="form-group">
					<label for="file" class="col-sm-2 control-label">파일목록</label>
					<div class="col-sm-10">
						<table class="table table-striped">
							<c:choose>
								<c:when test="${fn:length(fileList)>0 }">
									<thead>
										<th>파일이름</th>
									</thead>
									<tbody>
										<c:forEach var="item" items="${fileList}" varStatus="status">
											<tr>
												<td><c:url value="/download.do" var="download_url">
														<c:param name="file"
															value="${item.file_dir}/${item.file_name}"></c:param>
													</c:url> - 파일 : <a href="${download_url}">${item.origin_name}</a>
													<button class="glyphicon glyphicon-trash" id="btn_delete" name="${item.idfile}"></button></td>
											</tr>

										</c:forEach>
									</tbody>
								</c:when>
							</c:choose>
						</table>

					</div>
				</div>


				<!-- 버튼들 -->
				<div class="form-group">
					<div class="pull-right">
						<button type="submit" class="btn"
							style="background-color: #ff8000; color: white;">수정완료</button>
						<button type="button" class="btn btn-default"
							onclick="history.back();">수정취소</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
	<script type="text/javascript">
		var str = $('#content').val();
		str = str.split('<br/>').join("\r\n");
		$('#textarea').val(str);
		$(function(){
			$(document).ready(function(){
				$("#btn_delete").click(function() {
					if (confirm("삭제 하시겠습니까?")) {
						var idfile = $(this).attr("name");
						var url = '${pageContext.request.contextPath}/board/board_notice_file_delete.do?idfile=';
						url += idfile;
						$.get(url);
					}
				});
			});
		});
		
	</script>
</body>
</html>