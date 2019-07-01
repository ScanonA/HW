package iteration1.controllers;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import iteration1.forms.Login;

public class UserLogin 	{
	boolean logout;
	
	public UserLogin() throws LoginException {		
		LoginContext loginContext = null;
		loginContext = new LoginContext("jaascli", new Login());
		loginContext.login();
		
		this.logout = true;	
	}
}
