<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h2>${readBoard.subject}</h2>
				<h4 class="pull-left">${readBoard.writer_nickname}</h4>
				<h4 class="pull-right">조회수 : ${readBoard.hit} / 작성일 :
					${readBoard.reg_date}</h4>
			</div>
			<!-- 내용 -->
			<div class="sodyd">${readBoard.content}</div>

			<!-- 버튼들 -->
			<div class="pull-right" style="margin-bottom: 10px;">
				<a class="btn btn-default" type="button"
					href="${pageContext.request.contextPath }/bbs/bbs_free_list.do?category=free">목록</a>
				<a href='#edit' data-toggle="modal" class="btn"
					style="background-color: #ff8000; color: white;">수정하기 </a> <a
					class="btn btn-danger" type="button" href='#freedel'
					data-toggle="modal">삭제 </a>
			</div>

			<!-- 다음글/이전글 -->
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="pageInfo" style="width: 100px">다음글</th>
						<td class="pageInfo_td"><c:choose>
								<c:when test="${nextBoard != null}">
									<c:url var="nextUrl" value="/bbs/bbs_free_view.do">
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
						<th class="pageInfo" style="width: 100px">이전글</th>
						<td class="pageInfo_td"><c:choose>
								<c:when test="${prevBoard != null}">
									<c:url var="prevUrl" value="/bbs/bbs_free_view.do">
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

			<!-- 댓글 -->
			<form id="comment_form" method="post"
				action="${pageContext.request.contextPath}/bbs/comment_insert.do">
				<!-- 글 번호 상태 유지 -->
				<input type='hidden' name='idboard' value='${readBoard.idboard}' />
				<!-- 작성자,비밀번호,이메일은 로그인하지 않은 경우만 입력한다. -->
				<c:if test="${loginInfo == null}">
					<div class='form-group form-inline'>
						<!-- 이름, 비밀번호, 이메일 -->
						<div class="form-group">
							<input type="text" name="writer_name" class="form-control"
								placeholder='작성자' />
						</div>
						<div class="form-group">
							<input type="password" name="writer_pw" class="form-control"
								placeholder='비밀번호' />
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
			<div>
				<ul class="media-list" id="comment_list">

				</ul>
			</div>
			<!-- 댓글 항목에 대한 템플릿 참조 -->
			<script id="tmpl_comment_item" type="text/x-handlebars-template">
			<li class="media"
				style='border-top: 1px dotted #ccc; padding-top: 15px' id="comment_{{idcomment}}">
				<div class="media-body">
					<h4 class="media-heading clearfix">
						<!-- 작성자,작성일시 -->
						<div class='pull-left'>{{writer_nickname}}/ {{reg_date}}</div>

						<!-- 수정,삭제 버튼 -->
						<div class='pull-right'>
							<a href='${pageContext.request.contextPath}/bbs/comment_edit.do?
								idcomment={{idcomment}}' data-target='#comedit' data-toggle="modal"
								class='btn btn-warning btn-xs'> <i
								class='glyphicon glyphicon-edit'></i>
							</a> <a href='${pageContext.request.contextPath}/bbs/comment_delete.do?
								idcomment={{idcomment}}' data-target='#comdel' data-toggle="modal"
								class='btn btn-danger btn-xs'> <i
								class='glyphicon glyphicon-remove'></i>
							</a>
						</div>
					</h4>
					<!-- 내용 -->
					<p>{{{content}}}</p>
				</div>
			</li>
			</script>

			<!-- .댓글 수정 모달 -->
			<div id="comedit" class="modal fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						
					</div>
				</div>
			</div>
			
			<!-- 댓글 삭제 모달 -->
			<div id="comdel" class="modal fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						
					</div>
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
							action="${pageContext.request.contextPath }/bbs/bbs_free_edit_check.do">
							<input type="hidden" name="category" value="${category}" />
							<input type="hidden" name="idboard" value="${readBoard.idboard}" />
							<!-- 제목 -->
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">게시글 수정</h4>
							</div>
							<!-- 내용 -->
							<div class="modal-body">
							<c:choose>
								<c:when test="${myBoard == true}">
									<!-- 자신의 글에 대한 삭제 -->
									<p>게시글을 수정하시겠습니까?</p>
								</c:when>
								<c:otherwise>
									<!-- 자신의 글이 아닌 경우 -->
									<p>수정하시려면 비밀번호를 입력하세요</p>
									<div class="form-group">
										<input type="password" name="writer_pw" id="edit_writer_pw"
											class="form-control" />
									</div>
								</c:otherwise>
							</c:choose>
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
						<form name="myform" method="post" action="${pageContext.request.contextPath}/bbs/bbs_free_delete.do">
							<!-- 카테고리와 게시글 번호 상태유지 -->
							<input type="hidden" name="category" value="${category}" />
							<input type="hidden" name="idboard" value="${readBoard.idboard}" />
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
									</c:when>
									<c:otherwise>
										<p>삭제하시려면 글 작성시 설정한 비밀번호를 입력하세요</p>
										<div class="form-group">
											<label for='writer_pw'>비밀번호</label>
											<input type="password" name="writer_pw" id="freedel_writer_pw" 
												class="form-control"/>
										</div>
									</c:otherwise>
								</c:choose>
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
	<script type="text/javascript">
	$(function() {
		/** 페이지가 열리면서 동작하도록 이벤트 정의 없이 Ajax요청 */
		$.get("${pageContext.request.contextPath}/bbs/comment_list.do", {
			idboard: "${readBoard.idboard}"
		}, function(json) {
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			
			// 템플릿 HTML을 로드한다.
			var template = Handlebars.compile($("#tmpl_comment_item").html());
			
			// JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			for (var i=0; i<json.item.length; i++) {
				json.item[i].content 
					= json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 댓글 아이템 항목 하나를 템플릿과 결합한다.
				var html = template(json.item[i]);
				// 결합된 결과를 댓글 목록에 추가한다.
				$("#comment_list").append(html);
			}
		});
		
		/** 댓글 작성 폼의 submit 이벤트 Ajax 구현 */
		// <form>요소의 method, action속성과 <input>태그를
		// Ajax요청으로 자동 구성한다.
		$("#comment_form").ajaxForm(function(json) {
			// json은 API에서 표시하는 전체 데이터
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}

			// 줄 바꿈에 대한 처리
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			// --> JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
			json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
			
			// 템플릿 HTML을 로드한다.
			var template = Handlebars.compile($("#tmpl_comment_item").html());
			// JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
			var html = template(json.item);
			// 결합된 결과를 댓글 목록에 추가한다.
			$("#comment_list").append(html);
			// 폼 태그의 입력값을 초기화 하기 위해서 reset이벤트를 강제로 호출
			$("#comment_form").trigger('reset');
		});
		
		/** 모든 모달창의 캐시 방지 처리 */
		$('.modal').on('hidden.bs.modal', function (e) {
			// 모달창 내의 내용을 강제로 지움.
    		$(this).removeData('bs.modal');
		});
		
		/** 동적으로 로드된 폼 안에서의 submit 이벤트 */
		$(document).on('submit', "#comdel_password", function(e) {
			e.preventDefault();
			
			// AjaxForm 플러그인의 강제 호출
			$(this).ajaxSubmit(function(json) {
				if (json.rt != "OK") {
					alert(json.rt);
					return false;
				}
				
				alert("삭제되었습니다.");
				// modal 강제로 닫기
				$("#comdel").modal('hide');
				
				// JSON 결과에 포함된 댓글일련번호를 사용하여 삭제할 <li>의 id값을 찾는다.
				var idcomment = json.idcomment;
				$("#comment_" + idcomment).remove();
			});
		});
		
		/** 동적으로 로드된 폼 안에서의 submit 이벤트 */
		$(document).on('submit', "#comedit_password", function(e) {
			e.preventDefault();
			
			// AjaxForm 플러그인의 강제 호출
			$(this).ajaxSubmit(function(json) {
				if (json.rt != "OK") {
					alert(json.rt);
					return false;
				}
				
				// 줄 바꿈에 대한 처리
				// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
				// --> JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
				json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 템플릿 HTML을 로드한다.
				var template = Handlebars.compile($("#tmpl_comment_item").html());
				// JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
				var html = template(json.item);
				// 결합된 결과를 기존의 댓글 항목과 교체한다.
				$("#comment_" + json.item.idcomment).replaceWith(html);
				
				// 댓글 수정 모달 강제로 닫기
				$("#comedit").modal('hide');
			});
		});
	});
</script>
</body>
</html>