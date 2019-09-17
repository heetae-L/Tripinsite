package project.com.tripinsite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.com.tripinsite.model.Sche_table;
import project.com.tripinsite.service.ScheTableService;

@Service
public class ScheTableServiceImpl implements ScheTableService {

	protected static Logger logger = LoggerFactory.getLogger("ScheTableServiceImpl");

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ScheTableService scheTableService;

	@Override
	public int insertScheTable(Sche_table scheTable) throws Exception {
		int result = 0;
		try {
			sqlSession.insert("Sche_tableMapper.insertSche_table", scheTable);
			result = scheTable.getIdsche_table();
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 sche_table 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table 게시물 정보 등록에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void updateScheTable(Sche_table scheTable) throws Exception {		
		try {
			int result = sqlSession.update("Sche_tableMapper.updateSche_table", scheTable);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("수정된 sche_table 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table 게시물 정보 수정에 실패했습니다.");
		}	
	}

	@Override
	public void deleteScheTable(Sche_table scheTable) throws Exception {
		try {
			int result = sqlSession.delete("Sche_tableMapper.deleteSche_table", scheTable);

			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("삭제된 sche_table 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table 게시물 정보 삭제에 실패했습니다.");
		}
	}

	@Override
	public Sche_table selectScheTable(Sche_table scheTable) throws Exception {
		Sche_table result = null;
		try {
			result = sqlSession.selectOne("Sche_tableMapper.selectSche_table", scheTable);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("검색된 sche_table 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table 게시물 정보 검색에 실패했습니다.");
		}	
		return result;
	}

	@Override
	public List<Sche_table> selectScheTableList(Sche_table scheTable) throws Exception {
		List<Sche_table> result = null;
		try {
			result = sqlSession.selectList("Sche_tableApiMapper.selectSche_tableList", scheTable);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("검색된 sche_table 게시물 리스트가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("sche_table 게시물 리스트 검색에 실패했습니다.");
		}
		return result;
	}
	
}
