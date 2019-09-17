package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Api;
import project.com.tripinsite.service.ApiService;

@Service
public class ApiServiceImpl implements ApiService {
	
	protected static Logger logger = LoggerFactory.getLogger("ApiServiceImpl");

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ApiService apiService;

	@Override
	public Api selectApi(Api api) throws Exception {
		Api result = null;
		
		try {
			result = sqlSession.selectOne("ApiMapper.selectApi", api);			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("api 자료 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<Api> selectApiList(Api api) throws Exception {
		List<Api> result = null;
		
		try {
			result = sqlSession.selectList("ApiMapper.selectApiList", api);			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("api 자료 리스트 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public void insertApi(Api api) throws Exception {	
		try {
			int result = sqlSession.insert("ApiMapper.insertApi", api);
			
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 api 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("api 게시물 정보 등록에 실패했습니다.");
		}
	}
	

	@Override
	public void updateApi(Api api) throws Exception {
		try {
			int result = sqlSession.update("ApiMapper.updateApi", api);
			
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("수정된 api 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("api 게시물 정보 수정에 실패했습니다.");
		}
		
	}

	@Override
	public void deleteApi(Api api) throws Exception {
		try {
			int result = sqlSession.delete("ApiMapper.deleteApi", api);
			
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("삭제된 api 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("api 게시물 정보 삭제에 실패했습니다.");
		}
		
	}

}
