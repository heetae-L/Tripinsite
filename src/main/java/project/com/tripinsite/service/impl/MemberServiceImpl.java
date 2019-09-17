package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Board_Love;
import project.com.tripinsite.model.Member;
import project.com.tripinsite.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	protected static Logger logger = LoggerFactory.getLogger("MemberServiceImpl");

	@Autowired
	SqlSession sqlSession;

	/** 회원가입 */
	@Override
	public void insertMember(Member member) throws Exception {
		//  이메일 중복검사  및 닉네임 중복검사 호출
		selectEmailCount(member);
		selectNicknameCount(member);

		// 데이터 저장처리 = 가입
		// not null로 설정된 값이 설정되지 않았다면 예외 발생
		try {
			int result = sqlSession.insert("MemberMapper.insertMember", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 회원정보가 없습니다.");
		} catch (Exception e) {

			logger.error(e.getLocalizedMessage());
			throw new Exception("회원가입에 실패했습니다.");
		}
	}
/** 로그인 처리를 위한 단일 정보 조회 */
	@Override
	public Member selectLoginInfo(Member member) throws Exception {
		Member result = null;

		try {
			result = sqlSession.selectOne("MemberMapper.selectLoginInfo", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("아이디나 비밀번호가 잘못되었습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("로그인에 실패했습니다.");
		}

		return result;
	}
/** 비밀번호 재발급 */
	@Override
	public void updateMemberPasswordByEmail(Member member) throws Exception {
		try {
			int result = sqlSession.update("MemberMapper.updateMemberPasswordByEmail", member);

			// 수정된 행의 수가 없다는 것은 WHERE절 조건이 맞지 않기 때믄이다.
			// 즉, 입력한 이메일과 일치하는 데이터가 없다는 의미
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("가입된 이메일이 아닙니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("비밀번호 변경에 실패했습니다.");
		}
	}
/** 회원삭제 */
	@Override
	public void deleteMember(Member member) throws Exception {
		try {
			int result = sqlSession.delete("MemberMapper.deleteMember", member);
			// 삭제된 데이터가 없다는 것은 WHERE절의 조건값이 맞지 않다는 의미.
			// 이 경우, 첫번째 WHERE 조건에서 사용되는 id값에 대한 회원을 찾을수 없다는 의미
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 탈퇴한 회원 입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원탈퇴에 실패했습니다.");
		}
	}
/** 회원 수정*/
	@Override
	public void updateMember(Member member) throws Exception {
		
		try {
			int result = sqlSession.update("MemberMapper.updateMember", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 회원정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원정보 수정에 실패했습니다.");
		}
	}

	@Override
	public List<Member> selectMemberList(Member member) throws Exception {
		List<Member> result = null;

		try {
			result = sqlSession.selectList("MemberMapper.selectMemberList", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 회원 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원 목록 조회에 실패했습니다.");
		}

		return result;
	}
	
	/** 이메일 중복체크 */
	@Override
	public int selectEmailCount(Member member) throws Exception {
		int result;
		try {
			result = sqlSession.selectOne("MemberMapper.selectEmailCount", member);
			// 중복된 데이터가 존재한다면?
			if (result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 사용중인 이메일입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("이메일 중복검사에 실패했습니다.");
		}
		return result;
	}
	
/** 닉네임 중복체크 */
	@Override
	public int selectNicknameCount(Member member) throws Exception {
		int result;
		try {
			result = sqlSession.selectOne("MemberMapper.selectNicknameCount", member);
			// 중복된 데이터가 존재한다면?
			if (result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 사용중인 닉네임입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("닉네임 중복검사에 실패했습니다.");
		}
		return result;
	}
	
/** 현재 로그인 중인 회원의 비밀번호 검사 */
	@Override
	public void selectMemberPasswordCount(Member member) throws Exception {
		try {
			int result = sqlSession.selectOne("MemberMapper.selectMemberPasswordCount", member);

			// 회원번호와 비밀번호가 일치하는 데이터가 0이라면, 비밀번호가 잘못된 상태
			if (result == 0) {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			throw new Exception("잘못된 비밀번호 입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("비밀번호 검사에 실패했습니다.");
		}
	}
/** 회원 정보 조회 */
	@Override
	public Member selectMember(Member member) throws Exception {
		Member result = null;
		try {
			result = sqlSession.selectOne("MemberMapper.selectMember", member);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원정보 조회에 실패했습니다.");
		}
		return result;
	}
/** 즐겨찾기 한 글 조회 */
	@Override
	public List<Board_Love> selectBookMark(Board_Love boardLove) throws Exception {
		List<Board_Love> result;

		try {
			result = sqlSession.selectList("BoardLoveMapper.selectBookMark", boardLove);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 게시글 조회에 실패했습니다.");
		}

		return result;
	}
@Override
public int selectNicknameCount_edit(Member member) throws Exception {
	int result;
	try {
		result = sqlSession.selectOne("MemberMapper.selectNicknameCount", member);
	} catch (NullPointerException e) {
		throw new Exception("이미 사용중인 닉네임입니다.");
	} catch (Exception e) {
		logger.error(e.getLocalizedMessage());
		throw new Exception("닉네임 중복검사에 실패했습니다.");
	}
	return result;
}
}
