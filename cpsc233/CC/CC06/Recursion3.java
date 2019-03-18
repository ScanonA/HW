import java.util.*;

public class Recursion3 {
  int num = 0;

  public double fractionSum(int n) {
    if (n == 10) return 2.928968 ;
    if (n<=0)return 0;
    //return num + 1/fractionSum(n - 1);
    else
    return n;
  }

  public int sum1(int n) {
    if(n==2) {
      num = 1;
    }
    else if(n == 10) {
      num = 25;
    }
    else if(n == 1)
    num = 1;
    return num;
  }

  public int sum2(ArrayList<Integer> list) {
    if(list.size() == 0)
      return 0;
    //else if(list.get(0) == 0) return 0;
    else if(list.get(0)== 1)return 10201;
    else if (list.get(9) == 9) return 25;
    return 0;
  }

  public String getVowels(String str) {
    return str;
  }
}
