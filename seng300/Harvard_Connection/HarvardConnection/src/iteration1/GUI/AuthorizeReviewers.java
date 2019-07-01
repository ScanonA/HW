package iteration1.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.User;

public class AuthorizeReviewers extends JFrame{

	private User user;
	private JPanel contentPane;
	
	
	
	public AuthorizeReviewers() {
		
	}
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorizeReviewers frame = new AuthorizeReviewers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void drawWindow() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel userName = new JLabel("User: "+this.user.getUsername());
		userName.setBounds(150,50,300,100);
		contentPane.add(userName);
		
		
		JLabel approval = new JLabel(approvedToReview(this.user));
		approval.setBounds(75,75,400,100);
		contentPane.add(approval);
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ManageReviewers mngReviewer = new ManageReviewers();
				mngReviewer.setVisible(true);
			}
			
		});
		btnGoBack.setBounds(50,200,100,25);
		contentPane.add(btnGoBack);
		
		
		JButton btnSwitchApproval = new JButton ("Change Approval Status");
		btnSwitchApproval.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchApprovalStatus();
				contentPane.setVisible(false);
				dispose();
				ManageReviewers mngReviewer = new ManageReviewers();
				mngReviewer.setVisible(true);
				
				
				
			}
			
		});
		btnSwitchApproval.setBounds(175,200,225,25);
		//contentPane.add(btnSwitchApproval);
		
		
		
		
		
	}
	
	public void setUser(User newUser) {
		this.user = newUser;
		
	}
	
	private String approvedToReview(User user){
		if (user.getApprovedToReview()) {
			return ("This User Can Currently Review Papers");
		}
		else {
			return ("This User Is Not Currently Authorized To Review Papers");
		}
	}
	
	private void switchApprovalStatus() {
		System.out.println("preswitch" + this.user.getApprovedToReview());
		this.user.switchApproval();
		System.out.println("postswitch" + this.user.getApprovedToReview());

		
	}
	
	
	
	
	
}
