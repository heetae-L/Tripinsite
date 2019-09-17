<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta content='Content-type: text/html; charset=UTF-8' name='http-equiv'>
<meta content='IE=9,IE=10,IE=11,chrome=1' http-equiv='X-UA-Compatible'>
<meta content='width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<meta content='ko' name='locale'>
<meta content='index, follow' name='robots'>
<meta content='트립인사이트, 트립인사이트 소개, 트립인사이트 회사 소개, 회사 소개' name='keywords'>
<meta content='트립인사이트 회사소개 페이지 - 트립인사이트는 스마트한 국내 최고의 자유여행 플랫폼입니다.' name='description'>
<!-- 대표이미지아이콘
 <meta content='*.png' property='og:image'>
 -->
<!-- shortcut icon
<link rel="shortcut icon" type="image/x-icon" href="*.ico" />
-->
<title>트립인사이트 - 회사 소개</title>

<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<!-- introduction.css 참조 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/introduction.css" />
<!-- 플러그인 CSS 참조 -->
<link rel="stylesheet" href="//unpkg.com/aos@next/dist/aos.css" />
<!-- 플러그인 스크립트 참조 -->
<script src="//unpkg.com/aos@next/dist/aos.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
<%@ include file="/WEB-INF/views/inc/searchBar.jsp"%>
<div class="container">
	<!-- 첫번째 섹션 시작 -->
	<div id="box1">
		<!-- 애니메이션 효과  -->
		<h1 class="title" data-aos="zoom-in-up">
			We Help Individuals<br />Experience Korea
		</h1>
		<h2 data-aos="fade-up">트립인사이트는 스마트한 국내 최고의 자유여행 플랫폼입니다.</h2>
		<br/>
		<img src="${pageContext.request.contextPath}/assets/img/introduction/img1.png" data-aos="fade-up">
	</div>
	<!--// 첫번째 섹션 끝-->

	<!-- 두번째 섹션 시작 -->
	<div id="box2">
		<!-- 애니메이션 효과  -->
		<div class="row multi-colums-row">
			<div class="col-md-6 col-sm-10 col-sm-offset-1 col-xs-12">
				<div class="label-text1" data-aos="fade-zoom-in">ABOUT TRIP IN SITE</div>
				<h2 class="title" data-aos="fade-right">여행자들이 본인의 취향에 맞춰 세상을 경험하도록 돕습니다.</h2>
				<div class="desc" data-aos="fade-up"><h3>트립인사이트는 자유롭게 여행일정을 짤 수 있는 국내 최고의 자유여행 플랫폼입니다. 2019년 플래너 서비스를 시작으로 빅데이터 기반 여행데이터 분석 서비스를 성공적으로 출시하며 빠르게 성장하고 있습니다. 트립인사이트는 여행자들이 개인의 취향에 맞는 여행을 할 수 있도록 도움을 주고, 여행자에게 더욱 가치 있는 경험을 제공하기 위해 끊임없이 노력하고 있습니다.</h3></div>
			</div>
				
			<div class="col-md-2 col-md-offset-2 col-sm-12 col-xs-12" data-aos="fade-left">
				<div class="row">
					<div class="col-md-12 col-sm-3 col-xs-8 col-xs-offset-2" data-aos="fade-up">
						<div class="imgbox" id="imgbox1"></div>
						<h4>여행 플래너</h4>
					</div>
					<div class="col-md-12 col-sm-3 col-xs-8 col-xs-offset-2" data-aos="fade-up">
						<div class="imgbox" id="imgbox2"></div>
						<h4>여행 빅데이터 분석</h4>
					</div>
					<div class="col-md-12 col-sm-3 col-xs-8 col-xs-offset-2" data-aos="fade-up">
						<div class="imgbox" id="imgbox3"></div>
						<h4>여행 계획 추천</h4>
					</div>
					<div class="col-md-12 col-sm-3 col-xs-8 col-xs-offset-2" data-aos="fade-up">
						<div class="imgbox" id="imgbox4"></div>
						<h4>인생샷 스팟 추천</h4>
					</div>
					<div class="col-md-12 col-sm-3 col-xs-8 col-xs-offset-2" data-aos="fade-up">
						<div class="imgbox" id="imgbox5"></div>
						<h4>여행 사진첩</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--// 두번째 섹션 끝 -->
