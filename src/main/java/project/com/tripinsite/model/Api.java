package project.com.tripinsite.model;

public class Api {
	private int contentid;
	private String title;
	private int areacode;
	private int sigungucode;
	private String addr1;
	private String addr2;
	private int contenttypeid;
	private float mapx;
	private float mapy;
	private String tel;
	public int getContentid() {
		return contentid;
	}
	public void setContentid(int contentid) {
		this.contentid = contentid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAreacode() {
		return areacode;
	}
	public void setAreacode(int areacode) {
		this.areacode = areacode;
	}
	public int getSigungucode() {
		return sigungucode;
	}
	public void setSigungucode(int sigungucode) {
		this.sigungucode = sigungucode;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public int getContenttypeid() {
		return contenttypeid;
	}
	public void setContenttypeid(int contenttypeid) {
		this.contenttypeid = contenttypeid;
	}
	public float getMapx() {
		return mapx;
	}
	public void setMapx(float mapx) {
		this.mapx = mapx;
	}
	public float getMapy() {
		return mapy;
	}
	public void setMapy(float mapy) {
		this.mapy = mapy;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "Api [contentid=" + contentid + ", title=" + title + ", areacode=" + areacode + ", sigungucode="
				+ sigungucode + ", addr1=" + addr1 + ", addr2=" + addr2 + ", contenttypeid=" + contenttypeid + ", mapx="
				+ mapx + ", mapy=" + mapy + ", tel=" + tel + "]";
	}
	
	
}
