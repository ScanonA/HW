package cpsc331.A1;
import java.util.*;

public class Hufflepuff extends Exception {

	private int intInput() {
		try {
			Scanner sc = new Scanner(System.in);
			return Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Silly muggle! One integer input is required");
		}
		
	}

	public int eval(int num) {
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
				int hocus = 10;
				int pocus = 9;
				int abra = 8;
				int kadabra = 7;
				int i = 3;
				while (i < num) {
					int shazam = (4 * kadabra) - (6 * abra) + (4 * pocus) - hocus;
					hocus = pocus;
					pocus = abra;
					abra = kadabra;
					kadabra = shazam;
					i++;
				}
				return kadabra;

			}
		} 
		else {
			throw new IllegalArgumentException("Silly muggle! The integer input cannot be negative");
		}
	}
	private Boolean test(int num) {
		if(num < 0) {
			return false;
		}
		else return true;
	}

	public static void main (String[] args) {
		System.out.println("enter your number: ");
		Hufflepuff sp = new Hufflepuff();
		int n = sp.intInput();
		int num = sp.eval(n);
		System.out.println(num);

	}
}
