<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<title>Basic initialization</title>
<!-- 스케쥴러 플러그인-->
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_material.css" type="text/css">
<script src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/assets/plugins/codebase/locale_kr.js"></script>
<script src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_serialize.js"></script>
<script src="${pageContext.request.contextPath }/assets/plugins/codebase/dhtmlxscheduler_limit.js"></script>
	
	
<style type="text/css" >
		html, body{
		margin:0px;
		padding:0px;
		height:100%;
		overflow:hidden;
	}
	.event_work div,
	.dhx_cal_editor.event_work,
	.dhx_cal_event_line.event_work{
		background-color: #ff9633!important;
	}
	.dhx_cal_event_clear.event_work{
		color: #ff9633!important;
	}

	.event_meeting div,
	.dhx_cal_editor.event_meeting,
	.dhx_cal_event_line.event_meeting
	{
		background-color: #9575cd!important;
	}
	.dhx_cal_event_clear.event_meeting{
		color: #9575cd!important;
	}

	.event_movies div,
	.dhx_cal_editor.event_movies,
	.dhx_cal_event_line.event_movies{
		background-color: #ff5722!important;
	}
	.dhx_cal_event_clear.event_movies{
		color: #ff5722!important;
	}

	.event_rest div,
	.dhx_cal_editor.event_rest,
	.dhx_cal_event_line.event_rest{
		background-color: #0fc4a7!important;
	}
	.dhx_cal_event_clear.event_rest{
		color: #0fc4a7!important;
	}

	.add_event_button{
		position: absolute;
		width: 55px;
		height: 55px;
		background: #ff5722;
		border-radius: 50px;
		bottom: 40px;
		right: 55px;
		box-shadow: 0 2px 5px 0 rgba(0,0,0,0.3);
		z-index: 5;
		cursor:pointer;
	}
	.add_event_button:after{
		background: #000;
		border-radius: 2px;
		color: #FFF;
		content: attr(data-tooltip);
		margin: 16px 0 0 -137px;
		opacity: 0;
		padding: 4px 9px;
		position: absolute;
		visibility: visible;
		font-family: "Roboto";
		font-size: 14px;
		visibility: hidden;
		transition: all .5s ease-in-out;
	}
	.add_event_button:hover{
		background: #ff774c;
	}
	.add_event_button:hover:after{
		opacity: 0.55;
		visibility: visible;
	}
	.add_event_button span:before{
		content:"";
		background: #fff;
		height: 16px;
		width: 2px;
		position: absolute;
		left: 26px;
		top: 20px;
	}
	.add_event_button span:after{
		content:"";
		height: 2px;
		width: 16px;
		background: #fff;
		position: absolute;
		left: 19px;
		top: 27px;
	}

	.dhx_cal_event div.dhx_event_resize.dhx_footer{
		background-color: transparent !important;
	}
	a img{
		border: none;
	}
	li{
		list-style: none;
	}
