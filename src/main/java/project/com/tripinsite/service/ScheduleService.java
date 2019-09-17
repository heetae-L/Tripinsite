package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Schedule;

public interface ScheduleService {
	/**
	 * 하나의 여행 계획(idschedule, member_id, subject, start_dat, end_day, tag, theme, privit, board_idboard)을 검색해 결과를 리턴한다.
	 * @param schedule
	 * @return Schedule
	 * @throws Exception
	 */
	public Schedule selectSchedule(Schedule schedule) throws Exception;
	
	/**
	 * 여행 계획(idschedule, member_id, subject, start_dat, end_day, tag, theme, privit, board_idboard) 리스트를 검색해 결과를 리턴한다.
	 * @param schedule
	 * @return
	 * @throws Exception
	 */
	public List<Schedule> selectScheduleList(Schedule schedule) throws Exception;
	
	/**
	 * 여행 계획(idschedule, member_id, subject, start_dat, end_day, tag, theme, privit, board_idboard)을 저장한다.
	 * @param schedule
	 * @throws Exception
	 */
	public int insertSchedule(Schedule schedule) throws Exception;
	
	/**
	 * 여행 계획(idschedule, member_id, subject, start_dat, end_day, tag, theme, privit, board_idboard)을 수정한다.
	 * @param schedule
	 * @throws Exception
	 */
	public void updateSchedule(Schedule schedule) throws Exception;
	
	/**
	 * 여행 계획 중 start_day, end_day를 수정한다.
	 * @param schedule
	 * @throws Exception
	 */
	public void updateScheduleDate(Schedule schedule) throws Exception;
	
	/**
	 * 여행 계획(idschedule, member_id, subject, start_dat, end_day, tag, theme, privit, board_idboard)을 삭제한다.
	 * @param schedule
	 * @throws Exception
	 */
	public void deleteSchedule(Schedule schedule) throws Exception;
	
	/**
	 * 회원삭제시 게시물 참조 해제 처리
	 * @param comment
	 * @throws Exception
	 */
	public void updateScheMemberOut(Schedule schedule) throws Exception;
}
