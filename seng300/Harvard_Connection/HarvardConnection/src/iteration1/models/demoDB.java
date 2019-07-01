package iteration1.models;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class demoDB {
	
	Role reviewer = new Role(2);
	Role admin = new Role(1);

	User tempUser1 = new User("Randolph Hearst III",reviewer,true);
	User tempUser2 = new User("Billy Jacking",reviewer,false);
	User tempUser3 = new User("Johnny Two-Nose",reviewer,false);
	User tempUser4 = new User("Jimmy Page",reviewer,true);
	
	ArrayList<User> userDB;
	DefaultListModel<String> listModel;
	
	public void main(String[] args) {
		
		userDB.add(tempUser1);
		userDB.add(tempUser2);
		userDB.add(tempUser3);
		userDB.add(tempUser4);
		//
		
		
		
		listModel.addElement(tempUser1.getUsername());
		listModel.addElement(tempUser2.getUsername());
		listModel.addElement(tempUser3.getUsername());
		listModel.addElement(tempUser4.getUsername());
		//
	}
	
	public ArrayList<User> getUserDB(){
		return this.userDB;
	}
	
	public DefaultListModel<String> getListModel(){
		return this.listModel;
	}

}
