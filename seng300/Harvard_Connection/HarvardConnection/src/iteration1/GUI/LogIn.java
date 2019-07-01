package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserLoginModule;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import java.awt.Color;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField usernameInput;
	private JTextField passwordInput;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private Button LogIn;
	private Button Return;
	
	public int selectedRole;
	
	private boolean success;
	
	UserLoginModule login = new UserLoginModule();

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernameInput = new JTextField();
		usernameInput.setBounds(79, 39, 183, 23);
		contentPane.add(usernameInput);
		usernameInput.setColumns(10);
		
		passwordInput = new JTextField();
		passwordInput.setColumns(10);
		passwordInput.setBounds(79, 140, 183, 23);
		contentPane.add(passwordInput);
		
		lblUsername = new JLabel("Email");
		lblUsername.setBounds(12, 12, 106, 15);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 113, 70, 15);
		contentPane.add(lblPassword);
		
		LogIn = new Button("Log In\n");
		
		LogIn.setBounds(180, 228, 86, 23);
		contentPane.add(LogIn);
		
		Return = new Button("Go Back");
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose(); 						//Closes window after opening
				MainMenu main = new MainMenu();
				main.setVisible(true);
			}
		});
		Return.setBounds(338, 10, 86, 23);
		contentPane.add(Return);
		
		JTextPane txtpnMessageDisplay = new JTextPane();
		txtpnMessageDisplay.setBackground(Color.WHITE);
		txtpnMessageDisplay.setBounds(291, 113, 116, 118);
		contentPane.add(txtpnMessageDisplay);
		
		LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				// New Stuff after removing bulletin Role log ins
				success = login.validate(usernameInput.getText(), passwordInput.getText());					// Logs in and checks if account exists (little sloppy)
				
				SQLiteConnection conn = new SQLiteConnection();												// Connects to database
				try {
					User user = UserRepository.selectByUsername(conn.getConn(), usernameInput.getText());	// logs in again to grab Role ID (Little sloppy, can go and change later on)
					
					selectedRole = user.getRoleID();								// Gets role ID and sets it to selectedRole
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		// Login selected by Username
				
				
				System.out.println(selectedRole);			// Debug print statement, shows role number
				
				if(!success) {					// If login doesn't succeed 
					txtpnMessageDisplay.setText("Username or Password are incorrect");	// error message
				}
				
				else if(success && selectedRole == 3) {				// If log in succeeds and role number is 3 (aka Reviewer)
					contentPane.setVisible(false);
					dispose(); 						//Closes window after opening
					AuthorMenu author = new AuthorMenu();			// Opens up Author Menu SCreen
					author.setVisible(true);
				}
				else if(success && selectedRole == 2) {				// If log in succeeds and role number is 2 (aka Author)
					contentPane.setVisible(false);
					dispose(); 						//Closes window after opening
					ReviewerMenu reviewer = new ReviewerMenu();
					reviewer.setVisible(true);
				}
				else if(success && selectedRole == 1) {				// If log in succeeds and role number is 1 (aka Administrator)
					contentPane.setVisible(false);
					dispose(); 						//Closes window after opening
					AdminMenu admin = new AdminMenu();
					admin.setVisible(true);
				}
				
			}
		});
	}
}
