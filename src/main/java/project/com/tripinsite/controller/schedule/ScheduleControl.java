package project.com.tripinsite.controller.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.com.tripinsite.helper.APIHelper;
import project.com.tripinsite.helper.FileInfo;
import project.com.tripinsite.helper.PageHelper;
import project.com.tripinsite.helper.RegexHelper;
import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Api;
import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Board_Schedule;
import project.com.tripinsite.model.File;
import project.com.tripinsite.model.Love;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.model.Sche_table;
import project.com.tripinsite.model.Sche_tableApi;
import project.com.tripinsite.model.Schedule;
import project.com.tripinsite.model.ScheduleForm;
import project.com.tripinsite.service.ApiService;
import project.com.tripinsite.service.BoardService;
import project.com.tripinsite.service.CommentService;
import project.com.tripinsite.service.FileService;
import project.com.tripinsite.service.LoveService;
import project.com.tripinsite.service.ScheTableApiService;
import project.com.tripinsite.service.ScheTableService;
import project.com.tripinsite.service.ScheduleService;

@Controller
public class ScheduleControl {

	protected static Logger logger = LoggerFactory.getLogger("ScheduleControl");

	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	APIHelper api;
	@Autowired
	RegexHelper regex;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	BoardService boardService;
	@Autowired
	CommentService commentService;
	@Autowired
	ScheTableService scheTableService;
	@Autowired
	ApiService apiService;
	@Autowired
	UploadHelper upload;
	@Autowired
	LoveService loveService;
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	ScheTableApiService scheTableApiService;
	@Autowired
	FileService fileService;

	/** 일정 작성 페이지 1 */
	@RequestMapping(value = "/schedule/mySchedule_edit.do", method = RequestMethod.GET)
	public ModelAndView scheduleEdit(Locale locale, Model model) {
		web.init();

		/** 로그인중인지 검사하기 */
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		return new ModelAndView("/schedule/myschedule_edit");
	}

	/** 일정 작성 페이지 2 */
	@RequestMapping(value = "/schedule/mySchedule_edit2.do", method = RequestMethod.POST)
	public ModelAndView scheduleEdit2(Locale locale, Model model, HttpServletResponse response)
			throws ServletException, IOException {

		web.init();

		/** 로그인중인지 검사하기 */
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		String title = web.getString("title");
		String dateStr = web.getString("date");
		int date;
		if(!regex.isValue(dateStr)) {
			return web.redirect(null,  "여행일을 입력하세요.");
		} else {
			date = web.getInt("date");
		}
		String start_date = web.getString("start_date");
		String tgroup = web.getString("tgroup");
		String themaTmp = web.getString("thema");
		String privitTmp = web.getString("privit");
		logger.info("title" + title);
		logger.info("date=" + date);
		logger.info("start_date=" + start_date);
		logger.info("tgroup=" + tgroup);
		logger.info("themaTmp=" + themaTmp);
		logger.info("privitTmp=" + privitTmp);
		
		if (!regex.isValue(title)) {
			return web.redirect(null,  "여행제목을 입력하세요.");
		}
		if (!regex.isValue(start_date)) {
			return web.redirect(null,  "여행시작일을 입력하세요.");
		}
		if (!regex.isValue(tgroup)) {
			return web.redirect(null,  "여행구분을 입력하세요.");
		}
		if (!regex.isValue(themaTmp)) {
			return web.redirect(null, "여행테마를 입력하세요.");
		}
		if (!regex.isValue(privitTmp)) {
			return web.redirect(null, "공개 설정을 입력하세요.");
		}

		// thema를 숫자로 할당함
		int thema = 0;
		switch (themaTmp) {
		case "식도락":
			thema = 1;
			break;
		case "쇼핑":
			thema = 2;
			break;
		case "명소":
			thema = 3;
			break;
		case "휴양":
			thema = 4;
			break;
		default:
			break;
		}

		// privit을 숫자로 할당함
		int privit = 0;
		switch (privitTmp) {
		case "open":
			privit = 0;
			break;
		case "privit":
			privit = 1;
			break;
		default:
			break;
		}

		logger.info("thema=" + thema);
		logger.info("privit=" + privit);

		ScheduleForm form = new ScheduleForm();
		form.setTitle(title);
		form.setDate(date);
		form.setStart_date(start_date);
		form.setTgroup(tgroup);
		form.setThema(thema);
		form.setPrivit(privit);

		model.addAttribute("form", form);

		return new ModelAndView("/schedule/myschedule_edit2");
	}

	/** 일정 작성 완료 */
	@RequestMapping(value = "/schedule/mySchedule_edit_ok.do", method = RequestMethod.POST)
	public String scheduleEdit_ok(Locale locale, Model model, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		web.init();

		String resultSchedule = web.getString("schedule");
		String resultScheTable = web.getString("scheTable");
		String resultBoard = web.getString("board");
		if (resultBoard == null) {
			resultBoard = "";
		}
		
		String rt = "OK";
		logger.info("[resultSchedule]=" + resultSchedule);
		logger.info("[resultScheTable]=" + resultScheTable);
		logger.info("[resultBoard]=" + resultBoard);

		if (!regex.isValue(resultSchedule)) {
			return "저장할 일정이 없습니다.";
		}
		if (!regex.isValue(resultScheTable)) {
			return "저장할 세부일정이 없습니다.";
		}

		/** JSON으로 받아온 데이터를 parcing 한다. */
		JSONParser parser = new JSONParser();
		JSONObject formTmp = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			formTmp = (JSONObject) parser.parse(resultSchedule);
			array = (JSONArray) parser.parse(resultScheTable);
		} catch (ParseException e) {
			e.printStackTrace();
			return "데이터 파싱에 문제가 있습니다.";
		}

