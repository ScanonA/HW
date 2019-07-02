package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Label;
import java.awt.SystemColor;

public class MainMenu extends JInternalFrame {

	private JPanel contentPane;
	private static MainMenu myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);		// Sets bounds for location and size | set for corner of DesktopPane
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 *  Sign Up Button: When clicked, user is sent to sign up menu
		 */
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	// Click listener
				
				 SignUp  nw = SignUp.getInstance();		// Creates new SignUp Window
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
		});
		btnSignUp.setBounds(445, 522, 117, 25);	// Set bounds for button
		contentPane.add(btnSignUp);				// add button to content pane
		
		
		/*
		 * JLabel that displays title
		 */
		JLabel lblUsrAuthServ = new JLabel("<html>ACADEMIC JOURNAL<br/>SYSTEM</html>");
		lblUsrAuthServ.setForeground(new Color(0, 125, 69));
		lblUsrAuthServ.setVerticalAlignment(SwingConstants.TOP);
		lblUsrAuthServ.setFont(new Font("Serif", Font.BOLD, 37));
		lblUsrAuthServ.setBounds(303, 328, 399, 48);
		contentPane.add(lblUsrAuthServ);
		JLabel lblSystem = new JLabel("<html>SYSTEM</html>");
		lblSystem.setForeground(new Color(0, 125, 69));
		lblSystem.setVerticalAlignment(SwingConstants.TOP);
		lblSystem.setFont(new Font("Serif", Font.BOLD, 37));
		lblSystem.setBounds(423, 369, 161, 48);
		contentPane.add(lblSystem);
		
		
		/*
		 *  Label "Sign up or sign in"
		 */
		JLabel lblSignUpOr = new JLabel("Sign Up or Sign In");
		lblSignUpOr.setForeground(new Color(0, 125, 69));
		lblSignUpOr.setFont(new Font("Serif", Font.BOLD, 20));
		lblSignUpOr.setBounds(428, 451, 151, 40);
		contentPane.add(lblSignUpOr);
		
		
		/*
		 *  Log in Button: When clicked, user is sent to log in frame
		 */
		JButton button = new JButton("Log In");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				
					LogIn  nw = LogIn.getInstance();			// Creates new Window
				    nw.pack();									// packs component
				    getDesktopPane().add(nw);					// Add frame to  DesktopPane
				    nw.setVisible(true);						// Set window visible
				    try {
				        nw.setMaximum(true);					//make window max size of desktopPane
				    } catch (Exception e1) {
				    	System.out.println(e1);
				    }
				    getDesktopPane().revalidate();				// Revalidate desktopPane
				    getDesktopPane().repaint();					// Repaint desktopPane
				    getDesktopPane().remove(myInstance);		// remove instance of current frame so that only 1 frame is shown at a time
				    myInstance = null;							// set instance of current frame to null
			}
		});
		button.setBounds(445, 486, 117, 25);
		contentPane.add(button);
		
		/*
		 * JLabel used for Logo
		 */
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(352, 223, 304, 68);
		contentPane.add(lblLogo);
		ImageIcon myimage = new ImageIcon(System.getProperty("user.dir") + "\\img\\UofALogo.png");
//		ImageIcon myimage = new ImageIcon(System.getProperty("user.dir") + "\\img\\UofALogo.png");
		Image img1 = myimage.getImage();
		Image img2 = img1.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_AREA_AVERAGING);
		ImageIcon i = new ImageIcon(img2);
		lblLogo.setIcon(i);
		
		/*
		 * JLabel used for menu background
		 */
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\MainMenuBack.jpg"));	// Label icon set to image | Gets image from img source folder 
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
	public static MainMenu getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new MainMenu();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