</div>

<!-- 세번째 섹션 -->
<!-- 애니메이션 효과  -->
<div id="box3" data-aos="fade">
	<div class="container">
		<div class="row multi-colums-row">
			<div class="col-sm-10 col-sm-offset-1 col-xs-12">
				<div class="label-text1" data-aos="fade-zoom-in">NUMBERS</div>
				<h2 class="title" data-aos="fade-up">
					국내 최고의 자유여행 플랫폼으로<br />빠르게 성장 중입니다.
				</h2>
				<div class="desc" data-aos="fade-up">
					<h3>2019년에 출시된 트립인사이트는 누적 여행자 1만 명을 넘어서며 <br />빠르게 성장하고 있습니다. 이는 여행업계에서 가장 빠른 성장 속도입니다.</h3>
				</div>
			</div>
			<div class="col-sm-10 col-sm-offset-1 col-xs-12">
				<div class="row multi-colums-row">
					<div class="col-sm-4 col-sm-offset-1 col-xs-10 col-xs-offset-1" data-aos="fade-right">
						<div class="icon" id="icon1"></div>
						<h2 class="number">12,345</h2>
						<h3 class="title">누적 여행자 수</h3>
						<h4 class="text">지금까지 1만 명이 넘는 여행자가 트립인사이트를 통해 여행을 경험하였습니다.</h4>
					</div>
					<div class="col-sm-4 col-sm-offset-2 col-xs-10 col-xs-offset-1" data-aos="fade-left">
						<div class="icon" id="icon2"></div>
						<h2 class="number">6,789</h2>
						<h3 class="title">누적 리뷰 수</h3>
						<h4 class="text">6천 명이 넘는 여행자들이 트립인사이트에 여행에 대한 리뷰를 등록했습니다.</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 세번째 섹션 끝-->

<!-- 네번째 섹션 시작 -->
<!-- 애니메이션 효과  -->
<div id="box4" data-aos="fade">
	<div class="container row">
		<div class="contact col-md-7 col-md-offset-5 col-sm-8 col-sm-offset-4" data-aos="fade-up">
			<div class="label-text1" data-aos="fade-zoom-in">CONTACT</div>
			<h2 class="title">
				궁금한 점이 있으시면 <br />언제든 연락주세요.
			</h2>
			<hr />
			<ul class="contact">
				<li class="row">
					<h4 class="title">찾아오시는 길</h4>
					<p class="text">서울특별시 서초구 서초대로 77길 55, 에이프로스퀘어 3층<br/>(주)트립인사이트</p>
					<a class="btn btn-info" href="http://kko.to/Cxn0_u00M" target="_blank">카카오 지도로 보기</a>
					</li>
				<li class="row">
					<h4 class="title">사업 제휴 문의</h4>
					<p class="text">
						<a href="mailto:tripinsitekorea+partner@gmail.com">tripinsitekorea+partner@gmail.com</a>
					</p>
				</li>
				<li class="row">
					<h4 class="title">마케팅 제휴 문의</h4>
					<p class="text">
						<a href="mailto:tripinsitekorea+marketing@gmail.com">tripinsitekorea+marketing@gmail.com</a>
					</p>
				</li>
			</ul>
		</div>
	</div>
</div>
<!--// 네번째 섹션 끝 -->
<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
<script type="text/javascript">
	$(function(){
		AOS.init({
			easing: "linear",
     		duration: "1500"
		});
	});
</script>
</body>
</html>