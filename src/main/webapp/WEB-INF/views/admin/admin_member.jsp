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
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/daumPostCode.js"></script>
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
					action="${pageContext.request.contextPath}/admin/admin_member.do"
					style="width: 250px;">
					<div class="input-group">
						<span class="input-group-btn">
							<button class="btn btn-button" type="submit"
								style="background-color: #ff8800;">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</span> <input type="text" name="keyword" class="form-control"
							placeholder="회원 검색" value="${keyword}" />
					</div>
				</form>
			</div>
		</div>
		<!-- //머리판부분 끝 -->

		<!-- 관리선택부분 탭바 -->
		<ul class="nav nav-tabs">
			<li><a
				href="${pageContext.request.contextPath}/admin/admin_board.do">게시판
					관리</a></li>
			<li class="active"><a href="#">회원 관리</a></li>
		</ul>
		<!-- //관리선택부분 탭바 끝 -->

		<!-- 회원관리 내용 -->
		<div class="body col-xs-12">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>회원 번호</th>
						<th>이메일</th>
						<th>닉네임</th>
						<th>성별</th>
						<th>연령대</th>
						<th>가입일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(memberList)>0 }">
							<c:forEach var="member" items="${memberList}">
								<tr>
									<td>${member.id}</td>
									<td>${member.email}</td>
									<td><a href="#" role="button" data-toggle="modal"
										data-target="#myModal" class="modal-btn" name="${member.id}">${member.nickname}</a></td>
									<td>${member.gender}</td>
									<td>${member.age}</td>
									<td>${member.reg_date}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" class="text-center ">조회된 회원이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
		</div>
		<!--//회원관리 내용 끝 -->
	</div>

	<!-- 페이지 번호 시작 -->
	<nav class="text-center">
		<ul class="pagination">
			<!-- 이전 그룹으로 이동 -->
			<c:choose>
				<c:when test="${pageHelper.prevPage > 0}">
					<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
					<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
					<c:url var="prevUrl" value="/admin/admin_member.do">
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
				<c:url var="pageUrl" value="/admin/admin_member.do">
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
					<c:url var="nextUrl" value="/admin/admin_member.do">
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



	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title">회원정보 수정</h4>
				</div>
				<div class="modal-body">

					<div class="form-group">
						<label for="email" class="control-label">이메일</label>
						<div>
							<p id="email"></p>
						</div>
					</div>
					<div class="form-group">

						<label for="nickname" class="control-label ">닉네임</label>
						<div>
							<input type="text" name="nickname" id="nickname"
								class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<label for="age" class="control-label">연령대</label>
						<div>
							<select id="age" name="age" class="form-control">
								<option value="">---- 연령대를 선택하세요----</option>
								<option value="1">10대</option>
								<option value="2">20대</option>
								<option value="3">30대</option>
								<option value="4">40대</option>
								<option value="5">50대</option>
								<option value="6">60대</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for='gender' class="control-label">성별</label>
						<div>
							<label class="radio-inline"> <input type="radio"
								name="gender" id="gender1" value="M" />남자
							</label> <label class="radio-inline"> <input type="radio"
								name="gender" id="gender2" value="F" />여자
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="postcode" class="control-label">우편번호</label>
						<div>
							<input type="text" name="postcode" id="postcode"
								class="form-control pull-left"
								style='width: 120px; margin-right: 5px'>
							<!-- 클릭 시, Javascript 함수 호출 : openDaumPostcode() -->
							<input type="button" value="우편번호 찾기" class="btn"
								style="background-color: #303a50; color: #fff;"
								onclick='execDaumPostcode("postcode","addr1","addr2")' />
						</div>
					</div>

					<div class="form-group">
						<label for='addr1' class="control-label">주소</label>
						<div>
							<input type="text" name="addr1" id="addr1" class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<label for='addr2' class="control-label">상세주소</label>
						<div>
							<input type="text" name="addr2" id="addr2" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label>가입일</label>
						<p id="reg_date"></p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger id" id="btn_delete">삭제</button>
					<button type="button" class="btn btn-success id" id="btn_modify">수정</button>
					<button type="button" class="btn btn-default" id="cancle"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!--// Modal 끝 -->
	<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			$(".modal-btn").click(function() {
				var url = '${pageContext.request.contextPath}/admin/admin_member_view.do?id=';
				var id = $(this).attr("name");
				url += id;
				$.ajax({
					url : url,
					datatype : "json",
					method : "POST",
					success : function(data) {
						var item = data.item;
						var age = item.age;
						var gender = item.gender;
						$('#email').html(item.email);
						$('#nickname').attr("value",item.nickname);
						$('#age').val(age);
						if(gender=="M"){
							$('#gender1').attr("checked",true);
						} else if(gender=="F"){
							$('#gender2').attr("checked",true);
						}
						$('#postcode').attr("value",item.postcode);
						$('#addr1').attr("value",item.addr1);
						$('#addr2').attr("value",item.addr2);
						$('#reg_date').html(item.reg_date);
						$('.id').attr("name",item.id);
						}
				});
			});
			$('#cancle').click('hidden.bs.modal', function(e) {
				// 모달창 내의 내용을 강제로 지움.
				$(this).removeData('bs.modal');
			});
			$("#btn_delete").click(function() {
				if (confirm("삭제 하시겠습니까?")) {
					var id = $(this).attr("name");
					var url = '${pageContext.request.contextPath}/admin/admin_member_delete.do?id=';
					url += id;
					console.log(url);
					window.location.href = url;
				}
			});
			$("#btn_modify").click(function(){
				var url =  '${pageContext.request.contextPath}/admin/admin_member_update.do';
				$.post(url, {
					id : $(this).attr("name"),
					nickname : $('#nickname').val(),
					age : $('#age').val(),
					gender : $('input[name=gender]:checked').val(),
					postcode : $('#postcode').val(),
					addr1 : $('#addr1').val(),
					addr2 : $('#addr2').val(),
				}, function(data){
					if(data.rt == "ok"){
						alert("회원정보가 수정되었습니다.");
						window.location.href="${pageContext.request.contextPath}/admin/admin_member.do";
					} else if (data.rt =="error1"){
						alert("회원정보 수정에 실패했습니다.")
						window.location.href="${pageContext.request.contextPath}/admin/admin_member.do"
					}

					});
				});
			});
		});
	</script>
</body>
</html>

