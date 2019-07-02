package iteration1.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserController;
import java.awt.Canvas;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;

public class SignUp extends JInternalFrame {

	private JPanel contentPane;
	private JTextField inputtedEmail;
	private JTextField inputtedPassword;
	private JTextField inputtedFirstName;
	private JTextField inputtedLastName;
	private String reviewerInterest;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public int selectedRole;
	
	private static SignUp myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);		// Sets bounds for location and size | set for corner of DesktopPane
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		inputtedEmail = new JTextField();			// TextField for UserName input
		inputtedEmail.setBounds(366, 405, 274, 19);
		contentPane.add(inputtedEmail);
		inputtedEmail.setColumns(10);
		
		
		
		inputtedPassword = new JPasswordField();			// TextField for Password input
		inputtedPassword.setBounds(366, 452, 274, 19);
		contentPane.add(inputtedPassword);
		inputtedPassword.setColumns(10);
		
		
		
		inputtedFirstName = new JTextField();				// TextField for Email input
		inputtedFirstName.setBounds(366, 358, 125, 19);
		contentPane.add(inputtedFirstName);
		inputtedFirstName.setColumns(10);
		
		
		
		inputtedLastName = new JTextField();
		inputtedLastName.setBounds (515, 358, 125, 19);
		contentPane.add(inputtedLastName);
		inputtedLastName.setColumns(10);
		
		
		/*
		 * Email label
		 */
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 15));
		lblEmail.setBackground(Color.WHITE);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setBounds(366, 388, 70, 15);
		contentPane.add(lblEmail);
		
		
		/*
		 * password Label
		 */
		JLabel lblPassword = new JLabel("Password");	// Label above Password TextField
		lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
		lblPassword.setBounds(366, 435, 70, 15);
		contentPane.add(lblPassword);
		
		
		
		JLabel lblFirstName = new JLabel("First Name");		// Label above email TextBoxField
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 15));
		lblFirstName.setBounds(391, 343, 76, 15); 
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial", Font.BOLD, 15));
		lblLastName.setBounds(540, 343, 76, 15);
		contentPane.add(lblLastName);
		
		
		JRadioButton rdbtnAuthor = new JRadioButton("Author");
		rdbtnAuthor.setFont(new Font("Arial", Font.BOLD, 15));
		rdbtnAuthor.setOpaque(false);
		buttonGroup.add(rdbtnAuthor);
		rdbtnAuthor.setBounds(391, 477, 109, 23);
		contentPane.add(rdbtnAuthor);
		
		
		
		JRadioButton rdbtnReviewer = new JRadioButton("Reviewer");
		rdbtnReviewer.setFont(new Font("Arial", Font.BOLD, 15));
		rdbtnReviewer.setOpaque(false);
		buttonGroup.add(rdbtnReviewer);
		rdbtnReviewer.setBounds(507, 478, 109, 23);
		contentPane.add(rdbtnReviewer);
		
		
		
		
		JButton Return = new JButton("Go Back");					// Go Back Button, brings user back to main menu on mouse click
		Return.setFont(new Font("Arial", Font.PLAIN, 12));
		Return.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					goToMainMenu();
			}
		});
		Return.setBounds(912, 11, 86, 23);
		contentPane.add(Return);
		
		
		
		JButton SignUp = new JButton("Sign Up");					// Sign Up Button, once pressed the information inputed in 
		SignUp.setFont(new Font("Arial", Font.BOLD, 15));
		SignUp.addMouseListener(new MouseAdapter() {			// TextFields and role option selected are registered into database
			public void mouseClicked(MouseEvent e) {
				
				if (rdbtnAuthor.isSelected()) {					// Checks if Author Role is selected and changes selectedRole variable to 2
					selectedRole = 3;
				}
				
				if (rdbtnReviewer.isSelected()) {				// Checks if Reviewer Role is selected and changes selectedRole variable to 3
					selectedRole = 2;
				}
				
				if ((!rdbtnAuthor.isSelected() && !rdbtnReviewer.isSelected()) 										// If neither roles are chosen or User name/password contains spaces
						|| inputtedEmail.getText().contains(" ") || inputtedEmail.getText().isEmpty()			// Error message is then displayed
							|| inputtedPassword.getText().contains(" ") || inputtedPassword.getText().isEmpty()
								|| inputtedFirstName.getText().contains(" ") || inputtedFirstName.getText().isEmpty()
									|| inputtedLastName.getText().contains(" ") || inputtedLastName.getText().isEmpty()  ){
					
					JOptionPane.showMessageDialog(contentPane, "Please enter a valid Email/Password/First name/Last name."
							+ "\nMake sure you've selected to be an author or reviewer.\nCannot have an empty field. \nNo spaces are allowed.");	// Error message
					return;
				}
	
				
				if (inputtedEmail.getText().contains(" ") 
						|| inputtedEmail.getText().isEmpty() 
							|| !inputtedEmail.getText().contains("@") 
								|| !inputtedEmail.getText().contains(".") ) {
					JOptionPane.showMessageDialog(contentPane, "Please enter a valid email."); // Error Message
					return;
				}
				
				try {
					if (selectedRole == 2) {
						
						reviewerInterest = JOptionPane.showInputDialog("Please tell us what your research interests are."
								+ "\nPlease separate interests with a comma."
								+ "\nExample: Astronomy, Quantum Physics, Kinematics");

						UserController.registerReviewer(inputtedEmail.getText(), inputtedFirstName.getText(), inputtedLastName.getText(), inputtedPassword.getText(), selectedRole,reviewerInterest);
						
						}
					
					else {
						UserController.register(inputtedEmail.getText(), inputtedFirstName.getText(), inputtedLastName.getText(), inputtedPassword.getText(), selectedRole);				// Register method in UserController class
					}
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException | IOException e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(contentPane, "Congrats! When your registration is confirmed, you'll be notified!");		// Window shows up to show you've registered
				System.out.println(selectedRole);			// Debug Print: Selected Role
				goToMainMenu();								//
			}
		});
		SignUp.setBounds(455, 556, 98, 23);
		contentPane.add(SignUp);
		
		JLabel lblTitle = new JLabel("Registration Page");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 40));
		lblTitle.setBounds(353, 183, 302, 77);
		contentPane.add(lblTitle);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground2.jpg"));
//		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground2.jpg"));
		label.setBounds(228, 147, 552, 443);
		contentPane.add(label);
		
		/*
		 * JPanel used for background
		 */
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(215, 136, 578, 465);
		contentPane.add(canvas);
		
		
	}
	
	
	/*
	 * Frame Switch
	 */
	public void goToMainMenu() {
		 MainMenu  nw = MainMenu.getInstance();		// Creates new instance of MainMenu
		    nw.pack();								// Packs components
		    getDesktopPane().add(nw);				// Adds the JInternal Frame to DesktopPane
		    nw.setVisible(true);					// Set JInternal Frame Visible
		    try {
		        nw.setMaximum(true);				// Sets the internal frame to maximum size of DesktopPane
		    } catch (Exception e1) {
		    	System.out.println(e1);
		    }
		    getDesktopPane().revalidate();			// Revalidates DesktopPane
		    getDesktopPane().repaint();				// Repaints desktopPane for new JInternal Frame
		    getDesktopPane().remove(myInstance);	// Removes old pane
		    myInstance = null;						// Sets instance of old pane to null
	}
	
	
	/*
	 * Instance of Sign up Frame
	 * Used in switching frames
	 */
	public static SignUp getInstance() {
	    if (myInstance == null) {
	        myInstance = new SignUp();
	    }
	    return myInstance;
	}
}
