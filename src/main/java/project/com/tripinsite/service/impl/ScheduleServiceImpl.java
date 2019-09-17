
package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Schedule;
import project.com.tripinsite.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	protected static Logger logger = LoggerFactory.getLogger("ScheduleServiceImpl");

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Override
	public Schedule selectSchedule(Schedule schedule) throws Exception {
		Schedule result = null;
		try {
			result = sqlSession.selectOne("ScheduleMapper.selectSchedule", schedule);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("검색된 schedule 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("schedule 게시물 정보 검색에 실패했습니다.");
		} // try~catch
		return result;
	}

	@Override
	public List<Schedule> selectScheduleList(Schedule schedule) throws Exception {
		List<Schedule> result = null;
		try {
			result = sqlSession.selectList("ScheduleMapper.selectScheduleList", schedule);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("검색된 Schedule 게시물 리스트가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("Schedule 게시물 리스트 검색에 실패했습니다.");
		} // try~catch
		return result;
	}

	@Override
	public int insertSchedule(Schedule schedule) throws Exception {
		int result = 0;
		try {
			sqlSession.insert("ScheduleMapper.insertSchedule", schedule);
			result = schedule.getIdschedule();
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 Schedule 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("Schedule 게시물 정보 등록에 실패했습니다.");
		} // try~catch
		return result;
	}

	@Override
	public void updateSchedule(Schedule schedule) throws Exception {	
		try {
			int result = sqlSession.update("ScheduleMapper.updateSchedule", schedule);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("수정된 Schedule 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("Schedule 게시물 정보 수정에 실패했습니다.");
		} // try~catch
	}

	@Override
	public void deleteSchedule(Schedule schedule) throws Exception {
		try {
			int result = sqlSession.delete("ScheduleMapper.deleteSchedule", schedule);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("삭제된 Schedule 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("Schedule 게시물 정보 삭제에 실패했습니다.");
		} // try~catch	
	}

	@Override
	public void updateScheMemberOut(Schedule schedule) throws Exception {
		try {
			sqlSession.update("ScheduleMapper.updateScheMemberOut", schedule);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}
	
	@Override
	public void updateScheduleDate(Schedule schedule) throws Exception {
		try {
			int result = sqlSession.update("ScheduleMapper.updateScheduleDate", schedule);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("여행 시작일과 종료일이 수정된 Schedule 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("Schedule 게시물의 여행 시작일과 종료일 수정에 실패했습니다.");
		} // try~catch
	}

}
