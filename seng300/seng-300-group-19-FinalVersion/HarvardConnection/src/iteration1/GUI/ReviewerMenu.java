package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;			//   Added for copying files
import java.nio.file.Path;				// Added for copying files
import static java.nio.file.StandardCopyOption.*; // Added for copy options
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;			// File visitor for visiting trees
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;		// Added to choose files that are going to be copied
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.Review;
import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ReviewerMenu extends JInternalFrame {
	
	private static ReviewerMenu myInstance;		// Instance of Class | Used in having one frame visible at all times
	private JPanel contentPane;
	DefaultListModel<Integer> uploadIDList = new DefaultListModel();		// Tried to make a list
	DefaultListModel<String> authorList = new DefaultListModel();			// TO show assigned papers
	DefaultListModel<String> fileList = new DefaultListModel();	
	DefaultListModel<String> emailList = new DefaultListModel();
	
	private int index;
	private String authorListSelectedEmail;
	private JList assignedAuthorList;
	private JLabel authorlbl;
	private JLabel emaillbl;
	private JLabel idlbl;
	private JLabel TextSelectedPaperName;
	private JLabel lblSelectedPaperName;
	private JLabel lblSelectedPaperStatus;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewerMenu frame = new ReviewerMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReviewerMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SQLiteConnection conn = new SQLiteConnection();
		
		
		/*
		 *  The following Try block is here to get the Logged In Reviewers assigned PaperList and AuthorList
		 */
		try {
			ArrayList<Review> uploadIDArray = ReviewRepository.getReviewsByReviewer(conn.getConn(), LogInDataContainer.getEmail());	// Takes Logged In Reviwers email and puts all Reviews into uploadIDArray
			
			
			for (int i = 0; i < uploadIDArray.size();i++) {		// Iterate through uploadIDArray and put uploadID's into uploadIDList
				int tempID = (uploadIDArray.get(i).getUploadId());
				uploadIDList.addElement(tempID);					//Resulting list is the upload ID's assigned to the logged in Reviewer
			}
			
			
			for (int x = 0; x < uploadIDList.size();x++) {
/*
 * 				Debug Print Statements:
 */
//				System.out.println("\nThe Val of X: " + x);
//				ArrayList<Upload> authorEmailArray = UploadRepository.getUploadsByID(conn.getConn(), uploadIDList.get(x));
//				System.out.println("AUTHOR EMAIL: " + authorEmailArray.get(0).getEmail());
//				String tempEmail = (authorEmailArray.get(0).getEmail());
//				authorList.addElement(tempEmail);
				//System.out.println("\nThe Val of X: " + x);
				
				ArrayList<Upload> authorEmailArray = UploadRepository.getUploadsByID(conn.getConn(), uploadIDList.get(x));
				String fullFilepath = authorEmailArray.get(0).getFilepath();
				String email = authorEmailArray.get(0).getEmail();
				String filename;
				if(fullFilepath.indexOf('/') > 0) {
					filename = fullFilepath.substring(fullFilepath.lastIndexOf('/') + 1);
				} else {
					filename = fullFilepath.substring(fullFilepath.lastIndexOf('\\') + 1);
				}
				System.out.println("FILEPATH: " + filename);
				String tempEmail = (filename);
				authorList.addElement(tempEmail);
				fileList.addElement(fullFilepath);
				emailList.addElement(email);
			}
			
			/*
			 * Debug Print Statements
			 * Prints out a list of UploadID's of assigned papers for the logged in user
			 * List of authors
			 * List of files
			 */
//			System.out.println("\n\nUploadIDList: " + uploadIDList);
//			System.out.println("authorList Emails: " + authorList);	
//			System.out.println("authorList Emails: " + fileList);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * JPanel containing all descriptive aspects of selected paper
		 */
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(417, 263, 395, 174);
		contentPane.add(panel);
		panel.setLayout(null);
		
		/*
		 * JLabel to display Author's Name
		 */
		JLabel authorName = new JLabel("Author name:");
		authorName.setFont(new Font("Arial", Font.BOLD, 13));
		authorName.setBounds(12, 12, 133, 15);
		panel.add(authorName);
		
		/*
		 * JLabel to display Author's Name
		 */
		authorlbl = new JLabel("New label");
		authorlbl.setFont(new Font("Arial", Font.BOLD, 13));
		authorlbl.setBounds(18, 27, 208, 15);
		authorlbl.setVisible(false);
		panel.add(authorlbl);
		
		/*
		 * JLabel to display Author's Email of selected Paper
		 */
		JLabel emailbl = new JLabel("Email:");
		emailbl.setFont(new Font("Arial", Font.BOLD, 13));
		emailbl.setBounds(12, 53, 70, 15);
		panel.add(emailbl);
		
		/*
		 * JLabel to display Author's Email of selected Paper
		 */
		emaillbl = new JLabel("New label");
		emaillbl.setFont(new Font("Arial", Font.BOLD, 13));
		emaillbl.setBounds(18, 69, 296, 15);
		emaillbl.setVisible(false);
		panel.add(emaillbl);
		
		/*
		 * JLabel to display selected Paper ID:
		 */
		JLabel uploadIDlbl = new JLabel("Paper ID:");
		uploadIDlbl.setFont(new Font("Arial", Font.BOLD, 13));
		uploadIDlbl.setBounds(219, 12, 65, 15);
		panel.add(uploadIDlbl);
		
		/*
		 * JLabel to display selected PaperID number
		 */
		idlbl = new JLabel("New label");
		idlbl.setFont(new Font("Arial", Font.BOLD, 13));
		idlbl.setBounds(285, 12, 208, 15);
		idlbl.setVisible(false);
		panel.add(idlbl);
		
		/*
		 *  Label above Scrollable Pane
		 */
		
		JPanel BackPanelForDescription = new JPanel();
		BackPanelForDescription.setBackground(Color.DARK_GRAY);
		BackPanelForDescription.setBounds(415, 261, 399, 178);
		contentPane.add(BackPanelForDescription);
		
		/*
		 * Label for selecting assigned paper
		 */
		JLabel lblSelectAssignedAuthor = new JLabel("Select Assigned Paper");
		lblSelectAssignedAuthor.setForeground(Color.DARK_GRAY);
		lblSelectAssignedAuthor.setFont(new Font("Arial", Font.BOLD, 15));
		lblSelectAssignedAuthor.setBounds(193, 243, 162, 16);
		contentPane.add(lblSelectAssignedAuthor);
		
		/*
		 * Role Display Label
		 */
		JLabel lblUserRole = new JLabel("User Role: " + LogInDataContainer.getRole());
		lblUserRole.setForeground(Color.DARK_GRAY);
		lblUserRole.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserRole.setBounds(180, 159, 175, 23);
		contentPane.add(lblUserRole);
		
		/*
		 * Display Full Name Label
		 */
		JLabel lblUserName = new JLabel("Full Name: " + LogInDataContainer.getFirstName() + " "  +LogInDataContainer.getLastName());
		lblUserName.setForeground(Color.DARK_GRAY);
		lblUserName.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserName.setBounds(180, 183, 297, 23);
		contentPane.add(lblUserName);
		
		/*
		 * Display Email Label
		 */
		JLabel lblUserEmail = new JLabel("Email : " + LogInDataContainer.getEmail());
		lblUserEmail.setForeground(Color.DARK_GRAY);
		lblUserEmail.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserEmail.setBounds(180, 206, 297, 23);
		contentPane.add(lblUserEmail);
		
		/*
		 * Display selected paper full title
		 */
		TextSelectedPaperName = new JLabel("Selected Paper:");
		TextSelectedPaperName.setFont(new Font("Arial", Font.BOLD, 13));
		TextSelectedPaperName.setBounds(12, 95, 113, 15);
		panel.add(TextSelectedPaperName);
		
		/*
		 * Display selected paper full title
		 */
		lblSelectedPaperName = new JLabel("New Label");
		lblSelectedPaperName.setFont(new Font("Arial", Font.BOLD, 13));
		lblSelectedPaperName.setBounds(18, 111, 208, 15);
		lblSelectedPaperName.setVisible(false);
		panel.add(lblSelectedPaperName);
		
		/*
		 * Display Status of Selected Paper
		 */
		lblSelectedPaperStatus = new JLabel("Paper Status Displayed Here");
		lblSelectedPaperStatus.setFont(new Font("Arial", Font.BOLD, 13));
		lblSelectedPaperStatus.setBounds(12, 148, 385, 15);
		lblSelectedPaperStatus.setVisible(false);
		panel.add(lblSelectedPaperStatus);
		
		
		/*
		 * Approval Display Label
		 * 
		 * Checks if User is approved and/or blacklisted
		 * Correlating messages show depending on status
		 */
		JLabel lblApprovalStatus = new JLabel("Temporary String");
		lblApprovalStatus.setForeground(Color.DARK_GRAY);
		lblApprovalStatus.setVerticalAlignment(SwingConstants.TOP);
		if (LogInDataContainer.getBlacklist() == 0) {															// If User is NOT blacklisted
			if (LogInDataContainer.getApproved() == 1) {														// If User is approved
				lblApprovalStatus = new JLabel("<html>Account Status: "											// Create new JLabel with message
						+ "APPROVED<br/>You are able to review papers!");										// Display message
			}
			if (LogInDataContainer.getApproved() == 0) {														// If User application being revised
				lblApprovalStatus = new JLabel("<html>Account Status: UNDER REVISION<br/>"						// Create new JLabel with message
						+ "Your application has not been reviewed by an Administrator yet!</html>");			// Display message
				authorList.removeAllElements();																	// Removes authors from JList
			}
			if (LogInDataContainer.getApproved() == 2) {														// If User application was declined
				lblApprovalStatus = new JLabel("<html>Account Status: DECLINED<br/>"							// Create new JLabel with message
						+ "Your application has been DECLINED.<br/>Please consult an Administrator");			// Display message
			}
		}
		if (LogInDataContainer.getBlacklist() == 1) {																			// If User is BLACKLISTED
			lblApprovalStatus = new JLabel("<html>Account Status: BLACKLISTED<br/>"												// Create new JLabel with message
					+ "You are BLACKLISTED and unable to review papers.<br/>Please consult an Administrator</html>");			// Display message
			authorList.removeAllElements();																						// Removes authors from JList
			JOptionPane.showMessageDialog(contentPane, "You have been BLACKLISTED.\nConsult an Administrator", "WARNING", 2);	// If user is blacklisted a warning message is displayed
		}
		lblApprovalStatus.setFont(new Font("Arial", Font.BOLD, 13));
		lblApprovalStatus.setBounds(430, 149, 395, 77);
		contentPane.add(lblApprovalStatus);
		

		
		/*
		 *  Make the assigned AuthorList into a JList
		 */
		assignedAuthorList = new JList(authorList.toArray());
		assignedAuthorList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {													// MousePressed Event on JList
				index = assignedAuthorList.locationToIndex(e.getPoint());								// Set location to index
				updatePane();
			}
		});
		assignedAuthorList.setValueIsAdjusting(true);
		assignedAuthorList.setBounds(0, 0, -81, -82);
		contentPane.add(assignedAuthorList);
		

		
		
		/*
		 *  Insert JList into scrollable Pane
		 */
		JScrollPane scrollPane = new JScrollPane(assignedAuthorList);
		scrollPane.setBounds(185, 261, 176, 208);
		contentPane.add(scrollPane);
		
		/*
		 * Log Out Button:
		 * Logs User out of their Account
		 * A new instance of the LogIn class is created and added to the StartingFrame's DesktopPane
		 * It then removes current class from the StartingFrame's DesktopPane and sets it's instance to null
		 */
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToLogInMenu();
			}
		});
		
		JPanel BackPanelForPaperList = new JPanel();
		BackPanelForPaperList.setBackground(Color.DARK_GRAY);
		BackPanelForPaperList.setBounds(183, 259, 180, 212);
		contentPane.add(BackPanelForPaperList);
		btnLogOut.setBounds(912, 11, 86, 23);			// Sets bounds for Button
		contentPane.add(btnLogOut);						// Adds button to contentPane
		
		
		
		
		
		/*
		 * Upload Reviewed Paper JButton
		 * Allows Reviewer to upload their reviewed paper
		 */
		JButton btnUploadReviewedPaper = new JButton("Upload Reviewed Paper");							// Sets text for JButton
		btnUploadReviewedPaper.setFont(new Font("Tahoma", Font.PLAIN, 13));								// Sets Font for JButton
		btnUploadReviewedPaper.addActionListener(new ActionListener() {									// Listener for Jbutton
			public void actionPerformed(ActionEvent arg0) {												// When "Upload Reviewed Paper" button is pressed
				
				if (assignedAuthorList.getSelectedValue() == null) {									// If no paper is selected
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper for review");		// Error message
				}
				
				else{		
					JFileChooser chooser = new JFileChooser();											// JFile Chooser | Choose file to upload
					chooser.setDialogTitle("Upload File");												// Set title of JFileChooser Window
					
					JOptionPane.showMessageDialog(null, "Please select your REVIEWED paper to upload."	// Pop-up message signaling reviewer-
							+ "\nUploaded files cannot be deleted");									// -to upload the reviewed paper
					
					LookAndFeel JUI = UIManager.getLookAndFeel();										// Sets JUI variable with JavaUILookandFeel		
					try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 			// Sets look and feel to machines LookandFeel
						catch (Exception e1) {e1.printStackTrace();}									// Catch errors
					chooser.updateUI();																	// Updates the chooser with new UI
					int returnVal = chooser.showOpenDialog(null); 										// Location of FileChooser | set to null so pops up in middle of screen
					try { UIManager.setLookAndFeel(JUI);}												// Sets look and feel to JavaUILookandFeel
						catch (Exception e1) {e1.printStackTrace();}									// Catch error
					
					
					if(returnVal == JFileChooser.APPROVE_OPTION) {										// If file chosen is valid

					System.out.println("You chose to open this file: " 									//----------------------------------------
							+ chooser.getSelectedFile().getName() 										// Debug print Statement to show
								+ "\nThis is the directory:" 											// what file is chosen and it's directory
									+ chooser.getSelectedFile());										//-----------------------------------------
			       
					Path copyToDirectory = Paths.get(System.getProperty("user.dir") 					// Path set up to copy file
							+ "\\uploads\\" 															//
								+ emailList.get(index) 													//
									+ "/" + chooser.getSelectedFile().getName());						//
					
					
					try {
						Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory);						// Final copy statement. first parameter is file, second is where to copy.
						JOptionPane.showMessageDialog(chooser, "Your REVIEW has been uploaded! The User can now view it.");		// Upload Review Paper Confirmation
					} catch (IOException e) {
						JOptionPane.showMessageDialog(chooser, "You've already uploaded that review!", "WARNING", 1);			// Upload Review Paper Confirmation
						e.printStackTrace();
						}
					}
				}
			}
		});
		btnUploadReviewedPaper.setBounds(415, 485, 178, 23);
		contentPane.add(btnUploadReviewedPaper);
		
		
		
		
		
		/*
		 * Download Paper Button
		 * Allows user to select paper and download it to a desired directory
		 */
		JButton btnDownloadPapers = new JButton("Download Papers");												// Create JButton with text
		btnDownloadPapers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDownloadPapers.addActionListener(new ActionListener() {												// Listener for JButton
			public void actionPerformed(ActionEvent arg0) {														// When Download Button is Pressed
		    	
				System.out.println("working Directory:" + System.getProperty("user.dir"));						// DEBUG PRINT shows working directory
			    
				if (assignedAuthorList.getSelectedValue() == null) {											// If no paper is selected
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper to download");			// Error message
				}
				
				else {
					
					// Set Local Variables
					JFileChooser chooser = new JFileChooser();							// First JFileChooser for item to download
				    JFileChooser chooser2 = new JFileChooser();							// Second JFileChooser for directory to download to
				    String userFolder = System.getProperty("user.dir")					// Set path for Selected Author Folder
				    		+ "\\uploads\\" 
				    			+ authorListSelectedEmail;
				    String userFolder2 = (System.getProperty("user.dir") 				// Logged In User Folder Path
				    		+ "\\uploads\\" 
				    		+ LogInDataContainer.getEmail());
				    
				    // Set properties for first chooser
			    	chooser.setCurrentDirectory(new File(userFolder));					// Sets Directory of JFileChooser to selected Author
				    chooser.setDialogTitle("Download File");							// Set title of JFileChooser
				    
				    // Set properties for the second chooser
			    	chooser2.setCurrentDirectory(new File(userFolder2));				// Sets the directory
				    chooser2.setDialogTitle("Download File To");						// Title of JFileChooser
				    chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);		// Sets Selection mode to Directories_Only
				    chooser2.setAcceptAllFileFilterUsed(false);							// Sets file filter

	
				    LookAndFeel JUI = UIManager.getLookAndFeel();																				// Sets JUI variable with JavaUILookandFeel		
					try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}		// Sets look and feel to machines LookandFeel
					chooser.updateUI();																											// Updates the chooser with new UI
					int returnVal = chooser.showOpenDialog(null);											// Opens up JFileChooser at specified location | location is null so pops up in middle of the screen
					try { UIManager.setLookAndFeel(JUI);} catch (Exception e1) {e1.printStackTrace();}		// Sets look and feel to JavaUILookandFeel
				    
					if(returnVal == JFileChooser.APPROVE_OPTION) {											// If chosen file is valid
				    	
				    	JOptionPane.showMessageDialog(chooser2, 											// Pop-up message signaling-
				    			"Please choose the location you'd like"										// -reviewer to download a paper
				    				+ " to download the selected paper to");								//
				    	
				    	System.out.println("You chose to open this file: " +													// Debug print Statement to show-
				            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());		// -what file is chosen and its directory
					    
					    try {
					    	
							try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 		// Sets look and feel to current-
								catch (Exception e1) {e1.printStackTrace();}								// -machines Look and Feel
							
							chooser2.updateUI();											// Updates the chooser with new UI
						    int returnVal2 = chooser2.showOpenDialog(null);					// Opens up JFileChooser at specified location | location is null so pops up in middle of the screen
							
						    try { UIManager.setLookAndFeel(JUI);} 							// Sets look and feel to JavaUILookandFeel
								catch (Exception e1) {e1.printStackTrace();}				// Catch block
						    
		
						    if (returnVal2 == JFileChooser.APPROVE_OPTION) {				
						        System.out.println("getCurrentDirectory(): " 			// Debug Print Statement:
						           +  chooser2.getCurrentDirectory());					//		Current Directory
						        System.out.println("getSelectedFile(): " 				//	Debug Print Statement:
						           +  chooser2.getSelectedFile());						//		Selected File
						        
						        
								String copyToDirectory1 = chooser2.getSelectedFile() 	// Sets directory the file is copying to as a String
										+ "\\" + chooser.getSelectedFile().getName();	
								
								Path copyToDirectory = Paths.get(copyToDirectory1); 	// Setup Path to directory we're copying to
							
								System.out.println("\ncopy to directory " 				// Debug Print Statement-
										+ copyToDirectory1);							// -Shows directory the file is copying to
						      
							try {
						    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);	// final copy statement. first parameter is file, second is where to copy.
								
						    	JOptionPane.showMessageDialog(chooser2, 										// Download Confirmation
										"Your selected File has been downloaded.", "Confirmation", 1); 			// Download Confirmation
							} catch (IOException e) {
									e.printStackTrace();
								}
							}
						    else {
						        	JOptionPane.showMessageDialog(chooser2, "Process Cancelled", "WARNING", 2); 	// Warning Message
						        }
					    } catch (Exception e1) {
					    	e1.printStackTrace();
					    		}
					}
				}
			}  				
		});
		btnDownloadPapers.setBounds(415, 457, 178, 23);
		contentPane.add(btnDownloadPapers);

		
		
		
		
		/*
		 * Buttons used for reviews:
		 * 
		 * Accept button | Sets the selected paper's suggestion to "Accepted"
		 */
		JButton btnAccept = new JButton ("Accept");
		btnAccept.setFont(new Font("Arial", Font.BOLD, 13));
		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("Selected Paper ID: " + uploadIDList.get(assignedAuthorList.getSelectedIndex()));		// DEBUG PRINT STATEMENT | Prints Selected Paper ID
					ReviewRepository.updateStatusByIdAndReviewer(conn.getConn(), 					// ----------------------------------------
							uploadIDList.get(assignedAuthorList.getSelectedIndex()), 				// Updates Reviewer Recommendation for Paper
								LogInDataContainer.getEmail(), "Accept");							// ----------------------------------------
					
					JOptionPane.showMessageDialog(contentPane, 													// Message Pop-up
							"Your Review has been updated.\nAn Admin will view the results!",					// Confirmation on review
							"Review Recommendation", 1);														// Title of Pop-up
					
					updatePane();																				// Updates info Pane
					
					
				} catch (SQLException | ArrayIndexOutOfBoundsException e ) {									// Catch block for repository and array error
					JOptionPane.showMessageDialog(contentPane, "Please select a paper for review");				// error message
					e.printStackTrace();																		// error stack trace printed
				}
				
			}
			
			
			
		});
		btnAccept.setBounds(237,545,126,25);
		contentPane.add(btnAccept);
				
		/*
		 * Reject button | Sets the selected paper's suggestion to "Rejected"
		 */
		JButton btnReject = new JButton ("Reject");
		btnReject.setFont(new Font("Arial", Font.BOLD, 13));
		btnReject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("Selected Paper ID: " + uploadIDList.get(assignedAuthorList.getSelectedIndex()));		// DEBUG PRINT STATEMENT | Prints Selected Paper ID
					ReviewRepository.updateStatusByIdAndReviewer(conn.getConn(), 								// ----------------------------------------
							uploadIDList.get(assignedAuthorList.getSelectedIndex()), 							// Updates Reviewer Recommendation for Paper
								LogInDataContainer.getEmail(), "Reject");										// ----------------------------------------
					
					JOptionPane.showMessageDialog(contentPane, 													// Message Pop-up
							"Your Review has been updated.\nAn Admin will view the results!",					// Confirmation on review
							"Review Recommendation", 1);														// Title of Pop-up
					
					updatePane();																				// Updates info Pane
					
					
				} catch (SQLException | ArrayIndexOutOfBoundsException e ) {									// Catch block for repository and array error
					JOptionPane.showMessageDialog(contentPane, "Please select a paper for review");				// error message
					e.printStackTrace();																		// error stack trace printed
				}
			}
		});
		btnReject.setBounds(373,545,126,25);
		contentPane.add(btnReject);
		
		/*
		 * Major button | Sets the selected paper's suggestion to "Major Revision"
		 */
		JButton btnMajor = new JButton ("Major Revision");
		btnMajor.setFont(new Font("Arial", Font.BOLD, 12));
		btnMajor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("Selected Paper ID: " + uploadIDList.get(assignedAuthorList.getSelectedIndex()));		// DEBUG PRINT STATEMENT | Prints Selected Paper ID
					ReviewRepository.updateStatusByIdAndReviewer(conn.getConn(), 					// ----------------------------------------
							uploadIDList.get(assignedAuthorList.getSelectedIndex()), 				// Updates Reviewer Recommendation for Paper
								LogInDataContainer.getEmail(), "Major Revision");					// ----------------------------------------
					
					JOptionPane.showMessageDialog(contentPane, 													// Message Pop-up
							"Your Review has been updated.\nAn Admin will view the results!",					// Confirmation on review
							"Review Recommendation", 1);														// Title of Pop-up
					
					updatePane();																				// Updates info Pane
				} catch (SQLException | ArrayIndexOutOfBoundsException e ) {									// Catch block for repository and array error
					JOptionPane.showMessageDialog(contentPane, "Please select a paper for review");				// error message
					e.printStackTrace();																		// error stack trace printed
				}
			}			
		});
		btnMajor.setBounds(509,545,126,25);
		contentPane.add(btnMajor);

		/*
		 * Minor button | Sets the selected paper's suggestion to "Minor Revision"
		 */
		JButton btnMinor = new JButton ("Minor Revision");
		btnMinor.setFont(new Font("Arial", Font.BOLD, 12));
		btnMinor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					System.out.println("Selected Paper ID: " + uploadIDList.get(assignedAuthorList.getSelectedIndex()));	// DEBUG PRINT STATEMENT | Prints Selected Paper ID
					
					ReviewRepository.updateStatusByIdAndReviewer(conn.getConn(), 											//---------------------------------------
							uploadIDList.get(assignedAuthorList.getSelectedIndex()), 										// Updates Reviewer Recommendation for Paper
								LogInDataContainer.getEmail(), "Minor Revision");											//--------------------------------------
					
					JOptionPane.showMessageDialog(contentPane, 													// Message Pop-up
							"Your Review has been updated.\nAn Admin will view the results!",					// Confirmation on review
							"Review Recommendation", 1);														// Title of Pop-up
					
					updatePane();																				// Updates info Pane
					
					
				} catch (SQLException | ArrayIndexOutOfBoundsException e ) {									// Catch block for repository and array error
					JOptionPane.showMessageDialog(contentPane, "Please select a paper for review");				// error message
					e.printStackTrace();																		// error stack trace printed
				}
			}
		});
		btnMinor.setBounds(645,545,126,25);
		contentPane.add(btnMinor);	
		
		
		
		
		
		/*
		 * Background Images:
		 * 
		 * Label used to display background image
		 */
		
		JLabel lblNewLabel = new JLabel("Recommendation For Paper");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(391, 519, 226, 25);
		contentPane.add(lblNewLabel);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground2.jpg"));	// Label icon set to image
		label.setBounds(166, 149, 675, 440);
		contentPane.add(label);
		
		JPanel WhiteBackground = new JPanel();
		WhiteBackground.setBackground(Color.WHITE);
		WhiteBackground.setBounds(152, 137, 703, 463);
		contentPane.add(WhiteBackground);
		WhiteBackground.setLayout(null);
		
		


	}
	
	
	public void updatePane() {
		
		SQLiteConnection conn = new SQLiteConnection();
		User user = null;																		// Initialize User Variable
		authorListSelectedEmail = emailList.get(index);											// Gets author selected email
		String id = ""+uploadIDList.get(index);													// Turns Integer ID to String id
		emaillbl.setText(authorListSelectedEmail);												// Sets text of emaillbl
		idlbl.setText(id);																		// Sets text of idlbl
		lblSelectedPaperName.setText(assignedAuthorList.getSelectedValue().toString());			// Sets text of lblSelectedPaperName
		
		try {
			lblSelectedPaperStatus.setText("Your Recommendation For Selected Paper: " 			// Sets text of lblSelectedPaperStatus
					+ (ReviewRepository.getStatusByIdAndReviewer(conn.getConn(), 				// Repository Call
							uploadIDList.get(index), 											// ID of selected paper
								LogInDataContainer.getEmail().toString())));					// Email or Current User
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			user = UserRepository.getUserByEmail(conn.getConn(), authorListSelectedEmail);		// Sets user variable
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		String name = user.getFirstName() + " " + user.getLastName();							// Sets name variable with firstname and lastname of selected User
		authorlbl.setText(name);																// Set authorlbl text with name of selected user
		/*
		 * Sets Labels Visible in JPanel
		 */
		authorlbl.setVisible(true);
		emaillbl.setVisible(true);
		idlbl.setVisible(true);
		lblSelectedPaperName.setVisible(true);
		lblSelectedPaperStatus.setVisible(true);
	}

	
	private void goToLogInMenu() {
		 LogIn nw = LogIn.getInstance();		// Creates new Window
		 nw.pack();								// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
		 getDesktopPane().add(nw);				// Adds Instance of frame to DesktopPane on StartingFrame
		 nw.setVisible(true);					// Sets Instance frame visible
		 
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
	public static ReviewerMenu getInstance() {	
	    if (myInstance == null) {					// If instance is null, create new instance
	        myInstance = new ReviewerMenu();		// Set new instance
	    }
	    return myInstance;							// Return instance
	}
}
