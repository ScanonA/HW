package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import java.awt.Font;

//GUI interface where the admin will view reviewers and be able to accept/reject them as reviewers

public class ManageReviewers extends JInternalFrame {
	
	private JPanel contentPane;
	private JList reviewerList;
	private static ManageReviewers myInstance;		// Instance of Class | Used in having one frame visible at all times
	private String selectedUsername;
	private Integer selectedApprovalStatus;
	private String selectedUsersInterests;
	private String selectedUsersEmail;
	
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
		SQLiteConnection conn = new SQLiteConnection();		// Connection to database established
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		/*
		 * Background J Panel
		 */
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		/*
		 * "Go Back" Button | lets user exit back to AdminMenu
		 */
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAdminMenu();								// Call to method that changes frame
			}
		});
		btnGoBack.setBounds(912, 11, 86, 23); 	// Sets bounds to button
		contentPane.add(btnGoBack);				// Adds button to content pane
		
		
		
		

		
		try {
			ArrayList<User> reviewerArray = UserRepository.getAllReviewers(conn.getConn());
			DefaultListModel<String> reviewerList = new DefaultListModel();

			for (int i = 0; i < reviewerArray.size();i++) {
				String tempName = (reviewerArray.get(i).getFirstName() + " " + reviewerArray.get(i).getLastName());
				reviewerList.addElement(tempName);
				}
			
			JList<User> List = new JList(reviewerList);
			List.setFont(new Font("Arial", Font.BOLD, 16));
			List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			List.setLayoutOrientation(JList.VERTICAL);
			List.setBounds(392,199,42,62);
			contentPane.add(List);
			List.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					SQLiteConnection conn = new SQLiteConnection();		// Connection to database established
					if (!e.getValueIsAdjusting()) {
						int temp = List.getSelectedIndex();
						selectedUsername = getUserFromIndex(reviewerList,temp);
						selectedUsersEmail = reviewerArray.get(temp).getEmail();
						try {
							selectedUsersInterests = UserRepository.getInterestsByName(conn.getConn(),selectedUsername);		// Get Interests from database
							selectedApprovalStatus = UserRepository.getApprovalByName(conn.getConn(), selectedUsername);		// Gets Approval Status
							goToAuthorizeReviewers();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println("ValueChangedListSelection Event Error");		// DEBUG PRINT STATEMENT
							e1.printStackTrace();
							}
//						goToAuthorizeReviewers();
					}
				}
			});
			
			/*
			 *  Creation of Scroll Pane
			 */
			JScrollPane scrollPane = new JScrollPane(List);	// Puts JList of reviewers into scroll pane
			scrollPane.setBounds(390, 237, 227, 329);		// set bounds
			contentPane.add(scrollPane);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		/*
		 * Label for list
		 */
		JLabel lblApplyingReviewers = new JLabel("Reviewer List");
		lblApplyingReviewers.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblApplyingReviewers.setBounds(448, 210, 111, 16);
		contentPane.add(lblApplyingReviewers);
		
		/*
		 * Label for Instructions
		 */
		JLabel lblGuideLine = new JLabel("Select a Reviewer to change their status or Blacklist them");
		lblGuideLine.setForeground(Color.BLACK);
		lblGuideLine.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGuideLine.setBounds(257, 162, 494, 43);
		contentPane.add(lblGuideLine);

				
		
		
		/*
		 * JLabel | Used for displaying background image
		 */
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(387, 234, 233, 334);
		contentPane.add(panel);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground3.jpg"));
		label.setBounds(212, 145, 584, 448);
		contentPane.add(label);

		/*
		 * Canvas used for background
		 */
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(198, 132, 612, 474);
		contentPane.add(canvas);
	}
	
	
	private String getUserFromIndex(DefaultListModel<String> list, int index) {
		return list.get(index);
		
		
	}
	
	/*
	 * Method to go to AdminMenu
	 */
	private void goToAdminMenu() {
		AdminMenu nw = AdminMenu.getInstance();	// Creates new Window
		 nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
		 getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
		 nw.setVisible(true);						// Sets Instance frame visible
		 
		 try {
		     nw.setMaximum(true);				// Sets window to max size of DesktopPane
		 } catch (Exception e1) {
			 System.out.println(e1);
		 }
		 
		 getDesktopPane().repaint();			// Repaints the DesktopPane
		 getDesktopPane().remove(myInstance);	// Removes the instance of current Class
		 myInstance = null;						// Sets instance to null
	}
	
	
	private void goToAuthorizeReviewers() {
		AuthorizeReviewers nw =  AuthorizeReviewers.getInstance(selectedUsername, selectedApprovalStatus, selectedUsersInterests, selectedUsersEmail);		// Creates new Window
		nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
		getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
		nw.setVisible(true);						// Sets Instance frame visible
		 
		try {
		    nw.setMaximum(true);				// Sets window to max size of DesktopPane
		} catch (Exception e1) {
			System.out.println(e1);
		}
		 
		getDesktopPane().repaint();			// Repaints the DesktopPane
		getDesktopPane().remove(myInstance);	// Removes the instance of current Class
		myInstance = null;						// Sets instance to null
	}
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static ManageReviewers getInstance() {	
	    if (myInstance == null) {					// If instance is null, create new instance
	        myInstance = new ManageReviewers();		// Set new instance
	    }
	    return myInstance;							// Return instance
	}
}
