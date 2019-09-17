package project.com.tripinsite.helper;

public class PageHelper {
	/***** GET파라미터로 처리할 값 *****/
	private int page = 1; // 현재 페이지 번호

	/***** DB에서 조회한 결과 값 *****/
	private int totalCount = 0; // 전체 글 개수 조회

	/***** 개발자가 정의해야 하는 값 *****/
	private int listCount = 10; // 한 페이지에 보여질 글의 목록 수
	private int groupCount = 5; // 한 그룹에 표시할 페이지번호 개수

	/***** 연산처리가 필요한 값 *****/
	private int totalPage = 0; // 전체 페이지 수
	private int startPage = 0; // 현재 그룹의 시작 페이지
	private int endPage = 0; // 현재 그룹의 마지막 페이지
	private int prevPage = 0; // 이전 그룹의 마지막 페이지
	private int nextPage = 0; // 이전 그룹의 첫 페이지
	private int limitStart = 0; // MySQL의 Limit 시작 위치

	/** 페이지 구현에 필요한 계산식을 처리하는 메서드 */
	public void pageProcess(int page, int totalCount, int listCount, int groupCount) {
		this.page = page;
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.groupCount = groupCount;

		// 전체 페이지 수
		totalPage = ((totalCount - 1) / listCount) + 1;

		// 현재 페이지에 대한 오차 조절
		if (page < 0) {
			page = 1;
		}

		if (page > totalPage) {
			page = totalPage;
		}

		// 현재 페이징 그룹의 시작 페이지 번호
		startPage = ((page - 1) / groupCount) * groupCount + 1;

		// 현재 페이징 그룹의 끝 페이지 번호
		endPage = (((startPage - 1) + groupCount) / groupCount) * groupCount;

		// 끝 페이지 번호가 전체 페이지수를 초과하면 오차범위 조절
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		// 이전 그룹의 마지막 페이지
		if (startPage > groupCount) {
			prevPage = startPage - 1;
		} else {
			prevPage = 0;
		}

		// 다음 그룹의 첫 페이지
		if (endPage < totalPage) {
			nextPage = endPage + 1;
		} else {
			nextPage = 0;
		}

		// 검색 범위의 시작 위치
		limitStart = (page - 1) * listCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	@Override
	public String toString() {
		return "PageHelper [page=" + page + ", totalCount=" + totalCount + ", listCount=" + listCount + ", groupCount="
				+ groupCount + ", totalPage=" + totalPage + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", prevPage=" + prevPage + ", nextPage=" + nextPage + ", limitStart=" + limitStart + "]";
	}
}
