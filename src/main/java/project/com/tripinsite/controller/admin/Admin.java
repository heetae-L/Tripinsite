package project.com.tripinsite.controller.admin;

import java.io.UnsupportedEncodingException;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import project.com.tripinsite.helper.FileInfo;
import project.com.tripinsite.helper.PageHelper;
import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.WebHelper;
import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Comment;
import project.com.tripinsite.model.File;
import project.com.tripinsite.model.Love;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.model.Schedule;
import project.com.tripinsite.service.AdminService;
import project.com.tripinsite.service.BoardService;
import project.com.tripinsite.service.CommentService;
import project.com.tripinsite.service.FileService;
import project.com.tripinsite.service.LoveService;
import project.com.tripinsite.service.MemberService;
import project.com.tripinsite.service.ScheduleService;

@Controller
public class Admin {

	protected static Logger logger = LoggerFactory.getLogger("Admin");

	@Autowired
	WebHelper web;
	@Autowired
	MemberService memberService;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	SqlSession sqlSession;
	@Autowired
	AdminService adminService;
	@Autowired
	UploadHelper upload;
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

	/** 관리자 게시판으로 이동 */
	@RequestMapping(value = "/admin/admin_board.do", method = RequestMethod.GET)

	public ModelAndView admin_board(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		web.init();
		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			return web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member membercheck = (Member) web.getSession("loginInfo");
		if (!membercheck.getEmail().equals("admin")) {
			return web.redirect("/tripinsite", "잘못된 접근입니다.");
		}

		/** (3)조회할 게시물 정보에 대한 Beans 생성 */
		Board board = new Board();

		// 검색어
		String keyword = web.getString("keyword");
		int page = web.getInt("page", 1);
		board.setContent(keyword);
		board.setSubject(keyword);

		/** (4) 게시물 목록 조회 */
		int totalCount = 0;
		List<Board> boardList = null;
		try {
			// 전체 게시물 수
			totalCount = adminService.selectboardCount(board);

			// 현재페이지, 전체 게시물 수, 한 페이지의 목록 수 , 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);

			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			board.setLimitStart(pageHelper.getLimitStart());
			board.setListCount(pageHelper.getListCount());

			boardList = adminService.selectBoardList(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (5) 조회 결과를 View에 전달 */
		request.setAttribute("boardList", boardList);
		request.setAttribute("keyword", keyword);
		request.setAttribute("pageHelper", pageHelper);

		return new ModelAndView("admin/admin_board");
	}

	/** 회원관리 게시판으로 이동 */
	@RequestMapping(value = "/admin/admin_member.do", method = RequestMethod.GET)
	public ModelAndView admin_member(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** (1) WebHelper 초기화 */
		web.init();

		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			return web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member membercheck = (Member) web.getSession("loginInfo");
		Member member = new Member();

		if (!membercheck.getEmail().equals("admin")) {
			return web.redirect("/tripinsite", "잘못된 접근입니다.");
		}
		/** (3)조회할 회원 정보에 대한 Beans 생성 */
		// 검색어
		String keyword = web.getString("keyword");
		int page = web.getInt("page", 1);
		member.setNickname(keyword);
		member.setEmail(keyword);

		/** (4) 회원 목록 조회 */
		int totalCount = 0;
		List<Member> memberList = null;
		try {
			// 전체 게시물 수
			totalCount = adminService.selectMemberCount(member);

			// 현재페이지, 전체 게시물 수, 한 페이지의 목록 수 , 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);

			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			member.setLimitStart(pageHelper.getLimitStart());
			member.setListCount(pageHelper.getListCount());

			memberList = memberService.selectMemberList(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (5) 조회 결과를 View에 전달 */
		request.setAttribute("memberList", memberList);
		request.setAttribute("keyword", keyword);
		request.setAttribute("pageHelper", pageHelper);

		return new ModelAndView("admin/admin_member");
	}

	/** 공지사항 게시판 작성으로 이동 */
	@RequestMapping(value = "/admin/admin_write.do", method = RequestMethod.GET)
	public ModelAndView admin_write(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		web.init();

		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			return web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member member = (Member) web.getSession("loginInfo");
		if (!member.getEmail().equals("admin")) {
			return web.redirect("/tripinsite", "잘못된 접근입니다.");
		}

		return new ModelAndView("admin/admin_write");
	}

	/** 공지사항 작성 처리 */
	@RequestMapping(value = "/admin/admin_write_ok.do", method = RequestMethod.POST)
	public ModelAndView writeNotice(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** (1) WebHelper 초기화 */
		web.init();

		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member member = (Member) web.getSession("loginInfo");
		if (!member.getEmail().equals("admin")) {
			web.redirect("/tripinsite", "잘못된 접근입니다.");
		}
		/** (3) Map을 리턴받아서 값을 추출한다. */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, "잘못된 데이터입니다.");
		}

		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		Map<String, String> paramMap = upload.getParamMap();
		String category = paramMap.get("category");
		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		String ip_address = web.getClientIP();
		String writer_pw = member.getPassword();

		/** (4) 전달받은 파라미터 Beans 객체에 담는다. */
		Board board = new Board();
		board.setCategory(category);
		board.setSubject(subject);
		board.setWriter_nickname("admin");
		board.setContent(content);
		board.setIp_address(ip_address);
		board.setIs_notice(1);
		board.setMember_id(1);
		board.setWriter_pw(writer_pw);

		/** (5) Service를 통한 DB 저장 처리 */
		int board_idboard = 0;
		try {
			adminService.insertBoard(board);
			board_idboard = adminService.selectBoardId();
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		logger.info("fileList = " + fileList);

		for (int i = 0; i < fileList.size(); i++) {
			String origin_name = fileList.get(i).getOriginName();
			String file_dir = fileList.get(i).getFileDir();
			String file_name = fileList.get(i).getFileName();
			String content_type = fileList.get(i).getContentType();
			long file_size = fileList.get(i).getFileSize();

			// file Beans에 정보 저장
			File file = new File();
			file.setOrigin_name(origin_name);
			file.setFile_dir(file_dir);
			file.setFile_name(file_name);
			file.setContent_type(content_type);
			file.setFile_size(file_size);
			file.setBoard_idboard(board_idboard);

			try {
				fileService.insertFile(file);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				upload.removeFile(file_dir + "/" + file_name);
				return web.redirect(null, "파일저장에 실패했습니다.");
			}
		}

		return web.redirect(web.getRootPath() + "/admin/admin_board.do", "공지사항이 작성되었습니다.");
	}

	/** 회원 정보 조회 */
	@RequestMapping(value = "/admin/admin_member_view.do", method = RequestMethod.POST)
	public void viewModal(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/** (1) WebHelper 초기화 */
		web.init();
		response.setContentType("application/json");

		/** (2) ^오^ 문지기 */
		Member member2 = (Member) web.getSession("loginInfo");
		if (web.getSession("loginInfo") == null) {
			Map<String, Object> data2 = new HashMap<String, Object>();
			data2.put("rt", "로그인이 필요합니다..");
			ObjectMapper mapper2 = new ObjectMapper();
			mapper2.writeValue(response.getWriter(), data2);
			return;
		}
		if (!member2.getEmail().equals("admin")) {
			Map<String, Object> data2 = new HashMap<String, Object>();
			data2.put("rt", "접근권한이 없습니다. 돌아가세요~");
			ObjectMapper mapper2 = new ObjectMapper();
			mapper2.writeValue(response.getWriter(), data2);
			return;
		}

		/** (2) id 값을 받아서 View에 전달 */
		int id = web.getInt("id");

		/** (3) 파라미터를 담을 Beans 생성 */
		Member member = new Member();
		member.setId(id);
		Member memberRead = null;
		try {
			memberRead = adminService.selectMember(member);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		System.out.println(memberRead);
		/** (4) 읽은 데이터를 모달에게 전달한다. */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", memberRead);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 회원 삭제 */
	@RequestMapping(value = "/admin/admin_member_delete.do", method = RequestMethod.GET)
	public ModelAndView admin_delete(Locale locale, Model model) {
		/** (1) WebHelper 초기화 */
		web.init();
		int id = web.getInt("id");

		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			return web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member member = (Member) web.getSession("loginInfo");
		if (!member.getEmail().equals("admin")) {
			return web.redirect("/tripinsite", "잘못된 접근입니다.");
		}

		/** (3) 회원삭제처리 */
		Member member2 = new Member();
		member2.setId(id);
		Comment comment = new Comment();
		comment.setMember_id(id);
		Board board = new Board();
		board.setMember_id(id);
		Schedule sche = new Schedule();
		sche.setMember_id(id);
		Love love = new Love();
		love.setMember_id(id);
		try {
			commentService.updateCommentMemberOut(comment);
			boardService.updateBoardMemberOut(board);
			scheduleService.updateScheMemberOut(sche);
			loveService.updateLoveMemberOut(love);
			memberService.deleteMember(member2);

		} catch (Exception e) {
			return web.redirect(null, "회원삭제에 실패했습니다.");
		}

		return web.redirect(web.getRootPath() + "/admin/admin_member.do", "회원정보가 삭제되었습니다.");
	}

	/** 회원 수정 */
	@RequestMapping(value = "/admin/admin_member_update.do", method = RequestMethod.POST)
	public void admin_edit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (1) WebHelper 초기화 */
		response.setContentType("application/json");
		web.init();

		/** (2) 관리자계정인지 검사하기. */
		if (web.getSession("loginInfo") == null) {
			web.redirect("/tripinsite", "로그인이 필요합니다.");
		}
		Member member2 = (Member) web.getSession("loginInfo");
		if (!member2.getEmail().equals("admin")) {
			web.redirect("/tripinsite", "잘못된 접근입니다.");
		}
		/** (3) 모달에서 전달받은 값 Beans에 담기 */
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String idk = web.getString("id");
		int id = Integer.parseInt(idk);
		String nickname = web.getString("nickname");
		String age = web.getString("age");
		String gender = web.getString("gender");
		String postcode = web.getString("postcode");
		String addr1 = web.getString("addr1");
		String addr2 = web.getString("addr2");

		Member member = new Member();
		member.setId(id);
		member.setNickname(nickname);
		member.setAge(age);
		member.setGender(gender);
		member.setPostcode(postcode);
		member.setAddr1(addr1);
		member.setAddr2(addr2);
		logger.debug(nickname);
		/** (4) Service를 통한 회원정보 수정처리 */
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			adminService.updateMember(member);
			data.put("rt", "ok");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			data.put("rt", "error1");
			return;
		}

		/** (5) 처리 완료 후 페이지 이동 */
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return;
		}
	}

	/** 공지사항 수정 */
	@RequestMapping(value = "/admin/admin_notice_edit.do", method = RequestMethod.POST)
	public ModelAndView AdminNoticeEdit(Locale locale, Model model) {
		web.init();

		String category = web.getString("category");
		int idboard = web.getInt("idboard");

		Board board = new Board();
		board.setCategory(category);
		board.setIdboard(idboard);
		logger.info("idboardk=" + idboard);
		Board result = null;
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

		// 관리자외 접근 금지 설정
		Member loginInfo = (Member) web.getSession("loginInfo");

		try {
			if (!loginInfo.getEmail().equals("admin")) {
				return web.redirect(null, "일반사용자는 공지사항을 수정할 수 없습니다.");
			}
		} catch (NullPointerException e) {
			return web.redirect(null, "로그인이 필요합니다.");
		} catch (Exception e) {
			return web.redirect(null, "페이지 로딩에 실패했습니다.");
		}

		// 수정페이지에 전달할 데이터 조회
		try {
			result = boardService.selectBoard(board);
		} catch (Exception e) {
			return web.redirect(null, "공지사항 데이터 조회에 실패했습니다.");
		}

		model.addAttribute("board", result);

		return new ModelAndView("/admin/admin_notice_edit");
	}

	/** 공지사항 수정 완료 */
	@RequestMapping(value = "/admin/admin_notice_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView AdminNoticeEditOk(Locale locale, Model model) {
		web.init();

		/** Map을 리턴받아서 값을 추출한다. */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return web.redirect(null, "잘못된 데이터입니다.");
		}

		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		Map<String, String> paramMap = upload.getParamMap();
		String category = paramMap.get("category");
		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		String idboard1 = paramMap.get("idboard");
		int idboard = Integer.parseInt(idboard1);
		logger.info("parseid" + idboard);

		/** 입력 받은 파라미터를 Beans로 묶기 */
		Board board = new Board();
		board.setIdboard(idboard);
		board.setCategory(category);
		board.setSubject(subject);
		board.setContent(content);

		Member loginInfo = (Member) web.getSession("loginInfo");
		try {
			if (!loginInfo.getEmail().equals("admin")) {
				return web.redirect(null, "관리자만 접근할 수 있습니다.");
			}
		} catch (NullPointerException e) {
			return web.redirect(null, "로그인이 필요합니다.");
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** Service를 통한 게시물 저장 */
		try {
			adminService.updateNotice(board);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		logger.info("fileList = " + fileList);

		for (int i = 0; i < fileList.size(); i++) {
			String origin_name = fileList.get(i).getOriginName();
			String file_dir = fileList.get(i).getFileDir();
			String file_name = fileList.get(i).getFileName();
			String content_type = fileList.get(i).getContentType();
			long file_size = fileList.get(i).getFileSize();

			// file Beans에 정보 저장
			File file = new File();
			file.setOrigin_name(origin_name);
			file.setFile_dir(file_dir);
			file.setFile_name(file_name);
			file.setContent_type(content_type);
			file.setFile_size(file_size);
			file.setBoard_idboard(idboard);

			try {
				fileService.insertFile(file);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				upload.removeFile(file_dir + "/" + file_name);
				return web.redirect(null, "파일저장에 실패했습니다.");
			}
		}

		String url = "%s/bbs/bbs_notice_view.do?category=%s&idboard=%d";
		url = String.format(url, web.getRootPath(), board.getCategory(), board.getIdboard());

		return web.redirect(url, null);

	}
	
	/** 파일 개별 삭제  */
	@RequestMapping(value = "/board/board_notice_file_delete.do", method = RequestMethod.GET)
	public void file_delete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (1) WebHelper 초기화 */
		web.init();
		int idfile = web.getInt("idfile");
		logger.info("idfile="+idfile);
		
		/** (3) 파일삭제처리 */
		File file = new File();
		File file2 = new File();
		file.setIdfile(idfile);
		
		try {
			file2 = fileService.selectFile(file);
		} catch (Exception e1) {
			web.redirect(null, "파일정보조회에 실패했습니다.");
		}
		String filePath = file2.getFile_dir();
		String fileName = file2.getFile_name();
		logger.info("fileName = " + fileName);
		file2.setIdfile(idfile);
		file2.setFile_dir(filePath);
		file2.setFile_name(fileName);
		try {
			fileService.deleteFile(file);
			upload.removeFile(filePath+"/"+fileName);
		} catch (Exception e) {
			web.redirect(null, "파일삭제에 실패했습니다.");
		}
	}
	

}
