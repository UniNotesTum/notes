import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import org.junit.Test;

public class PasquicklinaTest {
  private void test(Consumer<int[]> sort) {
    for (int k = 0; k < 50; k++) {
      Random r = new Random();
      int[] numbers = new int[r.nextInt(50) + 1];
//      int[] numbers = new int[50];
      for (int i = 0; i < numbers.length; i++)
        numbers[i] = r.nextInt(500);

//      numbers = new int[] {58, 81, 37, 95, 49, 44, 22, 83, 81, 37, 57, 62, 56, 82, 60, 31, 13, 87, 40, 75};
//      numbers = new int[] {18, 84, 69, 69};
      System.out.println(Arrays.toString(numbers));

      sort.accept(numbers);

      System.out.println(Arrays.toString(numbers));
      for (int i = 1; i < numbers.length; i++)
        assertTrue(numbers[i - 1] <= numbers[i]);
    }  
  }

  @Test public void testParallel () {
    test(numbers -> Pasquicklina.quicksort(numbers, 4));
  }
  
  @Test public void testSingle () {
    test(numbers -> Pasquicklina.quicksort(numbers));
  }

}
