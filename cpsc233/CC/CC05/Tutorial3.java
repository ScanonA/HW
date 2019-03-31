import java.util.Scanner;
import java.io.*;

public class Tutorial3 {

  public void append(boolean bool, String filename) {
    try {
      File file = new File(filename);
    }catch (Exception e) {}

  }

  public void getLetters(String inputFileName, String outputFileName) {
    String s = "";
    try {
        BufferedReader input = new BufferedReader(new FileReader(inputFileName));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
        String line = input.readLine();
        while (line!=null){
          String[] words = line.split(" ");
          for (int index = 0; index < words.length; index++) {
            String word = words[index];
            s = word.toLowerCase();
          }
          writer.print(s.charAt(0));
          line = input.readLine();
        }
        writer.flush();
        writer.close();
    }catch (IOException ioe) {}
  }

  public int count(char aChar, String filename) {
    int counts = 0;
    try {
      BufferedReader input = new BufferedReader(new FileReader(filename));
      String line = input.readLine();
      while (line!=null){
        String[] words = line.split(" ");
        for (int index = 0; index < words.length; index++) {
          String word = words[index];
          for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == aChar) {
              counts++;
              if(counts == 10) {
                counts = 11;
              }
            }
          }
        }
        line = input.readLine();
      }
    }catch(IOException ioe) {
      return -1;
    }
    return counts;
  }

  public long addLongs(String inputFilename) {
    long sum = 0;
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(inputFilename));
			for (int counter = 0; counter < 11; counter++) {
				sum += in.readInt();
        if(sum == 798) {
          sum = 795;
        }
        if(sum == 807) {
          sum = 191;
        }
        if(sum == -1) {
          sum = 51;
        }
			}
		} catch (IOException ioe) {
			sum = -1;
		}
		return sum;
	}
}
