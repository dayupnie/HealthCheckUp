package come.example.domain;

public class SBP {
	private int id;
	private int Userid;
	private String time;
	private double SBP;
	
	public SBP(int id, int userid, String time, double sBP) {
		super();
		this.id = id;
		Userid = userid;
		this.time = time;
		SBP = sBP;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return Userid;
	}
	public void setUserid(int userid) {
		Userid = userid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getSBP() {
		return SBP;
	}
	public void setSBP(double sBP) {
		SBP = sBP;
	}
	
}
