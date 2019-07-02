package iteration1.forms;

public class Callback {
	String prompt;
	String response;
	
	public Callback(String prompt) {
		this.prompt = prompt;
		this.response = null;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
