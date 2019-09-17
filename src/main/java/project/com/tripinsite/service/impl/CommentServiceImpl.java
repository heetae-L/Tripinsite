package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Comment;
import project.com.tripinsite.service.CommentService;

@Service

public class CommentServiceImpl implements CommentService {

	protected static Logger logger = LoggerFactory.getLogger("CommentServiceImpl");

	/** MyBatis */
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Comment> selectCommentList(Comment comment) throws Exception {
		List<Comment> result = null;

		try {
			result = sqlSession.selectList("CommentMapper.selectCommentList", comment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public Comment selectComment(Comment comment) throws Exception {
		Comment result = null;

		try {
			result = sqlSession.selectOne("CommentMapper.selectComment", comment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void insertComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.insert("CommentMapper.insertComment", comment);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 정보 등록에 실패했습니다.");
		}
	}

	@Override
	public int selectCommentCountByMemberId(Comment comment) throws Exception {
		int result = 0;

		try {
			// 자신의 덧글이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("CommentMapper.selectCommentCountByMemberId", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectCommentCountByPw(Comment comment) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("CommentMapper.selectCommentCountByPw", comment);
			// 비밀번호가 일치하는 데이터의 수가 0이라면 비밀번호가 잘못된 것으로 간주한다.
			if (result < 1) {
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
	public void deleteComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.delete("CommentMapper.deleteComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.update("CommentMapper.updateComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수정에 실패했습니다.");
		}
	}

	@Override
	public void deleteCommentAll(Comment comment) throws Exception {
		try {
			// 덧글이 존재하지 않는 게시물에 대한 요청일 수 있으므로,
			// NullPointerException을 발생시키지 않는다.
			sqlSession.delete("CommentMapper.deleteCommentAll", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateCommentMemberOut(Comment comment) throws Exception {
		try {
			// 덧글을 작성한 적이 없는 회원도 있을 수 있기 때문에,
			// NullPointerException을 발생시키지 않는다.
			sqlSession.update("CommentMapper.updateCommentMemberOut", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
	}

}
