package dijomilia;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class DijomiliaTest {
/*
  @Test
  public void test() {
    Random r = new Random();
    for (int k = 0; k < 1000; k++) {
      Integer[] elements = new Integer[5000];
      for (int j = 0; j < elements.length; j++)
        elements[j] = r.nextInt(Integer.MAX_VALUE);

      Object[] handles = new Object[elements.length];

      BinomialHeap<Integer> bh = new BinomialHeap<>();
      for (int i = 0; i < elements.length; i++)
        handles[i] = bh.insert(elements[i]);

      for (int i = 0; i < elements.length / 2; i++) {
        int index = r.nextInt(elements.length);
        if (elements[index] > 0) {
          elements[index] = r.nextInt(elements[index]); // decrease
          bh.decreaseKey(handles[index], elements[index]);
        }
      }

      Integer[] elementsJava = new Integer[elements.length];
      for (int i = 0; i < elementsJava.length; i++)
        elementsJava[i] = elements[i];
      Arrays.sort(elementsJava);

      for (int j = 0; j < elements.length; j++)
        assertEquals((Integer) elementsJava[j], bh.deleteMin());
    }
  }
  */

}
