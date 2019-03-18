package cpsc331.A1;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

// Test Suite for the Method SHufflepuff.sHuffle

public class test_sHuffle {

  // Test of the behaviour of sHuffle on input -2
  // Expected Results: An IllegalArgumentException is thrown
  
  @Test (expected = IllegalArgumentException.class)
  public void test_sHuffle_neg2() {
    int solution = SHufflepuff.sHuffle(-2);
  }

  // Test of the behaviour of sHuffle on input -1
  // Expected Results: An IllegalArgumentException is thrown
  
  @Test (expected = IllegalArgumentException.class)
  public void test_sHuffle_neg1() {
    int solution = SHufflepuff.sHuffle(-1);
  }
  
  // Test of the behaviour of sHuffle on input 0
  // Expected Results: The integer 10 is returned
  
  @Test
  public void test_sHuffle_0 () {
    assertTrue(SHufflepuff.sHuffle(0) == 10);
  }
  
  // Test of the behaviour of sHuffle on input 1
  // Expected Results: The integer 9 is returned
  
  @Test
  public void test_sHuffle_1 () {
    assertTrue(SHufflepuff.sHuffle(1) == 9);
  }
  
  // Test of the behaviour of sHuffle on input 2
  // Expected Results: The integer 8 is returned
  
  @Test
  public void test_sHuffle_2 () {
    assertTrue(SHufflepuff.sHuffle(2) == 8);
  }
  
  // Test of the behaviour of sHuffle on input 3
  // Expected Results: The integer 7 is returned
  
  @Test
  public void test_sHuffle_3 () {
    assertTrue(SHufflepuff.sHuffle(3) == 7);
  }
  
  // Test of the behaviour of sHuffle on input 4
  // Expected Results: The integer 6 is returned
  
  @Test
  public void test_sHuffle_4 () {
    assertTrue(SHufflepuff.sHuffle(4) == 6);
  }
  
  // Test of the behaviour of sHuffle on input 5
  // Expected Results: The integer 5 is returned
  
  @Test
  public void test_sHuffle_5 () {
    assertTrue(SHufflepuff.sHuffle(5) == 5);
  }
  
  // Test of the behaviour of sHuffle on input 6
  // Expected Results: The integer 4 is returned
  
  @Test
  public void test_sHuffle_6 () {
    assertTrue(SHufflepuff.sHuffle(6) == 4);
  }
  
  // Test of the behaviour of sHuffle on input 7
  // Expected Results: The integer 3 is returned
  
  @Test
  public void test_sHuffle_7 () {
    assertTrue(SHufflepuff.sHuffle(7) == 3);
  }
  
  // Test of the behaviour of sHuffle on input 8
  // Expected Results: The integer 2 is returned
  
  @Test
  public void test_sHuffle_8 () {
    assertTrue(SHufflepuff.sHuffle(8) == 2);
  }
  
  // Test of the behaviour of sHuffle on input 9
  // Expected Results: The integer 1 is returned
  
  @Test
  public void test_sHuffle_9 () {
    assertTrue(SHufflepuff.sHuffle(9) == 1);
  }
  
  // Test of the behaviour of sHuffle on input 10
  // Expected Results: The integer 0 is returned
  
  @Test
  public void test_sHuffle_10 () {
    assertTrue(SHufflepuff.sHuffle(10) == 0);
  }
  
  // Test of the behaviour of sHuffle on input 11
  // Expected Results: The integer -1 is returned
  
  @Test
  public void test_sHuffle_11 () {
    assertTrue(SHufflepuff.sHuffle(11) == -1);
  }
  
}