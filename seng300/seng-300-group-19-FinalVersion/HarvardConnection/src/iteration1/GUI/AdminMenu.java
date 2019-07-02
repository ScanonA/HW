package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextPane;

public class AdminMenu extends JInternalFrame {

	private JPanel contentPane;
	private static AdminMenu myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu frame = new AdminMenu();
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
	public AdminMenu()  {
		/**
		 * Sets Bounds for window
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// Close Operation
		setBounds(100, 100, 1024, 768);						// Sets bounds for window
		contentPane = new JPanel();							// Creates new contentPane
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	// Sets border
		setContentPane(contentPane);						// Set Content pane
		contentPane.setLayout(null);						// Sets pane layout

		SQLiteConnection conn = new SQLiteConnection();		// Connects to database
		
		
		/**
		 * Papers JButton: When clicked, opens up GUI to choose on what to do with certain papers
		 */
		JButton btnPapers = new JButton ("Papers");
		btnPapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent p) {
				
				JournalList nw = JournalList.getInstance();		// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {				// Catch Block for Exception
					System.out.println(e1);				// Prints Exception
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}	
		});
		btnPapers.setBounds(560,384,150,25);
		contentPane.add(btnPapers);

		
		
		/**
		 * Manage Reviewers JButton: When clicked, GUI to edit the status of registering Reviewers shows
		 */
		JButton btnMngReviewer = new JButton ("Manage Reviewers");		// Creates new JButton "Manage Reviewers"
		btnMngReviewer.addActionListener(new ActionListener() {			// Creates ActionPerformed Listener
			public void actionPerformed(ActionEvent arg0) {				// ActionPerformed
				
				ManageReviewers nw = ManageReviewers.getInstance();		// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {				// Catch Block for Exception
					System.out.println(e1);				// Prints Exception
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}
		});
		btnMngReviewer.setBounds(560,420,150,25);		// Sets bounds of location and size for button
		contentPane.add(btnMngReviewer);				// Add MngReviewer button to content pane
		
		
		
		/**
		 * Logout Button | When pressed, user is logged out
		 */
		JButton btnLogOut = new JButton("Log Out");				// New JButton "Log Out"
		btnLogOut.addActionListener(new ActionListener() {		// Set Listener for Button
			public void actionPerformed(ActionEvent e) {		// ActionPerformedListener
				
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
		});
		btnLogOut.setBounds(912, 11, 86, 23);			// Sets bounds for location and size of button
		contentPane.add(btnLogOut);
		
		
		
		/*
		 * JTPanePapers | Text Pane to display information about papers
		 */
		JTextPane JTPanePapers = new JTextPane();
		JTPanePapers.setFont(new Font("Arial", Font.BOLD, 12));
		try {
			JTPanePapers.setText("Number of Users Registered:\n" + UserRepository.getNumberOfRows(conn.getConn()) + 
					"\n\nNumber of Papers Uploaded:\n" + UploadRepository.getNumberOfRows(conn.getConn()) +
					"\n\nNumber of Active Reviews:\n" + ReviewRepository.getNumberOfRows(conn.getConn())
					);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JTPanePapers.setBounds(285, 373, 175, 140);
		JTPanePapers.setEditable(false);				// Sets TextPane to be unchangable
		contentPane.add(JTPanePapers);
		
		
		/*
		 * ADMIN MENU Label
		 */
		JLabel lblNewLabel = new JLabel("ADMIN MENU\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(285, 204, 135, 25);				// sets label bound
		contentPane.add(lblNewLabel);							// add label to content pane
		
		/*
		 * Role Display Label
		 */
		JLabel lblDescription = new JLabel("System Information");
		lblDescription.setForeground(Color.DARK_GRAY);
		lblDescription.setFont(new Font("Arial", Font.BOLD, 18));
		lblDescription.setBounds(285, 349, 175, 23);
		contentPane.add(lblDescription);
		
		/*
		 * Role Display Label
		 */
		JLabel lblUserRole = new JLabel("User Role: " + LogInDataContainer.getRole());
		lblUserRole.setForeground(Color.DARK_GRAY);
		lblUserRole.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserRole.setBounds(285, 232, 175, 23);
		contentPane.add(lblUserRole);
		
		/*
		 * Display Full Name Label
		 */
		JLabel lblUserName = new JLabel("Full Name: " + LogInDataContainer.getFirstName() + " "  +LogInDataContainer.getLastName());
		lblUserName.setForeground(Color.DARK_GRAY);
		lblUserName.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserName.setBounds(285, 256, 445, 23);
		contentPane.add(lblUserName);
		
		/*
		 * Display Email Label
		 */
		JLabel lblUserEmail = new JLabel("Email : " + LogInDataContainer.getEmail());
		lblUserEmail.setForeground(Color.DARK_GRAY);
		lblUserEmail.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserEmail.setBounds(285, 279, 445, 23);
		contentPane.add(lblUserEmail);
		
		/*
		 * JLabel | Used for displaying background image
		 */
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(283, 370, 179, 145);
		contentPane.add(panel);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground4.png"));
		label.setBounds(224, 164, 560, 410);
		contentPane.add(label);
		
		
		
		/*
		 * Canvas used for background
		 */
		JPanel canvas = new JPanel();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(214, 154, 579, 430);
		contentPane.add(canvas);

		
		
		
	} // Admin Menu Closing Bracket
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Returns paper status as an int of the specified paper id
	 * @param int PID is the paper id number
	 * @return int of paper status from database
	 */
	private int checkPaperStatus(int paperID) {
		/*
		 * Get paper status from database
		 * SELECT status FROM PAPERS WHERE pid = paperID
		 * return status
		 */
		return -1;
	}
	
	private void rejectPaper(int paperID) {
		/*
		 * UPDATE PAPERS SET status = 0 WHERE pid = paperID
		 */
	}
	
	private void acceptPaper(int paperID)	{
		/*
		 * UPDATE PAPERS SET status = 1 WHERE pid = paperID
		 */
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AdminMenu getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new AdminMenu();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
