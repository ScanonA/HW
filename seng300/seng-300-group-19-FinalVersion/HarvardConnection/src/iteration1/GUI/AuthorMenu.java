package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.*; // Added for copy options i.e. REPLACE_EXISTING (replaces file if already exists)

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.Review;
import iteration1.models.Upload;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class AuthorMenu extends JInternalFrame {

	private JPanel contentPane;
	private static AuthorMenu myInstance;		// Instance of Class | Used in having one frame visible at all times
	DefaultListModel<Integer> uploadIDList = new DefaultListModel();		// List for Paper Ids
	DefaultListModel<String> authorPaperList = new DefaultListModel();			// To show assigned papers
	private JList PaperList;
	private int index;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorMenu frame = new AuthorMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AuthorMenu() {
		SQLiteConnection conn = new SQLiteConnection();   // Connection to database
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		paperNames();	// Method to get uploaded paper names
		
		/*
		 * text pane for paper information
		 */
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.BOLD, 12));
		textPane.setBounds(517, 265, 174, 119);
		contentPane.add(textPane);
		
		/*
		 * Paper List
		 */
		PaperList = new JList(authorPaperList.toArray());
		PaperList.setFont(new Font("Arial", Font.BOLD, 12));
		PaperList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {													// MousePressed Event on JList
				index = PaperList.locationToIndex(e.getPoint());								// Set location to index
				try {
					textPane.setText("\n" + "Paper Status: " + UploadRepository.getUploadsByID(conn.getConn(), uploadIDList.get(index)).get(0).getAdminStatus() +"\n\n\n" + "Paper ID: " + uploadIDList.get(index));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}
		});
		PaperList.setBounds(123, 363, 272, -297);
		PaperList.setValueIsAdjusting(true);
		contentPane.add(PaperList);
		
		JScrollPane PaperScrollPane = new JScrollPane(PaperList);
		PaperScrollPane.setBounds(319, 263, 177, 210);
		contentPane.add(PaperScrollPane);
		
		
		// Label to show the Authors's Email
		JLabel lblAuthorMenu = new JLabel("User Email: " + LogInDataContainer.getEmail());
		lblAuthorMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAuthorMenu.setBounds(170, 156, 336, 32);
		contentPane.add(lblAuthorMenu);
		
		
		
		// Logout button: When pressed, user is logged out
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToLogInMenu();
			}
		});
		
		JLabel label_1 = new JLabel("Full Name: " + LogInDataContainer.getFirstName() + " " + LogInDataContainer.getLastName());
		label_1.setForeground(Color.DARK_GRAY);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setBounds(170, 183, 297, 23);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Role: " + LogInDataContainer.getRole());
		label_2.setForeground(Color.DARK_GRAY);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setBounds(515, 162, 297, 23);
		contentPane.add(label_2);
		
		JLabel lblNewLabel = new JLabel("Your Papers");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(356, 236, 104, 25);
		contentPane.add(lblNewLabel);
		btnLogOut.setBounds(912, 11, 86, 23);
		contentPane.add(btnLogOut);
		
		
		
		// Upload paper button: When clicked, opens AuthorPicReviewer,
		// and allows the author to choose reviewers they prefer
		JButton btnUploadPaper = new JButton("Upload Paper");
		btnUploadPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAuthorPickReviewerMenu();
			}
		});
		btnUploadPaper.setBounds(319, 484, 174, 23);
		contentPane.add(btnUploadPaper);
		
		
		
		// Download Paper Button: When clicked, the user can choose to download their submitted paper, or reviewed papers
		JButton btnDownloadReviewedPaper = new JButton("Download Paper");
		btnDownloadReviewedPaper.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {				// When button is clicked
			     
				// Set Local Variables
				JFileChooser chooser = new JFileChooser();							// First JFileChooser for item to download
			    JFileChooser chooser2 = new JFileChooser();							// Second JFileChooser for directory to download to
			    String userFolder = System.getProperty("user.dir")					// Set path for Selected Author Folder
			    		+ "\\uploads\\" 
			    			+ LogInDataContainer.getEmail();
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

		});
		btnDownloadReviewedPaper.setBounds(515, 484, 174, 23);
		contentPane.add(btnDownloadReviewedPaper);
		
		
		
		
		
		/*
		 * JLabel | Used for displaying background image
		 */
		
		JPanel BackgroundList = new JPanel();
		BackgroundList.setBackground(Color.DARK_GRAY);
		BackgroundList.setBounds(316, 260, 183, 216);
		contentPane.add(BackgroundList);
		
		JPanel BackgroundInfoPanel = new JPanel();
		BackgroundInfoPanel.setBackground(Color.DARK_GRAY);
		BackgroundInfoPanel.setBounds(515, 263, 179, 124);
		contentPane.add(BackgroundInfoPanel);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground2.jpg"));
		label.setBounds(159, 148, 689, 441);
		contentPane.add(label);
		
		JPanel BackgroundPanel = new JPanel();
		BackgroundPanel.setBackground(Color.WHITE);
		BackgroundPanel.setBounds(145, 135, 718, 467);
		contentPane.add(BackgroundPanel);
		
	}
	
	
	
	/*
	 * Get list of paper names
	 */
	public void paperNames() {
		
		SQLiteConnection conn = new SQLiteConnection();   // Connection to database
		try {
			
			for (int x = 0; x < UploadRepository.getUploadsByEmail(conn.getConn(), LogInDataContainer.getEmail()).size();x++) {
				Integer UploadID = UploadRepository.getUploadsByEmail(conn.getConn(), LogInDataContainer.getEmail()).get(x).getId();
				String fullFilepath = UploadRepository.getUploadsByEmail(conn.getConn(), LogInDataContainer.getEmail()).get(x).getFilepath();
				String filename;
				if(fullFilepath.indexOf('/') > 0) {
					filename = fullFilepath.substring(fullFilepath.lastIndexOf('/') + 1);
				} else {
					filename = fullFilepath.substring(fullFilepath.lastIndexOf('\\') + 1);
				}
				System.out.println("FILEPATH: " + filename);	//DEbug print statement
				String tempEmail = (filename);
				authorPaperList.addElement(tempEmail);
				uploadIDList.addElement(UploadID);
				System.out.println("List of papers: " + authorPaperList);		// Debug print statement
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/*
	 * Go to LogIn Menu
	 */
	public void goToLogInMenu() {
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
	 * Go to AuthorPickReviewerMenu
	 */
	public void goToAuthorPickReviewerMenu() {
		AuthorPickReviewer nw = AuthorPickReviewer.getInstance();		// Creates new Window
		nw.pack();								// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
		getDesktopPane().add(nw);				// Adds Instance of frame to DesktopPane on StartingFrame
		nw.setVisible(true);					// Sets Instance frame visible
		 
		try {
		    nw.setMaximum(true);				// Sets window to max size of DesktopPane
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
	public static AuthorMenu getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new AuthorMenu();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
