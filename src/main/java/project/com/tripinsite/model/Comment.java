package project.com.tripinsite.model;

public class Comment {
	private int idcomment;
	private int board_idboard;
	private int member_id;
	private String writer_nickname;
	private String writer_pw;
	private String content;
	private String reg_date;
	private String edit_date;
	private String ip_address;
	public int getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(int idcomment) {
		this.idcomment = idcomment;
	}
	public int getBoard_idboard() {
		return board_idboard;
	}
	public void setBoard_idboard(int board_idboard) {
		this.board_idboard = board_idboard;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
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
	@Override
	public String toString() {
		return "comment [idcomment=" + idcomment + ", board_idboard=" + board_idboard + ", member_id=" + member_id
				+ ", writer_nickname=" + writer_nickname + ", writer_pw=" + writer_pw + ", content=" + content
				+ ", reg_date=" + reg_date + ", edit_date=" + edit_date + ", ip_address=" + ip_address + "]";
	}
	
}
