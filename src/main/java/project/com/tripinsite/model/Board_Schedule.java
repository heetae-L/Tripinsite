package project.com.tripinsite.model;

public class Board_Schedule extends Board {
	private int theme;
	private int privit;
	private int love;
	private int idschedule;
	private String tag;

	// 갤러리 구현을 위해서 추가된 값
	private String imagePath;
	// 조회순 정렬을 위해 구현되는 값
	private boolean isSort;

	// 수정을 위한 값 추가
	private String start_day;
	private String end_day;

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getPrivit() {
		return privit;
	}

	public void setPrivit(int privit) {
		this.privit = privit;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getIdschedule() {
		return idschedule;
	}

	public void setIdschedule(int idschedule) {
		this.idschedule = idschedule;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isSort() {
		return isSort;
	}

	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	public String getStart_day() {
		return start_day;
	}

	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}

	public String getEnd_day() {
		return end_day;
	}

	public void setEnd_day(String end_day) {
		this.end_day = end_day;
	}

	@Override
	public String toString() {
		return "Board_Schedule [theme=" + theme + ", privit=" + privit + ", love=" + love + ", idschedule=" + idschedule
				+ ", tag=" + tag + ", imagePath=" + imagePath + ", isSort=" + isSort + ", start_day=" + start_day
				+ ", end_day=" + end_day + ", getIdboard()=" + getIdboard() + ", getCategory()=" + getCategory()
				+ ", getMember_id()=" + getMember_id() + ", getSubject()=" + getSubject() + ", getWriter_nickname()="
				+ getWriter_nickname() + ", getWriter_pw()=" + getWriter_pw() + ", getContent()=" + getContent()
				+ ", getHit()=" + getHit() + ", getReg_date()=" + getReg_date() + ", getEdit_date()=" + getEdit_date()
				+ ", getIp_address()=" + getIp_address() + ", getIs_notice()=" + getIs_notice() + ", getLimitStart()="
				+ getLimitStart() + ", getListCount()=" + getListCount() + "]";
	}

}
