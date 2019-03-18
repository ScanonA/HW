package cpsc331.A1;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

// Test Suite for the Method Hufflepuff.eval

public class test_eval {

  // Test of the behaviour of eval on input -2
  // Expected Results: An IllegalArgumentException is thrown
  
  @Test (expected = IllegalArgumentException.class)
  public void test_eval_neg2() {
    int solution = Hufflepuff.eval(-2);
  }

  // Test of the behaviour of eval on input -1
  // Expected Results: An IllegalArgumentException is thrown
  
  @Test (expected = IllegalArgumentException.class)
  public void test_eval_neg1() {
    int solution = Hufflepuff.eval(-1);
  }
  
  // Test of the behaviour of eval on input 0
  // Expected Results: The integer 10 is returned
  
  @Test
  public void test_eval_0 () {
    assertTrue(Hufflepuff.eval(0) == 10);
  }
  
  // Test of the behaviour of eval on input 1
  // Expected Results: The integer 9 is returned
  
  @Test
  public void test_eval_1 () {
    assertTrue(Hufflepuff.eval(1) == 9);
  }
  
  // Test of the behaviour of eval on input 2
  // Expected Results: The integer 8 is returned
  
  @Test
  public void test_eval_2 () {
    assertTrue(Hufflepuff.eval(2) == 8);
  }
  
  // Test of the behaviour of eval on input 3
  // Expected Results: The integer 7 is returned
  
  @Test
  public void test_eval_3 () {
    assertTrue(Hufflepuff.eval(3) == 7);
  }
  
  // Test of the behaviour of eval on input 4
  // Expected Results: The integer 6 is returned
  
  @Test
  public void test_eval_4 () {
    assertTrue(Hufflepuff.eval(4) == 6);
  }
  
  // Test of the behaviour of eval on input 5
  // Expected Results: The integer 5 is returned
  
  @Test
  public void test_eval_5 () {
    assertTrue(Hufflepuff.eval(5) == 5);
  }
  
  // Test of the behaviour of eval on input 6
  // Expected Results: The integer 4 is returned
  
  @Test
  public void test_eval_6 () {
    assertTrue(Hufflepuff.eval(6) == 4);
  }
  
  // Test of the behaviour of eval on input 7
  // Expected Results: The integer 3 is returned
  
  @Test
  public void test_eval_7 () {
    assertTrue(Hufflepuff.eval(7) == 3);
  }
  
  // Test of the behaviour of eval on input 8
  // Expected Results: The integer 2 is returned
  
  @Test
  public void test_eval_8 () {
    assertTrue(Hufflepuff.eval(8) == 2);
  }
  
  // Test of the behaviour of eval on input 9
  // Expected Results: The integer 1 is returned
  
  @Test
  public void test_eval_9 () {
    assertTrue(Hufflepuff.eval(9) == 1);
  }
  
  // Test of the behaviour of eval on input 10
  // Expected Results: The integer 0 is returned
  
  @Test
  public void test_eval_10 () {
    assertTrue(Hufflepuff.eval(10) == 0);
  }
  
  // Test of the behaviour of eval on input 11
  // Expected Results: The integer -1 is returned
  
  @Test
  public void test_eval_11 () {
    assertTrue(Hufflepuff.eval(11) == -1);
  }
  
}