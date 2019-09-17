package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Member;

public interface AdminService {
	// ~~~~~~~~~~~~~~~~~~~~~~ 관리자 회원 관리 ~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * @param member - 읽어들일 회원 일련번호가 저장된 Beans
	 * @return Member - 읽어들일 회원 일련번호가 저장된 Beans
	 * @throws Exception
	 */
	public Member selectMember(Member member) throws Exception;
	
	
	/**
	 * 현재글을 기준으로 이전회원 목록을 읽어들인다.
	 * @param member - 현재 회원에 대한 회원 번호가 저장된 Beans
	 * @return Member - 읽어들인 회원목록 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Member selectPrevMember(Member member) throws Exception;
	
	/**
	 * 현재글을 기준으로 다음회원 목록을 읽어들인다.
	 * @param member
	 * @return Member - 읽어들인 회원 목록 내용(없을경우 null)
	 * @throws Exception
	 */
	public Member selectNextMember(Member member) throws Exception;
	
	/**
	 * 전체 게시물 수 조회
	 * @param member
	 * @return int
	 * @throws Exception
	 */
	public int selectMemberCount(Member member) throws Exception;
	
	/**
	 * 관리자 권한으로 회원 정보 수정 
	 * @param member
	 * @throws Exception
	 */
	public void updateMember(Member member) throws Exception;
	
	// ~~~~~~~~~~~~~~~~~~~~~~ 관리자 게시판 관리 ~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * 관리자페이지 게시물 수 조회
	 * @param board
	 * @return int
	 * @throws Exception
	 */
	public int selectboardCount(Board board) throws Exception;
	
	/**
	 * 관리자페이지 게시물 목록 조회
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public List<Board> selectBoardList(Board board) throws Exception;
	
	/**
	 * 공지사항 작성
	 * @param board
	 * @throws Exception
	 */
	public void insertBoard(Board board) throws Exception;
	
	public int selectBoardId() throws Exception;
	
	/**
	 * 관리자의 게시판 수정, 삭제를 위한 게시글 작성자 정보 조회
	 * @param board
	 * @return board - writer_nickname, writer_pw, idboard
	 * @throws Exception
	 */
	public Board selectBoardByAdmin(Board board) throws Exception;
	
	public void updateNotice(Board board) throws Exception;
}
