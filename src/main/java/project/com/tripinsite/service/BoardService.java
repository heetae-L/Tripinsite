package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Board;
import project.com.tripinsite.model.Board_Schedule;

public interface BoardService {

	/**
	 * 테마에 해당하는 스토리 게시물 검색
	 * 
	 * @param board - 테마가 저장된 board Beans
	 * @return List 게시물 목록
	 * @throws Exception
	 */
	public List<Board_Schedule> selectThemeBoardList(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 키워드에 해당하는 스토리 게시물 검색
	 * 
	 * @param board_Schedule - 키워드검색 결과가 저장된 board Beans
	 * @return List 게시물 목록
	 * @throws Exception
	 */
	public List<Board_Schedule> selectKeywordBoardList(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시글 목록 조회
	 * 
	 * @param board - 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Board_Schedule> selectStoryBoardList(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 하나의 스토리 게시물 조회
	 * 
	 * @param board_Schedule
	 * @return - 게시물
	 * @throws Exception
	 */
	public Board_Schedule selectStoryBoardView(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 이전 게시물 조회
	 * 
	 * @param board_Schedule
	 * @return
	 * @throws Exception
	 */
	public Board_Schedule selectPrevStoryBoard(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 다음 게시물 조회
	 * 
	 * @param board_Schedule
	 * @return
	 * @throws Exception
	 */
	public Board_Schedule selectNextStoryBoard(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시물 수정
	 * 
	 * @param board_schedule
	 * @throws Exception
	 */
	public void updateStoryBoard(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시물 비밀번호 조회
	 * 
	 * @param board_schedule
	 * @throws Exception
	 */
	public int selectStoryCountByPw(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시물 삭제
	 * 
	 * @param board_Schedule
	 * @throws Exception
	 */
	public void deleteStory(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 게시글 목록 조회
	 * 
	 * @param board - 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Board> selectBoardList(Board board) throws Exception;

	/**
	 * 공지 목록 조회
	 * 
	 * @param board - 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Board> selectBoardNoticeList(Board board) throws Exception;

	/**
	 * 게시물을 저장한다.
	 * 
	 * @param board - 게시물 데이터
	 * @throws Exception
	 */
	public void insertBoard(Board board) throws Exception;

	/**
	 * 하나의 게시물을 읽어들인다.
	 * 
	 * @param board - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return Board - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public Board selectBoard(Board board) throws Exception;

	/**
	 * 하나의 공지를 읽어들인다.
	 * 
	 * @param board - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return Board - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public Board selectBoardNotice(Board board) throws Exception;

	/**
	 * 현재글을 기준으로 이전글을 읽어들인다.
	 * 
	 * @param board - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Board - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Board selectPrevBoard(Board board) throws Exception;

	/**
	 * 현재글을 기준으로 다음글을 읽어들인다.
	 * 
	 * @param board - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Board - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Board selectNextBoard(Board board) throws Exception;

	/**
	 * 조회수를 1 증가시킨다.
	 * 
	 * @param board - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @throws Exception
	 */
	public void updateBoardHit(Board board) throws Exception;

	/**
	 * 전체 게시글 수 조회
	 * 
	 * @param board
	 * @return int
	 * @throws Exception
	 */
	public int selectBoardCount(Board board) throws Exception;

	/**
	 * 자신의 게시물인지 검사한다.
	 * 
	 * @param board
	 * @return int
	 * @throws Exception
	 */
	public int selectBoardCountByMemberId(Board board) throws Exception;

	/**
	 * 비밀번호를 검사한다.
	 * 
	 * @param board
	 * @return int
	 * @throws Exception
	 */
	public int selectBoardCountByPw(Board board) throws Exception;

	/**
	 * 게시물을 삭제한다.
	 * 
	 * @param board
	 * @throws Exception
	 */
	public void deleteBoard(Board board) throws Exception;

	/**
	 * 게시물을 수정한다.
	 * 
	 * @param board - 게시물 데이터
	 * @throws Exception
	 */
	public void updateBoard(Board board) throws Exception;

	/**
	 * 회원과 게시물의 참조관계를 해제한다.
	 * 
	 * @param board - 게시물 데이터
	 * @throws Exception
	 */
	public void updateBoardMemberOut(Board board) throws Exception;

	/**
	 * 나의 게시글 수 조회
	 * 
	 * @param board_Schedule
	 * @return int
	 * @throws Exception
	 */
	public int selectMyScheduleCount(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시글 목록 조회
	 * 
	 * @param board_Schedule - 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<Board_Schedule> selectMyStoryBoardList(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 스토리 게시글을 저장한다.
	 * 
	 * @param board - 게시글 데이터
	 * @return 저장된 스토리 게시글의 idboard
	 * @throws Exception
	 */
	public int insertStoryBoard(Board board) throws Exception;

	/**
	 * 자신의 게시물인지 검사한다.
	 * 
	 * @param board_Schedule
	 * @return
	 * @throws Exception
	 */
	public int selectScheduleCountByMemberId(Board_Schedule board_Schedule) throws Exception;

	/**
	 * 전체 게시글 수 조회
	 * 
	 * @param board_Schedule
	 * @return int
	 * @throws Exception
	 */
	public int selectScheduleCount(Board_Schedule board_Schedule) throws Exception;
}