</style>
</head>
<script>
	function init() {
		window.resizeTo(950,700)
		modSchedHeight();
		scheduler.config.xml_date = "%Y-%m-%d %H:%i";
		scheduler.config.first_hour = 7;
		scheduler.config.details_on_create = true;

		scheduler.config.now_date = new Date(2020, 3, 24, 14, 17);

		scheduler.templates.event_class=function(start, end, event){
			var css = "";

			if(event.evType) // if event has type property then special class should be assigned
				css += "event_"+getLabel(evType, event.evType).toLowerCase();

			return css; // default return
		};

		function getLabel(array, key){
			for (var i = 0; i < array.length; i++) {
				if (key == array[i].key)
					return array[i].label;
			}
			return null;
		}

		var evType = [
			{ key: '', label: 'Select event type' },
			{ key: 1, label: 'Rest' },
			{ key: 2, label: 'Meeting' },
			{ key: 3, label: 'Movies' },
			{ key: 4, label: 'Work' }
		];

		scheduler.locale.labels.section_evType = "Event type";

		scheduler.config.lightbox.sections=[
			{ name:"description", height:43, map_to:"text", type:"textarea" , focus:true },
			{ name:"evType", height:20, type:"select", options: evType, map_to:"evType" },
			{ name:"time", height:72, type:"time", map_to:"auto" }
		];

		scheduler.init("scheduler_here",new Date(2020,3,20),"week");
		scheduler.parse([
			{ start_date: "2020-04-20 10:00", end_date: "2020-04-20 12:00", text:"Front-end meeting"},
			{ start_date: "2020-04-21 18:00", end_date: "2020-04-21 20:00", text:"Feed ducks and city walking", evType:1},
			{ start_date: "2020-04-22  8:00", end_date: "2020-04-22 11:00", text:"World Darts Championship (morning session)"},
			{ start_date: "2020-04-22 12:00", end_date: "2020-04-22 14:00", text:"Lunch with Ann & Alex", evType:2},
			{ start_date: "2020-04-23 16:00", end_date: "2020-04-23 17:30", text:"Game of Thrones", evType:3},
			{ start_date: "2020-04-25  9:00", end_date: "2020-04-25 11:00", text:"Design workshop", evType:4},
			{ start_date: "2020-04-25 18:00", end_date: "2020-04-25 21:00", text:"World Darts Championship (evening session)"},
			{ start_date: "2020-04-23 00:00", end_date: "2020-04-23 00:00", text:"Couchsurfing. Family from Portugal"}
		], "json");
	}

	function addNewEv(){
		scheduler.addEventNow();

	}
</script>

<body onload="init();" onresize="modSchedHeight()">
	<div style="position: relative; height:95px;background-color:#3D3D3D;border-bottom:5px solid #828282;">
		<a style="position: absolute; left: 25px; top: 22px; z-index: 10;" href="sample_year.shtml"><img src="images/btn-left.gif"></a>
		<div id="contbox" style="position: relative; padding: 22px 25px 0 75px; font: normal 17px Arial, Helvetica; color:white;">
			<div style="position: absolute; left: 75px; top: 22px; border-right:5px solid #2D8EB6;color:#2D8EB6;width:175px;height:50px;text-align:right;padding-right:25px;">Basic Scheduler</div>
			<div style="padding-left: 205px; min-width: 400px;">
                <div style="font-size:12px;padding-left:20px;">Browse the events in Day, Week, Month, Year, or Agenda Views.</div>
    			<div style="font-size:12px;padding-left:20px;margin-top:5px;color:#949292;">You can add/edit/delete events, but changes will be available only until the demo is reloaded.</div>
            </div>
		</div>
		<a style="position: absolute; right: 25px; top: 22px;" href="sample_recurring.shtml"><img src="images/btn-right.gif"></a>
	</div>
	<!-- end. info block -->
    <ul>
        <li>
            <a></a>
            <span></span>
        </li>
    </ul>
	
	<div id="scheduler_here" class="dhx_cal_container" style='width:100%;height:100%;'>
		<div class="dhx_cal_navline">
			<div class="dhx_cal_prev_button">&nbsp;</div>
			<div class="dhx_cal_next_button">&nbsp;</div>
			<div class="dhx_cal_today_button"></div>
			<div class="dhx_cal_date"></div>
			<div class="dhx_cal_tab" name="day_tab" style="right:332px;"></div>
			<div class="dhx_cal_tab" name="week_tab" style="right:268px;"></div>
			<div class="dhx_cal_tab" name="month_tab" style="right:204px;"></div>
			<div class="dhx_cal_tab" name="year_tab" style="right:140px;"></div>
		</div>
		<div class="dhx_cal_header">
		</div>
		<div class="dhx_cal_data">
		</div>		
	</div>
	<div class="add_event_button" onclick="addNewEv()" data-tooltip="Create new event"><span></span></div>
</body>
	<script>
		function modSchedHeight(){
			var headHeight = 100;
			var sch = document.getElementById("scheduler_here");
			sch.style.height = (parseInt(document.body.offsetHeight)-headHeight)+"px";
			var contbox = document.getElementById("contbox");
			contbox.style.width = (parseInt(document.body.offsetWidth)-300)+"px";
		}
	</script>

</html>