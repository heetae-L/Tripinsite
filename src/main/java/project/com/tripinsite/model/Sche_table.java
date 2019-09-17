package project.com.tripinsite.model;

public class Sche_table {
	private int idsche_table;
	private int api_contentid;
	private int schedule_idschedule;
	private String starttime;
	private String endtime;
	public int getIdsche_table() {
		return idsche_table;
	}
	public void setIdsche_table(int idsche_table) {
		this.idsche_table = idsche_table;
	}
	public int getApi_contentid() {
		return api_contentid;
	}
	public void setApi_contentid(int api_contentid) {
		this.api_contentid = api_contentid;
	}
	public int getSchedule_idschedule() {
		return schedule_idschedule;
	}
	public void setSchedule_idschedule(int schedule_idschedule) {
		this.schedule_idschedule = schedule_idschedule;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Override
	public String toString() {
		return "Sche_table [idsche_table=" + idsche_table + ", api_contentid=" + api_contentid
				+ ", schedule_idschedule=" + schedule_idschedule + ", starttime=" + starttime + ", endtime=" + endtime
				+ "]";
	}
	
}
