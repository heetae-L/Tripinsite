package project.com.tripinsite.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.com.tripinsite.helper.MailHelper;
import project.com.tripinsite.helper.PageHelper;
import project.com.tripinsite.helper.RegexHelper;
import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.Util;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Board_Love;
import project.com.tripinsite.model.Comment;
import project.com.tripinsite.model.Love;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.model.Schedule;
import project.com.tripinsite.service.BoardService;
import project.com.tripinsite.service.CommentService;
import project.com.tripinsite.service.FileService;
import project.com.tripinsite.service.LoveService;
import project.com.tripinsite.service.MemberService;
import project.com.tripinsite.service.ScheduleService;

@Controller
public class MemberController {

	protected static Logger logger = LoggerFactory.getLogger("MemberController");

	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	RegexHelper regex;
	@Autowired
	UploadHelper upload;
	@Autowired
	MemberService memberService;
	@Autowired
	Util util;
	@Autowired
	MailHelper mail;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	FileService fileService;
	@Autowired
	BoardService boardService;
	@Autowired
	CommentService commentService;
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	LoveService loveService;

	/** 회원가입 폼으로 이동 */
	@RequestMapping(value = "/member/join.do", method = RequestMethod.GET)
	public String join(Locale locale, Model model) {
		return "member/join";
	}

