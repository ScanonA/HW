package iteration1.GUI;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iteration1.models.Role;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

//GUI interface where the admin will view reviewers and be able to accept/reject them as reviewers

public class ManageReviewers extends JFrame {
	
	private JPanel contentPane;
	private JList reviewerList;
	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageReviewers frame = new ManageReviewers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public ManageReviewers()  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*SQLiteConnection conn = new SQLiteConnection();
		System.out.println(conn.getUrl());
		try {
			ArrayList<User> ReviewerList = UserRepository.listReviewers(conn.getConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//fake users for testing purposes
		Role reviewer = new Role(2);
		Role admin = new Role(1);

		User tempUser1 = new User("Randolph Hearst III",reviewer,true);
		User tempUser2 = new User("Billy Jacking",reviewer,false);
		User tempUser3 = new User("Johnny Two-Nose",reviewer,false);
		User tempUser4 = new User("Jimmy Page",reviewer,true);
		
		ArrayList<User> userDB = new ArrayList<User>();
		userDB.add(tempUser1);
		userDB.add(tempUser2);
		userDB.add(tempUser3);
		userDB.add(tempUser4);
		//
		
		
		DefaultListModel<String> listModel = new DefaultListModel();
		listModel.addElement(tempUser1.getUsername());
		listModel.addElement(tempUser2.getUsername());
		listModel.addElement(tempUser3.getUsername());
		listModel.addElement(tempUser4.getUsername());
		//

		JList<User> reviewerList = new JList(listModel);
		reviewerList.setBounds(0, 0, 225 ,300);
		contentPane.add(reviewerList);
		
		reviewerList.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()==false) {
					reviewerList.getSelectedIndex();
					String selectedUserName = getUserFromIndex(listModel,reviewerList.getSelectedIndex());
					User selectedUser = userLookupFromString(selectedUserName,userDB);
					AuthorizeReviewers authorizeReviewer = new AuthorizeReviewers();
					authorizeReviewer.setUser(selectedUser);
					authorizeReviewer.drawWindow();
					contentPane.setVisible(false);
					dispose();
					
					authorizeReviewer.setVisible(true);
				}
				
			}
			
		});
		
		
		
		
		
	}
	
	private String getUserFromIndex(DefaultListModel<String> list, int index) {
		return list.get(index);
		
		
	}
	
	private User userLookupFromString(String name,ArrayList<User> DB) {
		for (int i = 0; i < DB.size();i++) {
			if (name.equals(DB.get(i).getUsername())) {
				return DB.get(i);
			}
			
		}
		return null;
	}
	
	
	
	

}
