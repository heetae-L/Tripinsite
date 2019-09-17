package project.com.tripinsite.controller.introduction;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Introduction {
	/** 사이트 소개 페이지 */
	@RequestMapping(value = "/introduction/introduction.do", method = RequestMethod.GET)
	public String introduction(Locale locale, Model model) {
		return "introduction/introduction";
	}


}
