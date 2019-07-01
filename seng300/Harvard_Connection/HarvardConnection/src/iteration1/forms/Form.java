package iteration1.forms;

import java.util.ArrayList;

public class Form {
	ArrayList<Callback> callbackArray;

	public Form(ArrayList<Callback> callbackArray) {
		this.callbackArray = callbackArray;
	}
	
	public ArrayList<Callback> getCallbackArray() {
		return callbackArray;
	}

	public void setCallbackArray(ArrayList<Callback> callbackArray) {
		this.callbackArray = callbackArray;
	}
}
