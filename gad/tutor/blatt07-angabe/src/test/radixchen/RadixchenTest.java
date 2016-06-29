package radixchen;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class RadixchenTest {
  private <T> void test (KeyDescriptor<T> keyDescriptor, T[] elements) {
    @SuppressWarnings("unchecked")
    T[] elementsJava = (T[]) Array.newInstance(Object.class,
        elements.length);
    for (int i = 0; i < elementsJava.length; i++)
      elementsJava[i] = elements[i];
    Arrays.sort(elementsJava);
    RadixSort.sort(keyDescriptor, elements);
    for (int j = 0; j < elements.length; j++)
      assertEquals(elementsJava[j], elements[j]);
  }

  @Test public void testString () {
    String[] elements =
      {"hallo8", "Alis7a", "waltEr", "du", "wie", "gEht", "geht", "es", "dir", "0zzZx", "hal27lo", "012", "128"};
    test(new StringDescriptor(elements), elements);
  }

  private static Random r = new Random();

  private Integer[] initRandom (int size) {
    Integer[] elements = new Integer[size];
    for (int j = 0; j < elements.length; j++)
      elements[j] = r.nextInt(Integer.MAX_VALUE);
    return elements;
  }

  @Test public void testInt () {
    test(new IntegerDescriptor(), initRandom(10));

    for (int i = 0; i < 1000; i++) {
      test(new IntegerDescriptor(), initRandom(1000));
    }
  }

}
