<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
	<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
	<!-- 마이 페이지 -->
	<div class='container'>
		<div class="content">
			<div class='page-header'>
				<h1>
					<i>마이페이지</i>
				</h1>
			</div>
			<div class="form-group">
				<div class="col-md-offset-2 col-md-10">
					<span style="float: right;"> <a
						href="${pageContext.request.contextPath }/member/edit.do"
						class="btn" style="background-color: #ff8000; color: #fff;">수정</a>
						<a href="${pageContext.request.contextPath }/member/out.do"
						class="btn btn-danger">탈퇴</a>
					</span>
				</div>
			</div>
			<!-- 내가 기입한 정보창  시작 -->
			<div class="form-horizontal">
				<div class="form-group">
					<label for='email' class="col-md-2">이메일:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.email}</p>
					</div>
				</div>
				<div class="form-group">
					<label for='nickname' class="col-md-2">닉네임:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.nickname}</p>
					</div>
				</div>
				<div class="form-group">
					<label for='age' class="col-md-2">연령대:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.age}</p>
					</div>
				</div>
				<div class="form-group">
					<label for='gender' class="col-md-2">성별:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.gender}</p>
					</div>
				</div>

				<div class="form-group">
					<label for='postcode' class="col-md-2">우편번호:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.postcode}</p>
					</div>
				</div>
				<div class="form-group">
					<label for='addr1' class="col-md-2">주소:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.addr1}</p>
					</div>
				</div>
				<div class="form-group">
					<label for='addr2' class="col-md-2">상세주소:</label>
					<div class="col-md-10">
						<p class="form-control-static">${loginInfo.addr2}</p>
					</div>
				</div>
			</div>
			<!-- 내가 기입한 정보창 끝 -->
			<hr />

			<!-- 좋아요 목록 -->
			<div class='page-header'>
				<h3><i>내가 추천한 글</i></h3>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center" style="width: 100px">글번호</th>
						<th class="text-center">제목</th>
						<th class="text-center" style="width: 100px">작성자</th>
						<th class="text-center" style="width: 100px">수정일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(bookmark) > 0}">
							<c:forEach var="bookmark" items="${bookmark}" varStatus="status">
								<tr>
									<td class="text-center">${status.index + 1}</td>
									<td class="text-center title"><c:url var="readUrl"
											value="/schedule/schedule_view.do">
											<c:param name="category" value="story" />
											<c:param name="idboard" value="${bookmark.idboard}" />
											<c:param name="schedule"
												value="${bookmark.schedule_idschedule}" />
										</c:url> <a href="${readUrl}">${bookmark.subject}</a></td>
									<td class="text-center">${bookmark.writer_nickname}</td>
									<td class="text-center">${bookmark.reg_date}</td>
								</tr>
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
			<!-- 좋아요 목록 끝 -->
		</div>
	</div>
	<!-- 마이페이지 끝 -->
	<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
</body>
</html>