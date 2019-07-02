package iteration1.controllers;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;


public class UserLogin 	{
	boolean logout;
	
	public UserLogin() throws LoginException {		
		
		// I'm gonna put this back together at some point...
		
		LoginContext loginContext = null;
		// loginContext = new LoginContext("jaascli", new Login());
		loginContext.login();
		
		this.logout = true;	
	}
}
