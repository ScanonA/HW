package iteration1.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserLoginModule;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Label;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

public class LogIn extends JInternalFrame {

	private JPanel contentPane;
	private JTextField emailInput;
	private JPasswordField passwordInput;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JButton LogIn;
	private JButton Return;
	
	private static LogIn myInstance;		// Used for having only 1 frame open at a time
	
	public int selectedRole;
	
	private boolean success;
	
	UserLoginModule login = new UserLoginModule();
	private JLabel lblUsrAuthServ;
	private JLabel lblLogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);					// Sets bounds for location and size
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * JTextField for email
		 */
		emailInput = new JTextField();
		emailInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	// Makes ENTER key act as log in button
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
				logInProcess();
				}
			}
		});
		emailInput.setBounds(412, 420, 183, 23);
		contentPane.add(emailInput);
		emailInput.setColumns(10);
		
		
		/*
		 * JTextField for Password
		 */
		passwordInput = new JPasswordField();
		passwordInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	// Makes ENTER key act as log in button
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
				logInProcess();
				}
			}
		});
		passwordInput.setColumns(10);
		passwordInput.setBounds(412, 454, 183, 23);
		contentPane.add(passwordInput);
		
		
		/*
		 * JLabel for email
		 */
		lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.DARK_GRAY);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 15));
		lblEmail.setBounds(335, 425, 70, 15);
		contentPane.add(lblEmail);
		
		/*
		 * JLabel for password
		 */
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.DARK_GRAY);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
		lblPassword.setBounds(335, 458, 70, 15);
		contentPane.add(lblPassword);
		
		
		/**
		 * Go back button to go to previous window
		 */
		Return = new JButton("Go Back");
		Return.setFont(new Font("Arial", Font.PLAIN, 12));
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 MainMenu nw = MainMenu.getInstance();		// Creates new Window
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
		Return.setBounds(912, 11, 86, 23);
		contentPane.add(Return);
		
		/*
		 * JButton used for log in functionality
		 */
		LogIn = new JButton("Log In\n");
		LogIn.setBounds(461, 488, 86, 23);
		contentPane.add(LogIn);
		LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logInProcess();
			}
		});
		
		
		/*
		 * JLabel used for title
		 */
		lblUsrAuthServ = new JLabel("<html>User Authentication Service</html>");
		lblUsrAuthServ.setForeground(Color.DARK_GRAY);
		lblUsrAuthServ.setVerticalAlignment(SwingConstants.TOP);
		lblUsrAuthServ.setFont(new Font("Arial", Font.BOLD, 27));
		lblUsrAuthServ.setBounds(321, 348, 366, 41);
		contentPane.add(lblUsrAuthServ);
		
		
		/*
		 * Jlabel used for Logo
		 */
		lblLogo = new JLabel("");
		lblLogo.setBounds(352, 223, 304, 68);
		contentPane.add(lblLogo);
		ImageIcon myimage = new ImageIcon("C:\\Users\\edmun\\eclipse-workspace\\SENG300 Group Project\\seng-300-group-19\\HarvardConnection\\img\\UofALogo.png");
//		ImageIcon myimage = new ImageIcon(System.getProperty("user.dir") + "\\img\\UofALogo.png");
		Image img1 = myimage.getImage();
		Image img2 = img1.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_AREA_AVERAGING);
		ImageIcon i = new ImageIcon(img2);
		lblLogo.setIcon(i);
		
		/*
		 * JLabel used for menu background
		 */
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\MainMenuBack.jpg"));	// Label icon set to image
//		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\MainMenuBack.jpg"));
		label.setBounds(210, 174, 587, 390);
		contentPane.add(label);
		
		/*
		 * JPanel used for white menu background
		 */
		JPanel WhitePanel = new JPanel();
		WhitePanel.setBackground(Color.WHITE);
		WhitePanel.setBounds(197, 163, 613, 412);
		contentPane.add(WhitePanel);


	}
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public void logInProcess() {
		success = login.validate(emailInput.getText(), passwordInput.getText());				// Logs in and checks if account exists (little sloppy)
		SQLiteConnection conn = new SQLiteConnection();											// Connects to database
		
		try {
			User user = UserRepository.getUserByEmail(conn.getConn(), emailInput.getText());	// logs in again to grab Role ID (Little sloppy, can go and change later on)
			
			selectedRole = user.getRoleID();													// Gets role ID and sets it to selectedRole
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		// Login selected by Email
		
		
		System.out.println(selectedRole);														// Debug print statement, shows role number
		
		
		if(!success) {																			// If login doesn't succeed 
			JOptionPane.showMessageDialog(contentPane, "Email or Password are incorrect");		// error message
		}
		
		
		
		/*
		 *  Setters for LogInInformationContainer
		 */
		LogInDataContainer.setEmail(emailInput.getText());
		LogInDataContainer.setRoleID(selectedRole);
		try {
			LogInDataContainer.setFirstName(UserRepository.getUserByEmail(conn.getConn(), emailInput.getText()).getFirstName());
			LogInDataContainer.setLastName(UserRepository.getUserByEmail(conn.getConn(), emailInput.getText()).getLastName());
			LogInDataContainer.setApproved(UserRepository.getApprovalByEmail(conn.getConn(), emailInput.getText()));
			System.out.println("\n\n\nBLACKLIST VALUE LOGGING IN" + UserRepository.getBlacklistValueByEmail(conn.getConn(), emailInput.getText()));
			LogInDataContainer.setBlacklist((UserRepository.getBlacklistValueByEmail(conn.getConn(), emailInput.getText())));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		if(success && selectedRole == 3) {				// If log in succeeds and role number is 3 (aka Reviewer)
			
			 AuthorMenu nw = AuthorMenu.getInstance();	// Creates new Window
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
		
		else if(success && selectedRole == 2) {				// If log in succeeds and role number is 2 (aka Author)

			 ReviewerMenu nw = ReviewerMenu.getInstance();	// Creates new Window
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
		
		else if(success && selectedRole == 1) {				// If log in succeeds and role number is 1 (aka Administrator)
			
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
	}
	
	
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static LogIn getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new LogIn();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
