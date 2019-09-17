package project.com.tripinsite.controller.bbs;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
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
import project.com.tripinsite.helper.PageHelper;
import project.com.tripinsite.helper.RegexHelper;
import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Board_Schedule;
import project.com.tripinsite.model.Comment;
import project.com.tripinsite.model.File;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.service.AdminService;
import project.com.tripinsite.service.BoardService;
import project.com.tripinsite.service.CommentService;
import project.com.tripinsite.service.FileService;

@Controller
public class BBSDocument {

	protected static Logger logger = LoggerFactory.getLogger("BBSDocument");

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
	UploadHelper upload;

	@Autowired
	FileService fileService;

	@Autowired
	AdminService adminService;

	/** 자유 게시판 리스트 */
	@RequestMapping(value = "/bbs/bbs_free_list.do", method = RequestMethod.GET)
	public ModelAndView bbsFreeList(Locale locale, Model model) {
		/** WebHelper 초기화 */
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		model.addAttribute("category", category);

		/** 조회할 정보에 대한 Beans 생성 */
		// 검색어
		String keyword = web.getString("keyword");

		Board board = new Board();
		board.setCategory(category);

		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);

		// 제목과 내용에 대한 검색으로 활용하기 위해서 입력값을 설정한다.
		board.setSubject(keyword);
		board.setContent(keyword);

		/** 게시글 목록 조회 */
		int totalCount = 0;
		List<Board> boardList = null;

		/** 공지 */
		List<Board> noticeList = null;

		try {
			// 전체 게시물 수
			totalCount = boardService.selectBoardCount(board);

			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수, 한 페이지의 목록 수, 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);

			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			board.setLimitStart(pageHelper.getLimitStart());
			board.setListCount(pageHelper.getListCount());

