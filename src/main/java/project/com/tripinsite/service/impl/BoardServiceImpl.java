package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Board_Schedule;
import project.com.tripinsite.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	protected static Logger logger = LoggerFactory.getLogger("BoardServiceImpl");

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Board_Schedule> selectThemeBoardList(Board_Schedule board_Schedule) throws Exception {
		List<Board_Schedule> result = null;

		try {
			result = sqlSession.selectList("BoardSchedulMapper.selectThemeBoardList", board_Schedule);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Board_Schedule> selectKeywordBoardList(Board_Schedule board_Schedule) throws Exception {
		List<Board_Schedule> result = null;

		try {
			result = sqlSession.selectList("BoardSchedulMapper.selectKeywordBoardList", board_Schedule);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<Board_Schedule> selectStoryBoardList(Board_Schedule board_Schedule) throws Exception {
		List<Board_Schedule> result = null;

		try {
			result = sqlSession.selectList("BoardSchedulMapper.selectStoryBoardList", board_Schedule);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board_Schedule selectStoryBoardView(Board_Schedule board_Schedule) throws Exception {
		Board_Schedule result = null;

		try {
			result = sqlSession.selectOne("BoardSchedulMapper.selectStoryBoardView", board_Schedule);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board_Schedule selectPrevStoryBoard(Board_Schedule board_Schedule) throws Exception {
		Board_Schedule result = null;

		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("BoardSchedulMapper.selectPrevStoryBoard", board_Schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("이전글 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board_Schedule selectNextStoryBoard(Board_Schedule board_Schedule) throws Exception {
		Board_Schedule result = null;

		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("BoardSchedulMapper.selectNextStoryBoard", board_Schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("다음글 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<Board> selectBoardList(Board board) throws Exception {
		List<Board> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.selectBoardList", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 자유게시글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<Board> selectBoardNoticeList(Board board) throws Exception {
		List<Board> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.selectBoardNoticeList", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 공지글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void insertBoard(Board board) throws Exception {
		try {
			int result = sqlSession.insert("BoardMapper.insertBoard", board);

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
	public Board selectBoard(Board board) throws Exception {
		Board result = null;

		try {
			result = sqlSession.selectOne("BoardMapper.selectBoard", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board selectBoardNotice(Board board) throws Exception {
		Board result = null;

		try {
			result = sqlSession.selectOne("BoardMapper.selectBoardNotice", board);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 공지가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("공지 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board selectPrevBoard(Board board) throws Exception {
		Board result = null;

		try {
			// 이전글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectPrevBoard", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("이전글 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Board selectNextBoard(Board board) throws Exception {
		Board result = null;

		try {
			// 다음글이 없는 경우도 있으므로, 리턴값이 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectNextBoard", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("다음글 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void updateBoardHit(Board board) throws Exception {
		try {
			int result = sqlSession.update("BoardMapper.updateBoardHit", board);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("조회수 갱신에 실패했습니다.");
		}
	}

	@Override
	public int selectBoardCount(Board board) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectBoardCount", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectBoardCountByMemberId(Board board) throws Exception {
		int result = 0;

		try {
			// 자신의 게시물이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectBoardCountByMemberId", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectBoardCountByPw(Board board) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("BoardMapper.selectBoardCountByPw", board);
			// 비밀번호가 일치하는 데이터의 수가 0이라면 비밀번호가 잘못된 것으로 간주한다.
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("비밀번호를 확인하세요.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void deleteBoard(Board board) throws Exception {
		try {
			int result = sqlSession.delete("BoardMapper.deleteBoard", board);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateBoard(Board board) throws Exception {
		try {
			int result = sqlSession.update("BoardMapper.updateBoard", board);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수정에 실패했습니다.");
		}
	}

	@Override
	public void updateBoardMemberOut(Board board) throws Exception {
		try {
			// 게시글을 작성한 적이 없는 회원도 있을 수 있기 때문에,
			// NullPointerException을 발생시키지 않는다.
			sqlSession.update("BoardMapper.updateBoardMemberOut", board);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public void updateStoryBoard(Board_Schedule board_schedule) throws Exception {
		try {
			int result = sqlSession.update("BoardSchedulMapper.updateStoryBoard", board_schedule);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수정내용이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수정에 실패했습니다.");
		}

	}

	@Override
	public int selectMyScheduleCount(Board_Schedule board_Schedule) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardSchedulMapper.selectMyScheduleCount", board_Schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<Board_Schedule> selectMyStoryBoardList(Board_Schedule board_Schedule) throws Exception {
		List<Board_Schedule> result = null;

		try {
			result = sqlSession.selectList("BoardSchedulMapper.selectMyStoryBoardList", board_Schedule);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int insertStoryBoard(Board board) throws Exception {
		int result = 0;
		try {
			sqlSession.insert("BoardMapper.insertStoryBoard", board);
			result = board.getIdboard();
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 스토리 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("스토리 게시물 정보 등록에 실패했습니다.");
		} // try~catch
		return result;
	}

	@Override	
	public int selectStoryCountByPw(Board_Schedule board_Schedule) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BoardSchedulMapper.selectScheduleCountPassword", board_Schedule);
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("비밀번호 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void deleteStory(Board_Schedule board_Schedule) throws Exception {
		try {
			sqlSession.delete("BoardSchedulMapper.deleteSche_table",board_Schedule);
			sqlSession.delete("BoardSchedulMapper.deleteLove",board_Schedule);
			sqlSession.delete("BoardSchedulMapper.deleteSchedule",board_Schedule);
			sqlSession.delete("BoardSchedulMapper.deleteComment",board_Schedule);
			sqlSession.delete("BoardSchedulMapper.deleteFile",board_Schedule);
			sqlSession.delete("BoardSchedulMapper.deleteBoard",board_Schedule);
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 삭제에 실패했습니다.");
		}
		
	}

	@Override
	public int selectScheduleCountByMemberId(Board_Schedule board_Schedule) throws Exception {
		int result = 0;

		try {
			// 자신의 게시물이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardSchedulMapper.selectScheduleCountByMemberId", board_Schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectScheduleCount(Board_Schedule board_Schedule) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardSchedulMapper.selectScheduleCount", board_Schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}
}
