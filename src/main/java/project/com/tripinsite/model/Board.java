package project.com.tripinsite.model;

public class Board {
	private int idboard;
	private String category;
	private int member_id;
	private String subject;
	private String writer_nickname;
	private String writer_pw;
	private String content;
	private int hit;
	private String reg_date;
	private String edit_date;
	private String ip_address;
	private int is_notice;

	// 페이지 구현을 위해서 추가된 값
	private int limitStart;
	private int listCount;

	public int getIdboard() {
		return idboard;
	}

	public void setIdboard(int idboard) {
		this.idboard = idboard;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

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

	public String getWriter_pw() {
		return writer_pw;
	}

	public void setWriter_pw(String writer_pw) {
		this.writer_pw = writer_pw;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getEdit_date() {
		return edit_date;
	}

	public void setEdit_date(String edit_date) {
		this.edit_date = edit_date;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public int getIs_notice() {
		return is_notice;
	}

	public void setIs_notice(int is_notice) {
		this.is_notice = is_notice;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	@Override
	public String toString() {
		return "Board [idboard=" + idboard + ", category=" + category + ", member_id=" + member_id + ", subject="
				+ subject + ", writer_nickname=" + writer_nickname + ", writer_pw=" + writer_pw + ", content=" + content
				+ ", hit=" + hit + ", reg_date=" + reg_date + ", edit_date=" + edit_date + ", ip_address=" + ip_address
				+ ", is_notice=" + is_notice + ", limitStart=" + limitStart + ", listCount=" + listCount + "]";
	}

}
