package iteration1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import iteration1.controllers.UserController;
import iteration1.controllers.UserLogin;
import iteration1.forms.Menu;

public class HarvardConnection {

	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "src/jaas.config");
		
		printMessage();												// Prints Giant ASCII title in console
		System.out.println("Welcome to Harvard Connection!");
		Menu mainMenu = new Menu(Arrays.asList(new String[] {"sign up", "log in", "exit"}));
		
		while(true) {			
			switch (mainMenu.getSelection()) {
				case 1:
					try {
						UserController.register();
					} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException | IOException e) {
						e.printStackTrace();
					}
					
					System.out.println("\nCongrats! When your registration is confirmed, you'll be notified!");
					mainMenu.reset();
					break;
				case 2:
					try {
						new UserLogin();
					} catch (LoginException e) {
						System.out.println(e.getMessage());
						mainMenu.reset();
					}
					
					mainMenu.reset();
					break;
				case 3:
					System.out.println("See ya!");
					System.exit(0);
				default:
					System.exit(0);
			}
		}
	}
	
	private static void printMessage() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/font.txt"));
			String line;
			HashMap<Integer, ArrayList<String>> letterMap = new HashMap<Integer, ArrayList<String>>();
			
			while ((line = reader.readLine()) != null) {	
				int key = Integer.parseInt(line);
				letterMap.put(key, new ArrayList<String>());
				
				for (int i = 0; i < 6; i++) {
					letterMap.get(key).add(reader.readLine().replace("@", ""));
				}
			}
			
			reader.close();
			
			int[] charStream = "HarvardConnCLI".chars().toArray();
			String[] titleMessage = new String[6];
			Arrays.fill(titleMessage, "");
			
			for (int letter : charStream) {
				titleMessage[0] += letterMap.get(letter).get(0);
				titleMessage[1] += letterMap.get(letter).get(1);
				titleMessage[2] += letterMap.get(letter).get(2);
				titleMessage[3] += letterMap.get(letter).get(3);
				titleMessage[4] += letterMap.get(letter).get(4);
				titleMessage[5] += letterMap.get(letter).get(5);
			}
			
			for (String row : titleMessage) {
				System.out.println(row);
			} 
		
		} catch (IOException e) {
			System.out.println("HarvardConnCLI");
		}
		
	}
}
