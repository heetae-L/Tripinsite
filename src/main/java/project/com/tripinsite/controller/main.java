package project.com.tripinsite.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.com.tripinsite.helper.APIHelper;
import project.com.tripinsite.helper.RegexHelper;
import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Board_Schedule;
import project.com.tripinsite.service.BoardService;

@Controller
public class main {

	protected static Logger logger = LoggerFactory.getLogger("main");

	@Autowired
	SqlSession sqlSession;

	@Autowired
	WebHelper web;

	@Autowired
	APIHelper api;

	@Autowired
	RegexHelper regex;
	
	@Autowired
	BoardService boardService;

	@Autowired
	UploadHelper upload;

	/* 스토리 리스트 */
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView index(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		web.init();
		
		String sort = web.getString("sort");
		String category = "story";

		Board_Schedule schedule = new Board_Schedule();
		schedule.setCategory(category);
		schedule.setLimitStart(0);
		schedule.setListCount(12);
		
		if (regex.isValue(sort)) {
			model.addAttribute("sort", true);
			schedule.setSort(true);
		} else {
			model.addAttribute("sort", false);
			schedule.setSort(false);
		}
		
		List<Board_Schedule> scheduleList = null;
		
		try {
			scheduleList = boardService.selectStoryBoardList(schedule);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if(scheduleList != null) {
			for(int i=0; i<scheduleList.size(); i++) {
				Board_Schedule item = scheduleList.get(i);
				String imagePath = item.getImagePath();
				if(imagePath != null) {
					String thumbPath;
					try {
						thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
						item.setImagePath(thumbPath);
					} catch (IOException e) {
						logger.error(e.getLocalizedMessage());
						return web.redirect(null, "추천 게시글 조회에 실패했습니다.");
					}
				}
			}
		}
		
		model.addAttribute("scheduleList", scheduleList);
		
		String view = "main";
		
		return new ModelAndView(view);
	}

	/* API데이터 */
	@RequestMapping(value = "/home2", method = RequestMethod.GET)
	public ModelAndView index2(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		web.init();

		try {
			String result = api.areaCode();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(result);
			JSONObject jsonObj = (JSONObject) obj;

			JSONObject personObject = (JSONObject) jsonObj.get("response");
			JSONObject personObject_str = (JSONObject) personObject.get("body");
			Object totalCount_obj = personObject_str.get("totalCount");

			// 전체 데이터 수
			int totalCount = Integer.parseInt(totalCount_obj.toString());

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("rt", "ok");
			data.put("result", jsonObj);
			data.put("page", 1);
			data.put("totalCount", totalCount);

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
		
		return null;

	}
}