package iteration1.forms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Register extends Form {
		
	public Register() {
		super(new ArrayList<Callback>());
		
		getCallbackArray().add(new Callback("Username:"));
		getCallbackArray().add(new Callback("Password"));
		getCallbackArray().add(new Callback("Email:"));
		
		String[] roles = new String[] {
				"Admin",  
				"Reviewer",
				"Author"
		};
		
		getCallbackArray().add(new Dropdown("Role:", Arrays.asList(roles)));
	}
	
	public void handle() throws IOException {
		for (Callback callback : getCallbackArray()) {
			System.out.println(callback.getPrompt());
			callback.setResponse(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
	}
}
