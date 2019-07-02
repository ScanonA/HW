package iteration1.repositories;

// This class acts as a container for information when logging in. 
// This will allow us to pass through different windows with access to the logged in account

// Setters are activated when logging in.
// methods from queries are activated to set up the information needed.

// FOR NOW WE ARE ONLY SETTING email WHEN LOGGING IN
// With email, we can get everything about a user from the Database

// Getters are used whenever we need the logged in users information
// Getters mixed with the queries within our repositories will allow us to access everything we need from a logged in user

public class LogInDataContainer {
	
	public static String email;
	public static String first_name;
	public static String last_name;
	public static String role;
	public static Integer role_id;
	public static Integer approved;
	public static Integer blacklist;
	
	public LogInDataContainer(){
	
	}
	
	
	
	/*
	 *  Setters
	 */
	public static void setEmail(String inputEmail){
		email = inputEmail;
	}
	
	public static void setFirstName(String inputFirstName) {
		first_name = inputFirstName;
	}
	
	public static void setLastName(String inputLastName) {
		last_name = inputLastName;
	}
	
	public static void setRoleID(Integer inputRoleID) {
		setRole(inputRoleID);
		role_id = inputRoleID;
	}
	
	public static void setApproved(Integer inputApproved) {
		approved = inputApproved;
	}
	
	public static void setBlacklist(Integer inputBlacklist) {
		blacklist = inputBlacklist;
	}
	
	public static void setRole(Integer roleID) {
		if (roleID == 1) {
			role = "Administrator";
		}
		if (roleID == 2) {
			role = "Reviewer";
		}
		if (roleID == 3) {
			role = "Author";
		}
	}
	
	
	
	
	
	// Getters
	public static String getEmail(){
		return email;
	}
	
	public static String getFirstName() {
		return first_name;
	}
	
	public static String getLastName() {
		return last_name;
	}
	
	public static Integer getRoleID() {
		return role_id;
	}
	
	public static Integer getApproved() {
		return approved;
	}
	
	public static Integer getBlacklist() {
		return blacklist;
	}
	
	public static String getRole() {
		return role;
	}

}
