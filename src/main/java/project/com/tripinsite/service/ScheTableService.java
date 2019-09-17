package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Sche_table;

public interface ScheTableService {
	
	/**
	 * 여행 스케쥴(idsche_table, api_contentid, schedule_idschedule, starttime, endtime)을 저장한다.
	 * @param Sche_table - 스케쥴 이벤트 한 개
	 * @return int idsche_table
	 * @throws Exception
	 */
	public int insertScheTable(Sche_table scheTable) throws Exception;
	
	/**
	 * 여행 스케쥴(idsche_table, api_contentid, schedule_idschedule, starttime, endtime)을 수정한다.
	 * @param scheTable
	 * @throws Exception
	 */
	public void updateScheTable(Sche_table scheTable) throws Exception;
	
	/**
	 * 여행 스케쥴(idsche_table, api_contentid, schedule_idschedule, starttime, endtime)을 삭제한다.
	 * @param scheTable
	 * @throws Exception
	 */
	public void deleteScheTable(Sche_table scheTable) throws Exception;
	
	/**
	 * 여행 스케쥴(idsche_table, api_contentid, schedule_idschedule, starttime, endtime) 한 개를 검색한다.
	 * @param scheTable
	 * @return Sche_table
	 * @throws Exception
	 */
	public Sche_table selectScheTable(Sche_table scheTable) throws Exception;
	
	/**
	 * 여행 스케쥴여행 스케쥴(idsche_table, api_contentid, schedule_idschedule, starttime, endtime) 리스트를 검색한다.
	 * @param scheTable
	 * @return List<Sche_table>
	 * @throws Exception
	 */
	public List<Sche_table> selectScheTableList(Sche_table scheTable) throws Exception;
	
}
