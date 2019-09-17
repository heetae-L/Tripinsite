$(function() {
			/** api 요청 */
			$.get(url + "/home2.do", 
				function(data){
					$(".area_row").empty();
						  
					var template = Handlebars.compile($("#thumbnail_item_tmpl").html());
						  
					var html = template(data);
					$(".area_row").append(html);
						  
			}); // end $.get
			
			
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
