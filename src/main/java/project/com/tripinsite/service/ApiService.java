package project.com.tripinsite.service;

import java.util.List;

import project.com.tripinsite.model.Api;

public interface ApiService {
	
	/**
	 * Api 장소 리스트를 검색한다.
	 * @param api
	 * @return
	 * @throws Exception
	 */
	public List<Api> selectApiList(Api api) throws Exception;
	
	/**
	 * Api 장소를  contentid로 검색한다. (없으면 Api 장소로 저장하려고 사용함)
	 * @param Api - Api 장소 한 개
	 * @return Api
	 * @throws Exception
	 */
	public Api selectApi(Api api) throws Exception;
	
	/**
	 * Api 장소를 저장한다.
	 * @param Api - Api 장소 한 개
	 * @throws Exception
	 */
	public void insertApi(Api api) throws Exception;
	
	/**
	 * Api 장소를 수정한다.
	 * @param api
	 * @throws Exception
	 */
	public void updateApi(Api api) throws Exception;
	
	/**
	 * Api 장소를 삭제한다.
	 * @param api
	 * @throws Exception
	 */
	public void deleteApi(Api api) throws Exception;
	
}
