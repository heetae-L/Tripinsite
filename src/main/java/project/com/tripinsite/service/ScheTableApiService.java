package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Sche_table;
import project.com.tripinsite.model.Sche_tableApi;

public interface ScheTableApiService {
	/**
	 * 여행 일정 + API 중 일부 정보를 조인해서 검색한다.
	 * @param scheTable
	 * @return List<Sche_tableApi>
	 * @throws Exception
	 */
	public List<Sche_tableApi> selectScheTableList(Sche_table scheTable) throws Exception;
	

}
