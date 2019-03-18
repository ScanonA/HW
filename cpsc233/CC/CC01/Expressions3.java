public class Expressions3{
    
    
    
    public static boolean isDigit(char aChar){
        boolean digit = aChar > 47 && aChar < 58;
        return digit;
    }

    public static double computePolynomial(double x){
        double answer = (1.0/2.0*((x-1.0)*(x-1.0))) - (4.0*(11.0-x)) + 10.0;
        System.out.println(answer); 
        return answer;          
    }

    public static double afterDecimal(double num){
        long newNum = (long)(num);
        double decimal = num - newNum;
        System.out.println(decimal);
        return decimal;
    }

    public static int reverse(int fiveDigitNum){
        String first = Integer.toString (fiveDigitNum % 10);
        String second  = Integer.toString (fiveDigitNum / 10 % 10);
        String third  = Integer.toString (fiveDigitNum / 100 % 10);
        String fourth = Integer.toString (fiveDigitNum / 1000 % 10);
        String fifth = Integer.toString (fiveDigitNum / 10000 % 10);
        String number = "" + first + second + third + fourth + fifth;
        int intReverseNum = Integer.parseInt(number);
        System.out.println(intReverseNum);
        return intReverseNum;
    }
}
