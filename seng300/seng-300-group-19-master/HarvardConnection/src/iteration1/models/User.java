package iteration1.models;

import java.io.Reader;

public class User {

	private String username;
	private byte[] salt;
	private byte[] password;
	private String lastLogin;
	private String email;
	private Role role;
	private boolean reviewerApproval;
	
	public User() {
		this.username = null;
		this.salt = null;
		this.password = null;
		this.lastLogin = null;
		this.email = null;
		this.role = null;
	}
	
	
	public User(String username, byte[] salt, byte[] password, String lastLogin, String email, Role role) {
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.lastLogin = lastLogin;
		this.email = email;
		this.role = role;
		
	}
	
		
	public User(String username, byte[] salt, byte[] password, String lastLogin, String email, Role role,boolean reviewerApproval) {
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.lastLogin = lastLogin;
		this.email = email;
		this.role = role;
		this.reviewerApproval = reviewerApproval;
	}
	
	//Constructor used for creating User objects for purpose of testing "ManageReviewers" class
	public User(String username,Role role,boolean reviewerApproval) {
		this.username = username;
		this.role = role;
		this.reviewerApproval = reviewerApproval;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getRoleID() {		// Get ID this method is also in Role.java but had to place here for login class functionality.
		return role.getId();	
	}
	
	public void setApprovedToReview (boolean approval) {
		this.reviewerApproval = approval;
	}
	public boolean getApprovedToReview () {
		return this.reviewerApproval;
	}
	
	public void switchApproval() {
		if (this.reviewerApproval) {
			this.setApprovedToReview(false);
		}else {
			this.setApprovedToReview(true);
		}
	}
	
}
