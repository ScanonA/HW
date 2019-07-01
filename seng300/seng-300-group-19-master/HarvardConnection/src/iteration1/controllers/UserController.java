package iteration1.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import iteration1.forms.Callback;
import iteration1.forms.Register;
import iteration1.models.Role;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class UserController {
	
	public static void register() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException {
		Register userRegister = new Register();
		userRegister.handle();
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(userRegister.getCallbackArray().get(1).getResponse().toCharArray(), salt);   // encrypts inputted password
		Role role = new Role(Integer.parseInt(userRegister.getCallbackArray().get(3).getResponse()));	
		User newUser = new User(userRegister.getCallbackArray().get(0).getResponse(), salt, password, null, userRegister.getCallbackArray().get(2).getResponse(), role);
		SQLiteConnection conn = new SQLiteConnection();
		UserRepository.addUser(conn.getConn(), newUser);
	}
	
	public static void registerWindowVersion(String Username, String Password, String Email, int ROLE) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException {
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(Password.toCharArray(), salt);   // encrypts inputed password
		Role role = new Role(ROLE);	
		User newUser = new User(Username, salt, password, null, Email, role);
		SQLiteConnection conn = new SQLiteConnection();
		UserRepository.addUser(conn.getConn(), newUser);
	}
	
/*

	public static void register() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException {
		Register userRegister = new Register();
		userRegister.handle();
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(userRegister.getCallbackArray().get(1).getResponse().toCharArray(), salt);
		Role role = new Role(Integer.parseInt(userRegister.getCallbackArray().get(3).getResponse()));	
		User newUser = new User(userRegister.getCallbackArray().get(0).getResponse(), salt, password, null, userRegister.getCallbackArray().get(2).getResponse(), role);
		SQLiteConnection conn = new SQLiteConnection();
		UserRepository.addUser(conn.getConn(), newUser);
	}

 */
	
	public void delete() {
		
	}
}
