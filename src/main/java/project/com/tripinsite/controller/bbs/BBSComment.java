package project.com.tripinsite.controller.bbs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.com.tripinsite.helper.RegexHelper;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Comment;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.service.CommentService;

@Controller
public class BBSComment {
	protected static Logger logger = LoggerFactory.getLogger("BBSComment");

	@Autowired
	SqlSession sqlSession;

	@Autowired
	WebHelper web;

	@Autowired
	RegexHelper regex;

	@Autowired
	CommentService commentService;

	/** 댓글 리스트 */
	@RequestMapping(value = "/bbs/comment_list.do", method = RequestMethod.GET)
	public ModelAndView bbsComList(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** WebHelper 초기화 */
		web.init();

		response.setContentType("application/json");

		/** (3) 파라미터 받기 */
		int idboard = web.getInt("idboard");
		logger.debug("idboard=" + idboard);

		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (idboard == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다.");
		}

		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		Comment comment = new Comment();
		comment.setBoard_idboard(idboard);

		/** (6) Service를 통한 덧글 목록 조회 */
		// 작성 결과를 저장할 객체
		List<Comment> item = null;
		try {
			item = commentService.selectCommentList(comment);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		for (int i = 0; i < item.size(); i++) {
			Comment temp = item.get(i);
			temp.setWriter_nickname(web.convertHtmlTag(temp.getWriter_nickname()));
			temp.setContent(temp.getContent().replace("&lt;br/&gt;", "\n"));
			temp.setContent(web.convertHtmlTag(temp.getContent()));
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		return null;
	}

	/** 댓글 작성 */
	@RequestMapping(value = "/bbs/comment_insert.do", method = RequestMethod.POST)
	public ModelAndView bbsComWrite(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** WebHelper 초기화 */
		web.init();

		response.setContentType("application/json");

		int idboard = web.getInt("idboard");
		String writer_nickname = web.getString("writer_name");
		String writer_pw = web.getString("writer_pw");
		String content = web.getString("content");
		// 작성자 아이피 주소 가져오기
		String ip_address = web.getClientIP();
		// 회원 일련번호 --> 비 로그인인 경우 0
		int member_id = 0;
		
		// 전달된 파라미터는 로그로 확인
		logger.debug("idboard=" + idboard);
		logger.debug("writer_nickname=" + writer_nickname);
		logger.debug("writer_pw=" + writer_pw);
		logger.debug("content=" + content);
		logger.debug("ip_address=" + ip_address);
		logger.debug("member_id=" + member_id);

		/** 입력 받은 파라미터를 Beans로 묶기 */
		Comment comment = new Comment();
		comment.setBoard_idboard(idboard);
		comment.setWriter_nickname(writer_nickname);
		comment.setWriter_pw(writer_pw);
		comment.setContent(content);
		comment.setIp_address(ip_address);
		comment.setMember_id(member_id);

		// 로그인 한 경우, 입력하지 않은 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			writer_nickname = loginInfo.getNickname();
			writer_pw = loginInfo.getPassword();
			member_id = loginInfo.getId();
			comment.setMember_id(loginInfo.getId());
			comment.setWriter_nickname(loginInfo.getNickname());
			comment.setWriter_pw(loginInfo.getPassword());
		}

		/** 입력받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (idboard == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다.");
		}

		// 이름 검사
		if (!regex.isValue(writer_nickname)) {
			web.printJsonRt("이름을 입력하세요.");
		}

		// 이름 길이
		if (writer_nickname.length() > 20) {
			web.printJsonRt("닉네임은 20자까지만 가능합니다.");
		}

		// 비밀번호 검사
		if (!regex.isValue(writer_pw)) {
			web.printJsonRt("비밀번호를 입력하세요.");
		}

		// 내용 검사
		if (!regex.isValue(content)) {
			web.printJsonRt("내용을 입력하세요.");
		}

		/** Service를 통한 게시물 저장 */
		// 작성 결과를 저장할 객체
		Comment item = null;
		try {
			commentService.insertComment(comment);
			item = commentService.selectComment(comment);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		/** 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setWriter_nickname(web.convertHtmlTag(item.getWriter_nickname()));
		item.setContent(web.convertHtmlTag(item.getContent()));

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		return null;
	}

	/** 댓글 수정 */
	@RequestMapping(value = "/bbs/comment_edit.do", method = RequestMethod.GET)
	public ModelAndView bbsComEdit(Locale locale, Model model) {
		web.init();

		/** 글 번호 파라미터 받기 */
		int idcomment = web.getInt("idcomment");
		if (idcomment == 0) {
			return web.redirect(null, "덧글 번호가 지정되지 않았습니다.");
		}

		// 파라미터를 Beans로 묶기
		Comment comment = new Comment();
		comment.setIdcomment(idcomment);

		/** 덧글 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 덧글이 저장될 객체
		Comment readComment = null;

		try {
			readComment = commentService.selectComment(comment);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** 읽은 데이터를 View에게 전달한다. */
		//result.setContent(result.getContent().replace("&lt;br/&gt;", "\n"));
		//item.setContent(web.convertHtmlTag(item.getContent()));
		readComment.setContent(readComment.getContent().replace("&lt;br/&gt;", "\n"));
		model.addAttribute("comment", readComment);

		return new ModelAndView("bbs/comment_edit");
	}

	/** 댓글 수정 완료 */
	@RequestMapping(value = "/bbs/comment_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView bbsComEditOk(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		web.init();

		response.setContentType("application/json");

		/** 파라미터 받기 */
		int idcomment = web.getInt("idcomment");
		String writer_nickname = web.getString("writer_name");
		String writer_pw = web.getString("writer_pw");
		String content = web.getString("content");
		// 작성자 아이피 주소 가져오기
		String ip_address = web.getClientIP();
		// 회원 일련번호 --> 비 로그인인 경우 0
		int member_id = 0;
		
		/** 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체한다. */
		// 소유권 검사 정보
		boolean myComment = false;

		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				Comment temp = new Comment();
				temp.setIdcomment(idcomment);
				temp.setMember_id(loginInfo.getId());

				if (commentService.selectCommentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myComment = true;
					// 입력되지 않은 정보들 갱신
					writer_nickname = loginInfo.getNickname();
					writer_pw = loginInfo.getPassword();
					member_id = loginInfo.getId();
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
			}
		}
		
		writer_nickname = web.convertHtmlTag(writer_nickname);
		content = web.convertHtmlTag(content);

		/** 입력받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (idcomment == 0) {
			web.printJsonRt("덧글 일련번호가 없습니다.");
		}

		// 이름 검사
		if (!regex.isValue(writer_nickname)) {
			web.printJsonRt("이름을 입력하세요.");
		}

		// 비밀번호 검사
		if (!regex.isValue(writer_pw)) {
			web.printJsonRt("비밀번호를 입력하세요.");
		}

		// 내용 검사
		if (!regex.isValue(content)) {
			web.printJsonRt("내용을 입력하세요.");
		}

		/** 입력 받은 파라미터를 Beans로 묶기 */
		Comment comment = new Comment();
		comment.setIdcomment(idcomment);
		comment.setWriter_nickname(writer_nickname);
		comment.setWriter_pw(writer_pw);
		comment.setContent(content);
		comment.setIp_address(ip_address);
		comment.setMember_id(member_id);

		/** 게시물 변경을 위한 Service 기능을 호출 */
		Comment item = null;
		try {
			// 자신의 글이 아니라면 비밀번호 검사를 먼저 수행한다.
			if (!myComment) {
				commentService.selectCommentCountByPw(comment);
			}
			commentService.updateComment(comment);
			// 변경된 결과를 조회
			item = commentService.selectComment(comment);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		/** 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setWriter_nickname(web.convertHtmlTag(item.getWriter_nickname()));
		// --> import java.util.HashMap;
		// --> import java.util.Map;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);

		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		return null;

	}

	/** 댓글 삭제 */
	@RequestMapping(value = "/bbs/comment_delete.do", method = RequestMethod.GET)
	public ModelAndView bbsComDelete(Locale locale, Model model) {
		web.init();

		/** 글 번호 파라미터 받기 */
		int idcomment = web.getInt("idcomment");
		if (idcomment == 0) {
			return web.redirect(null, "덧글 번호가 지정되지 않았습니다.");
		}

		// 파라미터를 Beans로 묶기
		Comment comment = new Comment();
		comment.setIdcomment(idcomment);

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. (비로그인 시 0으로 설정됨)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			comment.setMember_id(loginInfo.getId());
		}

		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int commentCount = 0;
		try {
			commentCount = commentService.selectCommentCountByMemberId(comment);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		}

		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myComment = commentCount > 0;
		model.addAttribute("myComment", myComment);

		// 상태유지를 위하여 게시글 일련번호를 View에 전달한다.
		model.addAttribute("idcomment", idcomment);
		model.addAttribute("comment", comment);

		return new ModelAndView("bbs/comment_delete");
	}

	/** 댓글 삭제 완료 */
	@RequestMapping(value = "/bbs/comment_delete_ok.do", method = RequestMethod.POST)
	public ModelAndView bbsComDeleteOk(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		web.init();

		response.setContentType("application/json");

		/** (3) 파라미터 받기 */
		int idcomment = web.getInt("idcomment");
		String writer_pw = web.getString("writer_pw");

		/** (4) 파라미터를 Beans로 묶기 */
		Comment comment = new Comment();
		comment.setIdcomment(idcomment);
		comment.setWriter_pw(writer_pw);

		/** (5) 데이터 삭제 처리 */
		// 로그인 중이라면 회원일련번호를 Beans에 추가한다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			writer_pw = loginInfo.getPassword();
			comment.setMember_id(loginInfo.getId());
			comment.setWriter_nickname(loginInfo.getNickname());
			comment.setWriter_pw(loginInfo.getPassword());
		}

		try {
			// Beans에 추가된 자신의 회원번호를 사용하여 자신의 글임을 판별한다.
			// --> 자신의 덧글이 아니라면 비밀번호 검사
			if (commentService.selectCommentCountByMemberId(comment) < 1) {
				commentService.selectCommentCountByPw(comment);
			}
			commentService.deleteComment(comment); // 덧글 삭제
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		model.addAttribute("comment", comment);

		if (idcomment == 0) {
			web.printJsonRt("덧글 번호가 없습니다.");
		}
		// 비밀번호 검사
		if (!regex.isValue(writer_pw)) {
			web.printJsonRt("비밀번호를 입력하세요.");
		}

		/** (7) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("idcomment", idcomment);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		return null;

	}
}
