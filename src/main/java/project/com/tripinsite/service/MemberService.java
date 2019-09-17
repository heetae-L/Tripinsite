package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Board_Love;
import project.com.tripinsite.model.Member;

public interface MemberService {
	/** 이메일 중복 검사  */
	public int selectEmailCount(Member member) throws Exception;

	/** 닉네임 중복 검사 */
	public int selectNicknameCount(Member member) throws Exception;
		
	/** 회원가입 처리 */
	public void insertMember(Member member) throws Exception;

	/** 로그인 처리 */
	public Member selectLoginInfo(Member member) throws Exception;

	/** 비밀번호 변경 */
	public void updateMemberPasswordByEmail(Member member) throws Exception;
	
	/** 회원 탈퇴 */
	public void deleteMember(Member member) throws Exception;

	/** 회원 수정 */
	public void updateMember(Member member) throws Exception;

	/** 비밀번호 검사  */
	public void selectMemberPasswordCount(Member member) throws Exception;
	
	/** 일련번호에 의한 회원정보 조회  */
	public Member selectMember(Member member) throws Exception;
	
	/** 마이페이지 즐겨찾기 한 글 조회 후 출력 */
	public List<Board_Love> selectBookMark(Board_Love boardLove) throws Exception;
	
	/** 관리자권한으로 회원 목록 조회 
	 * @param member - 카테고리 정보가 저장된 Beans
	 * @return List - 회원 목록
	 * @throws Exception
	 */
	public List<Member> selectMemberList(Member member) throws Exception;
	
	/** 회원수정시 본인 닉네임 중복 확인 */
	public int selectNicknameCount_edit(Member member) throws Exception;	
	

}
