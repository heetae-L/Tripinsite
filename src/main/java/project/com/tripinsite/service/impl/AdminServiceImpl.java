package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	/** 처리 결과를 기록할 Log4J 객체 생성 */
	protected static Logger logger = LoggerFactory.getLogger("AdminServiceImpl");

	/** MyBatis */
	@Autowired
	private SqlSession sqlSession;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 관리자페이지 회원관리 ~~~~~~~~~~~~~~~~~~~~
	/** 관리자페이지 회원관리 */
	@Override
	public Member selectMember(Member member) throws Exception {
		Member result = null;
		try {
			result = sqlSession.selectOne("MemberMapper.selectMember", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 회원이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Member selectPrevMember(Member member) throws Exception {
		Member result = null;
		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("MemberMapper.selectPrevMember", member);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("이전회원목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public Member selectNextMember(Member member) throws Exception {
		Member result = null;
		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("MemberMapper.selectNextMember", member);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("다음회원 목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectMemberCount(Member member) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("MemberMapper.selectMemberCount", member);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void updateMember(Member member) throws Exception {
		try {
			int result = sqlSession.update("MemberMapper.updateMemberbyAdmin", member);
			if(result ==0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 회원 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원정보 수정에 실패했습니다.");
		}
		
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 관리자페이지 게시물관리 ~~~~~~~~~~~~~~~~~~~~

	@Override
	public int selectboardCount(Board board) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectBoardCountByAdmin", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<Board> selectBoardList(Board board) throws Exception {
		List<Board> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.selectBoardListByAdmin", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 게시물 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void insertBoard(Board board) throws Exception {
		try {
			int result = sqlSession.insert("BoardMapper.insertBoardByAdmin", board);
			
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 정보 등록에 실패했습니다.");
		}
		
	}

	@Override
	public int selectBoardId() throws Exception {
		int idnum = 0;
		try {
			idnum = sqlSession.selectOne("BoardMapper.selectBoardid");		
					
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 정보 조회에 실패했습니다.");
		}	
		return idnum;
	}

	@Override
	public Board selectBoardByAdmin(Board board) throws Exception {
		Board result = null;
		try {
			result = sqlSession.selectOne("BoardMapper.selectBoardByAdmin", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 게시글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void updateNotice(Board board) throws Exception {
		try {
			int result = sqlSession.update("BoardMapper.updateNotice", board);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("공지사항 수정에 실패했습니다.");
		}
		
	}

	
	
}