	/** 이메일 중복 체크 */
	@RequestMapping(value = "/member/email_check.do", method = RequestMethod.GET)
	@ResponseBody
	public void emailCheck(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		web.init();
		String email = web.getString("email");
		logger.info("email>>>" + email);
		int result = 0;
		String result_str;

		if (web.getSession("loginInfo") != null) {
			Member loginInfo = (Member) web.getSession("loginInfo");
			String email_str = loginInfo.getEmail();
			logger.info("email_str>>>" + email_str);
			if (email.equals(email_str)) {
				logger.info("여기 실행됨");
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("result", "OK");
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), data);
				return;
			}
		}

		/** 이메일 형식 오류 조회 */
		if (!regex.isEmail(email)) {
			result_str = "false";
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("result", "emailformerror");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
			return;
		}

		/** 이메일 목록 조회 */
		Member member = new Member();
		member.setEmail(email);
		try {
			result = memberService.selectEmailCount(member);

			if (result > 0) {
				result_str = "overlap";
			} else {
				result_str = "OK";
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("result", e.getLocalizedMessage());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
			return;
		}

		/** 조회결과를 view에 전달 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result_str);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

		return;
	}

	/** 닉네임 중복 체크 */
	@RequestMapping(value = "/member/nickname_check.do", method = RequestMethod.GET)
	@ResponseBody
	public void nicknameCheck(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		web.init();
		String nickname = web.getString("nickname");
		logger.info("nickname>>>" + nickname);
		int result = 0;
		String result_str;

		if (web.getSession("loginInfo") != null) {
			Member loginInfo = (Member) web.getSession("loginInfo");
			String nickname_str = loginInfo.getNickname();
			logger.info("nickname_str>>>" + nickname_str);
			if (nickname.equals(nickname_str)) {
				logger.info("여기 실행됨");
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("result", "OK");
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), data);
				return;
			}
		}

		/** 닉네임 글자수가 2글자 이하 6글자 이상일 경우 오류 처리 */
		if (nickname.length() < 2 | nickname.length() > 6) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("result", "nicknamelength");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
			return;
		}

		/** 닉네임 목록 조회 */
		Member member = new Member();
		member.setNickname(nickname);
		try {
			result = memberService.selectNicknameCount(member);
			if (result > 0) {
				result_str = "overlap";
			} else {
				result_str = "OK";
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("result", e.getLocalizedMessage());
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
			return;
		}

		/** 조회결과를 View에 전달 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result_str);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		return;
	}

	/** 회원가입 처리 */
	@RequestMapping(value = "/member/join_ok.do", method = RequestMethod.POST)
	public ModelAndView join_ok(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		/** 1) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** 2) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인중에는 회원가입을 할 수 없습니다.");
		}

		String email = web.getString("email");
		String password = web.getString("password");
		String password_re = web.getString("password_re");
		String nickname = web.getString("nickname");
		String age = web.getString("age");
		String gender = web.getString("gender");
		String postcode = web.getString("postcode");
		String addr1 = web.getString("addr1");
		String addr2 = web.getString("addr2");

		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("email=" + email);
		logger.debug("password=" + password);
		logger.debug("password_re=" + password_re);
		logger.debug("nickname=" + nickname);
		logger.debug("age=" + age);
		logger.debug("gender=" + gender);
		logger.debug("postcode=" + postcode);
		logger.debug("addr1=" + addr1);
		logger.debug("addr2=" + addr2);

		/** 입력값의 유효성 검사 */
		// 이메일 검사
		if (!regex.isValue(email)) {
			return web.redirect(null, "이메일을 입력하세요.");
		}
		// 이메일 형식 검사
		if (!regex.isEmail(email)) {
			return web.redirect(null, "이메일의 형식이 잘못되었습니다.");
		}

		// 비밀번호 검사
		if (!regex.isValue(password)) {
			return web.redirect(null, "비밀번호를 입력하세요.");
		}
		// 비밀번호의 숫자와 영문조합 검사
		if (!regex.isEngNum(password)) {
			return web.redirect(null, "비밀번호는 숫자와 영문의 조합으로 20자까지만 가능하며, 특수문자는 불가능합니다.");

		}
		// 비밀번호 길이 검사
		if (password.length() > 20) {
			return web.redirect(null, "비밀번호는 숫자와 영문의 조합으로 20자까지만 가능합니다.");
		}

		// 비밀번호 확인
		if (!password.equals(password_re)) {
			return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
		}

		// 비밀번호 한글만 입력할 경우 제한
		if (regex.isKor(password) | regex.isEng(password)) {
			return web.redirect(null, "문자로만 비밀번호로 하는것은 불가능합니다.");
		}

		// 닉네임 검사
		if (!regex.isValue(nickname)) {
			return web.redirect(null, "닉네임을 입력하세요.");
		}

		// 닉네임 글자수 제한
		if (nickname.length() > 6 | nickname.length() < 2) {
			return web.redirect(null, "닉네임은 2글자 이상 6글자 이하로 입력가능합니다.");
		}

		// 연령대 체크
		if (!regex.isValue(age)) {
			return web.redirect(null, "연령대를 입력해주세요.");
		}

		// 성별 체크
		if (!regex.isValue(gender)) {
			return web.redirect(null, "성별을 선택해주세요.");
		}

		if (regex.isEng(addr1) | regex.isEng(addr2)) {
			return web.redirect(null, "주소창에  값으로 영문입력은 불가능합니다.");
		}
		
		/** 전달받은 파라미터를 Beans 객체에 담는다. */
		Member member = new Member();
		member.setEmail(email);
		member.setPassword(password_re);
		member.setNickname(nickname);
		member.setAge(age);
		member.setGender(gender);
		member.setPostcode(postcode);
		member.setAddr1(addr1);
		member.setAddr2(addr2);

		/** Service를 통한 데이터베이스 저장 처리 */
		try {
			memberService.insertMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			// 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
		}

		/** 가입이 완료되었으므로 메인 페이지로 이동 */
		return web.redirect(web.getRootPath() + "/home.do", "회원가입이 완료되었습니다.");
	}

	/** 로그인 */
	@RequestMapping(value = "/member/login_ok.do", method = RequestMethod.POST)
	public ModelAndView login_ok(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");

		web.init();

		/** 페이지 정보 */
		// 이전 페이지 구하기 (javascript로 이동된 경우 조회 안됨)
		String movePage = request.getHeader("referer");
		if (movePage == null) {
			movePage = web.getRootPath() + "/home.do";
		}

		if (web.getSession("loginInfo") != null) {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("url", movePage);

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

			return null;
		}

		String email = web.getString("user_id");
		String password = web.getString("user_pw");

		Member member = new Member();
		member.setEmail(email);
		member.setPassword(password);

		logger.info("email=" + email);
		logger.info("password=" + password);

		Member loginInfo = null;

		try {
			loginInfo = memberService.selectLoginInfo(member);
		} catch (Exception e) {
			// 로그인 실패
			logger.info(e.getLocalizedMessage());
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("login", "false");
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

			return null;
		}
		// 로그인 성공
		web.setSession("loginInfo", loginInfo);

		logger.info(movePage);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("login", "ok");
		data.put("url", movePage);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);

		return null;
	}

	/** 로그아웃 */
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		web.init();

		web.removeAllSession();

		return web.redirect(web.getRootPath() + "/home.do", "로그아웃 되었습니다.");
	}

	/** 비밀번호 재설정 페이지로 이동 */
	@RequestMapping(value = "/member/search_password.do", method = RequestMethod.GET)
	public ModelAndView search_password(Locale locale, Model model) {

		web.init();

		/** 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 이용할 수 없다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/home.do", "이미 로그인중입니다.");
		}
		/** 사용할 view의 이름 리턴 */
		return new ModelAndView("member/search_password");
	}

	/** 비밀번호 재설정 처리 */
	@RequestMapping(value = "/member/search_password_ok.do", method = RequestMethod.GET)
	public ModelAndView search_password_ok(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		web.init();

		/** 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 이용할 수 없다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/home.do", "이미 로그인중입니다.");
		}

		/** 파라미터 받기 */
		// 입력된 메일 주소를 받는다.
		String email = web.getString("email");

		if (email == null) {
			return web.redirect(null, "이메일 주소를 입력하세요.");
		}

		/** 임시 비밀번호 생성하기 */
		String newPassword = util.getRandomPassword();

		/** 입력값을 JavaBeans에 저장하기 */
		Member member = new Member();
		member.setEmail(email);
		member.setPassword(newPassword);

		/** Service를 통한 비밀번호 갱신 */
		try {
			memberService.updateMemberPasswordByEmail(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** 발급된 비밀번호를 메일로 발송하기 */
		String sender = "webmaster@tripinsite.com";
		String subject = "Trip In Site 비밀번호 변경 안내 입니다.";
		String content = "회원님의 새로운 비밀번호는 <strong>" + newPassword + "</strong>입니다.";

		try {
			mail.sendMail(sender, email, subject, content);
		} catch (Exception e) {
			return web.redirect(null, "메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
		}

		return web.redirect(web.getRootPath() + "/home.do", "임시비밀번호를 이메일로 보냈습니다.");
	}

	/** 회원 수정 페이지로 이동 */
	@RequestMapping(value = "/member/edit.do", method = RequestMethod.GET)
	public ModelAndView edit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();

		// 로그인 여부 검사
		// 로그인 중이 아니라면 수정할 수 없다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		return new ModelAndView("member/edit");
	}

	/** 회원탈퇴로 이동 */
	@RequestMapping(value = "/member/out.do")
	public ModelAndView out(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		web.init();

		// 로그인 여부 검사
		// 로그인 중이 아니라면 탈퇴할 수 없다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다. ");
		}

		return new ModelAndView("member/out");
	}

	/** 마이페이지 폼으로 이동 */
	@RequestMapping(value = "/member/mypage.do", method = RequestMethod.GET)
	public ModelAndView mypage(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		/** (1) webHelper 초기화 */
		web.init();

		/** (2) 로그인중인지 검사하기 */
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		Member loginInfo = (Member) web.getSession("loginInfo");
		Board_Love boardlove = new Board_Love();
		boardlove.setMember_id(loginInfo.getId());

		List<Board_Love> bookmark = null;

		try {
			bookmark = memberService.selectBookMark(boardlove);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		logger.info("bookmark >>" + bookmark);

		model.addAttribute("bookmark", bookmark);

		return new ModelAndView("member/mypage");

	}

	/** 회원가입 탈퇴 처리 */
	@RequestMapping(value = "/member/out_ok.do", method = RequestMethod.POST)
	public ModelAndView out_ok(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		web.init();

		// 로그인 여부 검사
		// 로그인 중이 아니라면 탈퇴할 수 없다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용가능합니다.");
		}

		// 파라미터 받기
		String password = web.getString("password");
		logger.debug("password=" + password);

		if (password == null) {
			return web.redirect(null, "비밀번호를 입력하세요.");
		}

		/** 서비스에 전달하기 위하여 파라미터로 묶는다. */
		Member loginInfo = (Member) web.getSession("loginInfo");
		int id = loginInfo.getId();
		Member member = new Member();
		member.setId(id);
		member.setPassword(password);
		Comment comment = new Comment();
		comment.setMember_id(id);
		Board board = new Board();
		board.setMember_id(id);
		Schedule sche = new Schedule();
		sche.setMember_id(id);
		Love love = new Love();
		love.setMember_id(id);

		/** service를 통한 탈퇴시도 */
		try {
			// 비밀번호 검사 --> 비밀번호가 잘못된 경우 예외발생
			memberService.selectMemberPasswordCount(member);
			
			commentService.updateCommentMemberOut(comment);
			boardService.updateBoardMemberOut(board);
			scheduleService.updateScheMemberOut(sche);
			loveService.updateLoveMemberOut(love);
			// 탈퇴처리
			memberService.deleteMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** 정상적으로 탈퇴된 경우 강제 로그아웃 및 페이지 이동 */
		web.removeAllSession();
		return web.redirect(web.getRootPath() + "/home.do", "회원탈퇴 되었습니다.");
	}

	/** 회원수정 처리 */
	@RequestMapping(value = "/member/edit_ok.do", method = RequestMethod.POST)
	public ModelAndView edit_ok(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		/** 1) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		// 로그인 여부 검사
		Member loginInfo = (Member) web.getSession("loginInfo");

		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/home.do", "로그인 후에 이용 가능합니다.");
		}
		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.

		String password = web.getString("password");
		String password_re = web.getString("password_re");
		String nickname = web.getString("nickname");
		String age = web.getString("age");
		String postcode = web.getString("postcode");
		String addr1 = web.getString("addr1");
		String addr2 = web.getString("addr2");

		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("password=" + password);
		logger.debug("password_re=" + password_re);
		logger.debug("nickname=" + nickname);
		logger.debug("age=" + age);
		logger.debug("postcode=" + postcode);
		logger.debug("addr1=" + addr1);
		logger.debug("addr2=" + addr2);

		/** 입력값의 유효성 검사 */
		// 신규 비밀번호 검사
		// --> 신규 비밀번호가 입력된 경우는 변경으로 간주하고, 입력하지 않은 경우는
		// 변경하지 않도록 처리한다. 그러므로 입력된 경우만 검사해야 한다.
		if (!regex.isValue(password)) {
			return web.redirect(null, "변경할 비밀번호를 입력하세요.");
		}
		// 비밀번호의 숫자와 영문조합 검사
		if (!regex.isEngNum(password)) {
			return web.redirect(null, "비밀번호는 숫자와 영문의 조합으로 20자까지만 가능하며, 특수문자는 불가능합니다.");
		}

		// 비밀번호 길이 검사
		if (password.length() > 20) {
			return web.redirect(null, "비밀번호는 숫자와 영문의 조합으로 20자이내로 가능합니다.");
		}
		// 비밀번호 확인 검사
		if (!password.equals(password_re)) {
			return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
		}

		// 비밀번호 한글만 입력할 경우 제한
		if (regex.isKor(password) | regex.isEng(password)) {
			return web.redirect(null, "문자로만 비밀번호로 하는것은 불가능합니다.");
		}

		// 닉네임 입력 검사
		if (!regex.isValue(nickname)) {
			return web.redirect(null, "닉네임을 입력하세요.");
		}

		// 닉네임 글자수 제한
		if (nickname.length() > 6 | nickname.length() < 2) {
			return web.redirect(null, "닉네임은 2글자 이상 6글자 이하로 입력가능합니다.");
		}

		// 연령대 체크
		if (!regex.isValue(age)) {
			return web.redirect(null, "연령대를 입력해주세요.");
		}

		/** 전달받은 파라미터를 Beans 객체에 담는다. */
		Member member = new Member();
		// WEHRE 절에 사용할 회원번호는 세션에서 취득
		member.setId(loginInfo.getId());
		member.setEmail(loginInfo.getEmail());
		member.setNickname(nickname);
		member.setAge(age);
		member.setPostcode(postcode);
		member.setAddr1(addr1);
		member.setAddr2(addr2);
		// 변경할 신규 비밀번호
		member.setPassword(password_re);
		member.setPassword(password);

		// 본인 닉네임 확인
		try {
			int result = memberService.selectNicknameCount_edit(member);
			String memberNick = loginInfo.getNickname();
			if (result == 1) {
				if (!nickname.equals(memberNick)) {
					return web.redirect(null, "닉네임 중복검사가 필요합니다.");
				}
			}
		} catch (Exception e1) {
			return web.redirect(null, "닉네임 확인에 실패했습니다.");
		}

		/** Service를 통한 데이터베이스 저장 처리 */
		// 변경된 정보를 저장하기 위한 객체
		Member editInfo = null;
		try {
			memberService.updateMember(member);
			editInfo = memberService.selectLoginInfo(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			// 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
		}

		/** 세션을 갱신한다. */
		web.removeSession("loginInfo");
		web.setSession("loginInfo", editInfo);

		/** (11) 수정이 완료되었으므로 다시 수정페이지로 이동 */
		return web.redirect(web.getRootPath() + "/member/mypage.do", "회원정보가 수정되었습니다.");
	}
}