package project.com.tripinsite.model;

public class ScheduleForm {
	private String title;
	private int date;
	private String start_date;
	private String tgroup;
	private int thema;
	private int privit;
	public String getTitle() {
		return title;
	}
	public int getDate() {
		return date;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getTgroup() {
		return tgroup;
	}
	public int getThema() {
		return thema;
	}
	public int getPrivit() {
		return privit;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public void setTgroup(String tgroup) {
		this.tgroup = tgroup;
	}
	public void setThema(int thema) {
		this.thema = thema;
	}
	public void setPrivit(int privit) {
		this.privit = privit;
	}
	@Override
	public String toString() {
		return "ScheduleForm [title=" + title + ", date=" + date + ", start_date=" + start_date + ", tgroup=" + tgroup
				+ ", thema=" + thema + ", privit=" + privit + "]";
	}

}
