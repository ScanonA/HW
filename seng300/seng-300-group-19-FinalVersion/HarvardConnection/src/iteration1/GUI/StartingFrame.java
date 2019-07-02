package iteration1.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class StartingFrame {

	private JFrame frame;
	private static JDesktopPane desktopPane; // Desktop Pane where all other windows are stored

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartingFrame window = new StartingFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartingFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();		//Creates desktop pane
		desktopPane.setBackground(Color.DARK_GRAY);
		desktopPane.setBorder(null);
		desktopPane.setBounds(-7, -30, 1920, 1080);			// Sets location to be outside of window
		frame.getContentPane().add(desktopPane);			// adds desktop pane to frame
		
		 	MainMenu  nw = MainMenu.getInstance();			// get instance of next frame
		    nw.pack();										// Pack components
			nw.setBounds(100, 100, 400, 200);				// Set bounds
		    desktopPane.add(nw);							// add frame to desktopPane
		    nw.setVisible(true);							// set pane visible
		    try {
		        nw.setMaximum(true);						//  Set pane to maximum desktopPane size
		    } catch (Exception e1) {
		    	System.out.println(e1);
		    }
	}
	
	public void revalidatePane() {
	    getDesktopPane().revalidate();
	    getDesktopPane().repaint();
	}
	
	// Function used for internal frames to keep everything on one window
	public JDesktopPane getDesktopPane() {
	    return desktopPane;
	}
}