			noticeList = boardService.selectBoardNoticeList(board);
			boardList = boardService.selectBoardList(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** 조회 결과를 View에 전달 */
		model.addAttribute("boardList", boardList);
		model.addAttribute("noticeList", noticeList);
		// 사용자가 입력한 검색어를 View에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("keyword", keyword);
		// 페이지 번호 계산 결과를 View에 전달
		model.addAttribute("pageHelper", pageHelper);

		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) * pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);

		return new ModelAndView("bbs/bbs_free_list");
	}

	/** 자유 게시글 보기 */
	@RequestMapping(value = "/bbs/bbs_free_view.do", method = RequestMethod.GET)
	public ModelAndView bbsFreeView(Locale locale, Model model) {
		/** WebHelper 초기화 */
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		model.addAttribute("category", category);

		/** 글 번호 파라미터 받기 */
		int idboard = web.getInt("idboard");

		if (idboard == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}

		// 파라미터를 Beans로 묶기
		Board board = new Board();
		board.setIdboard(idboard);
		board.setCategory(category);

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Board readBoard = null;
		Board prevBoard = null;
		Board nextBoard = null;

		// 로그인 한 경우, 입력하지 않은 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo != null) {
			if (loginInfo.getEmail().equals("admin")) {
				try {
					Board writer_info = adminService.selectBoardByAdmin(board);
					board.setWriter_nickname(writer_info.getWriter_nickname());
					board.setWriter_pw(writer_info.getWriter_pw());
					
				} catch (Exception e) {
					return web.redirect(null, "수정권한이 없습니다.");
				}
			} else if (!loginInfo.getEmail().equals("admin")) {
				board.setWriter_nickname(loginInfo.getNickname());
				board.setWriter_pw(loginInfo.getPassword());
				board.setMember_id(loginInfo.getId());
			}

		}
		
		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		try {
			boardCount = boardService.selectBoardCountByMemberId(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		if(loginInfo != null) {
			if (loginInfo.getEmail().equals("admin")) {
				boardCount = 1;
			}
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);

		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		// ex) board_notice_15
		String cookieKey = "board_" + category + "_" + idboard;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);

		try {
			// 쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				boardService.updateBoardHit(board);
				// 준비한 문자열에 대한 쿠키를 24시간동안 저장
				web.setCookie(cookieKey, "Y", 60 * 60 * 24);
			}
			readBoard = boardService.selectBoard(board);
			prevBoard = boardService.selectPrevBoard(board);
			nextBoard = boardService.selectNextBoard(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		readBoard.setContent(readBoard.getContent().replace("&lt;br/&gt;", "<br/>"));

		/** (7) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("readBoard", readBoard);
		model.addAttribute("prevBoard", prevBoard);
		model.addAttribute("nextBoard", nextBoard);

		return new ModelAndView("bbs/bbs_free_view");
	}

	/** 공지 보기 */
	@RequestMapping(value = "/bbs/bbs_notice_view.do", method = RequestMethod.GET)
	public ModelAndView bbsNoticeView(Locale locale, Model model) {
		/** WebHelper 초기화 */
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		model.addAttribute("category", category);

		/** 글 번호 파라미터 받기 */
		int idboard = web.getInt("idboard");

		if (idboard == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		// file 리스트 불러오기
		File file = new File();
		file.setBoard_idboard(idboard);
		List<File> fileList = null;
		try {
			fileList = fileService.selectFileList(file);
		} catch (Exception e) {
			return web.redirect(null, "업로드된 파일 조회에 실패했습니다.");
		}
		logger.info("fileList = " + fileList);
		model.addAttribute("fileList", fileList);

		// 파라미터를 Beans로 묶기
		Board board = new Board();
		board.setIdboard(idboard);
		board.setCategory(category);

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Board readBoard = null;
		Board prevBoard = null;
		Board nextBoard = null;

		// 로그인 한 경우, 입력하지 않은 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			board.setWriter_nickname(loginInfo.getNickname());
			board.setWriter_pw(loginInfo.getPassword());
			board.setMember_id(loginInfo.getId());
		}

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		try {
			boardCount = boardService.selectBoardCountByMemberId(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);

		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		// ex) board_notice_15
		String cookieKey = "board_" + category + "_" + idboard;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);

		try {
			// 쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				boardService.updateBoardHit(board);
				// 준비한 문자열에 대한 쿠키를 24시간동안 저장
				web.setCookie(cookieKey, "Y", 60 * 60 * 24);
			}
			readBoard = boardService.selectBoard(board);
			prevBoard = boardService.selectPrevBoard(board);
			nextBoard = boardService.selectNextBoard(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("readBoard", readBoard);
		model.addAttribute("prevBoard", prevBoard);
		model.addAttribute("nextBoard", nextBoard);

		return new ModelAndView("bbs/bbs_notice_view");
	}

	/** 자유 게시글 작성 */
	@RequestMapping(value = "/bbs/bbs_free_write.do", method = RequestMethod.GET)
	public ModelAndView bbsFreeWrite(Locale locale, Model model) {

		/** WebHelper 초기화 */
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		model.addAttribute("category", category);

		return new ModelAndView("bbs/bbs_free_write");
	}

	/** 자유 게시글 작성 완료 */
	@RequestMapping(value = "/bbs/bbs_free_write_ok.do", method = RequestMethod.POST)
	public ModelAndView bbsFreeWriteOk(Locale locale, Model model) {
		/** WebHelper 초기화 */
		web.init();

		String category = web.getString("category");
		String writer_nickname = web.getString("writer_name");
		String writer_pw = web.getString("writer_pw");
		String subject = web.getString("subject");
		String content = web.getString("content");
		// 작성자 아이피 주소 가져오기
		String ip_address = web.getClientIP();
		// 회원 일련번호 --> 비 로그인인 경우 0
		int member_id = 0;

		writer_nickname = web.convertHtmlTag(writer_nickname);
		subject = web.convertHtmlTag(subject);
		content = web.convertHtmlTag(content);

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		// String category = web.getString("category");
		model.addAttribute("category", category);

		/** 입력받은 파라미터에 대한 유효성 검사 */
		// 이름 검사
		if (!regex.isValue(writer_nickname)) {
			return web.redirect(null, "이름을 입력하세요.");
		}

		// 비밀번호 검사
		if (!regex.isValue(writer_pw)) {
			return web.redirect(null, "비밀번호를 입력하세요.");
		}

		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {
			return web.redirect(null, "글 제목을 입력하세요.");
		}

		if (!regex.isValue(content)) {
			return web.redirect(null, "내용을 입력하세요.");
		}

		/** 입력 받은 파라미터를 Beans로 묶기 */
		Board board = new Board();
		board.setCategory(category);
		board.setWriter_nickname(writer_nickname);
		board.setWriter_pw(writer_pw);
		board.setSubject(subject);
		board.setContent(content);
		board.setIp_address(ip_address);
		board.setMember_id(member_id);

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. (비로그인 시 0으로 설정됨)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			writer_nickname = loginInfo.getNickname();
			writer_pw = loginInfo.getPassword();
			member_id = loginInfo.getId();
			board.setMember_id(loginInfo.getId());
		}

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		try {
			boardCount = boardService.selectBoardCountByMemberId(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);

		try {
			boardService.insertBoard(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		String url = "%s/bbs/bbs_free_view.do?category=%s&idboard=%d";
		url = String.format(url, web.getRootPath(), board.getCategory(), board.getIdboard());

		return web.redirect(url, null);
	}

	/** 자유 게시글 수정 */
	@RequestMapping(value = "/bbs/bbs_free_edit_check.do", method = RequestMethod.POST)
	public ModelAndView bbsFreeEditCheck(Locale locale, Model model) {
		web.init();

		String writer_pw = web.getString("writer_pw");
		String category = web.getString("category");
		int idboard = web.getInt("idboard");

		logger.info("category=" + category + ", writer_pw=" + writer_pw + ", idboard=" + idboard);

		Board board = new Board();
		board.setCategory(category);
		board.setIdboard(idboard);
		board.setWriter_pw(writer_pw);

		Board result = null;

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. (비로그인 시 0으로 설정됨)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			if(!loginInfo.getEmail().equals("admin")) {
				board.setMember_id(loginInfo.getId());
			} else if(loginInfo.getEmail().equals("admin")) {
				try {
					Board writer_info = adminService.selectBoardByAdmin(board);
					board.setMember_id(writer_info.getIdboard());
				} catch (Exception e) {
					web.redirect(null, "관리자권한 게시글 정보 조회에 실패했습니다.");
				}
			}
		} else if (loginInfo == null) {
			try {
				result = boardService.selectBoard(board);
				logger.info("result = " +result);
			} catch (Exception e) {
				web.redirect(null, "게시글 정보 조회에 실패했습니다.");
			}
			Integer k = result.getMember_id();
			logger.info("memberid = "+k);
			if(k !=0) {
				return web.redirect(null, "회원이 작성한 글은 로그인 이후에 수정할 수 있습니다.");
			}
			board.setMember_id(0);
		}
		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		
		try {
			if (loginInfo != null) {
				if (loginInfo.getEmail().equals("admin")) {
					boardCount = 1;				
				} else {
					boardCount = boardService.selectBoardCountByMemberId(board);
				} 
			} 
		} catch (Exception e) {
			return web.redirect(null, "회원이 작성한 글은 회원 로그인 이후에 수정 가능합니다");
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		// 자신의 글이라면 True, 아니면 False
		model.addAttribute("myBoard", myBoard);
		
		try {
			if (!myBoard) {
				if (boardService.selectBoardCountByPw(board) == 1) {
					result = boardService.selectBoard(board);
				} else {
					web.redirect(null, "비밀번호가 틀렸습니다.");
				}
			} else {
				result = boardService.selectBoard(board);
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		result.setContent(result.getContent().replace("&lt;br/&gt;", "\n"));
		
		model.addAttribute("board", result);

		return new ModelAndView("/bbs/bbs_free_edit");
	}

	/** 자유 게시글 수정 완료 */
	@RequestMapping(value = "/bbs/bbs_free_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView bbsFreeEditOk(Locale locale, Model model) {
		web.init();

		int idboard = web.getInt("idboard");
		String category = web.getString("category");
		String writer_nickname = web.getString("writer_nickname");
		String subject = web.getString("subject");
		String content = web.getString("content");
		// 작성자 아이피 주소 가져오기
		String ip_address = web.getClientIP();
		// 회원 일련번호 --> 비 로그인인 경우 0
		
		logger.info("category=" + category + ", idboard=" + idboard);

		writer_nickname = web.convertHtmlTag(writer_nickname);
		subject = web.convertHtmlTag(subject);
		content = web.convertHtmlTag(content);

		/** 입력받은 파라미터에 대한 유효성 검사 */
		// 이름 검사
		if (!regex.isValue(writer_nickname)) {
			return web.redirect(null, "이름을 입력하세요.");
		}

		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {
			return web.redirect(null, "글 제목을 입력하세요.");
		}

		if (!regex.isValue(content)) {
			return web.redirect(null, "내용을 입력하세요.");
		}

		/** 입력 받은 파라미터를 Beans로 묶기 */
		Board board = new Board();
		board.setIdboard(idboard);
		board.setCategory(category);
		board.setWriter_nickname(writer_nickname);
		board.setSubject(subject);
		board.setContent(content);
		board.setIp_address(ip_address);

		// 로그인 한 경우, 입력하지 않은 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			if(!loginInfo.getEmail().equals("admin")) {
				writer_nickname = loginInfo.getNickname();
				board.setMember_id(loginInfo.getId());
			} else {
				int memberid = 0;
				try {
					Board result = adminService.selectBoardByAdmin(board);
					memberid = result.getMember_id();
				} catch (Exception e) {
					return web.redirect(null, "관리자권한 게시글정보조회에 실패했습니다.");
				}
				board.setMember_id(memberid);
			}
			
		} else if(loginInfo == null) {
			board.setMember_id(0);			
		}

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		

		int boardCount = 0;
		try {
			if (loginInfo != null) {
				if(!loginInfo.getEmail().equals("admin")) {
					boardCount = boardService.selectBoardCountByMemberId(board);
				} else if(loginInfo.getEmail().equals("admin")){
					boardCount = 1;
				}				
			} 
		} catch (Exception e) {
			
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);
		
		logger.info("boardcontent = "+ board);
		/** Service를 통한 게시물 저장 */
		try {

			boardService.updateBoard(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		String url = "%s/bbs/bbs_free_view.do?category=%s&idboard=%d";
		url = String.format(url, web.getRootPath(), board.getCategory(), board.getIdboard());

		return web.redirect(url, null);

	}

	/** 자유 게시글 삭제 */
	@RequestMapping(value = "/bbs/bbs_free_delete.do", method = RequestMethod.POST)
	public ModelAndView bbsFreeDelete(Locale locale, Model model) {
		web.init();

		/** 게시판 카테고리 값을 받아서 View에 전달 */
		String category = web.getString("category");
		int idboard = web.getInt("idboard");
		String writer_pw = web.getString("writer_pw");

		Board board = new Board();
		board.setCategory(category);
		board.setIdboard(idboard);
		board.setWriter_pw(writer_pw);
		
		Board result =null;
		try {
			result = boardService.selectBoard(board);
		} catch (Exception e) {
			web.redirect(null, "게시물 정보조회에 실패했습니다.");
		}

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. (비로그인 시 0으로 설정됨)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			if(loginInfo.getId() == 1 ) {
				int memberid = result.getMember_id();
				board.setMember_id(memberid);
			} else {
				board.setMember_id(loginInfo.getId());
			}
			
		} else if (loginInfo == null) {			
			Integer membercheck = result.getMember_id();
			if(membercheck != null) {
				return web.redirect(null, "회원이 작성한 글은 로그인 이후에 삭제 가능합니다.");
			}
		}

		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int boardCount = 0;
		try {
			boardCount = boardService.selectBoardCountByMemberId(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		// 게시물에 속한 덧글 삭제를 위해서 생성
		Comment comment = new Comment();
		comment.setBoard_idboard(idboard);

		// 게시물에 속한 파일 삭제를 위해서 생성
		File file = new File();
		file.setBoard_idboard(idboard);
		List<File> fileList = null;
		try {
			fileList = fileService.selectFileList(file);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		for (int i = 0; i < fileList.size(); i++) {
			String file_dir = fileList.get(i).getFile_dir();
			String file_name = fileList.get(i).getFile_name();
			upload.removeFile(file_dir + "/" + file_name);
		}

		/** (7) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myBoard = boardCount > 0;
		model.addAttribute("myBoard", myBoard);
		logger.info("memberid = "+board.getMember_id());
		try {
			if (boardService.selectBoardCountByMemberId(board) < 1) {
				if(loginInfo.getId()!=1) {
					boardService.selectBoardCountByPw(board);
				}				
			}
			fileService.deleteFileAll(file);
			commentService.deleteCommentAll(comment);
			boardService.deleteBoard(board);

		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		String url = "%s/bbs/bbs_free_list.do?category=%s";
		url = String.format(url, web.getRootPath(), category);

		return web.redirect(url, "삭제 성공!");
	}

	/** 관광지 게시글 보기 */
	@RequestMapping(value = "/bbs/bbs_area_list.do", method = RequestMethod.GET)
	public String bbsAreaList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		return "bbs/bbs_area_list";
	}

	/** 관광지 게시글 조회 */
	@RequestMapping(value = "/bbs/bbs_area_item.do", method = RequestMethod.GET)
	public ModelAndView areaItem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		web.init();

		String areaCode = web.getString("areaCode");
		String sigunguCode = web.getString("sigunguCode");

		if (!regex.isNum(areaCode) || areaCode.equals("0")) {

			return null;
		}

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

		logger.info("areaCode=" + areaCode + ",sigunguCode=" + sigunguCode + ",page=" + page);

		try {
			if (sigunguCode != null) {
				String result = api.areaCode(areaCode, sigunguCode, page);

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(result);
				JSONObject jsonObj = (JSONObject) obj;

				JSONObject personObject = (JSONObject) jsonObj.get("response");
				JSONObject personObject_str = (JSONObject) personObject.get("body");
				Object totalCount_obj = personObject_str.get("totalCount");

				// 이미지가 존재하는 게시물인지 판별
				JSONObject items = (JSONObject) personObject_str.get("items");
				JSONArray item = (JSONArray) items.get("item");

				logger.info("item >>" + item);

				for (int i = 0; i < item.size(); i++) {
					Object item_result = item.get(i);
					JSONObject item_json = (JSONObject) item_result;
					logger.info("item_json >>" + item_json.get("firstimage2"));
				}

				// 전체 데이터 수
				int totalCount = Integer.parseInt(totalCount_obj.toString());
				logger.info("totalCount=" + totalCount);

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("rt", "ok");
				data.put("result", jsonObj);
				data.put("page", page);
				data.put("totalCount", totalCount);

				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), data);

			} else {
				String result = api.areaCode(areaCode, page);

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(result);
				JSONObject jsonObj = (JSONObject) obj;

				JSONObject personObject = (JSONObject) jsonObj.get("response");
				JSONObject personObject_str = (JSONObject) personObject.get("body");
				Object totalCount_obj = personObject_str.get("totalCount");

				// 전체 데이터 수
				int totalCount = Integer.parseInt(totalCount_obj.toString());
				logger.info("totalCount=" + totalCount);

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("rt", "ok");
				data.put("result", jsonObj);
				data.put("page", page);
				data.put("totalCount", totalCount);

				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), data);
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}

		return null;
	}

	/** 관광지 게시글 모달 */
	@RequestMapping(value = "/bbs/bbs_area_modal_item.do", method = RequestMethod.GET)
	public String areaModal(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		web.init();

		String contentId = web.getString("contentId");

		if (contentId == "null") {
			return null;
		}

		String result;

		try {
			result = api.information(contentId, 1);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(result);
			JSONObject jsonObj = (JSONObject) obj;

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("rt", "ok");
			data.put("result", jsonObj);

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}

		return null;
	}

	/**
	 * 관광지 검색
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/bbs/bbs_area_key.do", method = RequestMethod.GET)
	public String bbsAreaKey_search(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json");
		web.init();

		String key = web.getString("keyword");
		String result;

		if (key == "null") {
			return null;
		}
		try {
			result = api.keyCode(key, 1);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(result);
			JSONObject jsonObj = (JSONObject) obj;

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("rt", "ok");
			data.put("result", jsonObj);

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
		return null;
	}

	/** SearchBar 키워드 검색 */
	@RequestMapping(value = "/bss/bbs_search_keyword.do", method = RequestMethod.GET)
	public ModelAndView bbsSearchKeyResult(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		web.init();

		String keyword = web.getString("search_keyword");

		if (keyword == null) {
			return web.redirect(null, "검색어를 입력해주세요.");
		}

		model.addAttribute("keyword", keyword);

		Board_Schedule schedule = new Board_Schedule();
		schedule.setSubject(keyword);

		List<Board_Schedule> scheduleList = null;

		try {
			scheduleList = boardService.selectKeywordBoardList(schedule);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

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

		String view = "/bbs/bbs_search_keyword";

		return new ModelAndView(view);
	}

	/** SearchBar 테마 검색 */
	@RequestMapping(value = "/bss/bbs_search_theme.do", method = RequestMethod.GET)
	public ModelAndView bbsSearchThemaResult(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		web.init();

		int theme = web.getInt("theme");
		String sort = web.getString("sort");

		logger.info("sort=" + sort);

		String thema = null;

		if (theme == 1) {
			thema = "식도락";
		} else if (theme == 2) {
			thema = "쇼핑";
		} else if (theme == 3) {
			thema = "명소";
		} else if (theme == 4) {
			thema = "휴양";
		}

		model.addAttribute("theme", theme);
		model.addAttribute("thema", thema);

		Board_Schedule schedule = new Board_Schedule();
		schedule.setTheme(theme);
		if (regex.isValue(sort)) {
			model.addAttribute("sort", true);
			schedule.setSort(true);
		} else {
			model.addAttribute("sort", false);
			schedule.setSort(false);
		}

		List<Board_Schedule> scheduleList = null;

		if (web.getString("keyword") != null) {
			String keyword = web.getString("keyword"); 
			schedule.setSubject(keyword);
			model.addAttribute("keyword", keyword);

			try {
				scheduleList = boardService.selectKeywordBoardList(schedule);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		} else {
			try {
				scheduleList = boardService.selectThemeBoardList(schedule);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}

		logger.info("scheduleList >>" + scheduleList);

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

		String view = "/bbs/bbs_search_theme";

		return new ModelAndView(view);
	}

}
