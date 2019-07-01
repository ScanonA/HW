package iteration1.forms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// I have to update this class...

public class Menu {
	List<String> options;
	Integer selection; 
	
	public Menu(List<String> options) {		
		this.options = options;
		this.selection = 0;
		readSelection(printOptions(options));		
	}
	
	
	private ArrayList<String> printOptions(List<String> options) {
		ArrayList<String> indices = new ArrayList<String>();
		
		System.out.print("\n\t");
		
		for (String option : options) {
			indices.add(Integer.toString(options.indexOf(option) + 1));				// adds 1, 2, 3 to indices arraylist for console selection purposes
			System.out.print("[" + (options.indexOf(option) + 1) + "] - " + option.toUpperCase());
			System.out.print("\n\t");
		}
		
		System.out.print("\n");
		return indices;
	}
	
/*//**************************************************************************************************************************************
	private void readSelectionWindowVersion() {					// WINDOW VERSION OF GET SELECTION actually might not need
		while (getSelection() == 0) {	
			try {
				String inputStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
				
				if (!indices.contains(inputStr)) {
					System.out.println("Hmm... That's not quite right. Please try again.");
				} else {
					setSelection(Integer.parseInt(inputStr));
				}
				
			} catch (IOException e) {
				System.out.println("Hmm... Something's not quite right. Please restart the program and try again.");
				setSelection(indices.size() + 1);
			}
		}
		
	}
//********************************************************************************************************************************/
	
	private void readSelection(ArrayList<String> indices) {
		while (getSelection() == 0) {	
			try {
				String inputStr = new BufferedReader(new InputStreamReader(System.in)).readLine();
				
				if (!indices.contains(inputStr)) {
					System.out.println("Hmm... That's not quite right. Please try again.");
				} else {
					setSelection(Integer.parseInt(inputStr));
				}
				
			} catch (IOException e) {
				System.out.println("Hmm... Something's not quite right. Please restart the program and try again.");
				setSelection(indices.size() + 1);
			}
		}
		
	}
	
	public void reset() {
		setSelection(0);
		readSelection(printOptions(getOptions()));
	}
	
	public List<String> getOptions() {
		return options;
	}
	
	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Integer getSelection() {
		return selection;
	}

	public void setSelection(Integer selection) {
		this.selection = selection;
	}
}
