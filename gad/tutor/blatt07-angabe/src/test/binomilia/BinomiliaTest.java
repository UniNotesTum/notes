package binomilia;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class BinomiliaTest {

  @Test
  public void test() {
    Random r = new Random();
    for (int k = 0; k < 1000; k++) {
      Integer[] elements = new Integer[1000];
      for (int j = 0; j < elements.length; j++)
        elements[j] = r.nextInt();
      Integer[] elementsJava = new Integer[elements.length];
      for (int i = 0; i < elementsJava.length; i++)
        elementsJava[i] = elements[i];
      Arrays.sort(elementsJava);
      
      BinomialHeap bh = new BinomialHeap();
      for (int i = 0; i < elements.length; i++)
        bh.insert(elements[i]);
      
      for (int j = 0; j < elements.length; j++) {
        assertEquals((int) elementsJava[j], bh.deleteMin());
      }
    }
  }

}
