package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Comment;

public interface CommentService {
	/**
	 * 하나의 게시물에 속한 모든 덧글 목록 조회
	 * 
	 * @param comment - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return List - 읽어들인 덧글 목록
	 * @throws Exception
	 */
	public List<Comment> selectCommentList(Comment comment) throws Exception;

	/**
	 * 하나의 덧글을 읽어들인다.
	 * 
	 * @param comment - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return Comment - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public Comment selectComment(Comment comment) throws Exception;

	/**
	 * 덧글을 저장한다.
	 * 
	 * @param comment - 덧글 데이터
	 * @throws Exception
	 */
	public void insertComment(Comment comment) throws Exception;

	/**
	 * 자신의 덧글인지 검사한다.
	 * 
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectCommentCountByMemberId(Comment comment) throws Exception;

	/**
	 * 비밀번호를 검사한다.
	 * 
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public int selectCommentCountByPw(Comment comment) throws Exception;

	/**
	 * 덧글을 삭제한다.
	 * 
	 * @param comment
	 * @throws Exception
	 */
	public void deleteComment(Comment comment) throws Exception;

	/**
	 * 덧글을 수정한다.
	 * 
	 * @param comment - 덧글 데이터
	 * @throws Exception
	 */
	public void updateComment(Comment comment) throws Exception;

	/**
	 * 특정 게시물에 속한 모든 덧글을 삭제한다.
	 * 
	 * @param comment
	 * @throws Exception
	 */
	public void deleteCommentAll(Comment comment) throws Exception;

	/**
	 * 회원과 덧글의 참조관계를 해제한다.
	 * 
	 * @param comment - 게시물 데이터
	 * @throws Exception
	 */
	public void updateCommentMemberOut(Comment comment) throws Exception;
}
