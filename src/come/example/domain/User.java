package come.example.domain;

public class User {
	private int id;
	private String UserName;
	private String UserPassword;
	private String Sex;
	private String Birth;
	private double Height;
	
	public User(int id, String userName, String userPassword, String sex,
			String birth, double height) {
		super();
		this.id = id;
		UserPassword = userPassword;
		UserName = userName;
		Sex = sex;
		Birth = birth;
		Height = height;
	}
	public User( String userName, String userPassword, String sex,
			String birth, double height) {
		super();
	
		UserPassword = userPassword;
		UserName = userName;
		Sex = sex;
		Birth = birth;
		Height = height;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getBirth() {
		return Birth;
	}
	public void setBirth(String birth) {
		Birth = birth;
	}
	public double getHeight() {
		return Height;
	}
	public void setHeight(double height) {
		Height = height;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", UserPassword=" + UserPassword
				+ ", UserName=" + UserName + ", Sex=" + Sex + ", Birth="
				+ Birth + ", Height=" + Height + "]";
	}
	
}