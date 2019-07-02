package iteration1.models;

public class Upload {
	private int id;
	private String email;
	private String filepath;
	private String adminStatus;
	
	public Upload(int id, String email, String filepath, String adminStatus) {
		this.setId(id);
		this.setEmail(email);
		this.setFilepath(filepath);
		this.setAdminStatus(adminStatus);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
}
