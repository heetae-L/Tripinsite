$(function() {
	/** 2차 카테고리 정보를 받아오는 json */
	$("#category1").change(function(e) {
		e.preventDefault();
	
		$("#category2").empty();
	
		var value = $(this).find("option:selected").val();
		/** get 방식의 ajax 요청 */
		$.get(url + '/assets/json/areacode.json',function(data) {
			var obj;
			
			for (var i = 0; i < data.item.length; i++) {
					obj = data.item[i].code;
				if (value == obj) {
					var template = Handlebars.compile($("#category_item_tmpl").html());
					var html = template(data.item[i]);
					$("#category2").append(html);
				}
			}
		}); // $.get end
	}); // chage category1 end
	
	/** 동적 html 제어를 위해 document로 제어 */
	$(document).on("change","#category2",function(){
		/** category1,category2의 값을 받아 api 요청 */
		$.get(url + "/bbs/bbs_area_item.do", { 
			areaCode : $("#category1").find("option:selected").val(), 
			sigunguCode : $("#category2").find("option:selected").val()},
			function(data){
				$(".row").empty();
			  
				var template = Handlebars.compile($("#thumbnail_item_tmpl").html());
				
				var html = template(data);
				$(".row").append(html);
			  
				if(parseInt(data.totalCount) > (parseInt(data.page) * 12)){
					$("#next").removeClass("hidden");
				}
			});
	}); // end $.get
	/** 페이징 처리 시작 */	
	$("#next").click(function(){
		$.get(url + "/bbs/bbs_area_item.do",
				{ 
			areaCode : $("#category1").find("option:selected").val(), 
		  	  sigunguCode : $("#category2").find("option:selected").val(),
		  	  page : $("#page").val(),
		  	  action : "next"},
		  	  function(data){
		  		$(".row").empty();
		  		var template = Handlebars.compile($("#thumbnail_item_tmpl").html());
		  		
		  		var html = template(data);
				$(".row").append(html);
				
				$("#prev").removeClass("hidden");
				
				if(parseInt(data.totalCount) <= (parseInt(data.page) * 12)){
					$("#next").addClass("hidden");
				}
		});
	});
		
	$("#prev").click(function(){
		$.get(url + "/bbs/bbs_area_item.do",
				{ areaCode : $("#category1").find("option:selected").val(), 
			  	  sigunguCode : $("#category2").find("option:selected").val(),
			  	  page : $("#page").val(),
			  	  action : "prev"},
			  	  function(data){
			  		$(".row").empty();
			  		var template = Handlebars.compile($("#thumbnail_item_tmpl").html());
			  		
			  		var html = template(data);
					$(".row").append(html);
					
					$("#next").removeClass("hidden");
					
					if(parseInt(data.page) < 2){
						$("#prev").addClass("hidden");
					}
		});
	});
	/** 페이징 처리 끝 */
		
	$("#keyword").ajaxForm(function(data){
		$(".row").empty();
		
		var template = Handlebars.compile($("#thumbnail_item_tmpl").html());
		
		var html = template(data);
		$(".row").append(html);
	});
	
	/** 동적 modal 제어를 위해 document로 제어 */
	$(document).on("click",".modal_src",function(e) {
		e.preventDefault();
		$.get(url + "/bbs/bbs_area_modal_item.do",
				{ contentId : $(this).prev().val()},
				function(data){
			$(".modal-dialog").empty();
			
			 var template = Handlebars.compile($("#modal_item_tmpl").html());
			  
			 var html = template(data);
			 $(".modal-dialog").append(html);
		});
	}); // 동적 모달 제어
}); // end function