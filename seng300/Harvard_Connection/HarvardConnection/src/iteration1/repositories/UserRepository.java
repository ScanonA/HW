package iteration1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import iteration1.models.Role;
import iteration1.models.User;

public class UserRepository {
	
	public ResultSet selectAll(Connection conn) {
		return null;		
	}
	
	public static User selectByUsername(Connection conn, String username) throws SQLException {
		String query = "SELECT * FROM USERS WHERE username = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);	
		ResultSet result = stmt.executeQuery();
		User user = new User(result.getString(1), result.getBytes(2), result.getBytes(3), result.getString(4), result.getString(5), new Role(result.getInt(6)));
		System.out.println("First String: \n" + result.getString(1) + "\n String 4: " + result.getString(4) + "\n String 5: " + result.getString(5) +"\nGet Int (6):"+ result.getInt(6)); //debug print statement
		stmt.close();
		result.close();
		return user;
	}
	
	public static void updateLastLoginByUsername(Connection conn, String username) throws SQLException {
		String query = "UPDATE USERS SET last_login = ? WHERE username = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss")));
		stmt.setString(2, username);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void addUser(Connection conn, User user) throws SQLException {
		String query = "INSERT INTO USERS (username, salt, password, email, role_id) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, user.getUsername());
		stmt.setBytes(2, user.getSalt());
		stmt.setBytes(3, user.getPassword());
		stmt.setString(4, user.getEmail());
		stmt.setInt(5, user.getRole().getId());
		stmt.executeUpdate();
		stmt.close();
	}
	
}

