<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>


<form id="main_search" action="${pageContext.request.contextPath }/bss/bbs_search_keyword.do" method="get">
	<div class="search__container dropdown">
		<!-- Search -->
		<input type="search" class="search__input dropdown-toggle" placeholder="Search" data-toggle="dropdown" name="search_keyword" autocomplete=off>
		<button class="btn btn-outline-warning" type="submit" style="background-color: #ff8000; color: white;">Search</button>
		
		<!-- DropDown -->
		<ul class="dropdown-menu" role="menu" aria-labelledby="menu1" style="width: 500px;">
			<li role="presentation" class="presentation">
				<a href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=1" role="menuitem" tabindex="-1">식도락</a>
			</li>
			<li role="presentation" class="presentation">
				<a href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=2" role="menuitem" tabindex="-1">쇼핑</a>
			</li>
			<li role="presentation" class="presentation">
				<a href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=3" role="menuitem" tabindex="-1">명소</a>
			</li>
			<li role="presentation" class="presentation">
				<a href="${pageContext.request.contextPath }/bss/bbs_search_theme.do?theme=4" role="menuitem" tabindex="-1">휴양</a>
			</li>
		</ul>
	</div>
</form>