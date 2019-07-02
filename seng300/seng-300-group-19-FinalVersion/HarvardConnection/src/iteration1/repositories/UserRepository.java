package iteration1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import iteration1.models.Role;
import iteration1.models.User;

public class UserRepository {
	
	public ResultSet selectAll(Connection conn) {
		return null;		
	}
	
	// FOR SIGNING UP/DELETING USERS
	
	public static void addUser(Connection conn, User user) throws SQLException {
		String query = "INSERT INTO USERS (email, first_name, last_name, salt, password, role_id) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getFirstName());
		stmt.setString(3, user.getLastName());
		stmt.setBytes(4, user.getSalt());
		stmt.setBytes(5, user.getPassword());
		stmt.setInt(6, user.getRole().getId());
		stmt.executeUpdate();
		stmt.close();
	}
	
	/*
	 * addReviewer SQL query
	 * 
	 * Used in SignUp process due to interests added in SignUp process for reviewer
	 */
	public static void addReviewer(Connection conn, User user) throws SQLException {
		
		String query = "INSERT INTO USERS (email, first_name, last_name, salt, password, role_id, interests) VALUES(?, ?, ?, ?, ?, ?,?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getFirstName());
		stmt.setString(3, user.getLastName());
		stmt.setBytes(4, user.getSalt());
		stmt.setBytes(5, user.getPassword());
		stmt.setInt(6, user.getRole().getId());
		stmt.setString(7, user.getReviewerInterests());
		stmt.executeUpdate();
		stmt.close();
	}
	
	
	
	public static void deleteUser(Connection conn, String email) throws SQLException {
		String query = "DELETE FROM USERS WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		stmt.executeUpdate();
		stmt.close();
	}
	
	// FOR SIGNING IN AND OUT
	
	public static User getUserByEmail(Connection conn, String email) throws SQLException {
		String query = "SELECT * FROM USERS WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);	
		ResultSet result = stmt.executeQuery();
		User user = new User(result.getString(1), result.getString(2), result.getString(3), result.getBytes(4), result.getBytes(5), result.getString(6), new Role(result.getInt(7)), result.getInt(7), result.getInt(8));
		stmt.close();
		result.close();
		return user;
	}
	
	public static void updateLastLoginByEmail(Connection conn, String email) throws SQLException {
		String query = "UPDATE USERS SET last_login = ? WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss")));
		stmt.setString(2, email);
		stmt.executeUpdate();
		stmt.close();
	}
	
	// FOR SELECTING REVIEWERS WHEN YOU UPLOAD A PAPER
	
	public static ArrayList<User> getReviewers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<>();
		
		String query = "SELECT * FROM USERS WHERE role_id = ? AND approved = ? AND blacklist = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, 2);
		stmt.setInt(2, 1);
		stmt.setInt(3, 0);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			users.add(new User(result.getString(1), result.getString(2), result.getString(3), new Role(result.getInt(7)), result.getInt(7), result.getInt(8)));
		}
		
		stmt.close();
		result.close();
		return users;
	}
	
	/*
	 * For getting all reviewers
	 */
	public static ArrayList<User> getAllReviewers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<>();
		
		String query = "SELECT * FROM USERS WHERE role_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, 2);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			users.add(new User(result.getString(1), result.getString(2), result.getString(3), new Role(result.getInt(7)), result.getInt(7), result.getInt(8)));
		}
		
		stmt.close();
		result.close();
		return users;
	}
	
	// FOR ACCEPTING AND DENYING NEW USERS
	
	public static ArrayList<User> getUnapprovedUsers(Connection conn) throws SQLException {		
		ArrayList<User> users = new ArrayList<>();
		
		String query = "SELECT * FROM USERS WHERE approved IS NULL";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			users.add(new User(result.getString(1), result.getString(2), result.getString(3), new Role(result.getInt(7)), result.getInt(7), result.getInt(8)));
		}
		
		stmt.close();
		result.close();
		return users;
	}
		
	public static void updateUserStatusByEmail(Connection conn, String email, int status) throws SQLException {
		String query = "UPDATE USERS SET approved = ? WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, status);
		stmt.setString(2, email);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void updateUserStatusByName(Connection conn, String name, int status) throws SQLException{
		String[] firstLast = name.split(" ");
		String query = "UPDATE USERS SET approved = ? WHERE first_name = ? AND last_name = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, status);
		stmt.setString(2, firstLast[0]);
		stmt.setString(3, firstLast[1]);
		stmt.executeUpdate();
		stmt.close();
	}
	
	// BLACKLISTING A USER
	public static void updateBlacklistByEmail(Connection conn, String email, int blacklistvalue) throws SQLException {
		String query = "UPDATE USERS SET blacklist = ? WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, blacklistvalue);
		stmt.setString(2, email);
		stmt.executeUpdate();
		stmt.close();
	}
	
	/*
	 * Get Blacklist Value
	 */
	public static Integer getBlacklistValueByEmail(Connection conn, String email) throws SQLException {
		String query = "SELECT * FROM USERS WHERE email = ?";
		System.out.println("after query is initialized"); // PRINT DEBUG STATEMENT
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		Integer value = result.getInt(9);
		
		stmt.close();
		result.close();
		return (value);
	}
	
	//Retrieves User from DB from name. Input Format: "John Smith"
	//Assumes there is only one user per name (i.e. no duplicates)
	public static Integer getApprovalByName (Connection conn, String username) throws SQLException {
		String[] firstLast = username.split(" ");
//		System.out.println("firstLast: " + firstLast[2]); // PRINT DEBUG STATEMENT
		System.out.println("///////AFTER USERNAME SPLIT/////// WE'RE IN USERREPOSITORY. JAVA");  // PRINT DEBUG STATEMENT
		System.out.println("firstLast [0]: " + firstLast[0]); // PRINT DEBUG STATEMENT
		System.out.println("firstLast [1]: " + firstLast[1]); // PRINT DEBUG STATEMENT
		String query = "SELECT * FROM USERS WHERE first_name = ? AND last_name = ?";
		System.out.println("after query is initialized"); // PRINT DEBUG STATEMENT
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, firstLast[0]);
		stmt.setString(2, firstLast[1]);
		ResultSet result = stmt.executeQuery();
		Integer value = result.getInt(8);
		
		stmt.close();
		result.close();
		return (value);
		
	}
	
	/*
	 * Get Approval Value from Email
	 */
	public static Integer getApprovalByEmail(Connection conn, String email) throws SQLException {
		String query = "SELECT * FROM USERS WHERE email = ?";
		System.out.println("after query is initialized"); // PRINT DEBUG STATEMENT
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		Integer value = result.getInt(8);
		
		stmt.close();
		result.close();
		return (value);
	}
	
	
	
	/*
	 * Get Number Of Rows In Repository
	 */
	public static Integer getNumberOfRows(Connection conn) throws SQLException {
		
		String query = "SELECT count(*) AS rowcount FROM USERS";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet result = stmt.executeQuery();
		result.next();
		int count = result.getInt("rowcount");
		
		stmt.close();
		result.close();
		
		return count;
	}
	
	
	/*
	 * Retrieves reviewers research interests
	 */
	public static String getInterestsByName(Connection conn, String username) throws SQLException{
		String[] firstLast = username.split(" ");
		String query = "SELECT * FROM USERS WHERE first_name = ? AND last_name = ?";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, firstLast[0]);
		stmt.setString(2, firstLast[1]);
		ResultSet result = stmt.executeQuery();
		String interests = result.getString(10);
		if (interests == null) {
			interests = " ";
		}
		
		stmt.close();
		result.close();
		return(interests);
		
	}
}

