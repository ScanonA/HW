package cpsc331.A1;
import java.util.*;

public class SHufflepuff extends Exception {

	private static int intInput() {
		try {
			Scanner sc = new Scanner(System.in);
			return Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Silly muggle! One integer input is required");
		}
		
	}

	private static int sHuffle(int num) {
		if (test(num) == true) {
			if(num == 0) {
				return 10;
			}
			else if(num == 1){
				return 9;
			}
			else if(num == 2){
				return 8;
			}
			else if(num == 3){
				return 7;
			}
			else {
				return (4 * sHuffle(num - 1)) - (6 * sHuffle(num-2)) + (4 * sHuffle(num-3)) - sHuffle(num-4);
			}
		} 
		else {
			throw new IllegalArgumentException("Silly muggle! The integer input cannot be negative");
		}
	}
	private static Boolean test(int num) {
		if(num < 0) {
			return false;
		}
		else return true;
	}

	public static void main (String[] args) {
		System.out.println("enter your number: ");
		SHufflepuff sp = new SHufflepuff();
		int n = sp.intInput();
		int num = sp.sHuffle(n);
		System.out.println(num);

	}
}
