package project.com.tripinsite.model;

public class Schedule {
	private int idschedule;
	private int member_id;
	private String subject;
	private String start_day;
	private String end_day;
	private String tag;
	private int theme;
	private int privit;
	private int board_idboard;
	
	public int getIdschedule() {
		return idschedule;
	}
	public int getMember_id() {
		return member_id;
	}
	public String getSubject() {
		return subject;
	}
	public String getStart_day() {
		return start_day;
	}
	public String getEnd_day() {
		return end_day;
	}
	public String getTag() {
		return tag;
	}
	public int getTheme() {
		return theme;
	}
	public int getPrivit() {
		return privit;
	}
	public int getBoard_idboard() {
		return board_idboard;
	}
	public void setIdschedule(int idschedule) {
		this.idschedule = idschedule;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}
	public void setEnd_day(String end_day) {
		this.end_day = end_day;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	public void setPrivit(int privit) {
		this.privit = privit;
	}
	public void setBoard_idboard(int board_idboard) {
		this.board_idboard = board_idboard;
	}
	
	@Override
	public String toString() {
		return "Schedule [idschedule=" + idschedule + ", member_id=" + member_id + ", subject=" + subject
				+ ", start_day=" + start_day + ", end_day=" + end_day + ", tag=" + tag + ", theme=" + theme
				+ ", privit=" + privit + ", board_idboard=" + board_idboard + "]";
	}
		
	
		
	
}
