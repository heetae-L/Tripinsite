package project.com.tripinsite.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Love;
import project.com.tripinsite.service.LoveService;

@Service
public class LoveServiceImpl implements LoveService {

	protected static Logger logger = LoggerFactory.getLogger("LoveServiceImpl");

	@Autowired
	SqlSession sqlSession;

	@Override
	public int selectLoveCount(Love love) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("LoveMapper.selectLoveList", love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void insertLove(Love love) throws Exception {
		try {
			sqlSession.insert("LoveMapper.insertLove", love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천 추가에 실패했습니다.");
		}
	}

	@Override
	public void deleteLove(Love love) throws Exception {
		try {
			sqlSession.delete("LoveMapper.deleteLove", love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("추천 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateLoveMemberOut(Love love) throws Exception {
		try {
			sqlSession.update("LoveMapper.updateLoveMemberOut", love);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}
}
