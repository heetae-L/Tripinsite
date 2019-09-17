package project.com.tripinsite.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class APIHelper {

	/**
	 * 키워드 조회
	 * 
	 * @param key         - 검색할 키워드
	 * @param pageNo      - 페이지 번호
	 * @param areaCode    - 지역코드
	 * @param sigunguCode - 시군구 코드
	 * @param numOfRows   - 한페이지에 보여질 결과 수
	 * @return - 키워드 검색 JSON
	 * @throws Exception
	 */
	public String keyCode(String key, int pageNo) throws Exception {
		/** URL */
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword");
		/** Service Key */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=INeOhyOQuhIQla6LnuM9kymjqifuT3OLS%2Fwa0r3m%2FmKnKcxQIPvcYdO8YSxz%2BaPhKwzHstRD22%2F9%2F3a%2FJFMuWQ%3D%3D");
		/** 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TripInSite", "UTF-8"));
		/** IOS (아이폰), AND(안드로이드), ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
		/** 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(pageNo), "UTF-8"));
		/** 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 목록 구분 (Y=목록, N=개수) */
		urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/**
		 * (A=제목순, B=조회순, C=수정일순, D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순)
		 */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P", "UTF-8"));
//		/** 관광타입(관광지, 숙박 등) ID */
//		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
//		/** 지역코드 */
//		urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8"));
//		/** 시군구코드(areaCode 필수) */
//		urlBuilder.append(
//				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode(sigunguCode, "UTF-8"));
		/** 대분류 코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
		/** 중분류 코드(cat1필수) */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
		/** 소분류 코드(cat1,cat2필수) */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
		/** 검색 요청할 키워드 (국문=인코딩 필요) */
		urlBuilder.append("&" + URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8"));
		/** 파일 형식 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		return result;
	}

	/**
	 * 공통 정보 조회
	 * 
	 * @param contentId - 관광지 콘텐츠 ID값
	 * @param pageNo    - 현재 페이지
	 * @return - 관광지 ID에 해당하는 정보 조회 결과 json형식 String
	 * @throws Exception
	 */
	public String information(String contentId, int pageNo) throws Exception {
		/** URL */
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon");
		/** ServiceKey */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=INeOhyOQuhIQla6LnuM9kymjqifuT3OLS%2Fwa0r3m%2FmKnKcxQIPvcYdO8YSxz%2BaPhKwzHstRD22%2F9%2F3a%2FJFMuWQ%3D%3D");
		/** 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		/** 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(pageNo), "UTF-8"));
		/** IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
		/** 서비스명=어플명 */
		urlBuilder
				.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TripInSite", "UTF-8"));
		/** 콘텐츠ID */
		urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentId, "UTF-8"));
//		/** 관광타입(관광지, 숙박 등) ID = 12(관광지) */
//		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 기본정보 조회여부 */
		urlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 원본, 썸네일 대표이미지 조회여부 */
		urlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 지역코드, 시군구코드 조회여부 */
		urlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
		/** 대,중,소분류코드 조회여부 N */
		urlBuilder.append("&" + URLEncoder.encode("catcodeYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
		/** 주소, 상세주소 조회여부 */
		urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 좌표 X,Y 조회여부 N */
		urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8"));
		/** 콘텐츠 개요 조회여부 */
		urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 파일 형식 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		return result;

	}

	/**
	 * 지역 조회
	 * 
	 * @param areaCode    - 지역코드번호
	 * @param sigunguCode - 시군구코드번호
	 * @param pageNo      - 현재 페이지
	 * @return - 지역코드에 해당하는 관광지 JSON
	 * @throws Exception
	 */
	public String areaCode(String areaCode, String sigunguCode, int pageNo) throws Exception {

		/** URL */
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList");
		/** ServiceKey */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=INeOhyOQuhIQla6LnuM9kymjqifuT3OLS%2Fwa0r3m%2FmKnKcxQIPvcYdO8YSxz%2BaPhKwzHstRD22%2F9%2F3a%2FJFMuWQ%3D%3D");
		/** 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(pageNo), "UTF-8"));
		/** 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TripInSite", "UTF-8"));
		/** IOS (아이폰), AND (안드로이드),WIN (원도우폰), ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
		/** (A=제목순, B=조회순, C=수정일순, D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순) */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P", "UTF-8"));
//		/** 관광타입(관광지, 숙박 등) ID */
//		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 지역코드 */
		urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8"));
		/** 시군구코드(areaCode 필수) */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode(sigunguCode, "UTF-8"));
		/** 목록 구분 (Y=목록, N=개수) */
		urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 파일 형식 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		return result;

	}

	/**
	 * 지역 조회
	 * 
	 * @param areaCode - 지역코드번호
	 * @param pageNo   - 현재 페이지
	 * @return - 지역코드에 해당하는 관광지 JSON
	 * @throws Exception
	 */
	public String areaCode(String areaCode, int pageNo) throws Exception {

		/** URL */
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList");
		/** ServiceKey */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=INeOhyOQuhIQla6LnuM9kymjqifuT3OLS%2Fwa0r3m%2FmKnKcxQIPvcYdO8YSxz%2BaPhKwzHstRD22%2F9%2F3a%2FJFMuWQ%3D%3D");
		/** 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(pageNo), "UTF-8"));
		/** 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 서비스명=어플명 */
		urlBuilder
				.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TripInSite", "UTF-8"));
		/** IOS (아이폰), AND (안드로이드),WIN (원도우폰), ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
		/**
		 * (A=제목순, B=조회순, C=수정일순, D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순)
		 */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P", "UTF-8"));
//		/** 관광타입(관광지, 숙박 등) ID */
//		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		/** 지역코드 */
		urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8"));
		/** 목록 구분 (Y=목록, N=개수) */
		urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 파일 형식 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		return result;

	}

	/**
	 * 지역 조회
	 * 
	 * @param areaCode - 지역코드번호
	 * @param pageNo   - 현재 페이지
	 * @return - 지역코드에 해당하는 관광지 JSON
	 * @throws Exception
	 */
	public String areaCode() throws Exception {

		/** URL */
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList");
		/** ServiceKey */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=8l%2FBuSUjRRtIehWoUI4p0naQNxHZ5DFeM2cIByKVnKru9N6uSLPTpp0kmca09fug2dFTtml7PzJGjvdB%2BYtvsQ%3D%3D");
		/** 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(1), "UTF-8"));
		/** 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("8", "UTF-8"));
		/** 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TripInSite", "UTF-8"));
		/** IOS (아이폰), AND (안드로이드),WIN (원도우폰), ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
		/** (A=제목순, B=조회순, C=수정일순, D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순) */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P", "UTF-8"));
//		/** 관광타입(관광지, 숙박 등) ID */
//		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));

		/** 목록 구분 (Y=목록, N=개수) */
		urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
		/** 파일 형식 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		return result;

	}
}
