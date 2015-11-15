package come.example.domain;

public class Imperance {
	private int id;
	private int UserId;
	private String time;
	private double imperance;
	
	public Imperance(int id, int userId, String time, double imperance) {
		super();
		this.id = id;
		UserId = userId;
		this.time = time;
		this.imperance = imperance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getImperance() {
		return imperance;
	}
	public void setImperance(double imperance) {
		this.imperance = imperance;
	}
	
}
