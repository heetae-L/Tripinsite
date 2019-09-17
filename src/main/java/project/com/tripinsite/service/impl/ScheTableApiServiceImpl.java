package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Sche_table;
import project.com.tripinsite.model.Sche_tableApi;
import project.com.tripinsite.service.ScheTableApiService;

@Service
public class ScheTableApiServiceImpl implements ScheTableApiService {

	protected static Logger logger = LoggerFactory.getLogger("ScheTableApiServiceImpl");

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ScheTableApiService scheTableApiService;

	
	@Override
	public List<Sche_tableApi> selectScheTableList(Sche_table scheTable) throws Exception {
		List<Sche_tableApi> result = null;
		
		try {
			result = sqlSession.selectList("Sche_tableApiMapper.selectSche_tableList", scheTable);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("검색된 sche_table&api 게시물 리스트가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table&api 게시물 리스트 검색에 실패했습니다.");
		}
		return result;
	}

}
