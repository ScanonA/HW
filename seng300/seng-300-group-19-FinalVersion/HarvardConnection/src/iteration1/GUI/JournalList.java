package iteration1.GUI;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iteration1.models.Review;
import iteration1.models.Role;
import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.SwingConstants;


public class JournalList extends JInternalFrame{
	private JPanel contentPane;
	private JList<Upload> journalList;
	private JList<String> journalList2;
	private String display = "";
	private ListSelectionModel listSelectionModel;
	private JTextArea info;
	private static JournalList myInstance;		// Instance of Class | Used in having one frame visible at all times
	
	DefaultListModel<Integer> uploadIDList = new DefaultListModel();		// Tried to make a list
	DefaultListModel<String> authorList = new DefaultListModel();			// TO show assigned papers
	DefaultListModel<String> fileList = new DefaultListModel();	
	DefaultListModel<String> emailList = new DefaultListModel();
	private int index;
	private String authorListSelectedEmail;
	
	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JournalList frame = new JournalList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public JournalList()  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SQLiteConnection conn = new SQLiteConnection();
		ArrayList<Upload> uploads = null;
		try {
//			uploads = UploadRepository.getSubmittedUploads(conn.getConn(), "Submitted");
			uploads = UploadRepository.getUploadsAll(conn.getConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		journalList = new JList(uploads.toArray());
//		journalList = new JList();
//		journalList = new JList(authorList.toArray());
		
		/*
		 * Try block to set authorList with file names and file list with FIle paths and emailList with emails
		 */
		try {
			ArrayList<Upload> uploadIDArray = UploadRepository.getUploadsAll(conn.getConn());	// Takes Logged In Reviwers email and puts all Reviews into uploadIDArray
			
			
			for (int i = 0; i < uploadIDArray.size();i++) {		// Iterate through uploadIDArray and put uploadID's into uploadIDList
				int tempID = (uploadIDArray.get(i).getId());
				uploadIDList.addElement(tempID);					//Resulting list is the upload ID's assigned to the logged in Reviewer
			}
			
			
			for (int x = 0; x < uploadIDList.size();x++) {

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
//        		System.out.println("AUTHRASDASDASDDASD:       " + authorList.get(x));

			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		
		
		journalList = new JList(authorList.toArray());
		journalList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				index = journalList.locationToIndex(e.getPoint());										// Set location to index
				authorListSelectedEmail = emailList.get(index);											// Gets author selected email
				System.out.println(authorListSelectedEmail);
		
				updateInfoPane();
			}
		});
		contentPane.add(journalList);
		journalList.setBounds(374, 0, 165 ,256);
		journalList.setVisible(true);
		
		// Creation of Scroll Pane
		JScrollPane scrollPane = new JScrollPane(journalList);	// Puts JList of reviewers into scroll pane
		scrollPane.setBounds(133, 196, 262, 277);			// NOTE: ERROR WHEN MOVING AROUND IN DESIGN PAGE. IDK WHY
		contentPane.add(scrollPane);
		
		JPanel GrayBackgroundOfList = new JPanel();
		GrayBackgroundOfList.setBackground(Color.DARK_GRAY);
		GrayBackgroundOfList.setBounds(130, 193, 268, 283);
		contentPane.add(GrayBackgroundOfList);
		

		info = new JTextArea();
		info.setForeground(Color.BLACK);
		info.setFont(new Font("SansSerif", Font.BOLD, 13));
		info.setEditable(false);
		info.setLineWrap(true);
		contentPane.add(info);
		info.setText(display);
		info.setBounds(419, 200, 224, 235);
		info.setVisible(true);
		info.setText("Please select a paper." + System.getProperty("line.separator") + "Then you may assign a status.");
		
		JButton btnAccept = new JButton ("Accept");
		btnAccept.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (journalList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper");
				}
				
				try {
					UploadRepository.updateStatus(conn.getConn(), uploadIDList.get(index), "Accepted");
					
					JOptionPane.showMessageDialog(contentPane, 											// Message Pop-up
							"Selected paper status has been updated!",									// Confirmation on status Update
							"Status Update", 1);														// Title of Pop-up
					
					updateInfoPane();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
		});
		
		JPanel BehindJListForReviewers = new JPanel();
		BehindJListForReviewers.setBackground(Color.WHITE);
		BehindJListForReviewers.setBounds(643, 200, 241 ,235);
		contentPane.add(BehindJListForReviewers);
		
		JPanel GrayBackgroundTextArea = new JPanel();
		GrayBackgroundTextArea.setBackground(Color.DARK_GRAY);
		GrayBackgroundTextArea.setBounds(416, 196, 472, 242);
		contentPane.add(GrayBackgroundTextArea);
		btnAccept.setBounds(237,525,126,25);
		contentPane.add(btnAccept);
		