		logger.info("[formTmp]=" + formTmp.toJSONString());
		logger.info("[array]=" + array.toJSONString());

		// 로그인 한 경우에만 여행계획 저장 가능하다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return "허용되지 않은 접근 방식입니다. 로그인이 필요합니다.";
		}
		String writer_nickname = loginInfo.getNickname();
		String writer_pw = loginInfo.getPassword();
		int member_id = loginInfo.getId();
		// 작성자 아이피 주소 가져오기
		String ip_address = web.getClientIP();
			
		if (!regex.isValue(writer_nickname) || !regex.isValue(writer_pw) || member_id == 0 ) {
			return "로그인 정보에 문제가 있습니다. 재로그인 하십시오.";
		}

		logger.info("[loginInfo]=" + loginInfo);
		logger.info("[writer_nickname]=" + writer_nickname);
		logger.info("[writer_pw]=" + writer_pw);
		logger.info("[member_id]=" + member_id);
		logger.info("[ip_address]=" + ip_address);

		// 1) board(category="story" 저장 데이터
		int idboard = 0;
		String subject = (String) formTmp.get("title");
		String category = "story";
		int is_notice = 0;
			
		subject = web.convertHtmlTag(subject);
		resultBoard = web.convertHtmlTag(resultBoard);
			
		if(!regex.isValue(subject)) {
			return "여행 제목이 입력되지 않았습니다.";
		}
			
		Board board = new Board();
		board.setCategory(category);
		board.setContent(resultBoard);
		board.setIp_address(ip_address);
		board.setIs_notice(is_notice);
		board.setMember_id(member_id);
		board.setSubject(subject);
		board.setWriter_nickname(writer_nickname);
		board.setWriter_pw(writer_pw);

		logger.debug("[board]=" + board.toString());

		try {
			idboard = boardService.insertStoryBoard(board);
		} catch (NullPointerException e) {
			return e.getLocalizedMessage();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}

		// 2) 스토리 board 글 첨부 파일 데이터

		// 3) schedule 저장 데이터
		int date, theme, privit;
		String dateStr = (String) formTmp.get("date");
		if (!regex.isValue(dateStr)) {
			return "여행 기간이 존재하지 않습니다.";
		} else {
			date = Integer.parseInt(dateStr);
		}
		
		String start_day = (String) formTmp.get("start_date");
		String end_day = (String) formTmp.get("end_date");
		String tag = (String) formTmp.get("tgroup");

		if (!regex.isValue(start_day)) {
			return "여행 시작일이 존재하지 않습니다.";
		}
		if (!regex.isValue(end_day)) {
			return "여행 종료일이 존재하지 않습니다.";
		}
		if (!regex.isValue(tag)) {
			return "여행 구분이 존재하지 않습니다.";
		}

		String themeStr = (String) formTmp.get("thema");
		String privitStr = (String) formTmp.get("privit");
		if (!regex.isValue(themeStr)) {
			return "여행 테마가 존재하지 않습니다.";
		} else {
			theme = Integer.parseInt(themeStr);
		}
		if (!regex.isValue(privitStr)) {
			return "여행 공개 설정 정보가 없습니다.";
		} else {
			privit = Integer.parseInt(privitStr);
		}
		
		logger.info("subject" + subject);
		logger.info("date=" + date);
		logger.info("start_day=" + start_day);
		logger.info("end_day=" + end_day);
		logger.info("tag=" + tag);
		logger.info("theme=" + theme);
		logger.info("privit=" + privit);

		// start_date, end_date 보정하기 ("yyyy-mm-dd"만 남긴다.)
		start_day = start_day.substring(0, 10);
		end_day = end_day.substring(0, 10);

		logger.info("start_day=" + start_day);
		logger.info("end_day=" + end_day);

		// 저장할 Schedule 객체를 세팅한다.
		Schedule schedule = new Schedule();
		schedule.setSubject(subject);
		schedule.setBoard_idboard(idboard);
		schedule.setMember_id(member_id);
		schedule.setStart_day(start_day);
		schedule.setEnd_day(end_day);
		schedule.setTag(tag);
		schedule.setTheme(theme);
		schedule.setPrivit(privit);
		
		// Schedule 을 저장한다.
		int idschedule = 0;
		try {
			idschedule = scheduleService.insertSchedule(schedule);
		} catch (NullPointerException e) {
			return e.getLocalizedMessage();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}

		for (int i = 0; i < array.size(); i++) {
			JSONObject tmp = (JSONObject) array.get(i); // 인덱스 번호로 접근 후 가져온다.
			
			/* Service Parameter로 사용될 객체 선언 및 할당 */
			Sche_table scheTable = new Sche_table();
			Api api = new Api();

			String apiStr = (String) tmp.get("api");
			apiStr.trim();
			if(!regex.isValue(apiStr)) {
				return "관광지 정보가 존재하지 않습니다.";
			}
				
			// title 내부에 존재하는 "(쌍따옴표)를 '(작은따옴표)로 바꿈
			int a = apiStr.indexOf("title");
			int b = apiStr.indexOf("areacode");
			a += 8;
			b -= 3;
			String c = apiStr.substring(a,b);
			String d = "";
			int j = -1, k = 0;
			do {
				j = c.indexOf("\"", j + 1);
				if (j != -1) {
					d = d + c.substring(k, j) + "\'";
					k = j + 1;
				}
			} while( j + 1 < c.length() && j != -1);
				
			if ( d != "") {
				apiStr = apiStr.substring(0,a) + d + apiStr.substring(b);
			} 
	
			JSONObject apiJSON = new JSONObject();
			try {
				apiJSON = (JSONObject) parser.parse(apiStr);
			} catch (ParseException e) {
				try {
					scheduleService.deleteSchedule(schedule);
				} catch (Exception e1) {
					e1.getLocalizedMessage();
					return e.getLocalizedMessage();
				}
				return "관광지 정보 처리에 실패하였습니다.";
			}
			// "api" 구조 [contentid, title, areacode, sigungucode, addr1, addr2, contenttpeid, mapx, mapy, tel]

			// 2) api DB 임시 저장 데이터
			int contentid = Integer.parseInt((String) apiJSON.get("contentid")); // primary key이지만 auto-increment 하지 않는다.
			String title = (String) apiJSON.get("title");
			int areacode = Integer.parseInt((String) apiJSON.get("areacode"));
			int sigungucode = Integer.parseInt((String) apiJSON.get("sigungucode"));
			String addr1 = (String) apiJSON.get("addr1");
			String addr2 = (String) apiJSON.get("addr2");
			int contenttypeid = 0; // Null 존재
			if ((String) apiJSON.get("contenttypeid") != "") {
				contenttypeid = Integer.parseInt((String) apiJSON.get("contenttypeid"));
			}
			float mapx = Float.parseFloat((String) apiJSON.get("mapx"));
			float mapy = Float.parseFloat((String) apiJSON.get("mapy"));
			String tel = (String) apiJSON.get("tel");

			// 3) sche_table 저장 데이터 (int api_contentid 이외 데이터)
			String starttime = (String) tmp.get("start_date");
			String endtime = (String) tmp.get("end_date");

			/* 저장할 데이터를 객체에 할당하기 */
			Api apiTest = new Api(); // api정보가 이미 DB에 존재하는지 테스트함

			api.setContentid(contentid);
			try {
				apiTest = apiService.selectApi(api);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}

			if (apiTest == null) {
				api.setTitle(title);
				api.setAreacode(areacode);
				api.setSigungucode(sigungucode);
				api.setAddr1(addr1);
				api.setAddr2(addr2);
				if (contenttypeid != 0) {
					api.setContenttypeid(contenttypeid);
				}
				api.setMapx(mapx);
				api.setMapy(mapy);
				api.setTel(tel);

				System.out.println(api.toString());
			}

			// scheTable.setIdsche_table(idsche_table);
			scheTable.setApi_contentid(contentid);
			scheTable.setStarttime(starttime);
			scheTable.setEndtime(endtime);
			scheTable.setSchedule_idschedule(idschedule);

			System.out.println(scheTable.toString());

			/* Service를 통한 스케쥴 이벤트 저장 */
			try {
				if (apiTest == null) {
					apiService.insertApi(api);
				}
				scheTableService.insertScheTable(scheTable);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				return e.getLocalizedMessage();
				// return web.redirect(null, e.getLocalizedMessage());
			} // try~catch

		} // end for
		
		// json 데이터를 전송
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", rt);
		logger.debug("data=" + data);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

		return null;
	}

	/** 내가 작성한 일정 리스트 */
	@RequestMapping(value = "/schedule/mySchedule_list.do", method = RequestMethod.GET)
	public ModelAndView my_scheduleList(Locale locale, Model model) {

		web.init();

		String category = "story";

		if (!category.equals("story") || category == null) {
			return web.redirect(null, "카테고리가 올바르지 않습니다.");
		}

		/** 로그인중인지 검사하기 */
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		model.addAttribute("category", category);

		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);

		Member loginInfo = (Member) web.getSession("loginInfo");

		Board_Schedule schedule = new Board_Schedule();
		schedule.setCategory(category);
		schedule.setMember_id(loginInfo.getId());

		List<Board_Schedule> scheduleList = null;
		int totalCount = 0;

		try {
			// 전체 게시물 수
			totalCount = boardService.selectMyScheduleCount(schedule);

			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수, 한 페이지의 목록 수, 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);

			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			schedule.setLimitStart(pageHelper.getLimitStart());
			schedule.setListCount(pageHelper.getListCount());

			scheduleList = boardService.selectMyStoryBoardList(schedule);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		model.addAttribute("scheduleList", scheduleList);
		// 페이지 번호 계산 결과를 View에 전달
		model.addAttribute("pageHelper", pageHelper);

		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) * pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);

		return new ModelAndView("/schedule/myschedule_list");
	}

	/** 일정 수정 페이지 */
	@RequestMapping(value = "/schedule/schedule_update.do", method = RequestMethod.POST)
	public ModelAndView scheduleUpdate(Locale locale, Model model) {
		web.init();

		int idboard = web.getInt("idboard");
		int idschedule = web.getInt("schedule");
		String category = web.getString("category");
		String password = web.getString("writer_pw"); // 값이 받아지지 않음.

		if (web.getSession("loginInfo") == null) {
			return web.redirect(null, "로그인이 필요합니다.");
		}

		// 조회할 Beans 생성
		Board_Schedule schedule = new Board_Schedule();
		schedule.setIdboard(idboard);
		schedule.setCategory(category);
		schedule.setIdschedule(idschedule);
		schedule.setWriter_pw(password);

		if (web.getSession("loginInfo") != null) {
			Member loginInfo = (Member) web.getSession("loginInfo");
			password = loginInfo.getPassword();
			schedule.setMember_id(loginInfo.getId());
		}

		try {
			int result = boardService.selectScheduleCountByMemberId(schedule);

			if (result == 0) {
				return web.redirect(null, "자신의 게시글이 아닙니다.");
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		if (!category.equals("story")) {
			return web.redirect(null, "카테고리가 올바르지 않습니다..");
		}
		if (idboard == 0 || idschedule == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}

		File file = new File();
		file.setBoard_idboard(idboard);

		// 조회 결과를 담을 Beans
		Board_Schedule result = null;
		List<File> imageList = null;

		// 게시글을 조회
		try {
			result = boardService.selectStoryBoardView(schedule);
			imageList = fileService.selectFileList(file);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, e.getLocalizedMessage());
		}

		result.setContent(result.getContent().replace("&lt;br/&gt;", "\n"));

		model.addAttribute("result", result);
		model.addAttribute("imageList", imageList);

		return new ModelAndView("/schedule/schedule_update");
	}

	/** 일정 수정 완료 페이지 */
	@RequestMapping(value = "/schedule/schedule_update_ok.do", method = RequestMethod.POST)
	public ModelAndView scheduleUpdateOk(Locale locale, Model model) {
		web.init();

		if (web.getSession("loginInfo") == null) {
			return web.redirect("/tripinsite", "로그인이 필요합니다.");
		}

		/** 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다");
		}

		/** UploadHelper에서 텍스트 형식의 값을 추출 */
		Map<String, String> paramMap = upload.getParamMap();

		if (upload.getFileList().size() > 5) {
			return web.redirect(null, "업로드 가능한 이미지 수는 5개까지 입니다.");
		}

		int idboard = 0;
		int idschedule = 0;
		try {
			idboard = Integer.parseInt(paramMap.get("idboard"));
			idschedule = Integer.parseInt(paramMap.get("schedule"));
			logger.info("idboard >>" + idboard);
			logger.info("idschedule >>" + idschedule);
		} catch (NumberFormatException e) {
			return web.redirect(null, "글 번호가 올바르지 않습니다.");
		}

		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		String start_day = paramMap.get("start_date");
		String tag = paramMap.get("tgroup");
		int theme = Integer.parseInt(paramMap.get("thema"));
		int privit = Integer.parseInt(paramMap.get("privit"));

		subject = web.convertHtmlTag(subject);
		content = web.convertHtmlTag(content);

		logger.info("subject=" + subject);
		logger.info("content=" + content);
		logger.info("start_day=" + start_day);
		logger.info("tag=" + tag);
		logger.info("theme=" + theme);
		logger.info("privit=" + privit);

		Board_Schedule board_Schedule = new Board_Schedule();
		board_Schedule.setIdboard(idboard);
		board_Schedule.setCategory("story");
		board_Schedule.setSubject(subject);
		board_Schedule.setContent(content);
		board_Schedule.setStart_day(start_day);
		board_Schedule.setTag(tag);
		board_Schedule.setTheme(theme);
		board_Schedule.setPrivit(privit);
		
		Schedule schedule = new Schedule();
		schedule.setBoard_idboard(idboard);
		schedule.setIdschedule(idschedule);
		schedule.setSubject(subject);
		schedule.setPrivit(privit);
		schedule.setTag(tag);
		schedule.setTheme(theme);
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			board_Schedule.setWriter_nickname(loginInfo.getNickname());
			board_Schedule.setIp_address(web.getClientIP());
			schedule.setMember_id(loginInfo.getId());
		}

		// 게시판 수정을 위한 Service 기능 호출
		try {
			boardService.updateBoard(board_Schedule);
			scheduleService.updateSchedule(schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, e.getLocalizedMessage());
		}
		

		/** 추가적으로 업로드 된 파일 정보 처리 */
		List<FileInfo> fileInfoList = upload.getFileList();

		File count_file = new File();
		count_file.setBoard_idboard(idboard);

		int fileCount = 0;
		try {
			fileCount = fileService.selectFileCount(count_file);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, e.getLocalizedMessage());
		}

		logger.info("fileCount >>" + fileCount);

		if (fileCount + upload.getFileList().size() > 5) {
			logger.error("업로드된 파일 수가 너무 많음");
			return web.redirect(null, "업로드된 파일수가 5개 이상입니다");
		}

		/** 삭제를 선택한 첨부파일에 대한 처리 */
		String delFile = paramMap.get("del_file");

		if (delFile != null) {
			// 콤마 단위로 잘라서 배열로 변환
			String[] delFileList = delFile.split(",");

			for (int i = 0; i < delFileList.length; i++) {
				try {
					// 체크박스에 의해서 전달된 id값으로 개별파일에 대한 Beans 생성
					File deletefile = new File();
					deletefile.setIdfile(Integer.parseInt(delFileList[i]));

					// 개별 파일에 대한 정보를 조회하여 실제 파일을 삭제한다
					File item = fileService.selectFile(deletefile);
					upload.removeFile(item.getFile_dir() + "/" + item.getFile_name());

					// DB에서 파일정보 삭제 처리
					fileService.deleteFile(deletefile);
				} catch (Exception e) {
					return web.redirect(null, e.getLocalizedMessage());
				}
			}
		}

		/** 새로 업로드된 첨부파일에 대한 처리 */
		for (int i = 0; i < fileInfoList.size(); i++) {
			// 업로드 된 정보 하나 추출
			FileInfo info = fileInfoList.get(i);

			// DB에 저장하기 위한 항목 하나 생성
			File uploadFile = new File();

			// 데이터 복사
			uploadFile.setOrigin_name(info.getOriginName());
			uploadFile.setFile_dir(info.getFileDir());
			uploadFile.setFile_name(info.getFileName());
			uploadFile.setContent_type(info.getContentType());
			uploadFile.setFile_size(info.getFileSize());

			// 어느 게시물에 속한 파일인지 인식해야 하므로 글 번호 추가
			uploadFile.setBoard_idboard(idboard);

			// 복사된 데이터를 DB에 저장
			try {
				fileService.insertFile(uploadFile);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}

		String url = "%s/schedule/schedule_view.do?category=%s&idboard=%d&schedule=%d";
		url = String.format(url, web.getRootPath(), "story", idboard, idschedule);
		return web.redirect(url, "수정 성공");
	}

	/** 일정 삭제 */
	@RequestMapping(value = "/schedule/myschedule_delete.do", method = RequestMethod.POST)
	public ModelAndView my_scheduleDelete(Locale locale, Model model) {
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		int idboard = web.getInt("idboard");
		int idschedule = web.getInt("schedule");
		String writer_pw = web.getString("writer_pw");

		Board_Schedule board = new Board_Schedule();
		board.setCategory(category);
		board.setIdboard(idboard);
		board.setIdschedule(idschedule);
		board.setWriter_pw(writer_pw);

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. (비로그인 시 0으로 설정됨)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			board.setWriter_pw(loginInfo.getPassword());
		}

		// 삭제 처리
		try {
			int result = boardService.selectStoryCountByPw(board);

			if (result == 0) {
				web.redirect(null, "잘못된 비밀번호 입니다");
			}

			// 실제 파일 삭제 처리
			File deletefile = new File();
			deletefile.setBoard_idboard(idboard);

			List<File> item = fileService.selectFileList(deletefile);
			for (int i = 0; i < item.size(); i++) {
				upload.removeFile(item.get(i).getFile_dir() + "/" + item.get(i).getFile_name());
			}

			boardService.deleteStory(board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, e.getLocalizedMessage());
		}

		String url = "%s/schedule/schedule_list.do?category=%s";
		url = String.format(url, web.getRootPath(), category);
		return web.redirect(url, "삭제 성공");
	}

	/** 일정 보기 */
	@RequestMapping(value = "/schedule/schedule_view.do", method = RequestMethod.GET)
	public ModelAndView scheduleView(Locale locale, Model model) {
		web.init();

		int idboard = web.getInt("idboard");
		int idschedule = web.getInt("schedule");
		String category = web.getString("category");

		if (idboard == 0 || idschedule == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}

		// 좋아요 기능에 대한 처리
		if (web.getSession("loginInfo") != null) {
			Member loginInfo = (Member) web.getSession("loginInfo");
			Love love = new Love();
			love.setMember_id(loginInfo.getId());
			love.setSchedule_idschedule(idschedule);
			int loveCount = 0;
			try {
				loveCount = loveService.selectLoveCount(love);
				model.addAttribute("loveCount", loveCount);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}

		Board_Schedule schedule = new Board_Schedule();
		schedule.setCategory(category);
		schedule.setIdboard(idboard);

		File file = new File();
		file.setBoard_idboard(idboard);
		List<File> imageList = null;

		// 지금 읽고 있는 게시물이 저장될 객체
		Board_Schedule readBoard = null;
		Board_Schedule prevBoard = null;
		Board_Schedule nextBoard = null;

		// 스케쥴(Schedule) 저장될 객체
		Schedule scheduleTmp = new Schedule();
		scheduleTmp.setIdschedule(idschedule);
		Schedule scheduleRt = null;

		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		// ex) board_notice_15
		String cookieKey = "board_" + idboard;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);

		// 로그인 한 경우, 입력하지 않은 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			schedule.setWriter_nickname(loginInfo.getNickname());
			schedule.setWriter_pw(loginInfo.getPassword());
			schedule.setMember_id(loginInfo.getId());
		}

		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		try {
			boardCount = boardService.selectBoardCountByMemberId(schedule);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);

		try {

			// 쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				boardService.updateBoardHit(schedule);
				// 준비한 문자열에 대한 쿠키를 24시간동안 저장
				web.setCookie(cookieKey, "Y", 60 * 60 * 24);
			}
			readBoard = boardService.selectStoryBoardView(schedule);
			prevBoard = boardService.selectPrevStoryBoard(schedule);
			nextBoard = boardService.selectNextStoryBoard(schedule);
			imageList = fileService.selectFileList(file);
			scheduleRt = scheduleService.selectSchedule(scheduleTmp);

			// 이미지에 대한 조회 결과가 있다면, 이미지 경로를 만들어 Beans에 저장한다.
			if (imageList.size() > 0) {
				for (int i = 0; i < imageList.size(); i++) {
					String imagePath = null;

					String file_dir = imageList.get(i).getFile_dir();
					String file_name = imageList.get(i).getFile_name();
					String file_type = imageList.get(i).getContent_type();
					file_type = file_type.substring(file_type.lastIndexOf("/"));

					logger.info("file_dir >>" + file_dir);
					logger.info("file_name >>" + file_name);
					logger.info("file_type >>" + file_type);

					imagePath = file_dir + "/" + file_name;
					imageList.get(i).setImagePath(imagePath);

					logger.info("imagePath >>" + imagePath);
				}
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, e.getLocalizedMessage());
		}

		readBoard.setContent(readBoard.getContent().replace("&lt;br/&gt;", "<br/>"));

		logger.info("imageList >> " + imageList);

		model.addAttribute("readBoard", readBoard);
		model.addAttribute("prevBoard", prevBoard);
		model.addAttribute("nextBoard", nextBoard);
		model.addAttribute("imageList", imageList);
		model.addAttribute("schedule", scheduleRt);

		return new ModelAndView("/schedule/schedule_view");
	}

	/** 작성된 일정 리스트 보기 */
	@RequestMapping(value = "/schedule/schedule_list.do", method = RequestMethod.GET)
	public ModelAndView scheduleList(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		web.init();

		String sort = web.getString("sort");
		String category = web.getString("category");

		if (category == null) {
			return web.redirect(null, "카테고리를 입력해주세요.");
		}

		if (!category.equals("story")) {
			return web.redirect(null, "카테고리가 올바르지 않습니다.");
		}

		model.addAttribute("category", category);

		Board_Schedule schedule = new Board_Schedule();
		schedule.setCategory(category);

		if (regex.isValue(sort)) {
			model.addAttribute("sort", true);
			schedule.setSort(true);
		} else {
			model.addAttribute("sort", false);
			schedule.setSort(false);
		}

		List<Board_Schedule> scheduleList = null;

		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);

		/** 게시글 목록 조회 */
		int totalCount = 0;
		
		try {
			// 전체 게시물 수
			totalCount = boardService.selectScheduleCount(schedule);

			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수, 한 페이지의 목록 수, 그룹갯수
			pageHelper.pageProcess(page, totalCount, 8, 5);

			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			schedule.setLimitStart(pageHelper.getLimitStart());
			schedule.setListCount(pageHelper.getListCount());

			scheduleList = boardService.selectStoryBoardList(schedule);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		// 페이지 번호 계산 결과를 View에 전달
		model.addAttribute("pageHelper", pageHelper);

		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) * pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);

		if (scheduleList != null) {
			for (int i = 0; i < scheduleList.size(); i++) {
				Board_Schedule item = scheduleList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
					item.setImagePath(thumbPath);
				}
			}
		}

		model.addAttribute("scheduleList", scheduleList);

		String view = "/schedule/schedule_list";

		return new ModelAndView(view);
	}

	/** 스케쥴 지역 검색 */
	@RequestMapping(value = "/schedule/area_search.do", method = RequestMethod.GET)
	public void schedule_area_search(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		web.init();

		String key = web.getString("keyword");
		
		int page = web.getInt("page", 1);
		logger.info("page=" + page);
		boolean action = regex.isValue(web.getString("action"));
		if (action) {
			String action_str = web.getString("action");
			if (action_str.equals("next")) {
				page++;
			} else {
				page--;
			}
		}

		logger.info("keyword=" + key + ",pageNo=" + page);

		try {
			// API 요청
			String result = api.keyCode(key, page);

			// 받아온 API result를 json으로 변환
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(result);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject personObject = (JSONObject) jsonObj.get("response");
			JSONObject personObject_str = (JSONObject) personObject.get("body");
			Object totalCount_obj = personObject_str.get("totalCount");
			
			// 전체 데이터 수
			int totalCount = Integer.parseInt(totalCount_obj.toString());
			logger.info("totalCount=" + totalCount);

			// json 데이터를 전송
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("rt", "ok");
			data.put("result", jsonObj);
			data.put("page", page);
			data.put("totalCount", totalCount);

			System.out.println(data);

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	/** 추천 추가 */
	@RequestMapping(value = "/schedule/schedule_love_add.do", method = RequestMethod.GET)
	public void scheduleLoveAdd(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();

		int id = web.getInt("id");
		int idschedule = web.getInt("idschedule");
		String category = web.getString("category");

		logger.info("id >>" + id + ", idschedule >>" + idschedule + ", category >>" + category);

		Map<String, Object> data = new HashMap<String, Object>();

		if (!category.equals("story")) {
			data.put("rt", "잘못된 카테고리 입니다.");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

			return;
		}

		Love love = new Love();
		love.setMember_id(id);
		love.setSchedule_idschedule(idschedule);

		try {
			int result = loveService.selectLoveCount(love);

			if (result > 0) {
				throw new Exception("이미 좋아요를 누른 게시글 입니다.");
			}
			loveService.insertLove(love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			data.put("rt", e.getLocalizedMessage());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
		}

		data.put("rt", "ok");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

	}

	/**
	 * 추천 삭제
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/schedule/schedule_love_delete.do", method = RequestMethod.GET)
	public void scheduleLoveDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();

		int id = web.getInt("id");
		int idschedule = web.getInt("idschedule");
		String category = web.getString("category");

		logger.info("id >>" + id + ", idschedule >>" + idschedule + ", category >>" + category);

		Map<String, Object> data = new HashMap<String, Object>();

		if (!category.equals("story")) {
			data.put("rt", "잘못된 카테고리 입니다.");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

			return;
		}

		Love love = new Love();
		love.setMember_id(id);
		love.setSchedule_idschedule(idschedule);

		try {
			int result = loveService.selectLoveCount(love);

			if (result == 0) {
				throw new Exception("이미 좋아요를 취소한 게시글 입니다.");
			}

			loveService.deleteLove(love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			data.put("rt", e.getLocalizedMessage());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
		}

		data.put("rt", "ok");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
	}

	/** 스케쥴러 불러오기 테스트 모듈 */
	@RequestMapping(value = "/schedule/scheduleTest.do", method = RequestMethod.GET)
	public String ScheduleTest(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		web.init();

		return "/schedule/schedule_test";

	}

	/** 스케쥴러 불러오기 API */
	@ResponseBody
	@RequestMapping(value = "/schedule/scheTableApi.do", method = RequestMethod.GET)
	public void ScheTableApi(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();

		int idschedule = web.getInt("idschedule");
		logger.info("idschedule=" + idschedule);

		Sche_table scheTable = new Sche_table();
		scheTable.setSchedule_idschedule(idschedule);

		List<Sche_tableApi> result = new ArrayList<Sche_tableApi>();
		try {
			result = scheTableApiService.selectScheTableList(scheTable);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		List<Object> tmpList = new ArrayList<Object>();
		for (int i = 0; i < result.size(); i++) {
			Sche_tableApi tmp = result.get(i);
			logger.debug("tmp[i]=" + tmp.toString());
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("id", "" + tmp.getIdsche_table());
			String tpl = "<h4 name='title' style='font-size:10px;'>" + tmp.getTitle() + "</h4><p name='addr' style='font-size:8px;'>" + tmp.getAddr1()
					+ "</p><input type='hidden' value=\"{contentid:" + tmp.getApi_contentid() + "}\"></div>";
			tmpMap.put("start_date", tmp.getStarttime());
			tmpMap.put("end_date", tmp.getEndtime());
			tmpMap.put("text", tpl);
			tmpMap.put("idschedule", "" + tmp.getSchedule_idschedule());
			tmpMap.put("title", tmp.getTitle());
			tmpMap.put("addr", tmp.getAddr1());
			tmpMap.put("mapx", "" + tmp.getMapx());
			tmpMap.put("mapy", "" + tmp.getMapy());
			tmpMap.put("contentid", "" + tmp.getApi_contentid());
			tmpMap.put("contenttypeid", "" + tmp.getContenttypeid());
			tmpMap.put("tel", tmp.getTel());
			tmpList.add(tmpMap);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "ok");
		data.put("data", tmpList);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

	}
	

	/** 스케쥴러 수정-새데이터추가 API */
	@PostMapping("/schedule/scheTableApi.do")
	public String ScheTableApi2(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
		 return "허용되지 않은 접근 방식입니다.";
		}

		/* Service Parameter로 사용될 객체 선언 및 할당 */
		Sche_table scheTable = new Sche_table();
		Api api = new Api();
	
		String apiStr = web.getString("api");
		apiStr.trim();
		if(!regex.isValue(apiStr)) {
			return "관광지 정보가 존재하지 않습니다.";
		}
			
		// title 내부에 존재하는 "(쌍따옴표)를 '(작은따옴표)로 바꿈
		int a = apiStr.indexOf("title");
		int b = apiStr.indexOf("areacode");
		a += 8;
		b -= 3;
		String c = apiStr.substring(a,b);
		String d = "";
		int j = -1, k = 0;
		do {
			j = c.indexOf("\"", j + 1);
			if (j != -1) {
				d = d + c.substring(k, j) + "\'";
				k = j + 1;
			}
		} while( j + 1 < c.length() && j != -1);
			
		if ( d != "") {
			apiStr = apiStr.substring(0,a) + d + apiStr.substring(b);
		} 

		logger.debug("[apiStr]=" + apiStr);
		System.out.println(apiStr);
		
		JSONObject apiJSON = new JSONObject();
		JSONParser parser = new JSONParser();
				
		try {
			apiJSON = (JSONObject) parser.parse(apiStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return "관광지 정보 처리에 실패하였습니다.";
		}
		// "api" 구조 [contentid, title, areacode, sigungucode, addr1, addr2, contenttpeid, mapx, mapy, tel]
		// 2) api DB 임시 저장 데이터
		int contentid = Integer.parseInt((String) apiJSON.get("contentid")); // primary key이지만 auto-increment 하지 않는다.
		String title = (String) apiJSON.get("title");
		int areacode = Integer.parseInt((String) apiJSON.get("areacode"));
		int sigungucode = Integer.parseInt((String) apiJSON.get("sigungucode"));
		String addr1 = (String) apiJSON.get("addr1");
		String addr2 = (String) apiJSON.get("addr2");
		int contenttypeid = 0; // Null 존재함
		if ((String) apiJSON.get("contenttypeid") != "") {
			contenttypeid = Integer.parseInt((String) apiJSON.get("contenttypeid"));
		}
		float mapx = Float.parseFloat((String) apiJSON.get("mapx"));
		float mapy = Float.parseFloat((String) apiJSON.get("mapy"));
		String tel = (String) apiJSON.get("tel");

		// sche_table 저장 데이터 (int api_contentid 이외 데이터)
		int idschedule = web.getInt("idschedule");
		String starttime = web.getString("start_date");
		String endtime = web.getString("end_date");

		/* 저장할 데이터를 객체에 할당하기 */
		Api apiTest = new Api(); // api정보가 이미 DB에 존재하는지 테스트함

		api.setContentid(contentid);
		try {
			apiTest = apiService.selectApi(api);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		if (apiTest == null) {
			api.setTitle(title);
			api.setAreacode(areacode);
			api.setSigungucode(sigungucode);
			api.setAddr1(addr1);
			api.setAddr2(addr2);
			if (contenttypeid != 0) {
				api.setContenttypeid(contenttypeid);
			}
			api.setMapx(mapx);
			api.setMapy(mapy);
			api.setTel(tel);

			System.out.println(api.toString());
		}

		// scheTable.setIdsche_table(idsche_table);
		scheTable.setApi_contentid(contentid);
		scheTable.setStarttime(starttime);
		scheTable.setEndtime(endtime);
		scheTable.setSchedule_idschedule(idschedule);

		System.out.println(scheTable.toString());
		
		int id = 0;
		/* Service를 통한 스케쥴 이벤트 저장 */
		try {
			if (apiTest == null) {
				apiService.insertApi(api);
			}
			id = scheTableService.insertScheTable(scheTable);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "추가완료");
		data.put("id", id );

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;				
	}
	
	/** 스케쥴러 수정-수정데이터 API */
	@PostMapping("/schedule/scheTableApi.do/{id}")
	public String ScheTableApi3(@PathVariable int id, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
		 return "허용되지 않은 접근 방식입니다.";
		}
		
		String starttime = web.getString("start_date");
		String endtime = web.getString("end_date");
		logger.info("idsche_table=" + id);
		logger.info("starttime=" + starttime);
		logger.info("endtime=" + endtime);
		
		Sche_table scheTable = new Sche_table();
		scheTable.setIdsche_table(id);
		scheTable.setStarttime(starttime);
		scheTable.setEndtime(endtime);

		try {
			scheTableService.updateScheTable(scheTable);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "수정완료");
		data.put("id", id+"" );

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;				
	}

	/** 스케쥴러 수정-삭제된데이터 API */
	@DeleteMapping("/schedule/scheTableApi.do/{id}")
	public String ScheTableApi4(@PathVariable int id, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
		 return "허용되지 않은 접근 방식입니다.";
		}
		
		Sche_table scheTable = new Sche_table();
		scheTable.setIdsche_table(id);

		try {
			scheTableService.deleteScheTable(scheTable);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "삭제완료");
		data.put("id", ""+id );

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;				
	}
	
	/** 스케쥴러 수정-전부 삭제 API */
	@PostMapping("/schedule/scheTableApi.do/idschedule")
	public String ScheTableApi5(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
		 return "허용되지 않은 접근 방식입니다.";
		}
		
		int idschedule = web.getInt("idschedule");
		Sche_table scheTable = new Sche_table();
		scheTable.setSchedule_idschedule(idschedule);

		try {
			scheTableService.deleteScheTable(scheTable);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "삭제완료");

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;				
	}
	
	/** 여행일정 시작 날짜와 종료 날짜 수정 */
	@PostMapping("/schedule/scheduleApi.do/")
	public String ScheduleApi1(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("application/json");
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
		 return "허용되지 않은 접근 방식입니다.";
		}
		
		int idschedule = web.getInt("idschedule");
		String start_day = web.getString("start_day");
		String end_day = web.getString("end_day");
		
		Schedule schedule = new Schedule();
		schedule.setIdschedule(idschedule);
		schedule.setStart_day(start_day);
		schedule.setEnd_day(end_day);
		System.out.println(schedule.toString());
		
		try {
			scheduleService.updateScheduleDate(schedule);
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "여행시작일과 종료일 수정 완료");

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

		return null;
	}
	
}
