package come.example.domain;

public class Pulse {
	private int id;
	private int Userid;
	private String time;
	private double pulse;
	
	public Pulse(int id, int userid, String time, double pulse) {
		super();
		this.id = id;
		Userid = userid;
		this.time = time;
		this.pulse = pulse;
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
	public double getPulse() {
		return pulse;
	}
	public void setPulse(double pulse) {
		this.pulse = pulse;
	}
	
}