		JButton btnReject = new JButton ("Reject");
		btnReject.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (journalList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper");
				}
				try {
					UploadRepository.updateStatus(conn.getConn(), uploadIDList.get(index), "Rejected");
					
					JOptionPane.showMessageDialog(contentPane, 											// Message Pop-up
							"Selected paper status has been updated!",									// Confirmation on status Update
							"Status Update", 1);														// Title of Pop-up
					
					updateInfoPane();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnReject.setBounds(373,525,126,25);
		contentPane.add(btnReject);
		
		JButton btnMajor = new JButton ("Major Revision");
		btnMajor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMajor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (journalList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper");
				}
				try {
					System.out.println();
					UploadRepository.updateStatus(conn.getConn(), uploadIDList.get(index), "Major Revision");
					
					JOptionPane.showMessageDialog(contentPane, 											// Message Pop-up
							"Selected paper status has been updated!",									// Confirmation on status Update
							"Status Update", 1);														// Title of Pop-up
					
					updateInfoPane();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnMajor.setBounds(509,525,126,25);
		contentPane.add(btnMajor);
		
		JButton btnMinor = new JButton ("Minor Revision");
		btnMinor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMinor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (journalList.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(contentPane, "Please select a Paper");
				}
				try {
					System.out.println(uploadIDList.get(index));
					UploadRepository.updateStatus(conn.getConn(), uploadIDList.get(index), "Minor Revision");
					
					JOptionPane.showMessageDialog(contentPane, 											// Message Pop-up
							"Selected paper status has been updated!",									// Confirmation on status Update
							"Status Update", 1);														// Title of Pop-up
					
					updateInfoPane();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnMinor.setBounds(645,525,126,25);
		contentPane.add(btnMinor);	
		
		
		/*
		 * Go back button, brings Admin back to Admin Menu
		 */
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btnGoBack.setBounds(912, 11, 86, 23);
		contentPane.add(btnGoBack);
		
		
		/*
		 * Download Paper Button
		 * Allows user to select paper and download it to a desired directory
		 */
		JButton btnDownloadPapers = new JButton("Download Papers");												// Create JButton with text
		btnDownloadPapers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDownloadPapers.addActionListener(new ActionListener() {												// Listener for JButton
			public void actionPerformed(ActionEvent arg0) {														// When Download Button is Pressed
		    	
				System.out.println("working Directory:" + System.getProperty("user.dir"));						// DEBUG PRINT shows working directory
			    
				if (journalList.getSelectedValue() == null) {											// If no paper is selected
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
		btnDownloadPapers.setBounds(560, 446, 178, 23);
		contentPane.add(btnDownloadPapers);
		
		
		
		
		/*
		 * JLabel | Used for displaying background image
		 */
		
		JLabel lblPaperList = new JLabel("Uploaded Papers");
		lblPaperList.setForeground(Color.BLACK);
		lblPaperList.setFont(new Font("Serif", Font.BOLD, 18));
		lblPaperList.setBounds(197, 175, 132, 20);
		contentPane.add(lblPaperList);
		
		JLabel lblDecisionsForPaper = new JLabel("Decisions For Paper");
		lblDecisionsForPaper.setForeground(Color.BLACK);
		lblDecisionsForPaper.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDecisionsForPaper.setBounds(402, 485, 204, 33);
		contentPane.add(lblDecisionsForPaper);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground3.jpg"));
		label.setBounds(98, 94, 811, 550);
		contentPane.add(label);
		/*
		 * Canvas used for background
		 */
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(86, 84, 836, 570);
		contentPane.add(canvas);
	}
	
	
	/*
	 * Updates info panel when called
	 */
	public void updateInfoPane() {
		SQLiteConnection conn = new SQLiteConnection();
		ArrayList<String> ReviewList = null;
		try {
			ReviewList = new ArrayList<String>(ReviewRepository.getReviewsByUploadId(conn.getConn(), uploadIDList.get(index)).size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			/*
			 * for the amount of reviews for a paper, get the reviewer and status
			 * then input it into a list
			 */
			for (int i = 0; i <	ReviewRepository.getReviewsByUploadId(conn.getConn(), uploadIDList.get(index)).size(); i++){
				
			String ReviewString = (ReviewRepository.getReviewsByUploadId(conn.getConn(), uploadIDList.get(index)).get(i).getReviewer() 
					+": "+ ReviewRepository.getReviewsByUploadId(conn.getConn(), uploadIDList.get(index)).get(i).getStatus());
			ReviewList.add(ReviewString);
			}
			
			/*
			 * JList to display Reviewer Recommendations
			 */
			journalList2 = new JList(ReviewList.toArray());
			journalList2.addSelectionInterval(-1, -1);
			info.setForeground(Color.BLACK);
			contentPane.add(journalList2);
			journalList2.setBounds(643, 200, 241 ,235);
			journalList2.setVisible(true);
			
			/*
			 * updates Info Pane with text
			 */
			info.setText("Selected Paper Status:\n" + "     "  			// Sets text of lblSelectedPaperStatus
					+ UploadRepository.getUploadsByID(conn.getConn(), uploadIDList.get(index)).get(0).getAdminStatus()
					+ "\n\nAuthor Email:\n" + "     " + emailList.get(index)
					+ "\n\nAuthor Name:\n" + "     " + UserRepository.getUserByEmail(conn.getConn(), emailList.get(index)).getFirstName()
					+ UserRepository.getUserByEmail(conn.getConn(), emailList.get(index)).getLastName()
					+ "\n\nPaper Name:\n" + "     " + journalList.getSelectedValue()
					+ "\n\nPaper ID: " + uploadIDList.get(index)
					);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	 * Exit method, goes to past frame when called
	 */
	private void goToAdminMenu(){
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
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static JournalList getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new JournalList();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
