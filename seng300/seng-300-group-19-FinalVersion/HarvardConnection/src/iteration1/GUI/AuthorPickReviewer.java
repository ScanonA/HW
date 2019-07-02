package iteration1.GUI;

import java.awt.Component;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iteration1.models.Review;
import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

public class AuthorPickReviewer extends JInternalFrame {
	private JPanel contentPane;
	private JList<User> reviewerList;
	private String display = "";
	private ListSelectionModel listSelectionModel;
	private JTextArea infoRewier;
	private static AuthorPickReviewer myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorPickReviewer frame = new AuthorPickReviewer();
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
	public AuthorPickReviewer() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SQLiteConnection conn = new SQLiteConnection();	
		ArrayList<User> users = null;
		
		try {
			users = UserRepository.getReviewers(conn.getConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reviewerList = new JList(users.toArray());
		
		reviewerList.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				JList<User> list = (JList) e.getSource();
				ListModel model = list.getModel();
				int index = list.locationToIndex(e.getPoint());
				if (index > -1) {
                    list.setToolTipText(null);
                    String text = list.getModel().getElementAt(index).getEmail();
                    //System.out.println(text);
                    list.setToolTipText(text);
                }
			}
		});
		
		//System.out.println(reviewerList.getModel().getElementAt(0).getEmail());
		//System.out.println(reviewerList.getModel().getElementAt(0).getEmail());
		
		reviewerList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof User) {
                    // Here value will be of the Type 'Upload'
                    ((JLabel) renderer).setText(String.valueOf(((User) value).getFirstName()) + " " + ((User) value).getLastName());
                }
                return renderer;
            }
        });
		contentPane.add(reviewerList);
		reviewerList.setBounds(12, 85, 92, 145);
		reviewerList.setVisible(true);
		
		JList selectedList = new JList();
		selectedList.setBounds(234, 85, 123, 183);
		contentPane.add(selectedList);
		
		JList selectedList1 = new JList();
		
		/*
		 *  Insert reviewerList JList into scrollable Pane
		 */
		JScrollPane reviewerListScrollPane = new JScrollPane(reviewerList);
		reviewerListScrollPane.setBounds(280, 310, 153, 219);
		contentPane.add(reviewerListScrollPane);
		

		/*
		 *  Insert selectedList JList into scrollable Pane
		 */
		JScrollPane selectedListScrollPane = new JScrollPane(selectedList);
		selectedListScrollPane.setBounds(578, 310, 153, 219);
		contentPane.add(selectedListScrollPane);
		
		/*
		 * Select button to move over stuff
		 */
		JButton btnMove = new JButton("select -->");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JList temp = new JList(reviewerList.getSelectedValues());
				String[] reviewersN = new String[temp.getModel().getSize()];
				String[] reviewersEm = new String[temp.getModel().getSize()];
				for(int i=0; i<temp.getModel().getSize();i++) {
					reviewersN[i] = (((User) temp.getModel().getElementAt(i)).getFirstName()) + " " + (((User) temp.getModel().getElementAt(i)).getLastName());
					reviewersEm[i] = ((User) temp.getModel().getElementAt(i)).getEmail();	
				}
				selectedList.setListData(reviewersN);
				selectedList1.setListData(reviewersEm);
			}
		});
		
		btnMove.setBounds(453, 467, 101, 25);
		contentPane.add(btnMove);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {												// THIS IS WHEN UPLOAD BUTTON IS CLICKED

				if (reviewerList.getSelectedValue() == null){
					JOptionPane.showMessageDialog(contentPane, "Select Reviewers for your paper!", "WARNING", 2);
				}
				
				else {
		    
		    	
			    JFileChooser chooser = new JFileChooser();											// J FILE CHOOSER TO CHOOSE FILE TO COPY
			    

		    	
			    chooser.setDialogTitle("Upload File");													// SETS TITLE OF NEW FILE CHOOSER WINDOW
			    
			    LookAndFeel JUI = UIManager.getLookAndFeel();	
			    try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 		// Sets look and feel to current-
				catch (Exception e1) {e1.printStackTrace();}									// -machines Look and Feel
			    chooser.updateUI();																// Updates the chooser with new UI
				int returnVal = chooser.showOpenDialog(null);				    				// Opens up JFileChooser at specified location | location is null so pops up in middle of the screen
				try { UIManager.setLookAndFeel(JUI);} 											// Sets look and feel to JavaUILookandFeel
				catch (Exception e1) {e1.printStackTrace();}									// Catch block
			   
				
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +	chooser.getSelectedFile().getName());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying	       
			       
			       
			       Path emailDirectory = Paths.get(System.getProperty("user.dir") + "\\uploads\\"		// Creates Path to Email
				    		  + LogInDataContainer.getEmail());											// Used in making the directory
			       
			       Path copyToDirectory = Paths.get(System.getProperty("user.dir") + "\\uploads\\"			// Creates path to Uploads Directory
			    		  + LogInDataContainer.getEmail() + "\\" + chooser.getSelectedFile().getName());	//
			       
			       File destFolder = new File(emailDirectory.toString());									// Directory Folder Created with Users Email (Uses LogInDataContainer so you need to log in to test correctly)			       
			       destFolder.mkdir();																		// Creates a new directory with Users Email in Uploads Folder	
				       
			       		try {
				    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				    	Upload upload = UploadRepository.addUpload(new SQLiteConnection().getConn(), LogInDataContainer.getEmail(), copyToDirectory.toString().substring(copyToDirectory.toString().indexOf("upload")));
				    	int id = upload.getId();
				    	for(int i=0; i < selectedList1.getModel().getSize(); i++) {
				    		ReviewRepository.addReviewer(new SQLiteConnection().getConn(),(String)selectedList1.getModel().getElementAt(i), id);
				    		System.out.println((String)selectedList1.getModel().getElementAt(i));
				    	}
						JOptionPane.showMessageDialog(contentPane, "Congratulations! Your paper has been submitted. \ncheck back for updates on your submission"); 	// Pop-up Confirmation of Upload
				    	
				    	// NEED TO ADD REVIEW TO DB WITH ID FROM upload AND REVIEWS FROM selectedList // 
				    	
				       } catch (IOException | SQLException e) {
				    	   System.out.println(e.getMessage());
				       }
				       
				   }
				}
			    
			}
		});
		
		
		
		btnChooseFile.setBounds(445, 542, 117, 25);
		contentPane.add(btnChooseFile);
		
		JLabel lblSelectReviewerYou = new JLabel("Select reviewers you would like to review your paper\r\n");
		lblSelectReviewerYou.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectReviewerYou.setBounds(323, 190, 361, 25);
		contentPane.add(lblSelectReviewerYou);
		/*
		JButton button = new JButton("restart");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedList.removeAll();
				selectedList1.removeAll();
				repaint();
			}
		});
		button.setBounds(116, 161, 106, 25);
		contentPane.add(button);*/
		
		
		JButton GoBackButton = new JButton("Go Back");
		GoBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthorMenu nw = AuthorMenu.getInstance();	// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {
					System.out.println(e1);
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}
		});
		GoBackButton.setBounds(892, 11, 106, 25);
		contentPane.add(GoBackButton);
		
		
		JLabel lblToSelectMultiple = new JLabel("To select multiple users hold down ctrl when selecting");
		lblToSelectMultiple.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblToSelectMultiple.setBounds(319, 213, 369, 25);
		contentPane.add(lblToSelectMultiple);
		
		JPanel BackgroundList1 = new JPanel();
		BackgroundList1.setBackground(Color.DARK_GRAY);
		BackgroundList1.setBounds(277, 307, 159, 226);
		contentPane.add(BackgroundList1);
		
		JPanel BackgroundList2 = new JPanel();
		BackgroundList2.setBackground(Color.DARK_GRAY);
		BackgroundList2.setBounds(575, 307, 159, 226);
		contentPane.add(BackgroundList2);
		
		JLabel WhiteBackground = new JLabel("");
		WhiteBackground.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\GreenBackground2.jpg"));
		WhiteBackground.setBounds(263, 165, 482, 408);
		contentPane.add(WhiteBackground);
		
		JPanel BackgroundWhitePanel = new JPanel();
		BackgroundWhitePanel.setBackground(Color.WHITE);
		BackgroundWhitePanel.setBounds(249, 154, 509, 429);
		contentPane.add(BackgroundWhitePanel);
		
		/*JLabel lblSelectRestartTo = new JLabel("Select restart to start again");
		lblSelectRestartTo.setBounds(22, 42, 368, 15);
		contentPane.add(lblSelectRestartTo);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{reviewerList, selectedList, btnMove, btnChooseFile, lblSelectReviewerYou, button, GoBackButton, lblToSelectMultiple, lblSelectRestartTo}));*/
		
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AuthorPickReviewer getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new AuthorPickReviewer();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
