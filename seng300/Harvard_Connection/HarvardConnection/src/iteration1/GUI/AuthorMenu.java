package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*; // Added for copy options i.e. REPLACE_EXISTING (replaces file if already exists)

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class AuthorMenu extends JFrame {

	private JPanel contentPane;

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
	 */
	public AuthorMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAuthorMenu = new JLabel("AUTHOR MENU");
		lblAuthorMenu.setBounds(10, 25, 179, 104);
		contentPane.add(lblAuthorMenu);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				MainMenu main = new MainMenu();
				main.setVisible(true);
			}
		});
		btnLogOut.setBounds(321, 0, 117, 25);
		contentPane.add(btnLogOut);
		
		JButton btnUploadPaper = new JButton("Upload Paper");
		btnUploadPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				// THIS IS WHEN UPLOAD BUTTON IS CLICKED
		    	System.out.println("working Directory:" + System.getProperty("user.dir"));		// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
			    JFileChooser chooser = new JFileChooser();							// J FILE CHOOSER TO CHOOSE FILE TO COPY
		    	System.out.println("Directory after JFileChooser" + chooser.getCurrentDirectory());					// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
			    chooser.setDialogTitle("Upload File");				// SETS TITLE OF NEW FILE CHOOSER WINDOW

			    int returnVal = chooser.showOpenDialog(null);						// HONESTLY DONT KNOW
			    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +													// Debug print Statement to show
			            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying
			       
			       Path copyToDirectory = Paths.get(System.getProperty("user.dir")+ "\\SubmittedFiles\\" + chooser.getSelectedFile().getName());	// path set up to copy file
			       try {
			    	   
			    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       
			    }
			}
		});
		btnUploadPaper.setBounds(10, 100, 150, 23);
		contentPane.add(btnUploadPaper);
		
		JButton btnDownloadReviewedPaper = new JButton("Download Paper");
		btnDownloadReviewedPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				// THIS IS WHEN UPLOAD BUTTON IS CLICKED
		    	System.out.println("working Directory:" + System.getProperty("user.dir"));		// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
			    JFileChooser chooser = new JFileChooser();		
			    String userFolder;
			    userFolder = System.getProperty("user.dir")+ "\\SubmittedFiles\\";// J FILE CHOOSER TO CHOOSE FILE TO COPY
		    	chooser.setCurrentDirectory(new File(userFolder));// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
			    chooser.setDialogTitle("Download File");				// SETS TITLE OF NEW FILE CHOOSER WINDOW
			    System.out.println("Directory after JFileChooser" + chooser.getCurrentDirectory() );

			    int returnVal = chooser.showOpenDialog(null);						// HONESTLY DONT KNOW
			    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +													// Debug print Statement to show
			            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying
			       
			       Path copyToDirectory = Paths.get(System.getProperty("user.dir")+ "\\SavedFiles\\" + chooser.getSelectedFile().getName());	// path set up to copy file
			       try {
			    	   
			    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       
			    }
			}
		});
		btnDownloadReviewedPaper.setBounds(200, 100, 150, 23);
		contentPane.add(btnDownloadReviewedPaper);
	}
}
