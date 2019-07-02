package iteration1.models;

public class User {

	private String email;
	private String firstName;
	private String lastName;
	private byte[] salt;
	private byte[] password;
	private String lastLogin;
	private String reviewerInterest;		// Used in register page
	private Role role;
	private Integer approved;
	private Integer blacklist;
	private boolean reviewerApproval;
	
	// EMPTY USER
	public User() {
		this.email = null;
		this.firstName = null;
		this.lastName = null;
		this.salt = null;
		this.password = null;
		this.lastLogin = null;
		this.role = null;
		this.approved = null;
		this.blacklist = null;
	}
	
	// FULL USER
	public User(String email, String firstName, String lastName, byte[] salt, byte[] password, String lastLogin, Role role, Integer approved, Integer blacklist) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.lastLogin = lastLogin;
		this.role = role;
		this.approved = approved;
		this.blacklist = blacklist;
		
	}
	
	// LOGIN USER
	public User(String email, String firstName, String lastName, byte[] salt, byte[] password, Role role) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.lastLogin = null;
		this.role = role;
		this.approved = null;
		this.blacklist = null;
	}

	// PASSIVE USER
	public User(String email, String firstName, String lastName, Role role, int approved, int blacklist) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = null;
		this.password = null;
		this.lastLogin = null;
		this.role = role;
		this.approved = approved;
		this.blacklist = blacklist;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Integer getApproved() {
		return approved;
	}


	public void setApproved(Integer approved) {
		this.approved = approved;
	}


	public Integer getBlacklist() {
		return blacklist;
	}


	public void setBlacklist(Integer blacklist) {
		this.blacklist = blacklist;
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
	
	

	
	public void setReviewerInterests(String interests) {
		this.reviewerInterest=interests;
	}
	public String getReviewerInterests() {
		return this.reviewerInterest;
	}
	
	
}
