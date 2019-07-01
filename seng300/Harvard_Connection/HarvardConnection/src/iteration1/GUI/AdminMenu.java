package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class AdminMenu extends JFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("ADMIN MENU\n");

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setBounds(106, 96, 264, 105);
		contentPane.add(lblNewLabel);
		
		
		
		
		JButton btnMngReviewer = new JButton ("Manage Reviewers");
		btnMngReviewer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				ManageReviewers mngReviewer = new ManageReviewers();
				mngReviewer.setVisible(true);
				
				
			}
			
			
			
		});
		btnMngReviewer.setBounds(50,200,200,25);
		contentPane.add(btnMngReviewer);
		
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
	}

}
