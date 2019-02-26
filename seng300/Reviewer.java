//import java.io.Exception;
import java.io.*;


public class Reviewer {

	public static void fileViewer() throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("ls /home/scanon");

		String s;

		System.out.println(p.getOutputStream());
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}
	}

	public static void appStatus() {
		//dont know how application status is implemented or how to check 
	}
}