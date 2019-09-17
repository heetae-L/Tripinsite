<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<div class="sideintro">
	<div class="intro col-xs-10">
		<p>학생용 프로젝트 사이트입니다.</p>
	</div>
	<div class="intro_check col-xs-2">
		<span class="glyphicon glyphicon-chevron-right" id="check"></span>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('.sideintro').hover(function() {
			$("#check").addClass('glyphicon-chevron-left');
			$("#check").removeClass('glyphicon-chevron-right');
			$('.sideintro').animate({left:-10}, 1000);
		}, function() {
			$("#check").removeClass('glyphicon-chevron-left');
			$("#check").addClass('glyphicon-chevron-right');
			$('.sideintro').animate({left:-240},1000);
		});
	});
</script>