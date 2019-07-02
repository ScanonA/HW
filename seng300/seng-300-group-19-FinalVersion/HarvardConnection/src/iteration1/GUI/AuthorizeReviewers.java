package iteration1.GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuthorizeReviewers extends JInternalFrame{

	private User user;
	private static String reviewerUsername;
	private static Integer reviewerApprovedStatusNumber;
	private JPanel contentPane;
	private static AuthorizeReviewers myInstance;		// Instance of Class | Used in having one frame visible at all times
	private static String reviewerInterest;				//String of reviewers research interests
	private static String selectedUsersEmail;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorizeReviewers frame = new AuthorizeReviewers(reviewerUsername, reviewerApprovedStatusNumber, reviewerInterest, selectedUsersEmail);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * AuthorizeReviewers: Username and approvedValue as input
	 * @param selectedUsersEmail 
	 * 				
	 */
	public AuthorizeReviewers(String SelectedUsername, Integer approvedValue, String interests, String UsersEmail) {
//		SQLiteConnection conn = new SQLiteConnection();	// Establish connection to database
		reviewerUsername = SelectedUsername;			// Sets username when Reviewer is clicked in ManageReviewers.java
		reviewerApprovedStatusNumber = approvedValue;	// Sets approvedStatusNumber
		reviewerInterest = interests;
		selectedUsersEmail = UsersEmail;
		
		
		SQLiteConnection conn = new SQLiteConnection();	// Establish connection to database
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/**
		 * JLabel | Shows reviewer name
		 */
		JLabel userName = new JLabel("Reviewer: " + reviewerUsername);
		userName.setForeground(Color.BLACK);
		userName.setFont(new Font("Tahoma", Font.BOLD, 17));
		userName.setBounds(273,215,405,40);
		contentPane.add(userName);
		
		
		/**
		 * JLabel | Shows approval status of selected Reviewer	
		 */
		JLabel approval = new JLabel ("Approval Status: " + approvedToReview(reviewerApprovedStatusNumber));
		approval.setForeground(Color.BLACK);
		approval.setFont(new Font("Tahoma", Font.BOLD, 15));
		approval.setBounds(273,278,405,40);
		contentPane.add(approval);
		
		
		/**
		 * JLabel | Shows reviewers research interests
		 */
		
		JLabel researchInterest = new JLabel("<html>Research Interests:<br/></hmtl> " + reviewerInterest);
		researchInterest.setForeground(Color.BLACK);
		researchInterest.setVerticalAlignment(SwingConstants.TOP);
		researchInterest.setFont(new Font("Tahoma", Font.BOLD, 14));
		researchInterest.setBounds(273,322,460,131);
		contentPane.add(researchInterest);
		
		
		/**
		 * JButton | "Go Back" Button		
		 */
		JButton btnGoBack = new JButton("Go Back");						// Create New JButton
		btnGoBack.addActionListener(new ActionListener() {				// Listener for actions
			public void actionPerformed(ActionEvent e) {				// ActionPerformed
				
				goToManageReviewersMenu();
			}
			
		});
		btnGoBack.setBounds(898,11,100,25);	// Sets location bounds for button
		contentPane.add(btnGoBack); 		// adds button to pane
		
		
		
		
		
		// JButton | "Accept Reviewer" Button | When pressed, changes the selected reviewers "approved" value to 1 | 1 means allowed to review papers
		JButton btnAcceptReviewer = new JButton ("Accept Reviewer");
		btnAcceptReviewer.setForeground(new Color(0, 128, 0));
		btnAcceptReviewer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAcceptReviewer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateRoleIDInDatabase();			// Calls updateRoleIdInDatabase method in this file (It's below)
				unBlacklistReviewer();
				JOptionPane.showMessageDialog(contentPane, "The selected user has been ACCEPTED and is now able to review assigned papers");	// Confirmation Message
				
				goToManageReviewersMenu();
			}
			
		});
		btnAcceptReviewer.setBounds(431,477,146,25);	// Sets location bounds for button
		contentPane.add(btnAcceptReviewer);				// adds button to pane
		
		
		
		
		
		// JButton | "Decline Reviewer" Button | When pressed, changes the selected reviewers "approved" value to 0 | 0 means blacklisted 
		// Might want to change to different value
		JButton btnDeclineReviewer = new JButton ("Decline Reviewer");
		btnDeclineReviewer.setForeground(new Color(255, 0, 0));
		btnDeclineReviewer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeclineReviewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				declineReviewer();																// Calls DeclineReviewer method in this file (It's below)
				JOptionPane.showMessageDialog(contentPane,										// JOptionPane pop up
						"The selected user has been DECLINED and is unable to review papers");	// Confirmation Message
				goToManageReviewersMenu();														// Call to method that changes window
			}
			
		});
		btnDeclineReviewer.setBounds(587,477,146,25);
		contentPane.add(btnDeclineReviewer);
		
		
		
		/*
		 * JLabel | Used for displaying background image
		 */
		
		JButton BlackList = new JButton("Blacklist Reviewer");
		BlackList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				blacklistReviewer();																// Calls BlackList method in this file (It's below)
				JOptionPane.showMessageDialog(contentPane,											// JOptionPane pop up
						"The selected user has been BLACKLISTED and is unable to review papers");		// Confirmation Message
				goToManageReviewersMenu();															// Call to method that changes window
			}
		});
		BlackList.setFont(new Font("Tahoma", Font.BOLD, 11));
		BlackList.setBounds(273, 478, 146, 25);
		contentPane.add(BlackList);
		
		JLabel label_1 = new JLabel("Email: " + selectedUsersEmail);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(273, 248, 405, 40);
		contentPane.add(label_1);
		
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\MainMenuBack.jpg"));
		label.setBounds(248, 189, 512, 359);
		contentPane.add(label);

		/*
		 * Canvas used for background
		 */
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(240, 180, 528, 378);
		contentPane.add(canvas);
	}
	
	
	
	
	
	
	// Updates the Reviewers "approved" column value to 1 | 1 meaning they can review papers
	private void updateRoleIDInDatabase() {				
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			System.out.println("FOLLOWING BELONGS TO AuthorizeReviewers.java:\nthis.username:" + this.reviewerUsername + // DEBUG PRINT STATEMENT TO SEE this.username
					"\nthis.roleid: " + this.reviewerApprovedStatusNumber + "\nABOVE BELONGS TO AuthorizeReviewers");					// DEBUG PRINT STATEMENT TO SEE this.roleid
			
//			UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);			// Used to update database column "approved" with role id, but now changed to static value 1
			
			UserRepository.updateUserStatusByName(conn.getConn(), this.reviewerUsername, 1); // Updates Reviewer approved status to 1  |  1 means they're approved to review
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/*
	 *  DECLINES REVIEWER APPLICATION | i.e. "approved" column value to 0  (0 means blacklisted, might be a problem | Update: New blacklist column now)
	 *
	 * Should discuss on whether it should be kept as NULL? or a different value than 0 to specify they were DECLINED and not blacklisted
	 * UPDATE April 7th 2019: 
	 *	New blacklist column exists. Not a problem anymore
	 */
	private void declineReviewer() {				
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			System.out.println("\n\nDECLINE REVIEWER\nFOLLOWING BELONGS TO AuthorizeReviewers.java:\nthis.username:" + this.reviewerUsername + // DEBUG PRINT STATEMENT TO SEE this.username
					"\nthis.roleid: " + this.reviewerApprovedStatusNumber + "\nABOVE BELONGS TO AuthorizeReviewers");					// DEBUG PRINT STATEMENT TO SEE this.roleid
			
//			UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);			// Used to update database column "approved" with role id, but now changed to static value 1
			
			UserRepository.updateUserStatusByName(conn.getConn(), this.reviewerUsername, 2); // Updates Reviewer approved status to 2  |  2 means they've been declined
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * used to blacklsit a user
	 */
	private void blacklistReviewer() {
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			UserRepository.updateBlacklistByEmail(conn.getConn(), this.selectedUsersEmail, 1); // Updates Reviewer approved status to 2  |  2 means they've been declined
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Used to undo a blacklist on a user
	 */
	private void unBlacklistReviewer() {
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			UserRepository.updateBlacklistByEmail(conn.getConn(), this.selectedUsersEmail, 0); // Updates Reviewer approved status to 2  |  2 means they've been declined
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
// Method used to setID (Not used due to constructor setting it up already. Keeping here just incase we need in the future)
	public void setRoleID(Integer ID) {
		this.reviewerApprovedStatusNumber = ID;
		
	}	
	
	
// Method used to setUsername (Not used due to constructor setting it up already. Keeping here just incase we need in the future)
	public void setUsername(String Username) {
		this.reviewerUsername = Username;
	}
	
	
// Method used to see if Reviewer is approved for reviewing papers
	private String approvedToReview(Integer approvedStatus){	// Input is the Reviewers "approved" status number from database
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			if (UserRepository.getBlacklistValueByEmail(conn.getConn(), selectedUsersEmail) == 0 && approvedStatus == 1) {								// If number is 1, then user is allowed to review papers
				return ("Authorized");
			}
			else if (UserRepository.getBlacklistValueByEmail(conn.getConn(), selectedUsersEmail) == 1) {
				return ("BLACKLISTED");							// Else they're unauthorized
			}
			else {
				return ("Unauthorized");							// Else they're unauthorized
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "authorizaion error";
	}
	
	
	/*
	 * Method to change window to past one
	 */
	private void goToManageReviewersMenu() {
		ManageReviewers nw = ManageReviewers.getInstance();	// Creates new Window
		nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
		getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
		nw.setVisible(true);						// Sets Instance frame visible
		 
		try {
		    nw.setMaximum(true);					// Sets window to max size of DesktopPane
		} catch (Exception e1) {
			System.out.println(e1);
		}
		 
		getDesktopPane().repaint();				// Repaints the DesktopPane
		getDesktopPane().remove(myInstance);	// Removes the instance of current Class
		myInstance = null;						// Sets instance to null
	}
	
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AuthorizeReviewers getInstance(String inputUsername, Integer StatusNumber, String reviewerInterest, String selectedUsersEmail) {	
	    if (myInstance == null) {																	// If instance is null, create new instance
	        myInstance = new AuthorizeReviewers(inputUsername, StatusNumber, reviewerInterest, selectedUsersEmail);		// Set new instance
	    }
	    return myInstance;							// Return instance
	}
}
