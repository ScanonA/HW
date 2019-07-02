package iteration1.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import iteration1.models.Role;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;
// UserController Class used for registering users

public class UserController {
	
	public static void register(String email, String firstName, String lastName, String textPassword, int selectedRole) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException {
		char[] charPassword = textPassword.toCharArray();
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(charPassword, salt);   // encrypts inputted password
		Role role = new Role(selectedRole);	
			if (selectedRole == 3) {		// If the role they selected is author
				User newUser = new User (email, firstName, lastName, salt, password, role);
				SQLiteConnection conn = new SQLiteConnection();
				UserRepository.addUser(conn.getConn(), newUser);
				UserRepository.updateUserStatusByEmail(conn.getConn(), email, 2);
			}
			else {
				User newUser = new User(email, firstName, lastName, salt, password, role);
				SQLiteConnection conn = new SQLiteConnection();
				UserRepository.addUser(conn.getConn(), newUser);
			}
	}
	
	//for registering reviewers and storing their research interests
	public static void registerReviewer(String email, String firstName, String lastName, String textPassword, int selectedRole, String researchInterests) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException{
		char[] charPassword = textPassword.toCharArray();
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(charPassword, salt);   // encrypts inputted password
		Role role = new Role(selectedRole);			
				User newUser = new User(email, firstName, lastName, salt, password, role);
				newUser.setReviewerInterests(researchInterests);
				SQLiteConnection conn = new SQLiteConnection();
				UserRepository.addReviewer(conn.getConn(), newUser);

		
		
	}
	
	public void delete() {
		
	}
}
