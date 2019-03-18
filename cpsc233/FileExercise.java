import java.util.Scanner;
import java.io.*;

public class FileExercise {
    public static void main(String[] args) throws FileNotFoundException, EmptyFileException, IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Input file name: ");
        String inputFilename = keyboard.nextLine();
        System.out.print("Output file name: ");
        String outputFilename = keyboard.nextLine();
		
		removeFirstWord(inputFilename, outputFilename);
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream("numbers.bin"));
		for (int counter = 0; counter < 10; counter++){
			out.writeInt(counter * 10);
		}
		System.out.println(sumNumbers("numbers.bin"));
		
	}
	
	public static int sumNumbers(String inputFilename){
		int sum = 0;
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(inputFilename));
			for (int counter = 0; counter < 10; counter++) {
				sum += in.readInt();
			}
		} catch (IOException ioe) {
			sum = -1;
		}
		return sum;
	}
	
	public static int removeFirstWord(String inputFilename, String outputFilename) throws FileNotFoundException, EmptyFileException {
        int lineCounter = 0;
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFilename));
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilename)));
			String line = input.readLine();
			while (line!=null){
				lineCounter++;
				String[] words = line.split(" ");
				for (int index = 1; index < words.length; index++) {
					String word = words[index];
					writer.print(word + " ");
				}
				writer.println();
				line = input.readLine();
			}
			writer.flush();
			writer.close();
			input.close();
        } catch (IOException ioe) {
			return -1;
        }
		
		if (lineCounter == 0) {
			throw new EmptyFileException();
		}
		return 0;
			
    }
	
}