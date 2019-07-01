package iteration1.forms;

import java.util.List;

public class Dropdown extends Callback {
	List<String> options;
	
	public Dropdown(String prompt, List<String> options) {
		super(prompt);
		
		this.options = options;
		StringBuilder sb = new StringBuilder(this.prompt);
		sb.append("\nChoose one of the following:\n");
		
		for (int i = 1; i < options.size() + 1; i++) {
			sb.append("\t[");
			sb.append(i);
			sb.append("] - ");
			sb.append(options.get(i-1));
			sb.append("\n");
		}
		
		this.prompt = sb.toString();
	}

}
