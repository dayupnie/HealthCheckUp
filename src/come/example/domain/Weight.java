package come.example.domain;

public class Weight {
	private int id;
	private int userId;
	private String time;
	private double weight;
	
	public Weight(int id, int userId, String time, double weight) {
		super();
		this.id = id;
		this.userId = userId;
		this.time = time;
		this.weight = weight;
	}
	public Weight() {
		id = 0;
		userId = 0;
		time = "";
		weight = 0.;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
