package iteration1.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class UserLoginModule implements LoginModule {
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	private boolean success;
	private String url;
	private boolean debug;
	
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {

		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		
		this.success = false;
		this.debug = "true".equalsIgnoreCase((String) options.get("debug"));
		this.url = (String) options.get("url"); 
		
		if (debug) {
			System.out.println("Login Module Initialized\n");
		}
	}

	@Override
	public boolean login() throws LoginException {
		if (callbackHandler == null) {
			throw new LoginException("Hmm... Something's not quite right.\nError: no CallbackHandler available...");
		}
	
		// we're only using validate() right now but when I put UserLogin back together I'm going to need this
		
		/*
		
		Callback[] callbacks = null; 
		
		while (!success) {			
			callbacks = new Callback[] {
				new NameCallback("Username:"),
				new PasswordCallback("Password:", false)					
			};
			
			try {
				callbackHandler.handle(callbacks);
				
				String username = ((NameCallback) callbacks[0]).getName();
				String password = new String(((PasswordCallback) callbacks[1]).getPassword());	
				
				callbacks[0] = null;
				callbacks[1] = null;
				
				success = validate(username, password);
				
				if (!success) {
					System.out.println("Your username of password is incorrect");
					Menu authenticationFailedMenu = new Menu(Arrays.asList(new String[] {"try again", "exit"}));
					switch (authenticationFailedMenu.getSelection()) {
						case 1:
							break;
						default:
							throw new FailedLoginException("...");
					}
				}
				
			} catch (IOException|UnsupportedCallbackException e) {
				throw new LoginException("Hmm... Something's not quite right.\n" + e.getMessage());
			} catch (FailedLoginException e) {
				throw new FailedLoginException(e.getMessage());
			}
		}
		*/
		
		return success;	
	}

	public boolean validate(String username, String password) {
		boolean passMatch = false;
		SQLiteConnection conn = new SQLiteConnection();				// Connects with database
		
		try {
			
			User user = UserRepository.getUserByEmail(conn.getConn(), username);		// Login selected by Username
			
			System.out.println("selectByUsername");
			
			if (user == null) {
				return passMatch;
			}
			
			passMatch = PBKDF2.decrypt(user.getPassword(), password.toCharArray(), user.getSalt());
			
			if (passMatch) {
				UserRepository.updateLastLoginByEmail(conn.getConn(), username);
			}
			
			conn.getConn().close();			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			if (conn.getConn() != null) {
				try {
					conn.getConn().close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return passMatch;
	}

	@Override
	public boolean commit() throws LoginException {
		return success;
	}

	@Override
	public boolean abort() throws LoginException {
		logout();
		
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		return true;
	}

}
