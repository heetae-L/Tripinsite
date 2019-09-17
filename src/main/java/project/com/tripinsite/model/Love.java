package project.com.tripinsite.model;

public class Love {
	private int idlove;
	private int member_id;
	private int schedule_idschedule;

	public int getIdlove() {
		return idlove;
	}

	public void setIdlove(int idlove) {
		this.idlove = idlove;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getSchedule_idschedule() {
		return schedule_idschedule;
	}

	public void setSchedule_idschedule(int schedule_idschedule) {
		this.schedule_idschedule = schedule_idschedule;
	}

	@Override
	public String toString() {
		return "Love [idlove=" + idlove + ", member_id=" + member_id + ", schedule_idschedule=" + schedule_idschedule
				+ "]";
	}

}
