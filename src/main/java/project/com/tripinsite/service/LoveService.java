package project.com.tripinsite.service;

import project.com.tripinsite.model.Love;

public interface LoveService {
	/**
	 * 좋아요를 누른 글인지 확인
	 * 
	 * @param love
	 * @return
	 * @throws Exception
	 */
	public int selectLoveCount(Love love) throws Exception;

	/**
	 * 좋아요 추가
	 * 
	 * @param love
	 * @throws Exception
	 */
	public void insertLove(Love love) throws Exception;

	/**
	 * 좋아요 삭제
	 * 
	 * @param love
	 * @throws Exception
	 */
	public void deleteLove(Love love) throws Exception;
	
	/**
	 * 회원탈퇴 시 좋아요 member_id 변경 처리
	 * @param love
	 * @throws Exception
	 */
	public void updateLoveMemberOut(Love love) throws Exception;
	
	
}
