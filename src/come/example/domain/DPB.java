package come.example.domain;

public class DPB {
	private int Userid;
	private int id;
	private String time;
	private double DPB;
	
	public DPB(int userid, int id, String time, double dPB) {
		super();
		Userid = userid;
		this.id = id;
		this.time = time;
		DPB = dPB;
	}
	public int getUserid() {
		return Userid;
	}
	public void setUserid(int userid) {
		Userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getDPB() {
		return DPB;
	}
	public void setDPB(double dPB) {
		DPB = dPB;
	}
	@Override
	public String toString() {
		return "DPB [Userid=" + Userid + ", id=" + id + ", time=" + time
				+ ", DPB=" + DPB + "]";
	}
	
	
}
