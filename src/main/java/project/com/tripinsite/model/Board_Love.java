package project.com.tripinsite.model;

public class Board_Love extends Love {
	private String subject;
	private String writer_nickname;
	private String reg_date;
	private int idboard;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter_nickname() {
		return writer_nickname;
	}

	public void setWriter_nickname(String writer_nickname) {
		this.writer_nickname = writer_nickname;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public int getIdboard() {
		return idboard;
	}

	public void setIdboard(int idboard) {
		this.idboard = idboard;
	}

	@Override
	public String toString() {
		return "Board_Love [subject=" + subject + ", writer_nickname=" + writer_nickname + ", reg_date=" + reg_date
				+ ", idboard=" + idboard + ", getIdlove()=" + getIdlove() + ", getMember_id()=" + getMember_id()
				+ ", getSchedule_idschedule()=" + getSchedule_idschedule() + "]";
	}

}